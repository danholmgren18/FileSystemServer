import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.StringTokenizer;

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
  private ArrayList<String> tokens = new ArrayList<String>();

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
      System.out.println("I HAVE ENTERED THE TRY BLOCK");
      String filePath = Paths.get("").toAbsolutePath().toString();
      String current = "MenuStateMachine";
      String destination = "Servers.txt";
      int startIndex = filePath.indexOf(current);
      int stopIndex = startIndex + current.length();
      StringBuilder builder = new StringBuilder(filePath);
      builder.delete(startIndex, stopIndex);
      builder.append(destination);
      scanner = new Scanner(new File(builder.toString()));
      System.out.println("I HAVE SCANNED SERVERS.TXT");

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

    // modify a copy of the file so it won't print out version # and # of readers to
    // the user
    String truncatedContents = ""; // holds file with version # and # of readers taken out
    short readerNum = -1; // intitalized to -1 so it's easy to tell if something goes wrong
    short versionNum = -1; // intitalized to -1 so it's easy to tell if something goes wrong

    String[] fileLines = new String[fileContents.length()]; // make an array to hold each line
    fileLines = fileContents.split("\n");
    for (int i = 0; i < fileLines.length; i++) {
      if (i == 0) { // if i = 0, you are on the version # line
        versionNum = (short) fileLines[i].charAt(0);
      } else if (i == 1) { // if i = 1, you are on the # of readers line
        readerNum = (short) fileLines[i].charAt(0);

      } else { // if not first two cases, you are on the regular contents of the file that the
               // user can see
        truncatedContents = truncatedContents + fileLines[i] + "\n";
      }
    } 
    
    if (fileFound == 0) { // This means that the file was found locally and we dont need to create a new
      // file for it
// out of loop, print file contents
      System.out.println("File " + fileName + "\n" + truncatedContents);

    } else if (fileFound != -1) { // This means the file was found but not on our local server
      fileCreator(truncatedContents, fileName);

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

// call method createLocalFile() to make sure the server adds the file to
// ListOfLocalFiles
      fileSystemImpl.createLocalFile(fileName, readerNum, versionNum);
      fileSystemImpl.lockForWrite(fileName);

// print the contents of the file to the user
      System.out.println("File " + fileName + "\n" + truncatedContents);

    } else { // file not found on any Servers
      System.out.println("Cound not locate file " + fileName + " on any server!");
    }

    StringTokenizer st = new StringTokenizer(truncatedContents, "\n");
    while(st.hasMoreTokens()) {
      tokens.add(st.nextToken());
  }
    /**
     * Block of code to change a line and then update that file with new line
     */
    System.out.println("Enter the line number you would like to change (0 is line one)");
    Scanner writeScanner = new Scanner(System.in);
    int userLineNum = writeScanner.nextInt();
    System.out.println("Enter what the new line will be");
    Scanner writeScannerTwo = new Scanner(System.in);
    String newLine = writeScannerTwo.nextLine();
    tokens.set(userLineNum, newLine);
    
    String newContents = "";
    for(String n : tokens) {
      newContents = newContents + n + "\n";
    }
    
    System.out.println("Start of new contents:\n" + newContents + "\nAHHHHHHHHHHHHHHHHHHHHHHHHHHHHH");

    //fileCreator(newContents, fileName);
    try {
      FileWriter fw = new FileWriter(fileName, false);
      fw.write(newContents);
    } catch (IOException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
    writeScanner.close();
    System.out.println(fileSystemImpl.closeWrite(fileName, newContents.substring(0, newContents.length() - 1)));

  }

  private void fileCreator(String fileContents, String fileName) {
    try {
      String filePath = Paths.get("").toAbsolutePath().toString();
      String current = "MenuStateMachine";
      String destination = "FileSystemServer/Files/";
      int startIndex = filePath.indexOf(current);
      int stopIndex = startIndex + current.length();
      StringBuilder builder = new StringBuilder(filePath);
      builder.delete(startIndex, stopIndex);
      builder.append(destination);
      File newFile = new File(builder + "/" + fileName);
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
