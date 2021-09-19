package com.genezeiniss.assignment.rest.model.mapper;

import com.genezeiniss.assignment.domain.SecretDetect;
import com.genezeiniss.assignment.rest.model.response.SecretDetectResponse;
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
