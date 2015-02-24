package com.ffinance

import com.ffinance.model.Client
import com.ffinance.model.ClientFraudStatus
import com.ffinance.rest.FraudDetectionController
import com.ffinance.service.FraudDetectionService
import com.ofg.infrastructure.web.resttemplate.fluent.ServiceRestClient
import spock.lang.Specification

class FraudDetectionControllerSpec extends Specification {

    ServiceRestClient serviceRestClient = Mock()

    FraudDetectionService fraudDetectionService = Mock()

    FraudDetectionController controller = new FraudDetectionController(serviceRestClient, fraudDetectionService)


    def "should call call the service"() {

        serviceRestClient._ >> serviceRestClient

        fraudDetectionService.checkClientFraudStatus(_) >> ClientFraudStatus.FISHY

        when:

        controller.notify(123L, new Client()).call()

        then:

            1 * serviceRestClient.ignoringResponse()

    }

}
