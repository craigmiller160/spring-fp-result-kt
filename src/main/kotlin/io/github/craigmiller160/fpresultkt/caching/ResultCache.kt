package io.github.craigmiller160.fpresultkt.caching

import io.github.craigmiller160.fpresultkt.converter.ResultConverterHandler
import org.springframework.cache.Cache

class ResultCache(private val resultConverterHandler: ResultConverterHandler, cache: Cache) :
    Cache by cache {
  override fun put(key: Any, value: Any?) {
    TODO("Not yet implemented")
  }
}
