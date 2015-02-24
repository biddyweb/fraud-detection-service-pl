package com.ffinance.rest
import com.ffinance.Collaborators
import com.ffinance.model.Client
import com.ffinance.model.ValidatedClient
import com.ffinance.service.FraudDetectionService
import com.ofg.infrastructure.web.resttemplate.fluent.ServiceRestClient
import com.wordnik.swagger.annotations.Api
import com.wordnik.swagger.annotations.ApiOperation
import groovy.transform.CompileStatic
import groovy.transform.TypeChecked
import groovy.util.logging.Slf4j
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

import javax.validation.constraints.NotNull
import java.util.concurrent.Callable

import static org.springframework.web.bind.annotation.RequestMethod.PUT

@Slf4j
@RestController
@RequestMapping('/api/loanApplication/{loanApplicationId}')
@TypeChecked
@CompileStatic
@Api(value = "loanApplicationId", description = "Verifies the loan application in terms of potential fraud")
class FraudDetectionController {

    private final ServiceRestClient serviceRestClient;
    
    private final FraudDetectionService fraudDetectionService;

    @Autowired
    FraudDetectionController(ServiceRestClient serviceRestClient, FraudDetectionService fraudDetectionService) {
        this.serviceRestClient = serviceRestClient
        this.fraudDetectionService = fraudDetectionService
    }

    @RequestMapping(
            value = '{loanApplicationId}',
            method = PUT,
            consumes = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Async collecting loan application to verify", notes = "This will asynchronously call LoanApplicationDecisionMaker")
    Callable<Void> notify(@PathVariable @NotNull final long loanApplicationId,  @RequestBody @NotNull final Client client) {
        return { ->
            def status = fraudDetectionService.checkClientFraudStatus(client)
            ValidatedClient validatedClient = new ValidatedClient(client)
            validatedClient.fraudStatus = status.name()
            serviceRestClient.forService(Collaborators.LOAN_APPLICATION_DECISION_MAKER)
                    .put()
                    .onUrlFromTemplate("/api/loanApplication/{loanApplicationId}").withVariables(loanApplicationId)
                    .body(validatedClient)
                    .withHeaders()
                    .contentTypeJson()
                    .andExecuteFor().ignoringResponse()
        }
    }
}
