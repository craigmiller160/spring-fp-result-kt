package io.github.craigmiller160.fpresultkt.container.domain.ds1.entities

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.Table
import java.util.UUID

@Entity
@Table(name = "person")
data class Person(
    @Id @Column(columnDefinition = "uuid") val id: UUID = UUID.randomUUID(),
    val name: String,
    val age: Int,
    val phone: String? = null
)
