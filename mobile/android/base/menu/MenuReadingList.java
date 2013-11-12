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


public class MenuReadingList extends Fragment {

	// The view shown by the fragment
    private ListView mList;

    private SimpleCursorAdapter mAdapter;

	@Override
	public View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroupContainer,
		Bundle savedInstanceState) {
		return layoutInflater.inflate(R.layout.menu_reading_list, viewGroupContainer, false);
	}

	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {	    
		super.onCreate(savedInstanceState);
	    mList = (ListView) view.findViewById(R.id.list);

		MenuReadingListLoader listLoader = new MenuReadingListLoader(getContext());
		Cursor cursor = listLoader.loadInBackground();

	    mAdapter = new MenuReadingListAdapter(getActivity(), cursor);
        mList.setAdapter(mAdapter);
	}

	 /**
     * Cursor loader for the list of reading list items.
     */
    private static class MenuReadingListLoader extends CursorLoader {
        public MenuReadingListLoader(Context context) {
            super(context);
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
    }

}