package com.hammerspace.jerseyusage.clientserver;

import com.fasterxml.jackson.annotation.JsonInclude;

/**
 * @author aganesh
 * @since 2019-03-18
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class MyRequest {

    private KeysValues keysValues;

    /**
     * Default constructor - used by Jackson.
     */
    @SuppressWarnings({"unused", "WeakerAccess"})
    public MyRequest() {
    }

    public MyRequest(KeysValues keysValues) {
        this.keysValues = keysValues;
    }

    public KeysValues getKeysValues() {
        return keysValues;
    }

    public void setKeysValues(KeysValues keysValues) {
        this.keysValues = keysValues;
    }

    @Override
    public String toString() {
        return "MyRequest{"
                + ", keysValues=" + keysValues
                + '}';
    }
}
