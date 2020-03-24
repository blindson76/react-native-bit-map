package com.reactlibrary;

import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.bridge.Promise;
com.facebook.react.bridge.Arguments;
import android.graphics.BitMap;
import android.graphics.BitmapFactory;
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
        tryr {
            final BitMap bitmap = BitmapFactory.decodeFile(imageUri);
            final double width = bitmap.getWidth();
            final double height = bitmap.getHeight();
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
