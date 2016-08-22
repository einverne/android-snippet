package info.einverne.exercise100.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import info.einverne.exercise100.R;
import timber.log.Timber;

/**
 * Created by einverne on 16/8/22.
 */
public class NormalRecyclerViewAdapter extends
        RecyclerView.Adapter<NormalRecyclerViewAdapter.NormalTextViewHolder> {
    private final Context context;
    private final LayoutInflater layoutInflater;
    private ArrayList<String> titles;

    public NormalRecyclerViewAdapter(Context context) {
        this.context = context;
        layoutInflater = LayoutInflater.from(context);
//        titles = context.getResources().getStringArray(R.array.titles);
        titles = new ArrayList<>();
        for (int i = 0; i < 40; i++) {
            titles.add("Text " + i);
        }
    }

    @Override
    public NormalTextViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new NormalTextViewHolder(layoutInflater.inflate(R.layout.item_text, parent, false));
    }

    @Override
    public void onBindViewHolder(NormalTextViewHolder holder, int position) {
        holder.textView.setText(titles.get(position));
    }

    @Override
    public int getItemCount() {
        return titles == null ? 0 : titles.size();
    }

    public static class NormalTextViewHolder extends RecyclerView.ViewHolder {
        TextView textView;

        public NormalTextViewHolder(View view) {
            super(view);
            textView = (TextView) view.findViewById(R.id.text_view);
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Timber.d("NormalTextViewHolder" + "onClick position " + getAdapterPosition());
                }
            });
        }
    }
}
