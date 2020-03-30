// Generated code from Butter Knife. Do not modify!
package com.bangni.yzcm.activity;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import butterknife.Unbinder;
import butterknife.internal.DebouncingOnClickListener;
import butterknife.internal.Utils;
import com.bangni.yzcm.R;
import java.lang.IllegalStateException;
import java.lang.Override;

public class FeedbackActivity_ViewBinding implements Unbinder {
  private FeedbackActivity target;

  private View view2131230995;

  private View view2131230841;

  @UiThread
  public FeedbackActivity_ViewBinding(FeedbackActivity target) {
    this(target, target.getWindow().getDecorView());
  }

  @UiThread
  public FeedbackActivity_ViewBinding(final FeedbackActivity target, View source) {
    this.target = target;

    View view;
    view = Utils.findRequiredView(source, R.id.txt_feedbook_submit, "field 'txt_feedbook_submit' and method 'onClick'");
    target.txt_feedbook_submit = Utils.castView(view, R.id.txt_feedbook_submit, "field 'txt_feedbook_submit'", TextView.class);
    view2131230995 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onClick(p0);
      }
    });
    target.et_feedbook_content = Utils.findRequiredViewAsType(source, R.id.et_feedbook_content, "field 'et_feedbook_content'", EditText.class);
    view = Utils.findRequiredView(source, R.id.img_feedbook_back, "field 'img_feedbook_back' and method 'onClick'");
    target.img_feedbook_back = Utils.castView(view, R.id.img_feedbook_back, "field 'img_feedbook_back'", LinearLayout.class);
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
    FeedbackActivity target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.txt_feedbook_submit = null;
    target.et_feedbook_content = null;
    target.img_feedbook_back = null;

    view2131230995.setOnClickListener(null);
    view2131230995 = null;
    view2131230841.setOnClickListener(null);
    view2131230841 = null;
  }
}
