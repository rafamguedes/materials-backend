package com.materials.api.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.jdbc.datasource.DataSourceUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.sql.DataSource;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/health")
@Tag(name = "Health Check", description = "Endpoint to check the health of the application")
public class HealthCheckController implements HealthIndicator {

  private final DataSource dataSource;

  @GetMapping
  @Operation(description = "Checks the health of the application, including database connectivity and system status.")
  @ApiResponses(value = {
    @ApiResponse(responseCode = "200", description = "Status OK, application is running smoothly"),
    @ApiResponse(responseCode = "503", description = "Application is down or database is unavailable", content = @Content(schema = @Schema())) })
  public Health check() {
    try {
      boolean databaseUp = checkDatabaseConnection();

      if (databaseUp) {
        return Health.up()
            .withDetail("database", "available")
            .withDetail(
                "databaseProductName",
                dataSource.getConnection().getMetaData().getDatabaseProductName())
            .withDetail(
                "databaseProductVersion",
                dataSource.getConnection().getMetaData().getDatabaseProductVersion())
            .withDetail(
                "activeConnections",
                DataSourceUtils.getConnection(dataSource).getMetaData().getMaxConnections())
            .withDetail("activeThreads", Thread.activeCount())
            .withDetail(
                "uptime",
                System.currentTimeMillis()
                    - dataSource.getConnection().getMetaData().getDatabaseMajorVersion())
            .withDetail("version", "1.0.0")
            .withDetail("status", "UP")
            .withDetail("message", "Application is running smoothly")
            .withDetail("timestamp", System.currentTimeMillis())
            .build();
      } else {
        return Health.down().withDetail("database", "unavailable").build();
      }
    } catch (Exception e) {
      return Health.down(e).build();
    }
  }

  private boolean checkDatabaseConnection() {
    try {
      var connection = DataSourceUtils.getConnection(dataSource);
      connection.close();
      return true;
    } catch (Exception e) {
      return false;
    }
  }

  @Override
  public Health health() {
    return check();
  }
}
