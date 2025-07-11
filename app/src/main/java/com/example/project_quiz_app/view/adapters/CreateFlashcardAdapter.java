package com.example.project_quiz_app.view.adapters;

import static java.lang.Integer.parseInt;

import android.content.Context;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.project_quiz_app.R;
import com.example.project_quiz_app.model.AppDatabase;
import com.example.project_quiz_app.model.CardReview;
import com.example.project_quiz_app.model.FlashcardItem;
import com.example.project_quiz_app.view.anim.FlipAnimation;

import java.util.List;


public class CreateFlashcardAdapter extends RecyclerView.Adapter<CreateFlashcardAdapter.ViewHolder> {

    private List<FlashcardItem> data;


    public CreateFlashcardAdapter(List<FlashcardItem> data) {
        this.data = data;
    }
    // lấy data theo postiton trên list đang hiên thị
    public FlashcardItem getItemByPosition(int position) {
        return data.get(position);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View v = inflater.inflate(R.layout.item_flashcard, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        FlashcardItem item = data.get(position);
        holder.setFlashcardItem(item);
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private FlashcardItem flashcardItem;
        private TextView tvQuestion, tvAnswer;
        private View cardFront, cardBack;
        private boolean isFront = true;
        private boolean isAnimating = false;
        private ImageView ivStar;
        private AppDatabase db = AppDatabase.getInstance(itemView.getContext());
        private int userId;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            bindingView();
            bindingAction();
        }

        private void bindingView() {
            tvQuestion = itemView.findViewById(R.id.textQuestion);
            tvAnswer = itemView.findViewById(R.id.textAnswer);
            cardFront = itemView.findViewById(R.id.cardFront);
            cardBack = itemView.findViewById(R.id.cardBack);
            ivStar = itemView.findViewById(R.id.imgStar);

            // Set camera distance để xoay 3D mượt
            float scale = itemView.getResources().getDisplayMetrics().density;
            cardFront.setCameraDistance(8000 * scale);
            cardBack.setCameraDistance(8000 * scale);

            // Lấy user_id
            SharedPreferences sharedPreferences = itemView.getContext().getSharedPreferences("user_info", Context.MODE_PRIVATE);
            String userIdStr = sharedPreferences.getString("user_id", null);
            if (userIdStr == null) {
                return;
            }
            userId = parseInt(userIdStr);
        }

        private void bindingAction() {
            itemView.setOnClickListener(this::onCardClick);
            ivStar.setOnClickListener(this::onStarClick);
        }
        private void delete(View view) {
            db.cardReviewDao().deleteAllCardReviews();
        }
        private void onStarClick(View view) {
            CardReview existing = db.cardReviewDao().findCardReview(flashcardItem.getId(), userId);
            int newStarValue = 1;

            if (existing != null) {
                newStarValue = existing.getIsStarred() == 1 ? 0 : 1;
                db.cardReviewDao().updateStar(flashcardItem.getId(), userId, newStarValue);
            } else {
                CardReview cardReview = new CardReview(userId, flashcardItem.getId(), "viewed");
                cardReview.setIsStarred(1); // Gán luôn star khi chưa có
                db.cardReviewDao().insertCardReview(cardReview);
            }

            String message = newStarValue == 1 ? "Đã gán sao " : "Đã gỡ sao ";
            Toast.makeText(itemView.getContext(), message + flashcardItem.getId(), Toast.LENGTH_SHORT).show();
        }



        private void onCardClick(View view) {
            if (isAnimating) return; // Ngăn click khi đang animation
            isAnimating = true;

            // Tạo animation flip
            FlipAnimation flip = isFront
                    ? new FlipAnimation(cardFront, cardBack)
                    : new FlipAnimation(cardBack, cardFront);

            // Lắng nghe animation kết thúc
            flip.setAnimationListener(new Animation.AnimationListener() {
                @Override
                public void onAnimationStart(Animation animation) {}

                @Override
                public void onAnimationEnd(Animation animation) {
                    isFront = !isFront;
                    isAnimating = false;
                }

                @Override
                public void onAnimationRepeat(Animation animation) {}
            });

            // Bắt đầu animation
            itemView.startAnimation(flip);
            // Ghi vao db flashcard_review

            // Tìm bản ghi hiện tại
            CardReview existing = db.cardReviewDao().findCardReview(flashcardItem.getId(), userId);

            if (existing != null) {
                db.cardReviewDao().updateReviewType(flashcardItem.getId(), userId, "viewed");
            } else {
                CardReview cardReview = new CardReview(userId, flashcardItem.getId(), "viewed");
                cardReview.setIsStarred(0); // Mặc định chưa gán sao
                db.cardReviewDao().insertCardReview(cardReview);
            }

            Toast.makeText(itemView.getContext(), "Đã thêm viewed cho " + flashcardItem.getId(), Toast.LENGTH_SHORT).show();
        }


        public void setFlashcardItem(FlashcardItem item) {
            this.flashcardItem = item; // LƯU DỮ LIỆU TẠI ĐÂY
            tvQuestion.setText(item.getTerm());
            tvAnswer.setText(item.getDefinition());
        }

    }
}
