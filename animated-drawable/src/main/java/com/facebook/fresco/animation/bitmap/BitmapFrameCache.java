/*
 * Copyright (c) 2015-present, Facebook, Inc.
 * All rights reserved.
 *
 * This source code is licensed under the BSD-style license found in the
 * LICENSE file in the root directory of this source tree. An additional grant
 * of patent rights can be found in the PATENTS file in the same directory.
 */
package com.facebook.fresco.animation.bitmap;

import javax.annotation.Nullable;

import android.graphics.Bitmap;

import com.facebook.common.references.CloseableReference;

/**
 * Bitmap frame cache that is used for animated images.
 */
public interface BitmapFrameCache {

  /**
   * Get the cached frame for the given frame number.
   *
   * @param frameNumber the frame number to get the cached frame for
   * @return the cached frame or null if not cached
   */
  @Nullable
  CloseableReference<Bitmap> getCachedFrame(int frameNumber);

  /**
   * Get a fallback frame for the given frame number. This method is called if all other attempts
   * to draw a frame failed.
   * The bitmap returned could for example be the last drawn frame (if any).
   *
   * @param frameNumber the frame number to get the fallback
   * @return the fallback frame or null if not cached
   */
  @Nullable
  CloseableReference<Bitmap> getFallbackFrame(int frameNumber);

  /**
   * Return a reusable bitmap that should be used to render the given frame.
   *
   * @param frameNumber the frame number to be rendered
   * @param width the width of the target bitmap
   * @param height the height of the target bitmap
   * @return the reusable bitmap or null if no reusable bitmaps available
   */
  @Nullable
  CloseableReference<Bitmap> getBitmapToReuseForFrame(int frameNumber, int width, int height);

  /**
   * @return the size in bytes of all cached data
   */
  int getSizeInBytes();

  /**
   * Clear the cache.
   */
  void clear();

  /**
   * Callback when the given bitmap has been drawn to a canvas.
   * This bitmap can either be a reused bitmap returned by
   * {@link #getBitmapToReuseForFrame(int, int, int)} or a new bitmap.
   *
   * Note: the implementation of this interface has to manually clone the given bitmap reference
   * if it wants to hold on to the bitmap.
   * The original reference will be automatically closed after this call.
   *
   * @param frameNumber the frame number that has been rendered
   * @param bitmap the bitmap that has been rendered
   * @param frameType the frame type that has been rendered
   */
  void onFrameRendered(
      int frameNumber,
      CloseableReference<Bitmap> bitmap,
      int frameType);
}
