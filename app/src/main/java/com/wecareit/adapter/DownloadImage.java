package com.wecareit.adapter;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.ImageView;

import java.io.InputStream;

import de.hdodenhof.circleimageview.CircleImageView;

public class DownloadImage extends AsyncTask<String, Void, Bitmap> {
    CircleImageView bmImage;

    public DownloadImage(ImageView bmImage) {
        this.bmImage = (CircleImageView) bmImage;
    }

    protected Bitmap doInBackground(String... urls) {
        String urldisplay = urls[0];
        Bitmap mIcon11 = null;
        try {
            InputStream in = new java.net.URL(urldisplay).openStream();
            mIcon11 = BitmapFactory.decodeStream(in);
        } catch (Exception e) {
            Log.d("Error", e.getStackTrace().toString());

        }
        return mIcon11;
    }

    protected void onPostExecute(Bitmap result) {
        bmImage.setImageBitmap(result);
    }
}
