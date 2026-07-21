package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class HealthController {

    // Injected from an env var so we can see exactly which build/commit is
    // running in the cluster once ArgoCD deploys it — set via the Helm
    // chart's env section (see values.yaml -> APP_VERSION).
    @Value("${app.version:unknown}")
    private String appVersion;

    @GetMapping("/")
    public Map<String, String> home() {
        return Map.of(
                "message", "Hello from the CI/CD demo app",
                "version", appVersion
        );
    }

    @GetMapping("/api/status")
    public Map<String, String> status() {
        return Map.of("status", "ok", "version", appVersion);
    }
}
