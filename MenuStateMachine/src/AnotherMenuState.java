
/**
 * This is just another example of what a menu state can look like.
 * 
 * @author merlin
 *
 */
public class AnotherMenuState extends State
{

	/**
	 * 
	 */
	AnotherMenuState()
	{
		MenuOption[] myMenuOptions =
			{ new MenuOptionForAction("Do another Action", new AnotherConcreteAction()),
					new MenuOptionForMenu("Exit", MenuStateEnum.END_STATE) };
		
		super.loadMenu(myMenuOptions);
	}
}
