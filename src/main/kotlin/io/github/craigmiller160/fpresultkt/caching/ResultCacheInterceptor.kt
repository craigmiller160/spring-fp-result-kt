package io.github.craigmiller160.fpresultkt.caching

import org.aopalliance.intercept.MethodInvocation
import org.springframework.cache.interceptor.CacheInterceptor
import org.springframework.cache.interceptor.CacheOperationSource

class ResultCacheInterceptor(private val defaultCacheInterceptor: CacheInterceptor) :
    CacheInterceptor() {
  override fun invoke(invocation: MethodInvocation): Any? {
    val result = super.invoke(invocation)
    TODO()
  }

  override fun getCacheOperationSource(): CacheOperationSource? =
      defaultCacheInterceptor.cacheOperationSource
}
