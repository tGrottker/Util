import java.io._
import java.nio._
import scala.io._

/**
  * Provides easy acces to a File.
  */
class RichFile(file: File) {
	
	// TODO add append
	
	/**
	  * Returns the content of a file as string.
	  */
	def text = Source.fromFile(file).mkString
	
	/**
	  * Sets the context of the file to the given string.
	  *
	  * @param newContent The new content of the file.
	  */
	def text_=(newContent: String) {
		val out = new PrintWriter(file)
		try { out.print(newContent) }
		finally { out.close }
	}
	
}

object RichFile {
	
	/**
	  * Provides an implicit conversion for java.io.File to RichFile.
	  */
	implicit def enrich(file: File) = new RichFile(file)
	
}