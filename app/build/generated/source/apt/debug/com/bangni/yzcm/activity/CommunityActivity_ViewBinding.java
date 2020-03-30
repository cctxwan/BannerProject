// Generated code from Butter Knife. Do not modify!
package com.bangni.yzcm.activity;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import butterknife.Unbinder;
import butterknife.internal.DebouncingOnClickListener;
import butterknife.internal.Utils;
import com.bangni.yzcm.R;
import com.scwang.smart.refresh.layout.api.RefreshLayout;
import java.lang.IllegalStateException;
import java.lang.Override;

public class CommunityActivity_ViewBinding implements Unbinder {
  private CommunityActivity target;

  private View view2131230861;

  @UiThread
  public CommunityActivity_ViewBinding(CommunityActivity target) {
    this(target, target.getWindow().getDecorView());
  }

  @UiThread
  public CommunityActivity_ViewBinding(final CommunityActivity target, View source) {
    this.target = target;

    View view;
    target.rv_community_list = Utils.findRequiredViewAsType(source, R.id.rv_community_list, "field 'rv_community_list'", RecyclerView.class);
    target.community_swipeRefreshLayout = Utils.findRequiredViewAsType(source, R.id.community_swipeRefreshLayout, "field 'community_swipeRefreshLayout'", RefreshLayout.class);
    view = Utils.findRequiredView(source, R.id.img_community_back, "method 'onClick'");
    view2131230861 = view;
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
    CommunityActivity target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.rv_community_list = null;
    target.community_swipeRefreshLayout = null;

    view2131230861.setOnClickListener(null);
    view2131230861 = null;
  }
}
