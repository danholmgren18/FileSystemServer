
/**
 * This class models a single state in the menuing state machine.  NOTE:  every subclass of this state MUST have a
 * public no arg constructor!!!!!
 * 
 * @author merlin
 *
 */
public abstract class State
{

	private MenuOption[] menuOptions;

	/**
	 * This method is used to set up the menu options that are available when we are
	 * in this state
	 * 
	 * @param myMenuOptions the menu options this state offers
	 */
	void loadMenu(MenuOption[] myMenuOptions)
	{
		menuOptions = myMenuOptions;
	}

	/**
	 * Display this state's menu options to the user
	 */
	void printOptions()
	{
		System.out.println("\n Please pick an option\n");
		for (int i = 0; i < menuOptions.length; i++)
		{
			System.out.println(i + ":  " + menuOptions[i].getMenuOptionText());
		}
	}

	/**
	 * Do the action associated with a given option. If the option is an
	 * option-for-action, its action will be executed.
	 * 
	 * @param option which of the options should be followed
	 * @return the next state the machine should go to.
	 */
	public State processOption(int option)
	{
		MenuOption chosenOption = menuOptions[option];
		if (chosenOption instanceof MenuOptionForMenu)
		{
			return ((MenuOption) chosenOption).getNextMenuState();
		} else
		{
			MenuOptionForAction menuOptionForAction = (MenuOptionForAction) chosenOption;
			menuOptionForAction.getMenuAction().execute();
			return menuOptionForAction.getNextMenuState();
		}
	}

}
