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
