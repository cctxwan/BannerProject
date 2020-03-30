// Generated code from Butter Knife. Do not modify!
package com.bangni.yzcm.activity;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import butterknife.Unbinder;
import butterknife.internal.DebouncingOnClickListener;
import butterknife.internal.Utils;
import com.bangni.yzcm.R;
import java.lang.IllegalStateException;
import java.lang.Override;

public class OrderDetailActivity_ViewBinding implements Unbinder {
  private OrderDetailActivity target;

  private View view2131230869;

  private View view2131230944;

  private View view2131230922;

  @UiThread
  public OrderDetailActivity_ViewBinding(OrderDetailActivity target) {
    this(target, target.getWindow().getDecorView());
  }

  @UiThread
  public OrderDetailActivity_ViewBinding(final OrderDetailActivity target, View source) {
    this.target = target;

    View view;
    target.txt_orderdetail_title = Utils.findRequiredViewAsType(source, R.id.txt_orderdetail_title, "field 'txt_orderdetail_title'", TextView.class);
    target.txt_orderdetail_info = Utils.findRequiredViewAsType(source, R.id.txt_orderdetail_info, "field 'txt_orderdetail_info'", TextView.class);
    target.txt_broadcasenumber = Utils.findRequiredViewAsType(source, R.id.txt_broadcasenumber, "field 'txt_broadcasenumber'", TextView.class);
    view = Utils.findRequiredView(source, R.id.img_orderdetail_back, "field 'img_orderdetail_back' and method 'onClick'");
    target.img_orderdetail_back = Utils.castView(view, R.id.img_orderdetail_back, "field 'img_orderdetail_back'", LinearLayout.class);
    view2131230869 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onClick(p0);
      }
    });
    view = Utils.findRequiredView(source, R.id.rel_order_community, "method 'onClick'");
    view2131230944 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onClick(p0);
      }
    });
    view = Utils.findRequiredView(source, R.id.orderdetail_txt_allbroadcast, "method 'onClick'");
    view2131230922 = view;
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
    OrderDetailActivity target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.txt_orderdetail_title = null;
    target.txt_orderdetail_info = null;
    target.txt_broadcasenumber = null;
    target.img_orderdetail_back = null;

    view2131230869.setOnClickListener(null);
    view2131230869 = null;
    view2131230944.setOnClickListener(null);
    view2131230944 = null;
    view2131230922.setOnClickListener(null);
    view2131230922 = null;
  }
}
