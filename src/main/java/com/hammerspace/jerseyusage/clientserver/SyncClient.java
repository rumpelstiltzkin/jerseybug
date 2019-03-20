package com.hammerspace.jerseyusage.clientserver;

import org.glassfish.jersey.client.ClientConfig;
import org.glassfish.jersey.jdk.connector.JdkConnectorProvider;
import org.glassfish.jersey.jsonp.JsonProcessingFeature;
import org.glassfish.jersey.logging.LoggingFeature;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.concurrent.TimeUnit;

/**
 * @author aganesh
 * @since 2019-03-20
 */
public class SyncClient {
    private static final long TIMEOUT_MILLIS = TimeUnit.MINUTES.toMillis(5);

    private static final String TEST_RESOURCE_NAME = "testresource";

    private final Client client;
    private final String baseUri;

    public SyncClient() {
        ClientBuilder builder = ClientBuilder.newBuilder();
        this.client = builder
                .register(JsonProcessingFeature.class)
                .register(LoggingFeature.class)
                .property(LoggingFeature.LOGGING_FEATURE_LOGGER_NAME_CLIENT, "hammerspaceJerseySyncTestLog")
                .connectTimeout(TIMEOUT_MILLIS, TimeUnit.MILLISECONDS)
                .readTimeout(TIMEOUT_MILLIS, TimeUnit.MILLISECONDS)
                .withConfig(new ClientConfig().connectorProvider(new JdkConnectorProvider()))
                .build();

        this.baseUri = "http://localhost:" + Constants.REST_SERVER_PORT + '/' + Constants.REST_SERVER_BASE_PATH;
    }

    public Response clientInvoke() {
        KeysValues kvs = new KeysValues();
        kvs.set("clientkey1", "clientval1");
        MyRequest myRequest = new MyRequest(kvs);

        return client.target(baseUri)
                .path(Constants.TEST_RESOURCE_PATH)
                .path("{testresource}").resolveTemplate("testresource", TEST_RESOURCE_NAME)
                .request(MediaType.APPLICATION_JSON)
                .put(Entity.entity(myRequest, MediaType.APPLICATION_JSON));
    }
}
