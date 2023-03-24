package io.github.craigmiller160.fpresultkt.container.service

import org.springframework.cache.annotation.CacheEvict
import org.springframework.cache.annotation.Cacheable
import org.springframework.stereotype.Service

@Service
class CachingService {
  companion object {
    private const val CACHE = "COUNT_CACHE"
  }
  var count: Int = 0

  @Cacheable(cacheNames = [CACHE]) fun getCountWithCache(throwException: Boolean): Int = count

  @CacheEvict(cacheNames = [CACHE], allEntries = true) fun clearCache() {}
}
