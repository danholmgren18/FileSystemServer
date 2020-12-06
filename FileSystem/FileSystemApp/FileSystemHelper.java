package FileSystemApp;


/**
* FileSystemApp/FileSystemHelper.java .
* Generated by the IDL-to-Java compiler (portable), version "3.2"
* from FileSystem.idl
* Sunday, December 6, 2020 at 7:25:26 AM Eastern Standard Time
*/

abstract public class FileSystemHelper
{
  private static String  _id = "IDL:FileSystemApp/FileSystem:1.0";

  public static void insert (org.omg.CORBA.Any a, FileSystemApp.FileSystem that)
  {
    org.omg.CORBA.portable.OutputStream out = a.create_output_stream ();
    a.type (type ());
    write (out, that);
    a.read_value (out.create_input_stream (), type ());
  }

  public static FileSystemApp.FileSystem extract (org.omg.CORBA.Any a)
  {
    return read (a.create_input_stream ());
  }

  private static org.omg.CORBA.TypeCode __typeCode = null;
  synchronized public static org.omg.CORBA.TypeCode type ()
  {
    if (__typeCode == null)
    {
      __typeCode = org.omg.CORBA.ORB.init ().create_interface_tc (FileSystemApp.FileSystemHelper.id (), "FileSystem");
    }
    return __typeCode;
  }

  public static String id ()
  {
    return _id;
  }

  public static FileSystemApp.FileSystem read (org.omg.CORBA.portable.InputStream istream)
  {
    return narrow (istream.read_Object (_FileSystemStub.class));
  }

  public static void write (org.omg.CORBA.portable.OutputStream ostream, FileSystemApp.FileSystem value)
  {
    ostream.write_Object ((org.omg.CORBA.Object) value);
  }

  public static FileSystemApp.FileSystem narrow (org.omg.CORBA.Object obj)
  {
    if (obj == null)
      return null;
    else if (obj instanceof FileSystemApp.FileSystem)
      return (FileSystemApp.FileSystem)obj;
    else if (!obj._is_a (id ()))
      throw new org.omg.CORBA.BAD_PARAM ();
    else
    {
      org.omg.CORBA.portable.Delegate delegate = ((org.omg.CORBA.portable.ObjectImpl)obj)._get_delegate ();
      FileSystemApp._FileSystemStub stub = new FileSystemApp._FileSystemStub ();
      stub._set_delegate(delegate);
      return stub;
    }
  }

  public static FileSystemApp.FileSystem unchecked_narrow (org.omg.CORBA.Object obj)
  {
    if (obj == null)
      return null;
    else if (obj instanceof FileSystemApp.FileSystem)
      return (FileSystemApp.FileSystem)obj;
    else
    {
      org.omg.CORBA.portable.Delegate delegate = ((org.omg.CORBA.portable.ObjectImpl)obj)._get_delegate ();
      FileSystemApp._FileSystemStub stub = new FileSystemApp._FileSystemStub ();
      stub._set_delegate(delegate);
      return stub;
    }
  }

}
