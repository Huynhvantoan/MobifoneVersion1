package com.toan_itc.tn.Adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.toan_itc.tn.Model.DStinThecao;
import com.toan_itc.tn.R;
import com.toan_itc.tn.Utils.Utils;

import java.util.List;

/**
 * Created by PC-07 on 7/20/2015.
 */
public class ThecaoAdapter extends RecyclerView.Adapter<ThecaoAdapter.ViewHolder>  {
    protected final Context context;
    private boolean twoPane;
    private int selectedItem = -1;

    public interface OnItemClickListener {
        void onItemClickTheCao(String link);
    }
    public static final String LOG_TAG = ThecaoAdapter.class.getSimpleName();
    protected OnItemClickListener onItemClickListener;
    private List<DStinThecao> articles;

    public ThecaoAdapter(@NonNull Context context, List<DStinThecao> articles, OnItemClickListener mOnItemClickListenerThecao) {
        if (articles == null) {
            throw new IllegalArgumentException("articles cannot be null");
        }
        this.context = context;
        this.articles = articles;
        this.onItemClickListener = mOnItemClickListenerThecao;
    }
    public void setTwoPane(boolean twoPane) {
        this.twoPane = twoPane;
        Log.d(LOG_TAG, String.format("setTwoPane : %b", this.twoPane));
    }
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(context).inflate(R.layout.item_list_menhgia_card, parent, false);
        return new ViewHolder(context, itemView);
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, final int position) {
        try {
            final DStinThecao data = this.articles.get(position);
            viewHolder.setimg(data.getThumb());
            viewHolder.setmenhgia(data.getTenTin());
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
                onItemClickListener.onItemClickTheCao(articles.get(position).getTenTin());
            });
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public int getItemCount() {
        return articles.size();
    }

    public DStinThecao getLastArticle() {
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
        ImageView img_menhgia;
        TextView txt_menhgia;
        private Context context;

        public ViewHolder(Context context, View itemView) {
            super(itemView);
            this.context = context;
            img_menhgia=(ImageView)itemView.findViewById(R.id.img_menhgia);
            txt_menhgia=(TextView)itemView.findViewById(R.id.txt_menhgia);
        }

        public void setimg(String img) {
            if(img!=null&&!img.equalsIgnoreCase("")) {
                Glide.with(context)
                        .load(img)
                        .crossFade()
                        .into(img_menhgia);
            }else {
                Glide.with(context)
                        .load("https://img.myplus.vn/upload/1443082474_mobi-50k.jpg")
                        .crossFade()
                        .into(img_menhgia);
            }
        }
        public void setmenhgia(String menhgia) {
            txt_menhgia.setText(menhgia);
        }
        public void setOnClickListener(View.OnClickListener listener) {
            img_menhgia.setOnClickListener(listener);
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