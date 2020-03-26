// Generated code from Butter Knife. Do not modify!
package com.bangni.yzcm.activity;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.view.View;
import android.widget.ImageView;
import butterknife.Unbinder;
import butterknife.internal.Utils;
import com.bangni.yzcm.R;
import java.lang.IllegalStateException;
import java.lang.Override;

public class GuiActivity_ViewBinding implements Unbinder {
  private GuiActivity target;

  @UiThread
  public GuiActivity_ViewBinding(GuiActivity target) {
    this(target, target.getWindow().getDecorView());
  }

  @UiThread
  public GuiActivity_ViewBinding(GuiActivity target, View source) {
    this.target = target;

    target.img_guidance_close = Utils.findRequiredViewAsType(source, R.id.iv_start, "field 'img_guidance_close'", ImageView.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    GuiActivity target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.img_guidance_close = null;
  }
}
