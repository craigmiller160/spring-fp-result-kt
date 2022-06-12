package io.craigmiller160.springarrowkt

import org.springframework.boot.autoconfigure.condition.ConditionalOnClass
import org.springframework.context.annotation.ComponentScan
import org.springframework.context.annotation.Configuration
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerAdapter

@Configuration
@ConditionalOnClass(RequestMappingHandlerAdapter::class)
@ComponentScan(basePackages = ["io.craigmiller160.springarrowkt.either.controller"])
class SpringArrowKtAutoConfiguration
