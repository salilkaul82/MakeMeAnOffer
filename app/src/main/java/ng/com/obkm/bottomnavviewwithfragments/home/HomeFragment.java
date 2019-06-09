package ng.com.obkm.bottomnavviewwithfragments.home;


import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import ng.com.obkm.bottomnavviewwithfragments.OffersData;
import ng.com.obkm.bottomnavviewwithfragments.R;


public class HomeFragment extends Fragment {

    private List<HomeItem> rv_list;
    private RecyclerView recyclerView;
    private List<HomeItem> smsList = new ArrayList();
    private  List<HomeItem> filteredSmsList = new ArrayList<>();
    private float totalMonthlySavings=0.0F;
    private TextView textView;
    private View view;
    private ArrayList<OffersData>offersList;

    //OkHttpClient creates connection pool between client and server

    public HomeFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public void setOffersData(ArrayList<OffersData> offersList)
    {
        this.offersList = offersList;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_home, container, false);
        recyclerView = (RecyclerView) view.findViewById(R.id.home_rv);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        HomeRecyclerAdapter mAdapter = new HomeRecyclerAdapter(getSmsMessages());
        //mAdapter.setTotalMonthlySavings(offersList.size());
        recyclerView.setAdapter(mAdapter);
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        textView = (TextView) view.findViewById(R.id.footer_text);

        //textView.setText("Savings This Month: "+ offersList.size());
        String formattedTotalMonthSavings = "Monthly Savings: " +  String.format("%.2f",totalMonthlySavings);

        textView.setText(formattedTotalMonthSavings);

        return view;
    }

    private ArrayList<HomeItem> getSmsMessages(){

        rv_list = new ArrayList<HomeItem>();
        filteredSmsList = new ArrayList<HomeItem>();

        Cursor cursor = this.getActivity().getContentResolver().query(Uri.parse("content://sms/inbox"), null, null, null, null);

        if (cursor.moveToFirst()){

            //val nameID = cursor.getColumnIndex("address")
            int messageID = cursor.getColumnIndex("body");
            int dateID = cursor.getColumnIndex("date");

            do {
                //val senderName = cursor.getString(nameID)
                String message = cursor.getString(messageID);
                long date = cursor.getLong(dateID);
                String formattedDate = new SimpleDateFormat("dd MMM yy").format(date);
                HomeItem homeItem = new HomeItem();

                //First filter only spends from the sms list
                //TODO: This logic needs improvement as we need to ideally read a configurable list of texts
                    if(message.contains("spent Rs")){
                        homeItem.setSenderName(getMerchantName(message,2));
                        homeItem.setMessage(getSpentAmount(message,2));
                        homeItem.setDate(formattedDate);
                        rv_list.add(homeItem);
                    }else if(message.contains("purchase for")){
                        homeItem.setSenderName(getMerchantName(message,1));
                        homeItem.setMessage(getSpentAmount(message,3));
                        homeItem.setDate(formattedDate);
                        rv_list.add(homeItem);
                    } else if(message.contains("spent on")){
                        homeItem.setSenderName(getMerchantName(message,1));
                        homeItem.setMessage(getSpentAmount(message,1));
                        homeItem.setDate(formattedDate);
                        rv_list.add(homeItem);
                    }

            }while (cursor.moveToNext());
        }
        cursor.close();

        //TODO: Now Iterate over the smsList & offers list and if match is found then add it to filteredSmsList
        //TODO: Resetting the list temporarily but need to cache it and use it to filter on month/year
        /*if(offersList != null && offersList.size()!=0 && rv_list != null && rv_list.size() !=0) {
            //TODO: Hack implemented as the size of array is not 0 based
            int smsListSize = rv_list.size() -1;
            int offerListSize = offersList.size() - 1
            //TODO : Create an if condition to filter using the month parameter passed to the function
            // create a calendar and set it to the SMS received date

            /*for (i in 0..smsListSize) {
                //println("smsloop.." + i)
                for (j in 0..offerListSize) {
                    //println("offerloop.." + j)
                    if (offersList[j].merchantName.equals(smsList[i].senderName, true)) {
                        println("Offer matches"+ merchantName.toString())
                        filteredSmsList.add(smsList[i])
                        //totalMonthSavings = totalMonthSavings + (((offersList[j].percentDisc.toFloat())/100)*smsList[i].message.toFloat())
                    }
                }
            }*/

            for(HomeItem homeItem : rv_list){
                    for(OffersData offersData: offersList){
                        if(homeItem.getSenderName().replaceAll(" ","").toLowerCase().contains(offersData.getMerchantName().toLowerCase())){
                            Float savings=0.0F;
                            savings = (Float.parseFloat(offersData.getPercentDisc())/100)*Float.parseFloat(homeItem.getMessage());
                            String formattedSavings = String.format("%.2f",savings);
                            String strSavingsMsg = "HSBC Saves: " + formattedSavings;
                            homeItem.setSavings(strSavingsMsg);
                            totalMonthlySavings = totalMonthlySavings + savings;
                        }
                    }
            }

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
                startIndex = message.indexOf("spent Rs") + 9;
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

        return extractNumber(message.substring(startIndex, endIndex));

    }

    public static String extractNumber(final String str) {

        if(str == null || str.isEmpty()) return "";

        StringBuilder sb = new StringBuilder();
        for(char c : str.toCharArray()){
            if(Character.isDigit(c)){
                sb.append(c);
            }else if(c == '.'){
                break;
            }
        }

        return sb.toString();
    }

}
