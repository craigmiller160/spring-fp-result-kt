package io.craigmiller160.springarrowkt.container

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/either")
class EitherController {
  @GetMapping("/success")
  fun success() {
    TODO()
  }

  @GetMapping("/failure")
  fun failure() {
    TODO()
  }
}
