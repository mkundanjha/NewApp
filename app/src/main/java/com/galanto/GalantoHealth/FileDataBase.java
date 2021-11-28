package com.galanto.GalantoHealth;

import android.Manifest;
import android.app.Activity;
import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.MediaStore;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.core.app.ActivityCompat;

import java.io.Console;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;

public class FileDataBase {
    Context context;
    private Settings settings=new Settings();

    public FileDataBase(Context context) {
        this.context=context;
    }

    public void  creteFolder(String foldername){
        try{
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                ContentValues values = new ContentValues();

                values.put(MediaStore.MediaColumns.DISPLAY_NAME, foldername);       //file name
                      //file extension, will automatically add to file
                values.put(MediaStore.MediaColumns.RELATIVE_PATH, Environment.DIRECTORY_DOCUMENTS + "/RehabRelive");     //end "/" is not mandatory

                context.getContentResolver().insert(MediaStore.Files.getContentUri("external"), values);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    //create new file
    public void createFile(String folderName, String fileName, String content) {
        try {

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {

                ContentValues values = new ContentValues();

                values.put(MediaStore.MediaColumns.DISPLAY_NAME, fileName);       //file name
                values.put(MediaStore.MediaColumns.MIME_TYPE, "application/json");        //file extension, will automatically add to file
                values.put(MediaStore.MediaColumns.RELATIVE_PATH, Environment.DIRECTORY_DOCUMENTS + "/"+folderName);     //end "/" is not mandatory

                Uri uri =context.getContentResolver().insert(MediaStore.Files.getContentUri("external"), values);      //important!
                OutputStream outputStream =context.getContentResolver().openOutputStream(uri    );
                outputStream.write(content.getBytes());
                outputStream.close();

                Toast.makeText(context, "File created successfully", Toast.LENGTH_SHORT).show();
            }
        } catch (Exception e) {
            Toast.makeText(context, "Fail to create file " + e.toString(), Toast.LENGTH_LONG).show();
        }
    }

    public boolean deleteFolder(String folderName){
        try{
            Uri contentUri = MediaStore.Files.getContentUri("external");
            String selection = MediaStore.MediaColumns.RELATIVE_PATH + "=?";
            String[] selectionArgs = new String[]{Environment.DIRECTORY_DOCUMENTS + "/"+folderName};
            ContentResolver resolver=context.getContentResolver();

            //Cursor cursor =context.getContentResolver().query(contentUri, null, selection, selectionArgs, null);
            resolver.delete(contentUri,selection,selectionArgs);
            return true;

        }catch (Exception ex){
            ex.printStackTrace();
            return false;
        }

    }

    // Read from file

    public String  readFile(String folderName, String readFileName){
        String returnJsonString="";

        try{
            Uri contentUri = MediaStore.Files.getContentUri("external");
            String selection = MediaStore.MediaColumns.RELATIVE_PATH + "=?";
            String[] selectionArgs = new String[]{Environment.DIRECTORY_DOCUMENTS + "/"+folderName+"/"};

            Cursor cursor =context.getContentResolver().query(contentUri, null, selection, selectionArgs, null);

            Uri uri = null;

            if (cursor.getCount() == 0) {
               // Toast.makeText(context, "No file found in \"" + Environment.DIRECTORY_DOCUMENTS + "/"+folderName+"\""+cursor.getCount(), Toast.LENGTH_LONG).show();
            } else {
                while (cursor.moveToNext()) {
                    int filenameTemp=cursor.getColumnIndex(MediaStore.MediaColumns.DISPLAY_NAME);
                    String fileName = cursor.getString(filenameTemp);

                    if (fileName.equals(readFileName)) {
                        int idTemp=cursor.getColumnIndex(MediaStore.MediaColumns._ID);
                        long id = cursor.getLong(idTemp);

                        uri = ContentUris.withAppendedId(contentUri, id);

                        break;
                    }
                }

                if (uri == null) {
                  //  Toast.makeText(context, "\""+readFileName+"\" not found", Toast.LENGTH_SHORT).show();
                } else {
                    try {
                        InputStream inputStream = context.getContentResolver().openInputStream(uri);

                        int size = inputStream.available();

                        byte[] bytes = new byte[size];

                        inputStream.read(bytes);

                        inputStream.close();

                        String jsonString = new String(bytes, StandardCharsets.UTF_8);
                        returnJsonString=jsonString;

                    } catch (IOException e) {
                        Toast.makeText(context.getApplicationContext(), "Fail to read file", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        cursor=null;
        }catch (Exception ex){
            Toast.makeText(context.getApplicationContext(),"Error: "+ex.toString(),Toast.LENGTH_SHORT).show();
        }
        return returnJsonString;
    }

    // Update content in the file
    public void updateFile(String folderName, String inpFileName, String content){
        Uri contentUri = MediaStore.Files.getContentUri("external");

        String selection = MediaStore.MediaColumns.RELATIVE_PATH + "=?";

        String[] selectionArgs = new String[]{Environment.DIRECTORY_DOCUMENTS + "/"+folderName+"/"};    //must include "/" in front and end

        Cursor cursor = context.getContentResolver().query(contentUri, null, selection, selectionArgs, null);

        Uri uri = null;

        if (cursor.getCount() == 0) {
            //createFile(folderName,folderName,content);
           // Toast.makeText(context, "No file found in \"" + Environment.DIRECTORY_DOCUMENTS + "/"+folderName+"/", Toast.LENGTH_LONG).show();
        } else {
            while (cursor.moveToNext()) {
                String fileName = cursor.getString(cursor.getColumnIndex(MediaStore.MediaColumns.DISPLAY_NAME));

                if (fileName.equals(inpFileName)) {                          //must include extension
                    long id = cursor.getLong(cursor.getColumnIndex(MediaStore.MediaColumns._ID));

                    uri = ContentUris.withAppendedId(contentUri, id);

                    break;
                }
            }

            if (uri == null) {
                //createFile(folderName,folderName,content);
                Toast.makeText(context, inpFileName+" not found", Toast.LENGTH_SHORT).show();
            } else {
                try {
                    OutputStream outputStream = context.getContentResolver().openOutputStream(uri, "rwt");      //overwrite mode, see below

                    outputStream.write(content.getBytes());

                    outputStream.close();

                    //Toast.makeText(context, "File updated successfully", Toast.LENGTH_SHORT).show();
                } catch (IOException e) {
                    Toast.makeText(context, "Fail to update file", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }

}



