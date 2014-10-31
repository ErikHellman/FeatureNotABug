package se.hellsoft.badservicebinding;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.widget.Toast;

public class MyService extends Service {
	private LocalBinder mBinder = null;

	public MyService() {
	}

	@Override
	public void onCreate() {
		super.onCreate();
		Toast.makeText(this, "onCreate called!", Toast.LENGTH_SHORT).show();
	}

	@Override
	public IBinder onBind(Intent intent) {
		Toast.makeText(this, "onBind called!", Toast.LENGTH_SHORT).show();
		return mBinder;
	}

	@Override
	public boolean onUnbind(Intent intent) {
		Toast.makeText(this, "onUnbind called!", Toast.LENGTH_SHORT).show();
		return super.onUnbind(intent);
	}

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		Toast.makeText(this, "onStartCommand called", Toast.LENGTH_SHORT).show();
		if(mBinder == null) {
			mBinder = new LocalBinder();
		}
		return START_NOT_STICKY;
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		Toast.makeText(this, "onDestroy called!", Toast.LENGTH_SHORT).show();
	}

	private class LocalBinder extends Binder {

	}
}
