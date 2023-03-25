package io.github.craigmiller160.fpresultkt.caching

import io.github.craigmiller160.fpresultkt.converter.ResultConverterHandler
import org.springframework.cache.Cache
import org.springframework.cache.CacheManager

class ResultCacheManager(
    private val resultConverterHandler: ResultConverterHandler,
    private val cacheManager: CacheManager
) : CacheManager by cacheManager {
  override fun getCache(name: String): Cache? =
      cacheManager.getCache(name)?.let { cache -> ResultCache(resultConverterHandler, cache) }
}
