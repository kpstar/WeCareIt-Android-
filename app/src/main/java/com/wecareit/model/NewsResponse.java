package com.wecareit.model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class NewsResponse extends BaseModel {

    @SerializedName("author")
    private AuthorRes author;

    @SerializedName("creation_date")
    private String creation_date;

    @SerializedName("id")
    private int id;

    @SerializedName("message")
    private String message;

    @SerializedName("mentioned_users")
    private ArrayList<Mentioned_users> mentioned_users;

    @SerializedName("seen_by")
    private ArrayList<Seen_by> seen_by;

    @SerializedName("me_mentioned")
    private boolean me_mentioned;

    @SerializedName("new_reply")
    private boolean new_reply;

    @SerializedName("replies")
    private ArrayList<RepliesRes> replies;

    @SerializedName("reply_count")
    private int reply_count;

    public NewsResponse(AuthorRes author, String creation_date, int id, String message, ArrayList<Mentioned_users> mentioned_users,
                        ArrayList<Seen_by> seen_by, boolean me_mentioned, boolean new_reply, ArrayList<RepliesRes> replies, int reply_count){
        this.author = author;
        this.creation_date = creation_date;
        this.id = id;
        this.message = message;
        this.mentioned_users = mentioned_users;
        this.seen_by = seen_by;
        this.me_mentioned = me_mentioned;
        this.new_reply = new_reply;
        this.replies = replies;
        this.reply_count = reply_count;
    }

    public AuthorRes getAuthor() {
        return author;
    }

    public void setAuthor(AuthorRes author) {
        this.author = author;
    }

    public String getCreation_date() {
        return creation_date;
    }

    public void setCreation_date(String creation_date) {
        this.creation_date = creation_date;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public ArrayList<Mentioned_users> getMentioned_users() {
        return mentioned_users;
    }

    public void setMentioned_users(ArrayList<Mentioned_users> mentioned_users) {
        this.mentioned_users = mentioned_users;
    }

    public ArrayList<Seen_by> getSeen_by() {
        return seen_by;
    }

    public void setSeen_by(ArrayList<Seen_by> seen_by) {
        this.seen_by = seen_by;
    }

    public boolean isMe_mentioned() {
        return me_mentioned;
    }

    public void setMe_mentioned(boolean me_mentioned) {
        this.me_mentioned = me_mentioned;
    }

    public boolean isNew_reply() {
        return new_reply;
    }

    public void setNew_reply(boolean new_reply) {
        this.new_reply = new_reply;
    }

    public ArrayList<RepliesRes> getReplies() {
        return replies;
    }

    public void setReplies(ArrayList<RepliesRes> replies) {
        this.replies = replies;
    }

    public int getReply_count() {
        return reply_count;
    }

    public void setReply_count(int reply_count) {
        this.reply_count = reply_count;
    }
}
