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

    @Override
    public String toString() {
        StringBuilder sb =  new StringBuilder(1024);
        sb.append('{');
        mdMap.forEach((key, value) -> {
            sb.append('\n').append(key).append('=').append(value).append(',');
        });
        sb.setLength(sb.length() - 1);
        sb.append('}');
        return sb.toString();
    }
}
