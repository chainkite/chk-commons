package chk.commons.test

import chk.commons.util.ArtifactVersionUtils
import org.apache.maven.artifact.versioning.{DefaultArtifactVersion, InvalidVersionSpecificationException, VersionRange}

class ArtifactVersionUtilsSpec extends UnitSpec with ArtifactVersionUtils {

  behavior of "Artifact Version Utils"

  it should "throws InvalidVersionSpecificationException if version range is invalid (static method)" in {
    an [InvalidVersionSpecificationException] should be thrownBy { "(,]" containsVersion "0.5" }
  }

  it should "throws InvalidVersionSpecificationException if version range is invalid (implicit method)" in {
    an [InvalidVersionSpecificationException] should be thrownBy { "(,]".toVersionRange }
  }

  it should "returns false if version range is invalid" in {
    "(,]".isValidVersionRange shouldBe false
  }

  it should "returns true if version range is invalid" in {
    "(1.0,)".isValidVersionRange shouldBe true
  }

  it should "checks artifact version contained in the range" in {
    "(,1.0]" containsVersion "0.5" shouldBe true
    "[1.0]" containsVersion "1.0" shouldBe true
    "[1.0,)" containsVersion "1.3" shouldBe true
    "[1.0,2.0]" containsVersion "1.4" shouldBe true
  }

  it should "checks artifact version contained not in the range" in {
    "(,1.0]" containsVersion "1.5" shouldBe false
    "[1.0]" containsVersion "2.0" shouldBe false
    "[1.0,)" containsVersion "0.9" shouldBe false
    "[1.0,2.0]" containsVersion "2.1" shouldBe false
  }

  it should "checks version range is valid" in {
    "(,1.0]".isValidVersionRange shouldBe true
    "1.0".isValidVersionRange shouldBe true
    "(,]".isValidVersionRange shouldBe false
    "(1.0".isValidVersionRange shouldBe false
  }

  it should "convert from string to artifact version" in {
    val ver = "1.0.0"
    val av = new DefaultArtifactVersion(ver)
    ver.toArtifactVersion shouldEqual av
  }

  it should "convert from string to version range" in {
    val spec = "[1.0,)"
    val vr = VersionRange.createFromVersionSpec(spec)
    spec.toVersionRange shouldEqual vr
  }

}
