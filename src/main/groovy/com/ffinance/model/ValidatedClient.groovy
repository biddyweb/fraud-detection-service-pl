package com.ffinance.model

class ValidatedClient extends Client {

    String fraudStatus
    
    ValidatedClient (Client client) {
        firstName = client.firstName
        lastName = client.lastName
        job = client.job
        amount = client.amount
        age = client.age
    }
    
}
