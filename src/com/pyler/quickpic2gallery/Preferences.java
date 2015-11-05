package com.pyler.quickpic2gallery;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceActivity;
import android.preference.PreferenceManager;

public class Preferences extends PreferenceActivity implements
		SharedPreferences.OnSharedPreferenceChangeListener {
	@SuppressWarnings("deprecation")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		getPreferenceManager().setSharedPreferencesMode(MODE_WORLD_READABLE);
		addPreferencesFromResource(R.xml.prefs);
		SharedPreferences prefs = PreferenceManager
				.getDefaultSharedPreferences(this);
		prefs.registerOnSharedPreferenceChangeListener(this);
	}

	public void onSharedPreferenceChanged(SharedPreferences prefs, String key) {
		boolean isCustomName = prefs.getBoolean("custom_name", false);
		String customQuickPicName = prefs.getString("quickpic_name", "Gallery");
		@SuppressWarnings("deprecation")
		Preference quickpicName = findPreference(
				"quickpic_name");
		quickpicName.setSummary(customQuickPicName);
		quickpicName.setEnabled(isCustomName);
	}
}