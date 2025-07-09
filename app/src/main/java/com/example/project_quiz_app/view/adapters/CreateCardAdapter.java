package com.example.project_quiz_app.view.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.project_quiz_app.R;
import com.example.project_quiz_app.model.FlashcardHeaderItem;
import com.example.project_quiz_app.model.FlashcardItem;

import java.util.List;

public class CreateCardAdapter extends RecyclerView.Adapter<CreateCardAdapter.CardViewHolder> {
    private FlashcardHeaderItem headerItem;
    private List<FlashcardItem> items;

    // Constructor
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
            ((HeaderViewHolder) holder).bindHeader(headerItem);
        } else {
            FlashcardItem item = items.get(position - 1); // Trừ 1 vì header chiếm vị trí 0
            ((ItemViewHolder) holder).bindItem(item);
        }
    }

    @Override
    public int getItemCount() {
        return items.size() + 1; // +1 cho header
    }

    // Base ViewHolder
    public static class CardViewHolder extends RecyclerView.ViewHolder {
        public CardViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }

    // ViewHolder cho Header
    public class HeaderViewHolder extends CardViewHolder {
       // TextView tvCategory, tvDescription;
        public HeaderViewHolder(@NonNull View itemView) {
            super(itemView);
           // tvCategory = itemView.findViewById(R.id.tvCategory); // ID trong XML
           // tvDescription = itemView.findViewById(R.id.tvDescription);
        }

        public void bindHeader(FlashcardHeaderItem header) {
          //  tvCategory.setText(header.getCategoryName());
         //   tvDescription.setText(header.getDescription());
        }
    }

    // ViewHolder cho Item
    public class ItemViewHolder extends CardViewHolder {
       // TextView tvTerm, tvDefinition;
        public ItemViewHolder(@NonNull View itemView) {
            super(itemView);
         //   tvTerm = itemView.findViewById(R.id.tvTerm); // ID trong XML
          //  tvDefinition = itemView.findViewById(R.id.tvDefinition);
        }

        public void bindItem(FlashcardItem item) {
          //  tvTerm.setText(item.getTerm());
         //   tvDefinition.setText(item.getDefinition());
        }
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