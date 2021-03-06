package org.telosys.tools.commons;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import junit.env.telosys.tools.commons.TestsEnv;
import junit.framework.TestCase;

import org.telosys.tools.commons.FileUtil;

public class FileUtilTest extends TestCase {

	public void testCopyWithFileName() throws IOException, Exception {
		
		String fileName = "/testfilecopy/origin/file1.txt" ;
		System.out.println("Searching file '" + fileName + "' by classpath..." );
		File file = FileUtil.getFileByClassPath(fileName);
		if ( file.exists() ) {
			System.out.println("File found : " + file);
			System.out.println(" . getAbsolutePath()  : " + file.getAbsolutePath() );
			System.out.println(" . getCanonicalPath() : " + file.getCanonicalPath() );
			System.out.println(" . getName()          : " + file.getName() );
			System.out.println(" . getPath()          : " + file.getPath() );
			System.out.println(" . getParent()        : " + file.getParent() );
		}
		else {
			System.out.println("File not found " );
		}
		assertTrue ( file.exists()) ;
		
		// Original file
		String originalFullFileName = file.getAbsolutePath();
		System.out.println("Original file    : " + originalFullFileName );

		// Destination file in inexistent folder 
		String destFullFileName = FileUtil.buildFilePath(file.getParentFile().getParent()+"/newfolder", "newfile1.txt");
		System.out.println("Destination file : " + destFullFileName );
		
		FileUtil.copy(originalFullFileName, destFullFileName, true);
	}
	
	public void testCopyWithFileObject() throws IOException, Exception {
		
//		//String sourceFileName = TestsFolders.getFullFileName("file1.txt");
//		String sourceFileName = TestsFolders.getTestFileAbsolutePath("file1.txt");
//		//String destinationFileName = TestsFolders.getFullFileName("file1-bis.txt");
//		String destinationFileName = TestsFolders.getTmpExistingFile("file1-bis.txt");
//		System.out.println("Copy from file '" + sourceFileName + "' to '" + destinationFileName + "'" );

		//File sourceFile = new File(sourceFileName);
		File sourceFile = TestsEnv.getTestFile("file1.txt");
		//File destinationFile = new File(destinationFileName);
		File destinationFile = TestsEnv.getTmpFile("file1-bis.txt");
		System.out.println("Copy from file '" + sourceFile.getAbsolutePath() + "' to '" + destinationFile.getAbsolutePath() + "'" );
		assertTrue ( sourceFile.exists()) ;
		
		FileUtil.copy(sourceFile, destinationFile, true);
	}
	
	public void testCopyWithFileObject2() throws IOException, Exception {
		
//		//String sourceFileName = TestsFolders.getFullFileName("file1.txt");
//		String sourceFileName = TestsFolders.getTestFileAbsolutePath("file1.txt");
//		String destinationFileName = TestsFolders.getFullFileName("foo/bar/file1-bis.txt");
//		System.out.println("Copy from file '" + sourceFileName + "' to '" + destinationFileName + "'" );

		//File sourceFile = new File(sourceFileName);
		File sourceFile = TestsEnv.getTestFile("file1.txt");
		//File destinationFile = new File(destinationFileName);
		File destinationFile = TestsEnv.getTmpFile("foo/bar/file1-bis.txt");
		System.out.println("Copy from file '" + sourceFile.getAbsolutePath() + "' to '" + destinationFile.getAbsolutePath() + "'" );
		assertTrue ( sourceFile.exists()) ;
		
		boolean exception = false ;
		try {
			// Copy to non existent destination, without 'create folder' flag
			FileUtil.copy(sourceFile, destinationFile, false);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			exception = true ;
		}
		assertTrue(exception);
	}
	
	public void testCopyStringToFile() throws Exception {
		
		File destinationFile = TestsEnv.getTmpFile("foo/bar2/file-copy-from-string.txt");
		System.out.println("Copy string content to '" + destinationFile.getAbsolutePath() + "'" );
		
		String content = "This is the first line \n" +
				"line 2 aaaaaaa\n" +
				"line 3 bbbbbb " ;
			
		FileUtil.copy(content, destinationFile, true);
		assertTrue(destinationFile.exists());

//	boolean exception = false ;
//	try {
//		FileUtil.copy(content, destinationFile, false);
//	} catch (Exception e) {
//		exception = true ;
//	}
//	assertFalse(exception);
	}


