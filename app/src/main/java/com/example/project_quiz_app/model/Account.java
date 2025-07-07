package com.example.project_quiz_app.model;


import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "user_profile")
/*
* id INTEGER PRIMARY KEY AUTOINCREMENT, -- ID định danh người dùng, tự động tăng
    user_name TEXT NOT NULL, -- Tên hiển thị của người dùng (bắt buộc nhập)
    email TEXT UNIQUE NOT NULL, -- Email người dùng để login
    password TEXT NOT NULL, -- ơassword
    avatar_path TEXT DEFAULT '', --Đường dẫn ảnh đại diện, mặc định là chuỗi rỗng nếu chưa có
    current_streak INTEGER DEFAULT 0, --Chuỗi ngày học liên tiếp hiện tại (dùng để tạo thói quen học)
    best_streak INTEGER DEFAULT 0, -- Chuỗi dài nhất từng có (ghi nhận kỷ lục liên tục)
    last_study_date TEXT, --Ngày học gần nhất của người dùng (dùng để so streak)
    created_date TEXT DEFAULT CURRENT_TIMESTAMP, --Ngày tạo tài khoản
    updated_date TEXT DEFAULT CURRENT_TIMESTAMP -- Ngày cập nhật thông tin cuối cùng
* */
public class Account {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    private int id;

    @ColumnInfo(name = "user_name")
    @NonNull
    private String userName;

    @ColumnInfo(name = "email")
    @NonNull
    private String email;

    @ColumnInfo(name = "password")
    @NonNull
    private String password;

    @ColumnInfo(name = "avatar_path")
    private String avatarPath = "";

    @ColumnInfo(name = "current_streak")
    private int currentStreak = 0;

    @ColumnInfo(name = "best_streak")
    private int bestStreak = 0;

    @ColumnInfo(name = "last_study_date")
    private String lastStudyDate;

    @ColumnInfo(name = "created_date")
    private String createdDate;

    @ColumnInfo(name = "updated_date")
    private String updatedDate;

    // Constructor mặc định (bắt buộc cho Room)
    public Account(@NonNull String userName, @NonNull String email, @NonNull String password) {
        this.userName = userName;
        this.email = email;
        this.password = password;
        this.avatarPath = "";
        this.currentStreak = 0;
        this.bestStreak = 0;
        // Có thể set created_date và updated_date ở đây hoặc trong DAO
    }
    // Getter và Setter methods
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @NonNull
    public String getUserName() {
        return userName;
    }

    public void setUserName(@NonNull String userName) {
        this.userName = userName;
    }

    @NonNull
    public String getEmail() {
        return email;
    }

    public void setEmail(@NonNull String email) {
        this.email = email;
    }

    @NonNull
    public String getPassword() {
        return password;
    }

    public void setPassword(@NonNull String password) {
        this.password = password;
    }

    public String getAvatarPath() {
        return avatarPath;
    }

    public void setAvatarPath(String avatarPath) {
        this.avatarPath = avatarPath;
    }

    public int getCurrentStreak() {
        return currentStreak;
    }

    public void setCurrentStreak(int currentStreak) {
        this.currentStreak = currentStreak;
    }

    public int getBestStreak() {
        return bestStreak;
    }

    public void setBestStreak(int bestStreak) {
        this.bestStreak = bestStreak;
    }

    public String getLastStudyDate() {
        return lastStudyDate;
    }

    public void setLastStudyDate(String lastStudyDate) {
        this.lastStudyDate = lastStudyDate;
    }

    public String getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(String createdDate) {
        this.createdDate = createdDate;
    }

    public String getUpdatedDate() {
        return updatedDate;
    }

    public void setUpdatedDate(String updatedDate) {
        this.updatedDate = updatedDate;
    }

    @Override
    public String toString() {
        return "UserProfile{" +
                "id=" + id +
                ", userName='" + userName + '\'' +
                ", email='" + email + '\'' +
                ", avatarPath='" + avatarPath + '\'' +
                ", currentStreak=" + currentStreak +
                ", bestStreak=" + bestStreak +
                ", lastStudyDate='" + lastStudyDate + '\'' +
                ", createdDate='" + createdDate + '\'' +
                ", updatedDate='" + updatedDate + '\'' +
                '}';
    }
}
