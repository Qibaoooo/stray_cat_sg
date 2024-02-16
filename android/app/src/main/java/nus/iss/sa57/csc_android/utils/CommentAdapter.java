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
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;

import java.io.File;
import java.util.ArrayList;
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
        ImageView avatar = view.findViewById(R.id.avatar);
        //File externalFilesDir = context.getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        //File destFile = new File(externalFilesDir, ("img-" + commentList.get(pos).getId() + "-0"));
        Bitmap avatorBitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.avator);
        avatar.setImageBitmap(BitmapHelper.getCircleBitmap(avatorBitmap));
        TextView nickname = view.findViewById(R.id.nickname);
        nickname.setText(commentList.get(pos).getScsUser().getUsername());

        LinearLayout labelsView = view.findViewById(R.id.labels);
        List<TextView> labelViews = new ArrayList<>();
        TextView label1 = view.findViewById(R.id.labels_1);
        TextView label2 = view.findViewById(R.id.labels_2);
        TextView label3 = view.findViewById(R.id.labels_3);
        TextView label4 = view.findViewById(R.id.labels_4);
        TextView label5 = view.findViewById(R.id.labels_5);
        labelViews.add(label1);
        labelViews.add(label2);
        labelViews.add(label3);
        labelViews.add(label4);
        labelViews.add(label5);
        if(commentList.get(pos).getNewlabels() == null){
            labelsView.setVisibility(View.GONE);
        } else {
            for(TextView tv : labelViews){
                tv.setVisibility(View.GONE);
            }
            for (int i = 0; i < commentList.get(pos).getNewlabels().size(); i++) {
                labelViews.get(i).setVisibility(View.VISIBLE);
                labelViews.get(i).setText(commentList.get(pos).getNewlabels().get(i));
            }
        }
        TextView publictime = view.findViewById(R.id.publictime);
        publictime.setText(commentList.get(pos).getTime().substring(0,10));
        TextView content=view.findViewById(R.id.content);
        content.setText(commentList.get(pos).getContent());
        return view;
    }

}
