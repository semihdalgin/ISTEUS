package com.example.isteus_v01;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

import com.google.android.maps.GeoPoint;
import com.google.android.maps.MapActivity;
import android.content.Context;
import android.location.Address;
import android.location.Criteria;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.widget.TextView;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

public class KonumActivity extends MapActivity {
	private GoogleMap mapView;

	@Override
	protected boolean isRouteDisplayed() {
		return false;
	}

	// private MapView mapView;
	// private MapController mapController;
	KonumOverlay positionOverlay;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_konum);

		mapView = ((MapFragment) getFragmentManager()
				.findFragmentById(R.id.map)).getMap();
		mapView.setMapType(GoogleMap.MAP_TYPE_SATELLITE);

		// mapView = (MapView) this.findViewById(R.id.myMapView);
		// mapView.setBuiltInZoomControls(true);

		// mapController = this.mapView.getController();
		// mapController.setZoom(2);

		// MapView myMapView = (MapView)findViewById(R.id.myMapView);
		// mapController = mapView.getController();

		// Configure the map display options
		// myMapView.setSatellite(true);
		// myMapView.setStreetView(true);

		// Zoom in
		// mapController.setZoom(17);

		// Add the KonumOverlay
		// positionOverlay = new KonumOverlay();
		// List<Overlay> overlays = mapView.getOverlays();
		// overlays.add(positionOverlay);

		LocationManager locationManager;
		String context = Context.LOCATION_SERVICE;
		locationManager = (LocationManager) getSystemService(context);

		Criteria criteria = new Criteria();
		criteria.setAccuracy(Criteria.ACCURACY_FINE);
		criteria.setAltitudeRequired(false);
		criteria.setBearingRequired(false);
		criteria.setCostAllowed(true);
		criteria.setPowerRequirement(Criteria.POWER_LOW);
		String provider = locationManager.getBestProvider(criteria, true);

		Location location = locationManager.getLastKnownLocation(provider);

		updateWithNewLocation(location);

		locationManager.requestLocationUpdates(provider, 2000, 1,
				locationListener);
	}

	private final LocationListener locationListener = new LocationListener() {
		@Override
		public void onLocationChanged(Location location) {
			updateWithNewLocation(location);
		}

		@Override
		public void onProviderDisabled(String provider) {
			updateWithNewLocation(null);
		}

		@Override
		public void onProviderEnabled(String provider) {
		}

		@Override
		public void onStatusChanged(String provider, int status, Bundle extras) {
		}
	};

	private void updateWithNewLocation(Location location) {
		String latLongString;
		TextView myLocationText;
		myLocationText = (TextView) findViewById(R.id.myLocationText);
		String addressString = "Adres Bulunamadý";

		if (location != null) {
			// Update my location marker
			positionOverlay.setLocation(location);

			// Update the map location.
			Double geoLat = location.getLatitude() * 1E6;
			Double geoLng = location.getLongitude() * 1E6;
			GeoPoint point = new GeoPoint(geoLat.intValue(), geoLng.intValue());

			// mapController.animateTo(point);

			final LatLng CIU = new LatLng(geoLat, geoLng);

			Marker ciu = mapView.addMarker(new MarkerOptions().position(CIU)
					.title("ISTEUS"));

			double lat = location.getLatitude();
			double lng = location.getLongitude();
			latLongString = "Enlem:" + lat + "\nBoylam:" + lng;

			double latitude = location.getLatitude();
			double longitude = location.getLongitude();

			Geocoder gc = new Geocoder(this, Locale.getDefault());
			try {
				List<Address> addresses = gc.getFromLocation(latitude,
						longitude, 1);
				StringBuilder sb = new StringBuilder();
				if (addresses.size() > 0) {
					Address address = addresses.get(0);

					for (int i = 0; i < address.getMaxAddressLineIndex(); i++)
						sb.append(address.getAddressLine(i)).append("\n");

					sb.append(address.getLocality()).append("\n");
					sb.append(address.getPostalCode()).append("\n");
					sb.append(address.getCountryName());
				}
				addressString = sb.toString();
			} catch (IOException e) {
			}
		} else {
			latLongString = "Konum Bulunamadý";
		}
		myLocationText.setText("Mevcut Konumum:\n" + latLongString + "\n"
				+ addressString);
	}
}