package io.craigmiller160.springarrowkt.container.domain.ds2.entities

import java.util.UUID
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.Id
import javax.persistence.Table

@Entity
@Table(name = "company")
data class Company(
    @Id @Column(columnDefinition = "uuid") val id: UUID = UUID.randomUUID(),
    val name: String
)
