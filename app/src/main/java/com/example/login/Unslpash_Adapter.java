package com.example.login;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class Unslpash_Adapter extends RecyclerView.Adapter<Unslpash_Adapter.ImagesListAdapter> {

    private List<Photo> photos;

    public Unslpash_Adapter(){

        photos = new ArrayList<>();
    }

    @NonNull
    @Override
    public Unslpash_Adapter.ImagesListAdapter onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.images_view_list, parent, false);
        return new ImagesListAdapter(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Unslpash_Adapter.ImagesListAdapter holder, int position) {
        Photo photo = photos.get(position);

        Picasso.get().load(photo.getUrls().getSmall()).into(holder.imageView);
    }

    @Override
    public int getItemCount() {
        return photos.size();
    }

    public void setPhotos(List<Photo> photos) {
        this.photos = photos;
        notifyDataSetChanged();
        Log.d("coutnt",""+photos.size());
    }

    public void addPhotos(List<Photo> photos){
        int lastCount = getItemCount();
        this.photos.addAll(photos);
        notifyItemRangeInserted(lastCount, photos.size());
    }

    public class ImagesListAdapter extends RecyclerView.ViewHolder implements View.OnClickListener {

        public final ImageView imageView;
        private final Context context;

        public ImagesListAdapter(@NonNull View itemView) {

            super(itemView);
            imageView = itemView.findViewById(R.id.Img_splash);
            context = itemView.getContext();
            imageView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
           int mposition = getLayoutPosition();

           Photo photo = photos.get(mposition);

           Intent intent = new Intent(context,FullScreenImageActivity.class);

           intent.putExtra("photo",photo.getUrls().getRegular());
           context.startActivity(intent);

           //Intent detailIntent = new Intent(context,FullScreenImageActivity.class);
           //detailIntent.putExtra("image_resources",photos.getUrls().getSmall())
        }

    }
}
