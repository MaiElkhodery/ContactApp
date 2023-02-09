package com.example.recyclerviewproject;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.recyclerviewproject.database.Contact;

import java.util.ArrayList;

public class Recyc_Adapter extends RecyclerView.Adapter<Recyc_Adapter.Holder> {

    private ArrayList<Contact> localDataSet;

    public Recyc_Adapter(ArrayList<Contact> localDataSet){
        this.localDataSet = localDataSet;
    }
    @NonNull
    @Override
    public Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.example_item,parent,false);
        return new Holder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Holder holder, int position) {
        Contact currentItem = localDataSet.get(position);
        holder.name_textView.setText(currentItem.getContact_name());
        holder.number_textView.setText(currentItem.getContact_no());
        holder.imageView.setImageResource(currentItem.getContact_img());
    }

    @Override
    public int getItemCount() {
        return localDataSet.size();
    }

    public static class Holder extends RecyclerView.ViewHolder{
        private TextView name_textView;
        private TextView number_textView;
        private ImageView imageView;
        public Holder(View view){
            super(view);
            name_textView = view.findViewById(R.id.name_textView);
            number_textView = view.findViewById(R.id.number_textView);
            imageView = view.findViewById(R.id.imageView);
        }
        public TextView getTextView(){
            return name_textView;
        }

        public TextView getNumber_textView() {
            return number_textView;
        }

        public ImageView getImageView(){
            return imageView;
        }

        public void setName_textView(TextView name_textView) {
            this.name_textView = name_textView;
        }

        public void setNumber_textView(TextView number_textView) {
            this.number_textView = number_textView;
        }

        public void setImageView(ImageView imageView) {
            this.imageView = imageView;
        }
    }
}
