package com.it.test;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;

import com.it.exception.SimpleException;

public class test1 {

	public static void main(String[] args) {
		File file = new File("C:\\Users\\Administrator\\Desktop\\hello.txt");
		File file1 = new File("C:\\Users\\Administrator\\Desktop\\hello1.txt");
		BufferedReader br = null;
		BufferedWriter bw = null;
		try {
			br = new BufferedReader(new InputStreamReader(new FileInputStream(file)));
			bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file1)));
		} catch (FileNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		String str = null;
		try {
			while ((str = br.readLine()) != null) {
				System.out.println(str);
				bw.write(str);
			}
			br.close();
			bw.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		/*try {
			InputStream is  = new FileInputStream(file);
			OutputStream os = new FileOutputStream("C:\\\\Users\\\\Administrator\\\\Desktop\\\\hello1.txt");
			int len = 0;
			try {
				while ((len = is.read()) != -1) {
					System.out.println((char)len);
					os.write(len);
				}
				is.close();
				os.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/
		
	}
}
