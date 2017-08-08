package com.keepkoding;

/*
*  MediaLoader.java
*  VidAnimator
*
*  Created 8/4/2017.
*  Copyright © 2017. All rights reserved.
*/

import java.net.URL;
import javafx.scene.media.Media;

class MediaLoader {
    static Media loadClip(String name) {
        URL url = MediaLoader.class.getResource(name);
        try {
            // Try loading media using name provided
            Media clip = new Media(url.toString());
            return clip;
        } catch (Exception e) {
            throw new RuntimeException("Could not load media '"
                    + name + ": \n" + e.getMessage());
        }
    }
}
