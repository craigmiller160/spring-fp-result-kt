package io.craigmiller160.springarrowkt.container

import java.util.UUID
import javax.persistence.Entity
import javax.persistence.Id
import javax.persistence.Table

@Entity
@Table(name = "person")
data class Person(
    @Id val id: UUID = UUID.randomUUID(),
    val name: String,
    val age: Int,
    val phone: String? = null
)
