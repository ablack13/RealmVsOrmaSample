package realmvsorma.ablack13.com.realmvsormasample;

import android.app.Application;
import android.content.Context;

import com.github.gfx.android.orma.AccessThreadConstraint;

import io.realm.Realm;
import io.realm.RealmConfiguration;
import realmvsorma.ablack13.com.realmvsormasample.beans.OrmaDatabase;

/**
 * Created by ablack13 on 10.12.16.
 */

public class App extends Application {
    private static OrmaDatabase orma;
    private static Context CONTEXT;

    @Override
    public void onCreate() {
        super.onCreate();
        CONTEXT = getApplicationContext();

        initOrma();
        initRealm();
    }

    private void initOrma() {
        orma = OrmaDatabase.builder(CONTEXT)
                .writeOnMainThread(AccessThreadConstraint.WARNING)
                .build();
    }

    private void initRealm() {
        Realm.init(getApplicationContext());
        RealmConfiguration realmConfiguration = new RealmConfiguration.Builder()
                .build();
        Realm.setDefaultConfiguration(realmConfiguration);
    }

    public static Realm realm() {
        return Realm.getDefaultInstance();
    }

    public static OrmaDatabase orma() {
        if (orma == null) {
            orma = OrmaDatabase.builder(CONTEXT)
                    .build();
        }
        return orma;
    }
}
