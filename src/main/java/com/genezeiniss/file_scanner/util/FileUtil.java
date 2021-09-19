package com.genezeiniss.file_scanner.util;

import java.io.File;
import java.util.Arrays;
import java.util.Optional;
import java.util.stream.Stream;

public class FileUtil {

    // todo: extend run time exception - add http error code: 400
    public static Stream<File> getFilesOrElseThrowException(String localPath) {

        File[] filesList = Optional.of(new File(localPath))
                .map(File::listFiles)
                .orElseThrow(() ->
                        new RuntimeException(String.format("folder %s is not exist!", localPath)));

        if (filesList.length == 0) {
            throw new RuntimeException(String.format("folder %s is empty!", localPath));
        }
        return Arrays.stream(filesList);
    }
}