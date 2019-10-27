package com.appbtl.appweather;

import android.content.Context;
import android.content.ContextWrapper;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;

public class IOFile {
    String data;
    FileOutputStream outputStream;
    FileInputStream inputStream;
        protected void saveFile(String filename,String data,Context context){

            try {
                outputStream = context.openFileOutput(filename,Context.MODE_PRIVATE);
                outputStream.write(data.getBytes());
                outputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        protected String readFile(String filename,Context context){


            try {
                inputStream = context.openFileInput(filename);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            DataInputStream in = new DataInputStream(inputStream);
                BufferedReader br = new BufferedReader(new InputStreamReader(in));
                StringBuilder sb = new StringBuilder();
            String s= null;
            try{
            while((s= br.readLine())!= null)  {
                sb.append(s).append("\n");
            }
            data = sb.toString();
            }catch (Exception e){
                e.printStackTrace();
            }


            return data;

}
}
