package com.ffinance.service

import com.ffinance.model.Client
import com.ffinance.model.ClientFraudStatus
import com.ffinance.model.JobPosition
import org.springframework.stereotype.Service

@Service
class FraudDetectionServiceImpl implements FraudDetectionService {

    private static final Integer MIN_AGE = 18
    private static final Integer MAX_AGE = 65
    private static final BigDecimal FIRST_THRESHOLD = 1000
    private static final BigDecimal SECOND_THRESHOLD = 2000
    private static final Integer MIN_NAME_LENGTH = 2
    private static final Integer MAX_NAME_LENGTH = 25
    
    @Override
    ClientFraudStatus checkClientFraudStatus(Client client) {
        final ClientFraudStatus status
        if(isClientFraud(loanApplication)) {
            status = FraudStatus.FRAUD
        } else if(isClientFishy(loanApplication)) {
            status = FraudStatus.FISHY
        } else if(isClientOK(loanApplication)) {
            status = FraudStatus.OK
        }
        return status
    }

    boolean isClientFraud(Client client) {
        return JobPosition.OTHER.getName().equalsIgnoreCase(client.job) &&
                client.age < MIN_AGE &&
                isAmountGreaterThanSecondThreshold(client) &&
                isClientNameTooShort(client)
    }

    boolean isClientFishy(Client client) {
        return JobPosition.FINANCE_SECTOR.getName().equalsIgnoreCase(client.job) &&
                client.age > MAX_AGE &&
                isAmountBetweenThresholds(client) &&
                isClientNameTooLong(client.firstName, client.lastName)
    }

    boolean isClientOK(Client client) {
        return JobPosition.IT.getName().equalsIgnoreCase(client.job) &&
                client.age > MIN_AGE &&
                client.age < MAX_AGE &&
                isAmountLessThanFirstThreshold(client) &&
                isClientNameOk(client)
    }

    boolean isAmountGreaterThanSecondThreshold(Client client) {
        return client.amount > SECOND_THRESHOLD
    }

    boolean isAmountBetweenThresholds(Client client) {
        return client.amount > FIRST_THRESHOLD && client.amount < SECOND_THRESHOLD
    }

    boolean isClientNameTooShort(Client client) {
        return client.firstName.length() < 2 ||
                client.lastName() < 2
    }

    boolean isClientNameTooLong(Client client) {
        return client.firstName.length() > 25 ||
                client.lastName() < 25
    }

    boolean isAmountLessThanFirstThreshold(Client client) {
        return client.amount < FIRST_THRESHOLD
    }

    boolean isClientNameOk(Client client) {
        return (client.firstName > MIN_NAME_LENGTH && client.firstName < MAX_NAME_LENGTH) ||
                (client.lastName > MIN_NAME_LENGTH && client.lastName < MAX_NAME_LENGTH)
    }

}
