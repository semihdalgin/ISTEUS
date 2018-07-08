package com.example.isteus_v01;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.View;

public class MainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
	}

	public void KonumClick(View view) {
		startActivity(new Intent(this, KonumActivity.class));
	}

	public void BasincClick(View view) {
		startActivity(new Intent(this, BasincActivity.class));
	}

	public void CompareClick(View view) {
		startActivity(new Intent(this, CompareActivity.class));
	}

	public void IzleClick(View view) {
		startActivity(new Intent(this, Izle.class));
	}

	public void DisariVerClick(View view) {
		startActivity(new Intent(this, Deneme.class));
	}
}
