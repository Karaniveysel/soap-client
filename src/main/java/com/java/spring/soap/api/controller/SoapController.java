package com.java.spring.soap.api.controller;

import com.java.spring.soap.api.clinet.SoapClinet;
import com.java.spring.soap.api.generated.CelsiusToFahrenheit;
import com.java.spring.soap.api.generated.CelsiusToFahrenheitResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/api/soap")
public class SoapController {

    @Autowired
    private SoapClinet clinet;

    @PostMapping("/getLoanStatusExchange")
    public ResponseEntity<CelsiusToFahrenheitResponse> invokeSoapClientToGetLoanStatusEx(@RequestBody CelsiusToFahrenheit request) {
        return clinet.getSoapStatusExchange(request);
    }

    @PostMapping("/getLoanStatus")
    public CelsiusToFahrenheitResponse invokeSoapClientToGetLoanStatus(@RequestBody CelsiusToFahrenheit request) {
        return clinet.getSoapStatus(request);
    }

}
