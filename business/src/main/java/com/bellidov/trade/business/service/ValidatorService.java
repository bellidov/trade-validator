package com.bellidov.trade.business.service;

import com.bellidov.trade.business.model.Transaction;
import com.bellidov.trade.business.model.GetValidatorResponse;
import com.bellidov.trade.support.Customer;
import com.bellidov.trade.support.ProductType;
import com.bellidov.trade.support.Style;
import org.joda.time.DateTime;
import org.joda.time.Days;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class ValidatorService {

    private static final Logger LOGGER = LoggerFactory.getLogger(ValidatorService.class);

    @Value("${error.validation.001}")
    private String COUNTERPARTY_UNSUPPORTED_ERROR;
    @Value("${error.validation.002}")
    private String BAD_VALUE_DATE_ERROR;
    @Value("${error.validation.003}")
    private String VALUEDATE_NON_WORKING_DAY_ERROR;
    @Value("${error.validation.004}")
    private String BAD_CURRENCY_CODE_ERROR;
    @Value("${error.validation.005}")
    private String DATE_VALUE_SPOT_ERROR;
    @Value("${error.validation.006}")
    private String DATE_VALUE_FORWARD_ERROR;
    @Value("${error.validation.007}")
    private String STYLE_NOT_VALID_ERROR;
    @Value("${error.validation.008}")
    private String EXCERCISE_START_DATE_ERROR;
    @Value("${error.validation.009}")
    private String EXPIRY_DATE_ERROR;
    @Value("${error.validation.010}")
    private String PREMIUM_DATE_ERROR;

    
    public GetValidatorResponse getValidatorResponse(Transaction ... transactions){
        GetValidatorResponse response = new GetValidatorResponse();
        
        for(Transaction transaction : transactions){
            List<String> errors = validateTransaction(transaction);
            if(errors.isEmpty()) {
                response.setStatus(true);
            }
            else {
                response.setStatus(false);
                response.putError(transaction, errors);
            }
        }
        
        return response;
    }
    
    private List<String> validateTransaction(Transaction transaction) {
        
        List<String> errors = new ArrayList<>();

        try{
            ProductType type = ProductType.valueOf(transaction.getType().toUpperCase());


            int days = -1;

            if(transaction.getTradeDate() != null && transaction.getValueDate() != null){
                days = Days.daysBetween(new DateTime(transaction.getTradeDate()), new DateTime(transaction.getValueDate())).getDays();
            }

            switch (type) {
                case SPOT:
                    if (days != 2){
                        errors.add(DATE_VALUE_SPOT_ERROR);
                    }
                    break;
                case FORWARD:
                    if (days < 3) {
                        errors.add(DATE_VALUE_FORWARD_ERROR);
                    }
                    break;
            }
        }
        catch (IllegalArgumentException e) {
            errors.add("Unsupported Type");
        }

        try{
            if(transaction.getStyle() != null) {
                Style style = Style.valueOf(transaction.getStyle().toUpperCase());
            }
        }
        catch (IllegalArgumentException e) {
            errors.add(STYLE_NOT_VALID_ERROR);
        }

        try{
            if(transaction.getCustomer() != null) {
                Customer.valueOf(transaction.getCustomer().toUpperCase());
            }
        }
        catch (IllegalArgumentException e) {
            errors.add(COUNTERPARTY_UNSUPPORTED_ERROR);
        }
        
        if(transaction.getValueDate() != null && transaction.getValueDate().before(transaction.getTradeDate())){
            errors.add(BAD_VALUE_DATE_ERROR);
        }

        //ccyPair, payCcy, premiumCcy, premiumType
        if(transaction.getCcyPair() != null && !validateCurrency(transaction.getCcyPair())) {
            errors.add("ccyPair: " + BAD_CURRENCY_CODE_ERROR);
        }

        if(transaction.getPayCcy() != null && !validateCurrency(transaction.getPayCcy())) {
            errors.add("payCcy: " + BAD_CURRENCY_CODE_ERROR);
        }

        if(transaction.getPremiumCcy() != null && !validateCurrency(transaction.getPremiumCcy())) {
            errors.add("premiumCcy: " + BAD_CURRENCY_CODE_ERROR);
        }

        if(transaction.getPremiumType() != null) {
            int ptLength = transaction.getPremiumType().length();
            if(!validateCurrency(transaction.getPremiumType().substring(ptLength - 3, ptLength))) {
                errors.add("premiumType: " + BAD_CURRENCY_CODE_ERROR);
            }
        }

        Calendar calendar = Calendar.getInstance();

        if(transaction.getValueDate() != null){
            calendar.setTime(transaction.getValueDate());
            if(calendar.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY || calendar.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY){
                errors.add(VALUEDATE_NON_WORKING_DAY_ERROR);
            }
        }

        Date excerciseStartDate = transaction.getExcerciseStartDate();
        Date tradeDate = transaction.getTradeDate();
        Date expiryDate = transaction.getExpiryDate();
        Date deliveryDate = transaction.getDeliveryDate();
        Date premiumDate = transaction.getPremiumDate();
        if(excerciseStartDate != null) {
            if(tradeDate != null && !excerciseStartDate.after(tradeDate) || expiryDate != null && !excerciseStartDate.before(expiryDate)){
                errors.add(EXCERCISE_START_DATE_ERROR);
            }
        }

        //Expiry date should be before delivery date
        if(expiryDate != null && deliveryDate != null && !expiryDate.before(deliveryDate)){
            errors.add(EXPIRY_DATE_ERROR);
        }

        //Premium date should be before delivery date
        if(premiumDate != null && deliveryDate != null && !premiumDate.before(deliveryDate)){
            errors.add(PREMIUM_DATE_ERROR);
        }

        return errors;
    }

    private static final String UNKNOWN_CURRENCY = "Unknown Currency";

    private boolean validateCurrency(String currency) {
        if(currency == null) {
            return false;
        }

        boolean isValid = false;

        try {
            if (currency.length() == 3 && !UNKNOWN_CURRENCY.equalsIgnoreCase(Currency.getInstance(currency).getDisplayName())) {
                isValid = true;
            } else if (currency.length() == 6) {
                String name1 = Currency.getInstance(currency.substring(0, 3)).getDisplayName();
                String name2 = Currency.getInstance(currency.substring(3, 6)).getDisplayName();
                if (!UNKNOWN_CURRENCY.equalsIgnoreCase(name1) && !UNKNOWN_CURRENCY.equalsIgnoreCase(name2)) {
                    isValid = true;
                }
            }
        }
        catch (IllegalArgumentException e) {
            isValid = false;
        }

        return isValid;
    }
    
}
