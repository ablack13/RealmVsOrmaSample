package realmvsorma.ablack13.com.realmvsormasample;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.github.gfx.android.orma.Selector;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import realmvsorma.ablack13.com.realmvsormasample.adapters.SimpleOrmaAdapter;
import realmvsorma.ablack13.com.realmvsormasample.beans.OrmaBeanObj;
import realmvsorma.ablack13.com.realmvsormasample.beans.OrmaBeanObj_Relation;

public class OrmaSampleActivity extends AppCompatActivity {
    private Button btnAdd, btnDispose;
    private SimpleOrmaAdapter adapter;
    private RecyclerView recyclerView;
    private Disposable loadList;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sample);
        getSupportActionBar().setTitle("Orma test");


        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        btnAdd = (Button) findViewById(R.id.btn_add);
        btnDispose = (Button) findViewById(R.id.btn_dispose);

        recyclerView.setLayoutManager(new LinearLayoutManager(OrmaSampleActivity.this));
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final OrmaBeanObj obj = new OrmaBeanObj();
                obj.name = "New orma " + System.currentTimeMillis();
                obj.lastName = "sdsdasda";
                App.orma().relationOfOrmaBeanObj().upserter().execute(obj);
            }
        });
        btnDispose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (loadList != null && !loadList.isDisposed()) {
                    Toast.makeText(OrmaSampleActivity.this, "Load list disposed", Toast.LENGTH_LONG).show();
                    loadList.dispose();
                }
            }
        });
        adapter = new SimpleOrmaAdapter();
        recyclerView.setAdapter(adapter);
        loadList = fillListData().subscribe(new Consumer<List<OrmaBeanObj>>() {
            @Override
            public void accept(List<OrmaBeanObj> ormaBeanObjs) throws Exception {
                adapter.setItems(ormaBeanObjs);
            }
        });
    }

    private Observable<List<OrmaBeanObj>> fillListData() {
        final Disposable[] updaterListenerDisposable = {null};
        return Observable.create(new ObservableOnSubscribe<List<OrmaBeanObj>>() {
            @Override
            public void subscribe(final ObservableEmitter<List<OrmaBeanObj>> emitter) throws Exception {
                OrmaBeanObj_Relation ormaBeanObjs = App.orma().relationOfOrmaBeanObj();
                Observable<Selector<OrmaBeanObj, ?>> queryObservable = ormaBeanObjs.createQueryObservable();
                updaterListenerDisposable[0] = queryObservable.subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new Consumer<Selector<OrmaBeanObj, ?>>() {
                            @Override
                            public void accept(Selector<OrmaBeanObj, ?> ormaBeanObjs) throws Exception {
                                Toast.makeText(OrmaSampleActivity.this, "Db updated", Toast.LENGTH_LONG).show();
                                emitter.onNext(ormaBeanObjs.toList());
                            }
                        });
                emitter.onNext(ormaBeanObjs.selector().toList());
            }
        }).doOnDispose(new Action() {
            @Override
            public void run() throws Exception {
                if (updaterListenerDisposable[0] != null && !updaterListenerDisposable[0].isDisposed()) {
                    updaterListenerDisposable[0].dispose();
                }
            }
        });
    }
}
