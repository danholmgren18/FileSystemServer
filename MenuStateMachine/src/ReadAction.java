import java.util.Scanner;

/**
 * This is a stupid action just to show how things work
 * 
 * This now is the action that is used to read a line from a file
 * 
 * @author merlin
 *
 */
public class ReadAction extends MenuAction
{

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
    System.out.println("Enter the line number you would like to see:");   
    lineNumber = scan.nextLine();
    
    // check that lineNumber is an int
    try {
      lineNum = Integer.valueOf(lineNumber);
  } catch (NumberFormatException e) {
    //while(lineNum != Integer.valueOf(lineNumber))
    System.out.println("Error: Please enter an integer value for a line number!");
    lineNum = scan.nextInt();
  }
    // check that file exists TODO
    
    
    
    System.out.println("Name: " + fileName + "  Line: " + lineNum);

	}

}
