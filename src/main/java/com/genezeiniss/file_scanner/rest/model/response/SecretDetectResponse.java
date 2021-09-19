package com.genezeiniss.file_scanner.rest.model.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class SecretDetectResponse {

    @JsonProperty("filename")
    private String fileName;
    @JsonProperty("AKI_line")
    private int accessKeyLine;
    @JsonProperty("SAK_line")
    private int secretKeyLine;
}
