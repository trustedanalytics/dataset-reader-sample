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

import org.apache.hadoop.fs.FileSystem;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.trustedanalytics.hadoop.config.client.helper.Hdfs;
import org.trustedanalytics.resourceserver.data.DataProvider;

import javax.security.auth.login.LoginException;
import java.io.IOException;
import java.net.URISyntaxException;

@Configuration
public class ApplicationConfiguration {

    @Bean
    public DataProvider getDataProvider() {
        return new DataProvider();
    }

    @Bean
    public FileSystem getFileSystem() throws IOException, LoginException,
        InterruptedException, URISyntaxException {
      return Hdfs.newInstance().createFileSystem();
    }

}
