package com.exmaple.review.util;

import android.widget.ImageView;

import androidx.databinding.BindingAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.exmaple.review.models.Photo;
import com.exmaple.review.ui.PhotoListFragment;

import java.util.List;

public class BindingAdapters {
    @BindingAdapter("items")
    public static void recyclerView_items(RecyclerView recyclerView, List<Photo> items) {
        if (items != null &&
                recyclerView.getAdapter() instanceof PhotoListFragment.PhotoAdapter) {
            PhotoListFragment.PhotoAdapter adapter = (PhotoListFragment.PhotoAdapter) recyclerView.getAdapter();

            adapter.setItems(items);
        }
    }

    @BindingAdapter("url")
    public static void imageView_url(ImageView imageView, String url) {
        Glide.with(imageView).load(url).into(imageView);
    }
}
