import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintStream;

/**
 * This is the state that the machine starts in.  In other words, it is the highest level menu
 * @author merlin
 *
 */
public class StartState extends State
{

	static final MenuOption[] x =
		{ new MenuOptionForMenu("Read a File", MenuStateEnum.READ_MENU),
				new MenuOptionForMenu("Write to a File", MenuStateEnum.WRITE_MENU),
				new MenuOptionForAction("Test the Servers", new TestServersAction()),
				new MenuOptionForMenu("Exit", MenuStateEnum.END_STATE) };

	/**
	 * 
	 */
	StartState()
	{
	  Process p;
    try {
      p = Runtime.getRuntime().exec("orbd -ORBInitialHost localhost -ORBInitialPort 1056 -port 1057&");
      PrintStream out = new PrintStream(p.getOutputStream());
      BufferedReader in = new BufferedReader(new InputStreamReader(p.getInputStream()));

      out.println("ls");
      while (in.ready()) {
        String s = in.readLine();
        System.out.println(s);
      }
      out.println("exit");

      p.waitFor();
    } catch (Exception e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
		super.loadMenu(x);
	}

}
