package com.hammerspace.jerseyusage.clientserver;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.InvocationCallback;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.xml.bind.DatatypeConverter;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import org.glassfish.jersey.client.ClientConfig;
import org.glassfish.jersey.jdk.connector.JdkConnectorProvider;
import org.glassfish.jersey.jsonp.JsonProcessingFeature;
import org.glassfish.jersey.logging.LoggingFeature;
import lombok.extern.slf4j.Slf4j;

/**
 * @author aganesh
 * @since 2019-03-18
 */
@Slf4j
public class AsyncClient {
    private static final int CORE_POOL_SIZE = 2;
    private static final int MAX_POOL_SIZE = 2;
    private static final long TIMEOUT_MILLIS = TimeUnit.MINUTES.toMillis(5);

    private static final byte[] TEST_RESOURCE_NAME = "testinput".getBytes();

    private final ExecutorService es;
    private final Client client;
    private final String baseUri;

    public AsyncClient() {
        es = new ThreadPoolExecutor(CORE_POOL_SIZE, MAX_POOL_SIZE, 0L, TimeUnit.MILLISECONDS,
                new LinkedBlockingQueue<Runnable>());

        ClientBuilder builder = ClientBuilder.newBuilder();
        this.client = builder
                .executorService(es)
                .register(JsonProcessingFeature.class)
                .register(LoggingFeature.class)
                .property(LoggingFeature.LOGGING_FEATURE_LOGGER_NAME_CLIENT, log.getName())
                .connectTimeout(TIMEOUT_MILLIS, TimeUnit.MILLISECONDS)
                .readTimeout(TIMEOUT_MILLIS, TimeUnit.MILLISECONDS)
                .withConfig(new ClientConfig().connectorProvider(new JdkConnectorProvider()))
                .build();

        this.baseUri = "http://localhost:" + Constants.REST_SERVER_PORT + '/' + Constants.REST_SERVER_BASE_PATH;
    }

    public Future<Response> clientInvoke(InvocationCallback<Response> handler) {
        KeysValues kvs = new KeysValues();
        kvs.set("clientkey1", "clientval1");
        MyRequest myRequest = new MyRequest(kvs);

        return client.target(baseUri)
                .path(Constants.TEST_RESOURCE_PATH)
                .path("{testresource}").resolveTemplate("testresource", DatatypeConverter.printHexBinary(TEST_RESOURCE_NAME))
                .request(MediaType.APPLICATION_JSON)
                .async()
                .put(Entity.entity(myRequest, MediaType.APPLICATION_JSON), handler);
    }
}
