package com.example.hospitalguide;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.HashSet;

public class DatabaseHelper extends SQLiteOpenHelper {
    private Context context;
    private String currentTable;

    // Logcat tag
    private static final String LOG = "DatabaseHelper";

    // Database Version
    private static final int DATABASE_VERSION = 4;

    // Database Name
    private static final String DATABASE_NAME = "hospitals";

    // Table Names
    static final String TABLE_FI_TERVEYSASEMA = "fi_terveysasema";
    static final String TABLE_SV_TERVEYSASEMA = "sv_terveysasema";
    static final String TABLE_EN_TERVEYSASEMA = "en_terveysasema";

    // Column names
    private static final String KEY_ID = "id";
    private static final String KEY_NAME = "name";
    private static final String KEY_REGION = "region";
    private static final String KEY_CITY = "city";
    private static final String KEY_DESCRIPTION = "description";

    // fi_terveysasema table create statement
    private static final String CREATE_TABLE_FI_TERVEYSASEMA = "CREATE TABLE IF NOT EXISTS "
            + TABLE_FI_TERVEYSASEMA + " (" + KEY_ID + " INTEGER PRIMARY KEY," + KEY_NAME
            + " TEXT," + KEY_REGION + " TEXT," + KEY_CITY + " TEXT," + KEY_DESCRIPTION
            + " TEXT)";

    // sv_terveysasema table create statement
    private static final String CREATE_TABLE_SV_TERVEYSASEMA = "CREATE TABLE IF NOT EXISTS "
            + TABLE_SV_TERVEYSASEMA + " (" + KEY_ID + " INTEGER PRIMARY KEY," + KEY_NAME
            + " TEXT," + KEY_REGION + " TEXT," + KEY_CITY + " TEXT," + KEY_DESCRIPTION
            + " TEXT)";

