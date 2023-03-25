package io.github.craigmiller160.fpresultkt.caching

import java.lang.reflect.Method
import org.aopalliance.intercept.MethodInvocation
import org.springframework.aop.framework.AopProxyUtils
import org.springframework.cache.interceptor.CacheInterceptor
import org.springframework.cache.interceptor.CacheOperationInvoker
import org.springframework.cache.interceptor.CacheOperationSource

class ResultCacheInterceptor(private val defaultCacheInterceptor: CacheInterceptor) :
    CacheInterceptor() {
  // TODO delete this
  private var initialized: Boolean = false
  override fun invoke(invocation: MethodInvocation): Any? {
    val result = super.invoke(invocation)
    return result
  }

  override fun afterSingletonsInstantiated() {
    super.afterSingletonsInstantiated()
    initialized = true
  }

  override fun execute(
      invoker: CacheOperationInvoker,
      target: Any,
      method: Method,
      args: Array<Any>
  ): Any? {
    if (!initialized) {
      return invoker.invoke()
    }

    val targetClass = AopProxyUtils.ultimateTargetClass(target)
    cacheOperationSource?.getCacheOperations(method, targetClass)?.let { operations ->
      //      val context = CacheOperationContexts(operations, method, args, target, targetClass)
    }

    return super.execute(invoker, target, method, args)
  }

  private fun updateCache(invocation: MethodInvocation, result: Any?) {}

  override fun getCacheOperationSource(): CacheOperationSource? =
      defaultCacheInterceptor.cacheOperationSource
}
