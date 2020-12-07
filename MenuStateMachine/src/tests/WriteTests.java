package tests;

import static org.junit.Assert.assertNotEquals;
import static org.junit.jupiter.api.Assertions.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

import org.junit.jupiter.api.Test;
import org.omg.CORBA.ORB;
import org.omg.CosNaming.NamingContextExt;
import org.omg.CosNaming.NamingContextExtHelper;

import FileSystemApp.FileSystem;
import FileSystemApp.FileSystemHelper;

class WriteTests {

  @Test
  void testOpenFileForWrite() {
    Scanner scanner = null;
    try {
      scanner = new Scanner(new File("../Servers.txt"));
    } catch (FileNotFoundException e) {
      fail("Servers file wasnt found");
    }
    /*
     * Servers.txt :
     * Local 
     * Spain
     * Germany
     */
    int count = 0;
    while (scanner.hasNextLine()) {
      String[] tokens = scanner.nextLine().split(" ");
      FileSystem Impl = makeConnection(tokens[1]);
      String contents = Impl.openFileForWrite("Taco.txt");
      /*
       * Taco.txt should be in Spain so...
       */
      if (count == 0) {
        assertEquals("File Not Here", contents); //When looking in local server, it should not find it
      }else if (count == 1) {
        assertEquals("File Not Here", contents); //When looking in German server, it should not find it
      }else if (count == 2) {
        assertNotEquals("File Not Here", contents); //When looking in Spain, it should find it
      }
    }  
    
  //Once it has opened the file for write the server should have it show as locked 
    FileSystem Impl = makeConnection("lsaremotees");
    String fileInfo = Impl.retreiveInfo("Taco.txt");
    String[] fileInfoSplit = fileInfo.split(" ");
    int retrievedVersion = Integer.parseInt(fileInfoSplit[0]);
    int retrievedReaders = Integer.parseInt(fileInfoSplit[1]);
    String retrievedIsLocked = fileInfoSplit[2];
    assertEquals(0, retrievedVersion);
    assertEquals(0, retrievedReaders);
    assertEquals("True", retrievedIsLocked);
    // closeWrite for Taco.txt
    Impl.closeWrite("Taco.txt", "Yah yeet");
    String fileInfoDos = Impl.retreiveInfo("Taco.txt");
    String[] fileInfoSplitDos = fileInfoDos.split(" ");
    int retrievedVersionDos = Integer.parseInt(fileInfoSplitDos[0]);
    int retrievedReadersDos = Integer.parseInt(fileInfoSplitDos[1]);
    String retrievedIsLockedDos = fileInfoSplitDos[2];
    assertEquals(0, retrievedVersionDos);
    assertEquals(0, retrievedReadersDos); 
    assertEquals("False", retrievedIsLockedDos);
  }

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
}
