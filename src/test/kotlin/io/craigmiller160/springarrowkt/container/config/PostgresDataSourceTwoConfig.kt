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
    basePackages = ["io.craigmiller160.springarrowkt.container.domain.ds2.repositories"],
    entityManagerFactoryRef = "dataSourceTwoEntityManagerFactoryBean",
    transactionManagerRef = PostgresDataSourceTwoConfig.TXN_MANAGER)
class PostgresDataSourceTwoConfig {
  companion object {
    const val TXN_MANAGER = "dataSourceTwoTransactionManager"
  }
  @Bean
  fun dataSourceTwoConfig() =
      HikariConfig().apply {
        driverClassName = "org.postgresql.Driver"
        jdbcUrl = "jdbc:postgresql://localhost:5432/springarrowtest"
        username = "postgres"
        password = "password"
      }

  @Bean
  fun dataSourceTwo(@Qualifier("dataSourceTwoConfig") dataSourceTwoConfig: HikariConfig) =
      HikariDataSource(dataSourceTwoConfig)

  @Bean
  fun dataSourceTwoEntityManagerFactoryBean(
      @Qualifier("dataSourceTwo") dataSourceTwo: HikariDataSource
  ) =
      LocalContainerEntityManagerFactoryBean().apply {
        dataSource = dataSourceTwo
        setPackagesToScan("io.craigmiller160.springarrowkt.container.domain.ds2.entities")
        val adapter = HibernateJpaVendorAdapter()
        jpaVendorAdapter = adapter
        setJpaPropertyMap(mapOf("hibernate.hbm2ddl.auto" to "create"))
      }

  @Bean
  fun dataSourceTwoTransactionManager(
      @Qualifier("dataSourceTwoEntityManagerFactoryBean")
      dataSourceTwoEntityManagerFactoryBean: LocalContainerEntityManagerFactoryBean
  ) =
      JpaTransactionManager().apply {
        entityManagerFactory = dataSourceTwoEntityManagerFactoryBean.`object`
      }
}
