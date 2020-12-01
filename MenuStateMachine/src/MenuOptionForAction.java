
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
	 * @param nextState the next state the machine should go to
	 */
	MenuOptionForAction(String menuOptionText, MenuAction menuAction, Class<? extends State> nextState)
	{
		super.menuOptionText = menuOptionText;
		this.menuAction = menuAction;
		this.nextStateClass = nextState;
	}

	/**
	 * @return the action association with this option
	 */
	MenuAction getMenuAction()
	{
		return menuAction;
	}

}
