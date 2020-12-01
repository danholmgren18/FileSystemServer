package FileSystemApp;

/**
 * FileSystemApp/FileSystemPOA.java . Generated by the IDL-to-Java compiler
 * (portable), version "3.2" from FileSystem.idl Thursday, November 10, 2016
 * 2:45:54 PM EST
 */

public abstract class FileSystemPOA extends org.omg.PortableServer.Servant
		implements FileSystemApp.FileSystemOperations, org.omg.CORBA.portable.InvokeHandler
{

	// Constructors

	private static java.util.Hashtable<String, Integer> _methods = new java.util.Hashtable<String, Integer>();
	static
	{
		_methods.put("sayHello", new java.lang.Integer(0));
		_methods.put("shutdown", new java.lang.Integer(1));
		_methods.put("readFile", new java.lang.Integer(2));
		_methods.put("openFileLineNumber", new java.lang.Integer(3));
		_methods.put("openFileForWrite", new java.lang.Integer(4));
		_methods.put("updateFileAfterWrite", new java.lang.Integer(5));
	}

	/**
	 * @see org.omg.CORBA.portable.InvokeHandler#_invoke(java.lang.String, org.omg.CORBA.portable.InputStream, org.omg.CORBA.portable.ResponseHandler)
	 */
	public org.omg.CORBA.portable.OutputStream _invoke(String $method, org.omg.CORBA.portable.InputStream in,
			org.omg.CORBA.portable.ResponseHandler $rh)
	{
		org.omg.CORBA.portable.OutputStream out = null;
		java.lang.Integer __method = (java.lang.Integer) _methods.get($method);
		if (__method == null)
			throw new org.omg.CORBA.BAD_OPERATION(0, org.omg.CORBA.CompletionStatus.COMPLETED_MAYBE);

		switch (__method.intValue())
		{
		case 0: // FileSystemApp/FileSystem/sayHello
		{
			String $result = null;
			$result = this.sayHello();
			out = $rh.createReply();
			out.write_string($result);
			break;
		}

		case 1: // FileSystemApp/FileSystem/shutdown
		{
			this.shutdown();
			out = $rh.createReply();
			break;
		}

		case 2:
		{
			String fileTitle = in.read_string();
			System.out.println("starting to read file!!!!!!!! " + fileTitle);
			String $result = null;
			$result = this.readFile(fileTitle);
			out = $rh.createReply();
			out.write_string($result);
			break;
		}
		
		case 3:
		{
		  String fileTitle = in.read_string();
		  short lineNum = in.read_short();
		  String $result = null;
		  $result = this.openFileLineNumber(fileTitle, lineNum);
		  out = $rh.createReply();
		  out.write_string($result);
		  break;
		}
		
		case 4:
		{
      String fileTitle = in.read_string();
      short lineNum = in.read_short();
      String $result = null;
      $result = this.openFileForWrite(fileTitle, lineNum);
      out = $rh.createReply();
      out.write_string($result);
		  break;
		}
		
		case 5:
		{
		  String newLine = in.read_string();
      String fileTitle = in.read_string();
      short lineNum = in.read_short();
      String $result = null;
      $result = this.updateFileAfterWrite(newLine, fileTitle, lineNum);
      out = $rh.createReply();
      out.write_string($result);
		  break;
		}
		default:
			throw new org.omg.CORBA.BAD_OPERATION(0, org.omg.CORBA.CompletionStatus.COMPLETED_MAYBE);
		}

		return out;
	} // _invoke

	// Type-specific CORBA::Object operations
	private static String[] __ids =
	{ "IDL:FileSystemApp/FileSystem:1.0" };

	/**
	 * @see org.omg.PortableServer.Servant#_all_interfaces(org.omg.PortableServer.POA, byte[])
	 */
	public String[] _all_interfaces(org.omg.PortableServer.POA poa, byte[] objectId)
	{
		return (String[]) __ids.clone();
	}

	/**
	 * @return the FileSystem we are working with
	 */
	public FileSystem _this()
	{
		return FileSystemHelper.narrow(super._this_object());
	}

	/**
	 * @param orb our Corba object
	 * @return the FileSystem it contains
	 */
	public FileSystem _this(org.omg.CORBA.ORB orb)
	{
		return FileSystemHelper.narrow(super._this_object(orb));
	}

}
