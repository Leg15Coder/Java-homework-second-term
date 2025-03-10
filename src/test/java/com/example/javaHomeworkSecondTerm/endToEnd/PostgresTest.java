package com.example.javaHomeworkSecondTerm.endToEnd;

import com.example.javaHomeworkSecondTerm.config.TestContainerConfig;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.utility.DockerImageName;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@ActiveProfiles("test")
public class PostgresTest extends TestContainerConfig {
  @LocalServerPort
  private int port;

  @Value("${spring.application.name}")
  private String applicationName;

  @Test
  void contextLoads() {
    assertEquals(8082, port);
    assertEquals("java-homework-second-term-test", applicationName);
  }

  private static final PostgreSQLContainer<?> postgresContainer = new PostgreSQLContainer<>(DockerImageName.parse("postgres:latest"))
      .withDatabaseName("testdb")
      .withUsername("test")
      .withPassword("testpassword")
      .withInitScript("init.sql");

  @Value("${spring.datasource.url}")
  private String datasourceUrl;

  @BeforeAll
  public static void setUp() {
    postgresContainer.start();
  }

  @Test
  void testPostgresConnection() {
    System.out.println("PostgreSQL запузен на: " +
        postgresContainer.getHost() + ":" + postgresContainer.getMappedPort(5432));

    String jdbcUrl = String.format("jdbc:postgresql://%s:%d/%s",
        postgresContainer.getHost(), postgresContainer.getMappedPort(5432), postgresContainer.getDatabaseName());

    System.out.printf("JDBC URL: %s%n", jdbcUrl);

    assertNotNull(postgresContainer);
    assertNotNull(datasourceUrl);
  }
}
