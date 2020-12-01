
/**
 * This type of menu option causes the machine to move to a new state.
 * @author merlin
 *
 */
public class MenuOptionForMenu extends MenuOption
{

	/**
	 * @param menuOptionText the text that describes the option to the user
	 * @param nextStateClass the class of the state that the menu machine should move to if this option is selected
	 */
	public MenuOptionForMenu(String menuOptionText, Class<? extends State> nextStateClass)
	{
		super.menuOptionText = menuOptionText;
		super.nextStateClass = nextStateClass;
	}

}
