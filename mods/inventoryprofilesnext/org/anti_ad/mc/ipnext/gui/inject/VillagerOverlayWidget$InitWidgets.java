package org.anti_ad.mc.ipnext.gui.inject;

import java.util.Iterator;
import java.util.List;
import kotlin.Unit;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.SourceDebugExtension;
import net.minecraft.client.gui.components.AbstractButton;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.gui.screens.inventory.MerchantScreen.TradeOfferButton;
import net.minecraft.world.entity.npc.AbstractVillager;
import net.minecraft.world.entity.npc.Villager;
import net.minecraft.world.inventory.AbstractContainerMenu;
import org.anti_ad.mc.alias.client.gui.screen.ingame.InGameExKt;
import org.anti_ad.mc.alias.client.gui.widget.WidgetExKt;
import org.anti_ad.mc.common.gui.layout.WidgetExtensionsKt;
import org.anti_ad.mc.common.gui.widgets.Widget;
import org.anti_ad.mc.common.math2d.Size;
import org.anti_ad.mc.common.vanilla.alias.glue.I18n;
import org.anti_ad.mc.ipn.api.IPNButton;
import org.anti_ad.mc.ipnext.config.ModSettings;
import org.anti_ad.mc.ipnext.event.villagers.VillagerTradeManager;
import org.anti_ad.mc.ipnext.gui.inject.base.VillagerBookmarkButtonWidget;
import org.anti_ad.mc.ipnext.integration.HintClassData;
import org.anti_ad.mc.ipnext.integration.HintsManagerNG;
import org.jetbrains.annotations.NotNull;

@SourceDebugExtension(
   "SMAP\nVillagerOverlayWidget.kt\nKotlin\n*S Kotlin\n*F\n+ 1 VillagerOverlayWidget.kt\norg/anti_ad/mc/ipnext/gui/inject/VillagerOverlayWidget$InitWidgets\n+ 2 _Collections.kt\nkotlin/collections/CollectionsKt___CollectionsKt\n+ 3 _Arrays.kt\nkotlin/collections/ArraysKt___ArraysKt\n+ 4 VanillaAccessors.kt\norg/anti_ad/mc/ipnext/ingame/VanillaAccessorsKt\n*L\n1#1,474:1\n1869#2,2:475\n1869#2,2:480\n1869#2,2:482\n1869#2,2:484\n1310#3,2:477\n124#4:479\n*S KotlinDebug\n*F\n+ 1 VillagerOverlayWidget.kt\norg/anti_ad/mc/ipnext/gui/inject/VillagerOverlayWidget$InitWidgets\n*L\n407#1:475,2\n429#1:480,2\n445#1:482,2\n461#1:484,2\n418#1:477,2\n419#1:479\n*E\n"
)
public final class VillagerOverlayWidget$InitWidgets {
   @NotNull
   private final HintClassData hints;
   private final int bookmarksTop;
   @NotNull
   private final VillagerBookmarkButtonWidget doGlobalButton;
   @NotNull
   private final VillagerBookmarkButtonWidget doGlobalButton1;
   @NotNull
   private final VillagerBookmarkButtonWidget doGlobalButton2;
   @NotNull
   private final VillagerBookmarkButtonWidget doLocalButton;
   @NotNull
   private final VillagerBookmarkButtonWidget doLocalButton1;
   @NotNull
   private final VillagerBookmarkButtonWidget doLocalButton2;
   @NotNull
   private final VillagerBookmarkButtonWidget localBookmark;
   @NotNull
   private final VillagerBookmarkButtonWidget localBookmark1;
   @NotNull
   private final VillagerBookmarkButtonWidget localBookmark2;
   @NotNull
   private final VillagerBookmarkButtonWidget globalBookmark;
   @NotNull
   private final VillagerBookmarkButtonWidget globalBookmark1;
   @NotNull
   private final VillagerBookmarkButtonWidget globalBookmark2;
   @NotNull
   private final List tradeRehintList;
   @NotNull
   private final List bookmarkRehintList;
   @NotNull
   private final List bookmarkRehintList1;
   @NotNull
   private final List bookmarkRehintList2;
   private int selectedTrade;
   private long lastSelectChange;

