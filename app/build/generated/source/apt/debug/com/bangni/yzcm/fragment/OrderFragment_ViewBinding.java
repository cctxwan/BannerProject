// Generated code from Butter Knife. Do not modify!
package com.bangni.yzcm.fragment;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import butterknife.Unbinder;
import butterknife.internal.Utils;
import com.bangni.yzcm.R;
import java.lang.IllegalStateException;
import java.lang.Override;

public class OrderFragment_ViewBinding implements Unbinder {
  private OrderFragment target;

  @UiThread
  public OrderFragment_ViewBinding(OrderFragment target, View source) {
    this.target = target;

    target.rv_order_list = Utils.findRequiredViewAsType(source, R.id.rv_order_list, "field 'rv_order_list'", RecyclerView.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    OrderFragment target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.rv_order_list = null;
  }
}
