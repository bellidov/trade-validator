package com.bellidov.trade.business.service;

import com.bellidov.trade.business.model.Transaction;
import com.bellidov.trade.business.model.response.GetValidatorResponse;
import com.bellidov.trade.support.Customer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ValidatorService {

    private static final Logger LOGGER = LoggerFactory.getLogger(ValidatorService.class);

    @Value("${error.validation.001}")
    private String COUNTERPARTY_UNSUPPORTED_ERROR;

    @Value("${error.validation.002}")
    private String BAD_VALUE_DATE_ERROR;
    
    public GetValidatorResponse getValidatorResponse(Transaction ... transactions){
        GetValidatorResponse response = new GetValidatorResponse();
        
        for(Transaction transaction : transactions){
            List<String> errors = validateTransaction(transaction);
            if(errors.isEmpty()) {
                response.setStatus(true);
            }
            else {
                response.setStatus(false);
                response.getErrors().put(transaction, errors);
            }
        }
        
        return response;
    }
    
    private List<String> validateTransaction(Transaction transaction) {
        
        List<String> errors = new ArrayList<>();

        try{
            Customer.valueOf(transaction.getCustomer());
        }
        catch (IllegalArgumentException e) {
            errors.add(COUNTERPARTY_UNSUPPORTED_ERROR);
        }
        
        if(transaction.getValueDate() != null && transaction.getValueDate().before(transaction.getTradeDate())){
            errors.add(BAD_VALUE_DATE_ERROR);
        }
        
        return errors;
    }
    
}
