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

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.apache.hadoop.fs.FileStatus;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.runners.MockitoJUnitRunner;
import org.mockito.stubbing.Answer;
import org.trustedanalytics.utils.hdfs.HdfsConfig;

import java.io.IOException;
import java.io.InputStream;
import java.io.SequenceInputStream;

@RunWith(MockitoJUnitRunner.class)
public class InputStreamProviderTest {

    @Mock
    private HdfsConfig hdfsConfig;

    @Mock
    private FileSystem fileSystem;

    private InputStreamProvider sut;

    @Before
    public void setUp() {
        when(hdfsConfig.getFileSystem()).thenReturn(fileSystem);

        sut = new InputStreamProvider(hdfsConfig);
    }

    @Test
    public void test_getContent_fileGiven_getStandardInputStream() throws IOException {
        when(fileSystem.isFile(any(Path.class))).thenReturn(true);
        Path path = mock(Path.class);

        InputStream result = sut.getInputStream(path);

        verify(fileSystem).open(path);
        Assert.assertFalse(result instanceof SequenceInputStream);
    }

    @Test
    public void test_getContent_dirGiven_getSequentialInputStream() throws IOException {
        when(fileSystem.isFile(any(Path.class))).thenReturn(false);
        when(fileSystem.isDirectory(any(Path.class))).thenReturn(true);
        when(fileSystem.listStatus(any(Path.class))).thenReturn(new FileStatus[] {
            mockFileStatus(), mockFileStatus(), mockFileStatus()
        });
        Path path = mock(Path.class);

        InputStream result = sut.getInputStream(path);

        verify(fileSystem, times(3)).open(any(Path.class));
        Assert.assertTrue(result instanceof SequenceInputStream);
    }

    @Test(expected = IllegalArgumentException.class)
    public void test_getContent_neitherFileNorDirGiven_throwException() throws IOException {
        when(fileSystem.isFile(any(Path.class))).thenReturn(false);
        when(fileSystem.isDirectory(any(Path.class))).thenReturn(false);
        Path path = mock(Path.class);

        sut.getInputStream(path);
    }

    private FileStatus mockFileStatus() {
        FileStatus fileStatus = mock(FileStatus.class, new Answer() {
            @Override public Object answer(InvocationOnMock invocationOnMock) throws Throwable {
                return mock(Path.class);
            }
        });
        return fileStatus;
    }
}
