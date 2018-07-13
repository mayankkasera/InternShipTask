package com.example.mayank.internshiptask;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.util.ArrayList;

import static com.example.mayank.internshiptask.R.layout.single_dashboard_item_layout;

public class Adapter extends RecyclerView.Adapter<Adapter.FileHolder>{

    Context context;
    ArrayList<String> list;
    Dialog dialog;

    Adapter(Context context, ArrayList<String> list){
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public FileHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater layoutInflater = LayoutInflater.from(viewGroup.getContext());
        View view = layoutInflater.inflate(R.layout.single_dashboard_item_layout,viewGroup,false);
        return new FileHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FileHolder fileHolder, final int i) {
        fileHolder.textView.setText(list.get(i));
        fileHolder.linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context,GraphActivity.class);
                intent.putExtra("name",list.get(i));
                context.startActivity(intent);
            }
        });

        fileHolder.share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String fileName = "Task/"+list.get(i);
                String path = Environment.getExternalStorageDirectory()+"/"+fileName;
                File file1 = new File(path);

                Uri uri = Uri.fromFile(file1);
                Intent sendIntent = new Intent(Intent.ACTION_SEND);
                sendIntent.putExtra(Intent.EXTRA_SUBJECT, "Person Details");
                sendIntent.putExtra(Intent.EXTRA_STREAM, uri);
                sendIntent.setType("text/html");
                context.startActivity(sendIntent);
            }
        });

        fileHolder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String path = Environment.getExternalStorageDirectory()+"/Task/"+list.get(i);
                File file = new File(path);
                file.delete();
                list.remove(i);
                notifyItemRemoved(i);
                notifyItemRangeChanged(i,list.size());
            }
        });


        fileHolder.rename.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                dialog = new Dialog(context);
                dialog.setContentView(R.layout.dialog_layout);
                dialog.show();

                dialog.findViewById(R.id.btn).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        EditText editText =  dialog.findViewById(R.id.editText);


                        String path = Environment.getExternalStorageDirectory()+"/Task/"+list.get(i);

                        File updatedFile = new File(path);
                        Boolean aBoolean=  updatedFile.renameTo(new File(Environment.getExternalStorageDirectory()+"/Task/"+editText.getText().toString()+".csv" ));
                        if(aBoolean){

                            list.remove(i);
                            notifyItemRemoved(i);
                            notifyItemRangeChanged(i,list.size());

                            list.add(editText.getText().toString()+".csv");
                            Toast.makeText(context, "rename completed", Toast.LENGTH_SHORT).show();
                            dialog.dismiss();
                        }

                    }
                });

            }
        });


    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class FileHolder extends RecyclerView.ViewHolder{

        TextView textView;
        LinearLayout linearLayout;
        ImageView share,rename,delete;


        public FileHolder(@NonNull View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.text);
            linearLayout  = itemView.findViewById(R.id.main);
            share = itemView.findViewById(R.id.send);
            rename = itemView.findViewById(R.id.rename);
            delete = itemView.findViewById(R.id.delete);
        }
    }
}
