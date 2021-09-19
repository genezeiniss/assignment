package com.genezeiniss.file_scanner.service;

import com.genezeiniss.file_scanner.domain.SecretDetect;
import com.genezeiniss.file_scanner.util.FileUtil;
import lombok.Getter;
import lombok.Setter;
import lombok.Synchronized;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.time.Instant;
import java.util.*;

@Slf4j
@Setter
@Getter
@Service
@Scope(ConfigurableBeanFactory.SCOPE_SINGLETON)
public class SecretDetectService {

    @Value("${secret-detect.max-seconds-until-scan-expired:3600}")
    private int scanValidityTime;

    private Map<String, Instant> scanRunMap = new HashMap<>();
    private Map<String, List<SecretDetect>> lastScanResult = new HashMap<>();

    // todo: the hardcoded string should be followed by non-empty string
    private final String ACCESS_KEY_PATTERN = "(?s).*\\bAccessKeyId\\b.*";
    private final String SECRET_KEY_PATTERN = "(?s).*\\bSecretAccessKey\\b.*";

    @Synchronized
    public List<SecretDetect> scanFiles(String localPath) {

        if (shouldRunScan(localPath)) {

            log.info(
                    String.format("path %s wasn't scanned in last %s seconds. Is about to perform path scan", localPath, scanValidityTime));

            List<SecretDetect> secretDetectList = new ArrayList<>();

            FileUtil.getFilesOrElseThrowException(localPath)
                    .forEach(file -> detectSecretInFile(file).ifPresent(secretDetectList::add));

            storeLastScanResult(localPath, secretDetectList);
        }

        return lastScanResult.get(localPath);
    }

    private Optional<SecretDetect> detectSecretInFile(File file) {

        try (FileInputStream inputStream = new FileInputStream(file);
             Scanner scanner = new Scanner(inputStream, StandardCharsets.UTF_8.name())) {

            SecretDetect secretDetect = new SecretDetect();
            secretDetect.setFileName(file.getName());

            String line;

            for (int lineNumber = 1; scanner.hasNextLine(); lineNumber++) {

                line = scanner.nextLine();

                if (line.matches(ACCESS_KEY_PATTERN)) {

                    secretDetect.setAccessKeyLine(lineNumber);

                    for (int j = lineNumber + 1; j <= lineNumber + 3; j++) {

                        line = scanner.nextLine();

                        if (line.matches(SECRET_KEY_PATTERN)) {
                            secretDetect.setSecretKeyLine(j);
                            return Optional.of(secretDetect);
                        }

                    }
                }
            }
        } catch (IOException exception) {
           throw new RuntimeException("something went wrong", exception);
        }

        return Optional.empty();
    }

    protected boolean shouldRunScan(String localPath) {
        if (scanRunMap.get(localPath) == null) { //first time service is up
            return true;
        }
        return Duration.between(scanRunMap.get(localPath), Instant.now()).getSeconds() >= scanValidityTime;
    }

    private void storeLastScanResult(String localPath, List<SecretDetect> secretDetects) {
        scanRunMap.put(localPath, Instant.now());
        lastScanResult.put(localPath, secretDetects);
    }
}
