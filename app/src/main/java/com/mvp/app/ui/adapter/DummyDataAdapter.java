package com.mvp.app.ui.adapter;

import android.app.Activity;
import android.support.design.widget.BottomSheetDialogFragment;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.mvp.app.R;
import com.mvp.app.data.network.model.DummyData;
import com.mvp.app.ui.fragment.CustomBottomSheetDialogFragment;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DummyDataAdapter extends RecyclerView.Adapter<DummyDataAdapter.ViewHolder> {
    private List<DummyData> dummyDatas;
    private Activity mActivity;
    public DummyDataAdapter(List<DummyData> dummyDatas, Activity activity) {
        this.dummyDatas = dummyDatas;
        this.mActivity=activity;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.row_dummy, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int i) {
        viewHolder.tvDummyName.setText("Name:"+dummyDatas.get(i).getName());
        viewHolder.tvDummyTitle.setText("Title:"+dummyDatas.get(i).getTitle());
        viewHolder.container.setOnClickListener(onClickListener(i));
    }

    private View.OnClickListener onClickListener(final int position) {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               /* final Dialog dialog = new Dialog(mActivity);
                dialog.setContentView(R.layout.dialog_booking);
                dialog.setTitle("Position " + position);
                dialog.setCancelable(true); // dismiss when touching outside Dialog

                // set the custom dialog components - texts and image
                TextView name = (TextView) dialog.findViewById(R.id.name);
                TextView job = (TextView) dialog.findViewById(R.id.job);
                ImageView icon = (ImageView) dialog.findViewById(R.id.image);

                setDataToView(name, job, icon, position);

                dialog.show();*/

                //Initializing a bottom sheet
                BottomSheetDialogFragment bottomSheetDialogFragment = new CustomBottomSheetDialogFragment();

                //show it
                bottomSheetDialogFragment.show(((FragmentActivity)mActivity).getSupportFragmentManager(), bottomSheetDialogFragment.getTag());
            }
        };
    }

    private void setDataToView(TextView name, TextView title, ImageView genderIcon, int position) {
        name.setText(dummyDatas.get(position).getName());
        title.setText(dummyDatas.get(position).getTitle());
        genderIcon.setImageResource(R.mipmap.app_logo);
    }

    @Override
    public int getItemCount() {
        return dummyDatas.size();
    }

    public void addItem(DummyData dummyData) {
        dummyDatas.add(dummyData);
        notifyItemInserted(dummyDatas.size());
    }

    public void removeItem(int position) {
        dummyDatas.remove(position);
        notifyItemRemoved(position);
        notifyItemRangeChanged(position, dummyDatas.size());
    }

    public void updateDataList(List<DummyData> data) {
        dummyDatas.clear();
        dummyDatas.addAll(data);
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        @BindView(R.id.dummyName)
        TextView tvDummyName;
        @BindView(R.id.dummyTitle)
        TextView tvDummyTitle;
        @BindView(R.id.cardView_dummy)
        View container;
        public ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}