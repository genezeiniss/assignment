package com.genezeiniss.assignment

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.context.ContextConfiguration
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.ResultActions
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import spock.lang.Specification

@AutoConfigureMockMvc
@ActiveProfiles(profiles = ["test"])
@ContextConfiguration(classes = AssignmentApplication)
@SpringBootTest(classes = AssignmentApplication.class)
class SecretDetectControllerSpecIT extends Specification {

    @Autowired
    MockMvc mockMvc

    def "scan files in path to detect secret - one posiyive example"() {
        expect:
        postScanFilesInPath("/Users/genezeiniss/Desktop/tmp/")
                .andExpect(status().isCreated())
                .andExpect(jsonPath("filename").value("positive_example.py"))
                .andExpect(jsonPath("AKI_line").value("3"))
                .andExpect(jsonPath("SAK_line").value("5"))
                .andReturn()
    }

    private ResultActions postScanFilesInPath(String localPath) {
        return mockMvc.perform(MockMvcRequestBuilders
                .post("/secret-detect")
                .contentType(MediaType.APPLICATION_JSON)
                .content(localPath))
    }
}
