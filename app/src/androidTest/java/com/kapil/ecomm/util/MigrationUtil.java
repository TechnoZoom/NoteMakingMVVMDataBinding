package com.kapil.ecomm.util;

import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.arch.persistence.room.migration.Migration;
import android.arch.persistence.room.testing.MigrationTestHelper;
import android.support.test.InstrumentationRegistry;

/**
 * Created by kapilbakshi on 06/02/18.
 */

public class MigrationUtil {

    public static RoomDatabase getDatabaseAfterPerformingMigrations(MigrationTestHelper migrationTestHelper,
                                                                    Class databaseClass, String databaseName,
                                                                    Migration... migrations) {
        RoomDatabase roomDatabase = Room.databaseBuilder(InstrumentationRegistry.getTargetContext(),
                databaseClass, databaseName)
                .addMigrations(migrations)
                .build();
        migrationTestHelper.closeWhenFinished(roomDatabase);
        return roomDatabase;

    }

}



