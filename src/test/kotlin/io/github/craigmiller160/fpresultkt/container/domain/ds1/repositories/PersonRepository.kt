package io.github.craigmiller160.fpresultkt.container.domain.ds1.repositories

import io.github.craigmiller160.fpresultkt.container.domain.ds1.entities.Person
import java.util.UUID
import org.springframework.data.jpa.repository.JpaRepository

interface PersonRepository : JpaRepository<Person, UUID>
