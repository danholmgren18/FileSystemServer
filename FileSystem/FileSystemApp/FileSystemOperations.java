package FileSystemApp;


/**
* FileSystemApp/FileSystemOperations.java .
* Generated by the IDL-to-Java compiler (portable), version "3.2"
* from FileSystem.idl
* Thursday, December 3, 2020 3:46:40 PM EST
*/

public interface FileSystemOperations 
{
  String sayHello ();
  String readFile (String title);
  void shutdown ();
  String openFileForRead (String title);
  String openFileForWrite (String title);
  String closeRead (String title);
  String closeWrite (String title);
  String lockForWrite (String title);
  String unlockLocally (String title);
  String stopReadLocally (String title);
} // interface FileSystemOperations
