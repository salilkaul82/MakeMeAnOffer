package ng.com.obkm.bottomnavviewwithfragments;


import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import okhttp3.Callback;
import okhttp3.Request;


/**
 * A simple {@link Fragment} subclass.
 */
public class NotificationsFragment extends Fragment {

    private RecyclerView recyclerView;
    private ArrayList<OffersData>offersList;
    private static final String offersURL = "https://s3.amazonaws.com/makemeanoffer/OffersJSON.txt";
    private SharedViewModel viewModel;
    private ExampleDBHelper dbHelper ;
    private SQLiteDatabase db;
    SendMessage SM;

    public NotificationsFragment() {
        // Required empty public constructor
    }

    interface SendMessage {
        void sendData(ArrayList<OffersData> offersData);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        try {
            SM = (SendMessage) getActivity();
        } catch (ClassCastException e) {
            throw new ClassCastException("Error in retrieving data. Please try again");
        }
    }

    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //initializeList();
        //getActivity().setTitle("Make Me An Offer");
        //loadOffersFromAWS();
        dbHelper = new ExampleDBHelper(getContext());
        db = dbHelper.getWritableDatabase();
        //Delete the database and create again
        dbHelper.onUpgrade(db,0,1);
        //dbHelper.onCreate(db);

        //Create one offer and insert into the database
        OffersData offer = new OffersData();
        offer.setMerchantName("BigBasket");
        offer.setCouponCode("HSBCBB15");
        offer.setOfferURL("https://www.hsbc.co.in/offers/big-basket/");
        offer.setPercentDisc("10");
        offer.setImageURL("bigbasket");
        dbHelper.insertOffer(offer);

        OffersData offer1 = new OffersData();
        offer1.setMerchantName("Flipkart");
        offer1.setCouponCode("HSBCFLIP");
        offer1.setOfferURL("https://www.hsbc.co.in/offers/flipkart/");
        offer1.setPercentDisc("20");
        offer1.setImageURL("flipkart");
        dbHelper.insertOffer(offer1);

        //Pass data to Home fragment
        offersList = new ArrayList<OffersData>();
        offersList.add(offer);
        offersList.add(offer1);
        SM.sendData(offersList);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        Bundle bundle = new Bundle();
        bundle.putInt("test", 10);
        this.setArguments(bundle);

        View view = inflater.inflate(R.layout.fragment_notifications, container, false);
        recyclerView = (RecyclerView) view.findViewById(R.id.notifications_rv);

        final LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);

        // call the constructor of CustomAdapter to send the reference and data to Adapter
        CustomAdapter customAdapter = new CustomAdapter(getContext(), getOffersList());
        recyclerView.setAdapter(customAdapter); // set the Adapter to RecyclerView

        //viewModel.setText("Offerlist injected");

        return view;

    }

    public void loadOffersFromAWS(){

    }

//    @Override
    /*public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        viewModel = ViewModelProviders
    }*/

    /**
     * Method to fetch offers from the database.
     * @return
     */
    private ArrayList<OffersData> getOffersList(){

        ArrayList<OffersData> offersList = new ArrayList<>();
        Cursor cursor = dbHelper.getAllOffers();

        if (cursor.moveToFirst()) {
            do {
                OffersData offer = new OffersData();
                offer.setId(cursor.getString(0));
                offer.setMerchantName(cursor.getString(1));
                offer.setPercentDisc(cursor.getString(2));
                offer.setCouponCode(cursor.getString(3));
                offer.setOfferURL(cursor.getString(4));
                offer.setImageURL(cursor.getString(5));
                // Adding offer to list
                offersList.add(offer);
            } while (cursor.moveToNext());
        }
        cursor.close();

        return offersList;
    }



    public class SharedViewModel extends ViewModel {
        //private final MutableLiveData<Item> selected = new MutableLiveData<Item>();
        private MutableLiveData<CharSequence> text = new MutableLiveData<>();

        public void setText(CharSequence input){
            text.setValue(input);
        }

        public LiveData<CharSequence> getText(){
            return text;
        }

    }

}
