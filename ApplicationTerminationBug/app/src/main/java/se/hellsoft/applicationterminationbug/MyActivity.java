package se.hellsoft.applicationterminationbug;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Log;
import android.view.View;


public class MyActivity extends Activity implements ServiceConnection {
	public static final String TAG = "AppTermBug";

	private IMyAidlInterface mService;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_my);
	}

	@Override
	protected void onResume() {
		super.onResume();
		startService(new Intent(this, MyService.class));
		bindService(new Intent(this, MyService.class), this, BIND_AUTO_CREATE);
	}

	@Override
	protected void onPause() {
		mService = null;
		unbindService(this);
		super.onPause();
	}

	public void doStartPlaying(View view) {
		if (mService != null) {
			try {
				mService.startForegroundJob();
			} catch (RemoteException e) {
				Log.e(TAG, "Error calling remote method!", e);
			}
		}

/*
		AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
		long now = SystemClock.elapsedRealtime();
		PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 1, new Intent(MyReceiver.MY_ACTION), 0);
		alarmManager.set(AlarmManager.ELAPSED_REALTIME, now + 10000, pendingIntent);
*/
	}

	public void doStopPlaying(View view) {
		if (mService != null) {
			try {
				mService.stopForegroundJob();
			} catch (RemoteException e) {
				Log.e(TAG, "Error calling remote method!", e);
			}
		}
	}

@Override
public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
mService = IMyAidlInterface.Stub.asInterface(iBinder);
}

@Override
public void onServiceDisconnected(ComponentName componentName) {
mService = null;
}
}
