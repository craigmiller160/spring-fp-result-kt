package io.craigmiller160.springarrowkt

import javax.persistence.EntityManager
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass
import org.springframework.context.annotation.ComponentScan
import org.springframework.context.annotation.Configuration

@Configuration
@ConditionalOnClass(EntityManager::class)
@ComponentScan(basePackages = ["io.craigmiller160.springarrowkt.controller"])
class SpringArrowKtTransactionAutoConfiguration