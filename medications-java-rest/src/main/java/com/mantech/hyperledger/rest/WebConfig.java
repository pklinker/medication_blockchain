package com.mantech.hyperledger.rest;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableWebMvc
@ComponentScan(basePackages = "com.mantech.hyperledger.rest")
public class WebConfig{
    //
}