package info.razibkani.nightmodesample;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by razibkani on 3/18/17.
 */

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private LayoutInflater mInflater;
    private ArrayList<News> mNewsList;
    private OnItemClickListener onItemClickListener;

    RecyclerAdapter(Context context) {
        mInflater = LayoutInflater.from(context);
        mNewsList = new ArrayList<>();
    }

    public void setNewsList(ArrayList<News> newsList) {
        this.mNewsList = newsList;
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.item_recycler, parent, false);
        return new ItemHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {

        News item = mNewsList.get(position);

        ((ItemHolder) holder).title.setText(item.title);
        ((ItemHolder) holder).description.setText(item.description);
        ((ItemHolder) holder).date.setText(item.date);

        if (onItemClickListener != null) {
            ((ItemHolder) holder).bookmark.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    onItemClickListener.onClick(view, position);
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return mNewsList.size();
    }

    static class ItemHolder extends RecyclerView.ViewHolder {

        TextView title;
        TextView description;
        TextView date;
        ImageView bookmark;

        public ItemHolder(View itemView) {
            super(itemView);
            title = (TextView) itemView.findViewById(R.id.item_title);
            description = (TextView) itemView.findViewById(R.id.item_description);
            date = (TextView) itemView.findViewById(R.id.item_date);
            bookmark = (ImageView) itemView.findViewById(R.id.item_bookmark);
        }
    }

    interface OnItemClickListener {
        void onClick(View view, int position);
    }
}
