import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;

import org.omg.CORBA.ORB;
import org.omg.CosNaming.NamingContextExt;
import org.omg.CosNaming.NamingContextExtHelper;

import FileSystemApp.FileSystem;
import FileSystemApp.FileSystemHelper;

/**
 * This is a stupid action just to show how things work ~ Merlin
 * 
 * This now is the action that is used to read a line from a file
 * 
 * @author Marlee & Dan
 *
 */
public class ReadAction extends MenuAction {
  static FileSystem fileSystemImpl;

  /**
   * 
   * First prompt the user to enter the name of the file and file number they want
   * to view
   */
  @Override
  void execute() {
    String[] arguments = { "java", "-Xmx10g", "-cp", ".:../../FileSystem/", "FileSystemApp.FileSystemClient",
        "-ORBInitialHost", "clipper", "-ORBInitialPort", "1056", "-port", "1057" };
    try {
      // create and initialize the ORB
      ORB orb = ORB.init(arguments, null);

      // get the root naming context
      org.omg.CORBA.Object objRef = orb.resolve_initial_references("NameService");
      // Use NamingContextExt instead of NamingContext. This is
      // part of the Interoperable naming Service.
      NamingContextExt ncRef = NamingContextExtHelper.narrow(objRef);

      // resolve the Object Reference in Naming
      String name = "FileSystem";
      fileSystemImpl = FileSystemHelper.narrow(ncRef.resolve_str(name));
    } catch (Exception e) {
      System.out.println("ERROR : " + e);
      e.printStackTrace(System.out);
    }
    Scanner scan = new Scanner(System.in);
    String fileName;
    String lineNumber;
    int lineNum; // converts the user input (string) into a usable integer

    // prompt the user to enter the name of the file and line number they want to
    // read
    System.out.println("Enter the name of the file:");
    fileName = scan.nextLine();
    System.out.println(fileSystemImpl.openFileForRead(fileName));
    System.out.println("Enter the line number you would like to see:");
    lineNumber = scan.nextLine();

    // check that lineNumber is an int
    try {
      lineNum = Integer.valueOf(lineNumber);
    } catch (NumberFormatException e) {
      System.out.println("Error: Please enter an integer value for a line number!");
      lineNum = scan.nextInt();
      // TODO: Make it so it loops until they give you an integer
    }

    // check that file exists TODO

    System.out.println("Name: " + fileName + "  Line: " + lineNum);

    // read the file
    try {
      FileReader fr = new FileReader(fileName);
      int currentLine = 0;
      while (currentLine < lineNum) { // skip to the correct line in the file
        currentLine++;
      }

      // get line from file and store into a string
      String line = "";

//      int i;
//      while ((i=fr.read()) != -1) 
//       line = line + (char) i; 

      // Print out the line number
      System.out.println(line);

    } catch (IOException e) {
      System.out.println("Error: Please enter a file name that exists.");
      fileName = scan.nextLine();
    }
    scan.close();
  }

}
