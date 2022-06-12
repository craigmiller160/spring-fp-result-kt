package io.craigmiller160.springarrowkt.container.domain.ds2.entities

import java.util.UUID
import javax.persistence.Column
import javax.persistence.Id

data class Company(@Id @Column(columnDefinition = "uuid") val id: UUID, val name: String)
