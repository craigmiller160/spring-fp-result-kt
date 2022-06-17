package io.github.craigmiller160.springarrowkt.container.config

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
    basePackages = ["io.github.craigmiller160.springarrowkt.container.domain.ds2.repositories"],
    entityManagerFactoryRef = "dataSourceTwoEntityManagerFactoryBean",
    transactionManagerRef = H2DataSourceTwoConfig.TXN_MANAGER)
class H2DataSourceTwoConfig {
  companion object {
    const val TXN_MANAGER = "dataSourceTwoTransactionManager"
  }
  @Bean
  fun dataSourceTwoConfig() =
      HikariConfig().apply {
        driverClassName = "org.h2.Driver"
        jdbcUrl = "jdbc:h2:mem:db;DB_CLOSE_DELAY=-1"
        username = "sa"
        password = "sa"
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
        setPackagesToScan("io.github.craigmiller160.springarrowkt.container.domain.ds2.entities")
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
