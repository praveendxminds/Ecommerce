package com.nisarga.nisargaveggiez.wallet;

import android.content.Context;
import android.text.Html;
import android.webkit.WebView;
import android.widget.TextView;

import com.nisarga.nisargaveggiez.R;
import com.mindorks.placeholderview.annotations.Layout;
import com.mindorks.placeholderview.annotations.NonReusable;
import com.mindorks.placeholderview.annotations.Resolve;
import com.mindorks.placeholderview.annotations.View;

@NonReusable
@Layout(R.layout.loyality_point_notes)
public class LoyalityPointNotes {

    @View(R.id.webViewNotes)
    public WebView webViewNotes;

    public Context mContext;
    public String mNotes;
    public Integer mNumber;

    public LoyalityPointNotes(Context contxt, Integer num,String notes)
    {
        this.mContext = contxt;
        this.mNumber = num;
        this.mNotes = notes;
    }
    @Resolve
    public void onResolved()
    {
//        webViewNotes.getSettings().setJavaScriptEnabled(true);
//        String res = null;
//        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
//            res = Html.fromHtml(mNotes, Html.FROM_HTML_MODE_COMPACT).toString();
//        } else {
//            res = Html.fromHtml(mNotes).toString();
//        }
//        webViewNotes.loadDataWithBaseURL(null, res, "text/html", "utf-8", null);
        String noteLines = mNumber.toString()+"."+" "+mNotes;
        String data = Html.fromHtml(noteLines).toString();
        webViewNotes.loadData(data, "text/html", "UTF-8");



    }

}
