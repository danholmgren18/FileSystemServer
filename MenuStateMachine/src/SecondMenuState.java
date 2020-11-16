
/**
 * Just one more example of a menu state
 * 
 * @author merlin
 *
 */
public class SecondMenuState extends State
{
	 MenuOption[] myMenuOptions =
		{ new MenuOptionForMenu("Go Back", MenuStateEnum.START_MENU), 
				new MenuOptionForMenu("Exit", MenuStateEnum.END_STATE) };

	/**
	 * 
	 */
	SecondMenuState()
	{

		
		super.loadMenu(myMenuOptions);
	}
}
