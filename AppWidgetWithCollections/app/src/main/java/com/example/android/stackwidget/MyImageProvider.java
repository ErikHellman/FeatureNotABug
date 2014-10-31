package com.example.android.stackwidget;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.ParcelFileDescriptor;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class MyImageProvider extends ContentProvider {
	public static final int[] IMAGES = new int[] {R.drawable.beatles_abbey_road, R.drawable.beatles_sgt_peppers, R.drawable.gnr, R.drawable.michael_jackson, R.drawable.nirvana_nevermind, R.drawable.pink_floyd, R.drawable.ramones};
	private File mAlbumDir;

	public MyImageProvider() {
	}

	@Override
	public boolean onCreate() {
		Context context = getContext();
		mAlbumDir = context.getDir("albums", Context.MODE_PRIVATE);
		for (int i = 0; i < IMAGES.length; i++) {
			try {
				File albumPhoto = new File(mAlbumDir, "album-" + i + ".jpg");
				FileOutputStream albumPhotoStream = new FileOutputStream(albumPhoto, false);
				BitmapFactory.decodeResource(context.getResources(), IMAGES[i]).compress(Bitmap.CompressFormat.JPEG, 100, albumPhotoStream);
				albumPhotoStream.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return true;
	}

	@Override
	public String getType(Uri uri) {
		return "image/jpeg";
	}

@Override
public ParcelFileDescriptor openFile(Uri uri, String mode) throws FileNotFoundException {
File albumPhoto = new File(mAlbumDir, "album-" + uri.getLastPathSegment() + ".jpg");
return ParcelFileDescriptor.open(albumPhoto, ParcelFileDescriptor.MODE_READ_ONLY);
}

	@Override
	public int delete(Uri uri, String selection, String[] selectionArgs) {
		// Implement this to handle requests to delete one or more rows.
		throw new UnsupportedOperationException("Not yet implemented");
	}

	@Override
	public Uri insert(Uri uri, ContentValues values) {
		// TODO: Implement this to handle requests to insert a new row.
		throw new UnsupportedOperationException("Not yet implemented");
	}

	@Override
	public Cursor query(Uri uri, String[] projection, String selection,
	                    String[] selectionArgs, String sortOrder) {
		// TODO: Implement this to handle query requests from clients.
		throw new UnsupportedOperationException("Not yet implemented");
	}

	@Override
	public int update(Uri uri, ContentValues values, String selection,
	                  String[] selectionArgs) {
		// TODO: Implement this to handle requests to update one or more rows.
		throw new UnsupportedOperationException("Not yet implemented");
	}
}
