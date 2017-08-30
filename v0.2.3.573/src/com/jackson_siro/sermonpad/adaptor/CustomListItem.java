package com.jackson_siro.sermonpad.adaptor;

import com.jackson_siro.sermonpad.*;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

	public class CustomListItem extends ArrayAdapter<String>{
	
		private final Activity context;
		private final String[] mytext;
		private final String[] mytexti;
		private final String[] mytextii;
		private final String[] mytextiii;
		private final String[] mytextiv;
		
	public CustomListItem(Activity context,	String[] mytext, String[] mytexti, 
			String[] mytextii, String[] mytextiii, String[] mytextiv) {
		super(context, R.layout.list_notes, mytext);
		this.context = context;
		this.mytext = mytext;
		this.mytexti = mytexti;
		this.mytextii = mytextii;
		this.mytextiii = mytextiii;
		this.mytextiv = mytextiv;

	}
	
	@Override
	public View getView(int position, View view, ViewGroup parent) {
		LayoutInflater inflater = context.getLayoutInflater();
		View rowView= inflater.inflate(R.layout.list_notes, null, true);
		TextView txtTitle = (TextView) rowView.findViewById(R.id.txt);
		TextView txtTitlei = (TextView) rowView.findViewById(R.id.txti);
		TextView txtTitleii = (TextView) rowView.findViewById(R.id.txtii);
		TextView txtTitleiii = (TextView) rowView.findViewById(R.id.txtiii);
		TextView txtTitleiv = (TextView) rowView.findViewById(R.id.txtiv);
	
		txtTitle.setText(mytext[position]);
		txtTitlei.setText(mytexti[position]);
		txtTitleii.setText(mytextii[position]);
		txtTitleiii.setText(mytextiii[position]);
		txtTitleiv.setText(mytextiv[position]);
	
		return rowView;
	
	}
}