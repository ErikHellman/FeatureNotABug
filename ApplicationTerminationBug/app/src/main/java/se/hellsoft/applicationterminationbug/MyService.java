package se.hellsoft.applicationterminationbug;

import android.app.Notification;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.IBinder;
import android.os.RemoteException;

public class MyService extends Service {
	private Handler mHandler;
	private MediaPlayer mPlayer;

	private final MyBinder mMyBinder = new MyBinder();

	@Override
	public IBinder onBind(Intent intent) {
		return mMyBinder;
	}

	public void startForegroundJob() {
		Notification.Builder builder = new Notification.Builder(this);
		builder.setContentTitle("Foreground job").setContentText("This service is running in the foreground!");
		builder.setSmallIcon(R.drawable.ic_launcher).setOngoing(true);
		builder.setContentIntent(PendingIntent.getActivity(this, 1, new Intent(this, MyActivity.class), 0));
		startForeground(1, builder.getNotification());

		HandlerThread handlerThread = new HandlerThread("MusicThread");
		handlerThread.start();
		mHandler = new Handler(handlerThread.getLooper());
		Runnable runnable = new Runnable() {
			public void run() {
				mPlayer = MediaPlayer.create(MyService.this, R.raw.hyperfun);
				mPlayer.start();
			}
		};
		mHandler.post(runnable);
	}

	public void stopForegroundJob() {
		stopForeground(true);
		mHandler.getLooper().quit();
		if (mPlayer != null) {
			mPlayer.release();
			mPlayer = null;
		}
	}

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		return START_NOT_STICKY;
	}

	private class MyBinder extends IMyAidlInterface.Stub {
		@Override
		public void startForegroundJob() throws RemoteException {
			MyService.this.startForegroundJob();
		}

		@Override
		public void stopForegroundJob() throws RemoteException {
			MyService.this.stopForegroundJob();
		}
	}

/*
@Override
public void onTaskRemoved(Intent rootIntent) {
super.onTaskRemoved(rootIntent);
stopForegroundJob();
}
*/
}
