/**
 * Copyright (c) 2015 Intel Corporation
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.trustedanalytics.resourceserver;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.trustedanalytics.resourceserver.data.DataProvider;
import org.trustedanalytics.resourceserver.data.InputStreamProvider;
import org.trustedanalytics.utils.hdfs.HdfsConfig;

@Configuration
@ComponentScan("org.trustedanalytics.utils.hdfs")
public class ApplicationConfiguration {

    @Bean
    public DataProvider getDataProvider() {
        return new DataProvider();
    }

    @Bean
    public InputStreamProvider getInputStreamProvider(HdfsConfig hdfsConfig) {
        return new InputStreamProvider(hdfsConfig);
    }
}
