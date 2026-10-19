package org.anti_ad.mc.ipnext.event;

import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.SourceDebugExtension;
import net.minecraft.core.component.DataComponentMap;
import net.minecraft.core.component.PatchedDataComponentMap;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.Item;
import org.anti_ad.mc.ipnext.ingame.VanillaAccessorsKt$(itemType)$1;
import org.anti_ad.mc.ipnext.item.ItemStack;
import org.anti_ad.mc.ipnext.item.ItemStack$Companion;
import org.anti_ad.mc.ipnext.item.ItemStackExtensionsKt;
import org.anti_ad.mc.ipnext.item.ItemType;
import org.jetbrains.annotations.NotNull;

@SourceDebugExtension(
   "SMAP\nContinuousCraftingHandler.kt\nKotlin\n*S Kotlin\n*F\n+ 1 ContinuousCraftingHandler.kt\norg/anti_ad/mc/ipnext/event/ContinuousCraftingHandler$ItemSlotMonitorP\n+ 2 VanillaAccessors.kt\norg/anti_ad/mc/ipnext/ingame/VanillaAccessorsKt\n*L\n1#1,367:1\n88#2:368\n71#2:369\n66#2,7:370\n*S KotlinDebug\n*F\n+ 1 ContinuousCraftingHandler.kt\norg/anti_ad/mc/ipnext/event/ContinuousCraftingHandler$ItemSlotMonitorP\n*L\n363#1:368\n363#1:369\n363#1:370,7\n*E\n"
)
final class ContinuousCraftingHandler$ItemSlotMonitorP implements ContinuousCraftingHandler$IItemSlotMonitor {
   @NotNull
   private final Slot slot;
   @NotNull
   private ItemStack storedItem;

   public ContinuousCraftingHandler$ItemSlotMonitorP(@NotNull Slot var1) {
      Intrinsics.checkNotNullParameter(var1, "");
      super();
      this.slot = var1;
      this.storedItem = ItemStackExtensionsKt.getEMPTY(ItemStack.Companion);
   }

   @NotNull
   @Override
   public final Slot getSlot() {
      return this.slot;
   }

   @NotNull
   @Override
   public final ItemStack getStoredItem() {
      return this.storedItem;
   }

   @Override
   public final void setStoredItem(@NotNull ItemStack var1) {
      Intrinsics.checkNotNullParameter(var1, "");
      this.storedItem = var1;
   }

   @Override
   public final void save() {
      net.minecraft.world.item.ItemStack var10001 = this.getSlot().getItem();
      Intrinsics.checkNotNullExpressionValue(var10001, "");
      net.minecraft.world.item.ItemStack var1 = var10001;
      ItemStack var3;
      if (var10001.isEmpty()) {
         var3 = ItemStackExtensionsKt.getEMPTY(ItemStack.Companion);
      } else {
         ItemStack$Companion var4 = ItemStack.Companion;
         net.minecraft.world.item.ItemStack var2 = var1;
         Item var10004 = var2.getItem();
         Intrinsics.checkNotNullExpressionValue(var10004, "");
         DataComponentMap var10005 = var2.getComponents();
         Intrinsics.checkNotNull(var10005);
         var3 = var4.invoke(
            new ItemType(
               var10004, (PatchedDataComponentMap)var10005, var2.getComponentsPatch(), new VanillaAccessorsKt$(itemType)$1(var2), false, false, null, 112, null
            ),
            var1.getCount()
         );
      }

      this.setStoredItem(var3);
   }
}