   public VillagerOverlayWidget$InitWidgets(VillagerOverlayWidget var1) {
      this.this$0 = var1;
      this.hints = HintsManagerNG.INSTANCE.getHints(this.this$0.getScreen().getClass());
      VillagerOverlayWidget$InitWidgets var10000 = this;
      AbstractVillager var10001 = VillagerTradeManager.INSTANCE.getCurrentVillager();
      int var72;
      if (var10001 != null) {
         AbstractVillager var7 = var10001;
         VillagerOverlayWidget$InitWidgets var6 = this;
         int var8 = var7 instanceof Villager ? 17 : 23;
         var10000 = var6;
         var72 = var8;
      } else {
         var72 = 17;
      }

      var10000.bookmarksTop = var72;
      VillagerBookmarkButtonWidget var9 = new VillagerBookmarkButtonWidget(
         VillagerOverlayWidget$InitWidgets::doGlobalButton$lambda$1, VillagerOverlayWidget$InitWidgets::doGlobalButton$lambda$2
      );
      VillagerOverlayWidget var2 = this.this$0;
      VillagerBookmarkButtonWidget var3 = var9;
      VillagerOverlayWidget$InitWidgets var54 = this;
      var2.getSnapableList().add(var3);
      var3.setHints(this.hints.hintFor(IPNButton.VILLAGER_DO_GLOBAL_TRADES));
      var3.setSize(new Size(30, 10));
      var3.setTx(150);
      var3.setTy(0);
      var3.setCtx(150);
      var3.setCty(10);
      var3.setChecked(VillagerOverlayWidget$InitWidgets::doGlobalButton$lambda$4$lambda$3);
      var2.addChild((Widget)var3);
      var3.setVisible(true);
      var3.setTooltipText(I18n.INSTANCE.translate("inventoryprofiles.tooltip.do_global_trades_button", new Object[0]));
      var3.setId("do_global_trades_button");
      var2.getHintableList().add(var3);
      var54.doGlobalButton = var9;
      VillagerBookmarkButtonWidget var10 = new VillagerBookmarkButtonWidget(
         VillagerOverlayWidget$InitWidgets::doGlobalButton1$lambda$5, VillagerOverlayWidget$InitWidgets::doGlobalButton1$lambda$6
      );
      var2 = this.this$0;
      var3 = var10;
      var54 = this;
      var2.getSnapableList().add(var3);
      var3.setHints(this.hints.hintFor(IPNButton.VILLAGER_DO_GLOBAL_TRADES1));
      var3.setSize(new Size(30, 10));
      var3.setTx(150);
      var3.setTy(0);
      var3.setCtx(150);
      var3.setCty(10);
      var3.setChecked(VillagerOverlayWidget$InitWidgets::doGlobalButton1$lambda$9$lambda$7);
      var2.addChild((Widget)var3);
      var3.setVisible(true);
      var3.setTooltipText(I18n.INSTANCE.translate("inventoryprofiles.tooltip.do_global_trades_button1", new Object[0]));
      var3.setId("do_global_trades_button");
      var2.getHintableList().add(var3);
      var3.setVisibleOverride(VillagerOverlayWidget$InitWidgets::doGlobalButton1$lambda$9$lambda$8);
      var54.doGlobalButton1 = var10;
      VillagerBookmarkButtonWidget var11 = new VillagerBookmarkButtonWidget(
         VillagerOverlayWidget$InitWidgets::doGlobalButton2$lambda$10, VillagerOverlayWidget$InitWidgets::doGlobalButton2$lambda$11
      );
      var2 = this.this$0;
      var3 = var11;
      var54 = this;
      var2.getSnapableList().add(var3);
      var3.setHints(this.hints.hintFor(IPNButton.VILLAGER_DO_GLOBAL_TRADES2));
      var3.setSize(new Size(30, 10));
      var3.setTx(150);
      var3.setTy(0);
      var3.setCtx(150);
      var3.setCty(10);
      var3.setChecked(VillagerOverlayWidget$InitWidgets::doGlobalButton2$lambda$14$lambda$12);
      var2.addChild((Widget)var3);
      var3.setVisible(true);
      var3.setTooltipText(I18n.INSTANCE.translate("inventoryprofiles.tooltip.do_global_trades_button2", new Object[0]));
      var3.setId("do_global_trades_button");
      var2.getHintableList().add(var3);
      var3.setVisibleOverride(VillagerOverlayWidget$InitWidgets::doGlobalButton2$lambda$14$lambda$13);
      var54.doGlobalButton2 = var11;
      VillagerBookmarkButtonWidget var12 = new VillagerBookmarkButtonWidget(
         VillagerOverlayWidget$InitWidgets::doLocalButton$lambda$15, VillagerOverlayWidget$InitWidgets::doLocalButton$lambda$16
      );
      var2 = this.this$0;
      var3 = var12;
      var54 = this;
      var2.getSnapableList().add(var3);
      var3.setHints(this.hints.hintFor(IPNButton.VILLAGER_DO_LOCAL_TRADES));
      var3.setSize(new Size(30, 10));
      var3.setTx(150);
      var3.setTy(0);
      var3.setCtx(150);
      var3.setCty(10);
      var3.setChecked(VillagerOverlayWidget$InitWidgets::doLocalButton$lambda$19$lambda$17);
      var2.addChild((Widget)var3);
      VillagerBookmarkButtonWidget var66 = var3;
      var10001 = VillagerTradeManager.INSTANCE.getCurrentVillager();
      boolean var75;
      if (var10001 != null) {
         AbstractVillager var4 = var10001;
         VillagerBookmarkButtonWidget var5 = var3;
         Boolean var74 = var4 instanceof Villager;
         var66 = var5;
         var75 = var74;
      } else {
         var75 = false;
      }

      var66.setVisible(var75);
      var3.setTooltipText(I18n.INSTANCE.translate("inventoryprofiles.tooltip.do_local_trades_button", new Object[0]));
      var3.setId("do_local_trades_button");
      var2.getHintableList().add(var3);
      var54.doLocalButton = var12;
      VillagerBookmarkButtonWidget var13 = new VillagerBookmarkButtonWidget(
         VillagerOverlayWidget$InitWidgets::doLocalButton1$lambda$20, VillagerOverlayWidget$InitWidgets::doLocalButton1$lambda$21
      );
      var2 = this.this$0;
      var3 = var13;
      var54 = this;
      var2.getSnapableList().add(var3);
      var3.setHints(this.hints.hintFor(IPNButton.VILLAGER_DO_LOCAL_TRADES));
      var3.setSize(new Size(30, 10));
      var3.setTx(150);
      var3.setTy(0);
      var3.setCtx(150);
      var3.setCty(10);
      var3.setChecked(VillagerOverlayWidget$InitWidgets::doLocalButton1$lambda$25$lambda$22);
      var2.addChild((Widget)var3);
      VillagerBookmarkButtonWidget var67 = var3;
      var10001 = VillagerTradeManager.INSTANCE.getCurrentVillager();
      boolean var78;
      if (var10001 != null) {
         AbstractVillager var44 = var10001;
         VillagerBookmarkButtonWidget var49 = var3;
         Boolean var77 = var44 instanceof Villager;
         var67 = var49;
         var78 = var77;
      } else {
         var78 = false;
      }

      var67.setVisible(var78);
      var3.setTooltipText(I18n.INSTANCE.translate("inventoryprofiles.tooltip.do_local_trades_button1", new Object[0]));
      var3.setId("do_local_trades_button");
      var2.getHintableList().add(var3);
      var3.setVisibleOverride(VillagerOverlayWidget$InitWidgets::doLocalButton1$lambda$25$lambda$24);
      var54.doLocalButton1 = var13;
      VillagerBookmarkButtonWidget var14 = new VillagerBookmarkButtonWidget(
         VillagerOverlayWidget$InitWidgets::doLocalButton2$lambda$26, VillagerOverlayWidget$InitWidgets::doLocalButton2$lambda$27
      );
      var2 = this.this$0;
      var3 = var14;
      var54 = this;
      var2.getSnapableList().add(var3);
      var3.setHints(this.hints.hintFor(IPNButton.VILLAGER_DO_LOCAL_TRADES));
      var3.setSize(new Size(30, 10));
      var3.setTx(150);
      var3.setTy(0);
      var3.setCtx(150);
      var3.setCty(10);
      var3.setChecked(VillagerOverlayWidget$InitWidgets::doLocalButton2$lambda$31$lambda$28);
      var2.addChild((Widget)var3);
      VillagerBookmarkButtonWidget var68 = var3;
      var10001 = VillagerTradeManager.INSTANCE.getCurrentVillager();
      boolean var81;
      if (var10001 != null) {
         AbstractVillager var45 = var10001;
         VillagerBookmarkButtonWidget var50 = var3;
         Boolean var80 = var45 instanceof Villager;
         var68 = var50;
         var81 = var80;
      } else {
         var81 = false;
      }

      var68.setVisible(var81);
      var3.setTooltipText(I18n.INSTANCE.translate("inventoryprofiles.tooltip.do_local_trades_button2", new Object[0]));
      var3.setId("do_local_trades_button");
      var2.getHintableList().add(var3);
      var3.setVisibleOverride(VillagerOverlayWidget$InitWidgets::doLocalButton2$lambda$31$lambda$30);
      var54.doLocalButton2 = var14;
      VillagerBookmarkButtonWidget var15 = new VillagerBookmarkButtonWidget(
         VillagerOverlayWidget$InitWidgets::localBookmark$lambda$32, VillagerOverlayWidget$InitWidgets::localBookmark$lambda$34
      );
      var2 = this.this$0;
      var3 = var15;
      var54 = this;
      this.init(var3, "inventoryprofiles.tooltip.set_local_bookmark", "localBookmark_button");
      VillagerBookmarkButtonWidget var69 = var3;
      var10001 = VillagerTradeManager.INSTANCE.getCurrentVillager();
      boolean var84;
      if (var10001 != null) {
         AbstractVillager var46 = var10001;
         VillagerBookmarkButtonWidget var51 = var3;
         Boolean var83 = var46 instanceof Villager;
         var69 = var51;
         var84 = var83;
      } else {
         var84 = false;
      }

      var69.setVisible(var84);
      var3.setVisibleOverride(VillagerOverlayWidget$InitWidgets::localBookmark$lambda$37$lambda$36);
      var2.getHintableList().add(var3);
      var54.localBookmark = var15;
      VillagerBookmarkButtonWidget var16 = new VillagerBookmarkButtonWidget(
         VillagerOverlayWidget$InitWidgets::localBookmark1$lambda$38, VillagerOverlayWidget$InitWidgets::localBookmark1$lambda$40
      );
      var2 = this.this$0;
      var3 = var16;
      var54 = this;
      this.init(var3, "inventoryprofiles.tooltip.set_local_bookmark1", "localBookmark_button");
      VillagerBookmarkButtonWidget var70 = var3;
      var10001 = VillagerTradeManager.INSTANCE.getCurrentVillager();
      boolean var87;
      if (var10001 != null) {
         AbstractVillager var47 = var10001;
         VillagerBookmarkButtonWidget var52 = var3;
         Boolean var86 = var47 instanceof Villager;
         var70 = var52;
         var87 = var86;
      } else {
         var87 = false;
      }

      var70.setVisible(var87);
      var3.setVisibleOverride(VillagerOverlayWidget$InitWidgets::localBookmark1$lambda$43$lambda$42);
      var2.getHintableList().add(var3);
      var54.localBookmark1 = var16;
      VillagerBookmarkButtonWidget var17 = new VillagerBookmarkButtonWidget(
         VillagerOverlayWidget$InitWidgets::localBookmark2$lambda$44, VillagerOverlayWidget$InitWidgets::localBookmark2$lambda$46
      );
      var2 = this.this$0;
      var3 = var17;
      var54 = this;
      this.init(var3, "inventoryprofiles.tooltip.set_local_bookmark2", "localBookmark_button");
      VillagerBookmarkButtonWidget var71 = var3;
      var10001 = VillagerTradeManager.INSTANCE.getCurrentVillager();
      boolean var90;
      if (var10001 != null) {
         AbstractVillager var48 = var10001;
         VillagerBookmarkButtonWidget var53 = var3;
         Boolean var89 = var48 instanceof Villager;
         var71 = var53;
         var90 = var89;
      } else {
         var90 = false;
      }

      var71.setVisible(var90);
      var3.setVisibleOverride(VillagerOverlayWidget$InitWidgets::localBookmark2$lambda$49$lambda$48);
      var2.getHintableList().add(var3);
      var54.localBookmark2 = var17;
      VillagerBookmarkButtonWidget var18;
      VillagerBookmarkButtonWidget var33 = var18 = new VillagerBookmarkButtonWidget(
         VillagerOverlayWidget$InitWidgets::globalBookmark$lambda$50, VillagerOverlayWidget$InitWidgets::globalBookmark$lambda$52
      );
      var54 = this;
      this.init(var33, "inventoryprofiles.tooltip.set_global_bookmark", "globalBookmark_button");
      var54.globalBookmark = var18;
      VillagerBookmarkButtonWidget var19;
      VillagerBookmarkButtonWidget var34 = var19 = new VillagerBookmarkButtonWidget(
         VillagerOverlayWidget$InitWidgets::globalBookmark1$lambda$54, VillagerOverlayWidget$InitWidgets::globalBookmark1$lambda$56
      );
      var54 = this;
      this.init(var34, "inventoryprofiles.tooltip.set_global_bookmark1", "globalBookmark_button");
      var34.setVisibleOverride(VillagerOverlayWidget$InitWidgets::globalBookmark1$lambda$58$lambda$57);
      var54.globalBookmark1 = var19;
      VillagerBookmarkButtonWidget var20;
      VillagerBookmarkButtonWidget var35 = var20 = new VillagerBookmarkButtonWidget(
         VillagerOverlayWidget$InitWidgets::globalBookmark2$lambda$59, VillagerOverlayWidget$InitWidgets::globalBookmark2$lambda$61
      );
      var54 = this;
      this.init(var35, "inventoryprofiles.tooltip.set_global_bookmark2", "globalBookmark_button");
      var35.setVisibleOverride(VillagerOverlayWidget$InitWidgets::globalBookmark2$lambda$63$lambda$62);
      var54.globalBookmark2 = var20;
      VillagerBookmarkButtonWidget[] var21;
      (var21 = new VillagerBookmarkButtonWidget[6])[0] = this.doGlobalButton;
      var21[1] = this.doLocalButton;
      var21[2] = this.doGlobalButton1;
      var21[3] = this.doLocalButton1;
      var21[4] = this.doGlobalButton2;
      var21[5] = this.doLocalButton2;
      this.tradeRehintList = CollectionsKt.listOf(var21);
      VillagerBookmarkButtonWidget[] var22;
      (var22 = new VillagerBookmarkButtonWidget[2])[0] = this.globalBookmark;
      var22[1] = this.localBookmark;
      this.bookmarkRehintList = CollectionsKt.listOf(var22);
      VillagerBookmarkButtonWidget[] var23;
      (var23 = new VillagerBookmarkButtonWidget[2])[0] = this.globalBookmark1;
      var23[1] = this.localBookmark1;
      this.bookmarkRehintList1 = CollectionsKt.listOf(var23);
      VillagerBookmarkButtonWidget[] var24;
      (var24 = new VillagerBookmarkButtonWidget[2])[0] = this.globalBookmark2;
      var24[1] = this.localBookmark2;
      this.bookmarkRehintList2 = CollectionsKt.listOf(var24);
      this.reHint();
      this.lastSelectChange = System.currentTimeMillis();
   }

