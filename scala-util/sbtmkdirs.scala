import java.io.File
import scala.swing._

object Mkdirs extends SimpleSwingApplication {
	
	val pathField = new TextField(25)
	val nameField = new TextField(25)
	val captionPanel = new BoxPanel(scala.swing.Orientation.Vertical) {
		contents += new Label("Path: ")
		contents += new Label("Name: ")
	}
	val entryPanel = new BoxPanel(scala.swing.Orientation.Vertical) {
		contents += new BoxPanel(scala.swing.Orientation.Horizontal) {
			contents += pathField
			contents += new Button {
				action = Action("Browse Files") {
					chooseDirectory() match {
						case Some(x) => pathField.text = x.getAbsolutePath
						case None =>
					}
				}
			}
		}
		contents += nameField
	}
	
	val gitignore_? = new CheckBox(".gitignore")
	val readme_? = new CheckBox("README.md")
	val fileChooser = new FileChooser(new File("./"))
	val mainPanel = new BoxPanel(scala.swing.Orientation.Vertical) {
		contents += new BoxPanel(scala.swing.Orientation.Horizontal) {
			contents += captionPanel
			contents += entryPanel
		}
		contents += gitignore_?
		contents += readme_?
		contents += new Button {
			action = Action("create projectstructure") {
				createStructure
				Dialog.showMessage(message = "Your projectstructure has been created.")
				System.exit(0)
			}
		}
	}
	
	def top = new MainFrame {
		title = "SBT mkdirs"
		contents = mainPanel
	}
	
	def chooseDirectory(title: String = ""): Option[File] = {
		val chooser = new FileChooser(null)
		chooser.fileSelectionMode = FileChooser.SelectionMode.DirectoriesOnly
		chooser.title = title
		val result = chooser.showOpenDialog(null)
		if (result == FileChooser.Result.Approve) Some(chooser.selectedFile)
		else None
	}
	
	def createStructure {
		val path = pathField.text
		val name = nameField.text
		
		val project = path + "/" + name
		new File(project).mkdir()
		val buildSbt = new File(project + "/build.sbt").createNewFile()
		val out = new java.io.PrintWriter(buildSbt)
		out.println("name := " + name + "\n")
		out.println("version := 1.0\n")		// TODO make it customizable
		out.println("scalaVersion := 2.9.2\n")
		out.close
		if (gitignore_?.selected)
			new File(project + "/.gitignore").createNewFile()
		if (readme_?.selected)
			new File(project + "/README.md").createNewFile()
		new File(project + "/lib").mkdir()
		new File(project + "/project").mkdir()
		new File(project + "/project/build.sbt").createNewFile()
		new File(project + "/src/main/java").mkdirs()
		new File(project + "/src/main/resources").mkdir()
		new File(project + "/src/main/scala").mkdir()
		new File(project + "/src/test/java").mkdirs()
		new File(project + "/src/test/resources").mkdir()
		new File(project + "/src/test/scala").mkdir()
		new File(project + "/target").mkdir()
	}
}