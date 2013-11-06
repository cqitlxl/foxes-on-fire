package org.mozilla.gecko.menu;

import org.mozilla.gecko.R;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.view.View;
import android.util.Log;

public class MenuReadingList extends Fragment {

	@Override
	public View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroupContainer,
		Bundle savedInstanceState) {
		Log.i("MyActivity", "Creating view");
		return layoutInflater.inflate(R.layout.menu_reading_list, viewGroupContainer, false);
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	}

}