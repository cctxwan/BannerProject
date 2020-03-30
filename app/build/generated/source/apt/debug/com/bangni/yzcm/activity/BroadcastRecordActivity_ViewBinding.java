// Generated code from Butter Knife. Do not modify!
package com.bangni.yzcm.activity;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import butterknife.Unbinder;
import butterknife.internal.DebouncingOnClickListener;
import butterknife.internal.Utils;
import com.bangni.yzcm.R;
import com.scwang.smart.refresh.layout.api.RefreshLayout;
import java.lang.IllegalStateException;
import java.lang.Override;

public class BroadcastRecordActivity_ViewBinding implements Unbinder {
  private BroadcastRecordActivity target;

  private View view2131230870;

  @UiThread
  public BroadcastRecordActivity_ViewBinding(BroadcastRecordActivity target) {
    this(target, target.getWindow().getDecorView());
  }

  @UiThread
  public BroadcastRecordActivity_ViewBinding(final BroadcastRecordActivity target, View source) {
    this.target = target;

    View view;
    target.rv_record_list = Utils.findRequiredViewAsType(source, R.id.rv_record_list, "field 'rv_record_list'", RecyclerView.class);
    target.record_swipeRefreshLayout = Utils.findRequiredViewAsType(source, R.id.record_swipeRefreshLayout, "field 'record_swipeRefreshLayout'", RefreshLayout.class);
    view = Utils.findRequiredView(source, R.id.img_record_back, "method 'onClick'");
    view2131230870 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onClick(p0);
      }
    });
  }

  @Override
  @CallSuper
  public void unbind() {
    BroadcastRecordActivity target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.rv_record_list = null;
    target.record_swipeRefreshLayout = null;

    view2131230870.setOnClickListener(null);
    view2131230870 = null;
  }
}
