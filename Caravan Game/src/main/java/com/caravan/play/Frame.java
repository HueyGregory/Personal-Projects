package com.caravan.play;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;

public class Frame {

    private final int width = 300, height = width * 9/16, scale = 3;		// pixel-precision
    private final String frameTitle = "Player";

    private BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
    private int[] imagePixels = ((DataBufferInt) image.getRaster().getDataBuffer()).getData();



}
