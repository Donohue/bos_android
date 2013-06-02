package org.artsinbushwick.bos13;

import java.util.ArrayList;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class Database extends SQLiteOpenHelper {
	private static String DATABASE_NAME = "bos2013";
	private static int DATABASE_VERSION = 24;
	private static String ARTIST_TABLE_NAME = "artists";
	private static String ARTIST_TABLE_CREATE =
			"CREATE TABLE " + ARTIST_TABLE_NAME + " (" +
					"id INTEGER PRIMARY KEY, last_modified INTEGER, name TEXT, active INTEGER);";
	private static String CATEGORY_TABLE_NAME = "categories";
	private static String CATEGORY_TABLE_CREATE =
			"CREATE TABLE " + CATEGORY_TABLE_NAME + "(" +
					"rank INTEGER, name TEXT, section TEXT, id INTEGER PRIMARY KEY," +
					"active INTEGER, last_modified INTEGER);";
	private static String CATEGORY_EVENT_TABLE_NAME = "category_event";
	private static String CATEGORY_EVENT_TABLE_CREATE =
			"CREATE TABLE " + CATEGORY_EVENT_TABLE_NAME + "(" +
					"category_id INTEGER, event_id INTEGER," +
					"FOREIGN KEY(category_id) REFERENCES categories(id)," +
					"FOREIGN KEY(event_id) REFERENCES events(id));";
	private static String EVENT_TABLE_NAME = "events";
	private static String EVENT_TABLE_CREATE =
			"CREATE TABLE " + EVENT_TABLE_NAME + "(" +
					"id INTEGER PRIMARY KEY, short_desc TEXT, section TEXT, active INTEGER, " +
					"room TEXT, last_modified INTEGER, name TEXT, long_desc TEXT, venue INTEGER);";
	private static String ARTIST_EVENT_TABLE_NAME = "artists_events";
	private static String ARTIST_EVENT_TABLE_CREATE =
			"CREATE TABLE " + ARTIST_EVENT_TABLE_NAME + "(" +
					"artist_id INTEGER, event_id INTEGER," +
					"FOREIGN KEY(artist_id) REFERENCES artists(id)," +
					"FOREIGN KEY(event_id) REFERENCES events(id));";
	private static String EVENT_HOURS_TABLE_NAME = "event_hours";
	private static String EVENT_HOURS_TABLE_CREATE =
			"CREATE TABLE " + EVENT_HOURS_TABLE_NAME + "(" +
					"opens INTEGER, closes INTEGER, notes TEXT, event_id INTEGER," +
					"FOREIGN KEY(event_id) REFERENCES events(id));";
	private static String SPONSOR_TABLE_NAME = "sponsors";
	private static String SPONSOR_TABLE_CREATE =
			"CREATE TABLE " + SPONSOR_TABLE_NAME + "(" +
					"name TEXT, website TEXT, id INTEGER PRIMARY KEY, short_desc TEXT," +
					"active INTEGER, sponsor_level String, last_modified INTEGER);";
	private static String STUDIO_TABLE_NAME = "studios";
	private static String STUDIO_TABLE_CREATE =
			"CREATE TABLE " + STUDIO_TABLE_NAME + "(" +
					"id INTEGER PRIMARY KEY, lon FLOAT, active INTEGER, last_modified INTEGER," +
					"map_identifier TEXT, lat FLOAT, address TEXT, name TEXT," +
					"state TEXT, city TEXT, country TEXT, zip TEXT);";
	private static String[] DATABASES = {
		ARTIST_TABLE_NAME,
		CATEGORY_TABLE_NAME,
		EVENT_TABLE_NAME,
		CATEGORY_EVENT_TABLE_NAME,
		ARTIST_EVENT_TABLE_NAME,
		EVENT_HOURS_TABLE_NAME,
		SPONSOR_TABLE_NAME,
		STUDIO_TABLE_NAME
	};
	private static String[] DATABASE_TABLE_CREATE =
			{ARTIST_TABLE_CREATE,
			 CATEGORY_TABLE_CREATE,
			 EVENT_TABLE_CREATE,
			 CATEGORY_EVENT_TABLE_CREATE,
			 ARTIST_EVENT_TABLE_CREATE,
			 EVENT_HOURS_TABLE_CREATE,
			 SPONSOR_TABLE_CREATE,
			 STUDIO_TABLE_CREATE};
	
	public Database(Context c) {
		super(c, DATABASE_NAME, null, DATABASE_VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		for (int i = 0; i < DATABASE_TABLE_CREATE.length; i++) {
			db.execSQL(DATABASE_TABLE_CREATE[i]);
		}
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		for (int i = 0; i < DATABASES.length; i++) {
			db.execSQL("DROP TABLE IF EXISTS " + DATABASES[i]);
		}
		
		onCreate(db);
	}
	
	public void addArtists(ArtistList artists) {
		SQLiteDatabase db = getWritableDatabase();
		for (int i = 0; i < artists.getResult().size(); i++) {
			Artist artist = artists.getResult().get(i);
			String cleanName = (artist.getName() == null)? null: artist.getName().replaceAll("\"", "'");
			String values = artist.getId() + "," + artist.getLastModified() + ",\"" + cleanName + "\"," + artist.getActive();
			String sql = "INSERT OR REPLACE INTO " + ARTIST_TABLE_NAME +
					" (id, last_modified, name, active) VALUES (" + values + ");";
			db.execSQL(sql);
		}
	}
	
	public ArrayList<Artist> getArtists() {
		SQLiteDatabase db = getReadableDatabase();
		String sql = "SELECT * FROM " + ARTIST_TABLE_NAME;
		Cursor cursor = db.rawQuery(sql, new String[0]);
		ArrayList<Artist> artists = new ArrayList<Artist>();
		if (cursor.moveToFirst()) {
			do {
				artists.add(new Artist(cursor));
			} while (cursor.moveToNext());
		}
		
		cursor.close();
		return artists;
	}
	
	public ArrayList<Artist> getArtists(Event e) {
		SQLiteDatabase db = getReadableDatabase();
		String sql = "SELECT artist_id FROM " + ARTIST_EVENT_TABLE_NAME + " WHERE event_id=" + e.getId();
		Cursor cursor = db.rawQuery(sql, new String[0]);
		ArrayList<Artist> artists = new ArrayList<Artist>();
		if (cursor.moveToFirst()) {
			do {
				String sql2 = "SELECT * FROM " + ARTIST_TABLE_NAME + " WHERE id=" + cursor.getInt(0);
				Cursor cursor2 = db.rawQuery(sql2, new String[0]);
				if (cursor2.moveToFirst()) {
					artists.add(new Artist(cursor2));
				}
			} while (cursor.moveToNext());
		}
		
		cursor.close();
		return artists;
	}
	
	public void addCategories(CategoryList categories) {
		SQLiteDatabase db = getWritableDatabase();
		for (int i = 0; i < categories.getResult().size(); i++) {
			Category category = categories.getResult().get(i);
			String category_sql = "INSERT OR REPLACE INTO " + CATEGORY_TABLE_NAME +
					" (rank, name, section, id, active, last_modified) VALUES (" +
					category.getOrder() + ",'" + category.getName() + "','" + category.getGroup() +
					"'," + category.getId() + "," + category.getActive() + "," + category.getLast_modified() + ");";
			db.execSQL(category_sql);
		}
	}
	
	public int countEventsWithCategory(Category category) {
		SQLiteDatabase db = getReadableDatabase();
		String sql = "SELECT COUNT(*) FROM " + CATEGORY_EVENT_TABLE_NAME + " WHERE category_id=" + category.getId() + ";";
		Cursor cursor = db.rawQuery(sql, new String[0]);
		return (cursor.moveToFirst())? cursor.getInt(0): 0;
	}
	
	public Category getCategory(int category_id) {
		SQLiteDatabase db = getReadableDatabase();
		String sql = "SELECT * FROM " + CATEGORY_TABLE_NAME + " WHERE id=" + category_id + ";";
		Cursor cursor = db.rawQuery(sql, new String[0]);
		Category category = null;
		if (cursor.moveToFirst())
			category = new Category(cursor);
		cursor.close();
		return category;
	}
	
	public ArrayList<Category> getCategories() {
		ArrayList<Category> categories = new ArrayList<Category>();
		SQLiteDatabase db = getReadableDatabase();
		String sql = "SELECT * FROM " + CATEGORY_TABLE_NAME + " ORDER BY rank;";
		Cursor cursor = db.rawQuery(sql, new String[0]);
		if (cursor.moveToFirst()) {
			do {
				categories.add(new Category(cursor));
			} while (cursor.moveToNext());
		}
		cursor.close();
		return categories;
	}
	
	public ArrayList<Category> getCategories(Event event) {
		ArrayList<Category> categories = new ArrayList<Category>();
		SQLiteDatabase db = getReadableDatabase();
		String sql = "SELECT category_id FROM " + CATEGORY_EVENT_TABLE_NAME + " WHERE event_id=" + event.getId() + ";";
		Cursor cursor = db.rawQuery(sql, new String[0]);
		if (cursor.moveToFirst()) {
			do {
				String sql2 = "SELECT * FROM " + CATEGORY_TABLE_NAME + " WHERE id=" + cursor.getInt(0) + ";";
				Cursor cursor2 = db.rawQuery(sql2, new String[0]);
				if (cursor2.moveToFirst()) {
					categories.add(new Category(cursor2));
				}
			} while (cursor.moveToNext());
		}
		cursor.close();
		return categories;
	}
	
	public ArrayList<Category> getFeatureCategories(Event event) {
		ArrayList<Category> categories = new ArrayList<Category>();
		SQLiteDatabase db = getReadableDatabase();
		String sql = "SELECT category_id FROM " + CATEGORY_EVENT_TABLE_NAME + " WHERE event_id=" + event.getId() + ";";
		Cursor cursor = db.rawQuery(sql, new String[0]);
		if (cursor.moveToFirst()) {
			do {
				String sql2 = "SELECT * FROM " + CATEGORY_TABLE_NAME + " WHERE id=" + cursor.getInt(0) + ";";
				Cursor cursor2 = db.rawQuery(sql2, new String[0]);
				if (cursor2.moveToFirst()) {
					Category category = new Category(cursor2);
					if (category.getGroup().equals("Event Features"))
						categories.add(category);
				}
			} while (cursor.moveToNext());
		}
		cursor.close();
		return categories;
	}
	
	public ArrayList<Category> getMediaCategories(Event event) {
		ArrayList<Category> categories = new ArrayList<Category>();
		SQLiteDatabase db = getReadableDatabase();
		String sql = "SELECT category_id FROM " + CATEGORY_EVENT_TABLE_NAME + " WHERE event_id=" + event.getId() + ";";
		Cursor cursor = db.rawQuery(sql, new String[0]);
		if (cursor.moveToFirst()) {
			do {
				String sql2 = "SELECT * FROM " + CATEGORY_TABLE_NAME + " WHERE id=" + cursor.getInt(0) + ";";
				Cursor cursor2 = db.rawQuery(sql2, new String[0]);
				if (cursor2.moveToFirst()) {
					Category category = new Category(cursor2);
					if (!category.getGroup().equals("Event Features"))
						categories.add(category);
				}
			} while (cursor.moveToNext());
		}
		cursor.close();
		return categories;
	}
	
	public void addEvents(EventList events) {
		SQLiteDatabase db = getWritableDatabase();
		for (int i = 0; i < events.getResult().size(); i++) {
			Event event = events.getResult().get(i);
			String cleanName = (event.getName() == null)? null: event.getName().replaceAll("\"", "'");
			String cleanShortDesc = (event.getShortDesc() == null)? null: event.getShortDesc().replaceAll("\"", "'");
			String cleanLongDesc = (event.getLongDesc() == null)? null: event.getLongDesc().replaceAll("\"", "'");
			String values = event.getId() + ",\"" + cleanShortDesc + "\",'" + event.getSection() + "'," +
							event.getActive() + ",'" + event.getRoom() + "'," + event.getLastModified() + ",\"" +
							cleanName + "\",\"" + cleanLongDesc + "\"," + event.getVenue();
			String sql = "INSERT OR REPLACE INTO " + EVENT_TABLE_NAME +
					" (id, short_desc, section, active, room, last_modified, name, long_desc, venue) VALUES (" + values + ");";
			db.execSQL(sql);
			
			ArrayList<Integer> artists = event.getArtists();
			for (int j = 0; j < artists.size(); j++) {
				int artist_id = artists.get(j);
				String artist_event_sql = "INSERT OR REPLACE INTO " + ARTIST_EVENT_TABLE_NAME +
											"(artist_id, event_id) VALUES (" + artist_id + "," + event.getId() + ");";
				db.execSQL(artist_event_sql);
			}
			
			ArrayList<Hours> hours_list = event.getHours();
			for (int j = 0; j < hours_list.size(); j++) {
				Hours hours = hours_list.get(j);
				String cleanNotes = (hours.getNotes() == null)? null: hours.getNotes().replace("\"", "'");
				String hours_sql = "INSERT OR REPLACE INTO " + EVENT_HOURS_TABLE_NAME +
						" (opens, closes, notes, event_id) VALUES (" +
						hours.getOpens() + "," + hours.getCloses() + ",\"" + cleanNotes + "\"," + event.getId() + ");";
				db.execSQL(hours_sql);
			}
			
			ArrayList<Integer> category_list = event.getCategories();
			for (int j = 0; j < category_list.size(); j++) {
				Integer category = category_list.get(j);
				String category_sql = "INSERT INTO " + CATEGORY_EVENT_TABLE_NAME +
						" (category_id, event_id) VALUES (" + category + "," + event.getId() + ");";
				db.execSQL(category_sql);
			}
		}
	}
	
	public ArrayList<Event> getEvents() {
		SQLiteDatabase db = getReadableDatabase();
		String sql = "SELECT * FROM " + EVENT_TABLE_NAME;
		Cursor cursor = db.rawQuery(sql, new String[0]);
		ArrayList<Event> events = new ArrayList<Event>();
		if (cursor.moveToFirst()) {
			do {
				Event event = new Event(cursor);
				events.add(event);
			} while (cursor.moveToNext());
		}
		
		cursor.close();
		return events;
	}
	
	public ArrayList<Event> getEvents(Studio studio) {
		SQLiteDatabase db = getReadableDatabase();
		String sql = "SELECT * FROM " + EVENT_TABLE_NAME + " WHERE venue=" + studio.getId();
		Cursor cursor = db.rawQuery(sql, new String[0]);
		ArrayList<Event> events = new ArrayList<Event>();
		if (cursor.moveToFirst()) {
			do {
				Event event = new Event(cursor);
				events.add(event);
			} while (cursor.moveToNext());
		}
		
		cursor.close();
		return events;
	}
	
	public ArrayList<Event> getEventsWithinTimeAtStudio(int opens_time, int closes_time, Studio studio) {
		SQLiteDatabase db = getReadableDatabase();
		String sql = "SELECT e.* FROM " + EVENT_TABLE_NAME + " AS e, " +
					 EVENT_HOURS_TABLE_NAME + " AS h WHERE " +
					 "e.venue=" + studio.getId() + " AND e.id=h.event_id AND " +
					 "h.opens > " + opens_time + " AND " +
					 " h.closes < " + closes_time + " ORDER BY h.opens;";
		Cursor cursor = db.rawQuery(sql, new String[0]);
		ArrayList<Event> events = new ArrayList<Event>();
		if (cursor.moveToFirst()) {
			do {
				events.add(new Event(cursor));
			} while (cursor.moveToNext());
		}
				
		cursor.close();
		return events;
	}
	
	public ArrayList<Event> getFridayEventsAtStudio(Studio category) {
		return getEventsWithinTimeAtStudio(1369958400, 1370080800, category);
	}
	
	public ArrayList<Event> getSaturdayEventsAtStudio(Studio category) {
		return getEventsWithinTimeAtStudio(1370080800, 1370167200, category);
	}
	
	public ArrayList<Event> getSundayEventsAtStudio(Studio category) {
		return getEventsWithinTimeAtStudio(1370167200, 1370253600, category);
	}
	
	public ArrayList<Event> getEventsWithinTimeWithCategory(int opens_time, int closes_time, Category category) {
		SQLiteDatabase db = getReadableDatabase();
		String sql = "SELECT e.* FROM " + EVENT_TABLE_NAME + " AS e, " +
					 EVENT_HOURS_TABLE_NAME + " AS h, " + CATEGORY_EVENT_TABLE_NAME +
					 " AS c WHERE e.section='studios' AND e.id=c.event_id AND " +
					 "c.category_id=" + category.getId() + " AND e.id=h.event_id AND " +
					 "h.opens > " + opens_time + " AND " +
					 " h.closes < " + closes_time + " ORDER BY h.opens;";
		Cursor cursor = db.rawQuery(sql, new String[0]);
		ArrayList<Event> events = new ArrayList<Event>();
		if (cursor.moveToFirst()) {
			do {
				events.add(new Event(cursor));
			} while (cursor.moveToNext());
		}
				
		cursor.close();
		return events;
	}
	
	public ArrayList<Event> getFridayEventsWithCategory(Category category) {
		return getEventsWithinTimeWithCategory(1369958400, 1370080800, category);
	}
	
	public ArrayList<Event> getSaturdayEventsWithCategory(Category category) {
		return getEventsWithinTimeWithCategory(1370080800, 1370167200, category);
	}
	
	public ArrayList<Event> getSundayEventsWithCategory(Category category) {
		return getEventsWithinTimeWithCategory(1370167200, 1370253600, category);
	}
	
	public ArrayList<Event> getEventsWithinTime(int opens_time, int closes_time, String type) {
		SQLiteDatabase db = getReadableDatabase();
		String sql = "SELECT e.* FROM " + EVENT_TABLE_NAME + " AS e, " +
					 EVENT_HOURS_TABLE_NAME + " AS h WHERE e.section='" + type +
					 "' AND e.id=h.event_id AND h.opens > " + opens_time + " AND " +
					 " h.closes < " + closes_time + " ORDER BY h.opens;";
		Cursor cursor = db.rawQuery(sql, new String[0]);
		ArrayList<Event> events = new ArrayList<Event>();
		if (cursor.moveToFirst()) {
			do {
				events.add(new Event(cursor));
			} while (cursor.moveToNext());
		}
				
		cursor.close();
		return events;
	}
	
	public ArrayList<Event> getFridayEvents(String type) {
		return getEventsWithinTime(1369958400, 1370080800, type);
	}
	
	public ArrayList<Event> getSaturdayEvents(String type) {
		return getEventsWithinTime(1370080800, 1370167200, type);
	}
	
	public ArrayList<Event> getSundayEvents(String type) {
		return getEventsWithinTime(1370167200, 1370253600, type);
	}
	
	public ArrayList<Event> getOfficialEvents() {
		SQLiteDatabase db = getReadableDatabase();
		String sql = "SELECT * FROM " + EVENT_TABLE_NAME + " WHERE section='events'";
		Cursor cursor = db.rawQuery(sql, new String[0]);
		ArrayList<Event> events = new ArrayList<Event>();
		if (cursor.moveToFirst()) {
			do {
				Event event = new Event(cursor);
				events.add(event);
			} while (cursor.moveToNext());
		}
		
		cursor.close();
		return events;
	}

	public Event getEvent(int event_id) {
		Event e = null;
		SQLiteDatabase db = getReadableDatabase();
		String sql = "SELECT * FROM " + EVENT_TABLE_NAME + " WHERE id=" + event_id;
		Cursor cursor = db.rawQuery(sql, new String[0]);
		if (cursor.moveToFirst()) {
			e = new Event(cursor);
		}
		
		cursor.close();
		return e;
	}
	
	public ArrayList<Hours> getEventHours(Event event) {
		ArrayList<Hours> hours = new ArrayList<Hours>();
		SQLiteDatabase db = getReadableDatabase();
		String sql = "SELECT * FROM " + EVENT_HOURS_TABLE_NAME + " WHERE event_id=" + event.getId();
		Cursor cursor = db.rawQuery(sql, new String[0]);
		if (cursor.moveToFirst()) {
			do {
				hours.add(new Hours(cursor));
			} while (cursor.moveToNext());
		}
		
		cursor.close();
		return hours;
	}
	
	public void addSponsors(SponsorList sponsors) {
		SQLiteDatabase db = getWritableDatabase();
		for (int i = 0; i < sponsors.getResult().size(); i++) {
			Sponsor sponsor = sponsors.getResult().get(i);
			String cleanName = (sponsor.getName() == null)? null: sponsor.getName().replace("\"", "'");
			String cleanShortDesc = (sponsor.getShortDesc() == null)? null: sponsor.getShortDesc().replace("\"", "'");
			String values = sponsor.getId() + ",'" + sponsor.getWebsite() + "',\"" + cleanShortDesc + "\"," +
							sponsor.getActive() + "," + sponsor.getSponsorLevel() + "," + sponsor.getLastModified() + ",\"" +
							cleanName + "\"";
			String sql = "INSERT OR REPLACE INTO " + SPONSOR_TABLE_NAME +
					" (id, website, short_desc, active, sponsor_level, last_modified, name) VALUES (" + values + ");";
			db.execSQL(sql);
		}
	}
	
	public ArrayList<Sponsor> getSponsors() {
		SQLiteDatabase db = getReadableDatabase();
		String sql = "SELECT * FROM " + SPONSOR_TABLE_NAME;
		Cursor cursor = db.rawQuery(sql, new String[0]);
		ArrayList<Sponsor> sponsors = new ArrayList<Sponsor>();
		if (cursor.moveToFirst()) {
			do {
				Sponsor sponsor = new Sponsor();
				sponsor.setName(cursor.getString(0));
				sponsor.setWebsite(cursor.getString(1));
				sponsor.setId(cursor.getInt(2));
				sponsor.setShortDesc(cursor.getString(3));
				sponsor.setActive(cursor.getInt(4) == 1);
				sponsor.setLastModified(cursor.getInt(5));
				sponsors.add(sponsor);
			} while (cursor.moveToNext());
		}
		
		cursor.close();
		return sponsors;
	}
	
	public void addStudios(StudioList studios) {
		SQLiteDatabase db = getWritableDatabase();
		for (int i = 0; i < studios.getResult().size(); i++) {
			Studio studio = studios.getResult().get(i);
			String values = studio.getId() + "," + studio.getLon() + "," + studio.getLastModified() + ",'" +
							studio.getMap_identifier() + "'," + studio.getLat() + ",'" + studio.getAddress() + "','" +
							studio.getName() + "','" + studio.getState() + "','" + studio.getCity() + "','" +
							studio.getCountry() + "'," + studio.getZip();
			String sql = "INSERT OR REPLACE INTO " + STUDIO_TABLE_NAME +
					" (id, lon, last_modified, map_identifier, lat, address, name, state, city, country, zip)" +
					"VALUES (" + values + ");";
			db.execSQL(sql);
		}
	}
	
	public ArrayList<Studio> getStudios() {
		SQLiteDatabase db = getReadableDatabase();
		String sql = "SELECT * FROM " + STUDIO_TABLE_NAME;
		Cursor cursor = db.rawQuery(sql, new String[0]);
		ArrayList<Studio> studios = new ArrayList<Studio>();
		if (cursor.moveToFirst()) {
			do {
				Studio studio = new Studio(cursor);
				studios.add(studio);
			} while (cursor.moveToNext());
		}
		
		cursor.close();
		return studios;
	}
	
	public ArrayList<Studio> getActiveStudiosForMap() {
		SQLiteDatabase db = getReadableDatabase();
		long time = System.currentTimeMillis() / 1000;
		String sql = "SELECT s.*, COUNT(e.*), e.name FROM " + STUDIO_TABLE_NAME + " AS s, " +
					 EVENT_TABLE_NAME + " AS e, " +
					 EVENT_HOURS_TABLE_NAME + " AS h " +
					 "WHERE e.venue=s.id AND e.id=h.event_id AND " +
					 "h.opens < " + time + " AND h.closes > " + time +
					 "GROUP BY s.id;";
		Cursor cursor = db.rawQuery(sql, new String[0]);
		ArrayList<Studio> studios = new ArrayList<Studio>();
		if (cursor.moveToFirst()) {
			do {
				String event_name = (cursor.getInt(12) > 0)?
									cursor.getInt(12) + " Events":
									cursor.getString(13);
				Studio studio = new Studio(cursor);
				studio.setEventName(event_name);
				studio.setActive(true);
				studios.add(new Studio(cursor));
			} while (cursor.moveToNext());
		}
		cursor.close();
		return studios;
	}
	
	
	
	public Studio getStudio(int studio_id) {
		Studio s = null;
		SQLiteDatabase db = getReadableDatabase();
		String sql = "SELECT * FROM " + STUDIO_TABLE_NAME + " WHERE id=" + studio_id;
		Cursor cursor = db.rawQuery(sql, new String[0]);
		if (cursor.moveToFirst()) {
			s = new Studio(cursor);
		}
		
		cursor.close();
		return s;
	}
}
