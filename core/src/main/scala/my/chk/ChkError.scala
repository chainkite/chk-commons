package my.chk

case class ChkError(code: String, error: String)

object SystemError extends SystemError
trait SystemError {
  final lazy val INTERNAL_ERROR = ChkError("sys.error", "Internal Server Error")
  final lazy val CONFIG_ERROR = ChkError("config.error", "Configuration Error")
}

/*
trait JsonError {
  final lazy val JSON_PARSE_ERROR = ChkError("json.error.parse", "Invalid JSON Format")
  final lazy val JSON_EXTRACT_ERROR = ChkError("json.error.extract", "Invalid JSON Format")
  final lazy val JSON_OP_ERROR = ChkError("json.error.operation", "JSON Operation Fail")
}
*/
