package io.github.craigmiller160.springarrowkt.transaction

import io.craigmiller160.springarrowkt.BaseTest
import io.craigmiller160.springarrowkt.container.domain.ds2.entities.Company
import io.craigmiller160.springarrowkt.container.domain.ds2.repositories.CompanyRepository
import io.craigmiller160.springarrowkt.container.service.CompanyTransactionService
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired

class SecondaryDataSourceTest : BaseTest() {
  @Autowired private lateinit var companyService: CompanyTransactionService
  @Autowired private lateinit var companyRepository: CompanyRepository

  @BeforeEach
  fun setup() {
    companyRepository.deleteAll()
  }

  @AfterEach
  fun cleanup() {
    companyRepository.deleteAll()
  }

  @Test
  fun `verifies that second data source is working`() {
    val company = Company(name = "My Company")
    val result = companyService.save(company)
    assertThat(result).isEqualTo(company)
    assertThat(companyRepository.findById(company.id)).isPresent.get().isEqualTo(company)
  }
}
