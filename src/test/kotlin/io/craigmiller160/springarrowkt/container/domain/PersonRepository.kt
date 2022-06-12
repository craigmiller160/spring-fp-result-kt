package io.craigmiller160.springarrowkt.container.domain

import java.util.UUID
import org.springframework.data.jpa.repository.JpaRepository

interface PersonRepository : JpaRepository<Person, UUID>
