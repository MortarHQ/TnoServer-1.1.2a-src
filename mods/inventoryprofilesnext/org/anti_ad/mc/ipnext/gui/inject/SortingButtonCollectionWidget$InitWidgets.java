package org.anti_ad.mc.ipnext.gui.inject;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Set;
import kotlin.NoWhenBranchMatchedException;
import kotlin.Pair;
import kotlin.TuplesKt;
import kotlin.Unit;
import kotlin.collections.CollectionsKt;
import kotlin.collections.SetsKt;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.MutablePropertyReference1;
import kotlin.jvm.internal.MutablePropertyReference1Impl;
import kotlin.jvm.internal.Reflection;
import kotlin.jvm.internal.SourceDebugExtension;
import kotlin.properties.ReadWriteProperty;
import kotlin.reflect.KProperty;
import org.anti_ad.mc.common.extensions.Kt_collectionKt;
import org.anti_ad.mc.common.gui.layout.WidgetExtensionsKt;
import org.anti_ad.mc.common.gui.widgets.Widget;
import org.anti_ad.mc.common.input.MainKeybind;
import org.anti_ad.mc.common.vanilla.alias.glue.I18n;
import org.anti_ad.mc.ipn.api.IPNButton;
import org.anti_ad.mc.ipnext.config.ConfigScreenSettings;
import org.anti_ad.mc.ipnext.config.ContinuousCraftingCheckboxValue;
import org.anti_ad.mc.ipnext.config.GuiSettings;
import org.anti_ad.mc.ipnext.config.ModSettings;
import org.anti_ad.mc.ipnext.config.SortingMethod;
import org.anti_ad.mc.ipnext.gui.inject.base.CheckBoxWidget;
import org.anti_ad.mc.ipnext.gui.inject.base.SortButtonWidget;
import org.anti_ad.mc.ipnext.inventory.ContainerType;
import org.anti_ad.mc.ipnext.inventory.ContainerTypes;
import org.jetbrains.annotations.NotNull;

@SourceDebugExtension(
   "SMAP\nSortingButtonCollectionWidget.kt\nKotlin\n*S Kotlin\n*F\n+ 1 SortingButtonCollectionWidget.kt\norg/anti_ad/mc/ipnext/gui/inject/SortingButtonCollectionWidget$InitWidgets\n+ 2 _Collections.kt\nkotlin/collections/CollectionsKt___CollectionsKt\n+ 3 PropertyDelegates.kt\norg/anti_ad/mc/common/extensions/PropertyDelegatesKt\n+ 4 Delegates.kt\nkotlin/properties/Delegates\n*L\n1#1,545:1\n774#2:546\n865#2,2:547\n1869#2,2:559\n1869#2,2:561\n1869#2,2:563\n1869#2,2:565\n35#3:549\n38#3:553\n35#3:554\n38#3:558\n33#4,3:550\n33#4,3:555\n*S KotlinDebug\n*F\n+ 1 SortingButtonCollectionWidget.kt\norg/anti_ad/mc/ipnext/gui/inject/SortingButtonCollectionWidget$InitWidgets\n*L\n324#1:546\n324#1:547,2\n483#1:559,2\n495#1:561,2\n504#1:563,2\n507#1:565,2\n375#1:549\n375#1:553\n379#1:554\n379#1:558\n375#1:550,3\n379#1:555,3\n*E\n"
)
public final class SortingButtonCollectionWidget$InitWidgets {
   @NotNull
   private final Widget dummyRenderUpdater;
   @NotNull
   private final Set types;
   private final boolean isCrafting;
   private final boolean isStoneCutter;
   private final boolean isAnvil;
   private final boolean addChestSide;
   private final boolean addNonChestSide;
   private final boolean shouldAdd;
   @NotNull
   private final SortButtonWidget sortButtonPlayer;
   @NotNull
   private final SortButtonWidget sortInColumnButtonPlayer;
   @NotNull
   private final SortButtonWidget sortInRowButtonPlayer;
   @NotNull
   private final SortButtonWidget sortButton;
   @NotNull
   private final SortButtonWidget sortInColumnButton;
   @NotNull
   private final SortButtonWidget sortInRowButton;
   private final boolean moveAllVisible;
   @NotNull
   private final String moveAllToolTip;
   @NotNull
   private final SortingButtonCollectionWidget$InitWidgets$MoveButton moveAllToContainer;
   @NotNull
   private final SortingButtonCollectionWidget$InitWidgets$MoveButton moveAllToPlayer;
   @NotNull
   private final ReadWriteProperty continuousCraftingValue$delegate;
   @NotNull
   private final ReadWriteProperty fastRenameValue$delegate;
   @NotNull
   private final CheckBoxWidget continuousCraftingCheckbox;

