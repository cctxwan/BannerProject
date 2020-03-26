// Generated code from Butter Knife. Do not modify!
package com.bangni.yzcm.activity;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import butterknife.Unbinder;
import butterknife.internal.DebouncingOnClickListener;
import butterknife.internal.Utils;
import com.bangni.yzcm.R;
import java.lang.IllegalStateException;
import java.lang.Override;

public class LoginActivity_ViewBinding implements Unbinder {
  private LoginActivity target;

  private View view2131230974;

  private View view2131230971;

  private View view2131230977;

  private View view2131230972;

  @UiThread
  public LoginActivity_ViewBinding(LoginActivity target) {
    this(target, target.getWindow().getDecorView());
  }

  @UiThread
  public LoginActivity_ViewBinding(final LoginActivity target, View source) {
    this.target = target;

    View view;
    target.et_username = Utils.findRequiredViewAsType(source, R.id.et_username, "field 'et_username'", EditText.class);
    target.et_password = Utils.findRequiredViewAsType(source, R.id.et_password, "field 'et_password'", EditText.class);
    view = Utils.findRequiredView(source, R.id.txt_login, "field 'txt_login' and method 'onClick'");
    target.txt_login = Utils.castView(view, R.id.txt_login, "field 'txt_login'", TextView.class);
    view2131230974 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onClick(p0);
      }
    });
    view = Utils.findRequiredView(source, R.id.txt_getcode, "field 'txt_getcode' and method 'onClick'");
    target.txt_getcode = Utils.castView(view, R.id.txt_getcode, "field 'txt_getcode'", TextView.class);
    view2131230971 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onClick(p0);
      }
    });
    view = Utils.findRequiredView(source, R.id.txt_register, "field 'txt_register' and method 'onClick'");
    target.txt_register = Utils.castView(view, R.id.txt_register, "field 'txt_register'", TextView.class);
    view2131230977 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onClick(p0);
      }
    });
    view = Utils.findRequiredView(source, R.id.txt_getcodelogin, "field 'txt_getcodelogin' and method 'onClick'");
    target.txt_getcodelogin = Utils.castView(view, R.id.txt_getcodelogin, "field 'txt_getcodelogin'", TextView.class);
    view2131230972 = view;
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
    LoginActivity target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.et_username = null;
    target.et_password = null;
    target.txt_login = null;
    target.txt_getcode = null;
    target.txt_register = null;
    target.txt_getcodelogin = null;

    view2131230974.setOnClickListener(null);
    view2131230974 = null;
    view2131230971.setOnClickListener(null);
    view2131230971 = null;
    view2131230977.setOnClickListener(null);
    view2131230977 = null;
    view2131230972.setOnClickListener(null);
    view2131230972 = null;
  }
}