   private final void init(VillagerBookmarkButtonWidget var1, String var2, String var3) {
      this.this$0.getSnapableList().add(var1);
      var1.setHints(this.hints.hintFor(IPNButton.VILLAGER_GLOBAL_BOOKMARK));
      var1.setTx(0);
      var1.setTy(0);
      var1.setCtx(20);
      var1.setCty(90);
      this.this$0.addChild((Widget)var1);
      var1.setVisible(this.selectedTrade != -1);
      var1.setTooltipText(I18n.INSTANCE.translate(var2, new Object[0]));
      var1.setId(var3);
   }

   public final void reHint() {
      int var1 = 5;
      Iterator var3 = this.tradeRehintList.iterator();

      while (var3.hasNext()) {
         VillagerBookmarkButtonWidget var2;
         VillagerBookmarkButtonWidget var10000 = var2 = (VillagerBookmarkButtonWidget)var3.next();
         var10000.setVisible((Boolean)var10000.getVisibleOverride().invoke(Boolean.TRUE));
         if (var2.getVisible()) {
            WidgetExtensionsKt.setTopRight((Widget)var2, var1 + var2.getHints().getTop(), 4 + var2.getHints().getHorizontalOffset());
            var1 += 12;
         }
      }

      if (System.currentTimeMillis() - this.lastSelectChange > 250L) {
         TradeOfferButton var25 = InGameExKt.get_offers_/* $VF was: get(offers) */(this.this$0.getScreen());
         Intrinsics.checkNotNullExpressionValue(var25, "");
         Object[] var7 = (Object[])var25;
         int var4 = 0;
         int var5 = var7.length;

         while (true) {
            if (var4 >= var5) {
               var25 = null;
               break;
            }

            Object var6;
            TradeOfferButton var12;
            Intrinsics.checkNotNull(var12 = (TradeOfferButton)(var6 = var7[var4]));
            if (WidgetExKt.get_isHovered_/* $VF was: get(isHovered) */((AbstractButton)var12)) {
               var25 = (TradeOfferButton)var6;
               break;
            }

            var4++;
         }

         var25 = var25;
         if (var25 != null) {
            TradeOfferButton var8 = var25;
            VillagerOverlayWidget var14 = this.this$0;
            TradeOfferButton var19 = var8;
            AbstractContainerMenu var28 = ((AbstractContainerScreen)var14.getScreen()).getMenu();
            Intrinsics.checkNotNullExpressionValue(var28, "");
            Intrinsics.checkNotNull(var28);
            this.selectedTrade = var19.getIndex() + InGameExKt.get_indexStartOffset_/* $VF was: get(indexStartOffset) */(var14.getScreen());
         }

         this.lastSelectChange = System.currentTimeMillis();
      }

      byte var13 = -10;
      var1 = this.bookmarksTop + 20 * (this.selectedTrade - InGameExKt.get_indexStartOffset_/* $VF was: get(indexStartOffset) */(this.this$0.getScreen()));
      int var15;
      boolean var20 = (var15 = this.selectedTrade - InGameExKt.get_indexStartOffset_/* $VF was: get(indexStartOffset) */(this.this$0.getScreen())) >= 0
         ? var15 < 7
         : false;
      boolean var21 = false;
      var3 = this.bookmarkRehintList.iterator();

      while (var3.hasNext()) {
         VillagerBookmarkButtonWidget var22;
         VillagerBookmarkButtonWidget var29 = var22 = (VillagerBookmarkButtonWidget)var3.next();
         var29.setVisible((Boolean)var29.getVisibleOverride().invoke(var20));
         if (var22.getVisible()) {
            WidgetExtensionsKt.setTopLeft((Widget)var22, var1 + var22.getHints().getTop(), -10 + var22.getHints().getHorizontalOffset());
            var1 += 12;
            var21 = true;
         }
      }

      if (var21) {
         var13 = -22;
         var21 = false;
      }

      var1 = this.bookmarksTop + 20 * (this.selectedTrade - InGameExKt.get_indexStartOffset_/* $VF was: get(indexStartOffset) */(this.this$0.getScreen()));
      var3 = this.bookmarkRehintList1.iterator();

      while (var3.hasNext()) {
         VillagerBookmarkButtonWidget var23;
         VillagerBookmarkButtonWidget var30 = var23 = (VillagerBookmarkButtonWidget)var3.next();
         var30.setVisible((Boolean)var30.getVisibleOverride().invoke(var20));
         if (var23.getVisible()) {
            WidgetExtensionsKt.setTopLeft((Widget)var23, var1 + var23.getHints().getTop(), var13 + var23.getHints().getHorizontalOffset());
            var1 += 12;
            var21 = true;
         }
      }

      if (var21) {
         var13 -= 12;
      }

      var1 = this.bookmarksTop + 20 * (this.selectedTrade - InGameExKt.get_indexStartOffset_/* $VF was: get(indexStartOffset) */(this.this$0.getScreen()));
      var3 = this.bookmarkRehintList2.iterator();

      while (var3.hasNext()) {
         VillagerBookmarkButtonWidget var24;
         VillagerBookmarkButtonWidget var31 = var24 = (VillagerBookmarkButtonWidget)var3.next();
         var31.setVisible((Boolean)var31.getVisibleOverride().invoke(var20));
         if (var24.getVisible()) {
            WidgetExtensionsKt.setTopLeft((Widget)var24, var1 + var24.getHints().getTop(), var13 + var24.getHints().getHorizontalOffset());
            var1 += 12;
         }
      }
   }

