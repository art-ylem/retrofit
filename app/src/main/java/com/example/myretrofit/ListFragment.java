package com.example.myretrofit;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.myretrofit.events.Events;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class ListFragment extends Fragment {

    private final String FIELDS = "images,id,dates,short_title,title,place,location,categories,tagline,age_restriction,price,is_free,site_url";
    private InformationFragment.OnFragmentInteractionListener mListener;
    private RecyclerViewAdapter recyclerViewAdapter;
    private RecyclerView recyclerView;
    private CardView recyclerCardView;

    public static ListFragment newInstance() {
        ListFragment fragment = new ListFragment();
        return fragment;

    }
//    public void frag2(Fragment fragment){
//        FragmentTransaction transaction = getChildFragmentManager().beginTransaction();
//        transaction.replace(R.id.information_fragment_container, fragment);
//        transaction.addToBackStack(null);
//        transaction.commit();
//    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.list_fragment, container, false);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        MainActivity mainActivity;
        mainActivity = (MainActivity) getActivity();
        recyclerView = view.findViewById(R.id.myRecyclerView);
        recyclerCardView = view.findViewById(R.id.recyclerCardView);



        getLocations()
                .subscribeOn(Schedulers.io())//где выполняется
                .observeOn(AndroidSchedulers.mainThread())//гд
                .doOnNext(events -> {
                    recyclerViewAdapter = new RecyclerViewAdapter(getContext(), events.getResults());
                    recyclerView.setAdapter(recyclerViewAdapter);
                    recyclerViewAdapter.getItemClick().subscribe(results ->{
                        mainActivity.frag(InformationFragment.newInstance(results.getId().toString()));
                    } );
                })
                .subscribe();

    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof InformationFragment.OnFragmentInteractionListener) {
            mListener = (InformationFragment.OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onAccountFragmentInteraction();
    }

    private Observable<Events> getLocations(){
        return NetworkService.getInstance()//создание HTTP клиента и вызов метода с сервера
                .getJSONApi()
                .getPosts(30, FIELDS);
    }

}
