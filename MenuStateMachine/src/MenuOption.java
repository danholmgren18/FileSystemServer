import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

/**
 * Defines the requirements of all menu options
 * 
 * @author merlin
 *
 */
public abstract class MenuOption
{

	/**
	 * The text describing this option to the user
	 */
	String menuOptionText;

	/**
	 * The next state the machine should go to
	 */
	Class<? extends State> nextStateClass;

	/**
	 * @return the text describing this option
	 */
	String getMenuOptionText()
	{
		return menuOptionText;
	}

	/**
	 * @return the state associated with this option.
	 */
	public State getNextMenuState()
	{
		
			Constructor<? extends State> constructor;
			try
			{
				constructor = nextStateClass.getConstructor();
				return (State)constructor.newInstance();
			} catch (NoSuchMethodException | SecurityException | InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException e)
			{
				e.printStackTrace();
			}
		
		
		return null;
	}

}
