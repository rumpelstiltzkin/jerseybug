package com.hammerspace.jerseyusage.clientserver;

import lombok.extern.slf4j.Slf4j;

import javax.inject.Singleton;
import javax.ws.rs.Consumes;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.xml.bind.DatatypeConverter;

// import lombok.extern.slf4j.Slf4j;

import java.nio.charset.Charset;
import java.util.Random;

/**
 * @author aganesh
 * @since 2019-03-18
 */
// @Component TODO: can this cause the bug?
@Singleton
@Consumes({ MediaType.APPLICATION_JSON })
@Produces({ MediaType.APPLICATION_JSON })
// @Slf4j
@Path(Constants.REST_SERVER_BASE_PATH)
public class SyncServer {
    private static final int KV_STR_SIZE = 64;
    private static final Random RANDOM = new Random();

    private final int payloadSize;

    public SyncServer(int payloadSize) {
        this.payloadSize = payloadSize;
    }

    private static String makeRandomString(int strSize) {
        byte[] array = new byte[strSize];
        RANDOM.nextBytes(array);
        return new String(array, Charset.forName("UTF-8"));
    }

    private MyResponse makeResponse() {
        KeysValues keysValues = new KeysValues();
        int payloadSz = payloadSize;
        while (payloadSz >= 0) {
            String newStr = makeRandomString(KV_STR_SIZE);
            keysValues.set(newStr + "_key", newStr + "_value");
            payloadSz -= KV_STR_SIZE;
        }
        return new MyResponse(keysValues);
    }

    @PUT
    @Path(Constants.TEST_RESOURCE_PATH + "/{testresource}")
    public Response serverHandler(@PathParam("testresource") String testResource, MyRequest req) {
        try {
            MyResponse rsp;
            byte[] testResourceBytes = DatatypeConverter.parseHexBinary(testResource);
            System.out.println("resource=" + testResourceBytes + ", request=" + req);
            rsp = makeResponse();
            System.out.println("returning response=" + rsp);
            return Response.ok().build();
        } catch (Exception e) {
            return Response.serverError().entity(e.getMessage()).build();
        }
    }
}
