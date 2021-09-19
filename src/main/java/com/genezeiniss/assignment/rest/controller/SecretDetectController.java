package com.genezeiniss.assignment.rest.controller;

import com.genezeiniss.assignment.domain.SecretDetect;
import com.genezeiniss.assignment.rest.model.mapper.SecretDetectModelMapper;
import com.genezeiniss.assignment.rest.model.request.SecretDetectRequest;
import com.genezeiniss.assignment.rest.model.response.SecretDetectResponse;
import com.genezeiniss.assignment.service.SecretDetectService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}
