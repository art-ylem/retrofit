package com.example.javalesson.retrofit;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import java.util.ArrayList;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity implements InformationFragment.OnFragmentInteractionListener {

    private RecyclerViewAdapter recyclerViewAdapter;
    private RecyclerView recyclerView;
    private CardView recyclerCardView;
    private int idd;

    private void frag(Fragment fragment){

        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.information_frag, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.myRecyclerView);
        recyclerCardView = findViewById(R.id.recyclerCardView);

        NetworkService networkService = NetworkService.getInstance();

        getLocations()
                .subscribeOn(Schedulers.io())//где выполняется
                .observeOn(AndroidSchedulers.mainThread())//гд
                .doOnNext(events -> {
                    recyclerViewAdapter = new RecyclerViewAdapter(this, events.getResults());
                    recyclerView.setAdapter(recyclerViewAdapter );
                })
                .subscribe();

//        textViewId.setOnClickListener(v -> frag(InformationFragment.newInstance(textViewId.getText().toString())));


    }

    private Observable<Events> getLocations(){
        return NetworkService.getInstance()//создание HTTP клиента и вызов метода с сервера
                        .getJSONApi()
                        .getPosts(30);
    }



    @Override
    public void onAccountFragmentInteraction() {

    }
}
