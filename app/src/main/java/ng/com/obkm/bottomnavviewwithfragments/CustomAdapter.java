package ng.com.obkm.bottomnavviewwithfragments;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;


public class CustomAdapter extends RecyclerView.Adapter {

    ArrayList<OffersData> offersData;
    Context context;

    public CustomAdapter(Context context, ArrayList<OffersData> offersData) {
        this.context = context;
        this.offersData = offersData;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // inflate the item Layout
        //** Note that the notifications fragment is bound to recycler view here**//
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.notification_item, parent, false);
        // set the view's size, margins, paddings and layout parameters
        MyViewHolder vh = new MyViewHolder(v); // pass the view to View Holder
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, final int position) {
        // set the data in items
        MyViewHolder myViewHolder = (MyViewHolder) holder;
        //myViewHolder.name.setText((offersData.get(position).getMerchantName()));
        String imageURI = "@drawable/"+offersData.get(position).getImageURL();
        final int imageResource = context.getResources().getIdentifier(imageURI, null, context.getPackageName());

        myViewHolder.image.setImageResource(imageResource);
        myViewHolder.couponCode.setText((offersData.get(position).getCouponCode()));
        myViewHolder.percentDisc.setText((offersData.get(position).getPercentDisc()));
        // implement setOnClickListener event on item view.
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            // open another activity on item click
            Intent intent = new Intent(context, SecondActivity.class);
            intent.putExtra("logo", imageResource); // put image data in Intent
            intent.putExtra("url", offersData.get(position).getOfferURL());
            context.startActivity(intent); // start Intent
            }
        });
    }


    @Override
    public int getItemCount() {
        return offersData.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        // init the item view's
        TextView name;
        ImageView image;
        TextView couponCode;
        TextView percentDisc;

        public MyViewHolder(View itemView) {
            super(itemView);

            //get the reference of item view's
            image = (ImageView) itemView.findViewById(R.id.logo1);
            couponCode = (TextView) itemView.findViewById(R.id.coupon);
            percentDisc = (TextView) itemView.findViewById(R.id.discount);
        }
    }
}
