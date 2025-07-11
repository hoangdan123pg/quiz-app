package com.example.project_quiz_app.view.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.project_quiz_app.R;
import com.example.project_quiz_app.model.FlashcardHeaderItem;
import com.example.project_quiz_app.model.FlashcardItem;

import java.util.ArrayList;
import java.util.List;

public class CreateCardAdapter extends RecyclerView.Adapter<CreateCardAdapter.CardViewHolder> {

    private FlashcardHeaderItem headerItem;
    private List<FlashcardItem> items;

    private HeaderViewHolder headerViewHolder; // giữ HeaderViewHolder
    private final List<ItemViewHolder> itemViewHolders = new ArrayList<>(); // giữ các ItemViewHolder

    public CreateCardAdapter(FlashcardHeaderItem headerItem, List<FlashcardItem> items) {
        this.headerItem = headerItem;
        this.items = items;
    }

    @Override
    public int getItemViewType(int position) {
        return (position == 0) ? 0 : 1;
    }

    @NonNull
    @Override
    public CardViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == 0) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.item_create_layout_harder, parent, false);
            return new HeaderViewHolder(view);
        } else {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.item_create_card, parent, false);
            return new ItemViewHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull CardViewHolder holder, int position) {
        if (getItemViewType(position) == 0) {
            HeaderViewHolder headerHolder = (HeaderViewHolder) holder;
            headerHolder.bindHeader(headerItem);
            headerViewHolder = headerHolder;  // lưu lại
        } else {
            FlashcardItem item = items.get(position - 1);
            ItemViewHolder itemHolder = (ItemViewHolder) holder;
            itemHolder.bindItem(item);

            // Lưu lại, tránh lưu trùng
            if (!itemViewHolders.contains(itemHolder)) {
                itemViewHolders.add(itemHolder);
            }
        }
    }

    @Override
    public int getItemCount() {
        return items.size() + 1;
    }

    // ======================== ViewHolder ===========================

    public static class CardViewHolder extends RecyclerView.ViewHolder {
        public CardViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }

    public class HeaderViewHolder extends CardViewHolder {
        private EditText tvCode, tvCategory, tvDescription;

        public HeaderViewHolder(@NonNull View itemView) {
            super(itemView);
            tvCode = itemView.findViewById(R.id.etCode);
            tvCategory = itemView.findViewById(R.id.etCategory);
            tvDescription = itemView.findViewById(R.id.etAIcanHelp);
        }

        public void bindHeader(FlashcardHeaderItem header) {
            tvCode.setText(header.getCategortyTitle());
            tvCategory.setText(header.getCategoryDescription());
            tvDescription.setText(header.getDescriptionForAI());
        }

        public FlashcardHeaderItem getUpdatedHeader() {
            return new FlashcardHeaderItem(
                    tvCode.getText().toString().trim(),
                    tvCategory.getText().toString().trim(),
                    tvDescription.getText().toString().trim()
            );
        }
    }

    public class ItemViewHolder extends CardViewHolder {
        private EditText etTerm, etDefinition;

        public ItemViewHolder(@NonNull View itemView) {
            super(itemView);
            etTerm = itemView.findViewById(R.id.editTextText);
            etDefinition = itemView.findViewById(R.id.editTextText2);
        }

        public void bindItem(FlashcardItem item) {
            etTerm.setText(item.getTerm());
            etDefinition.setText(item.getDefinition());
        }

        public FlashcardItem getUpdatedItem() {
            return new FlashcardItem(
                    0,
                    etTerm.getText().toString().trim(),
                    etDefinition.getText().toString().trim()
            );
        }
    }

    // =================== Phần lấy dữ liệu ========================

    public FlashcardHeaderItem getUpdatedHeader() {
        if (headerViewHolder != null) {
            return headerViewHolder.getUpdatedHeader();
        }
        return headerItem;
    }

    public List<FlashcardItem> getUpdatedItems() {
        List<FlashcardItem> updatedItems = new ArrayList<>();
        for (ItemViewHolder holder : itemViewHolders) {
            updatedItems.add(holder.getUpdatedItem());
        }
        return updatedItems;
    }
}



//private List<FlashcardItem> data; // bien cung cap du lieu cho Recycle View
//
//// Constructor truyền số lượng item (hoặc danh sách nếu có data thật)
//public CreateCardAdapter(List<FlashcardItem> data) {
//    this.data = data;
//}
//int count = 0;
//
//@NonNull
//@Override
//public CardViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
//    if (viewType == 0) {
//        View view = LayoutInflater.from(parent.getContext())
//                .inflate(R.layout.item_create_layout_harder, parent, false);  // layout đặc biệt
//        return new CardViewHolder(view);
//    } else {
//        View view = LayoutInflater.from(parent.getContext())
//                .inflate(R.layout.item_create_card, parent, false);  // layout thường
//        return new CardViewHolder(view);
//    }
//}
//
//@Override
//public void onBindViewHolder(@NonNull CardViewHolder holder, int position) {
//    // Nếu có data thì bind tại đây
//    // Example:
//    // holder.title.setText("Tiêu đề " + position);
//}
//
//@Override
//public int getItemCount() {return data != null ? data.size() : 0;}
//@Override
//public int getItemViewType(int position) {
//    if (position == 0) {
//        return 0;  // Layout đặc biệt
//    } else {
//        return 1;  // Layout item bình thường
//    }
//}
//static class CardViewHolder extends RecyclerView.ViewHolder {
//    public CardViewHolder(@NonNull View itemView) {
//        super(itemView);
//        // Tìm các view bên trong card nếu cần
//        // Example:
//        // TextView title = itemView.findViewById(R.id.tv_title);
//    }
//}



// 🟡 Thu thập dữ liệu từ giao diện (EditText)
//public FlashcardHeaderItem getHeaderFromUI() {
//    RecyclerView.ViewHolder vh = recyclerView.findViewHolderForAdapterPosition(0);
//    if (vh instanceof HeaderViewHolder) {
//        return ((HeaderViewHolder) vh).getUpdatedHeader();
//    }
//    return headerItem; // fallback nếu chưa bind được UI
//}
//
//public List<FlashcardItem> getItemsFromUI() {
//    List<FlashcardItem> updatedItems = new ArrayList<>();
//    for (int i = 1; i < getItemCount(); i++) {
//        RecyclerView.ViewHolder vh = recyclerView.findViewHolderForAdapterPosition(i);
//        if (vh instanceof ItemViewHolder) {
//            updatedItems.add(((ItemViewHolder) vh).getUpdatedItem());
//        } else if (i - 1 < items.size()) {
//            // fallback nếu ViewHolder chưa render (do off-screen)
//            updatedItems.add(items.get(i - 1));
//        }
//    }
//    return updatedItems;
//}