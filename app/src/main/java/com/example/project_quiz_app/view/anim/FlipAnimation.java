package com.example.project_quiz_app.view.anim;


import android.graphics.Matrix;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.Transformation;
import android.graphics.Camera;

public class FlipAnimation extends Animation {

    private final View fromView;
    private final View toView;
    private float centerX;
    private float centerY;
    private final Camera camera = new Camera();
    private boolean forward = true;
    private boolean isVertical = true; // true: top-bottom, false: left-right

    // Constructor mặc định - lật theo trục Y (top to bottom)
    public FlipAnimation(View fromView, View toView) {
        this.fromView = fromView;
        this.toView = toView;
        this.isVertical = true;
        setDuration(500);
        setFillAfter(true);
    }

    // Constructor với tùy chọn hướng lật
    public FlipAnimation(View fromView, View toView, boolean isVertical) {
        this.fromView = fromView;
        this.toView = toView;
        this.isVertical = isVertical;
        setDuration(500);
        setFillAfter(true);
    }

    public void reverse() {
        forward = false;
        // Reset animation để tránh tích lũy rotation
        reset();
    }

    // Method để bắt đầu animation mới
    public void startFlip() {
        reset();
        forward = true;
    }

    @Override
    public void initialize(int width, int height, int parentWidth, int parentHeight) {
        super.initialize(width, height, parentWidth, parentHeight);
        centerX = width / 2.0f;
        centerY = height / 2.0f;
    }

    @Override
    protected void applyTransformation(float interpolatedTime, Transformation t) {
        final Matrix matrix = t.getMatrix();

        // Tính toán rotation dựa trên nửa đầu hoặc nửa sau của animation
        final float degrees;
        final View visibleView;
        final View invisibleView;

        if (interpolatedTime <= 0.5f) {
            // Nửa đầu: từ 0 đến 90 độ
            degrees = 90 * interpolatedTime / 0.5f;
            visibleView = fromView;
            invisibleView = toView;
            visibleView.setVisibility(View.VISIBLE);
            invisibleView.setVisibility(View.GONE);
        } else {
            // Nửa sau: từ -90 đến 0 độ (để tránh lật chữ)
            degrees = -90 + (90 * (interpolatedTime - 0.5f) / 0.5f);
            visibleView = toView;
            invisibleView = fromView;
            visibleView.setVisibility(View.VISIBLE);
            invisibleView.setVisibility(View.GONE);
        }

        final float rotation = forward ? degrees : -degrees;

        camera.save();
        // Chọn hướng lật dựa trên isVertical
        if (isVertical) {
            camera.rotateX(rotation); // Lật top to bottom
        } else {
            camera.rotateY(rotation); // Lật left to right
        }
        camera.getMatrix(matrix);
        camera.restore();

        matrix.preTranslate(-centerX, -centerY);
        matrix.postTranslate(centerX, centerY);
    }
}


