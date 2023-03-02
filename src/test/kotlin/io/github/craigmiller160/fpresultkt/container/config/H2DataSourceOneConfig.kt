package io.github.craigmiller160.fpresultkt.container.config

import com.zaxxer.hikari.HikariConfig
import com.zaxxer.hikari.HikariDataSource
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Primary
import org.springframework.data.jpa.repository.config.EnableJpaRepositories
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate
import org.springframework.jdbc.datasource.DataSourceTransactionManager
import org.springframework.orm.jpa.JpaTransactionManager
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter

@Configuration
@EnableJpaRepositories(
    basePackages = ["io.github.craigmiller160.fpresultkt.container.domain.ds1.repositories"],
    entityManagerFactoryRef = "dataSourceOneEntityManagerFactoryBean",
    transactionManagerRef = H2DataSourceOneConfig.TXN_MANAGER)
class H2DataSourceOneConfig {
  companion object {
    const val TXN_MANAGER = "dataSourceOneTransactionManager"
    const val JDBC_TXN_MANAGER = "dataSourceOneJdbcTransactionManager"
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
        setPackagesToScan("io.github.craigmiller160.springarrowkt.container.domain.ds1.entities")
        val adapter = HibernateJpaVendorAdapter()
        jpaVendorAdapter = adapter
        setJpaPropertyMap(
            mapOf(
                "hibernate.hbm2ddl.auto" to "create",
                "hibernate.dialect" to "org.hibernate.dialect.PostgreSQLDialect"))
      }

  @Bean
  @Primary
  fun dataSourceOneTransactionManager(
      @Qualifier("dataSourceOneEntityManagerFactoryBean")
      dataSourceOneEntityManagerFactoryBean: LocalContainerEntityManagerFactoryBean
  ) =
      JpaTransactionManager().apply {
        entityManagerFactory = dataSourceOneEntityManagerFactoryBean.`object`
        isNestedTransactionAllowed = true
      }

  @Bean
  fun dataSourceOneJdbcTransactionManager(
      @Qualifier("dataSourceOne") dataSourceOne: HikariDataSource
  ) = DataSourceTransactionManager().apply { dataSource = dataSourceOne }

  @Bean
  fun jdbcTemplate(@Qualifier("dataSourceOne") dataSourceOne: HikariDataSource) =
      NamedParameterJdbcTemplate(dataSourceOne)
}
