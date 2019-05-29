package ng.com.obkm.bottomnavviewwithfragments;


import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;
import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;

import java.util.ArrayList;
import java.util.Arrays;



/**
 * A simple {@link Fragment} subclass.
 */
public class NotificationsFragment extends Fragment {

    private RecyclerView recyclerView;
    private ArrayList<OffersData>offersList;
    private static final String offersURL = "https://s3.amazonaws.com/makemeanoffer/OffersJSON.txt";
    private SharedViewModel viewModel;

    // ArrayList for person names
    //ArrayList<String> personNames = new ArrayList<>(Arrays.asList("Person 1", "Person 2", "Person 3", "Person 4", "Person 5", "Person 6", "Person 7","Person 8", "Person 9", "Person 10", "Person 11", "Person 12", "Person 13", "Person 14"));
    //ArrayList<Integer> personImages = new ArrayList<>(Arrays.asList(R.drawable.album1, R.drawable.album2, R.drawable.album3, R.drawable.album4, R.drawable.album5, R.drawable.album6, R.drawable.album7,R.drawable.album8, R.drawable.album9, R.drawable.album10, R.drawable.album11, R.drawable.album1, R.drawable.album2, R.drawable.album3));


    public NotificationsFragment() {
        // Required empty public constructor
    }

    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //initializeList();
        getActivity().setTitle("Make Me An Offer");

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        Bundle bundle = new Bundle();
        bundle.putInt("test", 10);
        this.setArguments(bundle);

        View view = inflater.inflate(R.layout.fragment_home, container, false);
        recyclerView = (RecyclerView) view.findViewById(R.id.home_rv);

        final LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);

        // call the constructor of CustomAdapter to send the reference and data to Adapter
        CustomAdapter customAdapter = new CustomAdapter(getContext(), getOffersList(offersURL,1));
        recyclerView.setAdapter(customAdapter); // set the Adapter to RecyclerView

        //viewModel.setText("Offerlist injected");

        return view;

    }

//    @Override
    /*public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        viewModel = ViewModelProviders
    }*/

    private ArrayList<OffersData> getOffersList(String URL, int type){

        ArrayList<OffersData> offersList = new ArrayList<OffersData>();

            offersList.add(new OffersData("Big Basket", "10%", "HSBCMAY","bigbasket","https://www.hsbc.co.in/offers/big-basket/"));
            offersList.add(new OffersData("Flipkart", "10%", "HSBCFLIP","flipkart","https://www.hsbc.co.in/offers/flipkart/"));
            //offersList.add(new OffersData("Croma", "15%", "HSBCC","croma","https://www.croma.in"));
            offersList.add(new OffersData("GoIbibo", "20%", "GOHSBC","goibibo","https://www.hsbc.co.in/credit-cards/offers/goibibo/"));
            offersList.add(new OffersData("Cleartrip", "15%", "CTHSBCFRIDAY","cleartrip","https://www.hsbc.co.in/credit-cards/offers/cleartrip-friday/"));
            //offersList.add(new OffersData("ShoppersStop", "10%", "N/A","shoppersstop","https://www.shoppersstop.com"));

            CustomAdapter customAdapter = new CustomAdapter(getContext(), offersList);
            recyclerView.setAdapter(customAdapter); // set the Adapter to RecyclerView

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
