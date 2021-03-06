package de.kneipe.kneipenquartett.service;

import static android.app.ProgressDialog.STYLE_SPINNER;
import static de.kneipe.kneipenquartett.ui.main.Prefs.timeout;
import static de.kneipe.kneipenquartett.util.Constants.KUNDEN_ID_PREFIX_PATH;
import static de.kneipe.kneipenquartett.util.Constants.BENUTZER_PATH;
import static de.kneipe.kneipenquartett.util.Constants.BEWERTUNG_PATH;
import static de.kneipe.kneipenquartett.util.Constants.USERNAMEN_PATH;
import static de.kneipe.kneipenquartett.util.Constants.USERNAMEN_PREFIX_PATH;
import static java.net.HttpURLConnection.HTTP_NO_CONTENT;
import static java.net.HttpURLConnection.HTTP_OK;
import static java.util.concurrent.TimeUnit.SECONDS;

import java.util.List;

import android.app.ProgressDialog;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Binder;
import android.os.IBinder;
import android.util.Log;
import android.webkit.WebView.FindListener;
import de.kneipe.R;
import de.kneipe.kneipenquartett.data.Benutzer;
import de.kneipe.kneipenquartett.data.Bewertung;
import de.kneipe.kneipenquartett.data.Gutschein;
import de.kneipe.kneipenquartett.util.InternalShopError;


public class BenutzerService extends Service {
	private static final String LOG_TAG = BenutzerService.class.getSimpleName();
	
	private BenutzerServiceBinder binder = new BenutzerServiceBinder();
	
//	static {
//		// 2 Eintraege in die HashMap mit 100% = 1.0 Fuellgrad
//	}

	@Override
	public IBinder onBind(Intent intent) {
		return binder;
	}
	
	public class BenutzerServiceBinder extends Binder {
		
		public BenutzerService getService() {
			return BenutzerService.this;
		}
		

		
		/**
		 */
		public HttpResponse<Benutzer> sucheBenutzerByEmail(String email, final Context ctx) {
			
			// (evtl. mehrere) Parameter vom Typ "Long", Resultat vom Typ "Benutzer"
			final AsyncTask<String, Void, HttpResponse<Benutzer>> sucheBenutzerByEmailTask = new AsyncTask<String, Void, HttpResponse<Benutzer>>() {
				
				@Override
				// Neuer Thread, damit der UI-Thread nicht blockiert wird
				protected HttpResponse<Benutzer> doInBackground(String... emails) {
					final String email = emails[0];
		    		final String path = BENUTZER_PATH + "/" + email;
		    		Log.v(LOG_TAG, "path = " + path);
		    		final HttpResponse<Benutzer> result = WebServiceClient.getJsonSingle(path, Benutzer.class);
					Log.d(LOG_TAG + ".AsyncTask", "doInBackground: " + result);
					return result;
				}

			};

    		sucheBenutzerByEmailTask.execute(email);
    		HttpResponse<Benutzer> result = null;
	    	try {
	    		result = sucheBenutzerByEmailTask.get(timeout, SECONDS);
			}
	    	catch (Exception e) {
	    		throw new InternalShopError(e.getMessage(), e);
			}
	    	
    		if (result.responseCode != HTTP_OK) {
	    		return result;
		    }
    		
    	//	setBestellungenUri(result.resultObject);
		    return result;
		}
		
		public HttpResponse<Gutschein> sucheGutscheinByUserID(Long id, final Context ctx) {
			
			final AsyncTask<Long, Void, HttpResponse<Gutschein>> sucheGutscheinByUserIDTask = new AsyncTask<Long, Void, HttpResponse<Gutschein>>() {
				protected HttpResponse<Gutschein> doInBackground(Long... ids) {
					final Long id = ids[0];
					final String path = BENUTZER_PATH + "/" + id + "/gutschein";
					Log.v(LOG_TAG, "path = " + path);
					
					//macht er nicht
					final HttpResponse<Gutschein> resultList = WebServiceClient.getJsonList(path, Gutschein.class);
					Log.d(LOG_TAG + ".AsyncTask", "doInBackground: " + resultList);
				
					return resultList;
					
				}
			};
			
			sucheGutscheinByUserIDTask.execute(id);
    		HttpResponse<Gutschein> result = null;
	    	try {
	    		result = sucheGutscheinByUserIDTask.get(timeout, SECONDS);
			}
	    	catch (Exception e) {
	    		throw new InternalShopError(e.getMessage(), e);
			}
	    	
    		if (result.responseCode != HTTP_OK) {
	    		return result;
		    }
    		
		    return result;
		}

		public HttpResponse<Gutschein> updateGutschein (final Long id,Gutschein gutschein, final Context ctx) {
			// (evtl. mehrere) Parameter vom Typ "Kunde", Resultat vom Typ "void"
			final AsyncTask<Gutschein, Void, HttpResponse<Gutschein>> updateGutscheinTask = new AsyncTask<Gutschein, Void, HttpResponse<Gutschein>>() {
				
				@Override
				// Neuer Thread, damit der UI-Thread nicht blockiert wird
				protected HttpResponse<Gutschein> doInBackground(Gutschein... gutschein) {
					final Gutschein gut = gutschein[0];
					
		    		final String path = "/gutschein";
		    		Log.v(LOG_TAG, "path = " + path);

		    		final HttpResponse<Gutschein> result = WebServiceClient.putJson(gut, path);
					Log.d(LOG_TAG + ".AsyncTask", "doInBackground: " + result);
					return result;
				}
				
			};
			
			updateGutscheinTask.execute(gutschein);
			final HttpResponse<Gutschein> result;
			try {
				result = updateGutscheinTask.get(timeout, SECONDS);
			}
	    	catch (Exception e) {
	    		throw new InternalShopError(e.getMessage(), e);
			}
			
			
				result.resultObject = gutschein;
			
			
			return result;
	    }
		
