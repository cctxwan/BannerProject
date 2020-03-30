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

public class BroadcastActivity_ViewBinding implements Unbinder {
  private BroadcastActivity target;

  private View view2131230858;

  @UiThread
  public BroadcastActivity_ViewBinding(BroadcastActivity target) {
    this(target, target.getWindow().getDecorView());
  }

  @UiThread
  public BroadcastActivity_ViewBinding(final BroadcastActivity target, View source) {
    this.target = target;

    View view;
    view = Utils.findRequiredView(source, R.id.img_broadcast_back, "field 'img_broadcast_back' and method 'onClick'");
    target.img_broadcast_back = Utils.castView(view, R.id.img_broadcast_back, "field 'img_broadcast_back'", LinearLayout.class);
    view2131230858 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onClick(p0);
      }
    });
    target.txt_broadcase_name = Utils.findRequiredViewAsType(source, R.id.txt_broadcase_name, "field 'txt_broadcase_name'", TextView.class);
    target.txt_broadcase_wz = Utils.findRequiredViewAsType(source, R.id.txt_broadcase_wz, "field 'txt_broadcase_wz'", TextView.class);
    target.txt_broadcase_time = Utils.findRequiredViewAsType(source, R.id.txt_broadcase_time, "field 'txt_broadcase_time'", TextView.class);
    target.txt_broadcase_takephoto = Utils.findRequiredViewAsType(source, R.id.txt_broadcase_takephoto, "field 'txt_broadcase_takephoto'", TextView.class);
    target.txt_broadcase_photosb = Utils.findRequiredViewAsType(source, R.id.txt_broadcase_photosb, "field 'txt_broadcase_photosb'", TextView.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    BroadcastActivity target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.img_broadcast_back = null;
    target.txt_broadcase_name = null;
    target.txt_broadcase_wz = null;
    target.txt_broadcase_time = null;
    target.txt_broadcase_takephoto = null;
    target.txt_broadcase_photosb = null;

    view2131230858.setOnClickListener(null);
    view2131230858 = null;
  }
}
