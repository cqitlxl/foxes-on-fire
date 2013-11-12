package org.mozilla.gecko.menu;

import org.mozilla.gecko.R;
import org.mozilla.gecko.db.BrowserContract.Bookmarks;
import org.mozilla.gecko.db.BrowserDB;

import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.os.Bundle;
import android.view.View;
import android.util.Log;
import android.support.v4.app.Fragment;
import android.widget.ListView;
import android.database.Cursor;
import android.content.Context;
import android.content.CursorLoader;
import android.content.Loader;
import android.app.LoaderManager.LoaderCallbacks;
import android.widget.CursorAdapter;
import android.widget.TextView;

public class MenuReadingList extends Fragment {

	// The view shown by the fragment
    private ListView mList;

    private MenuReadingListAdapter mAdapter;

	@Override
	public View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroupContainer,
		Bundle savedInstanceState) {
		Log.i("MyActivity", "create view");
		View view = layoutInflater.inflate(R.layout.menu_reading_list, viewGroupContainer, false);
		Log.i("MyActivity", "View " + view);
		return view;
	}

	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {	    
		Log.i("MyActivity", "onViewCreated before super");
		super.onCreate(savedInstanceState);
		Log.i("MyActivity", "onViewCreated - start");
	    mList = (ListView) view.findViewById(R.id.list);

		MenuReadingListLoader listLoader = new MenuReadingListLoader(getActivity());
		Cursor cursor = listLoader.loadInBackground();
		Log.i("MyActivity", "onViewCreated - middle");

	    mAdapter = new MenuReadingListAdapter(getActivity(), cursor);
        mList.setAdapter(mAdapter);
        Log.i("MyActivity", "onViewCreated - end");
	}

	 /**
     * Cursor loader for the list of reading list items.
     */
    private static class MenuReadingListLoader extends CursorLoader {
        public MenuReadingListLoader(Context context) {
            super(context);
            Log.i("MyActivity", "MenuReadingListLoader Cursor loader");
        }

        @Override
        public Cursor loadInBackground() {
            return BrowserDB.getBookmarksInFolder(getContext().getContentResolver(), Bookmarks.FIXED_READING_LIST_ID);
        }
    }

    /**
     * LoaderCallbacks implementation that interacts with the LoaderManager.
     */
    private class CursorLoaderCallbacks implements LoaderCallbacks<Cursor> {
        @Override
        public Loader<Cursor> onCreateLoader(int id, Bundle args) {
            Log.i("MyActivity", "onCreateLoader Cursor loader");
            return new MenuReadingListLoader(getActivity());
        }

        @Override
        public void onLoadFinished(Loader<Cursor> loader, Cursor c) {
            mAdapter.swapCursor(c);
        }

        @Override
        public void onLoaderReset(Loader<Cursor> loader) {
            mAdapter.swapCursor(null);
        }
    }

     /**
     * Cursor adapter for the list of reading list items.
     */
    private class MenuReadingListAdapter extends CursorAdapter {
        public MenuReadingListAdapter(Context context, Cursor cursor) {
            super(context, cursor, 0);
        }

        @Override
        public void bindView(View view, Context context, Cursor cursor){
        	TextView tv = (TextView) view.findViewById(R.id.reading_list_item);
        	Log.i("MyActivity", "bindview setting textview");
        	tv.setText("Hej hooooo");

        }

        @Override
        public View newView(Context context, Cursor cursor, ViewGroup parent){
        	Log.i("MyActivity", "new view, dunno what it does");
        	return LayoutInflater.from(parent.getContext()).inflate(R.id.reading_list_item, parent, false);
        }
    }

}