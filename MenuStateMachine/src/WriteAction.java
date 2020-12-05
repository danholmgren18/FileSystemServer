import java.util.Scanner;

/**
 * This action gets some information from the user and does something with it
 * 
 * @author merlin
 *
 */
public class WriteAction extends MenuAction
{

	/**
	 * {@inheritDoc}
	 */
	@Override
	void execute()
	{
		Scanner keyboard = new Scanner(System.in);
		System.out.println("Give me input for this action");
		String input = keyboard.nextLine();
		System.out.println("I can do something with this input: " + input);
		
		
		keyboard.close();
	}

}
