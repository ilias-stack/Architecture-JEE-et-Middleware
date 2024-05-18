package com.example.tp6.config;

import com.example.tp6.web.CustomerSoapController;
import jakarta.xml.ws.Endpoint;
import lombok.AllArgsConstructor;
import org.apache.cxf.Bus;
import org.apache.cxf.jaxws.EndpointImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@AllArgsConstructor
public class CxfConfig {
    private Bus bus;
    private CustomerSoapController customerSoapController;

    @Bean
    public EndpointImpl endpoint(){
        EndpointImpl endpoint = new EndpointImpl(bus,customerSoapController);
        endpoint.publish("/CustomerService");
        return endpoint;
    }
}
