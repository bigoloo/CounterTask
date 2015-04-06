package co.pichak.interview.bahiraei.countertask.common;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Map;

import butterknife.ButterKnife;
import butterknife.InjectView;
import co.pichak.interview.bahiraei.countertask.R;

/**
 * Created by bigoloo on 4/6/15.
 */
public class TreeMapAdapter extends BaseAdapter{
    private ArrayList mData;

    public TreeMapAdapter(Map<String, Integer> map) {
        mData = new ArrayList();
        mData.addAll(map.entrySet());
    }

    @Override
    public int getCount() {
        return mData.size();
    }

    public void setData(Map<String, Integer> map){

        mData = new ArrayList();
        mData.addAll(map.entrySet());
        notifyDataSetChanged();
    }
    @Override
    public Map.Entry<String, Integer> getItem(int position) {
        return (Map.Entry) mData.get(position);
    }

    @Override
    public long getItemId(int position) {
        // TODO implement you own logic with ID
        return 0;
    }

    class ViewHolder {
        @InjectView(R.id.date_time)
        TextView dateTime;
        @InjectView(R.id.count)
        TextView count;

        public ViewHolder(View view) {
            ButterKnife.inject(this, view);
        }
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder holder;

        if (convertView == null) {
            convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.hashmap_adapter_item, parent, false);
            holder=new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {

            holder = (ViewHolder) convertView.getTag();


        }

        Map.Entry<String, Integer> item = getItem(position);

           holder.dateTime.setText(item.getKey());
           holder.count.setText(item.getValue().toString());


        return convertView;
    }
}

