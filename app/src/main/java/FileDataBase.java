import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.MediaStore;
import android.widget.Toast;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;

public class FileDataBase {
    Context context;
    public FileDataBase(Context context) {
        this.context=context;
    }

    private void createFile(String folderName, String fileName, String content) {
        try {

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {

                ContentValues values = new ContentValues();

                values.put(MediaStore.MediaColumns.DISPLAY_NAME, fileName);       //file name
                values.put(MediaStore.MediaColumns.MIME_TYPE, "application/json");        //file extension, will automatically add to file
                values.put(MediaStore.MediaColumns.RELATIVE_PATH, Environment.DIRECTORY_DOCUMENTS + "/"+folderName);     //end "/" is not mandatory

                Uri uri =context.getContentResolver().insert(MediaStore.Files.getContentUri("external"), values);      //important!

                OutputStream outputStream =context.getContentResolver().openOutputStream(uri);

                outputStream.write(content.getBytes());

                outputStream.close();

                Toast.makeText(context, "File created successfully", Toast.LENGTH_SHORT).show();
            }
        } catch (Exception e) {
            Toast.makeText(context, "Fail to create file " + e.toString(), Toast.LENGTH_LONG).show();
        }
    }

    private String  readFile(String folderName,String readFileName){
        String returnJsonString="";

        try{
            Uri contentUri = MediaStore.Files.getContentUri("external");

            String selection = MediaStore.MediaColumns.RELATIVE_PATH + "=?";

            String[] selectionArgs = new String[]{Environment.DIRECTORY_DOCUMENTS + "/Galanto/"};

            Cursor cursor =context.getContentResolver().query(contentUri, null, selection, selectionArgs, null);

            Uri uri = null;
            //Toast.makeText(getApplicationContext(), "files count:"+cursor.getCount(), Toast.LENGTH_LONG).show();

            if (cursor.getCount() == 0) {
                Toast.makeText(context, "No file found in \"" + Environment.DIRECTORY_DOCUMENTS + "/"+folderName+"\""+cursor.getCount(), Toast.LENGTH_LONG).show();
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
                    Toast.makeText(context, "\""+readFileName+"\" not found", Toast.LENGTH_SHORT).show();
                } else {
                    try {
                        InputStream inputStream = context.getContentResolver().openInputStream(uri);

                        int size = inputStream.available();

                        byte[] bytes = new byte[size];

                        inputStream.read(bytes);

                        inputStream.close();

                        String jsonString = new String(bytes, StandardCharsets.UTF_8);
                        returnJsonString=jsonString;
                        Toast.makeText(context.getApplicationContext(),jsonString,Toast.LENGTH_LONG).show();

                    } catch (IOException e) {
                        Toast.makeText(context.getApplicationContext(), "Fail to read file", Toast.LENGTH_SHORT).show();
                    }
                }
            }

        }catch (Exception ex){
            Toast.makeText(context.getApplicationContext(),"Error: "+ex.toString(),Toast.LENGTH_SHORT).show();
        }

        return returnJsonString;
    }


}
