package edu.wctc.android.pedometer;

import android.app.Activity;
import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity {

	private LocationManager locationManager;
	private LocationListener locationListener;
	private Location startLocation;
	private Location endLocation;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
		locationListener = new SimpleLocationListener();
		locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0,
				0, locationListener);

		findViewById(R.id.button1).setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				startLocation = locationManager
						.getLastKnownLocation(LocationManager.GPS_PROVIDER);

				((TextView) findViewById(R.id.textView2)).setText(String
						.valueOf(startLocation.getLatitude())
						+ ", "
						+ String.valueOf(startLocation.getLongitude()));
			}
		});

		findViewById(R.id.button2).setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				endLocation = locationManager
						.getLastKnownLocation(LocationManager.GPS_PROVIDER);

				((TextView) findViewById(R.id.textView3)).setText(String
						.valueOf(endLocation.getLatitude())
						+ ", "
						+ String.valueOf(endLocation.getLongitude()));

				((TextView) findViewById(R.id.textView1)).setText(String
						.valueOf(endLocation.distanceTo(startLocation)));
			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_main, menu);
		return true;
	}

	@Override
	protected void onPause() {
		super.onPause(); // stop receiving GPS
		locationManager.removeUpdates(locationListener);
	}

	@Override
	protected void onResume() {
		super.onResume(); // restart receiving GPS
		locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0,
				0, locationListener);
	}

	private class SimpleLocationListener implements LocationListener {

		@Override
		public void onLocationChanged(Location location) {
			if (location != null) {
				Toast.makeText(MainActivity.this,
						location.getLatitude() + "" + location.getLongitude(),
						Toast.LENGTH_LONG).show();
			}
		}

		@Override
		public void onProviderDisabled(String provider) {
		}

		@Override
		public void onProviderEnabled(String provider) {
		}

		@Override
		public void onStatusChanged(String provider, int status, Bundle extras) {
		}
	}
}