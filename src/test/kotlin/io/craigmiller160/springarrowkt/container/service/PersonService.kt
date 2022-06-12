package io.craigmiller160.springarrowkt.container.service

import io.craigmiller160.springarrowkt.container.PersonRepository
import org.springframework.stereotype.Service

@Service class PersonService(private val personRepository: PersonRepository)
