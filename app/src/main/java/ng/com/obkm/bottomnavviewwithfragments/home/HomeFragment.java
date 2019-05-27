package ng.com.obkm.bottomnavviewwithfragments.home;


import android.Manifest;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import ng.com.obkm.bottomnavviewwithfragments.R;

import static java.sql.DriverManager.println;


public class HomeFragment extends Fragment {

    private List<HomeItem> rv_list;
    private RecyclerView recyclerView;
    private List<HomeItem> smsList = new ArrayList();
    private float totalMonthlySavings=0.0F;
    private TextView textView;

    //OkHttpClient creates connection pool between client and server

    public HomeFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        recyclerView = (RecyclerView) view.findViewById(R.id.home_rv);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        HomeRecyclerAdapter mAdapter = new HomeRecyclerAdapter(getSmsMessages());
        mAdapter.setTotalMonthlySavings(totalMonthlySavings);
        recyclerView.setAdapter(mAdapter);
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        textView = (TextView) view.findViewById(R.id.footer_text);
        textView.setText("Savings This Month: "+ totalMonthlySavings);

        return view;
    }

    private ArrayList<HomeItem> getSmsMessages(){

        rv_list = new ArrayList<HomeItem>();

        Cursor cursor = this.getActivity().getContentResolver().query(Uri.parse("content://sms/inbox"), null, null, null, null);

        if (cursor.moveToFirst()){

            //val nameID = cursor.getColumnIndex("address")
            int messageID = cursor.getColumnIndex("body");
            int dateID = cursor.getColumnIndex("date");

            do {
                //val senderName = cursor.getString(nameID)
                String message = cursor.getString(messageID);
                String dateString = cursor.getString(dateID);

                //First filter only spends from the sms list
                //TODO: This logic needs improvement as we need to ideally read a configurable list of texts
                    if(message.contains("spent Rs")){
                        rv_list.add(new HomeItem(getMerchantName(message,2),getSpentAmount(message,2)));
                    }else if(message.contains("purchase for")){
                        rv_list.add(new HomeItem(getMerchantName(message,1),getSpentAmount(message,3)));
                    } else if(message.contains("spent on")){
                        rv_list.add(new HomeItem(getMerchantName(message,1),getSpentAmount(message,1)));
                    }
            }while (cursor.moveToNext());
        }
        cursor.close();

        return (ArrayList<HomeItem>) rv_list;
    }

    private String getMerchantName (String message, int type){

        int startIndex=0;
        int endIndex=0;

        try{
            if(type ==1){
                startIndex = message.indexOf("at") + 3;
                endIndex = message.substring(startIndex).indexOf(".");
                endIndex = startIndex + endIndex;
            }if(type==2){
                startIndex = message.indexOf("at") + 3;
                endIndex = message.substring(startIndex).indexOf("on");
                endIndex = startIndex + endIndex;
            }
        }catch(StringIndexOutOfBoundsException strindexe){
            System.out.println(strindexe);
        }

        return message.substring(startIndex, endIndex);

    }

    private String getSpentAmount (String message, int type){

        int startIndex=0;
        int endIndex=0;

        try{
            if(type ==1){
                startIndex = message.indexOf("] Rs") + 5;
                endIndex = message.substring(startIndex).indexOf("spent");
                endIndex = startIndex + endIndex;

            }if(type==2){
                startIndex = message.indexOf("spent Rs.") + 10;
                endIndex = message.substring(startIndex).indexOf("on");
                endIndex = startIndex + endIndex;

            }if(type==3){
                startIndex = message.indexOf("amount of Rs.") + 14;
                endIndex = message.substring(startIndex).indexOf("on");
                endIndex = startIndex + endIndex;
            }
        }catch(StringIndexOutOfBoundsException strindexe){
            System.out.println(strindexe);
        }
        return message.substring(startIndex, endIndex);

    }

}
