package co.pichak.interview.bahiraei.countertask;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBarActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import java.util.Map;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import co.pichak.interview.bahiraei.countertask.common.CounterTaskPreference;
import co.pichak.interview.bahiraei.countertask.common.NotificationUtils;
import co.pichak.interview.bahiraei.countertask.common.TreeMapAdapter;


public class MainActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container, new PlaceholderFragment())
                    .commit();
        }

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        return false;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
    return false;
    }

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {

        private int current;
        private NotificationUtils notificationUtil;


        public PlaceholderFragment() {
        }

        @InjectView(R.id.counter_canvas)
        public CounterCanvas counterCanvas;

        @InjectView(R.id.action_btn)
        public Button actionBtn;

        @InjectView(R.id.log_listview)
        public ListView logListView;

        @InjectView(R.id.empty)
        public TextView emptyTv;

        TreeMapAdapter adapter;

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_main, container, false);
            ButterKnife.inject(this, rootView);
            notificationUtil=new NotificationUtils();
            current = CounterTaskPreference.getCurrentCountPref(getActivity()).getCurrent();

            if (current == 0) {
                counterCanvas.setEnabled(false);
            } else {
                notificationUtil.showNotification(getActivity(), MainActivity.class, current);
                counterCanvas.setCurrent(current);
                actionBtn.setText(getString(R.string.stop));
            }

            fillAdapter();



            logListView.setAdapter(adapter);


            return rootView;
        }

        private void fillAdapter() {
            Map logMap = CounterTaskPreference.getLogListPref(getActivity()).getAllLogCount();

            if(logMap.isEmpty()){
                emptyTv.setText(getString(R.string.do_your_best));
            }else{
                emptyTv.setVisibility(View.GONE);
                logListView.setVisibility(View.VISIBLE);
            }
            if (adapter == null) {
                adapter = new TreeMapAdapter(logMap);
            } else {

                adapter.setData(logMap);
            }
        }





        @OnClick(R.id.action_btn)
        public void setActionBtn(Button button) {

            String actionBtnText = button.getText().toString();
            if (actionBtnText.equals(getString(R.string.start))) {
                counterCanvas.setEnabled(true);
                actionBtn.setText(getString(R.string.stop));

            } else {
                actionBtn.setText(getString(R.string.start));
                counterCanvas.setEnabled(false);
                CounterTaskPreference.getLogListPref(getActivity()).setCountToLog(counterCanvas.getCurrent());
                counterCanvas.setCurrent(0);
                CounterTaskPreference.getCurrentCountPref(getActivity()).saveCurrent(0);
                fillAdapter();
            }
            notificationUtil.showNotification(getActivity(),MainActivity.class,   counterCanvas.getCurrent());
        }
    }
}