   public SortingButtonCollectionWidget$InitWidgets(SortingButtonCollectionWidget var1) {
      boolean var42;
      label117: {
         this.this$0 = var1;
         super();
         this.dummyRenderUpdater = new SortingButtonCollectionWidget$InitWidgets$dummyRenderUpdater$1(this.this$0, this);
         Set var13 = ContainerTypes.getTypes$default(ContainerTypes.INSTANCE, this.this$0.getContainer(), null, 2, null);
         SortingButtonCollectionWidget var2 = this.this$0;
         Set var3 = var13;
         SortingButtonCollectionWidget$InitWidgets var12 = this;
         var12.types = var2.getHints().getPlayerSideOnly()
            ? SetsKt.plus(SetsKt.minus(var3, ContainerType.SORTABLE_STORAGE), ContainerType.PURE_BACKPACK)
            : var3;
         this.isCrafting = this.types.contains(ContainerType.CRAFTING);
         this.isStoneCutter = this.types.contains(ContainerType.STONECUTTER);
         this.isAnvil = this.types.contains(ContainerType.ANVIL);
         this.addChestSide = this.types.contains(ContainerType.SORTABLE_STORAGE);
         this.addNonChestSide = this.types.contains(ContainerType.PURE_BACKPACK);
         this.shouldAdd = this.addChestSide || this.addNonChestSide;
         this.sortButtonPlayer = makeSortButton$default(this, true, false, false, 6, null);
         this.sortInColumnButtonPlayer = makeSortButton$default(this, true, true, false, 4, null);
         this.sortInRowButtonPlayer = makeSortButton$default(this, true, false, true, 2, null);
         this.sortButton = makeSortButton$default(this, false, false, false, 7, null);
         this.sortInColumnButton = makeSortButton$default(this, false, true, false, 5, null);
         this.sortInRowButton = makeSortButton$default(this, false, false, true, 3, null);
         if (GuiSettings.INSTANCE.getSHOW_MOVE_ALL_BUTTON().getBooleanValue()) {
            Iterable var10001 = this.types;
            ContainerType[] var14;
            (var14 = new ContainerType[3])[0] = ContainerType.SORTABLE_STORAGE;
            var14[1] = ContainerType.NO_SORTING_STORAGE;
            var14[2] = ContainerType.CRAFTING;
            if (Kt_collectionKt.containsAny(var10001, SetsKt.setOf(var14))) {
               var42 = true;
               break label117;
            }
         }

         var42 = false;
      }

      this.moveAllVisible = var42;
      ModSettings var21 = ModSettings.INSTANCE;
      SortingButtonCollectionWidget$InitWidgets var36 = this;
      String var15 = "inventoryprofiles.tooltip.move_all_button";
      Object[] var4 = var21.getALWAYS_MOVE_ALL().getBooleanValue() ? "title_move_all" : "title_move_matching";
      String var28 = var21.getALWAYS_INCLUDE_HOTBAR().getBooleanValue() ? "exclude_hotbar" : "include_hotbar";
      String var5 = var21.getALWAYS_MOVE_ALL().getBooleanValue() ? "move_matching_only" : "move_all";
      String var6 = "move_focused_only";
      String var7 = "move_just_refill";
      MainKeybind var8 = var21.getINCLUDE_HOTBAR_MODIFIER().getMainKeybind();
      MainKeybind var9 = var21.getMOVE_ALL_MODIFIER().getMainKeybind();
      MainKeybind var10 = var21.getMOVE_FOCUS_MACH_MODIFIER().getMainKeybind();
      MainKeybind var22 = var21.getMOVE_JUST_REFILL_MODIFIER().getMainKeybind();
      Pair[] var11;
      (var11 = new Pair[5])[0] = TuplesKt.to(var4, null);
      var11[1] = TuplesKt.to(var28, var8);
      var11[2] = TuplesKt.to(var5, var9);
      var11[3] = TuplesKt.to(var6, var10);
      var11[4] = TuplesKt.to(var7, var22);
      Iterable var23 = CollectionsKt.listOf(var11);
      var var29 = new ArrayList();
      Iterator var24 = var23.iterator();

      while (var24.hasNext()) {
         boolean var41;
         label109: {
            MainKeybind var10000 = (MainKeybind)((Pair)(var4 = var24.next())).component2();
            if (var10000 != null) {
               List var40 = var10000.getKeyCodes();
               if (var40 != null) {
                  var41 = var40.isEmpty();
                  break label109;
               }
            }

            var41 = false;
         }

         if (!var41) {
            var29.add(var4);
         }
      }

      var36.moveAllToolTip = CollectionsKt.joinToString$default(
         var29, "\n", null, null, 0, null, SortingButtonCollectionWidget$InitWidgets::moveAllToolTip$lambda$8$lambda$7, 30, null
      );
      SortingButtonCollectionWidget$InitWidgets$MoveButton var16 = new SortingButtonCollectionWidget$InitWidgets$MoveButton(this, false);
      SortingButtonCollectionWidget var25 = this.this$0;
      SortingButtonCollectionWidget$InitWidgets$MoveButton var30 = var16;
      var36 = this;
      var30.setHints(var25.getHints().hintFor(IPNButton.MOVE_TO_CONTAINER));
      var30.setTx(50);
      var25.addChild((Widget)var30);
      var30.setVisible(this.moveAllVisible);
      var30.setTooltipText(this.moveAllToolTip);
      var30.setId("sort_move_all_button");
      var25.getHintableList().add(var30);
      var36.moveAllToContainer = var16;
      SortingButtonCollectionWidget$InitWidgets$MoveButton var17 = new SortingButtonCollectionWidget$InitWidgets$MoveButton(this, true);
      SortingButtonCollectionWidget var26 = this.this$0;
      SortingButtonCollectionWidget$InitWidgets$MoveButton var31 = var17;
      var36 = this;
      var31.setHints(var26.getHints().hintFor(IPNButton.MOVE_TO_PLAYER));
      var31.setTx(60);
      var26.addChild((Widget)var31);
      var31.setVisible(this.moveAllVisible && !this.types.contains(ContainerType.CRAFTING));
      var31.setTooltipText(this.moveAllToolTip);
      var31.setId("sort_move_all_button");
      var26.getHintableList().add(var31);
      var36.moveAllToPlayer = var17;
      Boolean var18 = GuiSettings.INSTANCE.getCONTINUOUS_CRAFTING_SAVED_VALUE().getBooleanValue();
      this.continuousCraftingValue$delegate = (ReadWriteProperty)(new SortingButtonCollectionWidget$InitWidgets$special$$inlined$detectable$1(var18, this));
      Boolean var19 = GuiSettings.INSTANCE.getFAST_RENAME_SAVED_VALUE().getBooleanValue();
      this.fastRenameValue$delegate = (ReadWriteProperty)(new SortingButtonCollectionWidget$InitWidgets$special$$inlined$detectable$2(var19, this));

      this.setContinuousCraftingValue(switch ((ContinuousCraftingCheckboxValue)GuiSettings.INSTANCE.getCONTINUOUS_CRAFTING_CHECKBOX_VALUE().getValue()) {
         case REMEMBER -> GuiSettings.INSTANCE.getCONTINUOUS_CRAFTING_SAVED_VALUE().getBooleanValue();
         case CHECKED -> true;
         case UNCHECKED -> false;
         default -> throw new NoWhenBranchMatchedException();
      });

      this.setFastRenameValue(switch ((ContinuousCraftingCheckboxValue)GuiSettings.INSTANCE.getFAST_RENAME_CHECKBOX_VALUE().getValue()) {
         case REMEMBER -> GuiSettings.INSTANCE.getFAST_RENAME_SAVED_VALUE().getBooleanValue();
         case CHECKED -> true;
         case UNCHECKED -> false;
         default -> throw new NoWhenBranchMatchedException();
      });
      CheckBoxWidget var20 = new CheckBoxWidget(SortingButtonCollectionWidget$InitWidgets::continuousCraftingCheckbox$lambda$13);
      SortingButtonCollectionWidget var27 = this.this$0;
      CheckBoxWidget var32 = var20;
      var36 = this;
      var27.getSnapableList().add(var32);
      var32.setHints(var27.getHints().hintFor(IPNButton.CONTINUOUS_CRAFTING));
      var27.addChild((Widget)var32);
      if (this.isAnvil) {
         var32.setTx(this.getFastRenameValue() ? 80 : 70);
         var32.setHighlightTx(this.getFastRenameValue() ? 120 : 70);
         var32.setVisible(GuiSettings.INSTANCE.getSHOW_FAST_RENAME_CHECKBOX().getValue());
         var32.setTooltipText(I18n.INSTANCE.translate("inventoryprofiles.tooltip.fast_rename", new Object[0]));
         var32.setHighlightEnabled(false);
      } else if (!this.isCrafting && !this.isStoneCutter) {
         var32.setVisible(false);
      } else {
         var32.setTx(this.getContinuousCraftingValue() ? 80 : 70);
         var32.setHighlightTx(this.getContinuousCraftingValue() ? 120 : 70);
         var32.setVisible(GuiSettings.INSTANCE.getSHOW_CONTINUOUS_CRAFTING_CHECKBOX().getValue());
         I18n var45 = I18n.INSTANCE;
         Object[] var10003 = var4 = new Object[1];
         String var10005 = ModSettings.INSTANCE.getINCLUDE_HOTBAR_MODIFIER().getMainKeybind().getDisplayText().toUpperCase(Locale.ROOT);
         Intrinsics.checkNotNullExpressionValue(var10005, "");
         var10003[0] = var10005;
         var32.setTooltipText(var45.translate("inventoryprofiles.tooltip.continuous_crafting_checkbox", var4));
         I18n var46 = I18n.INSTANCE;
         var10003 = var4 = new Object[1];
         var10005 = ModSettings.INSTANCE.getINCLUDE_HOTBAR_MODIFIER().getMainKeybind().getDisplayText().toUpperCase(Locale.ROOT);
         Intrinsics.checkNotNullExpressionValue(var10005, "");
         var10003[0] = var10005;
         var32.setHighlightTooltip(var46.translate("inventoryprofiles.tooltip.auto_crafting_checkbox", var4));
      }

      var32.setId("sort_crafting_checkbox");
      var27.getHintableList().add(var32);
      var36.continuousCraftingCheckbox = var20;
      this.reHint();
   }

