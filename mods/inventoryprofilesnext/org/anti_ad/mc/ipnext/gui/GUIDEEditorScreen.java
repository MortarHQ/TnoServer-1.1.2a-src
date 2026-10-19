package org.anti_ad.mc.ipnext.gui;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import kotlin.Pair;
import kotlin.TuplesKt;
import kotlin.Unit;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.SourceDebugExtension;
import kotlin.jvm.internal.Ref.IntRef;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.world.inventory.AbstractContainerMenu;
import org.anti_ad.mc.alias.client.gui.screen.ingame.InGameExKt;
import org.anti_ad.mc.alias.screen.ScreenExKt;
import org.anti_ad.mc.common.config.IConfigOption;
import org.anti_ad.mc.common.gui.NativeContext;
import org.anti_ad.mc.common.gui.layout.Overflow;
import org.anti_ad.mc.common.gui.layout.WidgetExtensionsKt;
import org.anti_ad.mc.common.gui.screen.BaseOverlay;
import org.anti_ad.mc.common.gui.widgets.ConfigListWidget;
import org.anti_ad.mc.common.gui.widgets.HudLabeledText;
import org.anti_ad.mc.common.gui.widgets.SizeChangedEvent;
import org.anti_ad.mc.common.gui.widgets.Widget;
import org.anti_ad.mc.common.input.KeybindSettings;
import org.anti_ad.mc.common.input.MainKeybind;
import org.anti_ad.mc.common.math2d.Rectangle;
import org.anti_ad.mc.common.vanilla.alias.glue.I18n;
import org.anti_ad.mc.common.vanilla.render.ColorKt;
import org.anti_ad.mc.common.vanilla.render.ScreenKt;
import org.anti_ad.mc.common.vanilla.render.glue.RectKt;
import org.anti_ad.mc.ipnext.config.Debugs;
import org.anti_ad.mc.ipnext.gui.inject.base.InsertableWidget;
import org.anti_ad.mc.ipnext.gui.widgets.Hintable;
import org.anti_ad.mc.ipnext.integration.HintClassData;
import org.anti_ad.mc.ipnext.integration.HintsManagerNG;
import org.anti_ad.mc.ipnext.inventory.ContainerType;
import org.anti_ad.mc.ipnext.inventory.ContainerTypes;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@SourceDebugExtension(
   "SMAP\nGUIDEEditorScreen.kt\nKotlin\n*S Kotlin\n*F\n+ 1 GUIDEEditorScreen.kt\norg/anti_ad/mc/ipnext/gui/GUIDEEditorScreen\n+ 2 _Collections.kt\nkotlin/collections/CollectionsKt___CollectionsKt\n+ 3 VanillaAccessors.kt\norg/anti_ad/mc/ipnext/ingame/VanillaAccessorsKt\n+ 4 I18n.kt\norg/anti_ad/mc/common/vanilla/alias/glue/I18n\n*L\n1#1,473:1\n1869#2,2:474\n1869#2,2:476\n1869#2,2:478\n119#3,4:480\n119#3,4:492\n42#4,2:484\n42#4,2:486\n42#4,2:488\n42#4,2:490\n*S KotlinDebug\n*F\n+ 1 GUIDEEditorScreen.kt\norg/anti_ad/mc/ipnext/gui/GUIDEEditorScreen\n*L\n226#1:474,2\n233#1:476,2\n242#1:478,2\n258#1:480,4\n264#1:492,4\n275#1:484,2\n283#1:486,2\n231#1:488,2\n254#1:490,2\n*E\n"
)
public final class GUIDEEditorScreen extends BaseOverlay {
   @NotNull
   public static final GUIDEEditorScreen$Companion Companion = new GUIDEEditorScreen$Companion(null);
   @NotNull
   private final Screen target;
   @NotNull
   private final AbstractContainerMenu container;
   private int selectedIndex;
   @Nullable
   private Hintable activeHintable;
   @NotNull
   private List allHintable;
   private int leftLongPress;
   private int rightLongPress;
   private int upLongPress;
   private int downLongPress;
   @NotNull
   private final HintClassData screenHints;
   @NotNull
   private final HintClassData containerHints;
   private final boolean isVanillaContainer;
   private final boolean isVanillaScreen;
   @NotNull
   private final List infoStrings;
   @NotNull
   private final List helpStrings;
   @NotNull
   private static final String TRANSLATE_ROOT = "inventoryprofiles.gui.editor";
   @NotNull
   public static final String NAME_ROOT = "inventoryprofiles.gui.editor.name";
   @NotNull
   public static final String DESCRIPTION_ROOT = "inventoryprofiles.gui.editor.description";
   @NotNull
   public static final String CATEGORY_ROOT = "inventoryprofiles.gui.editor.category";
   @NotNull
   private static final MainKeybind SHIFT = new MainKeybind("LEFT_SHIFT", KeybindSettings.Companion.getGUI_EXTRA());

