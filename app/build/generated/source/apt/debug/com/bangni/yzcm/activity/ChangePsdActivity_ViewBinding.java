// Generated code from Butter Knife. Do not modify!
package com.bangni.yzcm.activity;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import butterknife.Unbinder;
import butterknife.internal.DebouncingOnClickListener;
import butterknife.internal.Utils;
import com.bangni.yzcm.R;
import java.lang.IllegalStateException;
import java.lang.Override;

public class ChangePsdActivity_ViewBinding implements Unbinder {
  private ChangePsdActivity target;

  private View view2131230840;

  private View view2131230993;

  private View view2131230846;

  private View view2131230844;

  private View view2131230845;

  @UiThread
  public ChangePsdActivity_ViewBinding(ChangePsdActivity target) {
    this(target, target.getWindow().getDecorView());
  }

  @UiThread
  public ChangePsdActivity_ViewBinding(final ChangePsdActivity target, View source) {
    this.target = target;

    View view;
    view = Utils.findRequiredView(source, R.id.img_changepsd_back, "field 'img_changepsd_back' and method 'onClick'");
    target.img_changepsd_back = Utils.castView(view, R.id.img_changepsd_back, "field 'img_changepsd_back'", LinearLayout.class);
    view2131230840 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onClick(p0);
      }
    });
    target.et_oldpsd = Utils.findRequiredViewAsType(source, R.id.et_oldpsd, "field 'et_oldpsd'", EditText.class);
    target.et_newpsd = Utils.findRequiredViewAsType(source, R.id.et_newpsd, "field 'et_newpsd'", EditText.class);
    target.et_newpsd_r = Utils.findRequiredViewAsType(source, R.id.et_newpsd_r, "field 'et_newpsd_r'", EditText.class);
    view = Utils.findRequiredView(source, R.id.txt_changepsd_comple, "field 'txt_changepsd_comple' and method 'onClick'");
    target.txt_changepsd_comple = Utils.castView(view, R.id.txt_changepsd_comple, "field 'txt_changepsd_comple'", TextView.class);
    view2131230993 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onClick(p0);
      }
    });
    view = Utils.findRequiredView(source, R.id.img_old_lookpsd, "field 'img_old_lookpsd' and method 'onClick'");
    target.img_old_lookpsd = Utils.castView(view, R.id.img_old_lookpsd, "field 'img_old_lookpsd'", ImageView.class);
    view2131230846 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onClick(p0);
      }
    });
    view = Utils.findRequiredView(source, R.id.img_new_lookpsd, "field 'img_new_lookpsd' and method 'onClick'");
    target.img_new_lookpsd = Utils.castView(view, R.id.img_new_lookpsd, "field 'img_new_lookpsd'", ImageView.class);
    view2131230844 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onClick(p0);
      }
    });
    view = Utils.findRequiredView(source, R.id.img_new_r_lookpsd, "field 'img_new_r_lookpsd' and method 'onClick'");
    target.img_new_r_lookpsd = Utils.castView(view, R.id.img_new_r_lookpsd, "field 'img_new_r_lookpsd'", ImageView.class);
    view2131230845 = view;
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
    ChangePsdActivity target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.img_changepsd_back = null;
    target.et_oldpsd = null;
    target.et_newpsd = null;
    target.et_newpsd_r = null;
    target.txt_changepsd_comple = null;
    target.img_old_lookpsd = null;
    target.img_new_lookpsd = null;
    target.img_new_r_lookpsd = null;

    view2131230840.setOnClickListener(null);
    view2131230840 = null;
    view2131230993.setOnClickListener(null);
    view2131230993 = null;
    view2131230846.setOnClickListener(null);
    view2131230846 = null;
    view2131230844.setOnClickListener(null);
    view2131230844 = null;
    view2131230845.setOnClickListener(null);
    view2131230845 = null;
  }
}
