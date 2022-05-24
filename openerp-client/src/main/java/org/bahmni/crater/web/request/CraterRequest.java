package org.bahmni.crater.web.request;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.HashMap;
import java.util.Map;
import org.bahmni.openerp.web.service.domain.Customer;

public class CraterRequest {

    public static String getCreateCustomerRequest(Customer customer) throws JsonProcessingException {
        Map<String, String> map = new HashMap<>();
        map.put("name", customer.getName());
        ObjectMapper mapper = new ObjectMapper();
        return mapper.writeValueAsString(map);
    }
}
