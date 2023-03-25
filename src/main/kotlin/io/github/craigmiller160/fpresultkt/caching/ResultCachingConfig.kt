package io.github.craigmiller160.fpresultkt.caching

import org.springframework.beans.factory.config.BeanDefinition
import org.springframework.cache.interceptor.CacheInterceptor
import org.springframework.context.annotation.Role

// @Order(999)
// @Configuration(proxyBeanMethods = false)
@Role(BeanDefinition.ROLE_INFRASTRUCTURE)
class ResultCachingConfig {
  // TODO delete this
  //  @Bean
  //  @Primary
  @Role(BeanDefinition.ROLE_INFRASTRUCTURE)
  fun resultCacheInterceptor(defaultCacheInterceptor: CacheInterceptor): CacheInterceptor {
    return ResultCacheInterceptor(defaultCacheInterceptor)
  }
}
