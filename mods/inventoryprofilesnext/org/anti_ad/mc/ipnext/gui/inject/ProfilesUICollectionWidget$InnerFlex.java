package org.anti_ad.mc.ipnext.gui.inject;

import org.anti_ad.mc.common.gui.layout.Axis;
import org.anti_ad.mc.common.gui.layout.BiFlex;
import org.anti_ad.mc.common.gui.widgets.Widget;
import org.jetbrains.annotations.NotNull;

public final class ProfilesUICollectionWidget$InnerFlex extends Widget {
   @NotNull
   private final BiFlex flex;

   public ProfilesUICollectionWidget$InnerFlex(ProfilesUICollectionWidget var1) {
      this.this$0 = var1;
      this.flex = new BiFlex(this, Axis.HORIZONTAL);
   }

   @NotNull
   public final BiFlex getFlex() {
      return this.flex;
   }
}
