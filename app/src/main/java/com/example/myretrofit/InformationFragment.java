package com.example.myretrofit;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.myretrofit.postInformation.Dates;
import com.example.myretrofit.postInformation.InfoPost;

import java.text.SimpleDateFormat;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class InformationFragment extends Fragment {

    private OnFragmentInteractionListener mListener;
    private String id;
    private TextView textViewdesc;
    private ImageView backBtn;
    private TextView textViewPrice;
    private TextView textViewLocation;
    private TextView textViewTitle;

    private TextView textData;
    private TextView publicationDate;
    private TextView textAge;
    private TextView likeText;
    private TextView text_under_desc_text;

    public static InformationFragment newInstance(String id) {
        InformationFragment fragment = new InformationFragment();
        Bundle args = new Bundle();
        args.putString("idKey", id);
        fragment.setArguments(args);
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
        textViewdesc = view.findViewById(R.id.description_text);
        backBtn = view.findViewById(R.id.back_arrow_img);
        textViewPrice = view.findViewById(R.id.text_under_price);
        textViewLocation = view.findViewById(R.id.place);
        textViewTitle = view.findViewById(R.id.price);
        textData = view.findViewById(R.id.text_metro);
        publicationDate = view.findViewById(R.id.publicationDate);
        textAge = view.findViewById(R.id.age);
        likeText = view.findViewById(R.id.like_text);
        text_under_desc_text = view.findViewById(R.id.text_under_desc_text);

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().onBackPressed();
            }
        });


        getInformation(id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnNext(
                        infoPost -> {
                            textViewdesc.setText(stripHtml(setTextString(infoPost.getDescription(), "Полное описание отсутствует")));
                            textViewTitle.setText(setTextString(infoPost.getTitle(),"Название в разработке"));
                            textViewLocation.setText(setTextString(infoPost.getLocation().getSlug(), "город проведения в разработке"));
                            textViewPrice.setText(setPrice(infoPost.getPrice(), infoPost.getIs_free(),"Цена неee определена" ));
                            publicationDate.setText(setTextString("Опубликовано: " + infoPost.getPublication_date(),"Дата публикации"));
                            textAge.setText(setAgeRestriction(infoPost.getAge_restriction()));
                            likeText.setText(setTextString(infoPost.getFavorites_count(),"100"));
//                            textData.setText(setDataStart(infoPost.getDates(),infoPost.getDates(),"Дата начала еще не определена"));???
                            text_under_desc_text.setText(stripHtml(setTextString(infoPost.getBody_text(),"")));
                        }
                )
                .subscribe();


    }
    public String setTextString(String title, String elsee){
        if(title != null && title != "" && title != " ") return title;
        else return elsee;
    }
    public String setPrice(String price, String free, String elsee){
        if (free == "true") return "Бесплатно";
        else if(price != null && price != "" && price != " ") return price;
        else return elsee;
    }

    public String setAgeRestriction(String ageRestriction){
        if(ageRestriction != null && ageRestriction != "" && ageRestriction != " "){
            if(ageRestriction.endsWith("+")) return ageRestriction;
            else return ageRestriction + "+";
        }
        else return "0+";
    }

    public String setData(String data, Dates[] dates, String elsee){
        if(data != " " && data != "" && data != null && dates != null) {
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            return dateFormat.format(Integer.parseInt(data) * 1000);
        } else return elsee;
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
    public String stripHtml(String html)
    {
        return Html.fromHtml(html).toString();
    }
}
