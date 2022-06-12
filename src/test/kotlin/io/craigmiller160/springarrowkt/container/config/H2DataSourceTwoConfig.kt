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
    basePackages = ["io.craigmiller160.springarrowkt.container.domain.ds2.repositories"],
    entityManagerFactoryRef = "dataSourceTwoEntityManagerFactoryBean",
    transactionManagerRef = "dataSourceTwoTransactionManager")
class H2DataSourceTwoConfig {
  @Bean
  @Primary
  fun dataSourceTwoConfig() =
      HikariConfig().apply {
        driverClassName = "org.h2.Driver"
        jdbcUrl = "jdbc:h2:mem:db;DB_CLOSE_DELAY=-1"
        username = "sa"
        password = "sa"
      }

  @Bean
  @Primary
  fun dataSourceTwo(dataSourceTwoConfig: HikariConfig) = HikariDataSource(dataSourceTwoConfig)

  @Bean
  @Primary
  fun dataSourceTwoEntityManagerFactoryBean(dataSourceTwo: HikariDataSource) =
      LocalContainerEntityManagerFactoryBean().apply {
        dataSource = dataSourceTwo
        setPackagesToScan("io.craigmiller160.springarrowkt.container.domain.ds2.entities")
        val adapter = HibernateJpaVendorAdapter()
        jpaVendorAdapter = adapter
        setJpaPropertyMap(mapOf("hibernate.hbm2ddl.auto" to "create"))
      }

  @Bean
  fun dataSourceTwoTransactionManager(
      dataSourceOneEntityManagerFactoryBean: LocalContainerEntityManagerFactoryBean
  ) =
      JpaTransactionManager().apply {
        entityManagerFactory = dataSourceOneEntityManagerFactoryBean.`object`
      }
}
