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

package uk.gov.hmrc.nddsstub.controllers

import org.scalatest.matchers.should.Matchers
import org.scalatest.wordspec.AnyWordSpec
import play.api.http.Status
import play.api.libs.json.Json
import play.api.test.Helpers.*
import play.api.test.{FakeRequest, Helpers}

class BarsStubControllerSpec
  extends AnyWordSpec
     with Matchers:

  private val fakeRequest = FakeRequest("GET", "/")
  private val controller  = new BarsStubController(Helpers.stubControllerComponents())

  "GET /metadata" should:
    "return 200" in:
      val result = controller.metadata()(fakeRequest)
      status(result) shouldBe Status.OK
      contentAsJson(result) shouldBe Json.parse(
        """{
          |  "bankName":"bankName",
          |  "ddiVoucherFlag":"N",
          |  "address":{
          |    "lines":["1","Place"],
          |    "town":"town",
          |    "country":{
          |      "name":"country"
          |    },
          |    "postCode":"FF1 1FF"
          |  }
          |}""".stripMargin)

  "GET /verify" should:
    "return 200" in:
      val result = controller.verify()(fakeRequest)
      status(result) shouldBe Status.OK
      contentAsJson(result) shouldBe Json.parse(
        """{
          |  "accountNumberIsWellFormatted":"yes",
          |  "sortCodeIsPresentOnEISCD":"yes",
          |  "accountExists":"yes",
          |  "nameMatches":"yes",
          |  "sortCodeSupportsDirectDebit":"yes",
          |  "sortCodeSupportsDirectCredit":"yes"
          |}""".stripMargin)
