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

package org.trustedanalytics.resourceserver.data;

import com.google.common.collect.Lists;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.io.IOUtils;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.trustedanalytics.utils.hdfs.HdfsConfig;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringWriter;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;

/**
 * This is really simple data provider, reading value from property. In the real
 * world, you might want to read something from database here.
 */

@Component
public class DataProvider {

    private static final Logger LOGGER = Logger.getLogger(DataProvider.class.getName());

    @Autowired
    private HdfsConfig hdfsConfig;

    public String getContent(String filePath)
        throws IllegalArgumentException, IOException {

        FileSystem fs = hdfsConfig.getFileSystem();
        InputStream is = fs.open(new Path(filePath));

        StringWriter writer = new StringWriter();
        IOUtils.copy(is, writer, "utf-8");
        String data = writer.toString();

        return data;
    }

    public List<List<String>> getParsedContent(String path)
        throws IllegalArgumentException, IOException {

        FileSystem fs = hdfsConfig.getFileSystem();
        InputStream is = fs.open(new Path(path));

        List<List<String>> result = null;
        try (CSVParser parser = new CSVParser(new InputStreamReader(is), CSVFormat.newFormat(','))) {
            result = parser.getRecords().stream()
                .map(row -> Lists.newArrayList(row.iterator()))
                .collect(Collectors.toList());
        } catch(Exception e) {
            LOGGER.log(Level.SEVERE, "Error parsing CSV file", e);
        }

        return result;
    }

}
