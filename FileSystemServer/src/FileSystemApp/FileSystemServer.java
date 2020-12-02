package FileSystemApp;

import java.io.File;
import java.io.FileNotFoundException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import org.omg.CORBA.ORB;
import org.omg.CosNaming.NameComponent;
import org.omg.CosNaming.NamingContextExt;
import org.omg.CosNaming.NamingContextExtHelper;
import org.omg.PortableServer.POA;
import org.omg.PortableServer.POAHelper;

class FileSystemImpl extends FileSystemPOA
{
	private ORB orb;
	private ArrayList<FileInstance> listOfLocalFiles = new ArrayList<FileInstance>();

	public FileSystemImpl() 
	{
	  //Initialize listOfLocalFiles
    Path currentRelativePath = Paths.get("");
    String s = currentRelativePath.toAbsolutePath().toString() + "/..";
    File f = new File(s + "/Files");
    String[] files = f.list();
  
 //Populates the array with names of files and directories
    for(int i = 0; i < files.length; i++) {
      listOfLocalFiles.add(new FileInstance(new File(s + "/Files/" + files[i])));
    }
	}
	
	public void setORB(ORB orb_val)
	{
		orb = orb_val;
	}

	// implement sayHello() method
	public String sayHello()
	{
		return "\nHello world !!\n";
	}

	// implement shutdown() method
	public void shutdown()
	{
		orb.shutdown(false);
	}

	@Override
	public String readFile(String title)
	{
		try
		{
			Scanner s = new Scanner(new File(title));
			StringBuffer contents = new StringBuffer("");
			while (s.hasNext())
			{
				contents.append(s.nextLine() + "\n");
			}

			s.close();
			return contents.toString();
		} catch (FileNotFoundException e)
		{
			return "File Not Here";
		}
	}

  @Override
  public String openFileForRead(String title) {
    for(int i = 0; i < listOfLocalFiles.size(); i++) {
      if (listOfLocalFiles.get(i).getTitle().equals(title)) {
        return listOfLocalFiles.get(i).getContents();
      }
    }
    return null;
  }

  @Override
  public String openFileForWrite(String title) {
    String targetFileContents = null;
    int whichpos = -1;
    //Check to see if here
    for(int i = 0; i < listOfLocalFiles.size(); i++) {
      if (listOfLocalFiles.get(i).getTitle().equals(title)) {
        targetFileContents =  listOfLocalFiles.get(i).getContents();
        whichpos = i;
      }
    }
    if (targetFileContents == null) {
      return "File Not Here";
    }
    
    /*
     * This is where we check to see if locked and then lock if not
     * 
     * Call the methods for every server to lock
     * 
     * while (list of servers txt file not EOF) {
     *   server.lockForWrite(title)
     * }
     */

    return targetFileContents;
  }

}


/**
 * This is the class that runs on the server
 * @author merlin
 *
 */
public class FileSystemServer
{

	/**
	 * @param args ignored
	 */
	public static void main(String args[])
	{
		try
		{ 
			// create and initialize the ORB
			ORB orb = ORB.init(args, null);

			// get reference to rootpoa & activate the POAManager
			POA rootpoa = POAHelper.narrow(orb.resolve_initial_references("RootPOA"));
			rootpoa.the_POAManager().activate();

			// create servant and register it with the ORB
			FileSystemImpl fileSystemImpl = new FileSystemImpl();
			fileSystemImpl.setORB(orb);

			// get object reference from the servant
			org.omg.CORBA.Object ref = rootpoa.servant_to_reference(fileSystemImpl);
			FileSystem href = FileSystemHelper.narrow(ref);

			// get the root naming context
			// NameService invokes the name service
			org.omg.CORBA.Object objRef = orb.resolve_initial_references("NameService");
			// Use NamingContextExt which is part of the Interoperable
			// Naming Service (INS) specification.
			NamingContextExt ncRef = NamingContextExtHelper.narrow(objRef);

			// bind the Object Reference in Naming
			String name = "FileSystem";
			NameComponent path[] = ncRef.to_name(name);
			ncRef.rebind(path, href);

			System.out.println("FileSystemServer ready and waiting ...");

			// wait for invocations from clients
			orb.run();
		}

		catch (Exception e)
		{
			System.err.println("ERROR: " + e);
			e.printStackTrace(System.out);
		}

		System.out.println("FileSystemServer Exiting ...");

	}
}
