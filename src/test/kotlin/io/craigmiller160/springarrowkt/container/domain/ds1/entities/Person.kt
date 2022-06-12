package io.craigmiller160.springarrowkt.container.domain.ds1.entities

import java.util.UUID
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.Id
import javax.persistence.Table

@Entity
@Table(name = "person")
data class Person(
    @Id @Column(columnDefinition = "uuid") val id: UUID = UUID.randomUUID(),
    val name: String,
    val age: Int,
    val phone: String? = null
)
