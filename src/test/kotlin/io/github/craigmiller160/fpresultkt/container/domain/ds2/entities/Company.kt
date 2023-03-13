package io.github.craigmiller160.fpresultkt.container.domain.ds2.entities

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.Table
import java.util.UUID

@Entity
@Table(name = "company")
data class Company(
    @Id @Column(columnDefinition = "uuid") val id: UUID = UUID.randomUUID(),
    val name: String
)
