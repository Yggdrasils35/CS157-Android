package info.androidhive.recyclersearch;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class SearchAdapter extends RecyclerView.Adapter<SearchAdapter.ViewHolder> {
    private List<Search> mSearchList;
    private OnItemClickListener myClickItemListener;


    static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView searchText;
        public OnItemClickListener mOnItemClickListener;

        public ViewHolder(View view, OnItemClickListener mListener)
        {
            super(view);
            view.setOnClickListener(this);
            this.mOnItemClickListener = mListener;
            searchText = (TextView) view.findViewById(R.id.search_text);
        }

        @Override
        public void onClick(View v)
        {
            mOnItemClickListener.onItemClick(v, getAdapterPosition());
        }
    }

    public SearchAdapter(List <Search> searchList){
        mSearchList = searchList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType){
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.search_item,parent,false);
        ViewHolder holder = new ViewHolder(view, myClickItemListener);
        return holder;
    }

    public void setOnItemClickListener(OnItemClickListener listener)
    {
        this.myClickItemListener = listener;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position){
        Search search = mSearchList.get(position);
        holder.searchText.setText(search.getText());
    }

    @Override
    public int getItemCount(){
        return mSearchList.size();
    }

    // 自定义回调接口
    public interface OnItemClickListener {
        void onItemClick(View v, int position);

        void onItemLongClick(View v);
    }
}
