/**
 * This class is used to write to a file that the user chooses,
 * will lock the file so no one else can use it when the user is
 * writing to it
 * @author dh7548
 *
 */
public class WriteFileAction extends MenuAction{

  @Override
  void execute() {
    System.out.println("This is where we'll write stuff");
  }

}
