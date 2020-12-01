
/**
 * This is the state that the machine starts in.  In other words, it is the highest level menu
 * @author merlin
 *
 */
public class StartState extends State
{

	private static final MenuOption[] x =
		{ new MenuOptionForMenu("Open file for read", AnotherMenuState.class),
				new MenuOptionForMenu("Open file for write",SecondMenuState.class),
				new MenuOptionForAction("This one does something", new ConcreteMenuAction(),StartState.class),
				new MenuOptionForMenu("Exit", EndState.class) };

	/**
	 * 
	 */
	public StartState()
	{

		super.loadMenu(x);
	}

}
