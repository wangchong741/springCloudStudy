package com.springcloud.controller;

import com.springcloud.entities.CommenResult;
import com.springcloud.entities.Payment;
import com.springcloud.service.PaymentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

@RestController
@Slf4j
public class PaymentController {
    @Resource
    private PaymentService paymentService;

    @Value("${server.port}")
    private String serverPort;

    @Resource
    private DiscoveryClient discoveryClient;

    @PostMapping(value = "/payment/create")
    public CommenResult create(@RequestBody Payment payment) {
        int result = paymentService.create(payment);
        log.info("-------插入成功------状态码：{0}",result);
        if (result>0){
            return new CommenResult(200,"插入成功,serverPort:"+serverPort,result);
        }
        return new CommenResult(444,"插入失败",null);
    }

    /**
     * http://127.0.0.1:8001/Payment/get/1
     * @param id
     * @return
     */
    @GetMapping(value = "/payment/get/{id}")
    public CommenResult create(@PathVariable("id") Long id) {
        Payment payment = paymentService.getPaymentById(id);
        log.info("-------查询成功------payment:{}",payment);
        if (payment==null){
            return new CommenResult(444,"查询失败",null);
        }
        return new CommenResult(200,"查询成功,serverPort"+serverPort,payment);

    }

    @GetMapping(value = "/payment/discovery")
    public Object discovery(){
        List<String> services = discoveryClient.getServices();
        for (String element : services) {
            log.info("****element: " +element);
        }
        List<ServiceInstance> instances = discoveryClient.getInstances("CLOUD-PAYMENT-SERVICE");
        for (ServiceInstance instance:instances) {
            log.info(instance.getServiceId()+"\t"+instance.getHost()+"\t"+instance.getPort()+"\t"+instance.getUri());
        }
        return this.discoveryClient;
    }

}
