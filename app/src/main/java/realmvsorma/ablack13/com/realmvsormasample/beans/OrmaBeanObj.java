package realmvsorma.ablack13.com.realmvsormasample.beans;

import com.github.gfx.android.orma.annotation.Column;
import com.github.gfx.android.orma.annotation.OnConflict;
import com.github.gfx.android.orma.annotation.PrimaryKey;
import com.github.gfx.android.orma.annotation.Table;

/**
 * Created by ablack13 on 10.12.16.
 */

@Table
public class OrmaBeanObj {

    public long id;

    @PrimaryKey(onConflict = OnConflict.REPLACE)
    @Column(indexed = true)
    public String name;
    @Column(indexed = true)
    public String lastName;
}
