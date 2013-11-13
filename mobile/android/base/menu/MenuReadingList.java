package org.mozilla.gecko.menu;

import org.mozilla.gecko.R;
import org.mozilla.gecko.db.BrowserContract.Bookmarks;
import org.mozilla.gecko.db.BrowserDB;
import org.mozilla.gecko.home.SimpleCursorLoader;

import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.os.Bundle;
import android.view.View;
import android.util.Log;
import android.support.v4.app.Fragment;
import android.widget.ListView;
import android.database.Cursor;
import android.content.Context;
import android.widget.TextView;
import android.support.v4.app.LoaderManager.LoaderCallbacks;
import android.support.v4.content.Loader;
import android.support.v4.widget.CursorAdapter;


public class MenuReadingList extends Fragment {

	// The view shown by the fragment
    private ListView mList;

    private MenuReadingListAdapter mAdapter;

    private CursorLoaderCallbacks mCursorLoaderCallbacks;

    private boolean mIsLoaded;

	@Override
	public View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroupContainer,
		Bundle savedInstanceState) {
		View view = layoutInflater.inflate(R.layout.menu_reading_list, viewGroupContainer, false);
		return view;
	}

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        mIsLoaded = false;
    }

	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {	    
		super.onCreate(savedInstanceState);
	    mList = (ListView) view.findViewById(R.id.list);
	}

	@Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
	    mAdapter = new MenuReadingListAdapter(getActivity(), null);
        mList.setAdapter(mAdapter);

        mCursorLoaderCallbacks = new CursorLoaderCallbacks();

        loadIfVisible();
    }

    protected void loadIfVisible() {
        if(!mIsLoaded){
            getLoaderManager().initLoader(0, null, mCursorLoaderCallbacks);
            mIsLoaded = true;
        }
    }
    /**
     * Cursor loader for the list of reading list items.
     */
    private static class MenuReadingListLoader extends SimpleCursorLoader {
        public MenuReadingListLoader(Context context) {
            super(context);
        }

        @Override
        public Cursor loadCursor() {
            Cursor cursor = BrowserDB.getBookmarksInFolder(getContext().getContentResolver(), Bookmarks.FIXED_READING_LIST_ID);
            return cursor;
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
        	TextView tv = (TextView) view;
        	tv.setText("Test");
        }

        @Override
        public View newView(Context context, Cursor cursor, ViewGroup parent){
        	return LayoutInflater.from(parent.getContext()).inflate(R.layout.menu_reading_list_item, parent, false);
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

}