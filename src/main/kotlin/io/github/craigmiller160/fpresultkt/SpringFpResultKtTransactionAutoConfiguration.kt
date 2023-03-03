package io.github.craigmiller160.fpresultkt

import javax.persistence.EntityManager
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass
import org.springframework.context.annotation.ComponentScan
import org.springframework.context.annotation.Configuration

@Configuration
@ConditionalOnClass(EntityManager::class)
@ComponentScan(basePackages = ["io.github.craigmiller160.fpresultkt.transaction"])
class SpringFpResultKtTransactionAutoConfiguration
