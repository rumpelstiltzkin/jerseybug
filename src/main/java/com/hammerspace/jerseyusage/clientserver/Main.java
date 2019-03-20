package com.hammerspace.jerseyusage.clientserver;

import org.glassfish.grizzly.http.server.HttpServer;
import org.glassfish.jersey.grizzly2.httpserver.GrizzlyHttpServerFactory;
import org.glassfish.jersey.server.ResourceConfig;

import javax.ws.rs.client.InvocationCallback;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriBuilder;

import java.net.URI;


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
                    System.out.println("async callback completed()");
                    int status = response.getStatus();
                    System.out.println("Got status=" + status + " from server");
                    switch (status) {
                        case 200:
                            MyResponse myResponse = response.readEntity(MyResponse.class);
                            System.out.println("Got MyResponse=" + myResponse);
                            break;
                        default:
                            String errMsg = response.readEntity(String.class);
                            System.out.println("Got errMsg=" + errMsg);
                            break;
                    }
                    wakeUpMain();
                }

                public void failed(Throwable throwable) {
                    System.out.println("async callback failed() with error " + throwable.getMessage());
                    wakeUpMain();
                }
            });

            waitForAsyncCallback();
            System.out.println("Main exiting ...");
            exit(0);
        }

        // server
        HttpServer server;
        URI baseUri = UriBuilder.fromUri("http://localhost/").port(Constants.REST_SERVER_PORT).build();
        int payloadSize = 64;
        if (args.length > 1) {
            try {
                payloadSize = Integer.valueOf(args[1]);
            } catch (Exception e) {
                System.out.println("Got exception = " + e.getMessage() + " when parsing number from " + args[1]);
            }
        }
        System.out.println("Using payloadSize=" + payloadSize + " bytes");
        SyncServerResource resource = new SyncServerResource(payloadSize);

        ResourceConfig resourceConfig = new ResourceConfig().register(resource);
        server = GrizzlyHttpServerFactory.createHttpServer(baseUri, resourceConfig);
        try {
            server.start();
            System.out.println("Server started ...");
        } catch (Exception e) {
            System.out.println("Error starting server " + e.getMessage());
            exit(2);
        }

        System.out.println("Server will wait around until Ctrl-C is entered ...");
        waitForAsyncCallback(); // it will never arrive; for server simply hit Ctrl-C to quit
        exit(0);
    }
}


