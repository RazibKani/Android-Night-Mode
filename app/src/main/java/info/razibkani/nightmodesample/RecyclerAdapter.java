package info.razibkani.nightmodesample;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

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

    void setNewsList(ArrayList<News> newsList) {
        this.mNewsList = newsList;
    }

    void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.item_recycler, parent, false);
        return new ItemHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, final int position) {

        News item = mNewsList.get(position);

        ((ItemHolder) holder).title.setText(item.title);
        ((ItemHolder) holder).description.setText(item.description);
        ((ItemHolder) holder).date.setText(item.date);

        if (onItemClickListener != null) {
            ((ItemHolder) holder).bind(onItemClickListener);
        }
    }

    @Override
    public int getItemCount() {
        return mNewsList.size();
    }

    static class ItemHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView title;
        TextView description;
        TextView date;
        ImageView bookmark;

        OnItemClickListener mOnItemClickListener;

        ItemHolder(View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.item_title);
            description = itemView.findViewById(R.id.item_description);
            date = itemView.findViewById(R.id.item_date);
            bookmark = itemView.findViewById(R.id.item_bookmark);
            itemView.setOnClickListener(this);
        }

        void bind(OnItemClickListener listener) {
            mOnItemClickListener = listener;
        }

        @Override
        public void onClick(View view) {
            if (mOnItemClickListener!=null) mOnItemClickListener.onClick(view, getAdapterPosition());
        }
    }

    interface OnItemClickListener {
        void onClick(View view, int position);
    }
}
