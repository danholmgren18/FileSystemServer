
/**
 * This is the state that the machine starts in.  In other words, it is the highest level menu
 * @author merlin
 *
 */
public class StartState extends State
{

	static final MenuOption[] x =
		{ new MenuOptionForMenu("First Option to another menu", MenuStateEnum.ANOTHER_MENU),
				new MenuOptionForMenu("Second Option to Second Menu",MenuStateEnum.SECOND_MENU),
				new MenuOptionForAction("This one does something", new ConcreteMenuAction()),
				new MenuOptionForMenu("Exit", MenuStateEnum.END_STATE) };

	/**
	 * 
	 */
	StartState()
	{

		super.loadMenu(x);
	}

}
