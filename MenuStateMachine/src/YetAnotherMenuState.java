/**
 * 
 * @author merlin
 *
 */
public class YetAnotherMenuState extends State
{
	/**
	 * 
	 */
	public YetAnotherMenuState()
	{
		MenuOption[] myMenuOptions =
			{ new MenuOptionForAction("Read another file", new AnotherConcreteAction(),YetAnotherMenuState.class),
			  new MenuOptionForMenu("Return to main menu", StartState.class), 
					new MenuOptionForMenu("Exit", EndState.class) };
		
		super.loadMenu(myMenuOptions);
	}
}
