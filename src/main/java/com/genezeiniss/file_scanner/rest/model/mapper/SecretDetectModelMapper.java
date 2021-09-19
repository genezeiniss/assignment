package com.genezeiniss.file_scanner.rest.model.mapper;

import com.genezeiniss.file_scanner.domain.SecretDetect;
import com.genezeiniss.file_scanner.rest.model.response.SecretDetectResponse;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class SecretDetectModelMapper {

    private final ModelMapper modelMapper;

    public SecretDetectResponse mapSecretDetectToResponse(SecretDetect secretDetect) {
        return modelMapper.map(secretDetect, SecretDetectResponse.class);
    }
}
