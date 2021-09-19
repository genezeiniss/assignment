package com.genezeiniss.file_scanner.service

import spock.lang.Specification

class SecretDetectServiceSpec extends Specification {

    SecretDetectService secretDetectService

    def setup() {
        secretDetectService = new SecretDetectService()
        secretDetectService.maxSecondsUntilScanExpired = 3600
    }

    def "search file"() {
        when:
        def firstCallResult = secretDetectService.scanFiles("/Users/genezeiniss/Desktop/tmp/")
        def secondCallResult = secretDetectService.scanFiles("/Users/genezeiniss/Desktop/tmp/")

        then: "first call is about to perform scan"
        assert firstCallResult.size() == 1
        assert firstCallResult.get(0).fileName == "positive_example.py"
        assert firstCallResult.get(0).accessKeyLine == 3
        assert firstCallResult.get(0).secretKeyLine == 5

        and: "second call is about to return stored result"
        assert secondCallResult.size() == 1
        assert secondCallResult.get(0).fileName == "positive_example.py"
        assert secondCallResult.get(0).accessKeyLine == 3
        assert secondCallResult.get(0).secretKeyLine == 5
    }
}
