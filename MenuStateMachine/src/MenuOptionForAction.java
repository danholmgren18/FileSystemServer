
/**
 * This describes a menu option that has a task associated with it.
 * @author merlin
 *
 */
public class MenuOptionForAction extends MenuOption
{

	private MenuAction menuAction;

	/**
	 * 
	 * @param menuOptionText the text that describes this action to the user
	 * @param menuAction the action that should be taken when the user selects this option
	 */
	MenuOptionForAction(String menuOptionText, MenuAction menuAction)
	{
		super.menuOptionText = menuOptionText;
		this.menuAction = menuAction;
	}

	/**
	 * @return the action association with this option
	 */
	MenuAction getMenuAction()
	{
		return menuAction;
	}

}
