package realmvsorma.ablack13.com.realmvsormasample.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import io.realm.OrderedRealmCollection;
import io.realm.RealmRecyclerViewAdapter;
import realmvsorma.ablack13.com.realmvsormasample.R;
import realmvsorma.ablack13.com.realmvsormasample.beans.RealmBeanObj;

/**
 * Created by ablack13 on 10.12.16.
 */

public class RealmAdapter extends RealmRecyclerViewAdapter<RealmBeanObj, RealmAdapter.ViewHolder> {

    public RealmAdapter(@NonNull Context context, @Nullable OrderedRealmCollection<RealmBeanObj> data) {
        super(context, data, true);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item, parent, false));
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.name.setText(getData().get(position).name);
    }


    public static class ViewHolder extends RecyclerView.ViewHolder {
        private TextView name;

        public ViewHolder(View itemView) {
            super(itemView);
            this.name = (TextView) itemView.findViewById(R.id.tv_name);
        }
    }
}
