package com.genezeiniss.file_scanner.rest.model.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Value;

@Value
public class SecretDetectResponse {

    @JsonProperty("filename")
    String fileName;
    @JsonProperty("AKI_line")
    int accessKeyLine;
    @JsonProperty("SAK_line")
    int secretKeyLine;
}
