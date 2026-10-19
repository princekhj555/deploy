package com.vercel.deploys.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class VercelDeployController {

    private static final Logger log = LoggerFactory.getLogger(VercelDeployController.class);

    @GetMapping("/is-deployed")
    public String getDeploys() {
        log.info("Server is running at remote :  vercel");
        return "Server is running at remote :  vercel on port " + System.getenv("PORT");
    }
}
