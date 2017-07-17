package thecat.example.sqldroid;

import org.flywaydb.core.Flyway;
import org.flywaydb.core.api.FlywayException;
import org.flywaydb.core.api.MigrationInfoService;
import org.flywaydb.core.api.MigrationVersion;
import org.flywaydb.core.api.android.ContextHolder;
import org.flywaydb.core.internal.info.MigrationInfoDumper;

import android.content.Context;
import android.util.Log;


public class FlywayDBMigration {

	public static final String BASE_VERSION_DESCRIPTION = "Base version";
	public static final String BASE_VERSION_NUMBER = "1";
	public static final String BASE_VERSION_SQL = 
			"V" + BASE_VERSION_NUMBER + "__" + 
					BASE_VERSION_DESCRIPTION.replace(' ', '_') + 
					".sql";
	
	public static int migrate(Context context, String dbPath, String password) throws FlywayException {
		
		ContextHolder.setContext(context);
		
		Flyway flyway = new Flyway();
		flyway.setDataSource("jdbc:sqlite:" + dbPath, "", password);
		
		dumpMigrationInfo("Before Flyway migration", flyway);

		if (dbRequiresTheBaseLine(flyway)) {
			Log.v(FlywayDBMigration.class.getCanonicalName(), "the db requires the BASE LINE");
			
			flyway.setBaselineDescription(BASE_VERSION_DESCRIPTION);
			flyway.setBaselineVersion(MigrationVersion.fromVersion(BASE_VERSION_NUMBER));
			flyway.baseline();
		}
		
		Log.v(FlywayDBMigration.class.getCanonicalName(), "executing the migrations...");
		
		int numberOfMigrations = flyway.migrate();
		
		dumpMigrationInfo("After Flyway migration", flyway);
		
		return numberOfMigrations;

	}
	
	private static boolean dbRequiresTheBaseLine(Flyway flyway) {
		boolean result = false;
		
		MigrationInfoService infoService = flyway.info();
		if (infoService.pending().length > 0) {
			if (infoService.pending()[0].getVersion().equals(MigrationVersion.fromVersion("1")) &&
					infoService.pending()[0].getScript().equals(BASE_VERSION_SQL)) {
				result = true;
			}
		}
		
		return result;
	}
	
	private static void dumpMigrationInfo(String headMessage, Flyway flyway) {
		Log.v(FlywayDBMigration.class.getCanonicalName(), headMessage);
		Log.v(FlywayDBMigration.class.getCanonicalName(),
				MigrationInfoDumper.dumpToAsciiTable(flyway.info().all()));

	}
}
