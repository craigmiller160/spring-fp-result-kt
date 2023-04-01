package io.github.craigmiller160.fpresultkt

import org.springframework.boot.autoconfigure.condition.ConditionalOnClass
import org.springframework.context.annotation.ComponentScan
import org.springframework.context.annotation.Configuration

@Configuration
@ConditionalOnClass(org.springframework.transaction.annotation.Transactional::class)
@ComponentScan(
    basePackages =
        [
            "io.github.craigmiller160.fpresultkt.transaction.support",
            "io.github.craigmiller160.fpresultkt.transaction.advice.spring"])
class SpringFpResultKtTransactionAutoConfiguration
