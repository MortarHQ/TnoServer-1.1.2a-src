package org.anti_ad.mc.ipnext.gui.inject;

import kotlin.Unit;
import org.anti_ad.mc.common.gui.layout.BiFlex;
import org.anti_ad.mc.common.gui.layout.WidgetExtensionsKt;
import org.anti_ad.mc.common.gui.widgets.Widget;
import org.anti_ad.mc.common.math2d.Rectangle;
import org.anti_ad.mc.common.vanilla.alias.glue.I18n;
import org.anti_ad.mc.ipnext.event.ProfileSwitchHandler;
import org.anti_ad.mc.ipnext.gui.inject.base.ProfileButtonWidget;
import org.anti_ad.mc.ipnext.inventory.ContainerType;
import org.jetbrains.annotations.NotNull;

public final class ProfilesUICollectionWidget$InitWidgets {
   @NotNull
   private final ProfileButtonWidget nextProfileButton;
   @NotNull
   private final ProfileButtonWidget prevProfileButton;
   @NotNull
   private final ProfilesUICollectionWidget$ActiveProfileButtonWidget profileButton;
   @NotNull
   private final ProfilesUICollectionWidget$InnerFlex flex;

   public ProfilesUICollectionWidget$InitWidgets(ProfilesUICollectionWidget var1) {
      this.this$0 = var1;
      ProfileButtonWidget var6 = new ProfileButtonWidget(ProfilesUICollectionWidget$InitWidgets::nextProfileButton$lambda$0);
      ProfilesUICollectionWidget var2 = this.this$0;
      ProfileButtonWidget var3 = var6;
      ProfilesUICollectionWidget$InitWidgets var5 = this;
      var3.setTx(50);
      var3.setTy(20);
      var3.setParent(var2);
      var2.getSnapableList().add(var3);
      var2.addChild((Widget)var3);
      var3.setVisible(ProfilesUICollectionWidget.access$getTypes$p(var2).contains(ContainerType.PLAYER));
      var3.setTooltipText(I18n.INSTANCE.translate("inventoryprofiles.tooltip.next_profile_button", new Object[0]));
      var5.nextProfileButton = var6;
      ProfileButtonWidget var7 = new ProfileButtonWidget(ProfilesUICollectionWidget$InitWidgets::prevProfileButton$lambda$2);
      var2 = this.this$0;
      var3 = var7;
      var5 = this;
      var3.setTx(60);
      var3.setTy(20);
      var3.setParent(var2);
      var2.getSnapableList().add(var3);
      var2.addChild((Widget)var3);
      var3.setVisible(ProfilesUICollectionWidget.access$getTypes$p(var2).contains(ContainerType.PLAYER));
      var3.setTooltipText(I18n.INSTANCE.translate("inventoryprofiles.tooltip.prev_profile_button", new Object[0]));
      var5.prevProfileButton = var7;
      ProfilesUICollectionWidget$ActiveProfileButtonWidget var8 = new ProfilesUICollectionWidget$ActiveProfileButtonWidget(
         this.this$0, ProfilesUICollectionWidget$InitWidgets::profileButton$lambda$4
      );
      var2 = this.this$0;
      ProfilesUICollectionWidget$ActiveProfileButtonWidget var15 = var8;
      var5 = this;
      var15.setParent(var2);
      var2.getSnapableList().add(var15);
      var2.addChild((Widget)var15);
      String var4 = ProfilesUICollectionWidget.access$getCurrentProfileName(var2);
      var15.setVisible(ProfilesUICollectionWidget.access$getTypes$p(var2).contains(ContainerType.PLAYER));
      var15.setText(var4);
      var15.setHeight(15);
      var15.setTop(1);
      var15.setTooltipText(I18n.INSTANCE.translate("inventoryprofiles.tooltip.apply_profile_button", new Object[0]));
      var5.profileButton = var8;
      ProfilesUICollectionWidget$InnerFlex var9 = new ProfilesUICollectionWidget$InnerFlex(this.this$0);
      var2 = this.this$0;
      ProfilesUICollectionWidget$InnerFlex var16 = var9;
      var5 = this;
      var16.setParent(var2);
      var16.setVisible(ProfilesUICollectionWidget.access$getTypes$p(var2).contains(ContainerType.PLAYER));
      Rectangle var18 = var2.getAbsoluteBounds();
      int var17 = var2.getAbsoluteBounds().getWidth() - 30;
      int var13 = var2.getAbsoluteBounds().getX() + 15;
      var16.setAbsoluteBounds(Rectangle.copy$default(var18, var13, 0, var17, 17, 2, null));
      var5.flex = var9;
      BiFlex.addAndFit$default(this.flex.getFlex(), (Widget)this.profileButton, false, 0, 6, null);
      WidgetExtensionsKt.setBottomLeft((Widget)this.prevProfileButton, 7, 0);
      WidgetExtensionsKt.setBottomRight((Widget)this.nextProfileButton, 7, 0);
   }

   private static final Unit nextProfileButton$lambda$0() {
      ProfileSwitchHandler.INSTANCE.nextProfile(true);
      return Unit.INSTANCE;
   }

   private static final Unit prevProfileButton$lambda$2() {
      ProfileSwitchHandler.INSTANCE.prevProfile(true);
      return Unit.INSTANCE;
   }

   private static final Unit profileButton$lambda$4() {
      ProfileSwitchHandler.INSTANCE.applyCurrent(true);
      return Unit.INSTANCE;
   }
}
