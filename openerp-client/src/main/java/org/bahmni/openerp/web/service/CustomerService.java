package org.bahmni.openerp.web.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.bahmni.crater.web.client.CraterClient;
import org.bahmni.crater.web.request.CraterRequest;
import org.bahmni.openerp.web.OpenERPException;
import org.bahmni.openerp.web.client.OpenERPClient;
import org.bahmni.openerp.web.request.OpenERPRequest;
import org.bahmni.openerp.web.request.mapper.OpenERPCustomerParameterMapper;
import org.bahmni.openerp.web.service.domain.Customer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Vector;

@Service
public class CustomerService {
    private static final Logger logger = LogManager.getLogger(CustomerService.class);
    private OpenERPClient openERPClient;
    private CraterClient craterClient;
    private OpenERPCustomerParameterMapper parameterMapper;

    @Autowired
    public CustomerService(OpenERPClient openERPClient, CraterClient craterClient) {
        this.openERPClient = openERPClient;
        this.craterClient = craterClient;
        this.parameterMapper = new OpenERPCustomerParameterMapper();
    }

    CustomerService(OpenERPClient openERPClient,OpenERPCustomerParameterMapper parameterMapper) {
        this.openERPClient = openERPClient;
        this.parameterMapper = parameterMapper;
    }

    public void create(Customer customer) {
        if (noCustomersFound(findCustomersWithPatientReference(customer.getRef()))) {
            OpenERPRequest request = parameterMapper.mapCustomerParams(customer, "create");
            openERPClient.execute(request);
            // TODO move it to different place?
            try {
                int customerId = craterClient.createCraterCustomer(CraterRequest.getCreateCustomerRequest(customer));
                // todo save customer id
            } catch (JsonProcessingException e) {
                // ignore it for now
                logger.error("Could not create crater customer", e);
            } catch (RuntimeException e) {
                logger.error("Error during post to crater", e);
            }

        } else
            throw new OpenERPException(String.format("Customer with id, name already exists: %s, %s ", customer.getRef(), customer.getName()));
    }

    public void deleteCustomerWithPatientReference(String patientId) {
        Object[] customerIds = findCustomersWithPatientReference(patientId);
        Vector params = new Vector();
        params.addElement(customerIds[0]);
        openERPClient.delete("res.partner", params);
    }

    public Object[] findCustomersWithPatientReference(String patientId) {
        Object args[] = {"ref", "=", patientId};
        Vector params = new Vector();
        params.addElement(args);
        return (Object[]) openERPClient.search("res.partner", params);
    }

    private boolean noCustomersFound(Object[] customers) {
        return customers.length == 0;
    }
}
