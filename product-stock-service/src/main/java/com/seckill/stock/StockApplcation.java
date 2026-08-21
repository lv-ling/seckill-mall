package com.seckill.stock;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class StockApplcation {

    public static void main(String[] args) {
        SpringApplication.run(StockApplcation.class,args);
    }
}
