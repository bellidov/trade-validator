package com.bellidov.trade;

import com.bellidov.trade.business.model.GetValidatorResponse;
import com.bellidov.trade.business.model.Transaction;
import com.bellidov.trade.business.service.ValidatorService;
import com.bellidov.trade.rest.ValidatorController;
import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MockMvcBuilder;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.io.IOException;
import java.io.Serializable;

import static org.mockito.Mockito.verify;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@RunWith(SpringRunner.class)
@WebAppConfiguration
public class ValidatorControllerTest {

    @InjectMocks
    private ValidatorController validatorController;

    @Mock
    private ValidatorService validatorService;

    private MockMvc mockMvc;

    private String PATH = "/v1/validator";

    @Before
    public void setUp(){
        MockitoAnnotations.initMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(validatorController).build();
    }

    @Test
    public void validator_should_return_http_status_ok() throws Exception {
        GetValidatorResponse response = new GetValidatorResponse();
        response.setStatus(true);

        when(validatorService.getValidatorResponse(any(Transaction.class))).thenReturn(response);

        Transaction[] transactions = new Transaction[] {new Transaction()};

        mockMvc.perform(post(PATH).contentType(MediaType.APPLICATION_JSON).content(fromJavaToJson(transactions))).andExpect(status().isOk());

        verify(validatorService).getValidatorResponse(any(Transaction.class));
    }

    @Test
    public void validator_should_return_bad_request_response(){
        /*TODO: a really nice junit test should be implemented here */
    }

    public static String fromJavaToJson(Serializable object)
            throws IOException {
        ObjectMapper jsonMapper = new ObjectMapper();
        return jsonMapper.writeValueAsString(object);
    }
}
