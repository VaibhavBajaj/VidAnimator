package com.keepkoding;

/*
*  AnimatedCircle.java
*  VidAnimator
*
*  Created 8/7/2017.
*  Copyright © 2017. All rights reserved.
*/

import java.awt.Color;
import java.awt.Graphics2D;

// Each white circle is an object. It is an Animated Circle
class AnimatedCircle {
    private double opacity = VidAnimator.opacity;
    private double speed, angle, diameter, x, y, changeSpeed, changeDiam;
    // RGB values lead to white color. Can be altered if required
    private Color color = new Color(255, 255, 255, (int)(opacity * 255));

    AnimatedCircle() {
        x = VidAnimator.screenWidth / 2;
        y = VidAnimator.screenHeight / 2;
        speed = 1;
        diameter = 2;
        angle = Math.random() * Math.PI * 2;
        double rand = Math.random();
        // Set different speed and diameter change values so as to bring about a random factor in animatin
        if (rand > 0.66) {
            changeDiam = 1.003;
            changeSpeed = 1.01;
        }
        else if (rand > 0.33) {
            changeDiam = 1.007;
            changeSpeed = 1.02;
        }
        else {
            changeDiam = 1.013;
            changeSpeed = 1.03;
        }
        changeDiam += ((Math.random() * 0.001) - 0.0005);

        this.update();
    }

    // Update the circle's values
    void update() {
        speed = changeSpeed * speed;
        diameter = changeDiam * diameter;
        double xVel = speed * Math.cos(angle);
        double yVel = speed * Math.sin(angle);
        x += xVel;
        y += yVel;

    }

    void paint(Graphics2D g) {
        g.setColor(color);
        g.fillOval((int)x - ((int)diameter/ 2), (int)y - ((int)diameter / 2), (int)diameter, (int)diameter);
    }

    double getX() {
        return x;
    }

    double getY() {
        return y;
    }
}
