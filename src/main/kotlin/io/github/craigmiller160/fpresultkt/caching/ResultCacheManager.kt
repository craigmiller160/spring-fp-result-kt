package io.github.craigmiller160.fpresultkt.caching

import io.github.craigmiller160.fpresultkt.converter.ResultConverterHandler
import org.springframework.cache.CacheManager

class ResultCacheManager(
    private val resultConverterHandler: ResultConverterHandler,
    cacheManager: CacheManager
) : CacheManager by cacheManager
