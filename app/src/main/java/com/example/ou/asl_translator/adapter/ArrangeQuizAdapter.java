package com.example.ou.asl_translator.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;

import com.example.ou.asl_translator.ArrangeQuizActivity;
import com.example.ou.asl_translator.R;

import java.util.List;

public class ArrangeQuizAdapter extends BaseAdapter implements View.OnClickListener {
    private LayoutInflater layoutinflater;
    private List listStorage;
    private Context context;

    public ArrangeQuizAdapter(Context context, List customizedListView) {
        layoutinflater =(LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        listStorage = customizedListView;
        this.context = context;
    }

    @Override
    public int getCount() {
        return listStorage.size();
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final ViewHolder listViewHolder;
        if(convertView == null){
            listViewHolder = new ViewHolder();
            convertView = layoutinflater.inflate(R.layout.grid_view, parent, false);
            //listViewHolder.textInListView = (TextView)convertView.findViewById(R.id.textView);
            listViewHolder.button = convertView.findViewById(R.id.start);
            convertView.setTag(listViewHolder);
        }else{
            listViewHolder = (ViewHolder)convertView.getTag();
        }


        final String text = listStorage.get(position).toString();
        listViewHolder.button.setText(text);
        listViewHolder.button.setOnClickListener(this);
        //int imageResourceId = this.context.getResources().getIdentifier(listStorage.get(position).getImageResource(), "drawable", this.context.getPackageName());
        // listViewHolder.imageInListView.setImageResource(imageResourceId);

        return convertView;
    }

    @Override
    public void onClick(View v) {
        Button clickedOption=(Button) v;
        String text = clickedOption.getText().toString();
        ((ArrangeQuizActivity)context).addWord(text);
        clickedOption.setVisibility(View.GONE);
    }

    static class ViewHolder{
        Button button;
    }
}
