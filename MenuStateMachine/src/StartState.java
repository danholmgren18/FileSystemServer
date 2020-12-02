
/**
 * This is the state that the machine starts in.  In other words, it is the highest level menu
 * @author merlin
 *
 */
public class StartState extends State
{

	private static final MenuOption[] x =
		{ new MenuOptionForMenu("Open file for read", ReadState.class),
				new MenuOptionForMenu("Open file for write", WriteState.class),
				new MenuOptionForAction("Ping Servers", new PingServerAction(), StartState.class),
				new MenuOptionForMenu("Exit", EndState.class) };

	/**
	 * 
	 */
	public StartState()
	{

		super.loadMenu(x);
	}

}
