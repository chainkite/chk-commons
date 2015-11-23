package my.chk.util

import org.apache.maven.artifact.versioning._

import scala.util.Try


object ArtifactVersionUtils extends ArtifactVersionUtils {

  /**
   * Checks if artifact version is contained in the version range.
   *
   * {{{
   * VersionRange.contains("[1.0,)", "1.3") // return true
   * VersionRange.contains("[1.0,)", "0.5") // return false
   * }}}
   *
   * @see [[http://maven.apache.org/enforcer/enforcer-rules/versionRanges.html Apache Maven Enforcer Rules - Version Range Specification]]
   *
   * @param versionRange Version Range Specification by Maven
   * @param artifactVersion Artifact Version
   * @return true if versionRange included the given artifactVersion, false if otherwise
   * @throws org.apache.maven.artifact.versioning.InvalidVersionSpecificationException if VersionRange is invalid
   */
  @throws[InvalidVersionSpecificationException]("if Version Range is invalid")
  def contains(versionRange: String, artifactVersion: String): Boolean = {
    val ver = new DefaultArtifactVersion(artifactVersion)
    val range = VersionRange.createFromVersionSpec(versionRange)
    range.containsVersion(ver)
  }

  /**
   * Checks if input string is valid version specification.
   *
   * @see [[http://maven.apache.org/enforcer/enforcer-rules/versionRanges.html Apache Maven Enforcer Rules - Version Range Specification]]
   * @param versionRange Version Range Specification By Maven
   * @return true if version range is valid, false if otherwise
   */
  def isValidVersionRange(versionRange: String): Boolean = Try(VersionRange.createFromVersionSpec(versionRange)).isSuccess
}

trait ArtifactVersionUtils {

  implicit class ArtifactVersionStringOps(underlying: String) {

    /**
     * Convert into ArtifactVersion
     * @return [[org.apache.maven.artifact.versioning.ArtifactVersion ArtifactVersion]]
     */
    def toArtifactVersion: ArtifactVersion = new DefaultArtifactVersion(underlying)

    /**
     * Convert into VersionRange
     * @return [[org.apache.maven.artifact.versioning.VersionRange VersionRange]]
     * @throws org.apache.maven.artifact.versioning.InvalidVersionSpecificationException if invalid version range expression
     */
    @throws[InvalidVersionSpecificationException]
    def toVersionRange = VersionRange.createFromVersionSpec(underlying)

    def isValidVersionRange = ArtifactVersionUtils.isValidVersionRange(underlying)
  }

  implicit class ArtifactVersionOps(ver: ArtifactVersion) {
    def < (other: ArtifactVersion) = ver.compareTo(other) < 0

    def <= (other: ArtifactVersion) = ver.compareTo(other) <= 0

    def > (other: ArtifactVersion) = ver.compareTo(other) > 0

    def >= (other: ArtifactVersion) = ver.compareTo(other) >= 0

    def == (other: ArtifactVersion) = ver.compareTo(other) == 0
  }
}
