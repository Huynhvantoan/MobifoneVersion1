package com.toan_itc.tn.Utils;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.net.ConnectivityManager;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.CursorLoader;
import android.text.TextUtils;
import android.util.Base64;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.WindowManager;

import com.afollestad.materialdialogs.MaterialDialog;
import com.toan_itc.tn.R;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by toan on mobi10.20.15.
 */
public class Utils {
    private static int screenWidth = 0;
    private static int screenHeight = 0;

    public static int dpToPx(int dp) {
        return (int) (dp * Resources.getSystem().getDisplayMetrics().density);
    }
    public static boolean isEmailValid(String email) {
        //TODO: Replace this with your own logic
        return email.contains("@");
    }
    public static boolean isPasswordValid(String password) {
        //TODO: Replace this with your own logic
        return password.length() > 3;
    }

    public static int getScreenHeight(Context c) {
        if (screenHeight == 0) {
            DisplayMetrics dm = c.getResources().getDisplayMetrics();
            screenHeight = dm.heightPixels;
        }

        return screenHeight;
    }

    public static int getScreenWidth(Context c) {
        if (screenWidth == 0) {
            DisplayMetrics dm = c.getResources().getDisplayMetrics();
            screenWidth = dm.widthPixels;
        }
        return screenWidth;
    }
    public static MaterialDialog show_progress(final Context context,int content) {
        MaterialDialog materialDialog=null;
        try{
            materialDialog=new MaterialDialog.Builder(context)
                    .title(R.string.app_name)
                    .content(content)
                    .autoDismiss(true)
                    .progress(true, 0)
                    .show();
        }catch (Exception e){
            e.printStackTrace();
        }
        return materialDialog;
    }
    public static MaterialDialog showdialog_error(final Context context,final String message) {
        MaterialDialog materialDialog=null;
        try{
            materialDialog=new MaterialDialog.Builder(context)
                .title("Lỗi!")
                .content(message)
                    .autoDismiss(true)
                .cancelable(false)
                .positiveText("OK")
                .onPositive((materialDialog1, dialogAction) -> materialDialog1.dismiss())
                    .buttonRippleColorRes(R.color.primary_light)
                    .positiveColorRes(R.color.primary_dark)
                .iconRes(R.mipmap.ic_launcher)
                .show();
        }catch (Exception e){
            e.printStackTrace();
        }
        return materialDialog;
    }
    /**
     * Check validate email
     *
     * @param email
     * @return
     */
    public static boolean isValidateEmail(String email) {
        Pattern pattern;
        Matcher matcher;
        String EMAIL_PATTERN = "^[_A-Za-z0-9-]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
        pattern = Pattern.compile(EMAIL_PATTERN);
        matcher = pattern.matcher(email);
        return matcher.matches();

    }
    public static boolean isValidDate(String inDate) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.US);
        dateFormat.setLenient(false);
        try {
            dateFormat.parse(inDate.trim());
        } catch (ParseException pe) {
            return false;
        }
        return true;
    }
    public static boolean isAndroid5() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP;
    }
    public static boolean isAndroid6() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.M;
    }
    public static String getFormatDateStr(final Date date){
        if(null == date)return null;
        return DateFormat.getDateInstance(DateFormat.DEFAULT).format(date);
    }

    public static void toFragment(@NonNull Fragment fragment, @NonNull Fragment target, int frameId){
        try{
            FragmentManager fragmentManager= fragment.getFragmentManager();
            FragmentTransaction transition=fragmentManager.beginTransaction()
                    .replace(frameId, target, target.getClass().getName());
            transition.addToBackStack(null);
            transition.commit();
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    public static void toChildFragment(@NonNull Fragment fragment,@NonNull Fragment target,@Nullable int frameId){
        try{
            fragment.getChildFragmentManager()
                    .beginTransaction()
                    .replace(frameId, target, target.getClass().getName()).commit();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public static Date formatDateFromStr(final String dateStr){
        Date date = new Date();
        if(!TextUtils.isEmpty(dateStr)){
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.sss'Z'");
            try {
                date = sdf.parse(dateStr);
            }catch (Exception e){
                System.out.print("Error,format Date error");
            }
        }
        return date;
    }
    public static String getStringFromBitmap(final Bitmap bitmap) {
        String s = null;
        try {
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);
            byte[] byteArray = byteArrayOutputStream .toByteArray();
            s=Base64.encodeToString(byteArray, Base64.NO_WRAP);
        } catch (Throwable e) {
            e.printStackTrace();
        }
        return s;
    }
    public String getRealPath(Context context,Uri uri) {
        Cursor cursor = null;
        try {
            String[] proj = { MediaStore.Video.Media.DATA };
            cursor = context.getContentResolver().query(uri, proj, null, null, null);
            int column_index = cursor.getColumnIndexOrThrow(MediaStore.Video.Media.DATA );
            cursor.moveToFirst();
            return cursor.getString(column_index);
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
    }

    private String getRealPathFromURI(Uri contentUri,Context context) {
        String[] proj = { MediaStore.Images.Media.DATA };
        CursorLoader loader = new CursorLoader(context, contentUri, proj, null,
                null, null);
        Cursor cursor = loader.loadInBackground();
        int column_index = cursor
                .getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        cursor.moveToFirst();
        String result = cursor.getString(column_index);
        cursor.close();
        return result;
    }
    public static String getPath(final Uri uri, Activity context) {

        String[] projection = { MediaStore.Images.Media.DATA };

        Cursor cursor = context.managedQuery(uri, projection, null, null, null);

        int columnIndex = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);

        cursor.moveToFirst();

        return cursor.getString(columnIndex);
    }
    public static String getPath(Context context,Uri uri) {
        Cursor cursor=null;
        try {
            String[] projection = {MediaStore.Images.Media.DATA};
            cursor = context.getContentResolver().query(uri, projection, null, null, null);
            if (cursor != null) {
                int column_index = cursor
                        .getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
                cursor.moveToFirst();
                return cursor.getString(column_index);
            } else
                return null;
        }finally {
            if (cursor != null) {
                cursor.close();
            }
        }
    }
    private static int calculateInSampleSize(
            BitmapFactory.Options options, int reqWidth, int reqHeight) {
        // Raw height and width of image
        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;
        if (height > reqHeight || width > reqWidth) {
            final int halfHeight = height / 2;
            final int halfWidth = width / 2;
            while ((halfHeight / inSampleSize) > reqHeight
                    && (halfWidth / inSampleSize) > reqWidth) {
                inSampleSize *= 2;
            }
        }
        return inSampleSize;
    }
    private static Bitmap decodeSampledBitmapFromResource(Resources res, int resId,int reqWidth, int reqHeight) {
        // First decode with inJustDecodeBounds=true to check dimensions
        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeResource(res, resId, options);
        // Calculate inSampleSize
        options.inSampleSize = calculateInSampleSize(options, reqWidth, reqHeight);
        // Decode bitmap with inSampleSize set
        options.inJustDecodeBounds = false;
        return BitmapFactory.decodeResource(res, resId, options);
    }
    public static Bitmap decodeSampledBitmapFromString(String m_photoPath,int reqWidth, int reqHeight) {
        Bitmap bitmap;
        //decode File and set inSampleSize
        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(m_photoPath, options);
        options.inSampleSize = calculateInSampleSize_String(options, reqWidth, reqHeight);
        // decode File with inSampleSize set
        options.inJustDecodeBounds = false;
        bitmap = BitmapFactory.decodeFile(m_photoPath, options);
        return bitmap;
    }

    //calculate bitmap sample sizes
    private static int calculateInSampleSize_String(BitmapFactory.Options options, int reqWidth, int reqHeight) {
        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;
        if (height > reqHeight || width > reqWidth) {
            if (width > height) {
                inSampleSize = Math.round((float) height / (float) reqHeight);
            } else {
                inSampleSize = Math.round((float) width / (float) reqWidth);
            }
        }
        return inSampleSize;
    }
    public static Bitmap ResizeBitmap(String m_photoPath,int max_width,int max_height){
        int outWidth=0;
        int outHeight=0;
        Bitmap bitmap=null;
        try {
            bitmap = BitmapFactory.decodeFile(m_photoPath);
            int inWidth = bitmap.getWidth();
            int inHeight = bitmap.getHeight();
            if (inWidth > max_width) {
                outWidth = max_width;
            } else {
                outWidth = inWidth;
            }
            if (inHeight > max_height) {
                outHeight = max_height;
            } else {
                outHeight = inHeight;
            }
        }catch (Exception e){
            e.printStackTrace();
            bitmap = BitmapFactory.decodeFile(m_photoPath);
            outWidth=960;
            outHeight=760;
        }
        return Bitmap.createScaledBitmap(bitmap, outWidth, outHeight, false);
    }
    public static Bitmap getBitmapFromString(final Context ctx, final String s) {
        Bitmap myBitmap = null;
        try {
            if (s == null) {
                return null;
            }
            byte[] bytes = null;
            bytes = Base64.decode(s, Base64.DEFAULT);
            Display display = ((WindowManager) ctx.getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay();

            int sclWidth = display.getWidth() / 2;
            int sclHeight = display.getHeight() / 2;

            BitmapFactory.Options opt = new BitmapFactory.Options();
            opt.inSampleSize = 1;
            opt.inJustDecodeBounds = true;

            BitmapFactory.decodeByteArray(bytes, 0, bytes.length, opt);

            if (opt.outWidth > sclWidth) {
                opt.inSampleSize = opt.outWidth / sclWidth;
            } else if (opt.outHeight > sclHeight) {
                opt.inSampleSize = opt.outHeight / sclHeight;
            }

            opt.inJustDecodeBounds = false;
            myBitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length, opt);
            // myBitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
            opt.requestCancelDecode();
            opt = null;
            bytes = null;
            System.gc();
        } catch (OutOfMemoryError e) {
            // getBitmapFromString(s);
            System.gc();
        }
        return myBitmap;
    }
    public Bitmap rotateBitmapOrientation(String photoFilePath) {
        // Create and configure BitmapFactory
        BitmapFactory.Options bounds = new BitmapFactory.Options();
        bounds.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(photoFilePath, bounds);
        BitmapFactory.Options opts = new BitmapFactory.Options();
        Bitmap bm = BitmapFactory.decodeFile(photoFilePath, opts);
        // Read EXIF ApiController
        ExifInterface exif = null;
        try {
            exif = new ExifInterface(photoFilePath);
        } catch (IOException e) {
            e.printStackTrace();
        }
        String orientString = exif.getAttribute(ExifInterface.TAG_ORIENTATION);
        int orientation = orientString != null ? Integer.parseInt(orientString) : ExifInterface.ORIENTATION_NORMAL;
        int rotationAngle = 0;
        if (orientation == ExifInterface.ORIENTATION_ROTATE_90) rotationAngle = 90;
        if (orientation == ExifInterface.ORIENTATION_ROTATE_180) rotationAngle = 180;
        if (orientation == ExifInterface.ORIENTATION_ROTATE_270) rotationAngle = 270;
        // Rotate Bitmap
        Matrix matrix = new Matrix();
        matrix.setRotate(rotationAngle, (float) bm.getWidth() / 2, (float) bm.getHeight() / 2);
        Bitmap rotatedBitmap = Bitmap.createBitmap(bm, 0, 0, bounds.outWidth, bounds.outHeight, matrix, true);
        // Return result
        return rotatedBitmap;
    }
    public static File ImageOrientation(String picturePath) {
        Bitmap rotatedBitmap = null;
        File f=null;
        try {
            BitmapFactory.Options bounds = new BitmapFactory.Options();
            bounds.inJustDecodeBounds = true;
            BitmapFactory.decodeFile(picturePath, bounds);

            BitmapFactory.Options opts = new BitmapFactory.Options();
            Bitmap bm = BitmapFactory.decodeFile(picturePath, opts);
            ExifInterface exif = null;
            exif = new ExifInterface(picturePath);
            String orientString = exif.getAttribute(ExifInterface.TAG_ORIENTATION);
            int orientation = orientString != null ? Integer.parseInt(orientString) : ExifInterface.ORIENTATION_NORMAL;

            int rotationAngle = 0;
            if (orientation == ExifInterface.ORIENTATION_ROTATE_90) rotationAngle = 90;
            if (orientation == ExifInterface.ORIENTATION_ROTATE_180) rotationAngle = 180;
            if (orientation == ExifInterface.ORIENTATION_ROTATE_270) rotationAngle = 270;

            Matrix matrix = new Matrix();
            matrix.setRotate(rotationAngle, (float) bm.getWidth() / 2, (float) bm.getHeight() / 2);
            rotatedBitmap = Bitmap.createBitmap(bm, 0, 0, bounds.outWidth, bounds.outHeight, matrix, true);
            ByteArrayOutputStream bytes = new ByteArrayOutputStream();
            rotatedBitmap.compress(Bitmap.CompressFormat.JPEG, 70, bytes);
            f = new File(Environment.getExternalStorageDirectory()
                    + File.separator + "Lugagi_Food.png");
            f.createNewFile();
            FileOutputStream fo = new FileOutputStream(f);
            fo.write(bytes.toByteArray());
            fo.close();
        }catch (Exception e){
            e.printStackTrace();
        }
        return f;
    }
    public static boolean checkInternetConnection(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        return (cm.getActiveNetworkInfo() != null) && cm.getActiveNetworkInfo().isAvailable() && cm.getActiveNetworkInfo().isConnected();
    }
}
