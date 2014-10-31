package se.hellsoft.applicationterminationbug;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

public class MyReceiver extends BroadcastReceiver {
	public static final String MY_ACTION = "se.hellsoft.applicationterminationbug.MY_ACTION";

    @Override
    public void onReceive(Context context, Intent intent) {
	    Log.d(MyActivity.TAG, "Received broadcast: " + intent);
	    Toast.makeText(context, "Received broadast!!!", Toast.LENGTH_SHORT).show();
    }
}
