package com.nisarga.nisargaveggiez.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Filter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.nisarga.nisargaveggiez.Home.ProductDetailHome;
import com.nisarga.nisargaveggiez.R;

import java.util.ArrayList;
import java.util.List;

import static com.nisarga.nisargaveggiez.Home.ProductSearch.noproducts;


public class AutoCompleteAdapter extends ArrayAdapter<AutocompleteModel> {
    ArrayList<AutocompleteModel> customers, tempCustomer, suggestions;

    public AutoCompleteAdapter(Context context, ArrayList<AutocompleteModel> objects) {
        super(context, android.R.layout.simple_list_item_1, objects);
        this.customers = objects;
        this.tempCustomer = new ArrayList<AutocompleteModel>(objects);
        this.suggestions = new ArrayList<AutocompleteModel>(objects);

    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        final AutocompleteModel customer = getItem(position);
        if (convertView == null) {

            convertView = LayoutInflater.from(getContext()).inflate(R.layout.autocomplete_layout, parent, false);
        }
        TextView txtCustomer = (TextView) convertView.findViewById(R.id.tvCustomer);
        ImageView ivCustomerImage = (ImageView) convertView.findViewById(R.id.ivCustomerImage);
        if (txtCustomer != null)


            Log.d("sss", String.valueOf(customer.getTitle()));

        txtCustomer.setText(customer.getTitle());

//           if (ivCustomerImage != null && customer.getProfilePic() != -1)
//            ivCustomerImage.setImageResource(customer.getProfilePic());


        Glide.with(getContext()).load(customer.getImgUrl()).into(ivCustomerImage);


        convertView.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                Log.d("product_idssss", String.valueOf(customer.getproduct_id()));
                Intent intent = new Intent(getContext(), ProductDetailHome.class);
                intent.putExtra("product_id", String.valueOf(customer.getproduct_id()));
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                getContext().startActivity(intent);


            }
        });


        // Now assign alternate color for rows
        //   if (position % 2 == 0)
        //   convertView.setBackgroundColor();
        //   else
        //  convertView.setBackgroundColor(getContext().getColor(R.color.black));

        return convertView;
    }


    @Override
    public Filter getFilter() {
        return myFilter;
    }


    Filter myFilter = new Filter() {
        @Override
        public CharSequence convertResultToString(Object resultValue) {


            AutocompleteModel customer = (AutocompleteModel) resultValue;

            return customer.getTitle();


        }

        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            if (constraint != null) {
                suggestions.clear();
                for (AutocompleteModel people : tempCustomer) {
                    if (people.getTitle().toLowerCase().startsWith(constraint.toString().toLowerCase())) {
                        suggestions.add(people);
                    }
                }

                FilterResults filterResults = new FilterResults();
                filterResults.values = suggestions;
                filterResults.count = suggestions.size();
                return filterResults;
            } else {
                return new FilterResults();
            }
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            ArrayList<AutocompleteModel> c = (ArrayList<AutocompleteModel>) results.values;
            if (results != null && results.count > 0)
            {
                noproducts.setText(" ");
                clear();
                for (AutocompleteModel cust : c) {
                    add(cust);
                    notifyDataSetChanged();
                }
            }
            else
            {
                AutoCompleteTextView storeTV =
                        (AutoCompleteTextView)((Activity)getContext()).findViewById(R.id.store);

                if(storeTV.getText().length()>0)
                noproducts.setText("No Products Found");
                Log.d("pddddd", "pddddd: ");

            }
        }
    };


}