   private static final int doGlobalButton$lambda$1() {
      return ModSettings.INSTANCE.getVILLAGER_TRADING_GLOBAL_COLOR().getValue();
   }

   private static final Unit doGlobalButton$lambda$2(VillagerOverlayWidget var0) {
      VillagerTradeManager.INSTANCE.doGlobalTrades(var0.getScreen());
      return Unit.INSTANCE;
   }

   private static final boolean doGlobalButton$lambda$4$lambda$3() {
      return true;
   }

   private static final int doGlobalButton1$lambda$5() {
      return ModSettings.INSTANCE.getVILLAGER_TRADING_GLOBAL_COLOR1().getValue();
   }

   private static final Unit doGlobalButton1$lambda$6(VillagerOverlayWidget var0) {
      VillagerTradeManager.INSTANCE.doGlobalTrades1(var0.getScreen());
      return Unit.INSTANCE;
   }

   private static final boolean doGlobalButton1$lambda$9$lambda$7() {
      return true;
   }

   private static final boolean doGlobalButton1$lambda$9$lambda$8(boolean var0) {
      return ModSettings.INSTANCE.getVILLAGER_TRADING_GROUP_1().getBooleanValue();
   }

   private static final int doGlobalButton2$lambda$10() {
      return ModSettings.INSTANCE.getVILLAGER_TRADING_GLOBAL_COLOR2().getValue();
   }

