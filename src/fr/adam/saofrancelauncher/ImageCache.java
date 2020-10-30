package fr.adam.saofrancelauncher;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ImageCache {

    public static final BufferedImage DEFAULT_IMAGE = new BufferedImage(1, 1, BufferedImage.TYPE_4BYTE_ABGR);

    private static final HashMap<String, Boolean> IS_DOWNLOADING = new HashMap<>();
    private static final HashMap<String, Long> DOWNLOADED_TIMES = new HashMap<>();
    private static final HashMap<String, BufferedImage> CACHE = new HashMap<>();

    private static final ExecutorService SERVICE = Executors.newFixedThreadPool(1);

    private static BufferedImage getImage(String url, BufferedImage defaultImage) {
        if (CACHE.containsKey(url)) {
            return CACHE.get(url);
        } else {
            if (IS_DOWNLOADING.getOrDefault(url, false)) {
                return defaultImage;
            } else {
                IS_DOWNLOADING.put(url, true);
                SERVICE.execute(() -> {
                    try {
                        BufferedImage image = ImageIO.read(new URL(url));
                        CACHE.put(url, image);
                        DOWNLOADED_TIMES.put(url, System.currentTimeMillis());
                    } catch (IOException e) {
                        e.printStackTrace();
                        IS_DOWNLOADING.put(url, false);
                    }
                });
            }
        }
        return defaultImage;
    }

    public static BufferedImage getImage(String url) {
        return getImage(url, DEFAULT_IMAGE);
    }

    public static long getDownloadedTime(String url) {
        return DOWNLOADED_TIMES.get(url);
    }

}
