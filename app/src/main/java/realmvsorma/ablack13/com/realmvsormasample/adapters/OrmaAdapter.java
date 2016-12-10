package realmvsorma.ablack13.com.realmvsormasample.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.github.gfx.android.orma.Relation;
import com.github.gfx.android.orma.widget.OrmaRecyclerViewAdapter;

import realmvsorma.ablack13.com.realmvsormasample.R;
import realmvsorma.ablack13.com.realmvsormasample.beans.OrmaBeanObj;

/**
 * Created by ablack13 on 10.12.16.
 */

public class OrmaAdapter extends OrmaRecyclerViewAdapter<OrmaBeanObj, OrmaAdapter.ViewHolder> {

    public OrmaAdapter(@NonNull Context context, @NonNull Relation<OrmaBeanObj, ?> relation) {
        super(context, relation);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item, parent, false));
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.name.setText(getItem(position).name);
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private TextView name;

        public ViewHolder(View itemView) {
            super(itemView);
            this.name = (TextView) itemView.findViewById(R.id.tv_name);
        }
    }
}
