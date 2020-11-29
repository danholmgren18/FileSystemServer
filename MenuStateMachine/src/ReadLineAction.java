import java.util.Scanner;

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

<<<<<<< HEAD
    System.out.println("The " + fileLineInput + "th line of " + fileNameInput + " is: " + "Something else bruv");
=======
    System.out.println("The " + fileLineInput + "th line of " + fileNameInput + " is: " + "");
>>>>>>> a868cad59c3b7d955dd32779a8739d1b5fc4e42e
    
    keyboard.close();
  }

}
