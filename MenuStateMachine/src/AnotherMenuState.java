
/**
 * This is just another example of what a menu state can look like.
 * 
 * @author merlin
 *
 */
public class AnotherMenuState extends State
{

	private MenuOption[] myMenuOptions =
		{ new MenuOptionForAction("Do another Action", new AnotherConcreteAction(),YetAnotherMenuState.class),
				new MenuOptionForMenu("Exit", YetAnotherMenuState.class) };
	
	/**
	 * 
	 */
	public AnotherMenuState()
	{
		super.loadMenu(myMenuOptions);
	}
}
