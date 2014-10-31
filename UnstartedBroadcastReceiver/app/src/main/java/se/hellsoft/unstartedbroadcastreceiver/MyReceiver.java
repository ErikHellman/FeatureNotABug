package se.hellsoft.unstartedbroadcastreceiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

public class MyReceiver extends BroadcastReceiver {

@Override
public void onReceive(Context context, Intent intent) {
    Toast.makeText(context, "Received broadcast: " + intent.getAction(), Toast.LENGTH_SHORT).show();
    Intent activity = new Intent(context, MyActivity.class);
    activity.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
    context.startActivity(activity);
}
}
