/*
 * Copyright 2025 HM Revenue & Customs
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package uk.gov.hmrc.nddsstub.allowList

import org.scalatest.matchers.should.Matchers
import org.scalatest.wordspec.AnyWordSpec
import play.api.http.Status
import play.api.libs.json.Json
import play.api.test.Helpers.*
import play.api.test.{FakeRequest, Helpers}

class RateLimitedAllowListControllerSpec extends AnyWordSpec with Matchers:

  private val controller = RateLimitedAllowListController(Helpers.stubControllerComponents())

  "GET" should:
    "return 500, when the identifier ends with status500" in:
      val fakeRequest =
        FakeRequest("POST", routes.RateLimitedAllowListController.verify("", "").url)
          .withBody(CheckRequest("status500"))

      val result = controller.verify("", "")(fakeRequest)

      status(result) shouldBe Status.INTERNAL_SERVER_ERROR

    "return 400, when the identifier ends with status400" in:
      val fakeRequest =
        FakeRequest("POST", routes.RateLimitedAllowListController.verify("", "").url)
          .withBody(CheckRequest("status400"))

      val result = controller.verify("", "")(fakeRequest)

      status(result) shouldBe Status.BAD_REQUEST

    "return 200 with a false, when the identifier ends `old`" in:
      val fakeRequest =
        FakeRequest("POST", routes.RateLimitedAllowListController.verify("", "").url)
          .withBody(CheckRequest("gold"))

      val result = controller.verify("", "")(fakeRequest)

      status(result)        shouldBe Status.OK
      contentAsJson(result) shouldBe Json.obj("included" -> false)

    "return 200 with a true, when the identifier ends does not end with old/status400/status500" in:
      val fakeRequest =
        FakeRequest("POST", routes.RateLimitedAllowListController.verify("", "").url)
          .withBody(CheckRequest("alksdjf238469"))

      val result = controller.verify("", "")(fakeRequest)

      status(result)        shouldBe Status.OK
      contentAsJson(result) shouldBe Json.obj("included" -> true)
