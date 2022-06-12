package io.craigmiller160.springarrowkt.container.config

import com.zaxxer.hikari.HikariConfig
import com.zaxxer.hikari.HikariDataSource
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter

@Configuration
class DataSourceOneConfig {
  @Bean
  fun dataSourceOneConfig() =
      HikariConfig().apply {
        jdbcUrl = "jdbc:h2:mem:db;DB_CLOSE_DELAY=-1"
        username = "sa"
        password = "sa"
      }

  @Bean fun dataSourceOne(dataSourceOneConfig: HikariConfig) = HikariDataSource(dataSourceOneConfig)

  @Bean
  fun dataSourceOneEntityManagerFactoryBean(dataSourceOne: HikariDataSource) =
      LocalContainerEntityManagerFactoryBean().apply {
        dataSource = dataSourceOne
        val adapter = HibernateJpaVendorAdapter()
        jpaVendorAdapter = adapter
      }
}
