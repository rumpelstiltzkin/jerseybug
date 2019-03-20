package com.hammerspace.jerseyusage.clientserver;

import com.fasterxml.jackson.annotation.JsonInclude;

/**
 * @author aganesh
 * @since 2019-03-18
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class MyResponse {
    private KeysValues keysValues;

    @SuppressWarnings("unused") // except by Jackson
    public MyResponse() {
    }

    public MyResponse(KeysValues keysValues) {
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
        return "MyResponse{" + "keysValues=" + keysValues + '}';
    }
}
