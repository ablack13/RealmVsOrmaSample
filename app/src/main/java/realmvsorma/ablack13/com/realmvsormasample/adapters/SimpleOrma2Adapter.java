package realmvsorma.ablack13.com.realmvsormasample.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import realmvsorma.ablack13.com.realmvsormasample.R;
import realmvsorma.ablack13.com.realmvsormasample.beans.OrmaBeanObj2;

/**
 * Created by ablack13 on 15.12.16.
 */

public class SimpleOrma2Adapter extends RecyclerView.Adapter<SimpleOrma2Adapter.ViewHolder> {
    private List<OrmaBeanObj2> items;

    public SimpleOrma2Adapter() {
    }

    public void setItems(List<OrmaBeanObj2> items) {
        this.items = items;
        notifyDataSetChanged();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item, parent, false));
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.textView.setText(getItem(position).name);
    }

    @Override
    public int getItemCount() {
        return items != null ? items.size() : null;
    }

    public OrmaBeanObj2 getItem(int pos) {
        return items.get(pos);
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        private TextView textView;

        public ViewHolder(View itemView) {
            super(itemView);
            textView = (TextView) itemView.findViewById(R.id.tv_name);
        }
    }
}
