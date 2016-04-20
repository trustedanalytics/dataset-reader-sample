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

package org.trustedanalytics.resourceserver.rest;

import static org.springframework.http.MediaType.ALL_VALUE;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.web.bind.annotation.RequestMethod.GET;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.trustedanalytics.resourceserver.data.DataProvider;

import javax.security.auth.login.LoginException;


@RestController
public class ResourceController {
    public static final String DATASET_PATH = "/rest/dataset";
    public static final String PARSED_DATASET_PATH = "/rest/parsed-dataset";

    @Value("${file:}")
    private String filePath;

    @Autowired
    private DataProvider dataProvider;


    @RequestMapping(value = DATASET_PATH, method = GET, produces = ALL_VALUE)
    public String getRawData()
        throws IllegalArgumentException, IOException {

        System.out.println("Using filePath:" + filePath);

        return dataProvider.getContent(filePath);
    }

    @RequestMapping(value = PARSED_DATASET_PATH, method = GET, produces = APPLICATION_JSON_VALUE)
    public List<List<String>> getParsedData()
        throws IllegalArgumentException, IOException {

        System.out.println("Using filePath:" + filePath);

        return dataProvider.getParsedContent(filePath);
    }
}
