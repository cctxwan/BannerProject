// Generated code from Butter Knife. Do not modify!
package com.bangni.yzcm.activity;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import butterknife.Unbinder;
import butterknife.internal.DebouncingOnClickListener;
import butterknife.internal.Utils;
import com.bangni.yzcm.R;
import java.lang.IllegalStateException;
import java.lang.Override;

public class SettingActivity_ViewBinding implements Unbinder {
  private SettingActivity target;

  private View view2131230887;

  private View view2131230888;

  private View view2131230886;

  private View view2131230889;

  private View view2131230890;

  private View view2131230975;

  @UiThread
  public SettingActivity_ViewBinding(SettingActivity target) {
    this(target, target.getWindow().getDecorView());
  }

  @UiThread
  public SettingActivity_ViewBinding(final SettingActivity target, View source) {
    this.target = target;

    View view;
    view = Utils.findRequiredView(source, R.id.rel_change_img, "field 'rel_change_img' and method 'onClick'");
    target.rel_change_img = Utils.castView(view, R.id.rel_change_img, "field 'rel_change_img'", RelativeLayout.class);
    view2131230887 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onClick(p0);
      }
    });
    view = Utils.findRequiredView(source, R.id.rel_change_name, "field 'rel_change_name' and method 'onClick'");
    target.rel_change_name = Utils.castView(view, R.id.rel_change_name, "field 'rel_change_name'", RelativeLayout.class);
    view2131230888 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onClick(p0);
      }
    });
    view = Utils.findRequiredView(source, R.id.rel_change_account, "field 'rel_change_account' and method 'onClick'");
    target.rel_change_account = Utils.castView(view, R.id.rel_change_account, "field 'rel_change_account'", RelativeLayout.class);
    view2131230886 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onClick(p0);
      }
    });
    view = Utils.findRequiredView(source, R.id.rel_change_pass, "field 'rel_change_pass' and method 'onClick'");
    target.rel_change_pass = Utils.castView(view, R.id.rel_change_pass, "field 'rel_change_pass'", RelativeLayout.class);
    view2131230889 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onClick(p0);
      }
    });
    view = Utils.findRequiredView(source, R.id.rel_clear_hc, "field 'rel_clear_hc' and method 'onClick'");
    target.rel_clear_hc = Utils.castView(view, R.id.rel_clear_hc, "field 'rel_clear_hc'", RelativeLayout.class);
    view2131230890 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onClick(p0);
      }
    });
    view = Utils.findRequiredView(source, R.id.txt_loginout, "field 'txt_loginout' and method 'onClick'");
    target.txt_loginout = Utils.castView(view, R.id.txt_loginout, "field 'txt_loginout'", TextView.class);
    view2131230975 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onClick(p0);
      }
    });
    target.img_userimg = Utils.findRequiredViewAsType(source, R.id.img_userimg, "field 'img_userimg'", ImageView.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    SettingActivity target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.rel_change_img = null;
    target.rel_change_name = null;
    target.rel_change_account = null;
    target.rel_change_pass = null;
    target.rel_clear_hc = null;
    target.txt_loginout = null;
    target.img_userimg = null;

    view2131230887.setOnClickListener(null);
    view2131230887 = null;
    view2131230888.setOnClickListener(null);
    view2131230888 = null;
    view2131230886.setOnClickListener(null);
    view2131230886 = null;
    view2131230889.setOnClickListener(null);
    view2131230889 = null;
    view2131230890.setOnClickListener(null);
    view2131230890 = null;
    view2131230975.setOnClickListener(null);
    view2131230975 = null;
  }
}