	public void testCopyFileToDirectory() throws IOException, Exception {
		
		//String sourceFileName = TestsFolders.getFullFileName("file1.txt");
		String sourceFileName = TestsEnv.getTestFileAbsolutePath("file1.txt");
		//String destinationDirectoryName = TestsFolders.getFullFileName("mydir/dest");
		String destinationDirectoryName = TestsEnv.getTmpExistingFolderFullPath("mydir/dest");
		System.out.println("Copy from file '" + sourceFileName + "' to '" + destinationDirectoryName + "'" );

		//File sourceFile = new File(sourceFileName);
		File sourceFile = TestsEnv.getTestFile("file1.txt");
		File destinationDirectory = new File(destinationDirectoryName);
		System.out.println("Copy from file '" + sourceFile.getAbsolutePath() + "' to '" + destinationDirectory.getAbsolutePath() + "'" );
		assertTrue ( sourceFile.exists()) ;
		
		FileUtil.copyToDirectory(sourceFile, destinationDirectory, true);
	}
	
	public void testFolderCopy() throws IOException, Exception {
		
		String folderName = "/testfilecopy" ;
		System.out.println("Searching folder '" + folderName + "' by classpath..." );
		File folder = FileUtil.getFileByClassPath(folderName);
		if ( folder.exists() ) {
			System.out.println("Folder found : " + folder);
			System.out.println(" . getAbsolutePath()  : " + folder.getAbsolutePath() );
			System.out.println(" . getCanonicalPath() : " + folder.getCanonicalPath() );
			System.out.println(" . getName()          : " + folder.getName() );
			System.out.println(" . getPath()          : " + folder.getPath() );
			System.out.println(" . getParent()        : " + folder.getParent() );
		}
		else {
			System.out.println("Folder not found " );
		}
		assertTrue ( folder.exists()) ;
		
		for ( String fileName : folder.list() ) {
			System.out.println(" . " + fileName );
		}
	
		for ( File file : folder.listFiles() ) {
			System.out.println(" . " + file );
			if ( "origin".equals( file.getName() ) ) {
				System.out.println("'origin' folder found.");
				File originFolder = file ;
				
				File destinationFolder = new File(folder.getAbsolutePath(), "dest");
				FileUtil.copyFolder(originFolder, destinationFolder, false) ;
			}
		}
	}
	
	public void testCopyFileFromMetaInf() throws Exception {
		
		Exception exception = null ;
		try {
			testCopyFileFromMetaInf("resource-file0.txt");
		} catch (Exception e) {
			exception = e ;
			System.out.println("Expected exception : " + e.getMessage() );
			assertNotNull(exception);
		}
		
		testCopyFileFromMetaInf("resource-file1.txt"); 

		testCopyFileFromMetaInf("dir2/resource-file2.txt"); 
	}
	
	private void testCopyFileFromMetaInf(String srcFileName) throws Exception {

		System.out.println("-----");
		System.out.println("Test with '" +srcFileName+"'");
		final String destFolderName = "/copied-from-metainf" ;
		String destFileFullPath   = TestsEnv.getTmpFileOrFolderFullPath(destFolderName + "/" + srcFileName );
		String destFolderFullPath = TestsEnv.getTmpFileOrFolderFullPath(destFolderName);

		//--- Initial state : no directory
		DirUtil.deleteDirectory( new File(destFolderFullPath) ) ;
		
		//--- 1rst try to copy : folder doesn't exist => exception expected
		System.out.println("Copying from META-INF : " + srcFileName + " to " + destFileFullPath);
		Exception exception = null ;
		try {
			FileUtil.copyFileFromMetaInf(srcFileName, destFileFullPath, false);
		} catch (Exception e) {
			exception = e ;
			System.out.println("Expected exception : " + e.getMessage() );
		}
		assertNotNull(exception);
		
		//--- 2nd try to copy : folder doesn't exist but flag is 'create folder' => no exception expected
		FileUtil.copyFileFromMetaInf(srcFileName, destFileFullPath, true);
		checkFileExistence(destFileFullPath);
		
		//--- 3nd try to copy : copy same file again
		FileUtil.copyFileFromMetaInf(srcFileName, destFileFullPath, false);
		checkFileExistence(destFileFullPath);
	}

