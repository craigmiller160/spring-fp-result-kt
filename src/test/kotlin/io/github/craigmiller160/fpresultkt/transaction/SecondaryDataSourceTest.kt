package io.github.craigmiller160.fpresultkt.transaction

import io.github.craigmiller160.fpresultkt.BaseTest
import io.github.craigmiller160.fpresultkt.container.domain.ds2.entities.Company
import io.github.craigmiller160.fpresultkt.container.domain.ds2.repositories.CompanyRepository
import io.github.craigmiller160.fpresultkt.container.service.CompanyTransactionService
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
