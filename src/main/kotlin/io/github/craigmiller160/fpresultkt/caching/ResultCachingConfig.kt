package io.github.craigmiller160.fpresultkt.caching

import org.springframework.beans.factory.config.BeanDefinition
import org.springframework.cache.interceptor.CacheInterceptor
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Primary
import org.springframework.context.annotation.Role
import org.springframework.core.annotation.Order

@Order(999)
@Configuration(proxyBeanMethods = false)
@Role(BeanDefinition.ROLE_INFRASTRUCTURE)
class ResultCachingConfig {
  // TODO delete this
  @Bean
  @Primary
  @Role(BeanDefinition.ROLE_INFRASTRUCTURE)
  fun resultCacheInterceptor(defaultCacheInterceptor: CacheInterceptor): CacheInterceptor {
    return ResultCacheInterceptor(defaultCacheInterceptor)
  }
}
