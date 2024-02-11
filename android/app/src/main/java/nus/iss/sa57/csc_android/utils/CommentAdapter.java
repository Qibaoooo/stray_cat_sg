package nus.iss.sa57.csc_android.utils;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import androidx.annotation.NonNull;

import java.util.List;

import nus.iss.sa57.csc_android.R;
import nus.iss.sa57.csc_android.model.Comment;

public class CommentAdapter extends ArrayAdapter<Object> {
    private final Context context;
    protected List<Comment> commentList;
    public CommentAdapter(Context context, List<Comment> commentList){
        super(context, R.layout.comments_list);
        this.context = context;
        this.commentList = commentList;
        addAll(new Object[commentList.size()]);
    }

    @Override
    public View getView(int pos, View view, @NonNull ViewGroup parent) {
        return view;
    }
}
