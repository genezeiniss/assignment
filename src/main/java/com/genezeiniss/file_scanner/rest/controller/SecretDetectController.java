package com.genezeiniss.file_scanner.rest.controller;

import com.genezeiniss.file_scanner.domain.SecretDetect;
import com.genezeiniss.file_scanner.rest.model.mapper.SecretDetectModelMapper;
import com.genezeiniss.file_scanner.rest.model.request.SecretDetectRequest;
import com.genezeiniss.file_scanner.rest.model.response.SecretDetectResponse;
import com.genezeiniss.file_scanner.service.SecretDetectService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@AllArgsConstructor
@RequestMapping("secret-detect")
public class SecretDetectController {

    private final SecretDetectService secretDetectService;
    private final SecretDetectModelMapper secretDetectModelMapper;

    @PostMapping
    public List<SecretDetectResponse> scanFilesInPath(@RequestBody SecretDetectRequest secretDetectRequest) {
        List<SecretDetect> secretDetects = secretDetectService.scanFiles(secretDetectRequest.getLocalPath());
        return secretDetects.stream().map(secretDetectModelMapper::mapSecretDetectToResponse).collect(Collectors.toList());
    }

    @GetMapping("/")
    public String index() {
        return "Greetings from Spring Boot!";
    }
}
