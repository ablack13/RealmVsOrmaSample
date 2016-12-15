package realmvsorma.ablack13.com.realmvsormasample;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;

import io.realm.Realm;
import io.realm.RealmResults;
import io.realm.Sort;
import realmvsorma.ablack13.com.realmvsormasample.adapters.RealmAdapter;
import realmvsorma.ablack13.com.realmvsormasample.beans.RealmBeanObj;

public class RealmSampleActivity extends AppCompatActivity {
    private Button btnAdd;
    private RealmAdapter adapter;
    private RecyclerView recyclerView;

    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sample);
        getSupportActionBar().setTitle("Realm test");

        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        btnAdd = (Button) findViewById(R.id.btn_add);

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                App.realm().executeTransaction(new Realm.Transaction() {
                    @Override
                    public void execute(Realm realm) {
                        RealmBeanObj obj = new RealmBeanObj();
                        obj.name = "New realm item" + System.currentTimeMillis();
                        realm.copyToRealmOrUpdate(obj);
                    }
                });
            }
        });
        recyclerView.setLayoutManager(new LinearLayoutManager(RealmSampleActivity.this));
        adapter = new RealmAdapter(RealmSampleActivity.this, fillData());
        recyclerView.setAdapter(adapter);
    }

    private RealmResults<RealmBeanObj> fillData() {
        return App.realm().where(RealmBeanObj.class).findAllSorted("name", Sort.ASCENDING);
    }
}