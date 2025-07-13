package com.example.project_quiz_app.view.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.example.project_quiz_app.R;
import com.example.project_quiz_app.model.UserTest;

public class TestAdapter extends ListAdapter<UserTest, TestAdapter.VH> {
    public interface OnItemClick { void onItemClick(UserTest t); }
    public interface OnDeleteClick { void onDeleteClick(UserTest t); }

    private final OnItemClick    itemClickListener;
    private final OnDeleteClick  deleteClickListener;

    public TestAdapter(OnItemClick itemClick, OnDeleteClick deleteClick) {
        super(new DiffUtil.ItemCallback<UserTest>() {
            @Override
            public boolean areItemsTheSame(UserTest a, UserTest b) {
                return a.id == b.id;
            }
            @Override
            public boolean areContentsTheSame(UserTest a, UserTest b) {
                return a.title.equals(b.title)
                        && a.createdAt.equals(b.createdAt);
            }
        });
        this.itemClickListener   = itemClick;
        this.deleteClickListener = deleteClick;
    }

    @Override
    public VH onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_user_test, parent, false);
        return new VH(v);
    }

    @Override
    public void onBindViewHolder(VH holder, int position) {
        UserTest test = getItem(position);
        holder.tvTitle.setText(test.title);
        holder.tvDate .setText(test.createdAt);

        // item tap
        holder.itemView.setOnClickListener(v ->
                itemClickListener.onItemClick(test)
        );

        // delete button tap
        holder.btnDelete.setOnClickListener(v ->
                deleteClickListener.onDeleteClick(test)
        );
    }

    static class VH extends RecyclerView.ViewHolder {
        final TextView     tvTitle, tvDate;
        final ImageButton  btnDelete;

        VH(View v) {
            super(v);
            tvTitle   = v.findViewById(R.id.tv_test_title);
            tvDate    = v.findViewById(R.id.tv_test_date);
            btnDelete = v.findViewById(R.id.btn_delete_test);
        }
    }
}
