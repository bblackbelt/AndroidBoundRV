package com.blackbelt.androidboundrv.misc;

import com.blackbelt.androidboundrv.api.model.Image;

import android.content.Context;
import android.support.v7.widget.GridLayoutManager;
import android.util.SparseArray;

import java.util.ArrayList;
import java.util.List;

import lombok.experimental.Accessors;

@Accessors(prefix = "m")
public class GalleryLayoutManager extends GridLayoutManager {

    private float mMinAspectRatio = 1.6f;

    private float mMaxAspectRatio = 3.8f;

    private Context mContext;

    private List<Image> mDataSet;

    SparseArray<Integer> mSpanSizeLookupCache;

    public GalleryLayoutManager(Context context) {
        super(context, 1);
        mContext = context;
    }

    public void setDataSet(List<Image> images) {
        mDataSet = images;
        updateDataSet();
    }

    private void updateDataSet() {
        final int maxWidth = mContext.getResources().getDisplayMetrics().widthPixels;
        applyAspects(mDataSet, maxWidth, mMinAspectRatio, mMaxAspectRatio);
        setSpanCount(maxWidth);
        setSpanSizeLookup(createSpanSizeLookup(maxWidth));
    }

    /**
     * Method to update the Aspect Ratio thresholds
     *
     * @param minAspectRatio Minimum Aspect ratio required
     * @param maxAspectRatio Maximum Aspect ratio limit
     */
    public void setThresholds(float minAspectRatio, float maxAspectRatio) {
        mMinAspectRatio = minAspectRatio;
        mMaxAspectRatio = maxAspectRatio;
        updateDataSet();
    }

    private SpanSizeLookup createSpanSizeLookup(final int maxWidth) {
        SpanSizeLookup spanSizeLookup = new SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                return mDataSet.get(position) != null
                        ? mDataSet.get(position).getWidth() <= maxWidth
                        ? mDataSet.get(position).getWidth()
                        : maxWidth
                        : maxWidth;
            }
        };

        spanSizeLookup.setSpanIndexCacheEnabled(true);
        return spanSizeLookup;
    }


    private synchronized void applyAspects(List<Image> imageList, int maxWidth, float minAspectRatio, float maxAspectRatio) {
        float aspectRatioSum = 0;
        List<Image> tempList = new ArrayList<>();
        for (Image temp : imageList) {
            tempList.add(temp);
            aspectRatioSum += temp.getAspectRatio();
            if (aspectRatioSum >= minAspectRatio && aspectRatioSum <= maxAspectRatio) {
                normalizeHeights(tempList, maxWidth / aspectRatioSum);
                aspectRatioSum = 0;
                tempList.clear();
            } else if (aspectRatioSum > maxAspectRatio) {
                Image pop = tempList.remove(tempList.size() - 1);
                aspectRatioSum -= pop.getAspectRatio();
                normalizeHeights(tempList, maxWidth / aspectRatioSum);
                tempList.clear();
                tempList.add(pop);
                aspectRatioSum = pop.getAspectRatio();
            }
        }
        normalizeHeights(tempList, maxWidth / aspectRatioSum);
    }

    private void normalizeHeights(List<Image> images, float height) {
        for (Image temp : images) {
            temp.setWidth((int) (height * temp.getAspectRatio()));
            temp.setHeight((int) height);
        }
    }
}
