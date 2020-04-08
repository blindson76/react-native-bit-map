package com.reactlibrary;

import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.bridge.Promise;
import com.facebook.react.bridge.Arguments;
import com.facebook.react.bridge.WritableArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import java.net.URL;
import java.net.HttpURLConnection;
import java.io.InputStream;
public class BitMapModule extends ReactContextBaseJavaModule {

    private final ReactApplicationContext reactContext;

    public BitMapModule(ReactApplicationContext reactContext) {
        super(reactContext);
        this.reactContext = reactContext;
    }

    @Override
    public String getName() {
        return "BitMap";
    }

    @ReactMethod
    public void getPixels(final String imageUri, Promise promise) {
        // TODO: Implement some actually useful functionality
        try {
            Bitmap bitmap;
            if (imageUri.startsWith("http")) {
                URL url = new URL(imageUri);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setDoInput(true);
                connection.connect();
                InputStream input = connection.getInputStream();
                bitmap = BitmapFactory.decodeStream(input);
            } else {
                bitmap = BitmapFactory.decodeFile(imageUri);
            }
            final int width = bitmap.getWidth();
            final int height = bitmap.getHeight();
            final int[] pixels = new int[width * height];
            bitmap.getPixels(pixels, 0, width, 0, 0, width, height);
            WritableArray toJS=Arguments.createArray();
            for(int i=0;i<width*height;i++){
                toJS.pushInt(pixels[i]);
            }
            promise.resolve(toJS);
        } catch (Exception e){
            promise.reject("ERROR", e);
        }
    }
}
