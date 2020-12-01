
/**
 * Just one more example of a menu state
 * 
 * @author merlin
 *
 */
public class WriteState extends State
{
	 private MenuOption[] myMenuOptions =
		{   new MenuOptionForAction("Write to a File", new WriteAction(), WriteState.class),
		    new MenuOptionForMenu("Return to Main Menu", StartState.class), 
				new MenuOptionForMenu("Exit", EndState.class) };

	/**
	 * 
	 */
	public WriteState()
	{
		super.loadMenu(myMenuOptions);
	}
}
