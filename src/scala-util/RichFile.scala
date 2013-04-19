import java.io._
import java.nio._
import scala.io._

/**
  * Provides easy access to a java.io.File.
  */
class RichFile(file: File) {
	
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
	
	/**
	 * Adds a string to the end of the file.
	 * 
	 * @param s The content to append to the file.
	 */
	def +=(s: String) {
		var outStream: Option[FileOutputStream] = None
		lazy val outChannel = outStream.get getChannel()
		try {
			outStream = Some(new FileOutputStream(file, true))
			val buffer = ByteBuffer allocate(1024)
			s foreach (c => buffer put(c toByte))
			buffer flip()
			outChannel write buffer
		} finally {
			outChannel close()
			outStream.get close()
		}
	}
	
}

object RichFile {
	
	/**
	  * Provides an implicit conversion for java.io.File to RichFile.
	  */
	implicit def enrich(file: File) = new RichFile(file)
	
}
