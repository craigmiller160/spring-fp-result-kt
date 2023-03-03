package io.github.craigmiller160.fpresultkt.converter.either

import arrow.core.Either
import io.github.craigmiller160.fpresultkt.converter.CommonResult
import io.github.craigmiller160.fpresultkt.converter.CommonResultFailure
import io.github.craigmiller160.fpresultkt.converter.CommonResultSuccess
import io.github.craigmiller160.fpresultkt.converter.EITHER_CLASS
import io.github.craigmiller160.fpresultkt.converter.ResultConverter
import io.github.craigmiller160.fpresultkt.converter.ResultConverterStrategy
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass
import org.springframework.stereotype.Component

@Component("eitherResultConverter")
@ConditionalOnClass(name = [EITHER_CLASS])
class EitherResultConverter : ResultConverter {
  override fun convert(value: Any): CommonResult =
      when (val either = value as Either<*, *>) {
        is Either.Right -> CommonResultSuccess(either.value, ResultConverterStrategy.EITHER)
        is Either.Left -> CommonResultFailure(either.value, ResultConverterStrategy.EITHER)
      }
}
