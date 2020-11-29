import java.util.Scanner;

public class ReadLineAction extends MenuAction{

  @Override
  void execute() {
    Scanner keyboard = new Scanner(System.in);
    System.out.println("The title of the file you would like to access: ");
    String fileNameInput = keyboard.nextLine();
    System.out.println("The line of the file you would like to access: ");
    String fileLineInput = keyboard.nextLine();

    System.out.println("The " + fileLineInput + "th line of " + fileNameInput + " is: " + "Something else bruv");
    
  }

}