   @NotNull
   public final Widget getDummyRenderUpdater() {
      return this.dummyRenderUpdater;
   }

   @NotNull
   public final Set getTypes() {
      return this.types;
   }

   private final SortButtonWidget makeSortButton(boolean var1, boolean var2, boolean var3) {
      if (var2) {
         SortingButtonCollectionWidget$InitWidgets$SortInColumnButton var7 = new SortingButtonCollectionWidget$InitWidgets$SortInColumnButton(this, var1);
         SortingButtonCollectionWidget var10 = this.this$0;
         SortingButtonCollectionWidget$InitWidgets$SortInColumnButton var12 = var7;
         var7.setHints(var1 ? var10.getHints().hintFor(IPNButton.SORT_COLUMNS_PLAYER) : var10.getHints().hintFor(IPNButton.SORT_COLUMNS));
         var12.setTx(20);
         var10.addChild((Widget)var12);
         var12.setVisible(GuiSettings.INSTANCE.getSHOW_SORT_IN_COLUMNS_BUTTON().getBooleanValue() && this.shouldAdd);
         var12.setTooltipText(I18n.INSTANCE.translate("inventoryprofiles.tooltip.sort_columns_button", new Object[0]));
         var12.setId("sort_columns_button");
         var10.getHintableList().add(var12);
         return var7;
      } else if (var3) {
         SortingButtonCollectionWidget$InitWidgets$SortInRowButton var6 = new SortingButtonCollectionWidget$InitWidgets$SortInRowButton(this, var1);
         SortingButtonCollectionWidget var9 = this.this$0;
         SortingButtonCollectionWidget$InitWidgets$SortInRowButton var11 = var6;
         var6.setHints(var1 ? var9.getHints().hintFor(IPNButton.SORT_ROWS_PLAYER) : var9.getHints().hintFor(IPNButton.SORT_ROWS));
         var11.setTx(30);
         var9.addChild((Widget)var11);
         var11.setVisible(GuiSettings.INSTANCE.getSHOW_SORT_IN_ROWS_BUTTON().getBooleanValue() && this.shouldAdd);
         var11.setTooltipText(I18n.INSTANCE.translate("inventoryprofiles.tooltip.sort_rows_button", new Object[0]));
         var11.setId("sort_rows_button");
         var9.getHintableList().add(var11);
         return var6;
      } else {
         SortingButtonCollectionWidget$InitWidgets$SortButton var5 = new SortingButtonCollectionWidget$InitWidgets$SortButton(this, var1);
         SortingButtonCollectionWidget var8 = this.this$0;
         SortingButtonCollectionWidget$InitWidgets$SortButton var4 = var5;
         var5.setHints(var1 ? var8.getHints().hintFor(IPNButton.SORT_PLAYER) : var8.getHints().hintFor(IPNButton.SORT));
         var4.setTx(10);
         var8.addChild((Widget)var4);
         var4.setVisible(GuiSettings.INSTANCE.getSHOW_REGULAR_SORT_BUTTON().getBooleanValue() && this.shouldAdd);
         var4.setTooltipTextSource(SortingButtonCollectionWidget$InitWidgets::makeSortButton$lambda$5$lambda$4);
         var4.setId("sort_button");
         var8.getHintableList().add(var4);
         return var5;
      }
   }

