package io.craigmiller160.springarrowkt.container.config

import com.zaxxer.hikari.HikariConfig
import com.zaxxer.hikari.HikariDataSource
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.data.jpa.repository.config.EnableJpaRepositories
import org.springframework.orm.jpa.JpaTransactionManager
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter

@Configuration
@EnableJpaRepositories(
    basePackages = ["io.craigmiller160.springarrowkt.container.domain.ds1.repositories"],
    entityManagerFactoryRef = "dataSourceOneEntityManagerFactoryBean",
    transactionManagerRef = H2DataSourceOneConfig.TXN_MANAGER)
class H2DataSourceOneConfig {
  companion object {
    const val TXN_MANAGER = "dataSourceOneTransactionManager"
  }
  @Bean
  fun dataSourceOneConfig() =
      HikariConfig().apply {
        driverClassName = "org.h2.Driver"
        jdbcUrl = "jdbc:h2:mem:db;DB_CLOSE_DELAY=-1"
        username = "sa"
        password = "sa"
      }

  @Bean
  fun dataSourceOne(@Qualifier("dataSourceOneConfig") dataSourceOneConfig: HikariConfig) =
      HikariDataSource(dataSourceOneConfig)

  @Bean
  fun dataSourceOneEntityManagerFactoryBean(
      @Qualifier("dataSourceOne") dataSourceOne: HikariDataSource
  ) =
      LocalContainerEntityManagerFactoryBean().apply {
        dataSource = dataSourceOne
        setPackagesToScan("io.craigmiller160.springarrowkt.container.domain.ds1.entities")
        val adapter = HibernateJpaVendorAdapter()
        jpaVendorAdapter = adapter
        setJpaPropertyMap(mapOf("hibernate.hbm2ddl.auto" to "create"))
      }

  @Bean
  fun dataSourceOneTransactionManager(
      @Qualifier("dataSourceOneEntityManagerFactoryBean")
      dataSourceOneEntityManagerFactoryBean: LocalContainerEntityManagerFactoryBean
  ) =
      JpaTransactionManager().apply {
        entityManagerFactory = dataSourceOneEntityManagerFactoryBean.`object`
      }
}
