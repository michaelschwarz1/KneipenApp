package de.kneipe.kneipenquartett.util;

import static de.kneipe.kneipenquartett.util.Constants.HOST_DEFAULT;
import static de.kneipe.kneipenquartett.util.Constants.LOCALHOST_DEVICE;

public final class Constants {
	public static final String BENUTZER_KEY = "benutzer";
	public static final String KNEIPE_KEY = "kneipe";
	public static final String KNEIPEN_KEY = "kneipen";
	
	public static final String BENUTZERN_KEY = "benutzern";
	
	public static final String BESTELLUNG_KEY = "bestellung";
	public static final String BESTELLUNGEN_KEY = "bestellungen";
	
	public static final String PRODUKT_KEY = "produkt";
	public static final String PRODUKTE_KEY = "produkte";
	
	public static final int WISCHEN_MIN_DISTANCE = 30;        // Min. Laenge des Wischens in Pixel
	public static final int WISCHEN_MAX_OFFSET_PATH = 30;       // Max. Abweichung in der Y-Richtung in Pixel
	public static final int WISCHEN_THRESHOLD_VELOCITY = 30;  // Geschwindigkeit: Pixel pro Sekunde
	
	public static final int TAB_KUNDE_STAMMDATEN = 0;
	public static final int TAB_KUNDE_BESTELLUNGEN = 1;
	
	public static final String BENUTZER_PATH = "/benutzer";
	public static final String KNEIPE_PATH = "/kneipe";
	public static final String GUTSCHEIN_PATH = "/gutschein";
	public static final String BEWERTUNG_PATH = "/bewertung";
	public static final String USERNAMEN_PATH = BENUTZER_PATH + "?nachname=";
	public static final String NAME_PATH = KNEIPE_PATH + "?name=";
	public static final String KUNDEN_PREFIX_PATH = BENUTZER_PATH + "/prefix";
	public static final String KUNDEN_ID_PREFIX_PATH = KUNDEN_PREFIX_PATH + "/id";
	public static final String KNEIPE_PREFIX_PATH = KNEIPE_PATH + "/prefix";
	public static final String KNEIPE_ID_PREFIX_PATH = KNEIPE_PREFIX_PATH + "/id";
	public static final String USERNAMEN_PREFIX_PATH = KUNDEN_PREFIX_PATH + "/nachname";
	public static final String NAME_PREFIX_PATH = KUNDEN_PREFIX_PATH + "/name";
	
	public static final String BESTELLUNG_PATH = "/bestellung";
	public static final String BESTELLPOSITION_PATH = "/bestellposition";
	
	public static final String PRODUKT_PATH = "/produkt";

	
	public static final short MIN_KATEGORIE = 1;
	public static final short MAX_KATEGORIE = 9;
	
	public static final String DEVICE_NAME = "2526989af32224f8";	//	Developer ID of the our Tablet
	public static final String PROTOCOL_DEFAULT = "http";
	public static final String LOCALHOST_EMULATOR = "10.0.2.2";
	public static final String LOCALHOST_DEVICE = "192.168.173.246"; //<---The IP Adress of the running Server in your network
	public static String HOST_DEFAULT = LOCALHOST_EMULATOR;
	public static final String PORT_DEFAULT = "8080";
	public static final String PATH_DEFAULT = "/kneipe1/rest";
	public static final String TIMEOUT_DEFAULT = "8";
	public static final boolean MOCK_DEFAULT = false;
	
	public static final String LOCALHOST = "localhost";
	
	public static final String DATE_FORMAT_JAXB = "yyyy-MM-dd'T'HH:mm:ssZ";
	
	private Constants() {}
}
