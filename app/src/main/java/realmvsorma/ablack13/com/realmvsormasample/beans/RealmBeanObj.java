package realmvsorma.ablack13.com.realmvsormasample.beans;

import io.realm.RealmModel;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;
import io.realm.annotations.RealmClass;

/**
 * Created by ablack13 on 10.12.16.
 */

@RealmClass
public class RealmBeanObj extends RealmObject implements RealmModel {
    @PrimaryKey
    public String name;
}
