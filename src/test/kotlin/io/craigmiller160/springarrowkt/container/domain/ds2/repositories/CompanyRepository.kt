package io.craigmiller160.springarrowkt.container.domain.ds2.repositories

import io.craigmiller160.springarrowkt.container.domain.ds2.entities.Company
import java.util.UUID
import org.springframework.data.jpa.repository.JpaRepository

interface CompanyRepository : JpaRepository<Company, UUID>