   public GUIDEEditorScreen(@NotNull Screen var1, @NotNull AbstractContainerMenu var2, @NotNull List var3) {
      Intrinsics.checkNotNullParameter(var1, "");
      Intrinsics.checkNotNullParameter(var2, "");
      Intrinsics.checkNotNullParameter(var3, "");
      super();
      this.target = var1;
      this.container = var2;
      this.allHintable = new ArrayList();
      this.screenHints = HintsManagerNG.INSTANCE.getHints(this.target.getClass());
      this.containerHints = HintsManagerNG.INSTANCE.getHints(this.container.getClass());
      this.isVanillaContainer = ScreenExKt.getVanillaContainers().contains(this.container.getClass());
      this.isVanillaScreen = InGameExKt.getVanillaScreens().contains(this.target.getClass());
      Pair[] var10;
      (var10 = new Pair[2])[0] = TuplesKt.to("inventoryprofiles.gui.editor.name.class.screen", this.target.getClass().getName());
      var10[1] = TuplesKt.to("inventoryprofiles.gui.editor.name.class.container", this.container.getClass().getName());
      this.infoStrings = CollectionsKt.mutableListOf(var10);
      String[] var11;
      (var11 = new String[4])[0] = "help.select.prev";
      var11[1] = "help.select.next";
      var11[2] = "help.move.speed";
      var11[3] = "help.move.keys";
      this.helpStrings = CollectionsKt.mutableListOf(var11);
      this.setBlurEnabled(false);
      WidgetExtensionsKt.fillParent((Widget)this.getRootWidget());

      for (InsertableWidget var4 : var3) {
         this.allHintable.addAll(var4.getHintableList());
      }

      selectWidgets$default(this, false, 1, null);
      IntRef var12;
      (var12 = new IntRef()).element = 5;
      Function1 var15 = GUIDEEditorScreen::_init_$lambda$2;

      for (Pair var5 : this.infoStrings) {
         String var10003 = (String)var15.invoke(var5.getFirst());
         Object var10004 = var5.getSecond();
         Intrinsics.checkNotNullExpressionValue(var10004, "");
         HudLabeledText var17;
         HudLabeledText var6 = var17 = new HudLabeledText(var10003, (String)var10004);
         GUIDEEditorScreen var7 = this;
         WidgetExtensionsKt.setTopRight((Widget)var6, var12.element, 5);
         var12.element += 10;
         var7.addWidget((Widget)var17);
      }

      byte var18 = 5;

      for (String var23 : this.helpStrings) {
         String var26 = (String)var15.invoke("inventoryprofiles.gui.editor.name." + var23);
         String var30 = (String)var15.invoke("inventoryprofiles.gui.editor.description." + var23);
         HudLabeledText var27;
         HudLabeledText var8 = var27 = new HudLabeledText(var26, var30);
         GUIDEEditorScreen var9 = this;
         WidgetExtensionsKt.setBottomLeft((Widget)var8, var18, 5);
         var18 += 10;
         var9.addWidget((Widget)var27);
      }

      var12.element += 3;
      ConfigListWidget var22;
      (var22 = new ConfigListWidget(GUIDEEditorScreen::_init_$lambda$8, GUIDEEditorScreen::_init_$lambda$9, 3))
         .setHeight(ScreenKt.getRScreenHeight() - var12.element - 5);
      Screen var10000 = this.target;
      Intrinsics.checkNotNull(this.target);
      AbstractContainerScreen var24 = (AbstractContainerScreen)var10000;
      Rectangle var19 = new Rectangle(var24.getGuiLeft(), var24.getGuiTop(), var24.getXSize(), var24.getYSize());
      var22.setWidth(ScreenKt.getRScreenWidth() - var19.getWidth() - var19.getX() - 3);
      this.addWidget((Widget)var22);
      var22.setOverflow(Overflow.VISIBLE);
      this.getRootWidget().getSizeChanged().plusAssign(GUIDEEditorScreen::lambda$14$lambda$10);
      var22.getAnchorHeader().setVisible(false);
      var22.getContainer().setScrollWheelAmount(14);
      var22.setRenderBorder(false);
      WidgetExtensionsKt.setTopRight((Widget)var22, var12.element, 0);
      GUIDEEditorScreen$Options var25 = new GUIDEEditorScreen$Options(this);
      Set var31 = ContainerTypes.getTypes$default(ContainerTypes.INSTANCE, this.container, null, 2, null);
      ConfigListWidget var36 = var22;
      I18n var28 = I18n.INSTANCE;
      String var32 = "inventoryprofiles.gui.editor.category.screen";
      Object[] var34 = new Object[0];
      String var10001 = var28.translateOrNull(var32, Arrays.copyOf(var34, var34.length));
      if (var10001 == null) {
         ConfigListWidget var13 = var22;
         var10001 = "Screen";
         var36 = var13;
      }

      var36.addCategory(var10001);
      var22.addConfigOption((IConfigOption)var25.getScreenIgnoreOption());
      var22.addConfigOption((IConfigOption)var25.getScreenPlayerSideOption());
      var22.addConfigOption((IConfigOption)var25.getScreenDisableSwipe());
      var22.addConfigOption((IConfigOption)var25.getScreenForceOption());
      if (var31.contains(ContainerType.PLAYER)) {
         var22.addConfigOption((IConfigOption)var25.getScreenShowProfileSelectorOption());
      }

      ConfigListWidget var37 = var22;
      var28 = I18n.INSTANCE;
      var32 = "inventoryprofiles.gui.editor.category.container";
      var34 = new Object[0];
      var10001 = var28.translateOrNull(var32, Arrays.copyOf(var34, var34.length));
      if (var10001 == null) {
         ConfigListWidget var14 = var22;
         var10001 = "Container";
         var37 = var14;
      }

      var37.addCategory(var10001);
      var22.addConfigOption((IConfigOption)var25.getContainerIgnoreOption());
      var22.addConfigOption((IConfigOption)var25.getContainerPlayerSideOption());
      var22.addConfigOption((IConfigOption)var25.getContainerForceOption());
      this.addWidget(new GUIDEEditorScreen$7());
   }