   private final void updateCraftingConfigValue(boolean var1) {
      GuiSettings.INSTANCE.getCONTINUOUS_CRAFTING_SAVED_VALUE().setValue(var1);
      ConfigScreenSettings.INSTANCE.getSaveLoadManager().save();
   }

   private final void updateFastRenameConfigValue(boolean var1) {
      GuiSettings.INSTANCE.getFAST_RENAME_SAVED_VALUE().setValue(var1);
      ConfigScreenSettings.INSTANCE.getSaveLoadManager().save();
   }

   public final boolean getContinuousCraftingValue() {
      return (Boolean)this.continuousCraftingValue$delegate.getValue(this, $$delegatedProperties[0]);
   }

   public final void setContinuousCraftingValue(boolean var1) {
      this.continuousCraftingValue$delegate.setValue(this, $$delegatedProperties[0], var1);
   }

   public final boolean getFastRenameValue() {
      return (Boolean)this.fastRenameValue$delegate.getValue(this, $$delegatedProperties[1]);
   }

   public final void setFastRenameValue(boolean var1) {
      this.fastRenameValue$delegate.setValue(this, $$delegatedProperties[1], var1);
   }

   private final void switchCheckBoxValues() {
      if (this.isAnvil) {
         this.setFastRenameValue(!this.getFastRenameValue());
         this.continuousCraftingCheckbox.setTx(this.getFastRenameValue() ? 80 : 70);
      } else {
         if (this.isCrafting || this.isStoneCutter) {
            this.setContinuousCraftingValue(!this.getContinuousCraftingValue());
            this.continuousCraftingCheckbox.setTx(this.getContinuousCraftingValue() ? 80 : 70);
            this.continuousCraftingCheckbox.setHighlightTx(this.getContinuousCraftingValue() ? 120 : 70);
         }
      }
   }

