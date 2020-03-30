// Generated code from Butter Knife. Do not modify!
package com.bangni.yzcm.activity;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import butterknife.Unbinder;
import butterknife.internal.DebouncingOnClickListener;
import butterknife.internal.Utils;
import com.bangni.yzcm.R;
import java.lang.IllegalStateException;
import java.lang.Override;

public class MainActivity_ViewBinding implements Unbinder {
  private MainActivity target;

  private View view2131230731;

  private View view2131230733;

  private View view2131230732;

  private View view2131231004;

  private View view2131231012;

  private View view2131231010;

  private View view2131230847;

  private View view2131230852;

  private View view2131230851;

  @UiThread
  public MainActivity_ViewBinding(MainActivity target) {
    this(target, target.getWindow().getDecorView());
  }

  @UiThread
  public MainActivity_ViewBinding(final MainActivity target, View source) {
    this.target = target;

    View view;
    view = Utils.findRequiredView(source, R.id.Lin_one, "field 'Lin_one' and method 'onClick'");
    target.Lin_one = Utils.castView(view, R.id.Lin_one, "field 'Lin_one'", LinearLayout.class);
    view2131230731 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onClick(p0);
      }
    });
    view = Utils.findRequiredView(source, R.id.Lin_two, "field 'Lin_two' and method 'onClick'");
    target.Lin_two = Utils.castView(view, R.id.Lin_two, "field 'Lin_two'", LinearLayout.class);
    view2131230733 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onClick(p0);
      }
    });
    view = Utils.findRequiredView(source, R.id.Lin_three, "field 'Lin_three' and method 'onClick'");
    target.Lin_three = Utils.castView(view, R.id.Lin_three, "field 'Lin_three'", LinearLayout.class);
    view2131230732 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onClick(p0);
      }
    });
    view = Utils.findRequiredView(source, R.id.txt_one_bottom, "field 'txt_one_bottom' and method 'onClick'");
    target.txt_one_bottom = Utils.castView(view, R.id.txt_one_bottom, "field 'txt_one_bottom'", TextView.class);
    view2131231004 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onClick(p0);
      }
    });
    view = Utils.findRequiredView(source, R.id.txt_two_bottom, "field 'txt_two_bottom' and method 'onClick'");
    target.txt_two_bottom = Utils.castView(view, R.id.txt_two_bottom, "field 'txt_two_bottom'", TextView.class);
    view2131231012 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onClick(p0);
      }
    });
    view = Utils.findRequiredView(source, R.id.txt_three_bottom, "field 'txt_three_bottom' and method 'onClick'");
    target.txt_three_bottom = Utils.castView(view, R.id.txt_three_bottom, "field 'txt_three_bottom'", TextView.class);
    view2131231010 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onClick(p0);
      }
    });
    view = Utils.findRequiredView(source, R.id.img_one_bottom, "field 'img_one_bottom' and method 'onClick'");
    target.img_one_bottom = Utils.castView(view, R.id.img_one_bottom, "field 'img_one_bottom'", ImageView.class);
    view2131230847 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onClick(p0);
      }
    });
    view = Utils.findRequiredView(source, R.id.img_two_bottom, "field 'img_two_bottom' and method 'onClick'");
    target.img_two_bottom = Utils.castView(view, R.id.img_two_bottom, "field 'img_two_bottom'", ImageView.class);
    view2131230852 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onClick(p0);
      }
    });
    view = Utils.findRequiredView(source, R.id.img_three_bottom, "field 'img_three_bottom' and method 'onClick'");
    target.img_three_bottom = Utils.castView(view, R.id.img_three_bottom, "field 'img_three_bottom'", ImageView.class);
    view2131230851 = view;
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
    MainActivity target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.Lin_one = null;
    target.Lin_two = null;
    target.Lin_three = null;
    target.txt_one_bottom = null;
    target.txt_two_bottom = null;
    target.txt_three_bottom = null;
    target.img_one_bottom = null;
    target.img_two_bottom = null;
    target.img_three_bottom = null;

    view2131230731.setOnClickListener(null);
    view2131230731 = null;
    view2131230733.setOnClickListener(null);
    view2131230733 = null;
    view2131230732.setOnClickListener(null);
    view2131230732 = null;
    view2131231004.setOnClickListener(null);
    view2131231004 = null;
    view2131231012.setOnClickListener(null);
    view2131231012 = null;
    view2131231010.setOnClickListener(null);
    view2131231010 = null;
    view2131230847.setOnClickListener(null);
    view2131230847 = null;
    view2131230852.setOnClickListener(null);
    view2131230852 = null;
    view2131230851.setOnClickListener(null);
    view2131230851 = null;
  }
}
