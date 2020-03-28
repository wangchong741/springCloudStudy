package com.springcloud.controller;

import com.springcloud.entities.CommenResult;
import com.springcloud.entities.Payment;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;

@RestController
@Slf4j
public class OrderController {
    public static final String url="http://CLOUD-PAYMENT-SERVICE";

    @Resource
    private RestTemplate restTemplate;

    @GetMapping("/consumer/payment/create")
    public CommenResult<Payment> create(Payment payment){
        return restTemplate.postForObject(url+"/payment/create",payment,CommenResult.class);
    }

    @GetMapping("/consumer/payment/get/{id}")
    public CommenResult<Payment> getPayment(@PathVariable("id") Long id){
        return restTemplate.getForObject(url+"/payment/get/"+id,CommenResult.class);
    }

}
