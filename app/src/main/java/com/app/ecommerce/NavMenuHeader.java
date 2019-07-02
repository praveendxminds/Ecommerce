package com.app.ecommerce;

import android.content.Context;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.mikhaellopez.circularimageview.CircularImageView;
import com.mindorks.placeholderview.annotations.Layout;
import com.mindorks.placeholderview.annotations.NonReusable;
import com.mindorks.placeholderview.annotations.Resolve;
import com.mindorks.placeholderview.annotations.View;

@NonReusable
@Layout(R.layout.nav_menu_header)
public class NavMenuHeader {

    @View(R.id.itemIconProfile)
    public CircularImageView itemIconProfile;

    @View(R.id.tvProPicEdit)
    public Button tvProPicEdit;

    @View(R.id.tvUserName)
    public TextView tvUserName;

    @View(R.id.tvUserMailId)
    public TextView tvUserMailId;

    @View(R.id.tvContactNo)
    public TextView tvContactNo;

    @View(R.id.imgBtnEdit)
    public ImageButton imgBtnEdit;

    public Context contxt;

    public NavMenuHeader(Context mcontext)
    {
        contxt = mcontext;
    }
    @Resolve
    public void onResolved()
    {
      Glide.with(contxt).load("https://www.gstatic.com/webp/gallery3/4.png").into(itemIconProfile);
       tvUserName.setText("User 1 name user name user name user name user name");
       tvUserMailId.setText("usermailid123@gmail.com");
       tvContactNo.setText("+91"+" "+"9087654567");
    }

}
