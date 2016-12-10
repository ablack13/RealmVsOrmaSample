package realmvsorma.ablack13.com.realmvsormasample;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;

import io.realm.Realm;
import realmvsorma.ablack13.com.realmvsormasample.adapters.OrmaAdapter;
import realmvsorma.ablack13.com.realmvsormasample.beans.OrmaBeanObj;
import realmvsorma.ablack13.com.realmvsormasample.beans.OrmaBeanObj_Relation;

/**
 * Created by ablack13 on 10.12.16.
 */

public class OrmaSampleActivity extends AppCompatActivity {
    private Button btnAdd;
    private OrmaAdapter adapter;
    private RecyclerView recyclerView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sample);
        getSupportActionBar().setTitle("Orma test");

        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        btnAdd = (Button) findViewById(R.id.btn_add);

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                App.realm().executeTransaction(new Realm.Transaction() {
                    @Override
                    public void execute(Realm realm) {
                        final OrmaBeanObj obj = new OrmaBeanObj();
                        obj.name = "New orma item" + System.currentTimeMillis();
                        App.orma().transactionSync(new Runnable() {
                            @Override
                            public void run() {
                                App.orma().relationOfOrmaBeanObj().inserter().execute(obj);
                            }
                        });
                    }
                });
            }
        });
        recyclerView.setLayoutManager(new LinearLayoutManager(OrmaSampleActivity.this));
        adapter = new OrmaAdapter(OrmaSampleActivity.this, fillData());
        recyclerView.setAdapter(adapter);
    }

    private OrmaBeanObj_Relation fillData() {
        return App.orma().relationOfOrmaBeanObj();
    }
}
