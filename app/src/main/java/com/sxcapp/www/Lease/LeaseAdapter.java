package com.sxcapp.www.Lease;

import android.app.Dialog;
import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.sxcapp.www.Lease.Bean.LeaseCar;
import com.sxcapp.www.R;
import com.sxcapp.www.Util.Properties;
import com.sxcapp.www.Util.SharedData;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 车辆列表
 * Created by wenleitao on 2017/7/13.
 */

public class LeaseAdapter extends BaseAdapter {
    private final RequestOptions myOption;
    private Context context;
    private List<LeaseCar> list;
    private LayoutInflater mInflater;
    private boolean isLogin ;

    public LeaseAdapter(Context context, List<LeaseCar> list) {
        this.context = context;
        this.list = list;
        myOption = new RequestOptions().placeholder(R.mipmap.car_placeholder);
        mInflater = LayoutInflater.from(context);
    }

    public void addData(List<LeaseCar> data) {
        list.addAll(data);
        notifyDataSetChanged();
    }

    public void loginRefresh() {
        isLogin = SharedData.getInstance().isLogin();
        notifyDataSetChanged();
    }


    @Override
    public int getCount() {
        return list.size() > 0 ? list.size() : 0;
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Holder holder;
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.leasecar_item, null);
            holder = new Holder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (Holder) convertView.getTag();
        }
        String info = list.get(position).getModel_name() + "|" +
                list.get(position).getPl() + "|" + "乘坐" + list.get(position).getNumber_seats() + "人";

        Glide
                .with(context)
                .load(Properties.baseImageUrl + list.get(position).getImage())
                .apply(myOption)
                .into(holder.iv);
        holder.name_tv.setText(list.get(position).getT_name());
        holder.info_tv.setText(info);
        holder.price_tv.setText(list.get(position).getDaily_average_price() + "");
        return convertView;
    }

    static class Holder {
        @BindView(R.id.car_iv)
        ImageView iv;
        @BindView(R.id.car_name)
        TextView name_tv;
        @BindView(R.id.car_info)
        TextView info_tv;
        @BindView(R.id.lease_price)
        TextView price_tv;

        public Holder(View view) {
            ButterKnife.bind(this, view);
        }


    }

    /**
     * @param car
     * @param lease_day
     * @param store_id
     * @param store_name
     * @param picker_time
     * @param g_time      预订车辆dialog
     */
    public void showAdvanceDialog(final LeaseCar car, final String lease_day,
                                  final String store_id, final String store_name,
                                  final String picker_time, final String g_time) {
        final Dialog dialog = new Dialog(context, R.style.ActionSheetDialogStyle);
        View view = LayoutInflater.from(context).inflate(R.layout.choose_car_dialog, null);
        dialog.setContentView(view);
        Window dialogWindow = dialog.getWindow();
        dialogWindow.setGravity(Gravity.CENTER);
        WindowManager.LayoutParams lp = dialogWindow.getAttributes();
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        lp.y = 0;
        dialogWindow.setAttributes(lp);
        dialog.setCanceledOnTouchOutside(true);
        dialog.setCancelable(true);
        dialogWindow.setWindowAnimations(R.style.dialog_anim_style);
        dialog.show();

        ImageView iv = (ImageView) view.findViewById(R.id.car_iv);
        TextView name_tv = (TextView) view.findViewById(R.id.car_name_tv);
        TextView price_tv = (TextView) view.findViewById(R.id.price_tv);
        TextView lease_day_tv = (TextView) view.findViewById(R.id.lease_day_tv);
        TextView car_info_tv = (TextView) view.findViewById(R.id.car_info_tv);
        car_info_tv.setText(car.getPl() + car.getGearbox_type() + "|" + "乘坐" + car.getNumber_seats() + "人");
        Button advance_btn = (Button) view.findViewById(R.id.advance_btn);
        Glide
                .with(context)
                .load(Properties.baseImageUrl + car.getImage())
                .apply(myOption)
                .into(iv);
        name_tv.setText(car.getT_name());
        price_tv.setText(car.getDaily_average_price() + "");
        lease_day_tv.setText(lease_day);
        advance_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (SharedData.getInstance().isLogin()) {
                    dialog.dismiss();
                    ((ChooseCarActivity) mInflater.getContext()).isAbleLease(car, lease_day,
                            store_id, store_name,
                            picker_time, g_time);
                } else {
                    ((ChooseCarActivity) mInflater.getContext()).isToLogin();
                }
            }

        });

    }
}
