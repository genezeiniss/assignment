package com.genezeiniss.file_scanner.domain;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@ToString
@EqualsAndHashCode
public class SecretDetect {

    private String id;
    private String fileName;
    private int accessKeyLine;
    private int secretKeyLine;
}
