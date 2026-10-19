package org.anti_ad.mc.ipnext.gui;

import java.util.List;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.SourceDebugExtension;
import kotlin.reflect.KProperty0;
import kotlin.text.StringsKt;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.core.component.DataComponentMap;
import net.minecraft.core.component.DataComponentPatch;
import net.minecraft.core.component.PatchedDataComponentMap;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import org.anti_ad.mc.common.gui.widgets.Page;
import org.anti_ad.mc.ipnext.ingame.InventoryKt;
import org.anti_ad.mc.ipnext.ingame.VanillaAccessorsKt$(itemType)$1;
import org.anti_ad.mc.ipnext.item.ItemStack$Companion;
import org.anti_ad.mc.ipnext.item.ItemStackExtensionsKt;
import org.anti_ad.mc.ipnext.item.ItemType;
import org.anti_ad.mc.ipnext.item.ItemTypeExtensionsKt$EMPTY$1;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@SourceDebugExtension(
   "SMAP\nDebugScreen.kt\nKotlin\n*S Kotlin\n*F\n+ 1 DebugScreen.kt\norg/anti_ad/mc/ipnext/gui/DebugScreen$PageContainer\n+ 2 VanillaAccessors.kt\norg/anti_ad/mc/ipnext/ingame/VanillaAccessorsKt\n+ 3 ItemTypeExtensions.kt\norg/anti_ad/mc/ipnext/item/ItemTypeExtensionsKt\n*L\n1#1,223:1\n117#2:224\n111#2,7:225\n88#2:232\n71#2:233\n66#2,7:234\n86#2:245\n84#2:246\n97#2:247\n99#2,3:248\n88#3,4:241\n*S KotlinDebug\n*F\n+ 1 DebugScreen.kt\norg/anti_ad/mc/ipnext/gui/DebugScreen$PageContainer\n*L\n111#1:224\n113#1:225,7\n115#1:232\n115#1:233\n115#1:234,7\n123#1:245\n123#1:246\n124#1:247\n125#1:248,3\n115#1:241,4\n*E\n"
)
public final class DebugScreen$PageContainer extends Page {
   public DebugScreen$PageContainer(DebugScreen var1) {
      super("Container");
      this.this$0 = var1;
   }

   @NotNull
   public final List getContent() {
      Slot var1;
      String var2 = Intrinsics.areEqual(var1 = this.getSlot(), this.getSlot2())
         ? this.getContent(var1)
         : this.getContent(var1) + "\n" + this.getContent(this.getSlot2());
      if (var1 == null) {
         return CollectionsKt.listOf(var2);
      }

      ItemType var5;
      String var3 = "itemType: " + (var5 = this.getItemType());
      KProperty0[] var4;
      (var4 = new KProperty0[23])[0] = new DebugScreen$PageContainer$content$d$1$1(var5);
      var4[1] = new DebugScreen$PageContainer$content$d$1$2(var5);
      var4[2] = new DebugScreen$PageContainer$content$d$1$3(var5);
      var4[3] = new DebugScreen$PageContainer$content$d$1$4(var5);
      var4[4] = new DebugScreen$PageContainer$content$d$1$5(var5);
      var4[5] = new DebugScreen$PageContainer$content$d$1$6(var5);
      var4[6] = new DebugScreen$PageContainer$content$d$1$7(var5);
      var4[7] = new DebugScreen$PageContainer$content$d$1$8(var5);
      var4[8] = new DebugScreen$PageContainer$content$d$1$9(var5);
      var4[9] = new DebugScreen$PageContainer$content$d$1$10(var5);
      var4[10] = new DebugScreen$PageContainer$content$d$1$11(var5);
      var4[11] = new DebugScreen$PageContainer$content$d$1$12(var5);
      var4[12] = new DebugScreen$PageContainer$content$d$1$13(var5);
      var4[13] = new DebugScreen$PageContainer$content$d$1$14(var5);
      var4[14] = new DebugScreen$PageContainer$content$d$1$15(var5);
      var4[15] = new DebugScreen$PageContainer$content$d$1$16(var5);
      var4[16] = new DebugScreen$PageContainer$content$d$1$17(var5);
      var4[17] = new DebugScreen$PageContainer$content$d$1$18(var5);
      var4[18] = new DebugScreen$PageContainer$content$d$1$19(var5);
      var4[19] = new DebugScreen$PageContainer$content$d$1$20(var5);
      var4[20] = new DebugScreen$PageContainer$content$d$1$21(var5);
      var4[21] = new DebugScreen$PageContainer$content$d$1$22(var5);
      var4[22] = new DebugScreen$PageContainer$content$d$1$23(var5);
      String var6 = CollectionsKt.joinToString$default(
         CollectionsKt.listOf(var4), "\n", null, null, 0, null, DebugScreen$PageContainer::_get_content_$lambda$1$lambda$0, 30, null
      );
      String[] var7;
      (var7 = new String[3])[0] = var2;
      var7[1] = var3;
      var7[2] = var6;
      return StringsKt.lines(CollectionsKt.joinToString$default(CollectionsKt.listOf(var7), "\n", null, null, 0, null, null, 62, null));
   }

