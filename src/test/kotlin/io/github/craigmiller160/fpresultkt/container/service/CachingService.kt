package io.github.craigmiller160.fpresultkt.container.service

import arrow.core.Either
import org.springframework.cache.annotation.CacheEvict
import org.springframework.cache.annotation.Cacheable
import org.springframework.stereotype.Service

@Service
class CachingService {
  companion object {
    private const val CACHE = "COUNT_CACHE"
  }
  var count: Int = 0

  @Cacheable(cacheNames = [CACHE])
  fun getCountWithCacheEither(withException: Boolean): Either<Throwable, Int> {
    if (withException) {
      return Either.Left(RuntimeException("Dying"))
    }
    return Either.Right(count)
  }

  @Cacheable(cacheNames = [CACHE], key = "#key")
  fun getCountWithCacheAndKeyEither(key: String, withException: Boolean): Either<Throwable, Int> {
    if (withException) {
      return Either.Left(RuntimeException("Dying"))
    }
    return Either.Right(count)
  }

  @Cacheable(cacheNames = [CACHE])
  fun getCountWithCache(withException: Boolean): Int {
    if (withException) {
      throw RuntimeException("Dying")
    }
    return count
  }

  @CacheEvict(cacheNames = [CACHE], allEntries = true) fun clearCache() {}
}
