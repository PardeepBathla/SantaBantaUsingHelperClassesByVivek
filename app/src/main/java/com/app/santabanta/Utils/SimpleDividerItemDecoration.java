package com.app.santabanta.Utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.view.View;

import androidx.recyclerview.widget.RecyclerView;

import com.app.santabanta.AppController;
import com.app.santabanta.R;

public class SimpleDividerItemDecoration extends RecyclerView.ItemDecoration {
    private Drawable mDivider;
    public SharedPreferences pref;

    public SimpleDividerItemDecoration(Context context) {
        pref = Utils.getSharedPref(context);

        if (Utils.getSharedPref(AppController.getInstance()).getBoolean(GlobalConstants.COMMON.THEME_MODE_LIGHT, false)) {
            mDivider = context.getResources().getDrawable(R.drawable.line_divider);
        } else {
            mDivider = context.getResources().getDrawable(R.drawable.line_divider_blackk);
        }
    }

    @Override
    public void onDrawOver(Canvas c, RecyclerView parent, RecyclerView.State state) {
        int left = parent.getPaddingLeft();
        int right = parent.getWidth() - parent.getPaddingRight();

        int childCount = parent.getChildCount();
        for (int i = 0; i < childCount; i++) {
            View child = parent.getChildAt(i);

            RecyclerView.LayoutParams params = (RecyclerView.LayoutParams) child.getLayoutParams();

            int top = child.getBottom() + params.bottomMargin;
            int bottom = top + mDivider.getIntrinsicHeight();

            mDivider.setBounds(left, top, right, bottom);
            mDivider.draw(c);
        }
    }
}

