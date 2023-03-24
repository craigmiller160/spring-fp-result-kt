package io.github.craigmiller160.fpresultkt.caching

import io.github.craigmiller160.fpresultkt.BaseTest
import io.github.craigmiller160.fpresultkt.container.service.CachingService
import io.kotest.assertions.arrow.core.shouldBeLeft
import io.kotest.assertions.arrow.core.shouldBeRight
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.matchers.comparables.shouldBeEqualComparingTo
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired

class EitherCachingTest : BaseTest() {
  @Autowired private lateinit var cachingService: CachingService

  @BeforeEach
  fun setup() {
    reset()
  }

  @AfterEach
  fun cleanup() {
    reset()
  }

  private fun reset() {
    cachingService.clearCache()
    cachingService.count = 0
  }
  @Test
  fun `spring - stores the value in the cache for a Right`() {
    cachingService.count = 1
    cachingService.getCountWithCacheEither(false).shouldBeRight(1)
    cachingService.count = 10
    cachingService.getCountWithCacheEither(false).shouldBeRight(1)
  }

  @Test
  fun `spring - does not store the value in the cache for a Left`() {
    cachingService.count = 1
    cachingService.getCountWithCacheEither(true).shouldBeLeft(RuntimeException("Dying"))
    cachingService.count = 10
    cachingService.getCountWithCacheEither(false).shouldBeRight(10)
  }

  @Test
  fun `spring - does not store the value in the cache for a Left, using a specific CacheManager`() {
    TODO()
  }

  @Test
  fun `spring - no either, stores value in the cache`() {
    cachingService.count = 1
    cachingService.getCountWithCache(false).shouldBeEqualComparingTo(1)
    cachingService.count = 10
    cachingService.getCountWithCache(false).shouldBeEqualComparingTo(1)
  }

  @Test
  fun `spring - no either, does not store value for exception`() {
    cachingService.count = 1
    shouldThrow<RuntimeException> { cachingService.getCountWithCache(true) }
    cachingService.count = 10
    cachingService.getCountWithCache(false).shouldBeEqualComparingTo(1)
  }
}
