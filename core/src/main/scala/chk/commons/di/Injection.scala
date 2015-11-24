package chk.commons.di

import scaldi._


trait Injection extends Injectable {
  implicit val injector: Injector
}