   public final void reHint() {
      byte var1 = 7;
      if (this.types.contains(ContainerType.CREATIVE)) {
         var1 = 25;
      }

      boolean var2 = this.types.contains(ContainerType.PLAYER);
      if (this.moveAllVisible) {
         WidgetExtensionsKt.setBottomRight(
            (Widget)this.moveAllToContainer,
            85 + (var2 ? 12 : 0) + this.moveAllToContainer.getHints().getBottom(),
            var1 + this.moveAllToContainer.getHints().getHorizontalOffset()
         );
         if (this.moveAllToPlayer.getVisible()) {
            WidgetExtensionsKt.setTopRight(
               (Widget)this.moveAllToPlayer, 5 + this.moveAllToPlayer.getHints().getTop(), var1 + this.moveAllToPlayer.getHints().getHorizontalOffset()
            );
         }

         if (!var2) {
            var1 += 12;
         }
      }

      if (GuiSettings.INSTANCE.getSHOW_BUTTONS_BOTH_SIDES().getValue() && !var2 && this.addChestSide) {
         SortButtonWidget[] var9;
         (var9 = new SortButtonWidget[3])[0] = this.sortInRowButton;
         var9[1] = this.sortInColumnButton;
         var9[2] = this.sortButton;
         Iterator var10 = CollectionsKt.listOf(var9).iterator();

         while (var10.hasNext()) {
            SortButtonWidget var13;
            if ((var13 = (SortButtonWidget)var10.next()).getVisible() && this.addChestSide) {
               WidgetExtensionsKt.setTopRight((Widget)var13, 5 + var13.getHints().getTop(), var1 + var13.getHints().getHorizontalOffset());
               var1 += 12;
            }
         }

         var1 = 19;
         if (this.types.contains(ContainerType.CREATIVE)) {
            var1 = 37;
         }

         SortButtonWidget[] var11;
         (var11 = new SortButtonWidget[3])[0] = this.sortInRowButtonPlayer;
         var11[1] = this.sortInColumnButtonPlayer;
         var11[2] = this.sortButtonPlayer;
         Iterator var12 = CollectionsKt.listOf(var11).iterator();

         while (var12.hasNext()) {
            SortButtonWidget var14;
            if ((var14 = (SortButtonWidget)var12.next()).getVisible()) {
               WidgetExtensionsKt.setBottomRight((Widget)var14, 85 + var14.getHints().getBottom(), var1 + var14.getHints().getHorizontalOffset());
               var1 += 12;
            }
         }
      } else {
         SortButtonWidget[] var5;
         (var5 = new SortButtonWidget[3])[0] = this.sortInRowButtonPlayer;
         var5[1] = this.sortInColumnButtonPlayer;
         var5[2] = this.sortButtonPlayer;
         Iterator var6 = CollectionsKt.listOf(var5).iterator();

         while (var6.hasNext()) {
            ((SortButtonWidget)var6.next()).setVisible(false);
         }

         SortButtonWidget[] var7;
         (var7 = new SortButtonWidget[3])[0] = this.sortInRowButton;
         var7[1] = this.sortInColumnButton;
         var7[2] = this.sortButton;
         Iterator var8 = CollectionsKt.listOf(var7).iterator();

         while (var8.hasNext()) {
            SortButtonWidget var3;
            if ((var3 = (SortButtonWidget)var8.next()).getVisible()) {
               if (this.addChestSide) {
                  WidgetExtensionsKt.setTopRight((Widget)var3, 5 + var3.getHints().getTop(), var1 + var3.getHints().getHorizontalOffset());
               } else {
                  WidgetExtensionsKt.setBottomRight((Widget)var3, 85 + var3.getHints().getBottom(), var1 + var3.getHints().getHorizontalOffset());
               }

               var1 += 12;
            }
         }
      }

      if (this.types.contains(ContainerType.PLAYER)) {
         WidgetExtensionsKt.setBottomRight(
            (Widget)this.continuousCraftingCheckbox,
            113 + this.continuousCraftingCheckbox.getHints().getBottom(),
            31 + this.continuousCraftingCheckbox.getHints().getHorizontalOffset()
         );
      } else if (this.types.contains(ContainerType.STONECUTTER)) {
         WidgetExtensionsKt.setBottomRight(
            (Widget)this.continuousCraftingCheckbox,
            100 + this.continuousCraftingCheckbox.getHints().getBottom(),
            27 + this.continuousCraftingCheckbox.getHints().getHorizontalOffset()
         );
      } else if (this.types.contains(ContainerType.ANVIL)) {
         WidgetExtensionsKt.setBottomRight(
            (Widget)this.continuousCraftingCheckbox,
            110 + this.continuousCraftingCheckbox.getHints().getBottom(),
            13 + this.continuousCraftingCheckbox.getHints().getHorizontalOffset()
         );
      } else {
         WidgetExtensionsKt.setBottomRight(
            (Widget)this.continuousCraftingCheckbox,
            96 + this.continuousCraftingCheckbox.getHints().getBottom(),
            81 + this.continuousCraftingCheckbox.getHints().getHorizontalOffset()
         );
      }
   }

