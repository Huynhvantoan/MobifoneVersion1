package com.toan_itc.tn.Adapter;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.afollestad.materialdialogs.MaterialDialog;
import com.toan_itc.tn.Lib.ripeffect.RippleViewLinear;
import com.toan_itc.tn.Model.DSlienheThucuoc;
import com.toan_itc.tn.R;
import com.toan_itc.tn.Utils.Utils;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by PC-07 on 7/20/2015.
 */
public class ThuCuocLHAdapter extends RecyclerView.Adapter<ThuCuocLHAdapter.ViewHolder> {
    protected final Context context;
    private boolean twoPane;
    private int selectedItem = -1;
    static String phone = "0";
    public interface OnItemClickListener {
        void onItemClickThuCuocLH(String link);
    }

    public static final String LOG_TAG = ThuCuocLHAdapter.class.getSimpleName();

    protected OnItemClickListener onItemClickListener;

    private List<DSlienheThucuoc> articles;

    public ThuCuocLHAdapter(@NonNull Context context, List<DSlienheThucuoc> articles, OnItemClickListener onItemClickListener) {
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
        View itemView = LayoutInflater.from(context).inflate(R.layout.item_list_lienhe, parent, false);
        return new ViewHolder(context, itemView);
    }
    @Override
    public void onBindViewHolder(ViewHolder viewHolder, final int position) {
        final DSlienheThucuoc data = this.articles.get(position);
        viewHolder.setten(data.getTenTK());
        viewHolder.settk(data.getTKNH());
        viewHolder.settennh(data.getTenNH());
        phone = data.getSDT();
        viewHolder.setsdt(phone);
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
            onItemClickListener.onItemClickThuCuocLH(phone);
        });
    }
    @Override
    public int getItemCount() {
        return articles.size();
    }
    public DSlienheThucuoc getLastArticle() {
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
        @BindView(R.id.txt_name)
         TextView mTxtName;
        @BindView(R.id.txt_tennh)
         TextView mTxtTennh;
        @BindView(R.id.txt_tknh)
         TextView mTxtTknh;
        @BindView(R.id.txt_sdt)
         TextView mTxtSdt;
        @BindView(R.id.layout_click)
         RippleViewLinear mLayoutClick;
        @BindView(R.id.card_ListView)
         CardView mCardListView;
        private Context context;
        public ViewHolder(Context context, View itemView) {
            super(itemView);
            this.context = context;
            ButterKnife.bind(this,itemView);
        }

        public void setten(String name) {
            mTxtName.setText(name);
        }

        public void settk(String tk) {
            mTxtTknh.setText(tk);
        }

        public void settennh(String tennh) {
            mTxtTennh.setText(tennh);
        }

        public void setsdt(String sdt) {
            mTxtSdt.setText(sdt);
        }

        public void setOnClickListener(View.OnClickListener listener) {
            itemView.setOnClickListener(listener);
        }

        @SuppressWarnings("deprecation")
        public void setSelected(boolean isSelected) {
            int color;
            if (Utils.isAndroid6())
                color = ContextCompat.getColor(context, android.R.color.transparent);
            else
                color = context.getResources().getColor(android.R.color.transparent);
            if (isSelected) {
                if (Utils.isAndroid6())
                    color = ContextCompat.getColor(context, android.R.color.white);
                else
                    color = context.getResources().getColor(android.R.color.white);
            }
            itemView.setBackgroundColor(color);
        }
        @OnClick(R.id.layout_click) public void click_call_phone(View v) {
            try {
                Log.wtf("phone=",phone);
                new MaterialDialog.Builder(context)
                        .title("Hot Line")
                        .content("Bạn có muốn gọi cho số điện thoại này!")
                        .positiveText("Đồng Ý")
                        .negativeText("Không")
                        .onPositive((dialog, which) -> {
                                    dialog.dismiss();
                                    Intent callIntent = new Intent(Intent.ACTION_CALL);
                                    callIntent.setData(Uri.parse("tel:" + phone));
                                    if (ActivityCompat.checkSelfPermission(context, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                                        //    ActivityCompat#requestPermissions
                                        // here to request the missing permissions, and then overriding
                                        //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                                        //                                          int[] grantResults)
                                        // to handle the case where the user grants the permission. See the documentation
                                        // for ActivityCompat#requestPermissions for more details.
                                        return;
                                    }
                                    context.startActivity(callIntent);
                                }
                        )
                        .onNegative((materialDialog1, dialogAction) -> materialDialog1.dismiss())
                        .buttonRippleColorRes(R.color.primary_light)
                        .iconRes(R.mipmap.ic_launcher)
                        .positiveColorRes(R.color.primary_dark)
                        .negativeColorRes(R.color.text_color)
                        .show();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}