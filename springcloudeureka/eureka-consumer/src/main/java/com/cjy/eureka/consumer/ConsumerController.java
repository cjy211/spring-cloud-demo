package com.cjy.eureka.consumer;

import com.cjy.eureka.provider.HelloRemote;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.cloud.client.ServiceInstance;

import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.cloud.client.serviceregistry.Registration;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class ConsumerController {

//    @Autowired
//    HelloRemote helloRemote;

    @Autowired
    private Registration registration;       // 服务注册
    @Autowired
    private DiscoveryClient discoveryClient; // 服务发现客户端
    @RequestMapping("/provider")
    public String provider() {
        ServiceInstance instance = serviceInstance();
       System.out.print("provider service, host = " + instance.getHost()
                + ", service_id = " + instance.getServiceId());
        return "Hello,Provider!";
    }
    /**
     * 获取当前服务的服务实例
     *
     * @return ServiceInstance
     */
    public ServiceInstance serviceInstance() {
        List<ServiceInstance> list = discoveryClient.getInstances("spring-cloud-producer");
        if (list != null && list.size() > 0) {
            return list.get(0);
        }
        return null;
    }

    @Autowired
    HelloRemote helloRemote;
    @RequestMapping(value = "/hello", method = RequestMethod.GET)
    public String hello() {
        ServiceInstance instance = serviceInstance();
       return helloRemote.hello(instance.getServiceId());
    }

    @RequestMapping(value = "/ff", method = RequestMethod.GET)
    public String ff() {
        ServiceInstance instance = serviceInstance();
        return helloRemote.hello(instance.getServiceId());
    }

//    @RequestMapping("/hello/{name}")
//    public String index(@PathVariable("name") String name) {
//    return helloRemote.hello(name);
//
//    }

}