   private static final Unit doGlobalButton2$lambda$11(VillagerOverlayWidget var0) {
      VillagerTradeManager.INSTANCE.doGlobalTrades2(var0.getScreen());
      return Unit.INSTANCE;
   }

   private static final boolean doGlobalButton2$lambda$14$lambda$12() {
      return true;
   }

   private static final boolean doGlobalButton2$lambda$14$lambda$13(boolean var0) {
      return ModSettings.INSTANCE.getVILLAGER_TRADING_GROUP_2().getBooleanValue();
   }

   private static final int doLocalButton$lambda$15() {
      return ModSettings.INSTANCE.getVILLAGER_TRADING_LOCAL_COLOR().getValue();
   }

   private static final Unit doLocalButton$lambda$16(VillagerOverlayWidget var0) {
      VillagerTradeManager.INSTANCE.doLocalTrades(var0.getScreen());
      return Unit.INSTANCE;
   }

   private static final boolean doLocalButton$lambda$19$lambda$17() {
      return true;
   }

   private static final int doLocalButton1$lambda$20() {
      return ModSettings.INSTANCE.getVILLAGER_TRADING_LOCAL_COLOR1().getValue();
   }

   private static final Unit doLocalButton1$lambda$21(VillagerOverlayWidget var0) {
      VillagerTradeManager.INSTANCE.doLocalTrades1(var0.getScreen());
      return Unit.INSTANCE;
   }

