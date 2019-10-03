package com.jackson_siro.sermonpad.ui;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.jackson_siro.sermonpad.*;
import com.jackson_siro.sermonpad.SQLite.*;
import com.jackson_siro.sermonpad.tools.*;
import com.jackson_siro.sermonpad.ui.Adapters.*;

import java.util.ArrayList;
import java.util.List;

public class CcMainFragDrafts extends Fragment {
    AppFunctions asf = new AppFunctions();
    private static final String ARG_PARAM1 = "param1", ARG_PARAM2 = "param2";
    private String mParam1, mParam2, setclass;
    FloatingActionButton mFabpapers;

    AdapterList adapter;
    ListView mList;
    private String[] My_Text, My_Texti, My_Textii, My_Textiii, My_Textiv, My_Icon;
    private View mProgressView, mListView;

    private OnFragmentInteractionListener mListener;
    public static CcMainFragDrafts newInstance(String param1, String param2) {
        CcMainFragDrafts fragment = new CcMainFragDrafts();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    public CcMainFragDrafts() { }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.cc_main_frag_notes, container, false);
    }

    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mProgressView = view.findViewById(R.id.loading_progress);
        mListView = view.findViewById(R.id.list_view);
        mList = view.findViewById(R.id.list_view);

        FillWithData();
    }

    private void FillWithData(){
        AppSQLite db = new AppSQLite(getContext(), AppDatabase.DB_NAME, null, AppDatabase.DB_VERSION);

        List<SermonItem> mylist = db.listSermon(2);
        List<String> listID = new ArrayList<>();
        List<String> listTitle = new ArrayList<>();
        List<String> listDates = new ArrayList<>();
        List<String> listExtra = new ArrayList<>();
        List<String> listContent = new ArrayList<>();

        for (int i = 0; i < mylist.size(); i++) {
            listID.add(i, Integer.toString(mylist.get(i).getRid()));
            listTitle.add(i, mylist.get(i).getRtitle());
            listDates.add(i, asf.datetimeStr(mylist.get(i).getRcreated()));
            listExtra.add(i, mylist.get(i).getRpreacher() + ", " + mylist.get(i).getRplace());
            listContent.add(i, mylist.get(i).getRcontent());
        }

        My_Text = listID.toArray(new String[listID.size()]);
        for (String string : My_Text) {	System.out.println(string);	}

        My_Texti = listTitle.toArray(new String[listTitle.size()]);
        for (String stringi : My_Texti) {	System.out.println(stringi);	}

        My_Textii = listDates.toArray(new String[listDates.size()]);
        for (String stringii : My_Textii) {	System.out.println(stringii);	}

        My_Textiii = listExtra.toArray(new String[listExtra.size()]);
        for (String stringiii : My_Textiii) {	System.out.println(stringiii);	}

        My_Textiv = listContent.toArray(new String[listContent.size()]);
        for (String stringiv : My_Textiv) {	System.out.println(stringiv);	}


        adapter = new AdapterList(getActivity(), null, My_Text, My_Texti, My_Textii, My_Textiii, My_Textiv);
        mList.setAdapter(adapter);

        mList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getContext(), DdNotePad.class);
                intent.putExtra("editor_act", 2);
                intent.putExtra("sermon_item", Integer.parseInt(My_Text[+ position]));
                startActivityForResult(intent, 1);
            }
        });
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            mListener = (OnFragmentInteractionListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }
}
