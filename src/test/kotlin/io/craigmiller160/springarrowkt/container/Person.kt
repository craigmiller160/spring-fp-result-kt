package io.craigmiller160.springarrowkt.container

import java.util.UUID
import javax.persistence.Entity
import javax.persistence.Id
import javax.persistence.Table

@Entity
@Table(name = "person")
class Person(val name: String, val age: Int) {
  @Id val id = UUID.randomUUID()

  var phone: String? = null
}
