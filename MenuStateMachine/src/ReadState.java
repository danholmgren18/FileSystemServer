import java.util.Scanner;

/**
 * This is just another example of what a menu state can look like.
 * Ask user for information on the file they want to read
 * 
 * @author merlin & Marlee
 *
 */
public class ReadState extends State
{

	private MenuOption[] myMenuOptions =
		{ new MenuOptionForAction("Read file", new ReadAction(), ReadState.class),
		  new MenuOptionForMenu("Return to main menu", StartState.class), 
				new MenuOptionForMenu("Exit", EndState.class) };
	
	/**
	 * First prompt the user to enter the name of the file and file number they want to view
	 * 
	 * Finally, call the loadMenu() method from the super class State, passing in the menu options from this class to allow the user to read another file or exit0
	 */
	public ReadState()
	{
	  
		super.loadMenu(myMenuOptions);
	}
}
