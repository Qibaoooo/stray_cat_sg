package nus.iss.sa57.csc_android.utils;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;

import java.io.File;
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
        if (view == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.comments_list, parent, false);
        }
        //ImageView avatar = view.findViewById(R.id.avatar);
        //File externalFilesDir = context.getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        //File destFile = new File(externalFilesDir, ("img-" + commentList.get(pos).getId() + "-0"));
        //Bitmap bitmap = BitmapFactory.decodeFile(destFile.getAbsolutePath());
        //avatar.setImageBitmap(bitmap);
        TextView nickname = view.findViewById(R.id.nickname);
        nickname.setText(commentList.get(pos).getScsUser().getUsername());
        TextView labels = view.findViewById(R.id.labels);
        //labels.setText(commentList.get(pos).getNewlabels().get(0));
        TextView publictime = view.findViewById(R.id.publictime);
        publictime.setText(commentList.get(pos).getTime().substring(0,10));
        TextView content=view.findViewById(R.id.content);
        content.setText(commentList.get(pos).getContent());
        return view;
    }

}
