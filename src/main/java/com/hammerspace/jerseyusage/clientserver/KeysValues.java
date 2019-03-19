package com.hammerspace.jerseyusage.clientserver;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.google.common.collect.Maps;

import javax.annotation.Nonnull;
import java.util.Collections;
import java.util.Map;
import java.util.SortedMap;

/**
 * @author aganesh
 * @since 2019-03-18
 */
public class KeysValues {
    private final SortedMap<String, String> mdMap = Maps.newTreeMap();   // makes tests deterministic for @JsonAnyGetter

    public KeysValues() {
    }

    /**
     * Return the key-value map. Called by Jackson during map serialization and by unit test code to validate contents.
     * This is a Jackson "any-getter", which means Jackson calls it to obtain every field in the object being serialized
     * by iterating the map entries to obtain keys (field names) and values (field values).
     *
     * @return the key-value map which is used by Jackson to define the JSON output fields and values.
     */
    @JsonAnyGetter
    @JsonProperty("meta")
    @JsonPropertyOrder(alphabetic = true)
    public Map<String, String> getMap() {
        return Collections.unmodifiableMap(mdMap);
    }

    /**
     * Add a key-value pair to the map. Called by Jackson during map deserialization to set values in the mdMap map
     * on this object. Also called by unit test code. This is a Jackson "any-setter", which means Jackson calls it for
     * each field name and value read from the JSON object meant to be stored as a map ({@link #mdMap} in this case).
     *
     * @param key the key to be added - may not be null.
     * @param value the value to be added under {@code key} - may not be null.
     */
    @JsonAnySetter
    @JsonProperty("meta")
    public void set(@Nonnull String key, @Nonnull String value) {
        mdMap.put(key, value);
    }
}