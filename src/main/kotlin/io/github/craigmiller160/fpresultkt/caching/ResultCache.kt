package io.github.craigmiller160.fpresultkt.caching

import io.github.craigmiller160.fpresultkt.converter.CommonResultFailure
import io.github.craigmiller160.fpresultkt.converter.ResultConverterHandler
import org.springframework.cache.Cache

class ResultCache(
    private val resultConverterHandler: ResultConverterHandler,
    private val cache: Cache
) : Cache by cache {
  override fun put(key: Any, value: Any?) {
    val commonResult = resultConverterHandler.convert(value)
    if (commonResult !is CommonResultFailure) {
      cache.put(key, value)
    }
  }
}
