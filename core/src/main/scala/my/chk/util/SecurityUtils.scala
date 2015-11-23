package my.chk.util

import java.nio.ByteBuffer
import java.security.SecureRandom
import java.util.UUID
import java.util.zip.CRC32

import org.apache.commons.codec.binary.Base64
import org.apache.commons.codec.digest.DigestUtils


trait SecurityUtils {

  implicit class StringSecurityOps(underlying: String) {
    def crc = SecurityUtils.crc(underlying)
    def md5 = DigestUtils.md5Hex(underlying)
    def sha1 = DigestUtils.sha1Hex(underlying)
    def sha256 = DigestUtils.sha256Hex(underlying)
    def base64 = Base64.encodeBase64URLSafeString(underlying.getBytes)
    def decodeBase64 = new String(Base64.decodeBase64(underlying))
    def toUUID = UUID.fromString(underlying)
    def base64ToUUID = SecurityUtils.base64ToUUID(underlying)
  }

  implicit class UUIDOps(uuid: UUID) {
    def base64: String = SecurityUtils.uuidToBase64(uuid)
  }

}

object SecurityUtils extends SecurityUtils {

  def uuid: UUID = UUID.randomUUID()

  def base64ToUUID(str: String): UUID = {
    val bytes = Base64.decodeBase64(str)
    val bbDecode = ByteBuffer.wrap(bytes)
    new UUID(bbDecode.getLong, bbDecode.getLong)
  }

  def uuidToBase64(uuid: UUID): String = {
    val bb = ByteBuffer.wrap(new Array[Byte](16))
    bb.putLong(uuid.getMostSignificantBits)
    bb.putLong(uuid.getLeastSignificantBits)
    Base64.encodeBase64URLSafeString(bb.array())
  }

  def crc(str: String): String = {
    val checksum = new CRC32
    checksum.update(str.getBytes)
    checksum.getValue.toHexString
  }

  def random(byteLength: Int): String = {
    val rand = new SecureRandom()
    val n: Array[Byte] = new Array[Byte](byteLength)
    rand.nextBytes(n)
    Base64.encodeBase64URLSafeString(n)
  }

}