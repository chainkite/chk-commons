package chk.commons.test

import chk.commons.util.SecurityUtils


class SecurityUtilsSpec extends SecurityUtils with UnitSpec {

   behavior of "Security Utils"

   it should "encode and decode UUID as Base64" in {
     val uuid = SecurityUtils.uuid
     val base64 = SecurityUtils.uuidToBase64(uuid)
     val decodeUUID = SecurityUtils.base64ToUUID(base64)
     decodeUUID should equal(uuid)
   }
 }
