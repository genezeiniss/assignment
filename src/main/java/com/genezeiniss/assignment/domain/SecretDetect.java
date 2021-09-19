package com.genezeiniss.assignment.domain;

import lombok.Data;

@Data
public class SecretDetect {

    private String id;
    private String fileName;
    private int accessKeyLine;
    private int secretKeyLine;
}
