// Generated code from Butter Knife. Do not modify!
package com.bangni.yzcm.fragment;

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

public class BroadCastFragment_ViewBinding implements Unbinder {
  private BroadCastFragment target;

  private View view2131231035;

  @UiThread
  public BroadCastFragment_ViewBinding(final BroadCastFragment target, View source) {
    this.target = target;

    View view;
    target.rv_broadcast_list = Utils.findRequiredViewAsType(source, R.id.rv_broadcast_list, "field 'rv_broadcast_list'", RecyclerView.class);
    target.broadcast_swipeRefreshLayout = Utils.findRequiredViewAsType(source, R.id.broadcast_swipeRefreshLayout, "field 'broadcast_swipeRefreshLayout'", RefreshLayout.class);
    view = Utils.findRequiredView(source, R.id.txt_ad_list, "method 'onClick'");
    view2131231035 = view;
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
    BroadCastFragment target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.rv_broadcast_list = null;
    target.broadcast_swipeRefreshLayout = null;

    view2131231035.setOnClickListener(null);
    view2131231035 = null;
  }
}
