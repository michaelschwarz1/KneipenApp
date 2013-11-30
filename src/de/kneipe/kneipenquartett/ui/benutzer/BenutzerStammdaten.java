package de.kneipe.kneipenquartett.ui.benutzer;


import static de.kneipe.kneipenquartett.util.Constants.BENUTZER_KEY;
import android.app.Activity;
import android.app.Fragment;
import android.app.SearchManager;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.SearchView;
import android.widget.TextView;
import de.kneipe.R;
import de.kneipe.kneipenquartett.data.Benutzer;
import de.kneipe.kneipenquartett.ui.main.Prefs;
import de.kneipe.kneipenquartett.util.WischenListener;

public class BenutzerStammdaten extends Fragment {
	private static final String LOG_TAG = BenutzerStammdaten.class.getSimpleName();
	
	private Bundle args;
	private Benutzer benutzer;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		args = getArguments();
        benutzer = (Benutzer) args.get(BENUTZER_KEY);
        Log.d(LOG_TAG, benutzer.toString());

        // Voraussetzung fuer onOptionsItemSelected()
        setHasOptionsMenu(true);
        
		// attachToRoot = false, weil die Verwaltung des Fragments durch die Activity erfolgt
		return inflater.inflate(R.layout.benutzer_stammdaten, container, false);
	}
	
	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
		fillValues(view);
    	
	    final GestureDetector gestureDetector = new GestureDetector(getActivity(), new WischenListener(this));
	    view.setOnTouchListener(new View.OnTouchListener() {
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				return gestureDetector.onTouchEvent(event);
			}
		});
    }
	
	private void fillValues(View view) {
		final TextView txtId = (TextView) view.findViewById(R.id.benutzer_id);
    	txtId.setText(benutzer.uid.toString());
    	
    	final TextView txtNachname = (TextView) view.findViewById(R.id.nachname_txt);
    	txtNachname.setText(benutzer.nachname);
    	
    	final TextView txtVorname = (TextView) view.findViewById(R.id.vorname);
    	txtVorname.setText(benutzer.vorname);
    	
    	final TextView txtEmail = (TextView) view.findViewById(R.id.email);
    	txtEmail.setText(benutzer.email);
    	
    	final TextView txtPlz = (TextView) view.findViewById(R.id.geschlecht);
    	txtPlz.setText(benutzer.geschlecht);
   
   
	}

	@Override
	// http://developer.android.com/guide/topics/ui/actionbar.html#ChoosingActionItems :
	// "As a general rule, all items in the options menu (let alone action items) should have a global impact on the app,
	//  rather than affect only a small portion of the interface."
	// Nur aufgerufen, falls setHasOptionsMenu(true) in onCreateView() aufgerufen wird
	public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
		super.onCreateOptionsMenu(menu, inflater);
		inflater.inflate(R.menu.benutzer_stammdaten_options, menu);
		
		// "Searchable Configuration" in res\xml\searchable.xml wird der SearchView zugeordnet
		final Activity activity = getActivity();
	    final SearchManager searchManager = (SearchManager) activity.getSystemService(Context.SEARCH_SERVICE);
	    final SearchView searchView = (SearchView) menu.findItem(R.id.suchen).getActionView();
	    searchView.setSearchableInfo(searchManager.getSearchableInfo(activity.getComponentName()));
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
			case R.id.edit:
				// Evtl. vorhandene Tabs der ACTIVITY loeschen
		    	getActivity().getActionBar().removeAllTabs();
		    	
				final Fragment neuesFragment = new BenutzerEdit();
				neuesFragment.setArguments(args);
				
				// Kein Name (null) fuer die Transaktion, da die Klasse BackStageEntry nicht verwendet wird
				getFragmentManager().beginTransaction()
				                    .replace(R.id.details, neuesFragment)
				                    .addToBackStack(null)  
				                    .commit();
				return true;
				
			case R.id.einstellungen:
				getFragmentManager().beginTransaction()
                                    .replace(R.id.details, new Prefs())
                                    .addToBackStack(null)
                                    .commit();
				return true;

			default:
				return super.onOptionsItemSelected(item);
		}
	}
}
