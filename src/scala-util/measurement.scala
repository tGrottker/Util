object Measurement {
	
	def measure[A](f: => A): Long = {
		val start = System.nanoTime()
		f
		System.nanoTime() - start
	}
	
	def multiMeasure[A](i: Int, f: => A): Long = {
		val durations = for (turn <- 0 until i) yield measure(f)
		(durations.sum / durations.size.toDouble) toLong
	}
	
}