package de.kneipe.kneipenquartett.util;

import static android.app.ActionBar.NAVIGATION_MODE_TABS;
import android.app.ActionBar;
import android.app.ActionBar.Tab;
import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import de.kneipe.R;
import de.kneipe.kneipenquartett.data.Benutzer;
import de.kneipe.kneipenquartett.ui.benutzer.BenutzerStammdaten;
import de.kneipe.kneipenquartett.ui.main.Login;


public class Startseite extends Fragment {
	
	private static final String LOG_TAG = Startseite.class.getSimpleName();
	
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		// attachToRoot = false, weil die Verwaltung des Fragments durch die Activity erfolgt
		Log.v(LOG_TAG,"hallo");
		return inflater.inflate(R.layout.startseite, container, false);
	}
	

	Benutzer benutzer = (Benutzer) getArguments() .get("be");
	
	
	@Override
	public void onViewCreated(View view, Bundle savedInstanceState){
		Log.v(LOG_TAG,"onViewCREATED DER STARTSEITE!!");
		final Activity a = getActivity();
		ActionBar actionBar = a.getActionBar();
		actionBar.setNavigationMode(NAVIGATION_MODE_TABS);
		actionBar.setDisplayShowTitleEnabled(false);
		Bundle args = new Bundle(1);
		args.putSerializable("be", benutzer);
		
		Tab tab = actionBar.newTab()
							.setText("Profil")
							.setTabListener(new TabListener<BenutzerStammdaten>(a, BenutzerStammdaten.class, args));
		
		actionBar.addTab(tab);
		
//		tab = actionBar.newTab()
//				.setText("Kneipen")
//				.setTabListener(new TabListener<KneipeStammdaten>(a, BenutzerStammdaten.class, args));
//
//actionBar.addTab(tab);
				 
	}
	
	
}
