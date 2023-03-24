package io.github.craigmiller160.fpresultkt

import org.springframework.boot.autoconfigure.condition.ConditionalOnClass
import org.springframework.cache.annotation.Cacheable
import org.springframework.context.annotation.ComponentScan
import org.springframework.context.annotation.Configuration

@Configuration
@ConditionalOnClass(Cacheable::class)
@ComponentScan(basePackages = ["io.github.craigmiller160.fpresultkt.caching"])
class SpringFpResultKtCachingAutoConfiguration {}
