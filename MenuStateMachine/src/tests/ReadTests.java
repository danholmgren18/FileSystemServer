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

class ReadTests {

  String[] arguments = { "java", "-Xmx10g", "-cp", ".:../../FileSystem/", "FileSystemApp.FileSystemClient",
      "-ORBInitialHost", "", "-ORBInitialPort", "1056", "-port", "1057" };

  @Test
  void testOpenForRead() {
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
      String contents = Impl.openFileForRead("Taco.txt");
      /*
       * Taco.txt should be in Spain so...
       */
      if (count == 0) {
        assertEquals("File Not Here", contents); //When looking in local server, it should not find it
      }else if (count == 1) {
        assertNotEquals("File Not Here", contents); //When looking in Spain, it should find it
        break;
      }else if (count == 2) {
        fail(); //It should find it in Spain so it should never get here
      }
      count ++;
    }
    //Once it has opened the file for read the server should have it show as one person reading 
    FileSystem Impl = makeConnection("lsaremotees");
    String fileInfo = Impl.retreiveInfo("Taco.txt");
    String[] fileInfoSplit = fileInfo.split(" ");
    int retrievedVersion = Integer.parseInt(fileInfoSplit[0]);
    int retrievedReaders = Integer.parseInt(fileInfoSplit[1]);
    assertEquals(0, retrievedVersion);
    assertEquals(1, retrievedReaders);
    // closeRead for Taco.txt
    Impl.closeRead("Taco.txt");
    String fileInfoDos = Impl.retreiveInfo("Taco.txt");
    String[] fileInfoSplitDos = fileInfoDos.split(" ");
    int retrievedVersionDos = Integer.parseInt(fileInfoSplitDos[0]);
    int retrievedReadersDos = Integer.parseInt(fileInfoSplitDos[1]);
    assertEquals(0, retrievedVersionDos);
    assertEquals(0, retrievedReadersDos); //And readers should go back to Zero
  }

  void testCantOpenIfLocked() {
    FileSystem Impl = makeConnection("lsaremotees");
    String fileInfo = Impl.retreiveInfo("Taco.txt");
    String[] fileInfoSplit = fileInfo.split(" ");
    int retrievedVersion = Integer.parseInt(fileInfoSplit[0]);
    int retrievedReaders = Integer.parseInt(fileInfoSplit[1]);
    String retrievedIsLocked = fileInfoSplit[2];
    assertEquals(0, retrievedVersion);
    assertEquals(0, retrievedReaders);
    assertEquals("False", retrievedIsLocked);
    Impl.openFileForWrite("Taco.txt");
    
    assertEquals("File Not Here", Impl.openFileForRead("Taco.txt"));
    Impl.closeWrite("Taco.txt");
    String fileInfoDos = Impl.retreiveInfo("Taco.txt");
    String[] fileInfoSplitDos = fileInfo.split(" ");
    int retrievedVersionDos = Integer.parseInt(fileInfoSplitDos[0]);
    int retrievedReadersDos = Integer.parseInt(fileInfoSplitDos[1]);
    String retrievedIsLockedDos = fileInfoSplitDos[2];
    assertEquals(0, retrievedVersionDos);
    assertEquals(0, retrievedReadersDos);
    assertEquals("False", retrievedIsLockedDos);
  }
 
  void testDistributedInfo() {
    FileSystem Impl = makeConnection("clipper");
    String fileInfo = Impl.retreiveInfo("Mutant.txt");
    String[] fileInfoSplit = fileInfo.split(" ");
    int retrievedVersion = Integer.parseInt(fileInfoSplit[0]);
    int retrievedReaders = Integer.parseInt(fileInfoSplit[1]);
    String retrievedIsLocked = fileInfoSplit[2];
    assertEquals(0, retrievedVersion);
    assertEquals(0, retrievedReaders);
    assertEquals("False", retrievedIsLocked);
    
    FileSystem ImplDos = makeConnection("lsaremotede");
    String fileInfoDos = ImplDos.retreiveInfo("Mutant.txt");
    String[] fileInfoSplitDos = fileInfoDos.split(" ");
    int retrievedVersionDos = Integer.parseInt(fileInfoSplitDos[0]);
    int retrievedReadersDos = Integer.parseInt(fileInfoSplitDos[1]);
    String retrievedIsLockedDos = fileInfoSplitDos[2];
    assertEquals(0, retrievedVersionDos);
    assertEquals(0, retrievedReadersDos);
    assertEquals("False", retrievedIsLockedDos);
    
    FileSystem ImplTres = makeConnection("lsaremotees");
    String fileInfoTres = ImplTres.retreiveInfo("Mutant.txt");
    String[] fileInfoSplitTres = fileInfoTres.split(" ");
    int retrievedVersionTres = Integer.parseInt(fileInfoSplitTres[0]);
    int retrievedReadersTres = Integer.parseInt(fileInfoSplitTres[1]);
    String retrievedIsLockedTres = fileInfoSplitTres[2];
    assertEquals(0, retrievedVersionTres);
    assertEquals(0, retrievedReadersTres);
    assertEquals("False", retrievedIsLockedTres);
    
    FileSystem newImpl = makeConnection("lsaremotede");
    newImpl.openFileForRead("Mutant.txt");
    String newInfo = newImpl.retreiveInfo("Mutant.txt");
    String[] newfileInfoSplit = newInfo.split(" ");
    int newretrievedVersion = Integer.parseInt(newfileInfoSplit[0]);
    int newretrievedReaders = Integer.parseInt(newfileInfoSplit[1]);
    String newretrievedIsLocked = newfileInfoSplit[2];
    assertEquals(0, newretrievedVersion);
    assertEquals(1, newretrievedReaders);
    assertEquals("False", newretrievedIsLocked);
    
    FileSystem newImplDos = makeConnection("lsaremotede");
    newImplDos.openFileForRead("Mutant.txt");
    String newInfoDos = newImpl.retreiveInfo("Mutant.txt");
    String[] newfileInfoSplitDos = newInfoDos.split(" ");
    int newretrievedVersionDos = Integer.parseInt(newfileInfoSplitDos[0]);
    int newretrievedReadersDos = Integer.parseInt(newfileInfoSplitDos[1]);
    String newretrievedIsLockedDos = newfileInfoSplitDos[2];
    assertEquals(0, newretrievedVersionDos);
    assertEquals(1, newretrievedReadersDos);
    assertEquals("False", newretrievedIsLockedDos);
    
    FileSystem newImplTres = makeConnection("lsaremotede");
    newImplTres.openFileForRead("Mutant.txt");
    String newInfoTres = newImpl.retreiveInfo("Mutant.txt");
    String[] newfileInfoSplitTres = newInfoTres.split(" ");
    int newretrievedVersionTres = Integer.parseInt(newfileInfoSplitTres[0]);
    int newretrievedReadersTres = Integer.parseInt(newfileInfoSplitTres[1]);
    String newretrievedIsLockedTres = newfileInfoSplitTres[2];
    assertEquals(0, newretrievedVersionTres);
    assertEquals(1, newretrievedReadersTres);
    assertEquals("False", newretrievedIsLockedTres);
  }
  
  private FileSystem makeConnection(String whichServer) {

    // First find what server you are on then update the arugments[] array with the
    // correct server name argument
    FileSystem fileSystemImpl = null;
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
