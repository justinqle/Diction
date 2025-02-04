/*
 * Copyright (C) The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.google.android.gms.samples.vision.ocrreader;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;

import com.google.android.gms.samples.vision.ocrreader.ui.camera.GraphicOverlay;
import com.google.android.gms.vision.text.Text;
import com.google.android.gms.vision.text.TextBlock;

import java.util.List;

/**
 * Graphic instance for rendering TextBlock position, size, and ID within an associated graphic
 * overlay view.
 */
public class OcrGraphic extends GraphicOverlay.Graphic {

    private int id;

    private static final int TEXT_COLOR = Color.WHITE;

    private static Paint rectPaint;
    private static Paint textPaint;
    private final TextBlock textBlock;

    OcrGraphic(GraphicOverlay overlay, TextBlock text) {
        super(overlay);

        textBlock = text;

        if (rectPaint == null) {
            rectPaint = new Paint();
            rectPaint.setColor(TEXT_COLOR);
            rectPaint.setStyle(Paint.Style.STROKE);
            rectPaint.setStrokeWidth(4.0f);
        }

        if (textPaint == null) {
            textPaint = new Paint();
            textPaint.setColor(TEXT_COLOR);
            textPaint.setTextSize(54.0f);
        }
        // Redraw the overlay, as this graphic has been added.
        postInvalidate();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public TextBlock getTextBlock() {
        return textBlock;
    }

    /**
     * Checks whether a point is within the bounding box of this graphic.
     * The provided point should be relative to this graphic's containing overlay.
     *
     * @param x An x parameter in the relative context of the canvas.
     * @param y A y parameter in the relative context of the canvas.
     * @return True if the provided point is contained within this graphic's bounding box.
     */
    public boolean contains(float x, float y) {
        if (textBlock == null) {
            return false;
        }
        RectF rect = new RectF(textBlock.getBoundingBox());
        rect = translateRect(rect);
        return rect.contains(x, y);
    }

    /**
     * Draws the text block annotations for position, size, and raw value on the supplied canvas.
     */
    @Override
    public void draw(Canvas canvas) {
        if (textBlock == null) {
            return;
        }

        // Draws the bounding box around the TextBlock.
//        RectF rect = new RectF(textBlock.getBoundingBox());
//        rect = translateRect(rect);
//        canvas.drawRect(rect, rectPaint);

        // Break the text into multiple lines and draw each one according to its own bounding box.

        List<? extends Text> textComponents = textBlock.getComponents();
        for (Text currentTextLine : textComponents) {
            for (Text currentTextWord : currentTextLine.getComponents()) {
                //if (currentTextWord.getValue().matches("[A-Za-z]+[.?!]*")) { //ANDREW LINE
                // Draws bounding box around word
                if (BottomNavigationDrawerFragment.state == 1) {
                    rectPaint.setColor(Color.parseColor("#FFAAAA"));
                } else if (BottomNavigationDrawerFragment.state == 2) {
                    rectPaint.setColor(Color.parseColor("#FFFFFF"));
                }

                if (BottomNavigationDrawerFragment.state >= 1) {
                    RectF rect = new RectF(currentTextWord.getBoundingBox());
                    rect = translateRect(rect);
                    if (BottomNavigationDrawerFragment.state == 1) {
                        rectPaint.setStyle(Paint.Style.STROKE);
                    } else if (BottomNavigationDrawerFragment.state == 2) {
                        rectPaint.setStyle(Paint.Style.FILL_AND_STROKE);
                    }

                    canvas.drawRoundRect(rect, 15.0f, 15.0f, rectPaint);
                }
                if (BottomNavigationDrawerFragment.state == 2) {
                    // Draws word in box
                    float scaleFactor = Math.abs((float) 1.4 * (currentTextWord.getBoundingBox().top - currentTextWord.getBoundingBox().bottom));
                    float left = translateX(currentTextWord.getBoundingBox().left);
                    float bottom = translateY(currentTextWord.getBoundingBox().bottom);
                    textPaint.setColor(Color.BLACK);
                    textPaint.setTextAlign(Paint.Align.LEFT);
                    textPaint.setTextSize(scaleFactor);
                    //textPaint.getTextBounds(currentTextWord.getValue(), 0, currentTextWord.getValue().length(), rect);
                    canvas.drawText(currentTextWord.getValue(), left, bottom, textPaint);
                }
            }
        }

    }

    /*Gets a word at a given coordinate*/
    public String getWord(float rawX, float rawY) {

        TextBlock text = null;
        text = this.getTextBlock();


        if (text != null && text.getValue() != null) {
            List<? extends Text> textComponents = text.getComponents();

            for (Text currentTextLine : textComponents) {
                //return currentTextLine.getValue();
                //Log.d("boxybox", "X: " + rawX + " Xhalp: " + GraphicOverlay.halp[0] + "\n");
                for (Text currentTextWord : currentTextLine.getComponents()) {
                    RectF rect = new RectF(currentTextWord.getBoundingBox());
                    rect = translateRect(rect);

                    /*JUSTIN READ ME AT SOME POINT*/

                    if (rect.contains(rawX - GraphicOverlay.halp[0], rawY - GraphicOverlay.halp[1])) {
                        return currentTextWord.getValue();
                    }
                }
            }
        }

        return null;
    }


}
