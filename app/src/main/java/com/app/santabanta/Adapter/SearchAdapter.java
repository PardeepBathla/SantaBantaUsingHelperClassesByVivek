package com.app.santabanta.Adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.app.santabanta.Modals.SearchResponse;
import com.app.santabanta.R;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SearchAdapter extends RecyclerView.Adapter<SearchAdapter.ViewHolder>{

    private List<SearchResponse.Hits.Hit> mList;
    private Activity mActivity;
    private SearchClickListener mClickListener;

    public SearchAdapter(Activity mActivity, SearchClickListener mClickListener) {
        this.mList = new ArrayList<>();
        this.mActivity = mActivity;
        this.mClickListener = mClickListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_search, parent, false);
        return new SearchAdapter.ViewHolder(v);
    }

    public void setItems(List<SearchResponse.Hits.Hit> mList){
        this.mList = mList;
        notifyDataSetChanged();
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.bindData(mList.get(position));
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{

        @BindView(R.id.tvSearchItem)
        TextView tvText;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }

        public void bindData(SearchResponse.Hits.Hit model){
            tvText.setText(model.getSource().getName());
            itemView.setOnClickListener(view -> mClickListener.onSearchClicked(model.getSource()));
        }
    }

    public interface SearchClickListener{
        void onSearchClicked(SearchResponse.Hits.Hit.Source model);
    }
}
