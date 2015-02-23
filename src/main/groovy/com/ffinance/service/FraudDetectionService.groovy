package com.ffinance.service

import com.ffinance.model.Client
import com.ffinance.model.ClientFraudStatus

interface FraudDetectionService {
    
    ClientFraudStatus checkClientFraudStatus(Client client)
    
}
