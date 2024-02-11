package nus.iss.sa57.csc_android.utils;

import android.util.Log;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.concurrent.CountDownLatch;

import nus.iss.sa57.csc_android.model.CatSighting;

public class ImageDownloadThread implements Runnable {
    private CatSighting cs;
    private File destFile;
    private CountDownLatch latch;

    public ImageDownloadThread(CatSighting cs, File destFile, CountDownLatch latch) {
        this.cs = cs;
        this.destFile = destFile;
        this.latch = latch;
    }

    @Override
    public void run() {
        String urlString = cs.getImagesURLs().get(0);
        URLConnection urlConnection = null;
        try {
            URL url = new URL(urlString);
            urlConnection = url.openConnection();

            InputStream in = urlConnection.getInputStream();
            FileOutputStream out = new FileOutputStream(destFile);

            byte[] buf = new byte[4096];
            int bytesRead = -1;
            while ((bytesRead = in.read(buf)) != -1) {
                out.write(buf, 0, bytesRead);
            }
            out.close();
            in.close();
            latch.countDown();
        } catch (Exception e) {
            Log.e("MainActivity", "Failed to download image");
            latch.countDown();
        }
    }
}
