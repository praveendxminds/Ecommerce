package com.nisarga.nisargaveggiez.wallet;

import android.content.Context;
import android.widget.TextView;

import com.nisarga.nisargaveggiez.R;
import com.mindorks.placeholderview.annotations.Layout;
import com.mindorks.placeholderview.annotations.NonReusable;
import com.mindorks.placeholderview.annotations.Resolve;
import com.mindorks.placeholderview.annotations.View;

@NonReusable
@Layout(R.layout.loyality_point_notes)
public class LoyalityPointNotes {

    @View(R.id.txtNotes)
    public TextView txtNotes;

    public Context mContext;
    public String mNotes;

    public LoyalityPointNotes(Context contxt, String notes)
    {
        this.mContext = contxt;
        this.mNotes = notes;
    }
    @Resolve
    public void onResolved()
    {
        txtNotes.setText(mNotes);
    }

}
