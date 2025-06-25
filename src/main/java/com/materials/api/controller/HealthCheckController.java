package com.materials.api.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/health")
public class HealthCheckController {

  @GetMapping
  public String checkHealth() {
    return "Backend is running! All systems operational.";
  }
}