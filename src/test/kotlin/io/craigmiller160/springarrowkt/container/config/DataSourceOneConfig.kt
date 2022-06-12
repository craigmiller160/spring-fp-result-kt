package io.craigmiller160.springarrowkt.container.config

import com.zaxxer.hikari.HikariConfig
import com.zaxxer.hikari.HikariDataSource
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Primary
import org.springframework.data.jpa.repository.config.EnableJpaRepositories
import org.springframework.orm.jpa.JpaTransactionManager
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter

@Configuration
@EnableJpaRepositories(
    basePackages = ["io.craigmiller160.springarrowkt.container.domain.ds1.repositories"],
    entityManagerFactoryRef = "dataSourceOneEntityManagerFactoryBean",
    transactionManagerRef = "dataSourceOneTransactionManager")
class DataSourceOneConfig {
  @Bean
  @Primary
  fun dataSourceOneConfig() =
      HikariConfig().apply {
        driverClassName = "org.h2.Driver"
        jdbcUrl = "jdbc:h2:mem:db;DB_CLOSE_DELAY=-1"
        username = "sa"
        password = "sa"
      }

  @Bean
  @Primary
  fun dataSourceOne(dataSourceOneConfig: HikariConfig) = HikariDataSource(dataSourceOneConfig)

  @Bean
  @Primary
  fun dataSourceOneEntityManagerFactoryBean(dataSourceOne: HikariDataSource) =
      LocalContainerEntityManagerFactoryBean().apply {
        dataSource = dataSourceOne
        setPackagesToScan("io.craigmiller160.springarrowkt.container.domain.ds1.entities")
        val adapter = HibernateJpaVendorAdapter()
        jpaVendorAdapter = adapter
      }

  @Bean
  fun dataSourceOneTransactionManager(
      dataSourceOneEntityManagerFactoryBean: LocalContainerEntityManagerFactoryBean
  ) =
      JpaTransactionManager().apply {
        entityManagerFactory = dataSourceOneEntityManagerFactoryBean.`object`
      }
}