   private static final boolean doLocalButton1$lambda$25$lambda$22() {
      return true;
   }

   private static final boolean doLocalButton1$lambda$25$lambda$24(boolean var0) {
      return ModSettings.INSTANCE.getVILLAGER_TRADING_GROUP_1().getBooleanValue();
   }

   private static final int doLocalButton2$lambda$26() {
      return ModSettings.INSTANCE.getVILLAGER_TRADING_LOCAL_COLOR2().getValue();
   }

   private static final Unit doLocalButton2$lambda$27(VillagerOverlayWidget var0) {
      VillagerTradeManager.INSTANCE.doLocalTrades2(var0.getScreen());
      return Unit.INSTANCE;
   }

   private static final boolean doLocalButton2$lambda$31$lambda$28() {
      return true;
   }

   private static final boolean doLocalButton2$lambda$31$lambda$30(boolean var0) {
      return ModSettings.INSTANCE.getVILLAGER_TRADING_GROUP_2().getBooleanValue();
   }

   private static final int localBookmark$lambda$32() {
      return ModSettings.INSTANCE.getVILLAGER_TRADING_LOCAL_COLOR().getValue();
   }

   private static final Unit localBookmark$lambda$34(VillagerOverlayWidget$InitWidgets var0, VillagerOverlayWidget var1) {
      AbstractVillager var10000 = VillagerTradeManager.INSTANCE.getCurrentVillager();
      if (var10000 != null) {
         AbstractVillager var2 = var10000;
         int var3;
         if ((var3 = var0.selectedTrade) >= 0) {
            VillagerTradeManager.INSTANCE.toggleBookmark(var1.getScreen(), var3, false, var2, 0);
         }
      }

      return Unit.INSTANCE;
   }

