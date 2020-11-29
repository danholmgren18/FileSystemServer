package Commands;

import org.omg.CosNaming.*;

import FileSystemApp.FileSystem;
import FileSystemApp.FileSystemHelper;

import org.omg.CORBA.*;

public class OpenFileForReadCommand {
  static FileSystem fileSystemImpl;
  public OpenFileForReadCommand(String serverName, String title, int lineNum)
  {
    try
    {
      String [] arguments = new String [] {"-ORBInitialHost", serverName, "-ORBInitialPort", "1056", "-port", "1057"};
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

      System.out.println("Obtained a handle on server object: " + fileSystemImpl);
      System.out.println(fileSystemImpl.openFileLineNumber(title, lineNum));
      // This is how we would shut down the server
      //fileSystemImpl.shutdown();

    } catch (Exception e)
    {
      System.out.println("ERROR : " + e);
      e.printStackTrace(System.out);
    }
  }
}
