package com.bellidov.trade.service.rest;

import com.bellidov.trade.service.business.model.Transaction;
import com.bellidov.trade.service.business.model.GetValidatorResponse;
import com.bellidov.trade.service.business.service.ValidatorService;
import io.swagger.annotations.Api;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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

    private static final Logger LOGGER = LoggerFactory.getLogger(ValidatorController.class);

    @Autowired
    private ValidatorService service;

    @RequestMapping(value = "/validator", method = RequestMethod.POST)
    public ResponseEntity<GetValidatorResponse> getValidationResults(@RequestBody final Transaction... transactions){
        LOGGER.debug("POST /validator request has been received.");
        ResponseEntity<GetValidatorResponse> response;

        try{
            GetValidatorResponse result = service.getValidatorResponse(transactions);

            if(result.getStatus()){
                response = new ResponseEntity<>(result, HttpStatus.OK);
            }
            else {
                response = new ResponseEntity<>(result, HttpStatus.BAD_REQUEST);
            }
        }
        catch (Exception e){
            LOGGER.error("VALIDATOR POST REQUEST: An unexpected error has occurred: ", e);
            response = new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }

        LOGGER.debug("POST /validator response has been sent.");
        return response;
    }
}
