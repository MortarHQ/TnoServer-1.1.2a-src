package org.anti_ad.mc.ipnext.gui;

import java.util.List;
import kotlin.jvm.internal.PropertyReference0Impl;
import kotlin.jvm.internal.SourceDebugExtension;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.component.PatchedDataComponentMap;
import net.minecraft.world.item.alchemy.PotionContents;
import org.anti_ad.mc.ipnext.item.ItemType;
import org.anti_ad.mc.ipnext.item.ItemTypeExtensionsKt;

// $VF: synthetic class
@SourceDebugExtension(
   "SMAP\nDebugScreen.kt\nKotlin\n*S Kotlin\n*F\n+ 1 DebugScreen.kt\norg/anti_ad/mc/ipnext/gui/DebugScreen$PageContainer$content$d$1$15\n+ 2 ItemTypeExtensions.kt\norg/anti_ad/mc/ipnext/item/ItemTypeExtensionsKt\n*L\n1#1,223:1\n411#2:224\n*S KotlinDebug\n*F\n+ 1 DebugScreen.kt\norg/anti_ad/mc/ipnext/gui/DebugScreen$PageContainer$content$d$1$15\n*L\n96#1:224\n*E\n"
)
final class DebugScreen$PageContainer$content$d$1$15 extends PropertyReference0Impl {
   DebugScreen$PageContainer$content$d$1$15(Object var1) {
      super(var1, ItemTypeExtensionsKt.class, "hasCustomPotionEffects", "getHasCustomPotionEffects(Lorg/anti_ad/mc/ipnext/item/ItemType;)Z", 1);
   }

   public final Object get() {
      PatchedDataComponentMap var10000 = ((ItemType)this.receiver).getTag();
      if (var10000 != null) {
         PotionContents var1 = (PotionContents)var10000.get(DataComponents.POTION_CONTENTS);
         if (var1 != null) {
            List var2 = var1.customEffects();
            if (var2 != null) {
               return !var2.isEmpty();
            }
         }
      }

      return false;
   }
}
