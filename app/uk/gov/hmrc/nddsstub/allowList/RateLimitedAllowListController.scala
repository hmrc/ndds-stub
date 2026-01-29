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

import play.api.Logging
import play.api.libs.json.Json
import play.api.mvc.{Action, ControllerComponents}
import uk.gov.hmrc.play.bootstrap.backend.controller.BackendController

import javax.inject.{Inject, Singleton}
import scala.util.Try

@Singleton()
class RateLimitedAllowListController @Inject()(cc: ControllerComponents) extends BackendController(cc) with Logging:

  def verify(ignore1: String, ignore2: String): Action[CheckRequest] =
    Action(parse.json[CheckRequest]):
      implicit request =>
        val endChar: Either[Char, Int] = Try(request.body.identifier.last.toString.toInt).toEither.left.map(_ => request.body.identifier.last)
        
        if (request.body.identifier.endsWith("5")) then 
          InternalServerError
        else if (request.body.identifier.endsWith("4")) then
          BadRequest
        else if (Try(request.body.identifier.last.toString.toInt).getOrElse(1) % 2 == 0 ) then
          Ok(Json.obj("included" -> true))
        else
          Ok(Json.obj("included" -> false))
