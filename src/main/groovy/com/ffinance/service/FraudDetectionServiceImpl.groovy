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
        if(isClientFraud(client)) {
            status = ClientFraudStatus.FRAUD
        } else if(isClientFishy(client)) {
            status = ClientFraudStatus.FISHY
        } else if(isClientOK(client)) {
            status = ClientFraudStatus.GOOD
        }
        return status
    }

    boolean isClientFraud(Client client) {
        return JobPosition.OTHER.getName().equalsIgnoreCase(client.job) ||
                isClientTooYoung(client) ||
                isLoanAmountTooHigh(client) ||
                isClientNameTooShort(client)
    }

    boolean isClientFishy(Client client) {
        return JobPosition.FINANCE_SECTOR.getName().equalsIgnoreCase(client.job) ||
                isClientTooOld(client) ||
                isLoanAmountFishy(client) ||    
                isClientNameTooLong(client)
    }


    boolean isClientOK(Client client) {
        return JobPosition.IT.getName().equalsIgnoreCase(client.job) ||
                isClientWithIdealAge(client) ||
                isLoanAmountOk(client) ||
                isClientNameOk(client)
    }

    private boolean isClientTooYoung(Client client) {
        client.age < MIN_AGE
    }

    private boolean isClientTooOld(Client client) {
        client.age > MAX_AGE
    }
    
    private boolean isClientWithIdealAge(Client client) {
        client.age >= MIN_AGE && client.age <= MAX_AGE
    }

    boolean isLoanAmountOk(Client client) {
        client.amount > FIRST_THRESHOLD
    }
    
    boolean isLoanAmountTooHigh(Client client) {
        client.amount > SECOND_THRESHOLD
    }

    boolean isLoanAmountFishy(Client client) {
        client.amount >= FIRST_THRESHOLD && client.amount <= SECOND_THRESHOLD
    }

    boolean isClientNameTooShort(Client client) {
        client.firstName.length() < 2 || client.lastName.length() < 2
    }

    boolean isClientNameTooLong(Client client) {
        client.firstName.length() > 25 || client.lastName.length() > 25
    }

    boolean isClientNameOk(Client client) {
        (client.firstName.length() >= MIN_NAME_LENGTH && client.firstName.length() <= MAX_NAME_LENGTH) ||
                (client.lastName.length() >= MIN_NAME_LENGTH && client.lastName.length() <= MAX_NAME_LENGTH)
    }

}
