package com.ffinance

import com.ffinance.model.Client
import com.ffinance.model.ClientFraudStatus
import com.ffinance.service.FraudDetectionService
import com.ffinance.service.FraudDetectionServiceImpl
import spock.lang.Specification
import spock.lang.Unroll

class FraudDetectionServiceSpec extends Specification {

    FraudDetectionService service = new FraudDetectionServiceImpl()

    @Unroll
    def "check fraud status for #firstName #lastName with job: #job of age: #age and asking for amount: #amount"() {
        given:

        def inMsg = new Client(firstName: firstName, lastName: lastName, job: job, amount: amount, age: age)

        when:

        def status = service.checkClientFraudStatus(inMsg)

        then:

        status == ClientFraudStatus.valueOf(fraudStatus)

        where:

        firstName                                      | lastName                                              | job              | amount | age | fraudStatus

        "John"                                         | "Doe"                                                 | "OTHER"          | 50     | 20  | "FRAUD"
        "John"                                         | "Doe"                                                 | "OTHER"          | 50     | 17  | "FRAUD"
        "John"                                         | "Doe"                                                 | "OTHER"          | 2001   | 20  | "FRAUD"
        "John"                                         | "D"                                                   | "OTHER"          | 100    | 22  | "FRAUD"
        "J"                                            | "Doe"                                                 | "OTHER"          | 100    | 22  | "FRAUD"

        "John"                                         | "Doe"                                                 | "FINANCE SECTOR" | 50     | 20  | "FISHY"
        "John"                                         | "Doe"                                                 | "Cook"           | 50     | 66  | "FISHY"
        "John"                                         | "Doe"                                                 | "IT"             | 50     | 66  | "FISHY"
        "John"                                         | "Doe"                                                 | "Cook"           | 1001   | 20  | "FISHY"
        "John"                                         | "Doe"                                                 | "IT"             | 1001   | 20  | "FISHY"
        "John"                                         | "Doe"                                                 | "Cook"           | 1500   | 20  | "FISHY"
        "John"                                         | "Doe"                                                 | "Cook"           | 2000   | 20  | "FISHY"
        "John"                                         | "Doe"                                                 | "IT"             | 2000   | 20  | "FISHY"
        "John"                                         | "Doeeraeraeraeraeraeraeraeraeraeraeraeraeraeraererae" | "Cook"           | 50     | 20  | "FISHY"
        "John"                                         | "Doeeraeraeraeraeraeraeraeraeraeraeraeraeraeraererae" | "IT"             | 50     | 20  | "FISHY"
        "Johnafadfadfadfadfadfadfadfadfadfadfadfadfda" | "Doe"                                                 | "Cook"           | 50     | 20  | "FISHY"
        "Johnafadfadfadfadfadfadfadfadfadfadfadfadfda" | "Doe"                                                 | "IT"             | 50     | 20  | "FISHY"

        "John"                                         | "Doe"                                                 | "IT"             | 50     | 20  | "GOOD"
        "John"                                         | "Doe"                                                 | "IT"             | 50     | 18  | "GOOD"
        "John"                                         | "Doe"                                                 | "IT"             | 50     | 65  | "GOOD"
        "John"                                         | "Doe"                                                 | "IT"             | 999    | 65  | "GOOD"

    }


}
