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

import org.apache.hadoop.fs.FileStatus;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.springframework.beans.factory.annotation.Autowired;
import org.trustedanalytics.utils.hdfs.HdfsConfig;

import java.io.IOException;
import java.io.InputStream;
import java.io.SequenceInputStream;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;

public class InputStreamProvider {

    private static final Logger LOGGER = Logger.getLogger(InputStreamProvider.class.getName());

    private final HdfsConfig hdfsConfig;

    @Autowired
    public InputStreamProvider(HdfsConfig hdfsConfig) {
        this.hdfsConfig = hdfsConfig;
    }

    /**
     * Gets a InputStream for a path on HDFS. If given path is a directory, it will read
     * files inside that dir and create a SequenceInputStream from them, which emulates
     * reading from directory just like from a regular file. Notice that this method is not meant
     * to read huge datasets (as well as the whole project).
     * @param path
     * @return
     * @throws IOException
     */
    public InputStream getInputStream(Path path) throws IOException {
        Objects.requireNonNull(path);

        FileSystem fs = hdfsConfig.getFileSystem();
        if (fs.isFile(path)) {
            return fs.open(path);
        } else if(fs.isDirectory(path)) {
            FileStatus[] files = fs.listStatus(path);
            List<InputStream> paths = Arrays.stream(files)
                .map(f -> {
                    try {
                        return fs.open(f.getPath());
                    } catch (IOException e) {
                        LOGGER.log(Level.SEVERE, "Cannot read file " + f.getPath().toString(), e);
                        return null;
                    }
                })
                .filter(f -> f != null)
                .collect(Collectors.toList());
            return new SequenceInputStream(Collections.enumeration(paths));
        } else {
            throw new IllegalArgumentException("Given path " + path.toString()
                + " is neither file nor directory");
        }
    }
}
