package com.tedkim.android.tutils;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Class collection of toast
 * Created by Ted
 */
public class ToastUtils {

    public static Toast toast;

    private int LAYOUT_TOAST_ID;
    private int TEXT_ID;
    private Context mContext;

    /**
     * Constructor for a toast with custom layout
     *
     * @param context       context
     * @param layoutToastId custom background layout id
     * @param textId        custom background layout 내 textView id
     */
    public ToastUtils(Context context, int layoutToastId, int textId) {
        mContext = context;
        LAYOUT_TOAST_ID = layoutToastId;
        TEXT_ID = textId;
    }

    /**
     * Show toast with custom background
     *
     * @param text toast text
     */
    public void showToast(String text) {
        if (toast != null)
            toast.cancel();

        toast = Toast.makeText(mContext, text, Toast.LENGTH_SHORT);
        LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(LAYOUT_TOAST_ID, null);

        TextView textView = (TextView) view.findViewById(TEXT_ID);
        textView.setText(text);
        toast.setView(view);

        toast.show();
    }

    /**
     * Show common toast
     *
     * @param context context
     * @param text    toast text
     */
    public static void showToast(Context context, String text) {
        if (toast != null)
            toast.cancel();

        toast = Toast.makeText(context, text, Toast.LENGTH_SHORT);
        toast.show();
    }
}
