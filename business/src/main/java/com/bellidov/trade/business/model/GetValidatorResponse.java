package com.bellidov.trade.business.model;

import java.util.ArrayList;
import java.util.List;

public class GetValidatorResponse {
    
    private Boolean status;
    private List<ErrorDescription> report;

    
    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public List<ErrorDescription> getReport() {
        if(report == null) {
            report = new ArrayList<>();
        }
        return report;
    }

    public void putError(Transaction transaction, List<String> errors){
        ErrorDescription errorDescription = new ErrorDescription();
        errorDescription.setTransaction(transaction);
        errorDescription.setErrors(errors);
        getReport().add(errorDescription);
    }
}