   public final void onTick() {
      updateHints$default(this, 0, 1, null);
   }

   private final void updateHints(int var1) {
      int var2 = this.leftLongPress;
      if (this.leftLongPress > 0 ? var2 < 10 : false) {
         var2 = this.leftLongPress++;
      }

      var2 = this.rightLongPress;
      if (this.rightLongPress > 0 ? var2 < 10 : false) {
         var2 = this.rightLongPress++;
      }

      var2 = this.upLongPress;
      if (this.upLongPress > 0 ? var2 < 10 : false) {
         var2 = this.upLongPress++;
      }

      var2 = this.downLongPress;
      if (this.downLongPress > 0 ? var2 < 10 : false) {
         var2 = this.downLongPress++;
      }

      var2 = SHIFT.isPressing() ? 10 : 1;
      if (this.leftLongPress >= var1) {
         if (this.activeHintable != null) {
            this.activeHintable.moveLeft(var2);
         }
      }

      if (this.rightLongPress >= var1) {
         if (this.activeHintable != null) {
            this.activeHintable.moveRight(var2);
         }
      }

      if (this.upLongPress >= var1) {
         if (this.activeHintable != null) {
            this.activeHintable.moveUp(var2);
         }
      }

      if (this.downLongPress >= var1 && this.activeHintable != null) {
         this.activeHintable.moveDown(var2);
      }
   }

   public final boolean keyReleased(int var1, int var2, int var3) {
      boolean var4;
      if (!(var4 = super.keyReleased(var1, var2, var3))) {
         var3 = SHIFT.isPressing() ? 10 : 1;
         switch (var1) {
            case 32:
               this.selectWidgets(!SHIFT.isPressing());
               break;
            case 65:
            case 263:
               if (this.activeHintable != null) {
                  this.activeHintable.moveLeft(var3);
               }

               this.leftLongPress = 0;
               break;
            case 68:
            case 262:
               if (this.activeHintable != null) {
                  this.activeHintable.moveRight(var3);
               }

               this.rightLongPress = 0;
               break;
            case 83:
            case 264:
               if (this.activeHintable != null) {
                  this.activeHintable.moveDown(var3);
               }

               this.downLongPress = 0;
               break;
            case 87:
            case 265:
               if (this.activeHintable != null) {
                  this.activeHintable.moveUp(var3);
               }

               this.upLongPress = 0;
               break;
            default:
               return false;
         }
      }

      return var4;
   }

   public final boolean keyPressed(int var1, int var2, int var3) {
      boolean var4;
      if (!(var4 = super.keyPressed(var1, var2, var3))) {
         switch (var1) {
            case 65:
            case 263:
               if (this.leftLongPress <= 0) {
                  this.leftLongPress = 1;
               }
               break;
            case 68:
            case 262:
               if (this.rightLongPress <= 0) {
                  this.rightLongPress = 1;
               }
               break;
            case 83:
            case 264:
               if (this.downLongPress <= 0) {
                  this.downLongPress = 1;
               }
               break;
            case 87:
            case 265:
               if (this.upLongPress <= 0) {
                  this.upLongPress = 1;
               }
               break;
            default:
               return false;
         }
      }

      return var4;
   }

