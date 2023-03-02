package io.github.craigmiller160.fpresultkt.converter.either

import arrow.core.Either
import io.github.craigmiller160.fpresultkt.converter.CommonResult
import io.github.craigmiller160.fpresultkt.converter.CommonResultFailure
import io.github.craigmiller160.fpresultkt.converter.CommonResultSuccess
import io.github.craigmiller160.fpresultkt.converter.ResultConverter
import org.springframework.stereotype.Component

@Component
class EitherResultConverter : ResultConverter<Either<*, *>> {
  override fun convert(value: Either<*, *>): CommonResult =
      when (value) {
        is Either.Right -> CommonResultSuccess(value.value)
        is Either.Left -> CommonResultFailure(value.value)
      }
}
