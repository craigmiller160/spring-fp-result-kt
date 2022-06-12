package io.craigmiller160.springarrowkt.container.service

import io.craigmiller160.springarrowkt.container.config.H2DataSourceTwoConfig
import io.craigmiller160.springarrowkt.container.domain.ds2.entities.Company
import io.craigmiller160.springarrowkt.container.domain.ds2.repositories.CompanyRepository
import java.util.UUID
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class CompanyTransactionService(
    private val companyRepository: CompanyRepository,
    private val nestedService: NestedCompanyTransactionService
) {
  @Transactional(transactionManager = H2DataSourceTwoConfig.TXN_MANAGER)
  fun save(company: Company): Company = companyRepository.save(company)

  @Transactional(transactionManager = H2DataSourceTwoConfig.TXN_MANAGER)
  fun nestedSave(initCompany: Company): Pair<Company, Company?> {
    val company = companyRepository.save(initCompany)
    val otherCompany =
        runCatching {
              nestedService.anotherSave(
                  initCompany.copy(id = UUID.randomUUID(), name = "${initCompany.name}-2"))
            }
            .getOrNull()
    return Pair(company, otherCompany)
  }
}
