package com.genezeiniss.file_scanner.service

import spock.lang.Ignore
import spock.lang.Specification
import spock.lang.Unroll

@Ignore("test looking for local folder")
class SecretDetectServiceSpec extends Specification {

    SecretDetectService secretDetectService

    def setup() {
        secretDetectService = new SecretDetectService()
        secretDetectService.scanValidityTime = 3600
    }

    def "scan files - local path contains files"() {
        when:
        def firstCallResult = secretDetectService.scanFiles("/Users/genezeiniss/Desktop/tmp/")
        def secondCallResult = secretDetectService.scanFiles("/Users/genezeiniss/Desktop/tmp/")

        then: "no exception expected to be thrown"
        noExceptionThrown()

        and: "first call is about to perform scan"
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

    @Unroll
    def "scan files - #scenario"() {
        when:
        secretDetectService.scanFiles(localPath)

        then: "exception expected to be thrown"
        thrown(RuntimeException)

        where:
        scenario              | localPath
        "folder is not exist" | "/not-exist/"
        "folder is empty"     | "/Users/genezeiniss/Desktop/empty-folder/"
    }
}