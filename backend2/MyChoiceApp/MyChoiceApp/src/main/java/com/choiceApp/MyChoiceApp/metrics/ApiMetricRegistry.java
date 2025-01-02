package com.choiceApp.MyChoiceApp.metrics;

import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.Timer;
import io.micrometer.prometheusmetrics.PrometheusConfig;
import io.micrometer.prometheusmetrics.PrometheusMeterRegistry;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class ApiMetricRegistry extends OncePerRequestFilter {

    private MeterRegistry registry = new PrometheusMeterRegistry(PrometheusConfig.DEFAULT);

    public ApiMetricRegistry(MeterRegistry meterRegistry) {
        this.registry = meterRegistry;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        String path = request.getRequestURI();

        // Count unique requests
        registry.counter("api_requests_total", "path", path, "method", request.getMethod()).increment();

        // Latency
        Timer.Sample sample = Timer.start(registry);
        try {
            filterChain.doFilter(request, response);
        } finally {

            sample.stop(registry.timer("http_request_duration_seconds", "path", path, "method", request.getMethod()));

            // Count status codes
            registry.counter("http_response_status", "status", String.valueOf(response.getStatus())).increment();
        }

    }

}

