package com.thangtien.firstapp.ultil;

import android.app.ActivityManager;
import android.content.ContentResolver;
import android.content.Context;
import android.content.res.XmlResourceParser;
import android.database.Cursor;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.widget.Toast;

import com.thangtien.firstapp.model.ProductType;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class FileUtil {
    static String TAG = "FileUtil";

    public static ArrayList<String> readFromFile(Context context, String fileName) {
        ArrayList<String> result = new ArrayList<>();
        InputStream inputStream = context.getResources().openRawResource(context.getResources().getIdentifier(fileName, "raw", context.getPackageName()));
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));

        String line;
        try {
            while ((line = bufferedReader.readLine()) != null) {
                Log.i(TAG, "line = " + line);
                result.add(line);
            }
        } catch (IOException e) {
            // Xử lý ngoại lệ nếu cần
            Log.i(TAG, "IOException: " + e);
        } finally {
            try {
                inputStream.close();
            } catch (IOException e) {
                // Xử lý ngoại lệ nếu cần
                Log.i(TAG, "IOException: " + e);
            }
        }
        return result;
    }

    public static ArrayList<ProductType> readXmlFile(Context context, String fileName) {
        ArrayList<ProductType> result = new ArrayList<>();
        int resId = context.getResources().getIdentifier(fileName, "xml", context.getPackageName());
        Log.i(TAG, "resId : " + resId);
        if (resId == 0) {
            Log.i(TAG, "Not found file");
            return result;
        } else {
            XmlResourceParser parser = context.getResources().getXml(resId);

            try {
                parser.next();
                int eventType = parser.getEventType();

                while (eventType != XmlPullParser.END_DOCUMENT) {
                    if (eventType == XmlPullParser.START_TAG) {
                        String tagName = parser.getName();
                        if (tagName.equals("item")) {
                            int id = 0;
                            String name = "";
                            String image = "";

                            // Đọc các thông tin từ thẻ con của <item>
                            while (parser.next() != XmlPullParser.END_TAG) {
                                if (parser.getEventType() != XmlPullParser.START_TAG) {
                                    continue;
                                }
                                String innerTagName = parser.getName();
                                if (innerTagName.equals("id")) {
                                    id = Integer.parseInt(readText(parser));
                                } else if (innerTagName.equals("name")) {
                                    name = readText(parser);
                                } else if (innerTagName.equals("image")) {
                                    image = readText(parser);
                                }
                            }

                            // In ra thông tin mỗi mục
                            Log.d(TAG, "id:  " + id + "Name: " + name + ", Image: " + image);
                            result.add(new ProductType(id, name, image));
                        }
                    }
                    eventType = parser.next();
                }
            } catch (XmlPullParserException | IOException e) {
                e.printStackTrace();
            } finally {
                parser.close();
            }
        }

        return result;
    }

    // Phương thức để đọc nội dung văn bản từ một thẻ XML
    private static String readText(XmlPullParser parser) throws IOException, XmlPullParserException {
        String result = "";
        if (parser.next() == XmlPullParser.TEXT) {
            result = parser.getText();
            parser.nextTag();
        }
        return result;
    }

    public static void toast_short(Context context, String message) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
    }

    public static boolean isServiceRunning(Context context, Class<?> serviceClass) {
        ActivityManager manager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        if (manager != null) {
            List<ActivityManager.RunningServiceInfo> runningServices = manager.getRunningServices(Integer.MAX_VALUE);
            for (ActivityManager.RunningServiceInfo service : runningServices) {
                if (serviceClass.getName().equals(service.service.getClassName())) {
                    return true;
                }
            }
        }
        return false;
    }

    public static List<Uri> getImagesAndVideos(Context context) {
        String externalStorageDirectory = Environment.getExternalStorageDirectory().getAbsolutePath();

        // Get the path to the Picture directory
        String pictureDirectoryPath = externalStorageDirectory + "/Pictures";

        // Get the content resolver
        ContentResolver contentResolver = context.getContentResolver();

        // Define the columns to retrieve
        String[] projection = {MediaStore.MediaColumns._ID};

        // Query the image collection
        Uri imageCollectionUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
        Cursor imageCursor = contentResolver.query(imageCollectionUri, projection, null, null, null);

        // Add the images to a list
        List<Uri> images = new ArrayList<>();
        if (imageCursor != null) {
            while (imageCursor.moveToNext()) {
                long imageId = imageCursor.getLong(imageCursor.getColumnIndexOrThrow(MediaStore.MediaColumns._ID));
                Uri imageUri = Uri.withAppendedPath(imageCollectionUri, String.valueOf(imageId));

                // Check if the image is in the Picture directory
                String imagePath = imageUri.getPath();
                Log.i(TAG, "imagePath: " + imagePath);
                if (imagePath.startsWith(pictureDirectoryPath)) {
                    images.add(imageUri);
                }
            }
            imageCursor.close();
        }

        return images;
    }

    public static List<Uri> getImagesFromInternalStorage(Context context) {
        List<Uri> imageUris = new ArrayList<>();
        String[] projection = {MediaStore.Images.Media._ID};
        String selection = MediaStore.Images.Media.DATA + " like ?";
        String[] selectionArgs = new String[]{"%" + Environment.getExternalStorageDirectory().getAbsolutePath() + "%"};
        Cursor cursor = context.getContentResolver().query(
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                projection,
                selection,
                selectionArgs,
                null
        );
        if (cursor != null) {
            while (cursor.moveToNext()) {
                long imageId = cursor.getLong(cursor.getColumnIndexOrThrow(MediaStore.Images.Media._ID));
                Uri imageUri = Uri.withAppendedPath(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, String.valueOf(imageId));
                imageUris.add(imageUri);
            }
            cursor.close();
        }
        Log.i(TAG, "getImagesFromInternalStorage size: " + imageUris.size());
        return imageUris;
    }


}
