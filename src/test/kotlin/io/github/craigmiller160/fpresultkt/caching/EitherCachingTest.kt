package io.github.craigmiller160.fpresultkt.caching

import io.github.craigmiller160.fpresultkt.BaseTest
import io.github.craigmiller160.fpresultkt.container.service.CachingService
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions.assertEquals
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
  fun `stores the value in the cache for a Right`() {
    cachingService.count = 1
    assertEquals(1, cachingService.getCountWithCache(false))
    cachingService.count = 10
    assertEquals(1, cachingService.getCountWithCache(false))
  }

  @Test
  fun `does not store the value in the cache for a Left`() {
    cachingService.count = 1
    assertEquals(1, cachingService.getCountWithCache(true))
    cachingService.count = 10
    assertEquals(10, cachingService.getCountWithCache(false))
  }
}
