
/**
 * This type of menu option causes the machine to move to a new state.
 * @author merlin
 *
 */
public class MenuOptionForMenu extends MenuOption
{

	private MenuStateEnum nextMenuState;

	/**
	 * @param menuOptionText the text that describes the option to the user
	 * @param nextMenuState the state that the menu machine should move to if this option is selected
	 */
	public MenuOptionForMenu(String menuOptionText, MenuStateEnum nextMenuState)
	{
		super.menuOptionText = menuOptionText;
		this.nextMenuState = nextMenuState;
	}

	/**
	 * @return the state associated with this option.
	 */
	public MenuStateEnum getNextMenuState()
	{
		return nextMenuState;
	}

}
