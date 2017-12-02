package com.toan_itc.tn.Adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.toan_itc.tn.Model.DStinMobileInternet;
import com.toan_itc.tn.R;
import com.toan_itc.tn.Utils.Utils;

import java.util.List;

/**
 * Created by PC-07 on 7/20/2015.
 */
public class DVmobiAdapter extends RecyclerView.Adapter<DVmobiAdapter.ViewHolder>  {
    protected final Context context;
    private boolean twoPane;
    private int selectedItem = -1;

    public interface OnItemClickListener {
        void onItemClickDVMobi(String link);
    }

    public static final String LOG_TAG = DVmobiAdapter.class.getSimpleName();

    protected OnItemClickListener onItemClickListener;

    private List<DStinMobileInternet> articles;

    public DVmobiAdapter(@NonNull Context context, List<DStinMobileInternet> articles, OnItemClickListener onItemClickListener) {
        if (articles == null) {
            throw new IllegalArgumentException("articles cannot be null");
        }
        this.context = context;
        this.articles = articles;
        this.onItemClickListener = onItemClickListener;
    }
    public void setTwoPane(boolean twoPane) {
        this.twoPane = twoPane;
        Log.d(LOG_TAG, String.format("setTwoPane : %b", this.twoPane));
    }
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(context).inflate(R.layout.item_list, parent, false);
        return new ViewHolder(context, itemView);
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, final int position) {
        final DStinMobileInternet data = this.articles.get(position);
        viewHolder.setArticleTitle(data.getTenTin());
        if (!data.getTomtat().equalsIgnoreCase("")) {
            viewHolder.setArticleDetails(data.getTomtat());
        }

        if (twoPane) {
            if (selectedItem == position) {
                viewHolder.setSelected(true);
            } else {
                viewHolder.setSelected(false);
            }
        }

        viewHolder.setOnClickListener(view -> {
            if (twoPane) {
                int oldPosition = selectedItem;
                viewHolder.setSelected(true);
                selectedItem = position;
                notifyItemChanged(oldPosition);
            }
            onItemClickListener.onItemClickDVMobi(data.getLink());
        });
    }

    @Override
    public int getItemCount() {
        return articles.size();
    }

    public DStinMobileInternet getLastArticle() {
        if (articles.size() > 0) {
            return articles.get(articles.size() - 1);
        }
        return null;
    }

    public int getSelectedItem() {
        return selectedItem;
    }

    public void setSelectedItem(int selectedItem) {
        this.selectedItem = selectedItem;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView mTxtName;
        TextView mTxtTomtat;
        private Context context;
        public ViewHolder(Context context, View itemView) {
            super(itemView);
            this.context = context;
            mTxtName=(TextView)itemView.findViewById(R.id.txt_name);
            mTxtTomtat=(TextView)itemView.findViewById(R.id.txt_tomtat);
        }

        public void setArticleTitle(String title) {
            mTxtName.setText(title);
        }

        public void setArticleDetails(String author) {
            mTxtTomtat.setText(author);
        }

        public void setOnClickListener(View.OnClickListener listener) {
            itemView.setOnClickListener(listener);
        }

        @SuppressWarnings("deprecation")
        public void setSelected(boolean isSelected) {
            int color;
            if(Utils.isAndroid6())
                color = ContextCompat.getColor(context, android.R.color.transparent);
            else
                color = context.getResources().getColor(android.R.color.transparent);
            if (isSelected) {
                if(Utils.isAndroid6())
                    color = ContextCompat.getColor(context, android.R.color.white);
                else
                    color = context.getResources().getColor(android.R.color.white);
            }
            itemView.setBackgroundColor(color);
        }
    }
}