	//------------------------------------------------------------------------------------------
	// Utilities
	//------------------------------------------------------------------------------------------
	private void checkFileExistence(String fileName)  {
		File file = new File(fileName);
		if ( file.exists() ) {
			System.out.println("File found : " + file);
			System.out.println(" . getAbsolutePath()  : " + file.getAbsolutePath() );
			//System.out.println(" . getCanonicalPath() : " + file.getCanonicalPath() );
			System.out.println(" . getName()          : " + file.getName() );
			System.out.println(" . getPath()          : " + file.getPath() );
			System.out.println(" . getParent()        : " + file.getParent() );
		}
		else {
			System.out.println("File not found " );
		}
		assertTrue ( file.exists()) ;
	}
	
	public void testBuildFilePath1()  {
		String dir = "D:\\workspaces\\runtime-EclipseApplication\\myapp/TelosysTools/templates/front-springmvc-TT210-R2/resources" ;
		String file = "/src/main/webapp" ;
		String s = FileUtil.buildFilePath(dir, file);
		System.out.println("s = " + s );
		assertEquals(dir+file, s);
	}
	
	public void testBuildFilePath2a()  {
		String dir = "D:\\aaa\\bbb/ccc/ddd/" ;
		String file = "/xxx/yyy/zzz.txt" ;
		String s = FileUtil.buildFilePath(dir, file);
		System.out.println("s = " + s );
		assertEquals(dir+"xxx/yyy/zzz.txt", s);
	}
	public void testBuildFilePath2b()  {
		String dir = "D:\\aaa\\bbb/ccc" ;
		String file = "/xxx/yyy/zzz.txt" ;
		String s = FileUtil.buildFilePath(dir, file);
		System.out.println("s = " + s );
		assertEquals("D:\\aaa\\bbb/ccc/xxx/yyy/zzz.txt", s);
	}

	public void testBuildFilePath3()  {
		String dir = "D:\\aaa\\bbb/ccc/ddd" ;
		String file = "/xxx/yyy/zzz.txt" ;
		String s = FileUtil.buildFilePath(dir, file);
		System.out.println("s = " + s );
		assertEquals(dir+file, s);
	}
	public void testBuildFilePath4()  {
		String dir = "D:\\aaa\\bbb/ccc/ddd" ;
		String file = "\\xxx/yyy/zzz.txt" ;
		String s = FileUtil.buildFilePath(dir, file);
		System.out.println("s = " + s );
		assertEquals(dir+"/xxx/yyy/zzz.txt", s);
	}
	public void testBuildFilePath5()  {
		String dir = "aaaa" ;
		String file = "foo.txt" ;
		String s = FileUtil.buildFilePath(dir, file);
		System.out.println("s = " + s );
		assertEquals("aaaa/foo.txt", s);
	}


	public void testReadNull()  {
		Exception exception = null ;
		try {
			FileUtil.read(null);
		} catch (Exception e) {
			System.out.println("Exception : " + e.getMessage());
			exception = e ;
		}
		assertNotNull(exception);
	}
	
	public void testReadFileNotFound()  {
		Exception exception = null ;
		try {
			FileUtil.read(new File("xxx/yyy/zzz/toto.txt"));
		} catch (Exception e) {
			System.out.println("Exception : " + e.getMessage());
			exception = e ;
		}
		assertNotNull(exception);
	}
	
	public void testRead1()  {
		String fileName = "files/file1.txt" ;
		System.out.println("read file " + fileName);
		File file = TestsEnv.getTestFile(fileName);
		System.out.println("file.getParent() : " + file.getParent() );
		System.out.println("file.getName()   : " + file.getName() );
		byte[] content = new byte[0];
		try {
			content = FileUtil.read(file);
		} catch (Exception e) {
			e.printStackTrace();
		}
		printFileContent(content);
		assertEquals(file.length(), content.length);
	}
	
	public void testRead2()  {
		String fileName = "files/file2.txt" ;
		System.out.println("read file " + fileName);
		File file = TestsEnv.getTestFile(fileName);
		byte[] content = new byte[0];
		try {
			content = FileUtil.read(file);
		} catch (Exception e) {
			e.printStackTrace();
		}
		printFileContent(content);
		assertEquals(file.length(), content.length);
	}
	
	private void printFileContent(byte[] content)  {
		System.out.println("File content :");
		for ( byte b : content ) {
			System.out.print((char)b);
		}
	}
}
