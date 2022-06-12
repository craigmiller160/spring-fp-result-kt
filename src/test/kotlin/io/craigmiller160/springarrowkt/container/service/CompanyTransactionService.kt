package io.craigmiller160.springarrowkt.container.service

import io.craigmiller160.springarrowkt.container.domain.ds2.entities.Company
import io.craigmiller160.springarrowkt.container.domain.ds2.repositories.CompanyRepository
import javax.transaction.Transactional
import org.springframework.stereotype.Service

@Service
class CompanyTransactionService(private val companyRepository: CompanyRepository) {
  @Transactional fun save(company: Company): Company = companyRepository.save(company)
}
