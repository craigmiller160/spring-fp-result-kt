package io.craigmiller160.springarrowkt.transaction

import io.craigmiller160.springarrowkt.container.TestApplication
import io.craigmiller160.springarrowkt.container.domain.ds2.entities.Company
import io.craigmiller160.springarrowkt.container.domain.ds2.repositories.CompanyRepository
import io.craigmiller160.springarrowkt.container.service.CompanyTransactionService
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.junit.jupiter.SpringExtension

@SpringBootTest(classes = [TestApplication::class])
@ExtendWith(SpringExtension::class)
class SecondaryDataSourceTest {
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

  @Test
  fun `default spring nested transaction partial rollback behavior`() {
    val company = Company(name = "Special")
    val (company1, company2) = companyService.nestedSave(company)
    assertThat(company1).isEqualTo(company)
    assertThat(company2).isNull()
    val companies = companyRepository.findAll()
    assertThat(companies).hasSize(1).contains(company)
  }
}