   private final void selectWidgets(boolean var1) {
      if (this.activeHintable != null) {
         this.activeHintable.setUnderManagement(false);
      }

      GUIDEEditorScreen$Companion.access$whenAtLeastOneVisible(Companion, this.allHintable, GUIDEEditorScreen::selectWidgets$lambda$15);
      if (this.activeHintable != null) {
         this.activeHintable.setUnderManagement(true);
      }
   }

   public final void render(@NotNull NativeContext var1, int var2, int var3, float var4) {
      Intrinsics.checkNotNullParameter(var1, "");
      super.render(var1, var2, var3, var4);
      if (Debugs.INSTANCE.getDEBUG_RENDER().getBooleanValue()) {
         RectKt.rDrawOutline(var1, this.getRootWidget().getAbsoluteBounds(), ColorKt.getOpaque(16711935));
      }
   }

   public final void closeScreen() {
      if (this.screenHints.dirty() || this.containerHints.dirty() || this.screenHints.areButtonsMoved()) {
         String var1 = this.screenHints.readId();
         String var2 = this.containerHints.readId();
         if (var1 == null) {
            String var10000 = var2;
            if (var2 == null) {
               var10000 = "player-defined";
            }

            var1 = var10000;
         }

         if (var2 == null) {
            var2 = var1;
         }

         this.screenHints.changeId(var1);
         this.containerHints.changeId(var2);
         HintsManagerNG.INSTANCE.saveDirty(this.screenHints, this.containerHints);
         ContainerTypes.INSTANCE.reset();
      }

      super.closeScreen();
   }

   private static final String _init_$lambda$2(String var0) {
      Intrinsics.checkNotNullParameter(var0, "");
      I18n var1 = I18n.INSTANCE;
      Object[] var2 = new Object[0];
      String var10000 = var1.translateOrNull(var0, Arrays.copyOf(var2, var2.length));
      if (var10000 == null) {
         var10000 = var0;
      }

      return var10000;
   }

   private static final String _init_$lambda$8(String var0) {
      Intrinsics.checkNotNullParameter(var0, "");
      I18n var1 = I18n.INSTANCE;
      String var2 = "inventoryprofiles.gui.editor.name." + var0;
      Object[] var3 = new Object[0];
      String var10000 = var1.translateOrNull(var2, Arrays.copyOf(var3, var3.length));
      if (var10000 == null) {
         var10000 = var0;
      }

      return var10000;
   }

   private static final String _init_$lambda$9(String var0) {
      Intrinsics.checkNotNullParameter(var0, "");
      return I18n.INSTANCE.translateOrEmpty("inventoryprofiles.gui.editor.description." + var0, new Object[0]);
   }

   private static final Unit lambda$14$lambda$10(GUIDEEditorScreen var0, ConfigListWidget var1, IntRef var2, SizeChangedEvent var3) {
      Intrinsics.checkNotNullParameter(var3, "");
      AbstractContainerScreen var4 = (AbstractContainerScreen)var0.target;
      Rectangle var5 = new Rectangle(var4.getGuiLeft(), var4.getGuiTop(), var4.getXSize(), var4.getYSize());
      var1.setHeight(ScreenKt.getRScreenHeight() - var2.element - 5);
      var1.setWidth(ScreenKt.getRScreenWidth() - var5.getWidth() - var5.getX() - 3);
      WidgetExtensionsKt.setTopRight((Widget)var1, var2.element, 0);
      return Unit.INSTANCE;
   }

   private static final Unit selectWidgets$lambda$15(boolean var0, GUIDEEditorScreen var1) {
      if (!var0) {
         do {
            var0 = var1.selectedIndex;
            var1.selectedIndex = var0 + -1;
            if (var1.selectedIndex <= 0) {
               var1.selectedIndex = var1.allHintable.size() - 1;
            }

            var1.activeHintable = (Hintable)var1.allHintable.get(var1.selectedIndex);
         } while (!GUIDEEditorScreen$Companion.access$isVisible(Companion, var1.activeHintable));
      } else {
         do {
            var0 = var1.selectedIndex++;
            if (var1.selectedIndex >= var1.allHintable.size()) {
               var1.selectedIndex = 0;
            }

            var1.activeHintable = (Hintable)var1.allHintable.get(var1.selectedIndex);
         } while (!GUIDEEditorScreen$Companion.access$isVisible(Companion, var1.activeHintable));
      }

      return Unit.INSTANCE;
   }
}
