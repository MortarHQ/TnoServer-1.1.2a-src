package org.anti_ad.mc.ipnext.gui;

import java.util.ArrayList;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.PropertyReference0Impl;
import kotlin.jvm.internal.SourceDebugExtension;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.component.PatchedDataComponentMap;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.item.alchemy.PotionContents;
import org.anti_ad.mc.ipnext.item.ItemType;
import org.anti_ad.mc.ipnext.item.ItemTypeExtensionsKt;
import org.anti_ad.mc.ipnext.item.PotionEffect;

// $VF: synthetic class
@SourceDebugExtension(
   "SMAP\nDebugScreen.kt\nKotlin\n*S Kotlin\n*F\n+ 1 DebugScreen.kt\norg/anti_ad/mc/ipnext/gui/DebugScreen$PageContainer$content$d$1$19\n+ 2 ItemTypeExtensions.kt\norg/anti_ad/mc/ipnext/item/ItemTypeExtensionsKt\n+ 3 _Collections.kt\nkotlin/collections/CollectionsKt___CollectionsKt\n*L\n1#1,223:1\n417#2:224\n414#2:225\n421#2,3:229\n1563#3:226\n1634#3,2:227\n1636#3:232\n*S KotlinDebug\n*F\n+ 1 DebugScreen.kt\norg/anti_ad/mc/ipnext/gui/DebugScreen$PageContainer$content$d$1$19\n*L\n100#1:224\n100#1:225\n100#1:229,3\n100#1:226\n100#1:227,2\n100#1:232\n*E\n"
)
final class DebugScreen$PageContainer$content$d$1$19 extends PropertyReference0Impl {
   DebugScreen$PageContainer$content$d$1$19(Object var1) {
      super(var1, ItemTypeExtensionsKt.class, "comparablePotionEffects", "getComparablePotionEffects(Lorg/anti_ad/mc/ipnext/item/ItemType;)Ljava/util/List;", 1);
   }

   public final Object get() {
      Iterable var10;
      label24: {
         PatchedDataComponentMap var10000 = ((ItemType)this.receiver).getTag();
         if (var10000 != null) {
            PotionContents var8 = (PotionContents)var10000.get(DataComponents.POTION_CONTENTS);
            if (var8 != null) {
               var10 = var8.getAllEffects();
               if (var10 != null) {
                  var10 = CollectionsKt.toList(var10);
                  if (var10 != null) {
                     break label24;
                  }
               }
            }
         }

         var10 = CollectionsKt.emptyList();
      }

      Iterable var1;
      Iterable var2 = var1 = var10;
      var1 = new ArrayList(CollectionsKt.collectionSizeOrDefault(var1, 10));

      for (MobEffectInstance var3 : var2) {
         var3 = var3;
         ArrayList var4 = var1;
         var4.add(new PotionEffect(String.valueOf(BuiltInRegistries.MOB_EFFECT.getKey(var3.getEffect().value())), var3.getAmplifier(), var3.getDuration()));
      }

      return var1;
   }
}
