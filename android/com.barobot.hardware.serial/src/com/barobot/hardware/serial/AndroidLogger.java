package com.barobot.hardware.serial;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintStream;
import java.text.SimpleDateFormat;
import java.util.Date;

import android.os.Environment;
import android.util.Log;

import com.barobot.common.constant.Constant;
import com.barobot.common.interfaces.CanLog;

public class AndroidLogger implements CanLog {
	File myFile;
	FileOutputStream fos;

	public AndroidLogger() {
		File external = Environment.getExternalStorageDirectory();
		String sdcardPath = external.getPath();
		File dir = new File(sdcardPath + "/Barobot");
		dir.mkdirs();
        myFile = new File(sdcardPath + "/Barobot/barobot.error.txt");
        if(!myFile.exists()){
			try {
				myFile.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
        }
        try {
			fos = new FileOutputStream(myFile);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} 
	}

	@Override
	public void println(int priority, String tag, String msg) {
		Log.println(priority, tag, msg);
	}

	@Override
	public void d(String tag, String msg) {
		Log.d( tag, msg);
	}

	@Override
	public void d(String tag, String msg, Throwable tr) {
		Log.d( tag, msg, tr);
		appendError( tr );
	}

	@Override
	public void e(String tag, String msg) {
		Log.e( tag, msg);
	}

	@Override
	public void e(String tag, String msg, Throwable tr) {
		Log.e( tag, msg, tr);
		appendError( tr );
	}

	@Override
	public void i(String tag, String msg) {
		Log.i( tag, msg);
	}

	@Override
	public void i(String tag, String msg, Throwable tr) {
		Log.i( tag, msg, tr);
		appendError( tr );
	}

	@Override
	public void v(String tag, String msg) {
		Log.v( tag, msg);
	}

	@Override
	public void v(String tag, String msg, Throwable tr) {
		Log.v( tag, msg, tr);
		appendError( tr );
	}

	@Override
	public void w(String tag, Throwable tr) {
		Log.w( tag, tr);
		appendError( tr );
	}

	@Override
	public void w(String tag, String msg, Throwable tr) {
		Log.w( tag, msg, tr);
		appendError( tr );
	}

	@Override
	public void w(String tag, String msg) {
		Log.w( tag, msg);
	}

	@Override
	public void appendError(Throwable tr) {
		tr.printStackTrace();
		if( fos!= null ){
			PrintStream ps = new PrintStream(fos);  
			try {
	            tr.printStackTrace( ps );
	        } catch (Exception e) {
	        } finally {
	        	 ps.close();
	        }
		}
		String rendered = tr.getMessage() + "\n----\n" + tr.getStackTrace();
		appendError(rendered);
	}

	public void appendError( String s ) {
		try {
            FileOutputStream fOut = new FileOutputStream(myFile);
            OutputStreamWriter myOutWriter = new OutputStreamWriter(fOut);
            myOutWriter.append(s);
            myOutWriter.close();
            fOut.close();
        } catch (Exception e) {
        }
	}

	private static SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss:SSS");
	@Override
	public void saveLog(String text){ 
		String path6 = 	Environment.getExternalStorageDirectory()+ Constant.logFile;
		File logFile = new File(path6);
		if (!logFile.exists()) {
			try {
				logFile.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		try {
			Date date = new Date();
			BufferedWriter buf = new BufferedWriter(new FileWriter(logFile,true));
			buf.append(dateFormat.format(date));
			buf.append("\t");
			buf.append(text);
			buf.newLine();
			buf.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public String getSavedLog() {	
		String path6 = 	Environment.getExternalStorageDirectory()+ Constant.logFile;
		try {
			return getStringFromFile(path6);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "";
	}

	public static String convertStreamToString(InputStream is) throws Exception {
	    BufferedReader reader = new BufferedReader(new InputStreamReader(is));
	    StringBuilder sb = new StringBuilder();
	    String line = null;
	    while ((line = reader.readLine()) != null) {
	      sb.append(line).append("\n");
	    }
	    reader.close();
	    return sb.toString();
	}

	public static String getStringFromFile (String filePath) throws Exception {
	    File fl = new File(filePath);
	    
	    FileInputStream fin;
		try {
			fin = new FileInputStream(fl);
		} catch (FileNotFoundException e) {
			return "";
		}
	    String ret = convertStreamToString(fin);
	    fin.close();        
	    return ret;
	}
	
	
}
