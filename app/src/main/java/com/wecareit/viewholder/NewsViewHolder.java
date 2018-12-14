package com.wecareit.viewholder;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.GradientDrawable;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.thoughtbot.expandablerecyclerview.viewholders.GroupViewHolder;
import com.wecareit.LoginActivity;
import com.wecareit.R;
import com.wecareit.adapter.CommentListAdapter;
import com.wecareit.adapter.DownloadImage;
import com.wecareit.common.Global;
import com.wecareit.fragments.news.NewsFragment;
import com.wecareit.model.Author;
import com.wecareit.model.News;
import com.wecareit.model.NewsResponse;
import com.wecareit.model.Replies;
import com.wecareit.model.RepliesRes;
import com.wecareit.model.Seen_by;
import com.wecareit.model.UpdateNews;
import com.wecareit.model.UpdateTitle;
import com.wecareit.model.Userlist;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import de.hdodenhof.circleimageview.CircleImageView;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import us.feras.mdv.MarkdownView;

public class NewsViewHolder extends RecyclerView.ViewHolder {
    private NewsResponse news;
    private String stringTime = "";
    private int flag_comment=0;
    private ArrayList<RepliesRes> replies;
    private ArrayList<Seen_by> seen_bies;
    private Context context;

    private TextView mUsername;
    private TextView mTime;
    private MarkdownView mMessage, mSeenBy;
    private EditText etUpdateTitle;
    private EditText etPostComment;
    private TextView mReplycounts;
    private Button mSave, mCancel;
    private ImageView mUserImg;
    private ImageView ivPostComment;
    private ImageView mResImag;
    private CircleImageView ivEdit;
    private ImageView ivDel;
    private LinearLayout layoutComment,newsrow, lnComment, lnList;
    private String sRead_by;

