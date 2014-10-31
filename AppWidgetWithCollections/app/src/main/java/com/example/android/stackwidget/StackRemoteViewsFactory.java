package com.example.android.stackwidget;

import android.appwidget.AppWidgetManager;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 *
 */
class StackRemoteViewsFactory implements RemoteViewsService.RemoteViewsFactory {
	public static final int[] IMAGES = new int[] {R.drawable.beatles_abbey_road, R.drawable.beatles_sgt_peppers, R.drawable.gnr, R.drawable.michael_jackson, R.drawable.nirvana_nevermind, R.drawable.pink_floyd, R.drawable.ramones};
	private static final int mCount = IMAGES.length;
	private List<WidgetItem> mWidgetItems = new ArrayList<WidgetItem>();
	private Context mContext;
	private int mAppWidgetId;

	public StackRemoteViewsFactory(Context context, Intent intent) {
		mContext = context;
		mAppWidgetId = intent.getIntExtra(AppWidgetManager.EXTRA_APPWIDGET_ID,
				AppWidgetManager.INVALID_APPWIDGET_ID);
	}

	public void onCreate() {
		// In onCreate() you setup any connections / cursors to your data source. Heavy lifting,
		// for example downloading or creating content etc, should be deferred to onDataSetChanged()
		// or getViewAt(). Taking more than 20 seconds in this call will result in an ANR.
		for (int i = 0; i < mCount; i++) {
			mWidgetItems.add(new WidgetItem(i + "!"));
		}

		// We sleep for 3 seconds here to show how the empty view appears in the interim.
		// The empty view is set in the StackWidgetProvider and should be a sibling of the
		// collection view.
		try {
			Thread.sleep(3000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	public void onDestroy() {
		// In onDestroy() you should tear down anything that was setup for your data source,
		// eg. cursors, connections, etc.
		mWidgetItems.clear();
	}

	public int getCount() {
		return mCount;
	}

	public RemoteViews getViewAt(int position) {
		// position will always range from 0 to getCount() - 1.

		// We construct a remote views item based on our widget item xml file, and set the
		// text based on the position.
		RemoteViews rv = new RemoteViews(mContext.getPackageName(), R.layout.photo_item);
		Bitmap albumPhoto = BitmapFactory.decodeResource(mContext.getResources(), IMAGES[position]);

//		rv.setImageViewBitmap(R.id.widget_item, albumPhoto);



//		long oldIdentity = Binder.clearCallingIdentity();
		try {
			Uri albumUri = Uri.parse("content://com.example.android.stackwidget.photo/albumPhoto/" + position);
			ContentResolver contentResolver = mContext.getContentResolver();
			InputStream inputStream = contentResolver.openInputStream(albumUri);
			albumPhoto = BitmapFactory.decodeStream(inputStream);
			rv.setImageViewBitmap(R.id.widget_item, albumPhoto);
		} catch (FileNotFoundException e) {
			Log.e("StackWidget", "Error loading album photo!", e);
		} finally {
//			Binder.restoreCallingIdentity(oldIdentity);
		}


		// Next, we set a fill-intent which will be used to fill-in the pending intent template
		// which is set on the collection view in StackWidgetProvider.
		Bundle extras = new Bundle();
		extras.putInt(StackWidgetProvider.EXTRA_ITEM, position);
		Intent fillInIntent = new Intent();
		fillInIntent.putExtras(extras);
		rv.setOnClickFillInIntent(R.id.widget_item, fillInIntent);

		// Return the remote views object.
		return rv;
	}

	public RemoteViews getLoadingView() {
		// You can create a custom loading view (for instance when getViewAt() is slow.) If you
		// return null here, you will get the default loading view.
		return null;
	}

	public int getViewTypeCount() {
		return 1;
	}

	public long getItemId(int position) {
		return position;
	}

	public boolean hasStableIds() {
		return true;
	}

	public void onDataSetChanged() {
		// This is triggered when you call AppWidgetManager notifyAppWidgetViewDataChanged
		// on the collection view corresponding to this factory. You can do heaving lifting in
		// here, synchronously. For example, if you need to process an image, fetch something
		// from the network, etc., it is ok to do it here, synchronously. The widget will remain
		// in its current state while work is being done here, so you don't need to worry about
		// locking up the widget.
	}
}
