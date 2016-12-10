package realmvsorma.ablack13.com.realmvsormasample.beans;

import android.support.annotation.Nullable;

import com.github.gfx.android.orma.annotation.Column;
import com.github.gfx.android.orma.annotation.Table;

import io.realm.annotations.PrimaryKey;

/**
 * Created by ablack13 on 10.12.16.
 */

@Table
public class OrmaBeanObj {

    @PrimaryKey
    @Column
    @Nullable
    public String name;
}
