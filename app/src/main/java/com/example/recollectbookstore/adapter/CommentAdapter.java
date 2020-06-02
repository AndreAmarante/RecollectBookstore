package com.example.recollectbookstore.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.recollectbookstore.R;
import com.example.recollectbookstore.entity.Comment;
import com.example.recollectbookstore.entity.Item;

import java.util.ArrayList;
import java.util.LinkedHashMap;

public class CommentAdapter extends RecyclerView.Adapter<CommentAdapter.ViewHolder> {

    private ArrayList<Comment> mComments;

    public CommentAdapter(ArrayList<Comment> mComments) {

       this.mComments = mComments;
    }

    @NonNull
    @Override
    public CommentAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        return new CommentAdapter.ViewHolder(inflater.inflate(R.layout.item_comments, parent, false));
    }

    public void updateResults(ArrayList<Comment> mComments){
        this.mComments = mComments;
        notifyDataSetChanged();
    }


    public void onChanged(RecyclerView commentsRecycler, ViewGroup mEmptyView) {
        if (getItemCount() == 0) {
            commentsRecycler.setVisibility(View.GONE);
            mEmptyView.setVisibility(View.VISIBLE);
        } else {
            commentsRecycler.setVisibility(View.VISIBLE);
            mEmptyView.setVisibility(View.GONE);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.bind(mComments.get(position));
    }

    @Override
    public int getItemCount() {
        return mComments != null ? mComments.size() : 0;
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        TextView nameView;

        TextView textView;

        public ViewHolder(View itemView) {
            super(itemView);
            nameView = itemView.findViewById(R.id.comment_date);

            textView = itemView.findViewById(R.id.comment_text);
        }

        public void bind(Comment comment) {
            nameView.setText(comment.getTimeStamp().toString());
            textView.setText(comment.getCommentText());

        }
    }

}

