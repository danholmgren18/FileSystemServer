package FileSystemApp;

import java.io.File;
import java.io.FileNotFoundException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Scanner;

import org.omg.CORBA.ORB;
import org.omg.CosNaming.NameComponent;
import org.omg.CosNaming.NamingContextExt;
import org.omg.CosNaming.NamingContextExtHelper;
import org.omg.PortableServer.POA;
import org.omg.PortableServer.POAHelper;

class FileSystemImpl extends FileSystemPOA {
  private ORB orb;
  private ArrayList<FileInstance> listOfLocalFiles = new ArrayList<FileInstance>();

  public FileSystemImpl() {
    // Initialize listOfLocalFiles
    Path currentRelativePath = Paths.get("");
    String s = currentRelativePath.toAbsolutePath().toString() + "/..";
    File f = new File(s + "/Files");
    String[] files = f.list();

    // Populates the array with names of files and directories
    for (int i = 0; i < files.length; i++) {
      listOfLocalFiles.add(new FileInstance(new File(s + "/Files/" + files[i])));
    }
  }

  public void setORB(ORB orb_val) {
    orb = orb_val;
  }

  // implement sayHello() method
  public String sayHello() {
    return "\nHello world !!\n";
  }

  // implement shutdown() method
  public void shutdown() {
    orb.shutdown(false);
  }

  @Override
  public String readFile(String title) {
    try {
      Scanner s = new Scanner(new File(title));
      StringBuffer contents = new StringBuffer("");
      while (s.hasNext()) {
        contents.append(s.nextLine() + "\n");
      }

      s.close();
      return contents.toString();
    } catch (FileNotFoundException e) {
      return "File Not Here";
    }
  }

  @Override
  public String openFileForRead(String title) {

    FileInstance targetFile = fileFinder(title);
    if(targetFile != null && targetFile.isLocked()) {
      return "File currently locked";
    }
    if (targetFile == null) {
      return "File Not Here";
    }
    String targetFileContents = targetFile.getContents();
    /**
     * Loops through servers in Servers.txt and calls startReadLocally on each in
     * turn
     */
    try {
      Scanner scanner = new Scanner(new File("../../Servers.txt"));
      System.out.println("RIGHTHERE!!!!!!");
      while (scanner.hasNextLine()) {
        String[] tokens = scanner.nextLine().split(" "); // Takes in the next line of the file and splits it at tokens

        // update the arugments[] array with the correct server name
        FileSystem fileSystemImpl = makeConnection(tokens[1]);
        if (fileSystemImpl.startReadLocally(title).equals("Failed")) {
          return "Failed in " + tokens[0];
        }
      }
      scanner.close();
    } catch (FileNotFoundException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }

    /**
     * Version#
     * Reader#
     * fileContents
     */
    targetFileContents = fileFinder(title).getVersion() + "\n" 
        + fileFinder(title).getAmntOfPeopleReading() + "\n" + targetFileContents;
    return targetFileContents;
  }

  @Override
  public String openFileForWrite(String title) {
    FileInstance targetFile = fileFinder(title);
    if(targetFile != null && targetFile.isLocked()) {
      return "File currently locked";
    }
    if (targetFile == null) {
      return "File Not Here";
    }
    String targetFileContents = targetFile.getContents();

    /**
     * Loops through servers in Servers.txt and calls lockForWrite on each in turn
     */
    try {
      Scanner scanner = new Scanner(new File("../../Servers.txt"));
      int lineNum = 0;
      while (scanner.hasNextLine()) {
        String[] tokens = scanner.nextLine().split(" "); // Takes in the next line of the file and splits it at tokens

        // update the arugments[] array with the correct server name
        FileSystem fileSystemImpl = makeConnection(tokens[1]);
        if (fileSystemImpl.lockForWrite(title).equals("Failed")) {
          // If it fails to lock one of the servers we need to unlock any that we might
          // have already locked
          int lineNumDos = 0;
          try {
            Scanner scannerDos = new Scanner(new File("../../Servers.txt"));
            while (scannerDos.hasNextLine() || lineNumDos <= lineNum) {
              String[] tokensDos = scannerDos.nextLine().split(" "); // Takes in the next line of the file and splits it
                                                                     // at tokens
              // update the arugments[] array with the correct server name
              FileSystem fileSystemImplDos = makeConnection(tokensDos[1]);
              fileSystemImplDos.unlockLocally(title);
              lineNumDos++;
            }
            scannerDos.close();
          } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
          }
          return "Failed in " + tokens[0];
        }
        lineNum++;
      }
      scanner.close();
    } catch (FileNotFoundException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
    /**
     * Version#
     * Reader#
     * FileContents
     */
    targetFileContents = fileFinder(title).getVersion() + "\n" 
        + fileFinder(title).getAmntOfPeopleReading() + "\n" + targetFileContents;
    return targetFileContents;
  }

