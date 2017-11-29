package com.bellidov.trade.business.model;

import java.util.ArrayList;
import java.util.List;

public class ErrorDescription {

    private Transaction transaction;
    private List<String> errors;

    public Transaction getTransaction() {
        return transaction;
    }

    public void setTransaction(Transaction transaction) {
        this.transaction = transaction;
    }

    public List<String> getErrors() {
        if(errors == null) {
            errors = new ArrayList<>();
        }
        return errors;
    }

    public void setErrors(List<String> errors) {
        this.errors = errors;
    }
}
