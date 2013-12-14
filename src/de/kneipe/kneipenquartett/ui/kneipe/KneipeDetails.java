package de.kneipe.kneipenquartett.ui.kneipe;


import static android.app.ActionBar.NAVIGATION_MODE_TABS;
import static de.kneipe.kneipenquartett.util.Constants.KNEIPEN_KEY;
import static de.kneipe.kneipenquartett.util.Constants.KNEIPE_KEY;
import static java.net.HttpURLConnection.HTTP_OK;

import java.util.List;

import android.app.ActionBar;
import android.app.Activity;
import android.app.Fragment;
import android.app.ActionBar.Tab;
import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import de.kneipe.R;
import de.kneipe.kneipenquartett.data.Bewertung;
import de.kneipe.kneipenquartett.data.Gutschein;
import de.kneipe.kneipenquartett.data.Kneipe;
import de.kneipe.kneipenquartett.service.HttpResponse;
import de.kneipe.kneipenquartett.service.KneipeService.KneipeServiceBinder;
import de.kneipe.kneipenquartett.ui.benutzer.BenutzerCreate;
import de.kneipe.kneipenquartett.ui.benutzer.BenutzerStammdaten;
import de.kneipe.kneipenquartett.ui.main.Main;
import de.kneipe.kneipenquartett.ui.main.Prefs;
import de.kneipe.kneipenquartett.util.TabListener;

public class KneipeDetails extends Fragment {

	private static final String LOG_TAG = KneipeDetails.class.getSimpleName();
	private Kneipe kneipe;
	private List<Long> bewertungIds;
	private KneipeServiceBinder kneipeServiceBinder;
	private Bundle args;

//	private LazyAdapter adapter;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		kneipe = (Kneipe) getArguments().get(KNEIPE_KEY);
		Log.d(LOG_TAG, kneipe.toString());
		args = getArguments();
		setHasOptionsMenu(false);
		// attachToRoot = false, weil die Verwaltung des Fragments durch die
		// Activity erfolgt
		return inflater.inflate(R.layout.kneipe_details, container, false);
	}

	@Override
	// Nur aufgerufen, falls setHasOptionsMenu(true) in onCreateView()
	// aufgerufen wird
	public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
		super.onCreateOptionsMenu(menu, inflater);
		inflater.inflate(R.menu.main, menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.einstellungen:
			getFragmentManager().beginTransaction()
					.replace(R.id.details, new Prefs()).addToBackStack(null)
					.commit();
			return true;

		default:
			return super.onOptionsItemSelected(item);
		}
	}

	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
		final Activity activity = getActivity();
		final ActionBar actionBar = activity.getActionBar();
		actionBar.removeAllTabs();
		// (horizontale) Tabs; NAVIGATION_MODE_LIST fuer Dropdown Liste
		actionBar.setNavigationMode(NAVIGATION_MODE_TABS);
		actionBar.setDisplayShowTitleEnabled(false); // Titel der App
														// ausblenden, um mehr
														// Platz fuer die Tabs
														// zu haben
		Tab tab = actionBar.newTab()
				.setText("Kneipen")
				.setTabListener(new TabListener<KneipeSucheKategorie>(activity,KneipeSucheKategorie.class, args));

		actionBar.addTab(tab);
		
		 tab = actionBar.newTab()
							.setText("Profil")
							.setTabListener(new TabListener<BenutzerStammdaten>(activity, BenutzerStammdaten.class, args));

		actionBar.addTab(tab);
		Log.v(LOG_TAG,"tablistener");

//		final Bundle args = new Bundle(1);
//		args.putSerializable(KNEIPEN_KEY, kneipe);
		final TextView txtId = (TextView) view.findViewById(R.id.txt_KneipeName);
		txtId.setText(kneipe.name);

		final TextView txtPreis = (TextView) view
				.findViewById(R.id.txt_KneipeInternetadresse);
		txtPreis.setText(kneipe.internetadresse);

	 /*Hab hier auskommentiert um emulator starten zu k�nnen
	  * final TextView txtverf = (TextView) view.findViewById(R.id.kunde_adresse);
		txtverf.setText(kneipe.adresse);*/

		// if (Main.class.equals(activity.getClass())) {
		Main main = (Main) activity;
		kneipeServiceBinder = main.getKneipeServiceBinder();
		
		
		
	}
	
	public void onClick(View view) {
		final Context ctx = view.getContext();
		switch(view.getId()){
		case R.id.btn_bewertung:	

				
				
			 
//				List<Kneipe> k = suchen2(view, kneipeNameStr);
//				//if(k==null)System.out.println("Fehler");
//				//Log.v(LOG_TAG,k.toString());
//			
//					

					Log.v(LOG_TAG, "bundle key anlegen");
					
					Fragment nf = new BewertungCreate();
					nf.setArguments(args);
					
					Log.v(LOG_TAG,"Fragment BewertungCreate aufrufen");
					
					getFragmentManager().beginTransaction()
		            .replace(R.id.details, nf)
		            .commit();

				
				
				break;
			
			
				
			
		case R.id.btn_reg:
			getFragmentManager().beginTransaction()
			.replace(R.id.details, new BenutzerCreate())
			.commit();
			break;
		}
		
		// Eingabetext ermitteln
		
			
			
	}
		

}

