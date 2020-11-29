
/**
 * Just one more example of a menu state
 * This class now creates the menu for writing to a file - Dan Holmgren
 * 
 * @author merlin
 *
 */
public class WriteState extends State
{
	 MenuOption[] myMenuOptions =
		{ new MenuOptionForMenu("Go Back", MenuStateEnum.START_MENU),
		    new MenuOptionForAction("Choose a file to write", new WriteFileAction()), 
				new MenuOptionForMenu("Exit", MenuStateEnum.END_STATE) };

	/**
	 * 
	 */
	WriteState()
	{

		
		super.loadMenu(myMenuOptions);
	}
}
