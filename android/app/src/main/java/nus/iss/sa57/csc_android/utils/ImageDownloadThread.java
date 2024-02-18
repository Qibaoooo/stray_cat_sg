package nus.iss.sa57.csc_android.utils;

import android.util.Log;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicInteger;

import nus.iss.sa57.csc_android.model.CatSighting;

public class ImageDownloadThread implements Runnable {
    private CatSighting cs;
    private File destFile;
    private CountDownLatch latch;
    private ProgressBar progressBar;
    private TextView progressText;
    private int sum;

    public ImageDownloadThread(CatSighting cs, File destFile, CountDownLatch latch,
                               ProgressBar progressBar, TextView progressText, int sum) {
        this.cs = cs;
        this.destFile = destFile;
        this.latch = latch;
        this.progressBar = progressBar;
        this.progressText = progressText;
        this.sum = sum;
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
            int progress = (int)(((float)(sum - latch.getCount()) / sum) * 100);
            progressBar.post(() -> progressBar.setProgress(progress));
            String text = "Downloading " + (sum - latch.getCount()) + " of " + sum + " Images";
            progressText.post(() -> progressText.setText(text));
        } catch (Exception e) {
            Log.e("MainActivity", "Failed to download image");
            latch.countDown();
        }
    }
}
