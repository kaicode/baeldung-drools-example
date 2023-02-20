package com.baeldung.drools.service;

import com.baeldung.drools.config.DroolsBeanFactory;
import com.baeldung.drools.model.Customer;
import com.baeldung.drools.model.Customer.CustomerType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.kie.api.io.Resource;
import org.kie.api.runtime.KieSession;
import org.kie.internal.io.ResourceFactory;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class DiscountExcelIntegrationTest {

    private KieSession kSession;

    @BeforeEach
    public void setup() {
        Resource resource = ResourceFactory.newClassPathResource("com/baeldung/drools/rules/Discount.xls", getClass());
        kSession = new DroolsBeanFactory().getKieSession(resource);
    }

    @Test
    public void giveIndvidualLongStanding_whenFireRule_thenCorrectDiscount() throws Exception {
        Customer customer = new Customer(CustomerType.INDIVIDUAL, 5);
        kSession.insert(customer);

        kSession.fireAllRules();

        assertEquals(customer.getDiscount(), 15);
    }

    @Test
    public void giveIndvidualRecent_whenFireRule_thenCorrectDiscount() throws Exception {

        Customer customer = new Customer(CustomerType.INDIVIDUAL, 1);
        kSession.insert(customer);

        kSession.fireAllRules();

        assertEquals(customer.getDiscount(), 5);
    }

    @Test
    public void giveBusinessAny_whenFireRule_thenCorrectDiscount() throws Exception {
        Customer customer = new Customer(CustomerType.BUSINESS, 0);
        kSession.insert(customer);

        kSession.fireAllRules();

        assertEquals(customer.getDiscount(), 20);
    }

}