   @Nullable
   public final Slot getSlot() {
      Screen var1;
      AbstractContainerScreen var10000 = (var1 = this.this$0.getParent()) instanceof AbstractContainerScreen ? (AbstractContainerScreen)var1 : null;
      return var10000 != null ? var10000.getSlotUnderMouse() : null;
   }

   @Nullable
   public final Slot getSlot2() {
      Screen var10000 = this.this$0.getParent();
      if (var10000 != null) {
         Screen var1 = var10000;
         AbstractContainerScreen var2 = var10000 instanceof AbstractContainerScreen ? (AbstractContainerScreen)var1 : null;
         if ((var10000 instanceof AbstractContainerScreen ? (AbstractContainerScreen)var1 : null) != null) {
            Slot var3 = var2.getSlotUnderMouse();
            if (var3 != null) {
               return InventoryKt.vPlayerSlotOf(var3, var1);
            }
         }

         return null;
      } else {
         return null;
      }
   }

   @NotNull
   public final ItemType getItemType() {
      Slot var10000 = this.getSlot();
      if (var10000 != null) {
         ItemStack var3 = var10000.getItem();
         Intrinsics.checkNotNullExpressionValue(var3, "");
         ItemStack var1 = var3;
         org.anti_ad.mc.ipnext.item.ItemStack var4;
         if (var3.isEmpty()) {
            var4 = ItemStackExtensionsKt.getEMPTY(org.anti_ad.mc.ipnext.item.ItemStack.Companion);
         } else {
            ItemStack$Companion var5 = org.anti_ad.mc.ipnext.item.ItemStack.Companion;
            ItemStack var2 = var1;
            Item var10003 = var2.getItem();
            Intrinsics.checkNotNullExpressionValue(var10003, "");
            DataComponentMap var10004 = var2.getComponents();
            Intrinsics.checkNotNull(var10004);
            var4 = var5.invoke(
               new ItemType(
                  var10003,
                  (PatchedDataComponentMap)var10004,
                  var2.getComponentsPatch(),
                  new VanillaAccessorsKt$(itemType)$1(var2),
                  false,
                  false,
                  null,
                  112,
                  null
               ),
               var1.getCount()
            );
         }

         if (var4 != null) {
            ItemType var6 = var4.getItemType();
            if (var6 != null) {
               return var6;
            }
         }
      }

      Item var10002 = Items.AIR;
      Intrinsics.checkNotNullExpressionValue(Items.AIR, "");
      return new ItemType(
         var10002,
         new PatchedDataComponentMap(Items.AIR.components()),
         DataComponentPatch.EMPTY,
         ItemTypeExtensionsKt$EMPTY$1.INSTANCE,
         false,
         false,
         null,
         112,
         null
      );
   }

   @NotNull
   public final String getContent(@Nullable Slot var1) {
      String var4;
      label25: {
         if (var1 != null) {
            Class var10000 = var1.getClass();
            if (var10000 != null) {
               var4 = var10000.getCanonicalName();
               break label25;
            }
         }

         var4 = null;
      }

      String var2 = "slot: " + var4;
      if (var1 == null) {
         return var2;
      }

      String var6;
      label19: {
         var5 = var1.getSlotIndex();
         if (var1.container != null) {
            Class var10002 = var1.container.getClass();
            if (var10002 != null) {
               var6 = var10002.getCanonicalName();
               break label19;
            }
         }

         var6 = null;
      }

      String var3 = StringsKt.trimMargin$default(
         "invSlot: "
            + var5
            + " id: "
            + var1.index
            + "\n              |inventory: "
            + var6
            + "\n              |x: "
            + var1.x
            + " y: "
            + var1.y
            + "\n              |\n              ",
         null,
         1,
         null
      );
      return var2 + "\n" + var3;
   }

   private static final CharSequence _get_content_$lambda$1$lambda$0(KProperty0 var0) {
      Intrinsics.checkNotNullParameter(var0, "");
      return var0.getName() + ": " + var0.get();
   }
}
