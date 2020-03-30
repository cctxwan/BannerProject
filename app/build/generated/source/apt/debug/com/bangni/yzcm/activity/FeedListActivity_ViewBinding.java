// Generated code from Butter Knife. Do not modify!
package com.bangni.yzcm.activity;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import butterknife.Unbinder;
import butterknife.internal.DebouncingOnClickListener;
import butterknife.internal.Utils;
import com.bangni.yzcm.R;
import com.scwang.smart.refresh.layout.api.RefreshLayout;
import java.lang.IllegalStateException;
import java.lang.Override;

public class FeedListActivity_ViewBinding implements Unbinder {
  private FeedListActivity target;

  private View view2131230863;

  @UiThread
  public FeedListActivity_ViewBinding(FeedListActivity target) {
    this(target, target.getWindow().getDecorView());
  }

  @UiThread
  public FeedListActivity_ViewBinding(final FeedListActivity target, View source) {
    this.target = target;

    View view;
    view = Utils.findRequiredView(source, R.id.img_feedlist_back, "field 'img_feedlist_back' and method 'onClick'");
    target.img_feedlist_back = Utils.castView(view, R.id.img_feedlist_back, "field 'img_feedlist_back'", LinearLayout.class);
    view2131230863 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onClick(p0);
      }
    });
    target.txt_feedbooklist_title = Utils.findRequiredViewAsType(source, R.id.txt_feedbooklist_title, "field 'txt_feedbooklist_title'", TextView.class);
    target.rv_feedlist = Utils.findRequiredViewAsType(source, R.id.rv_feedlist, "field 'rv_feedlist'", RecyclerView.class);
    target.feedlist_swipeRefreshLayout = Utils.findRequiredViewAsType(source, R.id.feedlist_swipeRefreshLayout, "field 'feedlist_swipeRefreshLayout'", RefreshLayout.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    FeedListActivity target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.img_feedlist_back = null;
    target.txt_feedbooklist_title = null;
    target.rv_feedlist = null;
    target.feedlist_swipeRefreshLayout = null;

    view2131230863.setOnClickListener(null);
    view2131230863 = null;
  }
}
