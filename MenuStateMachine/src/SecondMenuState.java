
/**
 * Just one more example of a menu state
 * 
 * @author merlin
 *
 */
public class SecondMenuState extends State
{
	 private MenuOption[] myMenuOptions =
		{ new MenuOptionForMenu("Go Back", StartState.class), 
				new MenuOptionForMenu("Exit", EndState.class) };

	/**
	 * 
	 */
	public SecondMenuState()
	{
		super.loadMenu(myMenuOptions);
	}
}
