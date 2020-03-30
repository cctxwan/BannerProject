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

public class ChangeAccountActivity_ViewBinding implements Unbinder {
  private ChangeAccountActivity target;

  private View view2131230859;

  @UiThread
  public ChangeAccountActivity_ViewBinding(ChangeAccountActivity target) {
    this(target, target.getWindow().getDecorView());
  }

  @UiThread
  public ChangeAccountActivity_ViewBinding(final ChangeAccountActivity target, View source) {
    this.target = target;

    View view;
    target.et_changeaccount_phone = Utils.findRequiredViewAsType(source, R.id.et_changeaccount_phone, "field 'et_changeaccount_phone'", EditText.class);
    target.et_changeaccount_code = Utils.findRequiredViewAsType(source, R.id.et_changeaccount_code, "field 'et_changeaccount_code'", EditText.class);
    target.txt_changeaccount_getcode = Utils.findRequiredViewAsType(source, R.id.txt_changeaccount_getcode, "field 'txt_changeaccount_getcode'", TextView.class);
    target.txt_changeaccount_comple = Utils.findRequiredViewAsType(source, R.id.txt_changeaccount_comple, "field 'txt_changeaccount_comple'", TextView.class);
    view = Utils.findRequiredView(source, R.id.img_changeaccount_back, "method 'onClick'");
    view2131230859 = view;
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
    ChangeAccountActivity target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.et_changeaccount_phone = null;
    target.et_changeaccount_code = null;
    target.txt_changeaccount_getcode = null;
    target.txt_changeaccount_comple = null;

    view2131230859.setOnClickListener(null);
    view2131230859 = null;
  }
}
