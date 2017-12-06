import scala.swing.SimpleSwingApplication
import scala.swing.FlowPanel
import scala.swing.ScrollPane
import scala.swing.ListView
import scala.swing.MainFrame
import scala.swing.FileChooser
import java.io.File


object swing extends SimpleSwingApplication{
  	def top = new MainFrame {
  	  val items = List(0,0,0,0);
			import ListView._;
			contents = new FlowPanel(new ScrollPane(new ListView(items))){
			  //contents.append(fileChooser(""))
			}
		}
  	def fileChooser(title : String =""):Option[File] = {
  	  val chooser = new FileChooser(new File("."));
  	  chooser.title = title;
  	  val result = chooser.showOpenDialog(null);
  	  if(result==FileChooser.Result.Approve){
  	    println("");
  	    Some(chooser.selectedFile);
  	  } else 
  	    None
  	  
  	}
}