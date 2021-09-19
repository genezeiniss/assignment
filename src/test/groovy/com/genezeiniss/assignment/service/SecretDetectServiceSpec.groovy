package com.genezeiniss.assignment.service

import spock.lang.Specification

class SecretDetectServiceSpec extends Specification {

    SecretDetectService secretDetectService

    def setup() {
        secretDetectService = new SecretDetectService()
        secretDetectService.maxSecondsUntilScanExpired = 3600
    }

    def "search file"() {
        when:
        def detectedList = secretDetectService.scanFiles("/Users/genezeiniss/Desktop/tmp/")

        then:
        assert detectedList.size() == 1
        assert detectedList.get(0).fileName == "positive_example.py"
        assert detectedList.get(0).accessKeyLine == 3
        assert detectedList.get(0).secretKeyLine == 5
    }
}
