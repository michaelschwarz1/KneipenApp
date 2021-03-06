package de.kneipe.kneipenquartett.ui.main;

import static de.kneipe.kneipenquartett.util.Constants.BENUTZER_KEY;

import java.util.List;

import android.app.ActionBar;
import android.app.Activity;
import android.app.Fragment;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;
import android.view.Menu;
import android.view.WindowManager;
import de.kneipe.R;
import de.kneipe.kneipenquartett.data.Benutzer;
import de.kneipe.kneipenquartett.data.Kneipe;
import de.kneipe.kneipenquartett.service.BenutzerService;
import de.kneipe.kneipenquartett.service.BenutzerService.BenutzerServiceBinder;
import de.kneipe.kneipenquartett.service.BewertungService.BewertungServiceBinder;
import de.kneipe.kneipenquartett.service.GutscheinService.GutscheinServiceBinder;
import de.kneipe.kneipenquartett.service.KneipeService;
import de.kneipe.kneipenquartett.service.KneipeService.KneipeServiceBinder;

public class Main extends Activity  {

	public List<Kneipe> kneipenArray;
	public KneipeService ks = new KneipeService();

	private static final String LOG_TAG = Main.class.getSimpleName();
	
	private BenutzerServiceBinder benutzerServiceBinder;
	private KneipeServiceBinder kneipeServiceBinder;
	private GutscheinServiceBinder gutscheinServiceBinder;
	private BewertungServiceBinder bewertungServiceBinder;
	
	
	
	private ServiceConnection benutzerServiceConnection = new ServiceConnection() {
		@Override
		public void onServiceConnected(ComponentName name, IBinder serviceBinder) {
			Log.v(LOG_TAG, "onServiceConnected() fuer benutzerServiceBinder");
			benutzerServiceBinder = (BenutzerServiceBinder) serviceBinder;
			
		}

		@Override
		public void onServiceDisconnected(ComponentName name) {
			benutzerServiceBinder = null;
		}
	};
	
	private ServiceConnection kneipeServiceConnection = new ServiceConnection() {
		@Override
		public void onServiceConnected(ComponentName name, IBinder serviceBinder) {
			Log.v(LOG_TAG, "onServiceConnected() fuer KundeServiceBinder");
			kneipeServiceBinder = (KneipeServiceBinder) serviceBinder;
		}

		@Override
		public void onServiceDisconnected(ComponentName name) {
			kneipeServiceBinder = null;
		}
	};
	
	public BenutzerServiceBinder getBenutzerServiceBinder() {
		return benutzerServiceBinder;
	}
	
	
	public KneipeServiceBinder getKneipeServiceBinder() {
		return kneipeServiceBinder;
	}
	
	public GutscheinServiceBinder getGutscheinServiceBinder() {
		return gutscheinServiceBinder;
	}
	
	public BewertungServiceBinder getBewertungServiceBinder() {
		return bewertungServiceBinder;
	}


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		ActionBar ab = getActionBar();
		ab.setTitle("KneipenQuartett");
		setContentView(R.layout.main);
		  Fragment detailsFragment = null;
	        final Bundle extras = getIntent().getExtras();
	        if (extras == null) {
	        	// Keine Suchergebnisse o.ae. vorhanden
	        	
	        	detailsFragment = new Login();
	        	
	          // Preferences laden
	          Prefs.init(this);
	        }
	        else {
		        final Benutzer benutzer = (Benutzer) extras.get(BENUTZER_KEY);
		        if (benutzer != null) {
		        	Log.d(LOG_TAG, benutzer.toString());
		        	
		    		final Bundle args = new Bundle(1);
		    		args.putSerializable(BENUTZER_KEY, benutzer);
		    		
		        	//detailsFragment = new KundeDetails();
		        	detailsFragment.setArguments(args);
		        }
	        }
	        getWindow().setSoftInputMode(
	        	      WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
	        getFragmentManager().beginTransaction()
	                            .add(R.id.details, detailsFragment)
	                            .addToBackStack(null)
	                            .commit();
	        
		
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return false;
	}
	
	
	
	   @Override
		public void onStart() {
			super.onStart();
		
			Intent intent = new Intent(this, BenutzerService.class);
			bindService(intent, benutzerServiceConnection, Context.BIND_AUTO_CREATE);
			
			intent = new Intent(this, KneipeService.class);
			bindService(intent, kneipeServiceConnection, Context.BIND_AUTO_CREATE);
			
	    }
	    
		@Override
		public void onStop() {
			super.onStop();
			
			unbindService(benutzerServiceConnection);
			unbindService(kneipeServiceConnection);
//			unbindService(artikelServiceConnection);
		}
		
		
}
