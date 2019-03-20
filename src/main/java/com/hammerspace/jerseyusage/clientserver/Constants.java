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

/**
 * @author aganesh
 * @since 2019-03-18
 */
public class Constants {

    public static final String REST_SERVER_BASE_PATH = "testbug";
    public static final int REST_SERVER_PORT = 9999;

    public static final String TEST_RESOURCE_PATH = "test";

    private Constants() {
        throw new IllegalStateException("should not be instantiated");
    }
}