   private static final boolean localBookmark$lambda$37$lambda$36(boolean var0) {
      AbstractVillager var1;
      return ((var1 = VillagerTradeManager.INSTANCE.getCurrentVillager()) == null || var1 instanceof Villager) && var0;
   }

   private static final int localBookmark1$lambda$38() {
      return ModSettings.INSTANCE.getVILLAGER_TRADING_LOCAL_COLOR1().getValue();
   }

   private static final Unit localBookmark1$lambda$40(VillagerOverlayWidget$InitWidgets var0, VillagerOverlayWidget var1) {
      AbstractVillager var10000 = VillagerTradeManager.INSTANCE.getCurrentVillager();
      if (var10000 != null) {
         AbstractVillager var2 = var10000;
         int var3;
         if ((var3 = var0.selectedTrade) >= 0) {
            VillagerTradeManager.INSTANCE.toggleBookmark(var1.getScreen(), var3, false, var2, 1);
         }
      }

      return Unit.INSTANCE;
   }

   private static final boolean localBookmark1$lambda$43$lambda$42(boolean var0) {
      AbstractVillager var1 = VillagerTradeManager.INSTANCE.getCurrentVillager();
      return ModSettings.INSTANCE.getVILLAGER_TRADING_GROUP_1().getBooleanValue() && (var1 == null || var1 instanceof Villager) && var0;
   }

