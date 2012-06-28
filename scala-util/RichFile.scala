import java.io._
import java.nio._
import scala.io._

class RichFile(file: File) {
	
	def text = Source.fromFile(file).mkString
	
	def text_=(s: String) {
		val out = new PrintWriter(file)
		try { out.print(s) }
		finally { out.close }
	}
	
}

object RichFile {
	
	implicit def enrich(file: File) = new RichFile(file)
	
}