package com.ffinance.accurest

import com.jayway.restassured.module.mockmvc.RestAssuredMockMvc
import spock.lang.Specification

abstract class BaseMockMvcSpec extends Specification {

    def setup() {
        //RestAssuredMockMvc.standaloneSetup(new PairIdController(createAndStubPropagationWorker()))
    }

    //private PropagationWorker    createAndStubPropagationWorker() {
    //    return Mock(PropagationWorker)
    //}
}