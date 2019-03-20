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

import org.glassfish.jersey.server.ResourceConfig;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import javax.inject.Inject;

/**
 * @author aganesh
 * @since 2019-03-19
 */
@Component
public class MyJerseyConfig extends ResourceConfig {
    private static final String SCAN_PACKAGES = "com.hammerspace.jerseyusage.clientserver";

    /**
     * Constructor.
     *
     * @param appCtx  The existing Spring app context.
     */
    @Inject
    public MyJerseyConfig(ApplicationContext appCtx) {
        // look here for jersey resources
        packages(SCAN_PACKAGES);
        // so grizzly will use our existing Spring app context
        property("contextConfig", appCtx);  // use the existing Spring application context

        // Jersey logging options - if you use ON_DEMAND for the
        // tracing.type, you can send special request headers
        // and tracing will be enabled and configured only for that request:
        //      X-Jersey-Tracing-Accept - enabled tracing for this request (value not used)
        //      X-Jersey-Tracing-Threshold - set the trace level (value may be SUMMARY, TRACE, VERBOSE)
        //      X-Jersey-Tracing-Logger - used to override the tracing Java logger name suffix (value is suffix)
        //property(ServerProperties.TRACING, "ALL");                  // OFF (def), ON_DEMAND, ALL      //NOSONAR
        //property(ServerProperties.TRACING_THRESHOLD, "TRACE");      // SUMMARY, TRACE (def), VERBOSE  //NOSONAR
    }
}

