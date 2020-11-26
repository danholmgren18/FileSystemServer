import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * This is a stupid action just to show how things work
 * 
 * @author merlin
 *
 */
public class TestServersAction extends MenuAction
{

	/**
	 * {@inheritDoc}
	 */
	@Override
	void execute()
	{
		System.out.println("Gonna Test Those Server bois");
		
		Process p;
    try {
      p = Runtime.getRuntime().exec("java -Xmx10g -cp .:../../FileSystem/ FileSystemApp.FileSystemClient -ORBInitialHost clipper -ORBInitialPort 1056 -port 1057");
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


	}

}
