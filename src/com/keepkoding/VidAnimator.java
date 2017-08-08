package com.keepkoding;

/*
*  VidAnimator.java
*  VidAnimator
*
*  Created 8/4/2017.
*  Copyright © 2017. All rights reserved.
*/

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Toolkit;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.ConcurrentModificationException;
import javafx.embed.swing.JFXPanel;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javax.swing.JFrame;
import javax.swing.WindowConstants;


class VidAnimator extends JFXPanel{

    // Toolkit is used to get resolution of user's computer
    // If not wanted, set custom values.
    static int
            screenWidth = (int)Toolkit.getDefaultToolkit().getScreenSize().getWidth(),
            screenHeight = (int)Toolkit.getDefaultToolkit().getScreenSize().getHeight() - 100;
    // Change opacity to make circles more or less opaque. Max Val --> 1
    static double opacity = 0.65;
    // Sample image has png extension. Put your extension here.
    private static String imageExtension = ".png";

    // When true, start animation, and when false, stop
    private static boolean playing;
    // To play mp3 files
    private static MediaPlayer mediaPlayer;
    private static ArrayList<AnimatedCircle> animations = new ArrayList<>();
    // This list is used to store animations that have gone off screen and must be removed
    private static ArrayList<AnimatedCircle> tmpAnimations = new ArrayList<>();

    private static BufferedImage bgImage = ImageLoader.load("/backgroundImage" + imageExtension);
    // AffineTransform scales image to user computer resolution
    private static final AffineTransform
            transformBackground = AffineTransform.getScaleInstance(
            (double)VidAnimator.screenWidth / bgImage.getWidth(),
            (double)VidAnimator.screenHeight / bgImage.getHeight()
        );

    // Paint screen
    @Override
    public void paint(Graphics g) {
        // Clears screen
        super.paint(g);
        // Converting to 2d object
        Graphics2D g2d = (Graphics2D) g;
        // Paint the background
        g2d.drawRenderedImage(bgImage, transformBackground);
        // Paint all animation circles
        if (!animations.isEmpty()) {
            try {
                for (AnimatedCircle animation : animations) {
                    animation.paint(g2d);
                }
            }
            catch (ConcurrentModificationException e ) {
                    // Skip this paint
                }
        }
    }

    // Update values in all animations
    private void update() {
        if (!animations.isEmpty()) {
            for (AnimatedCircle animation : animations) {
                animation.update();
                if (animation.getX() > screenWidth || animation.getX() < 0
                        || animation.getY() > screenHeight || animation.getY() < 0) {
                    tmpAnimations.add(animation);
                }
            }
        }
        animations.removeAll(tmpAnimations);
        tmpAnimations.clear();
    }

    public static void main(String[] args) throws InterruptedException {
        // This will be our frame which will contain everything
        JFrame frame = new JFrame("Video Animator");

        // Our panel for animation
        VidAnimator animationPanel = new VidAnimator();
        animationPanel.setPreferredSize(new Dimension(screenWidth, screenHeight));

        // Setting up frame properties
        frame.add(animationPanel);
        frame.pack();
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setVisible(true);

        // Adding music
        Media clip = MediaLoader.loadClip("/sampleMusic.mp3");
        mediaPlayer = new MediaPlayer(clip);

        mediaPlayer.setOnReady(new Runnable() {
            @Override
            public void run() {
                mediaPlayer.play();
                playing = true;
            }
        });
        // Set playing to false when clip ends
        mediaPlayer.setStopTime(clip.getDuration());
        Thread.sleep(100);
        mediaPlayer.setOnEndOfMedia(new Runnable() {
            @Override
            public void run() {
                playing = false;
            }
        });

        // Once music stops, wait till all animated circles have reached screen edge
        while(playing || !animations.isEmpty()) {
            animationPanel.repaint();
            animationPanel.update();
            if(playing) {
                // If music is playing, add new circles
                animations.add(new AnimatedCircle());
            }
            Thread.sleep(30);
        }
    }
}
