import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Scanner;

//import FileSystemApp.FileInstance;

/**
 * Reads a file name and specific line in from user,
 * will then print the contents of that line
 * @author dh7548
 *
 */
public class ReadLineAction extends MenuAction{

  @Override
  void execute() {
    Scanner keyboard = new Scanner(System.in);
    System.out.println("The title of the file you would like to access: ");
    String fileNameInput = keyboard.nextLine();
    System.out.println("The line of the file you would like to access: ");
    String fileLineInput = keyboard.nextLine();
    
    ArrayList<FileInstance> listOfFiles = new ArrayList<FileInstance>();
      Path currentRelativePath = Paths.get("");
      String s = currentRelativePath.toAbsolutePath().toString();
      File f = new File(s + "/Files");
      String[] files = f.list();
      
      
      // Populates the array with names of files and directories
      for(int i = 0; i < files.length; i++) {
        listOfFiles.add(new FileInstance(new File(s + "/Files/" + files[i])));
      }
    String targetLine = "Didnt get it sorry";
    for(int i = 0; i < listOfFiles.size(); i++) {
      if (listOfFiles.get(i).getTitle().equals(fileNameInput)) {
        targetLine = listOfFiles.get(i).readLine(Integer.parseInt(fileLineInput));
      }
    }
    System.out.println("The " + fileLineInput + "th line of " + fileNameInput + " is: " + targetLine);
    
    keyboard.close();
  }

}
