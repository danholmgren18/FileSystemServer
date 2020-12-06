import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardOpenOption;
import java.util.Scanner;

import org.omg.CORBA.ORB;
import org.omg.CosNaming.NamingContextExt;
import org.omg.CosNaming.NamingContextExtHelper;

import FileSystemApp.FileSystem;
import FileSystemApp.FileSystemHelper;

/**
 * This action gets some information from the user and does something with it
 * 
 * @author merlin
 *
 */
public class WriteAction extends MenuAction {
  static FileSystem fileSystemImpl;

  /**
   * {@inheritDoc}
   */
  @Override
  void execute() {
    String[] arguments = { "java", "-Xmx10g", "-cp", ".:../../FileSystem/", "FileSystemApp.FileSystemClient",
        "-ORBInitialHost", "lsaremotede", "-ORBInitialPort", "1056", "-port", "1057" };

    // loop to walk through all 3 servers (clipper, Germany, Spain)
    Scanner scanner;
    int count = 0;
    String fileName, fileContents = null;
    int fileFound = -1; // -1 means no file found, 0 means found on clipper, 1 means found in germany, 2
                        // means found in spain
    String localServer = "";
    Scanner userScan = new Scanner(System.in);

    // Ask user for file
    System.out.println("Enter the name of the file:");
    fileName = userScan.nextLine();
    try {
      scanner = new Scanner(new File("../Servers.txt"));

      while (scanner.hasNextLine()) {
        // First find what server you are on then update the arugments[] array with the
        // correct server name argument
        String serverArgument = ""; // will hold the correct sever argument (ex. lsaremotede)
        String theLine = scanner.nextLine(); // stores the line with server location, argument, and server address
                                             // (seperated by spaces)
        String[] tokens = theLine.split(" ");

        // update the arugments[] array with the correct server name
        serverArgument = tokens[1];
        arguments[6] = serverArgument;
        if (count == 0) {
          localServer = serverArgument;
        }
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

        fileContents = fileSystemImpl.openFileForWrite(fileName);

        // Step 3: see if file is local

        if (fileContents.equals("File Not Here")) { // condition: if file is located on the server
          System.out.println("File not on : " + serverArgument);
        } else { // if the file is found, break from loop and continue to file actions
          System.out.println("File found in " + tokens[0]);
          fileFound = count; // ex. if cound = 2, then you cound the file in Spain
          break;
        }
        count++;
      }
    } catch (FileNotFoundException e1) {
      System.out.println("Error! Servers.txt file is unreachable");
    }
    if (fileFound == 0) { // This means that the file was found locally and we dont need to create a new
      // file for it
// out of loop, print file contents
      System.out.println("File " + fileName + "\n" + fileContents);

    } else if (fileFound != -1) { // This means the file was found but not on our local server
      fileCreator(fileContents, fileName);

      arguments[6] = localServer;

// create an orb using local server argument to update the server with the new
// local file
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

      short peepRead = 2;
      short verNo = 8;
// call method createLocalFile() to make sure the server adds the file to
// ListOfLocalFiles
      fileSystemImpl.createLocalFile(fileName, peepRead, verNo);

// print the contents of the file to the user
      System.out.println("File " + fileName + "\n" + fileContents);

    } else { // file not found on any Servers
      System.out.println("Cound not locate file " + fileName + " on any server!");
    }

    /*
     * Option for user to edit the txt
     * and then closeWrite
     */
  fileSystemImpl.closeWrite(fileName);

  }

  private void fileCreator(String fileContents, String fileName) {
    try {
      File newFile = new File("/home/jk7045/eclipse-workspace/Project4/project4swe/FileSystemServer/Files/" + fileName);
      if (newFile.createNewFile()) {
        System.out.println("Created: " + fileName + " locally");
      } else {
        System.out.println("Error: Could not create file");
      }
      Files.write(newFile.toPath(), fileContents.getBytes(), StandardOpenOption.APPEND);
      System.out.println("Successfully wrote to the file.");
    } catch (IOException e) {
      System.out.println("An error occurred.");
      e.printStackTrace();
    }
  }
}