		public HttpResponse<Bewertung> createBewertung(Bewertung bw, final Context ctx) {
			Log.d(LOG_TAG,"create benutzer vom ServiceBinder wird aufgerufen");
			// (evtl. mehrere) Parameter vom Typ "Benutzer", Resultat vom Typ "void"
			final AsyncTask<Bewertung, Void, HttpResponse<Bewertung>> createBewertungTask = new AsyncTask<Bewertung, Void, HttpResponse<Bewertung>>() {

				
				@Override
				// Neuer Thread, damit der UI-Thread nicht blockiert wird
				protected HttpResponse<Bewertung> doInBackground(Bewertung... bewertung) {
					final Bewertung be = bewertung[0];
		    		final String path = BEWERTUNG_PATH;
		    		Log.v(LOG_TAG, "path = " + path);
		    		Log.v(LOG_TAG, bewertung.toString());
		    		Log.v(LOG_TAG,"jz kommmmmmmmmmmmmmmmmmmmmt jsssssssssssssssssssssssssssoooooooooooooooooooooooooooooooooooooooooooooooon!!!");
		    		final HttpResponse<Bewertung> result = WebServiceClient.postJson(be, path);
		    		Log.v(LOG_TAG,"WebServiceClient.postJson durchgelaufen!!");
					Log.d(LOG_TAG + ".AsyncTask", "doInBackground: " + result);
					return result;
				}
				
			};
			
			createBewertungTask.execute(bw);
			HttpResponse<Bewertung> response = null; 
			try {
				
				response = createBewertungTask.get(1000, SECONDS);
			}
	    	catch (Exception e) {
	    		throw new InternalShopError(e.getMessage(), e);
			}
			
			bw.bid = Long.valueOf(response.content);
			final HttpResponse<Bewertung> result = new HttpResponse<Bewertung>(response.responseCode, response.content, bw);
			return result;
	    }
		
		/**
		 */
		public HttpResponse<Benutzer> updateBenutzer(Benutzer be, final Context ctx) {
			// (evtl. mehrere) Parameter vom Typ "Benutzer", Resultat vom Typ "void"
			final AsyncTask<Benutzer, Void, HttpResponse<Benutzer>> updateBenutzerTask = new AsyncTask<Benutzer, Void, HttpResponse<Benutzer>>() {

				
				@Override
				// Neuer Thread, damit der UI-Thread nicht blockiert wird
				protected HttpResponse<Benutzer> doInBackground(Benutzer... benutzer) {
					final Benutzer be = benutzer[0];
		    		final String path = BENUTZER_PATH;
		    		Log.v(LOG_TAG, "path = " + path);

		    		final HttpResponse<Benutzer> result = WebServiceClient.putJson(be, path);
					Log.d(LOG_TAG + ".AsyncTask", "doInBackground: " + result);
					return result;
				}

			};
			
			updateBenutzerTask.execute(be);
			final HttpResponse<Benutzer> result;
			try {
				result = updateBenutzerTask.get(timeout, SECONDS);
			}
	    	catch (Exception e) {
	    		throw new InternalShopError(e.getMessage(), e);
			}
			
			if (result.responseCode == HTTP_NO_CONTENT || result.responseCode == HTTP_OK) {
				//be.updateVersion();  // kein konkurrierendes Update auf Serverseite
				result.resultObject = be;
			}
			
			return result;
	    }
		public HttpResponse<Benutzer> createBenutzer(Benutzer be, final Context ctx) {
			 
			
			Log.d(LOG_TAG,"create benutzer vom ServiceBinder wird aufgerufen");
			// (evtl. mehrere) Parameter vom Typ "Benutzer", Resultat vom Typ "void"
			final AsyncTask<Benutzer, Void, HttpResponse<Benutzer>> createBenutzerTask = new AsyncTask<Benutzer, Void, HttpResponse<Benutzer>>() {
//				@Override
//	    		protected void onPreExecute() {
//					progressDialog = showProgressDialog(ctx);
//				}
				
				@Override
				// Neuer Thread, damit der UI-Thread nicht blockiert wird
				protected HttpResponse<Benutzer> doInBackground(Benutzer... benutzer) {
					final Benutzer be = benutzer[0];
		    		final String path = BENUTZER_PATH;
		    		Log.v(LOG_TAG, "path = " + path);
		    		Log.v(LOG_TAG, benutzer.toString());
		    		Log.v(LOG_TAG,"jz kommmmmmmmmmmmmmmmmmmmmt jsssssssssssssssssssssssssssoooooooooooooooooooooooooooooooooooooooooooooooon!!!");
		    		final HttpResponse<Benutzer> result = WebServiceClient.postJson(be, path);
		    		Log.v(LOG_TAG,"WebServiceClient.postJson durchgelaufen!!");
					Log.d(LOG_TAG + ".AsyncTask", "doInBackground: " + result);
					return result;
				}
				
//				@Override
//	    		protected void onPostExecute(HttpResponse<Benutzer> unused) {
//					progressDialog.dismiss();
//					
//	    		}
			};
			createBenutzerTask.execute(be);
			HttpResponse<Benutzer> response = null; 
			try {
				
				response = createBenutzerTask.get(1000, SECONDS);
			}
	    	catch (Exception e) {
	    		throw new InternalShopError(e.getMessage(), e);
			}
			
			be.uid = Long.valueOf(response.content);
			final HttpResponse<Benutzer> result = new HttpResponse<Benutzer>(response.responseCode, response.content, be);
			return result;
	    }

	}
}
