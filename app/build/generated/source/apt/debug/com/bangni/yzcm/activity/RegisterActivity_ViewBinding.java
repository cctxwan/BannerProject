// Generated code from Butter Knife. Do not modify!
package com.bangni.yzcm.activity;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import butterknife.Unbinder;
import butterknife.internal.DebouncingOnClickListener;
import butterknife.internal.Utils;
import com.bangni.yzcm.R;
import java.lang.IllegalStateException;
import java.lang.Override;

public class RegisterActivity_ViewBinding implements Unbinder {
  private RegisterActivity target;

  private View view2131231009;

  private View view2131231007;

  private View view2131231013;

  private View view2131231011;

  private View view2131230849;

  @UiThread
  public RegisterActivity_ViewBinding(RegisterActivity target) {
    this(target, target.getWindow().getDecorView());
  }

  @UiThread
  public RegisterActivity_ViewBinding(final RegisterActivity target, View source) {
    this.target = target;

    View view;
    target.et_rg_username = Utils.findRequiredViewAsType(source, R.id.et_rg_username, "field 'et_rg_username'", EditText.class);
    target.et_rg_code = Utils.findRequiredViewAsType(source, R.id.et_rg_code, "field 'et_rg_code'", EditText.class);
    view = Utils.findRequiredView(source, R.id.txt_rg_getcode, "field 'txt_rg_getcode' and method 'onClick'");
    target.txt_rg_getcode = Utils.castView(view, R.id.txt_rg_getcode, "field 'txt_rg_getcode'", TextView.class);
    view2131231009 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onClick(p0);
      }
    });
    target.et_rg_password = Utils.findRequiredViewAsType(source, R.id.et_rg_password, "field 'et_rg_password'", EditText.class);
    view = Utils.findRequiredView(source, R.id.txt_register, "field 'txt_register' and method 'onClick'");
    target.txt_register = Utils.castView(view, R.id.txt_register, "field 'txt_register'", TextView.class);
    view2131231007 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onClick(p0);
      }
    });
    view = Utils.findRequiredView(source, R.id.txt_yhxy, "field 'txt_yhxy' and method 'onClick'");
    target.txt_yhxy = Utils.castView(view, R.id.txt_yhxy, "field 'txt_yhxy'", TextView.class);
    view2131231013 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onClick(p0);
      }
    });
    view = Utils.findRequiredView(source, R.id.txt_tologin, "field 'txt_tologin' and method 'onClick'");
    target.txt_tologin = Utils.castView(view, R.id.txt_tologin, "field 'txt_tologin'", TextView.class);
    view2131231011 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onClick(p0);
      }
    });
    target.txt_register_title = Utils.findRequiredViewAsType(source, R.id.txt_register_title, "field 'txt_register_title'", TextView.class);
    view = Utils.findRequiredView(source, R.id.img_register_lookpsd, "field 'img_register_lookpsd' and method 'onClick'");
    target.img_register_lookpsd = Utils.castView(view, R.id.img_register_lookpsd, "field 'img_register_lookpsd'", ImageView.class);
    view2131230849 = view;
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
    RegisterActivity target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.et_rg_username = null;
    target.et_rg_code = null;
    target.txt_rg_getcode = null;
    target.et_rg_password = null;
    target.txt_register = null;
    target.txt_yhxy = null;
    target.txt_tologin = null;
    target.txt_register_title = null;
    target.img_register_lookpsd = null;

    view2131231009.setOnClickListener(null);
    view2131231009 = null;
    view2131231007.setOnClickListener(null);
    view2131231007 = null;
    view2131231013.setOnClickListener(null);
    view2131231013 = null;
    view2131231011.setOnClickListener(null);
    view2131231011 = null;
    view2131230849.setOnClickListener(null);
    view2131230849 = null;
  }
}
