/*
 * Copyright (C) 2008 ZXing authors
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

package com.kingame.barcode.view;

import com.google.zxing.ResultPoint;
import com.kingame.barcode.camera.CameraManager;
import com.kingame.mainfunction.activity.R;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.View;

import java.util.Collection;
import java.util.HashSet;


public final class ViewfinderView extends View {
     // 设置一系列的透明度值，0为完全不透明，255为完全透明(为动画准备)
	 private static final int[] SCANNER_ALPHA = {0, 64, 128, 192, 255, 192, 128, 64};
	 // 动画延迟时间
	 private static final long ANIMATION_DELAY = 100L;
	 private static final int OPAQUE = 0xFF;
	
	 private final Paint paint;
	 private Bitmap resultBitmap;
	 
	 private final int maskColor;
	 private final int resultColor;
	 private final int frameColor;
	 private final int laserColor;
	 private final int resultPointColor;
	 private int scannerAlpha;
	 
	 private Collection<ResultPoint> possibleResultPoints;
	 private Collection<ResultPoint> lastPossibleResultPoints;
	
	 // This constructor is used when the class is built from an XML resource.
	 public ViewfinderView(Context context, AttributeSet attrs) {
	   super(context, attrs);
	
	   // Initialize these once for performance rather than calling them every time in onDraw().
	   paint = new Paint();
	   Resources resources = getResources();
	   // 遮罩颜色
	   maskColor = resources.getColor(R.color.viewfinder_mask);
	   // 获取码图后遮罩颜色
	   resultColor = resources.getColor(R.color.result_view);
	   // 取景区域边框颜色
	   frameColor = resources.getColor(R.color.viewfinder_frame);
	   // 取景区域中水平线颜色
	   laserColor = resources.getColor(R.color.viewfinder_laser);
	   // 取景区域中小黄点儿的颜色
	   resultPointColor = resources.getColor(R.color.possible_result_points);
	   scannerAlpha = 0;
	   // 容纳动画小黄点儿的容器
	   possibleResultPoints = new HashSet<ResultPoint>(5);
	 }
	
	 @Override
	 public void onDraw(Canvas canvas) {
	   Rect frame = CameraManager.get().getFramingRect();
	   if (frame == null) {
	     return;
	   }
	   int width = canvas.getWidth();
	   int height = canvas.getHeight();
	
	   // 画取景区域四周的遮罩
	   paint.setColor(resultBitmap != null ? resultColor : maskColor);
	   // (竖着拿)上块
	   canvas.drawRect(0, 0, width, frame.top - (frame.bottom - frame.top) / 2 , paint);
	   // (竖着拿)左块
	   //canvas.drawRect(0, frame.top, frame.left, frame.bottom + 1, paint);
	   // (竖着拿)右块
	   //canvas.drawRect(frame.right + 1, frame.top, width, frame.bottom + 1, paint);
	   // (竖着拿)下块
	   canvas.drawRect(0, frame.bottom + (frame.bottom - frame.top) / 2, width, height, paint);
	
	   if (resultBitmap != null) {
	     // Draw the opaque result bitmap over the scanning rectangle
	     paint.setAlpha(OPAQUE);
	     canvas.drawBitmap(resultBitmap, frame.left, frame.top, paint);
	   } else {
	
	     // 画取景区域四周的边框
	 paint.setColor(frameColor);
	 // (竖着拿)上左边框
	 canvas.drawRect(frame.left - 5, frame.top - 5, frame.left + 40, frame.top, paint);
	 // (竖着拿)上右边框
	 canvas.drawRect(frame.right - 40, frame.top - 5, frame.right + 5, frame.top , paint);
	 // (竖着拿)左上边框
	 canvas.drawRect(frame.left - 5, frame.top - 5, frame.left, frame.top + 40, paint);
	 // (竖着拿)左下边框
	 canvas.drawRect(frame.left - 5, frame.bottom - 40, frame.left, frame.bottom + 5, paint);
	 // (竖着拿)右上边框
	 canvas.drawRect(frame.right, frame.top - 5, frame.right + 5, frame.top + 40, paint);
	 // (竖着拿)右下边框
	 canvas.drawRect(frame.right, frame.bottom - 40, frame.right + 5, frame.bottom + 5, paint);
	 // (竖着拿)下左边框
	 canvas.drawRect(frame.left - 5, frame.bottom, frame.left + 40, frame.bottom + 5, paint);
	 // (竖着拿)下右边框
	 canvas.drawRect(frame.right - 40, frame.bottom, frame.right + 5, frame.bottom + 5, paint);
	
	 // 画取景区域中心的红线
	 paint.setColor(laserColor);
	 paint.setAlpha(SCANNER_ALPHA[scannerAlpha]);
	 scannerAlpha = (scannerAlpha + 1) % SCANNER_ALPHA.length;
	 int middle = frame.height() / 2 + frame.top;
	 canvas.drawRect(frame.left + 2, middle - 1, frame.right - 1, middle + 2, paint);
	
	 // 准备动画小黄点儿们
	 Collection<ResultPoint> currentPossible = possibleResultPoints;
	 Collection<ResultPoint> currentLast = lastPossibleResultPoints;
	 if (currentPossible.isEmpty()) {
	   lastPossibleResultPoints = null;
	 } else {
	   possibleResultPoints = new HashSet<ResultPoint>(5);
	   lastPossibleResultPoints = currentPossible;
	   paint.setAlpha(OPAQUE);
	   paint.setColor(resultPointColor);
	   for (ResultPoint point : currentPossible) {
	     canvas.drawCircle(frame.left + point.getX(), frame.top + point.getY(), 6.0f, paint);
	   }
	 }
	 if (currentLast != null) {
	   paint.setAlpha(OPAQUE / 2);
	   paint.setColor(resultPointColor);
	   for (ResultPoint point : currentLast) {
	     canvas.drawCircle(frame.left + point.getX(), frame.top + point.getY(), 3.0f, paint);
	   }
	 }
	
	 // Request another update at the animation interval, but only repaint the laser line,
	 // not the entire viewfinder mask.
	     postInvalidateDelayed(ANIMATION_DELAY, frame.left, frame.top, frame.right, frame.bottom);
	   }
	 }
	
	 public void drawViewfinder() {
	   resultBitmap = null;
	   invalidate();
	 }

 /**
  * Draw a bitmap with the result points highlighted instead of the live scanning display.
  *
  * @param barcode An image of the decoded barcode.
  */
 public void drawResultBitmap(Bitmap barcode) {
   resultBitmap = barcode;
   invalidate();
 }

 public void addPossibleResultPoint(ResultPoint point) {
   possibleResultPoints.add(point);
 }

}
