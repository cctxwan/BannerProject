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

  private View view2131230976;

  private View view2131230981;

  private View view2131230979;

  private View view2131230838;

  private View view2131230842;

  private View view2131230841;

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
    view2131230976 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onClick(p0);
      }
    });
    view = Utils.findRequiredView(source, R.id.txt_two_bottom, "field 'txt_two_bottom' and method 'onClick'");
    target.txt_two_bottom = Utils.castView(view, R.id.txt_two_bottom, "field 'txt_two_bottom'", TextView.class);
    view2131230981 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onClick(p0);
      }
    });
    view = Utils.findRequiredView(source, R.id.txt_three_bottom, "field 'txt_three_bottom' and method 'onClick'");
    target.txt_three_bottom = Utils.castView(view, R.id.txt_three_bottom, "field 'txt_three_bottom'", TextView.class);
    view2131230979 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onClick(p0);
      }
    });
    view = Utils.findRequiredView(source, R.id.img_one_bottom, "field 'img_one_bottom' and method 'onClick'");
    target.img_one_bottom = Utils.castView(view, R.id.img_one_bottom, "field 'img_one_bottom'", ImageView.class);
    view2131230838 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onClick(p0);
      }
    });
    view = Utils.findRequiredView(source, R.id.img_two_bottom, "field 'img_two_bottom' and method 'onClick'");
    target.img_two_bottom = Utils.castView(view, R.id.img_two_bottom, "field 'img_two_bottom'", ImageView.class);
    view2131230842 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onClick(p0);
      }
    });
    view = Utils.findRequiredView(source, R.id.img_three_bottom, "field 'img_three_bottom' and method 'onClick'");
    target.img_three_bottom = Utils.castView(view, R.id.img_three_bottom, "field 'img_three_bottom'", ImageView.class);
    view2131230841 = view;
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
    view2131230976.setOnClickListener(null);
    view2131230976 = null;
    view2131230981.setOnClickListener(null);
    view2131230981 = null;
    view2131230979.setOnClickListener(null);
    view2131230979 = null;
    view2131230838.setOnClickListener(null);
    view2131230838 = null;
    view2131230842.setOnClickListener(null);
    view2131230842 = null;
    view2131230841.setOnClickListener(null);
    view2131230841 = null;
  }
}
