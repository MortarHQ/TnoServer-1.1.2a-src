package org.anti_ad.mc.ipnext.gui;

import java.util.Optional;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.PropertyReference0Impl;
import kotlin.jvm.internal.SourceDebugExtension;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.component.PatchedDataComponentMap;
import net.minecraft.world.item.alchemy.Potion;
import net.minecraft.world.item.alchemy.PotionContents;
import org.anti_ad.mc.ipnext.item.ItemType;
import org.anti_ad.mc.ipnext.item.ItemTypeExtensionsKt;

// $VF: synthetic class
@SourceDebugExtension(
   "SMAP\nDebugScreen.kt\nKotlin\n*S Kotlin\n*F\n+ 1 DebugScreen.kt\norg/anti_ad/mc/ipnext/gui/DebugScreen$PageContainer$content$d$1$17\n+ 2 ItemTypeExtensions.kt\norg/anti_ad/mc/ipnext/item/ItemTypeExtensionsKt\n*L\n1#1,223:1\n405#2:224\n402#2:225\n*S KotlinDebug\n*F\n+ 1 DebugScreen.kt\norg/anti_ad/mc/ipnext/gui/DebugScreen$PageContainer$content$d$1$17\n*L\n98#1:224\n98#1:225\n*E\n"
)
final class DebugScreen$PageContainer$content$d$1$17 extends PropertyReference0Impl {
   DebugScreen$PageContainer$content$d$1$17(Object var1) {
      super(var1, ItemTypeExtensionsKt.class, "potionName", "getPotionName(Lorg/anti_ad/mc/ipnext/item/ItemType;)Ljava/lang/String;", 1);
   }

   public final Object get() {
      ItemType var1;
      if ((var1 = (ItemType)this.receiver).getTag() != null) {
         PatchedDataComponentMap var10000 = var1.getTag();
         if (var10000 != null ? var10000.has(DataComponents.POTION_CONTENTS) : false) {
            label18: {
               PotionContents var2 = (PotionContents)var1.getTag().get(DataComponents.POTION_CONTENTS);
               if (var2 != null) {
                  var3 = var2.potion();
                  if (var3 != null) {
                     break label18;
                  }
               }

               var3 = Optional.empty();
            }

            String var4 = Potion.getName(var3, "");
            Intrinsics.checkNotNullExpressionValue(var4, "");
            return var4;
         }
      }

      return "";
   }
}
