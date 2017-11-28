package com.bellidov.trade.business.model.response;

import com.bellidov.trade.business.model.Transaction;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GetValidatorResponse {
    
    private Boolean status;
    private Map<Transaction, List<String>> errors;

    
    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }
    
    public Map<Transaction, List<String>> getErrors() {
        if(errors == null) {
            errors = new HashMap<>();
        }
        return errors;
    }
}
