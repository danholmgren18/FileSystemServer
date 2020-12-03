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
        listOfLocalFiles.get(i).startReading();
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
    
    FileSystem fileSystemImpl;
    String[] arguments = { "java", "-Xmx10g", "-cp", ".:../../FileSystem/", "FileSystemApp.FileSystemClient",
        "-ORBInitialHost", "lsaremotees", "-ORBInitialPort", "1056", "-port", "1057" };
    try {
      // create and initialize the ORB
      ORB orb = ORB.init(arguments, null);

      // get the root naming context
      org.omg.CORBA.Object objRef = orb.resolve_initial_references("NameService");
      // Use NamingContextExt instead of NamingContext. This is
      // part of the Interoperable naming Service.
      NamingContextExt ncRef = NamingContextExtHelper.narrow(objRef);

      // resolve the Object Reference in Naming
      String name = "FileSystem";
      fileSystemImpl = FileSystemHelper.narrow(ncRef.resolve_str(name));
    } catch (Exception e) {
      return "Failed";
    }
    
    if(!(fileSystemImpl.lockForWrite(title)).equals("Success")) {
      return "Failed";
    }

    return targetFileContents;
  }

  @Override
  public String closeRead(String title) {
    
    FileSystem fileSystemImpl;
    String[] arguments = { "java", "-Xmx10g", "-cp", ".:../../FileSystem/", "FileSystemApp.FileSystemClient",
        "-ORBInitialHost", "lsaremotees", "-ORBInitialPort", "1056", "-port", "1057" };
    try {
      // create and initialize the ORB
      ORB orb = ORB.init(arguments, null);

      // get the root naming context
      org.omg.CORBA.Object objRef = orb.resolve_initial_references("NameService");
      // Use NamingContextExt instead of NamingContext. This is
      // part of the Interoperable naming Service.
      NamingContextExt ncRef = NamingContextExtHelper.narrow(objRef);

      // resolve the Object Reference in Naming
      String name = "FileSystem";
      fileSystemImpl = FileSystemHelper.narrow(ncRef.resolve_str(name));
    } catch (Exception e) {
      return "Failed";
    }
    
    if(stopReadLocally(title).equals("Failed")) {
      return "Failed";
    }
    
    if(!(fileSystemImpl.stopReadLocally(title)).equals("Success")) {
      return "Failed";
    } 
    return "Success";
  }

  @Override
  public String closeWrite(String title) {
    
    FileSystem fileSystemImpl;
    String[] arguments = { "java", "-Xmx10g", "-cp", ".:../../FileSystem/", "FileSystemApp.FileSystemClient",
        "-ORBInitialHost", "lsaremotees", "-ORBInitialPort", "1056", "-port", "1057" };
    try {
      // create and initialize the ORB
      ORB orb = ORB.init(arguments, null);

      // get the root naming context
      org.omg.CORBA.Object objRef = orb.resolve_initial_references("NameService");
      // Use NamingContextExt instead of NamingContext. This is
      // part of the Interoperable naming Service.
      NamingContextExt ncRef = NamingContextExtHelper.narrow(objRef);

      // resolve the Object Reference in Naming
      String name = "FileSystem";
      fileSystemImpl = FileSystemHelper.narrow(ncRef.resolve_str(name));
    } catch (Exception e) {
      return "Failed";
    }
    
    if(unlockLocally(title).equals("Failed")) {
      return "Failed";
    }
    
    if(!(fileSystemImpl.unlockLocally(title)).equals("Success")) {
      return "Failed";
    } 
    return "Success";
  }

  /*
   * Locks the local file. Called by A server
   */
 @Override
  public String lockForWrite(String title) {
   
   for(int i = 0; i < listOfLocalFiles.size(); i++) {
     if (listOfLocalFiles.get(i).getTitle().equals(title)) {
       if( listOfLocalFiles.get(i).isLocked()) {
         return "Failed";
       } else { 
         listOfLocalFiles.get(i).setLocked(true);
         return "Success";
       }
     }
   }
    return null;
  }

@Override
public String unlockLocally(String title) {
    for(int i = 0; i < listOfLocalFiles.size(); i++) {
      if (listOfLocalFiles.get(i).getTitle().equals(title)) {
        listOfLocalFiles.get(i).setLocked(false);
        return "Success";
      }
    }
    return "Failed";
}

@Override
public String stopReadLocally(String title) {
    for(int i = 0; i < listOfLocalFiles.size(); i++) {
      if (listOfLocalFiles.get(i).getTitle().equals(title)) {
        listOfLocalFiles.get(i).stopReading();
        return "Success";
      }
    }
    return "Failed";
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