   private static final int localBookmark2$lambda$44() {
      return ModSettings.INSTANCE.getVILLAGER_TRADING_LOCAL_COLOR2().getValue();
   }

   private static final Unit localBookmark2$lambda$46(VillagerOverlayWidget$InitWidgets var0, VillagerOverlayWidget var1) {
      AbstractVillager var10000 = VillagerTradeManager.INSTANCE.getCurrentVillager();
      if (var10000 != null) {
         AbstractVillager var2 = var10000;
         int var3;
         if ((var3 = var0.selectedTrade) >= 0) {
            VillagerTradeManager.INSTANCE.toggleBookmark(var1.getScreen(), var3, false, var2, 2);
         }
      }

      return Unit.INSTANCE;
   }

   private static final boolean localBookmark2$lambda$49$lambda$48(boolean var0) {
      AbstractVillager var1 = VillagerTradeManager.INSTANCE.getCurrentVillager();
      return ModSettings.INSTANCE.getVILLAGER_TRADING_GROUP_2().getBooleanValue() && (var1 == null || var1 instanceof Villager) && var0;
   }

   private static final int globalBookmark$lambda$50() {
      return ModSettings.INSTANCE.getVILLAGER_TRADING_GLOBAL_COLOR().getValue();
   }

   private static final Unit globalBookmark$lambda$52(VillagerOverlayWidget$InitWidgets var0, VillagerOverlayWidget var1) {
      AbstractVillager var10000 = VillagerTradeManager.INSTANCE.getCurrentVillager();
      if (var10000 != null) {
         AbstractVillager var2 = var10000;
         int var3;
         if ((var3 = var0.selectedTrade) >= 0) {
            VillagerTradeManager.INSTANCE.toggleBookmark(var1.getScreen(), var3, true, var2, 0);
         }
      }

      return Unit.INSTANCE;
   }

   private static final int globalBookmark1$lambda$54() {
      return ModSettings.INSTANCE.getVILLAGER_TRADING_GLOBAL_COLOR1().getValue();
   }

   private static final Unit globalBookmark1$lambda$56(VillagerOverlayWidget$InitWidgets var0, VillagerOverlayWidget var1) {
      AbstractVillager var10000 = VillagerTradeManager.INSTANCE.getCurrentVillager();
      if (var10000 != null) {
         AbstractVillager var2 = var10000;
         int var3;
         if ((var3 = var0.selectedTrade) >= 0) {
            VillagerTradeManager.INSTANCE.toggleBookmark(var1.getScreen(), var3, true, var2, 1);
         }
      }

      return Unit.INSTANCE;
   }

   private static final boolean globalBookmark1$lambda$58$lambda$57(boolean var0) {
      return ModSettings.INSTANCE.getVILLAGER_TRADING_GROUP_1().getBooleanValue();
   }

   private static final int globalBookmark2$lambda$59() {
      return ModSettings.INSTANCE.getVILLAGER_TRADING_GLOBAL_COLOR2().getValue();
   }

   private static final Unit globalBookmark2$lambda$61(VillagerOverlayWidget$InitWidgets var0, VillagerOverlayWidget var1) {
      AbstractVillager var10000 = VillagerTradeManager.INSTANCE.getCurrentVillager();
      if (var10000 != null) {
         AbstractVillager var2 = var10000;
         int var3;
         if ((var3 = var0.selectedTrade) >= 0) {
            VillagerTradeManager.INSTANCE.toggleBookmark(var1.getScreen(), var3, true, var2, 2);
         }
      }

      return Unit.INSTANCE;
   }

   private static final boolean globalBookmark2$lambda$63$lambda$62(boolean var0) {
      return ModSettings.INSTANCE.getVILLAGER_TRADING_GROUP_2().getBooleanValue();
   }
}
