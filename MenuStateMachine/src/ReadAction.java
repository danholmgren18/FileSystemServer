import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;

import FileSystemApp.FileSystem;

/**
 * This is a stupid action just to show how things work ~ Merlin
 * 
 * This now is the action that is used to read a line from a file
 * 
 * @author Marlee & Dan
 *
 */
public class ReadAction extends MenuAction
{
  static FileSystem fileSystemImpl;
	/**
	 * 
   * First prompt the user to enter the name of the file and file number they want to view
	 */
	@Override
	void execute()
	{
	  Scanner scan = new Scanner(System.in);
    String fileName; 
    String lineNumber; 
    int lineNum; // converts the user input (string) into a usable integer
    
    // prompt the user to enter the name of the file and line number they want to read
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
      
    } catch ( IOException e) {
      System.out.println("Error: Please enter a file name that exists.");
      fileName = scan.nextLine();
    }
    
	}

}
