package com.example.brandonderbidge.familymapserver.Activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;

import com.example.brandonderbidge.familymapserver.Model;
import com.example.brandonderbidge.familymapserver.R;

import java.util.LinkedList;
import java.util.List;

public class FilterActivity extends AppCompatActivity {



    RecyclerView eventList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filter);


        List<String > eventList1 = new LinkedList<>();

        for( String type :Model.getEventTypes())
            eventList1.add(type);

        eventList1.add("Father's Side");
        eventList1.add("Mother's Side");
        eventList1.add("By Male");
        eventList1.add("By Female");

        eventList = (RecyclerView) findViewById(R.id.recycler_filter);
        eventList.setLayoutManager(new LinearLayoutManager(getApplicationContext()));

        FilterActivity.FilterAdapt eventAdapt = new FilterActivity.FilterAdapt(eventList1);
        eventList.setAdapter(eventAdapt);


    }


    private class FilterAdapt extends RecyclerView.Adapter<FilterActivity.filterHolder>
    {
        private List<String> mfilter;

        public FilterAdapt(List<String> events) {

            mfilter = events;
        }

        @Override
        public FilterActivity.filterHolder onCreateViewHolder(ViewGroup parent, int viewType) {

            View itemView = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.filter_layout_item, parent, false);
            return new FilterActivity.filterHolder(itemView);
        }

        @Override
        public void onBindViewHolder(FilterActivity.filterHolder holder, int position) {


            holder.bind(mfilter.get(position));
        }

        @Override
        public int getItemCount() {
            return mfilter.size();
        }


    }

    private class filterHolder extends RecyclerView.ViewHolder{

        private TextView filterText;
        private Switch filter;
        private String eventTypeFocus;

        public filterHolder(View itemView) {
            super(itemView);

            filterText = (TextView) itemView.findViewById(R.id.filtertext);
            filter = (Switch) itemView.findViewById(R.id.filter_switch);

            filter.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener(){

                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                    setFilter(isChecked);

                }
            });

        }


        public void bind(String e){

            eventTypeFocus = e;
            filterText.setText("Filter By " + e + " Events");
            filter.setChecked(Model.getEventTypeMap().get(e));

        }

        private void setFilter( boolean isChecked){

            if(Model.getEventTypeMap().containsKey(eventTypeFocus)){

                Model.getEventTypeMap().put(eventTypeFocus, isChecked);

            }

        }
    }

}
