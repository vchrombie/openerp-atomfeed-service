package org.bahmni.crater.web.request;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.bahmni.openerp.web.service.domain.Customer;
import org.junit.Assert;
import org.junit.Test;

public class CraterRequestTest {

    @Test
    public void testJson() throws JsonProcessingException {
        String expectedJson = "{\"name\":\"testUser\"}";
        String json = CraterRequest.getCreateCustomerRequest(new Customer("testUser", "", ""));
        Assert.assertEquals(expectedJson, json);
    }

}
