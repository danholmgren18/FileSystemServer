import java.util.InputMismatchException;
import java.util.Scanner;

import org.omg.CORBA.*;
import org.omg.CosNaming.*;

import FileSystemApp.FileSystem;
import FileSystemApp.FileSystemHelper;

/**
 * A runnable finite state machine for a text-based menuing system.  The kinds of menus it manages look like this
 * 
 * <ol>
 * <li> Open a file for reading
 * <li> Open a file for write
 * <li> Exit
 * </ol>
 * <br>
 * <h3> States</h3>
 * The basic strategy here is that each set of menu options is encoded in a class that is a subclass of the State class.
 * Those classes only need to define their menu and call super.loadMenu in their constructor.  The first menu is defined in
 * StartState and the given code shows how that state gets set up.
 * <br>
 * Creating a new state requires:
 * <ul>
 * <li>Creating the new state class (you'll need to encode MenuOptions that are described below)
 * <li>Adding an instance of that state to the MenuStateEnum
 * <li>Adding menu options in other states to encode how the user gets to this new state
 * </ul>
 * 
 * <h3> Menu Options</h3>
 * Each state has an array of menu options describing the text the user should see and what should happen if the
 * user selects that option.  The options in a menu can be of two types
 * <ul>
 * <li>MenuOptionForMenu is used when selecting that option moves the machine to a new state so a new menu would be displayed
 * <li>MenuOptionForAction is used when selection that option has an action beyond changing the menu state
 * </ul>
 * 
 * When you want to create a new action (a new transaction script), you have to make a new class that is a subclass of
 * MenuAction.  It has to have an execute method that encodes the behavior associated with that action.
 * 
 * @author Merlin
 *
 */
public class MenuMachine
{
 // static FileSystem fileSystemImpl;
	/**
	 * @param args ignored
	 */
	public static void main(String[] args)
	{
//	  String[] arguments = {"java", "-Xmx10g", "-cp", ".:../../FileSystem/", "FileSystemApp.FileSystemClient",
//	                       "-ORBInitialHost", "clipper", "-ORBInitialPort", "1056", "-port", "1057"};
//	  try
//    {
//      // create and initialize the ORB
//      ORB orb = ORB.init(arguments, null);
//
//      // get the root naming context
//      org.omg.CORBA.Object objRef = orb.resolve_initial_references("NameService");
//      // Use NamingContextExt instead of NamingContext. This is
//      // part of the Interoperable naming Service.
//      NamingContextExt ncRef = NamingContextExtHelper.narrow(objRef);
//
//      // resolve the Object Reference in Naming
//      String name = "FileSystem";
//      fileSystemImpl = FileSystemHelper.narrow(ncRef.resolve_str(name));
//    } catch (Exception e) {
//      System.out.println("ERROR : " + e);
//      e.printStackTrace(System.out);
//    }
		Scanner keyboard = new Scanner(System.in);
		State currentState = new StartState();
		while (currentState.getClass() != EndState.class)
		{
			currentState.printOptions();
			try
			{
				int option = keyboard.nextInt();
				currentState = currentState.processOption(option);
			} catch (InputMismatchException e)
			{
				System.out.println("Please enter the number of the option you'd like to select");
				
			}
			keyboard.nextLine();
		}
		keyboard.close();
	}
}
