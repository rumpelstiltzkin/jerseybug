// Copyright (c) 2019 Hammerspace, Inc.
// 	  www.hammer.space
//
// Licensed under the Eclipse Public License - v 2.0 ("the License");
// you may not use this file except in compliance with the License.
// You may obtain a copy of the License at
//
//     https://www.eclipse.org/org/documents/epl-2.0/EPL-2.0.txt
//
// Unless required by applicable law or agreed to in writing, software
// distributed under the License is distributed on an "AS IS" BASIS,
// WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// See the License for the specific language governing permissions and
// limitations under the License.

package com.hammerspace.jerseyusage.clientserver;

import org.springframework.stereotype.Component;

import javax.inject.Singleton;
import javax.ws.rs.Consumes;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import java.util.Random;

/**
 * @author aganesh
 * @since 2019-03-18
 */
@Component
@Singleton
@Consumes({ MediaType.APPLICATION_JSON })
@Produces({ MediaType.APPLICATION_JSON })
@Path(Constants.REST_SERVER_BASE_PATH)
public class SyncServerResource {
    private static final int KV_STR_SIZE = 64;
    private static final Random RANDOM = new Random();
    private static final String ALL_CHARS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890";

    private final int payloadSize;

    public SyncServerResource(int payloadSize) {
        this.payloadSize = payloadSize;
    }

    private static String makeRandomString(int strSize) {
        String retVal = "";
        int randomIndex = RANDOM.nextInt(ALL_CHARS.length());
        char c = ALL_CHARS.charAt(randomIndex); // pick a random character
        for (int x = 0; x < strSize; x++) {
            retVal += c;
        }
        return retVal;
    }

    private MyResponse makeResponse() {
        KeysValues keysValues = new KeysValues();
        System.out.println("Making responses for payloadSize=" + payloadSize);
        int payloadSz = payloadSize;
        while (payloadSz > 0) {
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
            System.out.println("resource=" + testResource + ", request=" + req);
            rsp = makeResponse();
            System.out.println("returning response=" + rsp);
            return Response.ok(rsp).build();
        } catch (Exception e) {
            System.out.println("Exception=" + e.getMessage() + " thrown");
            return Response.serverError().entity(e.getMessage()).build();
        }
    }
}
