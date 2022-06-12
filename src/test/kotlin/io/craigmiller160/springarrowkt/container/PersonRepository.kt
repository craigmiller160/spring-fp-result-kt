package io.craigmiller160.springarrowkt.container

import java.util.UUID
import org.springframework.data.jpa.repository.JpaRepository

interface PersonRepository : JpaRepository<Person, UUID>
