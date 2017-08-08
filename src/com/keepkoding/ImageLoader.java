package com.keepkoding;

/*
*  ImageLoader.java
*  VidAnimator
*
*  Created 8/4/2017.
*  Copyright © 2017. All rights reserved.
*/

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;
import javax.imageio.ImageIO;

class ImageLoader {
    static BufferedImage load(String name) {
        URL url = ImageLoader.class.getResource(name);
        try {
            // Try to read image using name provided
            return ImageIO.read(url);
        } catch (IOException e) {
            throw new RuntimeException("Could not load image '"
                    + name + ": \n" + e.getMessage());
        }
    }
}
