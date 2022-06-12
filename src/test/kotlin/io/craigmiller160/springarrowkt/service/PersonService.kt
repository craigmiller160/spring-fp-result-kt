package io.craigmiller160.springarrowkt.service

import io.craigmiller160.springarrowkt.domain.PersonRepository
import org.springframework.stereotype.Service

@Service class PersonService(private val personRepository: PersonRepository)
