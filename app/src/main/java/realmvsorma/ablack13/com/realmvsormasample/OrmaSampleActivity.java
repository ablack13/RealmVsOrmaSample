package realmvsorma.ablack13.com.realmvsormasample;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
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
import realmvsorma.ablack13.com.realmvsormasample.adapters.SimpleOrma2Adapter;
import realmvsorma.ablack13.com.realmvsormasample.adapters.SimpleOrmaAdapter;
import realmvsorma.ablack13.com.realmvsormasample.beans.OrmaBeanObj;
import realmvsorma.ablack13.com.realmvsormasample.beans.OrmaBeanObj2;
import realmvsorma.ablack13.com.realmvsormasample.beans.OrmaBeanObj2_Relation;
import realmvsorma.ablack13.com.realmvsormasample.beans.OrmaBeanObj_Relation;

public class OrmaSampleActivity extends AppCompatActivity {
    private Button btnAdd, btnDispose;
    private SimpleOrmaAdapter adapter;
    private SimpleOrma2Adapter adapter2;
    private RecyclerView recyclerView, recyclerView2;
    private Disposable loadList, loadList2;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sample);
        getSupportActionBar().setTitle("Orma test");


        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        recyclerView2 = (RecyclerView) findViewById(R.id.recycler_view2);

        btnAdd = (Button) findViewById(R.id.btn_add);
        btnDispose = (Button) findViewById(R.id.btn_dispose);

        recyclerView.setLayoutManager(new LinearLayoutManager(OrmaSampleActivity.this));
        recyclerView2.setLayoutManager(new LinearLayoutManager(OrmaSampleActivity.this));
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                App.orma().transactionSync(new Runnable() {
                    @Override
                    public void run() {
                        final OrmaBeanObj obj = new OrmaBeanObj();
                        obj.name = "New orma " + System.currentTimeMillis();
                        obj.lastName = "sdsdasda";
                        App.orma().relationOfOrmaBeanObj().upserter().execute(obj);
                        final OrmaBeanObj2 obj2 = new OrmaBeanObj2();
                        obj2.name = "New orma 2" + System.currentTimeMillis();
                        obj2.lastName = "sdsdasda";
                        App.orma().relationOfOrmaBeanObj2().upserter().execute(obj2);
                    }
                });
            }
        });
        btnDispose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (loadList != null && !loadList.isDisposed()) {
                    Toast.makeText(OrmaSampleActivity.this, "Db updated", Toast.LENGTH_LONG).show();
                    loadList.dispose();
                }
            }
        });
        adapter = new SimpleOrmaAdapter();
        adapter2 = new SimpleOrma2Adapter();
        recyclerView.setAdapter(adapter);
        recyclerView2.setAdapter(adapter2);
        loadList = fillListData("update 1").subscribe(new Consumer<List<OrmaBeanObj>>() {
            @Override
            public void accept(List<OrmaBeanObj> ormaBeanObjs) throws Exception {
                Log.d("subscribe", "subscribe update 1");
                adapter.setItems(ormaBeanObjs);
            }
        });
        loadList2 = fillList2Data("update 2").subscribe(new Consumer<List<OrmaBeanObj2>>() {
            @Override
            public void accept(List<OrmaBeanObj2> ormaBeanObjs) throws Exception {
                Log.d("subscribe", "subscribe update 2");
                adapter2.setItems(ormaBeanObjs);
            }
        });
    }

    private Observable<List<OrmaBeanObj>> fillListData(final String text) {
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
                                Toast.makeText(OrmaSampleActivity.this, text, Toast.LENGTH_LONG).show();
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

    private Observable<List<OrmaBeanObj2>> fillList2Data(final String text) {
        final Disposable[] updaterListenerDisposable = {null};
        return Observable.create(new ObservableOnSubscribe<List<OrmaBeanObj2>>() {
            @Override
            public void subscribe(final ObservableEmitter<List<OrmaBeanObj2>> emitter) throws Exception {
                OrmaBeanObj2_Relation ormaBeanObjs = App.orma().relationOfOrmaBeanObj2();
                Observable<Selector<OrmaBeanObj2, ?>> queryObservable = ormaBeanObjs.createQueryObservable();
                updaterListenerDisposable[0] = queryObservable.subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new Consumer<Selector<OrmaBeanObj2, ?>>() {
                            @Override
                            public void accept(Selector<OrmaBeanObj2, ?> ormaBeanObjs) throws Exception {
                                Toast.makeText(OrmaSampleActivity.this, text, Toast.LENGTH_LONG).show();
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
