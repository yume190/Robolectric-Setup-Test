package yume190.com.tester10;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigInteger;
import java.net.URI;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class YumeFile extends File{

	/**
	 * Auto Generate UID
	 */
	private static final long serialVersionUID = 7507847856944951331L;

	public YumeFile(File dir, String name) {
		super(dir, name);
	}

	public YumeFile(String path) {
		super(path);
	}

	public YumeFile(String dirPath, String name) {
		super(dirPath, name);
	}

	public YumeFile(URI uri) {
		super(uri);
	}

	public int size(){
		return (int)this.length();
	}
	
	public String checksum(){
		try{
			return checksumWithThrowException();
		}catch (NullPointerException e) {
			return "";
		}
	}
	
	@SuppressWarnings("resource")
	public String checksumWithThrowException() throws NullPointerException{
        try {
            return YumeFile.checksum(new FileInputStream(this));
        } catch (FileNotFoundException e) {
            throw new NullPointerException();
        }
    }

    public static String checksum(InputStream is) throws NullPointerException{
        String checksum;
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");

            //Using MessageDigest update() method to provide input
            byte[] buffer = new byte[8192];
            int numOfBytesRead;
            while( (numOfBytesRead = is.read(buffer)) > 0){
                md.update(buffer, 0, numOfBytesRead);
            }
            byte[] hash = md.digest();
            checksum = new BigInteger(1, hash).toString(16);
            while ( checksum.length() < 32 ) {
                checksum = "0"+checksum;
            }
            is.close();

            return checksum;
        } catch (NoSuchAlgorithmException | IOException e) {
            throw new NullPointerException();
        }
    }
	
	public JSONArray parseJsonArray() throws JSONException, IOException{
		StringBuffer fileData = new StringBuffer();
        BufferedReader reader = new BufferedReader(new FileReader(this));
        char[] buf = new char[1024];
        int numRead=0;
        while((numRead = reader.read(buf)) != -1){
            String readData = String.valueOf(buf, 0, numRead);
            fileData.append(readData);
        }
        reader.close();
        
		return new JSONArray(fileData.toString());
	}
	
	public boolean checkFileExist(){
		return this.exists() && this.isFile();
	}
	
	public boolean checkFileSize(int expectations){
		return this.size() == expectations;
	}
	
	public void ensureDirectory(){
		if (!this.exists()){
			this.mkdirs();
		}
	}
}
