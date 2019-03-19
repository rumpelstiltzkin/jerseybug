package com.hammerspace.jerseyusage.clientserver;

import com.sun.net.httpserver.HttpServer;

import javax.ws.rs.client.InvocationCallback;
import javax.ws.rs.core.Response;

import static java.lang.System.exit;

/**
 * @author aganesh
 * @since 2019-03-18
 */
public class Main {

    private static final String CLIENT = "client";
    private static final String SERVER = "server";

    private static final Object SYNCHRO = new Object();

    private static void wakeUpMain() {
        synchronized (SYNCHRO) {
            SYNCHRO.notify();
        }
    }

    private static void waitForAsyncCallback() {
        synchronized (SYNCHRO) {
            try {
                SYNCHRO.wait();
            } catch (InterruptedException ie) {
                System.out.println("got interrupted: " + ie);
            }
        }
    }

    public static void main(String args[]) {
        if (args.length < 1) {
            System.out.println("invoke with parameter '" + CLIENT + "' or '" + SERVER + " <payloadsize in bytes>' without the quotes");
            exit(1);
        }

        if (args[0].equals(CLIENT)) {
            AsyncClient client = new AsyncClient();
            client.clientInvoke(new InvocationCallback<Response>() {
                public void completed(Response response) {
                    int status = response.getStatus();
                    switch (status) {
                        case 200:

                    }
                    System.out.println("async callback completed()");
                    wakeUpMain();
                }

                public void failed(Throwable throwable) {
                    System.out.println("async callback failed()");
                    wakeUpMain();
                }
            });

            waitForAsyncCallback();
            exit(0);
        }

        // server
        HttpServer server;
        int payloadSize = 1024;
        if (args.length > 1) {
            try {
                payloadSize = Integer.valueOf(args[1]);
            } catch (Exception e) {
                System.out.println("Got exception = " + e.getMessage() + " when parsing number from " + args[1]);
            }
        }
        System.out.println("Using payloadSize=" + payloadSize + " bytes");
        SyncServer resource = new SyncServer(payloadSize);
    }
}
