// Generated code from Butter Knife. Do not modify!
package com.bangni.yzcm.fragment;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.view.View;
import android.widget.RelativeLayout;
import butterknife.Unbinder;
import butterknife.internal.DebouncingOnClickListener;
import butterknife.internal.Utils;
import com.bangni.yzcm.R;
import java.lang.IllegalStateException;
import java.lang.Override;

public class InfoFragment_ViewBinding implements Unbinder {
  private InfoFragment target;

  private View view2131230892;

  private View view2131230891;

  private View view2131230885;

  @UiThread
  public InfoFragment_ViewBinding(final InfoFragment target, View source) {
    this.target = target;

    View view;
    view = Utils.findRequiredView(source, R.id.rel_setting, "field 'rel_setting' and method 'onClick'");
    target.rel_setting = Utils.castView(view, R.id.rel_setting, "field 'rel_setting'", RelativeLayout.class);
    view2131230892 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onClick(p0);
      }
    });
    view = Utils.findRequiredView(source, R.id.rel_feedbook, "field 'rel_feedbook' and method 'onClick'");
    target.rel_feedbook = Utils.castView(view, R.id.rel_feedbook, "field 'rel_feedbook'", RelativeLayout.class);
    view2131230891 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onClick(p0);
      }
    });
    view = Utils.findRequiredView(source, R.id.rel_about, "field 'rel_about' and method 'onClick'");
    target.rel_about = Utils.castView(view, R.id.rel_about, "field 'rel_about'", RelativeLayout.class);
    view2131230885 = view;
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
    InfoFragment target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.rel_setting = null;
    target.rel_feedbook = null;
    target.rel_about = null;

    view2131230892.setOnClickListener(null);
    view2131230892 = null;
    view2131230891.setOnClickListener(null);
    view2131230891 = null;
    view2131230885.setOnClickListener(null);
    view2131230885 = null;
  }
}
