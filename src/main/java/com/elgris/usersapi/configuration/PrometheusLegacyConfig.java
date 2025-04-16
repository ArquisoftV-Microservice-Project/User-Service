package com.elgris.usersapi.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.micrometer.spring.autoconfigure.export.prometheus.PrometheusMetricsExportAutoConfiguration;

@Configuration
public class PrometheusLegacyConfig {
    @Bean
    public PrometheusMetricsExportAutoConfiguration prometheusMetrics() {
        return new PrometheusMetricsExportAutoConfiguration();
    }
}
