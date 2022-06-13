package io.craigmiller160.springarrowkt.container.service

import io.craigmiller160.springarrowkt.container.config.H2DataSourceTwoConfig
import io.craigmiller160.springarrowkt.container.domain.ds2.entities.Company
import io.craigmiller160.springarrowkt.container.domain.ds2.repositories.CompanyRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class CompanyTransactionService(private val companyRepository: CompanyRepository) {
  @Transactional(transactionManager = H2DataSourceTwoConfig.TXN_MANAGER)
  fun save(company: Company): Company = companyRepository.save(company)
}
