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
			{ new MenuOptionForAction("Do another Action and stay in this state", new AnotherConcreteAction(),YetAnotherMenuState.class),
					new MenuOptionForMenu("Exit", EndState.class) };
		
		super.loadMenu(myMenuOptions);
	}
}