    public NewsViewHolder(@NonNull View itemView) {
        super(itemView);

        context = itemView.getContext();

        mUsername = (TextView) itemView.findViewById(R.id.tvUsername_newslist);
        mTime = (TextView) itemView.findViewById(R.id.tvTime_newslist);
        mMessage = (MarkdownView) itemView.findViewById(R.id.tvMessage_newslist);
        mSeenBy = (MarkdownView) itemView.findViewById(R.id.tvSeenBy_newslist);
        etUpdateTitle = (EditText) itemView.findViewById(R.id.etMessage_newslist);
        etPostComment = (EditText) itemView.findViewById(R.id.editAddcomment_neslist);
        mReplycounts = (TextView) itemView.findViewById(R.id.tvCommentnumber_newslist);
        mUserImg = (ImageView) itemView.findViewById(R.id.userImg_newslist);
        mResImag = (ImageView) itemView.findViewById(R.id.btnRespond_newslist);
        mSave = (Button) itemView.findViewById(R.id.btnAccept);
        mCancel = (Button) itemView.findViewById(R.id.btnCancel);
        ivEdit = (CircleImageView) itemView.findViewById(R.id.ivEdit_newslist);
        //ivDel = (ImageView) itemView.findViewById(R.id.ivDelete_newslist);
        ivPostComment = (ImageView) itemView.findViewById(R.id.btnAddcomment_newslist);
//        ivUpdateTitle = (ImageView) itemView.findViewById(R.id.ivUpdateTitle_newslist);
        layoutComment = (LinearLayout) itemView.findViewById(R.id.editComent_newslist);
        lnComment = (LinearLayout) itemView.findViewById(R.id.lnComment_newsfragment);
        newsrow = (LinearLayout) itemView.findViewById(R.id.row_news);
        lnList = (LinearLayout) itemView.findViewById(R.id.lnList_newsrow);
        sRead_by = "<b>\u2714 Läst av: </b>";

        GradientDrawable gd = new GradientDrawable();
        gd.setShape(GradientDrawable.RECTANGLE);
        gd.setStroke(2,Color.LTGRAY);
        gd.setCornerRadius(5.0f);

        newsrow.setBackground(gd);

        lnComment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(flag_comment ==0) {
                    layoutComment.setVisibility(View.VISIBLE);
                    flag_comment =1;
                } else {
                    layoutComment.setVisibility(View.GONE);
                    flag_comment =0;
                }
            }
        });
        ivEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showPopup(v);
            }
        });
        mCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mMessage.setVisibility(View.VISIBLE);
                mSeenBy.setVisibility(View.VISIBLE);
                etUpdateTitle.setVisibility(View.GONE);
                mSave.setVisibility(View.GONE);
                mCancel.setVisibility(View.GONE);
            }
        });
        mSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updateTitle();
            }
        });

        ivPostComment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                postComment();
                Global.newsFragment = NewsFragment.createInstance();
                Global.fragmentManager.beginTransaction()
                        .replace(R.id.fragment_container, Global.newsFragment)
                        .addToBackStack(null)
                        .commit();
            }
        });
    }

    public void showPopup(View v) {
        PopupMenu popup = new PopupMenu(context, v);
        MenuInflater inflater = popup.getMenuInflater();
        inflater.inflate(R.menu.menu_register, popup.getMenu());
        popup.show();

        popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                mMessage.setVisibility(View.GONE);
                mSeenBy.setVisibility(View.GONE);
                etUpdateTitle.setVisibility(View.VISIBLE);
                mSave.setVisibility(View.VISIBLE);
                mCancel.setVisibility(View.VISIBLE);
                etUpdateTitle.setFocusable(true);
                etUpdateTitle.setText(news.getMessage());
                return false;
            }
        });
    }


    public void postComment(){

        UpdateNews updateNews;
        ArrayList<Userlist> userlist = new ArrayList<Userlist>();
        Userlist user = new Userlist(Global.user.getId(),Global.user.getFirstname(),Global.user.getLastname(),Global.user.getTitle());
        userlist.add(user);
        updateNews = new UpdateNews(etPostComment.getText().toString(),userlist);

        Call<NewsResponse> apiCall = Global.getAPIService.postComment( "Token "+Global.token, news.getId(), updateNews);
        Log.d("UpdateMsg",apiCall.toString());
        apiCall.enqueue(new Callback<NewsResponse>() {
            @Override
            public void onResponse(Call<NewsResponse> call, Response<NewsResponse> response) {

                if(response.code() == 401){
                    Intent intent = new Intent(context, LoginActivity.class);
                    context.startActivity(intent);
                    //context.finish();
                }

            }

            @Override
            public void onFailure(Call<NewsResponse> call, Throwable t) {
                Log.d("UpdateMsg","Failed");

            }
        });
    }

    public void updateTitle(){
        UpdateNews updateNews;
        ArrayList<Userlist> userlist = new ArrayList<Userlist>();
        Userlist user = new Userlist(Global.user.getId(),Global.user.getFirstname(),Global.user.getLastname(),Global.user.getTitle());
        userlist.add(user);
        updateNews = new UpdateNews(etUpdateTitle.getText().toString(),userlist);
        Log.d("updateNews",updateNews.toJSON()+ news.getId());
        Call<NewsResponse> apiCall = Global.getAPIService.updateTitle("Token "+ Global.token, news.getId(), updateNews, news.isMe_mentioned() == true ? "true": "false");
        Log.d("UpdateMsg",apiCall.toString());
        apiCall.enqueue(new Callback<NewsResponse>() {
            @Override
            public void onResponse(Call<NewsResponse> call, Response<NewsResponse> response) {

                if (response.isSuccessful()) {
                    Log.d("UpdateMsg",""+news.getId());
                    etUpdateTitle.setVisibility(View.GONE);
                    mSave.setVisibility(View.GONE);
                    mCancel.setVisibility(View.GONE);
                    mMessage.setVisibility(View.VISIBLE);
                    if (news.getSeen_by().size() > 0) {
                        mSeenBy.setVisibility(View.VISIBLE);
                    }
                    mMessage.loadMarkdown(etUpdateTitle.getText().toString());
                }
            }

            @Override
            public void onFailure(Call<NewsResponse> call, Throwable t) {
                Log.d("UpdateMsg","Failed");

            }
        });
    }

    public void setContent(NewsResponse news) {
        this.news = news;
        gettimetext();
        this.seen_bies = null;
        this.seen_bies = new ArrayList<Seen_by>();
        this.seen_bies = news.getSeen_by();
        sRead_by = "<b>\u2714 Läst av: </b>";
        if (seen_bies.size() > 0) {
            mSeenBy.setVisibility(View.VISIBLE);
            for (Seen_by seen_by: seen_bies) {
                sRead_by += seen_by.getName() + ", ";
            }
            sRead_by = sRead_by.substring(0, sRead_by.length()-2);
        } else {
            mSeenBy.setVisibility(View.GONE);
        }
        if(NewsFragment.flag_relevant==0) {
            mUsername.setText(news.getAuthor().getName());
            new DownloadImage(mUserImg).execute(news.getAuthor().getAvatar());
            mTime.setText(stringTime);
            mMessage.loadMarkdown(news.getMessage());
            mSeenBy.loadMarkdown(sRead_by);
            mReplycounts.setText("" + news.getReply_count());
        } else{
            if(news.isMe_mentioned()){
                mUsername.setText(news.getAuthor().getName());
                new DownloadImage(mUserImg).execute(news.getAuthor().getAvatar());
                mTime.setText(stringTime);
                mMessage.loadMarkdown(news.getMessage());
                mSeenBy.loadMarkdown(sRead_by);
                mReplycounts.setText("" + news.getReply_count());
            } else{
                newsrow.setVisibility(View.GONE);
            }
        }

        int j = 0;
        this.replies = news.getReplies();

        if(news.getReply_count()!=0) {
            lnList.removeAllViews();
            for (RepliesRes repliesRes : replies) {

                GradientDrawable gd_list = new GradientDrawable();
                gd_list.setShape(GradientDrawable.RECTANGLE);
                gd_list.setStroke(2,Color.LTGRAY);
                gd_list.setColor(Color.argb(255,240,240,240));
                gd_list.setCornerRadius(5.0f);

                LinearLayout lnListRow = new LinearLayout(context);
                lnListRow.setOrientation(LinearLayout.VERTICAL);
                LinearLayout lnUsermain = new LinearLayout(context);
                lnUsermain.setOrientation(LinearLayout.HORIZONTAL);
                LinearLayout lnUsername = new LinearLayout(context);
                lnUsername.setOrientation(LinearLayout.VERTICAL);
                CircleImageView ivUser = new CircleImageView(context);
                new DownloadImage(ivUser).execute(repliesRes.getAuthor().getAvatar());
                lnUsermain.addView(ivUser);
                TextView tvUsername = new TextView(context);
                tvUsername.setText(repliesRes.getAuthor().getName());
                tvUsername.setTextColor(Color.DKGRAY);
                tvUsername.setTextSize(18);
                tvUsername.setTypeface(Typeface.DEFAULT_BOLD);
                tvUsername.setPadding(12,13,4,2);
                TextView tvTime = new TextView(context);
                tvTime.setText(stringTime);
                tvTime.setPadding(12,4,4,4);
                tvTime.setTypeface(null, Typeface.ITALIC);
                lnUsername.addView(tvUsername);
                lnUsername.addView(tvTime);
                lnUsermain.addView(lnUsername);
                TextView tvDesc = new TextView(context);
                tvDesc.setPadding(10,2,2,2);
                tvDesc.setText(repliesRes.getMessage());
                tvDesc.setTextSize(15);
                tvDesc.setTextColor(Color.DKGRAY);
                lnListRow.addView(lnUsermain);
                lnListRow.addView(tvDesc);
                lnListRow.setBackground(gd_list);
                lnListRow.setPadding(10,10,10,10);
                lnList.addView(lnListRow);

            }

        } else {
            lnList.removeAllViews();
        }

        if(news.getReply_count()>0){
            for(int i=0; i<=news.getReply_count();i++){

            }
        }
    }

    public void gettimetext(){
        Date currentTime = Calendar.getInstance().getTime();
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
        String curTime = sdf.format(new Date());
        String curDate = df.format(currentTime);
        int betYear = Integer.parseInt(curDate.substring(0,4))-Integer.parseInt(news.getCreation_date().substring(0,4));
        int betMonth = Integer.parseInt(curDate.substring(5,7))-Integer.parseInt(news.getCreation_date().substring(5,7));
        int betDay = Integer.parseInt(curDate.substring(8,10))-Integer.parseInt(news.getCreation_date().substring(8,10));
        int betHour = Integer.parseInt(curTime.substring(0,2))-Integer.parseInt(news.getCreation_date().substring(11,13));
        int betMin = Integer.parseInt(curTime.substring(3,5))-Integer.parseInt(news.getCreation_date().substring(14,16));
        int betSec = Integer.parseInt(curTime.substring(6,8))-Integer.parseInt(news.getCreation_date().substring(17,19));

        if(betYear >= 1 ){
            stringTime = ""+betYear+" år sedan";
        } else if(betMonth >= 1 ){
            stringTime = ""+betMonth+" månader sedan";
        } else if(betDay >= 1){
            stringTime = ""+betDay+" dagar sedan";
        } else if(betHour >= 1 ){
            stringTime = ""+betHour+" timmar sedan";
        } else if(betMin >= 1){
            stringTime = ""+betMin+" minuter sedan";
        } else if(betSec >= 1){
            stringTime = "Just now";
        }

    }
}
