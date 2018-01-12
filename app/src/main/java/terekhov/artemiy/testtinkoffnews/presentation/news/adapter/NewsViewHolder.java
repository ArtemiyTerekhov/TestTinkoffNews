package terekhov.artemiy.testtinkoffnews.presentation.news.adapter;

import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.text.format.DateFormat;
import android.view.View;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import terekhov.artemiy.testtinkoffnews.R;
import terekhov.artemiy.testtinkoffnews.domain.model.News;

/**
 * Created by Artemiy Terekhov on 11.01.2018.
 * Copyright (c) 2018 Artemiy Terekhov. All rights reserved.
 */

public class NewsViewHolder extends RecyclerView.ViewHolder {
    @BindView(R.id.titleOfNews)
    TextView mTitleTextView;

    @BindView(R.id.dateOfNews)
    TextView mDateTextView;

    public NewsViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }

    public void bind(News news, final View.OnClickListener onItemClickListener) {
        itemView.setOnClickListener(onItemClickListener);
        mTitleTextView.setText(Html.fromHtml(news.getText()));
        mDateTextView.setText(DateFormat.format("dd.MM.yyyy HH:mm:ss", news.getDate()));
    }
}
