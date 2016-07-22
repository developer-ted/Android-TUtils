package com.tedkim.android.tutils;

import android.app.Activity;
import android.os.Handler;
import android.text.Spannable;
import android.text.style.ForegroundColorSpan;
import android.text.style.StyleSpan;
import android.text.util.Linkify;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.EditText;
import android.widget.TextView;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Class collection of view
 * Created by Ted
 */
public class ViewUtils {

    private static final String TAG = ViewUtils.class.getSimpleName();

    /**
     * Get video height (rate 16:9)
     *
     * @param width video width
     * @return video height
     */
    public static int getVideoHeight(int width) {
        return width * 9 / 16;
    }

    /**
     * Get video width (rate 16:9)
     *
     * @param height video height
     * @return video width
     */
    public static int getVideoWidth(int height) {
        return height * 16 / 9;
    }

    /**
     * Get rate
     *
     * @param width  width
     * @param height height
     * @param xRate  x rate
     * @param yRate  y rate
     * @return width height rate
     */
    public static int getScreenRate(int width, int height, int xRate, int yRate) {
        if (width == 0) {
            return height * xRate / yRate;
        } else {
            return width * yRate / xRate;
        }
    }

    /**
     * Hide keyboard touch out side
     *
     * @param activity activity
     * @param view     view
     */
    public static void hideKeyboardTouchOutside(final Activity activity, View view) {
        //Set up touch listener for non-text box views to hide keyboard.
        if (!(view instanceof EditText)) {
            view.setOnTouchListener(new View.OnTouchListener() {
                public boolean onTouch(View v, MotionEvent event) {
                    SystemUtils.hideSoftKeyboard(activity);
                    return false;
                }

            });
        }

        //If a layout container, iterate over children and seed recursion.
        if (view instanceof ViewGroup) {
            for (int i = 0; i < ((ViewGroup) view).getChildCount(); i++) {
                View innerView = ((ViewGroup) view).getChildAt(i);
                hideKeyboardTouchOutside(activity, innerView);
            }
        }
    }

    /**
     * Bold processed in a certain area TextView
     *
     * @param view     TextView
     * @param fulltext full text
     * @param subtext  bold text
     */
    public static void setTextViewBoldPartial(final TextView view, final String fulltext, final String subtext) {
        view.setText(fulltext, TextView.BufferType.SPANNABLE);
        Spannable str = (Spannable) view.getText();
        int i = fulltext.indexOf(subtext);
        if (i > 0) {
            str.setSpan(new StyleSpan(android.graphics.Typeface.BOLD), i, i + subtext.length(), Spannable.SPAN_INCLUSIVE_INCLUSIVE);
        }
    }

    /**
     * Color changes in a certain area TextView
     *
     * @param view     TextView
     * @param fulltext full text
     * @param subtext  color text
     * @param color    text color
     */
    public static void setTextViewColorPartial(final TextView view, final String fulltext, final String subtext, final int color) {
        view.setText(fulltext, TextView.BufferType.SPANNABLE);
        Spannable str = (Spannable) view.getText();
        int i = fulltext.indexOf(subtext);
        if (i > 0) {
            str.setSpan(new ForegroundColorSpan(color), i, i + subtext.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        }
    }

    /**
     * Add link to the string
     *
     * @param view    TextView
     * @param pattern show string
     * @param link    link url
     */
    public static void addLinkToTextview(final TextView view, String pattern, final String link) {
        Linkify.TransformFilter filter = new Linkify.TransformFilter() {
            @Override
            public String transformUrl(Matcher match, String url) {
                return link;
            }
        };
        Linkify.addLinks(view, Pattern.compile(pattern), null, null, filter);
    }

    /**
     * 해당 포지션으로 애니메이션을 주며 이동하는 메소드
     * (단, 이 메소드 사용 후 ScrollListener를 다시 등록해줘야함)
     *
     * @param view     AbsListView
     * @param position move position
     */
    public static void smoothScrollToPositionFromTop(final AbsListView view, final int position) {
        View child;
        final int index = position - view.getFirstVisiblePosition();
        if ((index >= 0) && (index < view.getChildCount())) {
            child = view.getChildAt(index);
        } else {
            child = null;
        }

        // There's no need to scroll if child is already at top or view is already scrolled to its end
        if ((child != null) && ((child.getTop() == 0) || ((child.getTop() > 0) && !view.canScrollVertically(1)))) {
            return;
        }

        view.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(final AbsListView view, final int scrollState) {
                if (scrollState == SCROLL_STATE_IDLE) {
                    view.setOnScrollListener(null);

                    // Fix for scrolling bug
                    new Handler().post(new Runnable() {
                        @Override
                        public void run() {
                            view.setSelection(position);
                        }
                    });
                }
            }

            @Override
            public void onScroll(final AbsListView view, final int firstVisibleItem, final int visibleItemCount,
                                 final int totalItemCount) {
            }
        });

        // Perform scrolling to position
        new Handler().post(new Runnable() {
            @Override
            public void run() {
                view.smoothScrollToPositionFromTop(position, 0);
            }
        });
    }
}