    // en_terveysasema table create statement
    private static final String CREATE_TABLE_EN_TERVEYSASEMA = "CREATE TABLE IF NOT EXISTS "
            + TABLE_EN_TERVEYSASEMA + " (" + KEY_ID + " INTEGER PRIMARY KEY," + KEY_NAME
            + " TEXT," + KEY_REGION + " TEXT," + KEY_CITY + " TEXT," + KEY_DESCRIPTION
            + " TEXT)";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context.getApplicationContext();
        this.currentTable = this.context.getString(R.string.table);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // creating tables
        db.execSQL(CREATE_TABLE_FI_TERVEYSASEMA);
        db.execSQL(CREATE_TABLE_SV_TERVEYSASEMA);
        db.execSQL(CREATE_TABLE_EN_TERVEYSASEMA);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // drop old tables when upgrading data
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_FI_TERVEYSASEMA);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_SV_TERVEYSASEMA);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_EN_TERVEYSASEMA);
        // recreate database after dropping tables
        onCreate(db);
    }

    public long addTerveysasema(String table, String name, String region, String city, String description) {
        SQLiteDatabase db = this.getWritableDatabase();
        //This method is to streamline the insert() method for each terveysasema
        ContentValues ta_details = new ContentValues();
        ta_details.put(KEY_NAME, name);
        ta_details.put(KEY_REGION, region);
        ta_details.put(KEY_CITY, city);
        ta_details.put(KEY_DESCRIPTION, description);

        long newRowId = db.insert(table, null, ta_details);
        db.close();
        return newRowId;
    }

    public void populateDB(){
        addTerveysasema(TABLE_FI_TERVEYSASEMA,"Haagan terveysasema","Haaga", "Helsinki", "Huovitie 5, 00400 Helsinki<br>09 310 49270<br>https://www.hel.fi/helsinki/fi/sosiaali-ja-terveyspalvelut/terveyspalvelut/terveysasemat/terveysasemien-yhteystiedot/haagan-terveysasema");
        addTerveysasema(TABLE_FI_TERVEYSASEMA,"Jakomäen terveysasema","Suurmetsä", "Helsinki","Vuorensyrjä 8, 00770 Helsinki<br>09 310 53153<br>https://www.hel.fi/helsinki/fi/sosiaali-ja-terveyspalvelut/terveyspalvelut/terveysasemat/terveysasemien-yhteystiedot/jakomaen-terveysasema");
        addTerveysasema(TABLE_FI_TERVEYSASEMA,"Kalasataman terveysasema","Sörnäinen", "Helsinki","Työpajankatu 14 A, 00580 Helsinki<br>09 310 50333<br>https://www.hel.fi/sote/toimipisteet-fi/aakkosittain/kalasataman-thk");
        addTerveysasema(TABLE_FI_TERVEYSASEMA,"Kannelmäen terveysasema","Kaarela", "Helsinki","Kaustisenpolku 6 A, 00420 Helsinki<br>09 310 47355<br>https://www.hel.fi/helsinki/fi/sosiaali-ja-terveyspalvelut/terveyspalvelut/terveysasemat/terveysasemien-yhteystiedot/kannelmaen-terveysasema");
        addTerveysasema(TABLE_FI_TERVEYSASEMA,"Kivikon terveysasema","Mellunkylä", "Helsinki","Kivikonkaari 21, 00940 Helsinki<br>09 310 61520<br>https://www.hel.fi/helsinki/fi/sosiaali-ja-terveyspalvelut/terveyspalvelut/terveysasemat/terveysasemien-yhteystiedot/kivikon-terveysasema");
        addTerveysasema(TABLE_FI_TERVEYSASEMA,"Kontulan terveysasema","Mellunkylä", "Helsinki","Ostoskuja 3, 00940 Helsinki<br>09 310 60410<br>https://www.hel.fi/helsinki/fi/sosiaali-ja-terveyspalvelut/terveyspalvelut/terveysasemat/terveysasemien-yhteystiedot/kontulan-terveysasema");
        addTerveysasema(TABLE_FI_TERVEYSASEMA,"Laajasalon terveysasema","Laajasalo", "Helsinki","Koulutanhua 2 A, 00840 Helsinki<br>09 310 55400<br>https://www.hel.fi/helsinki/fi/sosiaali-ja-terveyspalvelut/terveyspalvelut/terveysasemat/terveysasemien-yhteystiedot/laajasalon-terveysasema");
        addTerveysasema(TABLE_FI_TERVEYSASEMA,"Laakson terveysasema","Laakso", "Helsinki","Lääkärinkatu 8 R, 00250 Helsinki<br>09 310 47810<br>https://www.hel.fi/helsinki/fi/sosiaali-ja-terveyspalvelut/terveyspalvelut/terveysasemat/terveysasemien-yhteystiedot/laakson-terveysasema");
        addTerveysasema(TABLE_FI_TERVEYSASEMA,"Lauttasaaren terveysasema","Lauttasaari", "Helsinki","Taivaanvuohentie 6, 00200 Helsinki<br>09 310 45260<br>https://www.hel.fi/helsinki/fi/sosiaali-ja-terveyspalvelut/terveyspalvelut/terveysasemat/terveysasemien-yhteystiedot/lauttasaaren-terveysasema");
        addTerveysasema(TABLE_FI_TERVEYSASEMA, "Malmin terveysasema","Malmi", "Helsinki","Talvelantie 4, 00700 Helsinki<br>09 310 57702<br>https://www.hel.fi/helsinki/fi/sosiaali-ja-terveyspalvelut/terveyspalvelut/terveysasemat/terveysasemien-yhteystiedot/malmin-terveysasema");
        addTerveysasema(TABLE_FI_TERVEYSASEMA, "Malminkartanon terveysasema","Kaarela", "Helsinki","Luutnantintie 12-14, 00410 Helsinki<br>09 310 48210<br>https://www.hel.fi/helsinki/fi/sosiaali-ja-terveyspalvelut/terveyspalvelut/terveysasemat/terveysasemien-yhteystiedot/malminkartanon-terveysasema");
        addTerveysasema(TABLE_FI_TERVEYSASEMA, "Maunulan terveysasema","Oulunkylä", "Helsinki","Suursuonlaita 3 A, 00630 Helsinki<br>09 310 69100<br>https://www.hel.fi/helsinki/fi/sosiaali-ja-terveyspalvelut/terveyspalvelut/terveysasemat/terveysasemien-yhteystiedot/maunulan-terveysasema");
        addTerveysasema(TABLE_FI_TERVEYSASEMA, "Munkkiniemen terveysasema","Munkkiniemi", "Helsinki","Laajalahdentie 30, 00330 Helsinki<br>09 310 48600<br>https://www.hel.fi/helsinki/fi/sosiaali-ja-terveyspalvelut/terveyspalvelut/terveysasemat/terveysasemien-yhteystiedot/munkkiniemen-terveysasema");
        addTerveysasema(TABLE_FI_TERVEYSASEMA, "Myllypuron terveysasema","Vartiokylä", "Helsinki","Jauhokuja 4, 00920 Helsinki<br>09 310 60360<br>https://www.hel.fi/helsinki/fi/sosiaali-ja-terveyspalvelut/terveyspalvelut/terveysasemat/terveysasemien-yhteystiedot/myllypuron-terveysasema");
        addTerveysasema(TABLE_FI_TERVEYSASEMA, "Oulunkylän terveysasema","Oulunkylä", "Helsinki","Kylänvanhimmantie 25, 00640 Helsinki<br>09 310 69791<br>https://www.hel.fi/helsinki/fi/sosiaali-ja-terveyspalvelut/terveyspalvelut/terveysasemat/terveysasemien-yhteystiedot/oulunkylan-terveysasema");
        addTerveysasema(TABLE_FI_TERVEYSASEMA, "Paloheinän terveysasema","Tuomarinkylä", "Helsinki","Paloheinäntie 22, 2. krs, 00670 Helsinki<br>09 310 69200<br>https://www.hel.fi/helsinki/fi/sosiaali-ja-terveyspalvelut/terveyspalvelut/terveysasemat/terveysasemien-yhteystiedot/paloheinan-terveysasema");
        addTerveysasema(TABLE_FI_TERVEYSASEMA, "Pihlajamäen terveysasema","Malmi", "Helsinki","Meripihkatie 8, 00710 Helsinki<br>09 310 59800<br>https://www.hel.fi/helsinki/fi/sosiaali-ja-terveyspalvelut/terveyspalvelut/terveysasemat/terveysasemien-yhteystiedot/pihlajamaen-terveysasema");
        addTerveysasema(TABLE_FI_TERVEYSASEMA, "Pitäjänmäen terveysasema","Pitäjänmäki", "Helsinki","Konalantie 6-8 C, 00370 Helsinki<br>09 310 48300<br>https://www.hel.fi/helsinki/fi/sosiaali-ja-terveyspalvelut/terveyspalvelut/terveysasemat/terveysasemien-yhteystiedot/pitajanmaen-terveysasema");
        addTerveysasema(TABLE_FI_TERVEYSASEMA, "Puistolan terveysasema","Suurmetsä", "Helsinki","Ajurinaukio 1, 00750 Helsinki<br>09 310 53300<br>https://www.hel.fi/helsinki/fi/sosiaali-ja-terveyspalvelut/terveyspalvelut/terveysasemat/terveysasemien-yhteystiedot/puistolan-terveysasema");
        addTerveysasema(TABLE_FI_TERVEYSASEMA, "Suutarilan terveysasema","Suutarila", "Helsinki","Suutarilantie 32, 00740 Helsinki<br>09 310 53410<br>https://www.hel.fi/helsinki/fi/sosiaali-ja-terveyspalvelut/terveyspalvelut/terveysasemat/terveysasemien-yhteystiedot/suutarilan-terveysasema");
        addTerveysasema(TABLE_FI_TERVEYSASEMA, "Töölön terveysasema","Töölö", "Helsinki","Sibeliuksenkatu 14, 00260 Helsinki<br>09 310 45500<br>https://www.hel.fi/helsinki/fi/sosiaali-ja-terveyspalvelut/terveyspalvelut/terveysasemat/terveysasemien-yhteystiedot/toolon-terveysasema");
        addTerveysasema(TABLE_FI_TERVEYSASEMA, "Viiskulman terveysasema","Punavuori", "Helsinki","Pursimiehenkatu 4, 00150 Helsinki<br>09 310 45930<br>https://www.hel.fi/helsinki/fi/sosiaali-ja-terveyspalvelut/terveyspalvelut/terveysasemat/terveysasemien-yhteystiedot/viiskulman-terveysasema");
        addTerveysasema(TABLE_FI_TERVEYSASEMA, "Vuosaaren terveysasema","Vuosaari", "Helsinki","Kahvikuja 3, 00980 Helsinki<br>09 310 60850<br>https://www.hel.fi/sote/toimipisteet-fi/aakkosittain/vuosaaren-perhekeskus-ja-terveys--ja-hyvinvointikeskus/terveysasemapalvelut");
        addTerveysasema(TABLE_FI_TERVEYSASEMA, "Hakunilan terveysasema","Hakunila", "Vantaa", "Laukkarinne 4, 01200 Vantaa<br>09 839 11<br>http://www.vantaa.fi/terveys-_ja_sosiaalipalvelut/terveyspalvelut/terveysasemat/hakunilan_terveysasema");
        addTerveysasema(TABLE_FI_TERVEYSASEMA, "Koivukylän sosiaali- ja terveysasema","Koivukylä", "Vantaa","Karsikkokuja 15, 01360 Vantaa<br>09 839 11<br>http://www.vantaa.fi/terveys-_ja_sosiaalipalvelut/terveyspalvelut/terveysasemat/koivukylan_terveysasema");
        addTerveysasema(TABLE_FI_TERVEYSASEMA, "Korson terveysasema","Korso", "Vantaa","Naalipolku 6, 01450 Vantaa<br>09 839 11<br>http://www.vantaa.fi/terveys-_ja_sosiaalipalvelut/terveyspalvelut/terveysasemat/korson_terveysasema");
        addTerveysasema(TABLE_FI_TERVEYSASEMA, "Länsimäen terveysasema","Hakunila", "Vantaa","Keilakuja 1, 01280 Vantaa<br>09 839 11<br>http://www.vantaa.fi/terveys-_ja_sosiaalipalvelut/terveyspalvelut/terveysasemat/lansimaen_terveysasema");
        addTerveysasema(TABLE_FI_TERVEYSASEMA, "Martinlaakson sosiaali- ja terveysasema","Myyrmäki", "Vantaa","Laajaniityntie 3, 01620 Vantaa<br>09 839 11<br>http://www.vantaa.fi/terveys-_ja_sosiaalipalvelut/terveyspalvelut/terveysasemat/martinlaakson_terveysasema");
        addTerveysasema(TABLE_FI_TERVEYSASEMA, "Myyrmäen sosiaali- ja terveysasema","Myyrmäki", "Vantaa","Jönsaksentie 4, 01600 Vantaa<br>09 839 11<br>http://www.vantaa.fi/terveys-_ja_sosiaalipalvelut/terveyspalvelut/terveysasemat/myyrmaen_terveysasema");
        addTerveysasema(TABLE_FI_TERVEYSASEMA, "Tikkurilan sosiaali- ja terveysasema","Tikkurila", "Vantaa","Kielotie 11 A, 01300 Vantaa<br>09 839 11<br>http://www.vantaa.fi/terveys-_ja_sosiaalipalvelut/terveyspalvelut/terveysasemat/tikkurilan_terveysasema");
        addTerveysasema(TABLE_FI_TERVEYSASEMA, "Espoonlahden terveysasema","Suur-Espoonlahti", "Espoo", "Merikansantie 4 A, 02320 Espoo<br>09 8163 4500<br>http://espoo.fi/fi-FI/Sosiaali_ja_terveyspalvelut/Terveyspalvelut/Terveysasemat/Espoonlahden_terveysasema");
        addTerveysasema(TABLE_FI_TERVEYSASEMA, "Ison Omenan terveysasema","Suur-Matinkylä", "Espoo","Suomenlahdentie 1, 02230 Espoo<br>09 8163 4500<br>https://www.espoo.fi/fi-FI/Sosiaali_ja_terveyspalvelut/Terveyspalvelut/Terveysasemat/Ison_Omenan_terveysasema");
        addTerveysasema(TABLE_FI_TERVEYSASEMA, "Kalajärven terveysasema","Pohjois-Espoo", "Espoo","Ruskaniitty 4, 02970 Espoo<br>09 8163 4500<br>http://espoo.fi/fi-FI/Sosiaali_ja_terveyspalvelut/Terveyspalvelut/Terveysasemat/Kalajarven_terveysasema");
        addTerveysasema(TABLE_FI_TERVEYSASEMA, "Kilon terveysasema","Suur-Leppävaara", "Espoo","Trillakatu 5, 02610 Espoo<br>09 8163 4500<br>http://espoo.fi/fi-FI/Sosiaali_ja_terveyspalvelut/Terveyspalvelut/Terveysasemat/Kilon_terveysasema");
        addTerveysasema(TABLE_FI_TERVEYSASEMA, "Kivenlahden terveysasema","Suur-Espoonlahti", "Espoo","Meriusva 3, 02320 Espoo<br>09 8163 4500<br>http://espoo.fi/fi-FI/Sosiaali_ja_terveyspalvelut/Terveyspalvelut/Terveysasemat/Kivenlahden_terveysasema");
        addTerveysasema(TABLE_FI_TERVEYSASEMA, "Leppävaaran terveysasema","Suur-Leppävaara", "Espoo","Konstaapelinkatu 2, 02650 Espoo<br>09 8163 4500<br>http://espoo.fi/fi-FI/Sosiaali_ja_terveyspalvelut/Terveyspalvelut/Terveysasemat/Leppavaaran_terveysasema");
        addTerveysasema(TABLE_FI_TERVEYSASEMA, "Oma Lääkärisi Espoontori","Vanha-Espoo", "Espoo","Kamreerintie 7, 5 krs, 02070 Espoo<br>09 855 4303<br>http://www.espoo.fi/fi-FI/Sosiaali_ja_terveyspalvelut/Terveyspalvelut/Terveysasemat/Oma_Laakarisi_Espoontori_terveysasema");
        addTerveysasema(TABLE_FI_TERVEYSASEMA, "Samarian terveysasema","Vanha-Espoo", "Espoo","Terveyskuja 2, 02770 Espoo<br>09 8163 4500<br>http://espoo.fi/fi-FI/Sosiaali_ja_terveyspalvelut/Terveyspalvelut/Terveysasemat/Samarian_terveysasema__Espoon_keskus");
        addTerveysasema(TABLE_FI_TERVEYSASEMA, "Tapiolan terveysasema","Suur-Tapiola", "Espoo","Ahertajantie 2, 02100 Espoo<br>09 8163 4500<br>http://espoo.fi/fi-FI/Sosiaali_ja_terveyspalvelut/Terveyspalvelut/Terveysasemat/Tapiolan_terveysasema");
        addTerveysasema(TABLE_FI_TERVEYSASEMA, "Viherlaakson terveysasema","Suur-Leppävaara", "Espoo","Kievarinpolku 1, 02710 Espoo<br>09 8163 4500<br>http://espoo.fi/fi-FI/Sosiaali_ja_terveyspalvelut/Terveyspalvelut/Terveysasemat/Viherlaakson_terveysasema");
        addTerveysasema(TABLE_FI_TERVEYSASEMA, "Kauniaisten terveysasema","Kauniainen", "Kauniainen", "Asematie 19, 02700 Kauniainen<br>09 8789 1300<br>http://www.kauniainen.fi/sosiaali-_ja_terveyspalvelut/terveyspalvelut/terveysasema");

        addTerveysasema(TABLE_SV_TERVEYSASEMA,"Haga hälsostation","Haga", "Helsingfors", "Knektvägen 5, 00400 Helsingfors<br>09 310 49270<br>https://www.hel.fi/helsinki/sv/social-och-halso/halso/halsostationer/kontaktuppgifter/haga-halsostation");
        addTerveysasema(TABLE_SV_TERVEYSASEMA,"Jakobacka hälsostation","Storskog", "Helsingfors","Bergssidan 8, 00770 Helsingfors<br>09 310 53153<br>https://www.hel.fi/helsinki/sv/social-och-halso/halso/halsostationer/kontaktuppgifter/jakobacka-halsostation");
        addTerveysasema(TABLE_SV_TERVEYSASEMA,"Fiskehamnens hälsostation","Sörnäs", "Helsingfors","Verkstadsgatan 14 A, 00580 Helsingfors<br>09 310 50333<br>https://www.hel.fi/sote/enheterna-sv/enheterna-i-alfabetisk-ordning/fiskehamnens-central/halsostationstjanster");
        addTerveysasema(TABLE_SV_TERVEYSASEMA,"Gamlas hälsostation","Kårböle", "Helsingfors","Kaustbystigen 6 A, 00420 Helsingfors<br>09 310 47355<br>https://www.hel.fi/helsinki/sv/social-och-halso/halso/halsostationer/kontaktuppgifter/gamlas-halsostation");
        addTerveysasema(TABLE_SV_TERVEYSASEMA,"Stensböle hälsostation","Mellungsby", "Helsingfors","Stensbölebågen 21, 00940 Helsingfors<br>09 310 61520<br>https://www.hel.fi/helsinki/sv/social-och-halso/halso/halsostationer/kontaktuppgifter/stensbole-halsostation");
        addTerveysasema(TABLE_SV_TERVEYSASEMA,"Gårdsbacka hälsostation","Mellungsby", "Helsingfors","Köpgränden 3, 00940 Helsingfors<br>09 310 60410<br>https://www.hel.fi/helsinki/sv/social-och-halso/halso/halsostationer/kontaktuppgifter/gardsbacka-halsostation");
        addTerveysasema(TABLE_SV_TERVEYSASEMA,"Degerö hälsostation","Degerö", "Helsingfors","Skoltået 2 A, 00840 Helsingfors<br>09 310 55400<br>https://www.hel.fi/helsinki/sv/social-och-halso/halso/halsostationer/kontaktuppgifter/degero-halsostation");
        addTerveysasema(TABLE_SV_TERVEYSASEMA,"Dals hälsostation","Dal", "Helsingfors","Läkaregatan 8 R, 00250 Helsingfors<br>09 310 47810<br>https://www.hel.fi/helsinki/sv/social-och-halso/halso/halsostationer/kontaktuppgifter/dals-halsostation");
        addTerveysasema(TABLE_SV_TERVEYSASEMA,"Drumsö hälsostation","Drumsö", "Helsingfors","Beckasinvägen 6, 00200 Helsingfors<br>09 310 45260<br>https://www.hel.fi/helsinki/sv/social-och-halso/halso/halsostationer/kontaktuppgifter/drumso-halsostation");
        addTerveysasema(TABLE_SV_TERVEYSASEMA,"Malms hälsostation","Malm", "Helsingfors","Talvelavägen 4, 00700 Helsingfors<br>09 310 57702<br>https://www.hel.fi/helsinki/sv/social-och-halso/halso/halsostationer/kontaktuppgifter/malms-halsostation");
        addTerveysasema(TABLE_SV_TERVEYSASEMA,"Malmgårds hälsostation","Kårböle", "Helsingfors","Löjtnantsvägen 12-14, 00410 Helsingfors<br>09 310 48210<br>https://www.hel.fi/helsinki/sv/social-och-halso/halso/halsostationer/kontaktuppgifter/malmgards-halsostation");
        addTerveysasema(TABLE_SV_TERVEYSASEMA,"Månsas hälsostation","Åggelby", "Helsingfors","Storkärrskanten 3 A, 00630 Helsingfors<br>09 310 69100<br>https://www.hel.fi/helsinki/sv/social-och-halso/halso/halsostationer/kontaktuppgifter/mansas-halsostation");
        addTerveysasema(TABLE_SV_TERVEYSASEMA,"Munksnäs hälsostation","Munksnäs", "Helsingfors","Bredviksvägen 30, 00330 Helsingfors<br>09 310 48600<br>https://www.hel.fi/helsinki/sv/social-och-halso/halso/halsostationer/kontaktuppgifter/munksnas-halsostation");
        addTerveysasema(TABLE_SV_TERVEYSASEMA,"Kvarnbäckens hälsostation ","Botby", "Helsingfors","Mjölgränden 4, 00920 Helsingfors<br>09 310 60360<br>https://www.hel.fi/helsinki/sv/social-och-halso/halso/halsostationer/kontaktuppgifter/kvarnbackens-halsostation");
        addTerveysasema(TABLE_SV_TERVEYSASEMA,"Åggelby hälsostation","Åggelby", "Helsingfors","Byäldstevägen 25, 00640 Helsingfors<br>09 310 69791<br>https://www.hel.fi/helsinki/sv/social-och-halso/halso/halsostationer/kontaktuppgifter/aggelby-halsostation");
        addTerveysasema(TABLE_SV_TERVEYSASEMA,"Svedängens hälsostation","Domarby", "Helsingfors","Svedängsvägen 22, 2. vån., 00670 Helsingfors<br>09 310 69200<br>https://www.hel.fi/helsinki/sv/social-och-halso/halso/halsostationer/kontaktuppgifter/svedangens-halsostation");
        addTerveysasema(TABLE_SV_TERVEYSASEMA,"Rönnbacka hälsostation","Malm", "Helsingfors","Bärnstensvägen 8, 00710 Helsingfors<br>09 310 59800<br>https://www.hel.fi/helsinki/sv/social-och-halso/halso/halsostationer/kontaktuppgifter/ronnbacka-halsostation");
        addTerveysasema(TABLE_SV_TERVEYSASEMA,"Sockenbacka hälsostation","Sockenbacka", "Helsingfors","Kånalavägen 6-8 C, 00370 Helsingfors<br>09 310 48300<br>https://www.hel.fi/helsinki/sv/social-och-halso/halso/halsostationer/kontaktuppgifter/sockenbacka-halsostation");
        addTerveysasema(TABLE_SV_TERVEYSASEMA,"Parkstads hälsostation","Storskog", "Helsingfors","Formansplatsen 1, 00750 Helsingfors<br>09 310 53300<br>https://www.hel.fi/helsinki/sv/social-och-halso/halso/halsostationer/kontaktuppgifter/parkstads-halsostation");
        addTerveysasema(TABLE_SV_TERVEYSASEMA,"Skomakarböle hälsostation","Skomakarböle", "Helsingfors","Skomakarbölevägen 32, 00740 Helsingfors<br>09 310 53410<br>https://www.hel.fi/helsinki/sv/social-och-halso/halso/halsostationer/kontaktuppgifter/skomakarbole-halsostation");
        addTerveysasema(TABLE_SV_TERVEYSASEMA,"Tölö hälsostation","Tölö", "Helsingfors","Sibeliusgatan 14, 00260 Helsingfors<br>09 310 45500<br>https://www.hel.fi/helsinki/sv/social-och-halso/halso/halsostationer/kontaktuppgifter/tolo-halsostation");
        addTerveysasema(TABLE_SV_TERVEYSASEMA,"Femkantens hälsostation ","Rödbergen", "Helsingfors","Båtsmansgatan 4, 00150 Helsingfors<br>09 310 45930<br>https://www.hel.fi/helsinki/sv/social-och-halso/halso/halsostationer/kontaktuppgifter/femkantens-halsostation");
        addTerveysasema(TABLE_SV_TERVEYSASEMA,"Nordsjö hälsostation","Nordsjö", "Helsingfors","Kaffegränden 3, 00980 Helsingfors<br>09 310 60850<br>https://www.hel.fi/helsinki/sv/social-och-halso/halso/halsostationer/kontaktuppgifter/nordsjo-halsostation");
        addTerveysasema(TABLE_SV_TERVEYSASEMA,"Håkansböle hälsostation","Håkansböle", "Vanda", "Galoppbrinken 4, 01200 Vanda<br>09 839 11<br>https://www.vanda.fi/halsovard_och_sociala_tjanster/halsovardstjanster/halsostationer/hakansbole_halsostation");
        addTerveysasema(TABLE_SV_TERVEYSASEMA,"Björkby hälsostation","Björkby","Vanda", "Vårdträdsgränden 15, 01360 Vanda<br>09 839 11<br>https://www.vanda.fi/halsovard_och_sociala_tjanster/halsovardstjanster/halsostationer/bjorkby_halsostation");
        addTerveysasema(TABLE_SV_TERVEYSASEMA,"Korso hälsostation","Korso","Vanda", "Fjällrävsstigen 6 B, 2 vån., 01450 Vanda<br>09 839 11<br>https://www.vanda.fi/halsovard_och_sociala_tjanster/halsovardstjanster/halsostationer/korso_halsostation");
        addTerveysasema(TABLE_SV_TERVEYSASEMA,"Västerkulla hälsostation","Håkansböle","Vanda", "Kägelgränden 1, 01280 Vanda<br>09 839 11<br>https://www.vantaa.fi/halsovard_och_sociala_tjanster/halsovardstjanster/halsostationer/vasterkulla_halsostation");
        addTerveysasema(TABLE_SV_TERVEYSASEMA,"Mårtensdals hälsostation","Myrbacka","Vanda", "Bredängsvägen 3, 4 vån., 01620 Vanda<br>09 839 11<br>https://www.vanda.fi/halsovard_och_sociala_tjanster/halsovardstjanster/halsostationer/martensdals_halsostation");
        addTerveysasema(TABLE_SV_TERVEYSASEMA,"Myrbacka hälsostation","Myrbacka","Vanda", "Jönsasvägen 4, 01600 Vanda<br>09 839 11<br>https://www.vanda.fi/halsovard_och_sociala_tjanster/halsovardstjanster/halsostationer/myrbacka_halsostation");
        addTerveysasema(TABLE_SV_TERVEYSASEMA,"Dickursby hälsostation","Dickursby","Vanda", "Konvaljvägen 11 A, 01300 Vanda<br>09 839 11<br>https://www.vanda.fi/halsovard_och_sociala_tjanster/halsovardstjanster/halsostationer/dickursby_halsostation");
        addTerveysasema(TABLE_SV_TERVEYSASEMA,"Esbovikens hälsostation","Stor-Esboviken", "Esbo", "Sjöfolksvägen 4 A, 02320 Esbo<br>09 8163 4500<br>https://www.esbo.fi/sv-FI/Social_och_halsovard/Sjuk_och_halsovard/Halsostationerna/Esbovikens_halsostation");
        addTerveysasema(TABLE_SV_TERVEYSASEMA,"Hälsostationen i Iso Omena","Stor-Mattby", "Esbo", "Finnviksvägen 1, 02230 Esbo<br>09 8163 4500<br>https://www.esbo.fi/sv-FI/Social_och_halsovard/Sjuk_och_halsovard/Halsostationerna/Halsostationen_i_Iso_Omena/Halsostationen_i_Iso_Omena(60070)");
        addTerveysasema(TABLE_SV_TERVEYSASEMA,"Kalajärvi hälsostation","Norra Esbo", "Esbo", "Höstglödsängen 4, 02970 Esbo<br>09 8163 4500<br>https://www.esbo.fi/sv-FI/Social_och_halsovard/Sjuk_och_halsovard/Halsostationerna/Kalajarvi_halsostation");
        addTerveysasema(TABLE_SV_TERVEYSASEMA,"Kil hälsostation","Stor-Alberga", "Esbo", "Trillagatan 5, 02610 Esbo<br>09 8163 4500<br>https://www.esbo.fi/sv-FI/Social_och_halsovard/Sjuk_och_halsovard/Halsostationerna/Kilo_halsostation");
        addTerveysasema(TABLE_SV_TERVEYSASEMA,"Stensviks hälsostation","Stor-Esboviken", "Esbo", "Sjöröken 3, 02320 Esbo<br>09 8163 4500<br>https://www.esbo.fi/sv-FI/Social_och_halsovard/Sjuk_och_halsovard/Halsostationerna/Stensviks_halsostation");
        addTerveysasema(TABLE_SV_TERVEYSASEMA,"Alberga hälsostation","Stor-Alberga", "Esbo", "Konstapelsgatan 2, 02650 Esbo<br>09 8163 4500<br>https://www.esbo.fi/sv-FI/Social_och_halsovard/Sjuk_och_halsovard/Halsostationerna/Alberga_halsostation");
        addTerveysasema(TABLE_SV_TERVEYSASEMA,"Oma Lääkärisi Esbotorget","Gamla Esbo", "Esbo", "Kamrersvägen 7, 5 vån., 02070 Esbo<br>09 855 4303<br>https://www.esbo.fi/sv-FI/Social_och_halsovard/Sjuk_och_halsovard/Halsostationerna/Oma_Laakarisi_Esbotorget");
        addTerveysasema(TABLE_SV_TERVEYSASEMA,"Hälsostationen Samaria i Esbo centrum","Gamla Esbo", "Esbo", "Hälsogränden 2, 02770 Esbo<br>09 8163 4500<br>https://www.esbo.fi/sv-FI/Social_och_halsovard/Sjuk_och_halsovard/Halsostationerna/Halsostationen_Samaria_i_Esbo_centrum");
        addTerveysasema(TABLE_SV_TERVEYSASEMA,"Hagalunds hälsostation","Stor-Hagalund", "Esbo", "Flitarvägen 2, 02100 Esbo<br>09 8163 4500<br>https://www.esbo.fi/sv-FI/Social_och_halsovard/Sjuk_och_halsovard/Halsostationerna/Hagalunds_halsostation");
        addTerveysasema(TABLE_SV_TERVEYSASEMA,"Gröndals hälsostation","Stor-Alberga", "Esbo", "Gästgivarstigen 1, 02710 Esbo<br>09 8163 4500<br>https://www.esbo.fi/sv-FI/Social_och_halsovard/Sjuk_och_halsovard/Halsostationerna/Grondals_halsostation");
        addTerveysasema(TABLE_SV_TERVEYSASEMA,"Grankulla hälsovårdscentral","Grankulla", "Grankulla", "Stationsvägen 19, 02700 Grankulla<br>09 8789 1300<br>https://www.kauniainen.fi/sv/social-_och_halsovard/halsovardstjanster");

        addTerveysasema(TABLE_EN_TERVEYSASEMA,"Haaga Health Station","Haaga", "Helsinki", "Huovitie 5, 00400 Helsinki<br>09 310 49270<br>https://www.hel.fi/helsinki/en/socia-health/health/stations/contact-information/haaga");
        addTerveysasema(TABLE_EN_TERVEYSASEMA,"Jakomäki Health Station","Suurmetsä", "Helsinki", "Vuorensyrjä 8, 00770 Helsinki<br>09 310 53153<br>https://www.hel.fi/helsinki/en/socia-health/health/stations/contact-information/jakomaki-health-station");
        addTerveysasema(TABLE_EN_TERVEYSASEMA,"Kalasatama Health and Well-being Centre","Sörnäinen", "Helsinki", "Työpajankatu 14 A, 00580 Helsinki<br>09 310 50333<br>https://www.hel.fi/sote/units-en/units-alphabetically/kalasatama-centre");
        addTerveysasema(TABLE_EN_TERVEYSASEMA,"Kannelmäki Health Station","Kaarela", "Helsinki", "Kaustisenpolku 6 A, 00420 Helsinki<br>09 310 47355<br>https://www.hel.fi/helsinki/en/socia-health/health/stations/contact-information/kannelmaki-health-station");
        addTerveysasema(TABLE_EN_TERVEYSASEMA,"Kivikko Health Station","Mellunkylä", "Helsinki", "Kivikonkaari 21, 00940 Helsinki<br>09 310 61520<br>https://www.hel.fi/helsinki/en/socia-health/health/stations/contact-information/kivikko-health-station");
        addTerveysasema(TABLE_EN_TERVEYSASEMA,"Kontula Health Station","Mellunkylä", "Helsinki", "Ostoskuja 3, 00940 Helsinki<br>09 310 60410<br>https://www.hel.fi/helsinki/en/socia-health/health/stations/contact-information/kontula-health-station");
        addTerveysasema(TABLE_EN_TERVEYSASEMA,"Laajasalo Health Station","Laajasalo", "Helsinki", "Koulutanhua 2 A, 00840 Helsinki<br>09 310 55400<br>https://www.hel.fi/helsinki/en/socia-health/health/stations/contact-information/laajasalo-health-station");
        addTerveysasema(TABLE_EN_TERVEYSASEMA,"Laakso Health Station","Laakso", "Helsinki", "Lääkärinkatu 8 R, 00250 Helsinki<br>09 310 47810<br>https://www.hel.fi/helsinki/en/socia-health/health/stations/contact-information/laakso-health-station");
        addTerveysasema(TABLE_EN_TERVEYSASEMA,"Lauttasaari Health Station","Lauttasaari", "Helsinki", "Taivaanvuohentie 6, 00200 Helsinki<br>09 310 45260<br>https://www.hel.fi/helsinki/en/socia-health/health/stations/contact-information/lauttasaari-health-station");
        addTerveysasema(TABLE_EN_TERVEYSASEMA,"Malmi Health Station","Malmi", "Helsinki", "Talvelantie 4, 00700 Helsinki<br>09 310 57702<br>https://www.hel.fi/helsinki/en/socia-health/health/stations/contact-information/malmi-health-station");
        addTerveysasema(TABLE_EN_TERVEYSASEMA,"Malminkartano Health Station","Kaarela", "Helsinki", "Luutnantintie 12-14, 00410 Helsinki<br>09 310 48210<br>https://www.hel.fi/helsinki/en/socia-health/health/stations/contact-information/malminkartano-health-station");
        addTerveysasema(TABLE_EN_TERVEYSASEMA,"Maunula Health Station","Oulunkylä", "Helsinki", "Suursuonlaita 3 A, 00630 Helsinki<br>09 310 69100<br>https://www.hel.fi/helsinki/en/socia-health/health/stations/contact-information/maunula-health-station");
        addTerveysasema(TABLE_EN_TERVEYSASEMA,"Munkkiniemi Health Station","Munkkiniemi", "Helsinki", "Laajalahdentie 30, 00330 Helsinki<br>09 310 48600<br>https://www.hel.fi/helsinki/en/socia-health/health/stations/contact-information/munkkiniemi-health-station");
        addTerveysasema(TABLE_EN_TERVEYSASEMA,"Myllypuro Health Station","Vartiokylä", "Helsinki", "Jauhokuja 4, 00920 Helsinki<br>09 310 60360<br>https://www.hel.fi/helsinki/en/socia-health/health/stations/contact-information/myllypuro-health-station");
        addTerveysasema(TABLE_EN_TERVEYSASEMA,"Oulunkylä Health Station","Oulunkylä", "Helsinki", "Kylänvanhimmantie 25, 00640 Helsinki<br>09 310 69791<br>https://www.hel.fi/helsinki/en/socia-health/health/stations/contact-information/oulunkyla-health-station");
        addTerveysasema(TABLE_EN_TERVEYSASEMA,"Paloheinä Health Station","Tuomarinkylä", "Helsinki", "Paloheinäntie 22, 2nd floor, 00670 Helsinki<br>09 310 69200<br>https://www.hel.fi/helsinki/en/socia-health/health/stations/contact-information/paloheina-health-station");
        addTerveysasema(TABLE_EN_TERVEYSASEMA,"Pihlajamäki Health Station","Malmi", "Helsinki", "Meripihkatie 8, 00710 Helsinki<br>09 310 59800<br>https://www.hel.fi/helsinki/en/socia-health/health/stations/contact-information/pihlajamaki-health-station");
        addTerveysasema(TABLE_EN_TERVEYSASEMA,"Pitäjänmäki Health Station","Pitäjänmäki", "Helsinki", "Konalantie 6-8 C, 00370 Helsinki<br>09 310 48300<br>https://www.hel.fi/helsinki/en/socia-health/health/stations/contact-information/pitajanmaki-health-station");
        addTerveysasema(TABLE_EN_TERVEYSASEMA,"Puistola Health Station","Suurmetsä", "Helsinki", "Ajurinaukio 1, 00750 Helsinki<br>09 310 53300<br>https://www.hel.fi/helsinki/en/socia-health/health/stations/contact-information/puistola-health-station");
        addTerveysasema(TABLE_EN_TERVEYSASEMA,"Suutarila Health Station","Suutarila", "Helsinki", "Suutarilantie 32, 00740 Helsinki<br>09 310 53410<br>https://www.hel.fi/helsinki/en/socia-health/health/stations/contact-information/suutarila-health-station");
        addTerveysasema(TABLE_EN_TERVEYSASEMA,"Töölö Health Station","Töölö", "Helsinki", "Sibeliuksenkatu 14, 00260 Helsinki<br>09 310 45500<br>https://www.hel.fi/helsinki/en/socia-health/health/stations/contact-information/toolo-health-station");
        addTerveysasema(TABLE_EN_TERVEYSASEMA,"Viiskulma Health Station","Punavuori", "Helsinki", "Pursimiehenkatu 4, 00150 Helsinki<br>09 310 45930<br>https://www.hel.fi/helsinki/en/socia-health/health/stations/contact-information/viiskulma-health-station");
        addTerveysasema(TABLE_EN_TERVEYSASEMA,"Vuosaari Health Station","Vuosaari", "Helsinki", "Kahvikuja 3, 00980 Helsinki<br>09 310 60850<br>https://www.hel.fi/helsinki/en/socia-health/health/stations/contact-information/vuosaari-health-station");
        addTerveysasema(TABLE_EN_TERVEYSASEMA,"Hakunila Health Center","Hakunila", "Vantaa", "Laukkarinne 4, 01200 Vantaa<br>09 839 11<br>https://www.vantaa.fi/health_care_and_social_services/health_services/health_centers/hakunila_health_center");
        addTerveysasema(TABLE_EN_TERVEYSASEMA,"Koivukylä Health Center","Koivukylä", "Vantaa", "Karsikkokuja 15, 01360 Vantaa<br>09 839 11<br>https://www.vantaa.fi/health_care_and_social_services/health_services/health_centers/koivukyla_health_center");
        addTerveysasema(TABLE_EN_TERVEYSASEMA,"Korso Health Center","Korso", "Vantaa", "Naalipolku 6, 01450 Vantaa<br>09 839 11<br>https://www.vantaa.fi/health_care_and_social_services/health_services/health_centers/korso_health_center");
        addTerveysasema(TABLE_EN_TERVEYSASEMA,"Länsimäki Health Center","Hakunila", "Vantaa", "Keilakuja 1, 01280 Vantaa<br>09 839 11<br>https://www.vantaa.fi/health_care_and_social_services/health_services/health_centers/lansimaki_health_center");
        addTerveysasema(TABLE_EN_TERVEYSASEMA,"Martinlaakso Health Center","Myyrmäki", "Vantaa", "Laajaniityntie 3, 01620 Vantaa<br>09 839 11<br>https://www.vantaa.fi/health_care_and_social_services/health_services/health_centers/martinlaakso_health_center");
        addTerveysasema(TABLE_EN_TERVEYSASEMA,"Myyrmäki Health Center","Myyrmäki", "Vantaa", "Jönsaksentie 4, 01600 Vantaa<br>09 839 11<br>https://www.vantaa.fi/health_care_and_social_services/health_services/health_centers/myyrmaki_health_center");
        addTerveysasema(TABLE_EN_TERVEYSASEMA,"Tikkurila Health Center","Tikkurila", "Vantaa", "Kielotie 11 A, 01300 Vantaa<br>09 839 11<br>https://www.vantaa.fi/health_care_and_social_services/health_services/health_centers/tikkurila_health_center");
        addTerveysasema(TABLE_EN_TERVEYSASEMA,"Espoonlahti Health Centre","Suur-Espoonlahti", "Espoo", "Merikansantie 4 A, 02320 Espoo<br>09 8163 4500<br>https://www.espoo.fi/en-US/Social_and_health_services/Health_Services/Health_centres/Espoonlahti_Health_Centre");
        addTerveysasema(TABLE_EN_TERVEYSASEMA,"Iso Omena Health Centre","Suur-Matinkylä", "Espoo", "Suomenlahdentie 1, 02230 Espoo<br>09 8163 4500<br>https://www.espoo.fi/en-US/Social_and_health_services/Health_Services/Health_centres/Iso_Omena_Health_Centre");
        addTerveysasema(TABLE_EN_TERVEYSASEMA,"Kalajärvi Health Centre","Pohjois-Espoo", "Espoo", "Ruskaniitty 4, 02970 Espoo<br>09 8163 4500<br>https://www.espoo.fi/en-US/Social_and_health_services/Health_Services/Health_centres/Kalajarvi_Health_Centre");
        addTerveysasema(TABLE_EN_TERVEYSASEMA,"Kilo Health Centre","Suur-Leppävaara", "Espoo", "Trillakatu 5, 02610 Espoo<br>09 8163 4500<br>https://www.espoo.fi/en-US/Social_and_health_services/Health_Services/Health_centres/Kilo_Health_Centre");
        addTerveysasema(TABLE_EN_TERVEYSASEMA,"Kivenlahti Health Centre","Suur-Espoonlahti", "Espoo", "Meriusva 3, 02320 Espoo<br>09 8163 4500<br>https://www.espoo.fi/en-US/Social_and_health_services/Health_Services/Health_centres/Kivenlahti_Health_Centre");
        addTerveysasema(TABLE_EN_TERVEYSASEMA,"Leppävaara Health Centre","Suur-Leppävaara", "Espoo", "Konstaapelinkatu 2, 02650 Espoo<br>09 8163 4500<br>https://www.espoo.fi/en-US/Social_and_health_services/Health_Services/Health_centres/Leppavaara_Health_Centre");
        addTerveysasema(TABLE_EN_TERVEYSASEMA,"Oma Lääkärisi Espoontori","Vanha-Espoo", "Espoo", "Kamreerintie 7, 5th floor, 02070 Espoo<br>09 855 4303<br>https://www.espoo.fi/en-US/Social_and_health_services/Health_Services/Health_centres/Oma_Laakarisi_Espoontori");
        addTerveysasema(TABLE_EN_TERVEYSASEMA,"Samaria Health Centre","Vanha-Espoo", "Espoo", "Terveyskuja 2, 02770 Espoo<br>09 8163 4500<br>https://www.espoo.fi/en-US/Social_and_health_services/Health_Services/Health_centres/Samaria_Health_Centre");
        addTerveysasema(TABLE_EN_TERVEYSASEMA,"Tapiola Health Centre","Suur-Tapiola", "Espoo", "Ahertajantie 2, 02100 Espoo<br>09 8163 4500<br>https://www.espoo.fi/en-US/Social_and_health_services/Health_Services/Health_centres/Tapiola_Health_Centre");
        addTerveysasema(TABLE_EN_TERVEYSASEMA,"Viherlaakso Health Centre","Suur-Leppävaara", "Espoo", "Kievarinpolku 1, 02710 Espoo<br>09 8163 4500<br>https://www.espoo.fi/en-US/Social_and_health_services/Health_Services/Health_centres/Viherlaakso_Health_Centre");
        addTerveysasema(TABLE_EN_TERVEYSASEMA,"Kauniainen Health Centre","Kauniainen", "Kauniainen", "Asematie 19, 02700 Kauniainen<br>09 8789 1300<br>https://www.kauniainen.fi/en/social_services_and_health_care/health_services/health_centre");

    }
    
    public ArrayList<String> getRegions(String selectedCity) {
        HashSet<String> regionsSet = new HashSet<>();
        ArrayList<String> regions = new ArrayList<>();

        String selectQuery = "SELECT " + KEY_REGION + " FROM " + currentTable + " WHERE " + KEY_CITY + " = '" + selectedCity + "'";
        //e.g. "Select regions from the table where the description contains a specific city
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                regionsSet.add(cursor.getString(0));
            } while (cursor.moveToNext());
        }
        db.close();
        regions.addAll(regionsSet);
        return regions;
    }

    public ArrayList<Hospital> getTerveysasemat(String selectedRegion) {
        ArrayList<Hospital> hospitalList = new ArrayList<>();
        String selectQuery = "SELECT * FROM " + currentTable + " WHERE " + KEY_REGION + " = '" + selectedRegion + "'";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                Hospital hospital = new Hospital();
                hospital.setId(cursor.getInt(cursor.getColumnIndex(KEY_ID)));
                hospital.setName(cursor.getString(cursor.getColumnIndex(KEY_NAME)));
                hospital.setRegion(cursor.getString(cursor.getColumnIndex(KEY_REGION)));
                hospital.setCity(cursor.getString(cursor.getColumnIndex(KEY_CITY)));
                hospital.setDescription(cursor.getString(cursor.getColumnIndex(KEY_DESCRIPTION)));
                hospitalList.add(hospital);
            } while (cursor.moveToNext());
        }
        db.close();
        return hospitalList;
    }

    public Terveysasema getTerveysasema(int id) {
        Terveysasema taDetails = new Terveysasema();
        SQLiteDatabase db = this.getReadableDatabase();

        //specify the columns to be fetched
        String[] columns = {KEY_ID, KEY_NAME, KEY_REGION, KEY_DESCRIPTION};
        //Select condition
        String selection = KEY_ID + " = ?";
        //Arguments for selection
        String[] selectionArgs = {String.valueOf(id)};


        Cursor cursor = db.query(TABLE_FI_TERVEYSASEMA, columns, selection,
                selectionArgs, null, null, null);
        if (null != cursor) {
            cursor.moveToFirst();
            taDetails.setId(cursor.getInt(0));
            taDetails.setName(cursor.getString(1));
            taDetails.setRegion(cursor.getString(2));
            taDetails.setDescription(cursor.getString(3));
        }
        db.close();
        return taDetails;
    }

    public void updateTerveysasema(Terveysasema ta) {

    }

}