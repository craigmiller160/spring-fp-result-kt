package io.github.craigmiller160.fpresultkt.container.domain.ds2.repositories

import io.github.craigmiller160.fpresultkt.container.domain.ds2.entities.Company
import java.util.UUID
import org.springframework.data.jpa.repository.JpaRepository

interface CompanyRepository : JpaRepository<Company, UUID>
