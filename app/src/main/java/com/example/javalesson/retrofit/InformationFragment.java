package com.example.javalesson.retrofit;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class InformationFragment extends Fragment {

    private OnFragmentInteractionListener mListener;
    private String id;
    private TextView textViewdesc;

    public static InformationFragment newInstance(String id) {
        InformationFragment fragment = new InformationFragment();
        Bundle args = new Bundle();
        args.putString("idKey", id);
        return fragment;

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        id = getArguments().getString("idKey");

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.information_fragment, container, false);
        textViewdesc = container.findViewById(R.id.recycler_view_row_id_text);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        getInformation(id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnNext(
                        infoPost -> {
                            textViewdesc.setText(infoPost.getDescription());
                        }
                )
                .subscribe();


    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
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

    private Observable<InfoPost> getInformation(String id) {
        return NetworkService.getInstance()//создание HTTP клиента и вызов метода с сервера
                .getJSONApi()
                .getPostInformationById(id);
    }
}
