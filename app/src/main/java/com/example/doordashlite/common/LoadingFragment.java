package com.example.doordashlite.common;

import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.example.doordashlite.R;
import com.wang.avi.AVLoadingIndicatorView;

import androidx.fragment.app.Fragment;
import butterknife.BindView;

public class LoadingFragment extends Fragment {
    @BindView(R.id.indicator)
    AVLoadingIndicatorView loadingIndicatorView;

    @BindView(R.id.msg)
    TextView msgView;

    public void showLoading(boolean visible) {
        msgView.setVisibility(View.INVISIBLE);
        if (visible) {
            loadingIndicatorView.show();
            loadingIndicatorView.setVisibility(View.VISIBLE);
        } else {
            loadingIndicatorView.hide();
            loadingIndicatorView.setVisibility(View.INVISIBLE);
        }
    }

    public void showMsg(String msg) {
        showLoading(false);
        if (TextUtils.isEmpty(msg)) {
            msgView.setVisibility(View.INVISIBLE);
            msgView.setText("");
        } else {
            msgView.setVisibility(View.VISIBLE);
            msgView.setText(msg);
        }
    }
}