   private static final String makeSortButton$lambda$5$lambda$4() {
      StringBuilder var10000 = new StringBuilder().append(I18n.INSTANCE.translate("inventoryprofiles.tooltip.sort_button", new Object[0]));
      I18n var10001 = I18n.INSTANCE;
      Object[] var0;
      (var0 = new Object[1])[0] = I18n.INSTANCE.translate(((SortingMethod)ModSettings.INSTANCE.getSORT_ORDER().getValue()).toString(), new Object[0]);
      StringBuilder var2;
      StringBuilder var1 = var2 = var10000.append(var10001.translate("inventoryprofiles.tooltip.sort_button.current_order", var0));
      if (GuiSettings.INSTANCE.getCHANGE_SORT_ORDER_WITH_SCROLL_WHEEL().getBooleanValue()) {
         var1.append(I18n.INSTANCE.translate("inventoryprofiles.tooltip.sort_button.key_help", new Object[0]));
      }

      return var2.toString();
   }

   private static final CharSequence moveAllToolTip$lambda$8$lambda$7(String var0, Pair var1) {
      String var5;
      I18n var10000;
      String var10001;
      Object[] var10002;
      label12: {
         Intrinsics.checkNotNullParameter(var1, "");
         String var2 = (String)var1.component1();
         MainKeybind var4 = (MainKeybind)var1.component2();
         var10000 = I18n.INSTANCE;
         var10001 = var0 + "." + var2;
         var10002 = var3 = new Object[1];
         if (var4 != null) {
            var5 = var4.getDisplayText();
            if (var5 != null) {
               var5 = var5.toUpperCase(Locale.ROOT);
               Intrinsics.checkNotNullExpressionValue(var5, "");
               break label12;
            }
         }

         var5 = null;
      }

      var10002[0] = var5;
      return var10000.translate(var10001, var3);
   }

   private static final Unit continuousCraftingCheckbox$lambda$13(SortingButtonCollectionWidget$InitWidgets var0) {
      var0.switchCheckBoxValues();
      return Unit.INSTANCE;
   }

   static {
      KProperty[] var0;
      (var0 = new KProperty[2])[0] = Reflection.mutableProperty1(
         (MutablePropertyReference1)(
            new MutablePropertyReference1Impl(SortingButtonCollectionWidget$InitWidgets.class, "continuousCraftingValue", "getContinuousCraftingValue()Z", 0)
         )
      );
      var0[1] = Reflection.mutableProperty1(
         (MutablePropertyReference1)(
            new MutablePropertyReference1Impl(SortingButtonCollectionWidget$InitWidgets.class, "fastRenameValue", "getFastRenameValue()Z", 0)
         )
      );
      $$delegatedProperties = var0;
   }
}
