import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Scanner;

import org.omg.CORBA.ORB;
import org.omg.CosNaming.NamingContextExt;
import org.omg.CosNaming.NamingContextExtHelper;

import FileSystemApp.FileSystem;
import FileSystemApp.FileSystemHelper;

/**
 * This is a stupid action just to show how things work ~ Merlin
 * 
 * This now is the action that is used to read a line from a file
 * 
 * @author Marlee & Dan & Josh
 *
 */
public class ReadAction extends MenuAction {
  static FileSystem fileSystemImpl;

  /**
   * 
   * First prompt the user to enter the name of the file and file number they want
   * to view
   */
  @Override
  void execute() {
    String[] arguments = { "java", "-Xmx10g", "-cp", ".:../../FileSystem/", "FileSystemApp.FileSystemClient",
        "-ORBInitialHost", "lsaremotede", "-ORBInitialPort", "1056", "-port", "1057" };
    
    // loop to walk through all 3 servers (clipper, Germany, Spain)
    Scanner scanner;
    int count = 0;
    String fileName, fileContents = null;
    int fileFound = -1; // -1 means no file found, 0 means found on clipper, 1 means found in germany, 2 means found in spain
    String localServer = "";
    Scanner userScan = new Scanner(System.in);
    
    // Ask user for file
    System.out.println("Enter the name of the file:");
    fileName = userScan.nextLine();
    try {
      scanner = new Scanner(new File("../Servers.txt"));
      
      
      while (scanner.hasNextLine())
      {
        // First find what server you are on then update the arugments[] array with the correct server name argument
        String serverArgument = ""; // will hold the correct sever argument (ex. lsaremotede)
        String theLine = scanner.nextLine(); // stores the line with server location, argument, and server address (seperated by spaces)
        String [] tokens = theLine.split(" ");
       
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
        
        
        fileContents = fileSystemImpl.openFileForRead(fileName);
        
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
    
    if (fileFound == 0) { //This means that the file was found locally and we dont need to create a new file for it
      // out of loop, print file contents
      System.out.println("File " + fileName + "\n" + fileContents);
      
    } else if (fileFound != -1) { // This means the file was found but not on our local server
      Path currentRelativePath = Paths.get("");
      String newFileLoc = currentRelativePath.toAbsolutePath().toString() + "/../../FileSystemServer/Files/";
      
      // create file locally
      File file = new File(newFileLoc + "/" +fileName);
      
      // use a FileWriter to fill the file with the fileContents
      FileWriter myWriter;
      BufferedWriter output = null;
      try {
//        myWriter = new FileWriter(file);
//        myWriter.write(fileContents);
//        myWriter.close();
      
        output = new BufferedWriter(new FileWriter(file));
        output.write(fileContents);
        
      } catch (IOException e1) {
        System.out.println("Unable to write the File");
      }
      
      
      arguments[6] = localServer;
      
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
      
      // call method createLocalFile() to make sure the server adds the file to ListOfLocalFiles
      fileSystemImpl.createLocalFile(fileName);
      
      // print the contents of the file to the user
      System.out.println("File " + fileName + "\n" + fileContents);
      
      
      
    } else { // file not found on any Servers
      System.out.println("Cound not locate file " + fileName + "on any server!");
    }
    
    /*
     * Option for user to close read
     */
    //fileSystemImpl.closeRead(fileName);
    
    
  }
}
  
      
      
      
    
//      try {
//    
//      // create and initialize the ORB
//      ORB orb = ORB.init(arguments, null);
//
//      // get the root naming context
//      org.omg.CORBA.Object objRef = orb.resolve_initial_references("NameService");
//      // Use NamingContextExt instead of NamingContext. This is
//      // part of the Interoperable naming Service.
//      NamingContextExt ncRef = NamingContextExtHelper.narrow(objRef);
//
//      // resolve the Object Reference in Naming
//      String name = "FileSystem";
//      fileSystemImpl = FileSystemHelper.narrow(ncRef.resolve_str(name));
//    } catch (Exception e) {
//      System.out.println("ERROR : " + e);
//      e.printStackTrace(System.out);
//    }
//    Scanner scan = new Scanner(System.in);
//    String fileName;
//    String lineNumber;
//    int lineNum; // converts the user input (string) into a usable integer
//
//    // prompt the user to enter the name of the file and line number they want to
//    // read
//    System.out.println("Enter the name of the file:");
//    fileName = scan.nextLine();
//    System.out.println(fileSystemImpl.openFileForRead(fileName));
//    System.out.println("Enter the line number you would like to see:");
//    lineNumber = scan.nextLine();
//
//    // check that lineNumber is an int
//    try {
//      lineNum = Integer.valueOf(lineNumber);
//    } catch (NumberFormatException e) {
//      System.out.println("Error: Please enter an integer value for a line number!");
//      lineNum = scan.nextInt();
//      // TODO: Make it so it loops until they give you an integer
//    }
//
//    // check that file exists TODO
//
//    System.out.println("Name: " + fileName + "  Line: " + lineNum);
//
//    // read the file
//    try {
//      FileReader fr = new FileReader(fileName);
//      int currentLine = 0;
//      while (currentLine < lineNum) { // skip to the correct line in the file
//        currentLine++;
//      }
//
//      // get line from file and store into a string
//      String line = "";
//
////      int i;
////      while ((i=fr.read()) != -1) 
////       line = line + (char) i; 
//
//      // Print out the line number
//      System.out.println(line);
//
//    } catch (IOException e) {
//      System.out.println("Error: Please enter a file name that exists.");
//      fileName = scan.nextLine();
//    }
//    scan.close();
//  }
//
//}