  @Override
  public String closeRead(String title) {

    /**
     * Loops through servers in Servers.txt and calls stopReadLocally on each in
     * turn
     */
    try {
      Scanner scanner = new Scanner(new File("../../Servers.txt"));
      while (scanner.hasNextLine()) {
        String[] tokens = scanner.nextLine().split(" "); // Takes in the next line of the file and splits it at tokens

        // update the arugments[] array with the correct server name
        FileSystem fileSystemImpl = makeConnection(tokens[1]);
        if (fileSystemImpl.stopReadLocally(title).equals("Failed")) {
          return "Failed in " + tokens[0];
        }
      }
      scanner.close();
    } catch (FileNotFoundException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }

    return "Success";
  }

  @Override
  public String closeWrite(String title, String newContents) {

    /**
     * Loops through servers in Servers.txt and calls startReadLocally on each in
     * turn
     */
    try {
      String filePath = Paths.get("").toAbsolutePath().toString();
      String current = "FileSystemServer";
      String destination = "FileSystemServer/Files/";
      int startIndex = filePath.indexOf(current);
      int stopIndex = startIndex + current.length();
      StringBuilder builder = new StringBuilder(filePath);
      builder.delete(startIndex, stopIndex);
      builder.append(destination);
      Scanner scanner = new Scanner(new File(builder.toString()));
      while (scanner.hasNextLine()) {
        String[] tokens = scanner.nextLine().split(" "); // Takes in the next line of the file and splits it at tokens

        // update the arugments[] array with the correct server name
        FileSystem fileSystemImpl = makeConnection(tokens[1]);
//        if (!fileSystemImpl.updateLocalFile(title, newContents).equals("Success")){
//          return "Failed in " + tokens[0];
//        }
//        if (fileSystemImpl.unlockLocally(title).equals("Failed")) {
//          return "Failed in " + tokens[0];
//        }
      }
      scanner.close();
    } catch (FileNotFoundException e) {
      return "Servers.txt not Found in close Write";
    }
    return "Success";
  }

  /*
   * Locks the local file. Called by A server
   */
  @Override
  public String lockForWrite(String title) {
    if (fileFinder(title).isLocked()) {
      return "Failed";
    } else {
      fileFinder(title).setLocked(true);
      return "Success";
    }
  }

  @Override
  public String unlockLocally(String title) {
    fileFinder(title).setLocked(false);
    return "Success";
  }

  @Override
  public String stopReadLocally(String title) {
    fileFinder(title).stopReading();
    return "Success";
  }

  @Override
  public String startReadLocally(String title) {
    fileFinder(title).startReading();
    return "Success";
  }

  @Override
  public boolean createLocalFile(String title, short readers, short version) {
    Path currentRelativePath = Paths.get("");
    String s = currentRelativePath.toAbsolutePath().toString() + "/..";

    listOfLocalFiles.add(new FileInstance(new File(s + "/Files/" + title), readers, version));
    return true;
  }

  /**
   * 
   * @param whichServer
   * @return the FileSystemImpl for the server to connect to server in the list
   */
  private FileSystem makeConnection(String whichServer) {
    FileSystem fileSystemImpl = null;
    String[] arguments = { "java", "-Xmx10g", "-cp", ".:../../FileSystem/", "FileSystemApp.FileSystemClient",
        "-ORBInitialHost", "", "-ORBInitialPort", "1056", "-port", "1057" };
    // First find what server you are on then update the arugments[] array with the
    // correct server name argument
    arguments[6] = whichServer;
    // Step 1:create orb
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
      System.out.println("ERROR : " + e);
      e.printStackTrace(System.out);
    }
    return fileSystemImpl;
  }

  /**
   * Searches the list listOfLocalFiles for the requested file
   * 
   * @param title The title of the file
   * @return the FileInstance of the file
   */
  public FileInstance fileFinder(String title) {
    for (FileInstance n : listOfLocalFiles) {
      if (n.getTitle().equals(title)){
        return n;
      }
    }
    return null;
  }

  /**
   * a method for testing, retrieves the amntOfPeopleReading and version and isLocked according to this server
   */
  @Override
  public String retreiveInfo(String title) {
    FileInstance targetFile = fileFinder(title);
    if (targetFile == null) {
      return "File Not Here";
    }
    return fileFinder(title).getVersion() + " " + fileFinder(title).getAmntOfPeopleReading() + " " + fileFinder(title).isLocked();
    
  }

  @Override
  public String updateLocalFile(String title, String newContents) {
    FileInstance file = fileFinder(title);
    if (file == null) {
      return "File Not Here";
    }
    file.setContents(newContents);
    return "Success";
  }
}

/**
 * This is the class that runs on the server
 * 
 * @author merlin
 *
 */
public class FileSystemServer {

  /**
   * @param args ignored
   */
  public static void main(String args[]) {
    try {
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

    catch (Exception e) {
      System.err.println("ERROR: " + e);
      e.printStackTrace(System.out);
    }

    System.out.println("FileSystemServer Exiting ...");

  }
}
