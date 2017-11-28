package com.bellidov.trade.rest;

import com.bellidov.trade.business.model.Transaction;
import com.bellidov.trade.business.model.response.GetValidatorResponse;
import com.bellidov.trade.business.service.ValidatorService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(ValidatorController.version)
@Api(tags = {"Validator API"})
public class ValidatorController {
    public static final String version = "/v1";

    @Autowired
    private ValidatorService service;

    @RequestMapping(value = "/validator", method = RequestMethod.POST)
    public ResponseEntity<GetValidatorResponse> getValidationResults(@RequestBody final Transaction ... transactions){
        return null;
    }
}
