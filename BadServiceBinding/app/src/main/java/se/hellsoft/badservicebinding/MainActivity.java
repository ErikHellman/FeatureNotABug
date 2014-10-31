package se.hellsoft.badservicebinding;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.util.LinkedList;


public class MainActivity extends Activity {
	private LinkedList<MyServiceConn> mMyServiceConnList = new LinkedList<MyServiceConn>();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
	}

	public void doStartService(View view) {
		startService(new Intent(this, MyService.class));
	}

	public void doBindService(View view) {
		MyServiceConn myServiceConn = new MyServiceConn();
		mMyServiceConnList.add(myServiceConn);
		bindService(new Intent(this, MyService.class), myServiceConn, BIND_AUTO_CREATE);
		((TextView) findViewById(R.id.binding_counter)).setText("Binding count: " + mMyServiceConnList.size());
	}

	public void doUnbindService(View view) {
		if (mMyServiceConnList.size() > 0) {
			unbindService(mMyServiceConnList.removeLast());
			((TextView) findViewById(R.id.binding_counter)).setText("Binding count: " + mMyServiceConnList.size());
		}
	}

	public void doStopService(View view) {
		stopService(new Intent(this, MyService.class));
	}

	private class MyServiceConn implements ServiceConnection {
		@Override
		public void onServiceConnected(ComponentName name, IBinder service) {
			Toast.makeText(MainActivity.this, "onServiceConnected!", Toast.LENGTH_SHORT).show();
		}

		@Override
		public void onServiceDisconnected(ComponentName name) {
			Toast.makeText(MainActivity.this, "onServiceDisconnected!", Toast.LENGTH_SHORT).show();
		}
	}
}
