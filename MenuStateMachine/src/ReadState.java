
/**
 * This is just another example of what a menu state can look like.
 * This class now provides the menu state for reading a file - Dan Holmgren
 * @author merlin
 *
 */
public class ReadState extends State
{

	/**
	 * 
	 */
	ReadState()
	{
		MenuOption[] myMenuOptions =
			{ new MenuOptionForMenu("Go Back", MenuStateEnum.START_MENU),
			    new MenuOptionForAction("Choose a file and line to read", new ReadLineAction()),
					new MenuOptionForMenu("Exit", MenuStateEnum.END_STATE) };
		
		super.loadMenu(myMenuOptions);
	}
}
