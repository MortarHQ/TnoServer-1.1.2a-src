package org.anti_ad.mc.ipnext.profiles.config;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.SourceDebugExtension;
import kotlin.ranges.IntRange;
import kotlin.text.StringsKt;
import net.minecraft.resources.ResourceLocation;
import org.anti_ad.a.b.a.a.a.d.i;
import org.anti_ad.mc.alias.util.UtilExKt;
import org.anti_ad.mc.common.LogBase;
import org.anti_ad.mc.common.gen.ProfilesParser$ActiveSlotNameContext;
import org.anti_ad.mc.common.gen.ProfilesParser$ComponentContext;
import org.anti_ad.mc.common.gen.ProfilesParser$ComponentsContext;
import org.anti_ad.mc.common.gen.ProfilesParser$CustomNameContext;
import org.anti_ad.mc.common.gen.ProfilesParser$ItemDefContext;
import org.anti_ad.mc.common.gen.ProfilesParser$ProfileContext;
import org.anti_ad.mc.common.gen.ProfilesParser$ScriptContext;
import org.anti_ad.mc.common.gen.ProfilesParser$SlotDefContext;
import org.anti_ad.mc.ipnext.Log;
import org.anti_ad.mc.ipnext.item.NbtUtils;
import org.jetbrains.annotations.NotNull;

@SourceDebugExtension(
   "SMAP\nProfilesConfigParser.kt\nKotlin\n*S Kotlin\n*F\n+ 1 ProfilesConfigParser.kt\norg/anti_ad/mc/ipnext/profiles/config/ProfilesConfigParserKt\n+ 2 Log.kt\norg/anti_ad/mc/common/LogBase\n+ 3 _Collections.kt\nkotlin/collections/CollectionsKt___CollectionsKt\n*L\n1#1,292:1\n107#2,2:293\n107#2,2:296\n107#2,2:299\n109#2,2:303\n109#2,2:306\n109#2,2:309\n1869#3:295\n1869#3:298\n1869#3,2:301\n1870#3:305\n1870#3:308\n1869#3:311\n1869#3:312\n1869#3:313\n1563#3:314\n1634#3,3:315\n1870#3:318\n1870#3:319\n1870#3:320\n*S KotlinDebug\n*F\n+ 1 ProfilesConfigParser.kt\norg/anti_ad/mc/ipnext/profiles/config/ProfilesConfigParserKt\n*L\n65#1:293,2\n74#1:296,2\n77#1:299,2\n77#1:303,2\n74#1:306,2\n65#1:309,2\n66#1:295\n75#1:298\n78#1:301,2\n75#1:305\n66#1:308\n97#1:311\n99#1:312\n101#1:313\n104#1:314\n104#1:315,3\n101#1:318\n99#1:319\n97#1:320\n*E\n"
)
public final class ProfilesConfigParserKt {
   @NotNull
   private static Map slotIdToProfileSlotId = new LinkedHashMap();

   public static final void dump(@NotNull List var0) {
      Intrinsics.checkNotNullParameter(var0, "");
      Log.INSTANCE.trace("Loaded fallowing profiles...");
      Log.INSTANCE.clearIndent();
      LogBase var1;
      (var1 = Log.INSTANCE).indent();

      for (ProfileData var2 : var0) {
         Log.INSTANCE.trace(ProfilesConfigParserKt::dump$lambda$6$lambda$5$lambda$0);
         LogBase var3;
         (var3 = Log.INSTANCE).indent();

         for (ProfileSlot var4 : var2.getSlots()) {
            Log.INSTANCE.trace(var4.getId().name());
            LogBase var5;
            (var5 = Log.INSTANCE).indent();
            Iterator var9 = var4.getItems().iterator();

            while (var9.hasNext()) {
               ProfileItemData var6;
               String var10000 = (var6 = (ProfileItemData)var9.next()).getItemId();
               Object var10001 = var6.getComponents();
               if (var10001 == null) {
                  var10001 = "";
               }

               String var10 = var10000 + " -> " + var10001;
               Log.INSTANCE.trace(var10);
            }

            var5.unindent();
         }

         var3.unindent();
      }

      var1.unindent();
      Log.INSTANCE.clearIndent();
   }

   @NotNull
   public static final ResourceLocation get_asIdentifier_/* $VF was: get(asIdentifier) */(@NotNull String var0) {
      Intrinsics.checkNotNullParameter(var0, "");
      return UtilExKt.IdentifierOf(var0);
   }

   private static final List toProfileList(ProfilesParser$ScriptContext var0) {
      List var1;
      List var2 = var1 = new ArrayList();
      List var10000 = var0.profile();
      Intrinsics.checkNotNullExpressionValue(var10000, "");

      for (ProfilesParser$ProfileContext var3 : var10000) {
         String var10001 = var3.Id().getText();
         Intrinsics.checkNotNullExpressionValue(var10001, "");
         ProfileSlotId$Companion var10002 = ProfileSlotId.Companion;
         ProfilesParser$ActiveSlotNameContext var10003 = var3.activeSlotName();
         ProfileSlotId var51 = var10002.valueOfOrFAKE(var10003 != null ? var10003.getText() : null);
         List var4;
         List var5 = var4 = new ArrayList();
         ProfileSlotId var6 = var51;
         String var7 = var10001;
         List var8 = var2;
         var10000 = var3.slotsDef().slotDef();
         Intrinsics.checkNotNullExpressionValue(var10000, "");

         for (ProfilesParser$SlotDefContext var9 : var10000) {
            var10001 = var9.slotname().getText();
            Intrinsics.checkNotNullExpressionValue(var10001, "");
            ProfileSlotId var48 = ProfileSlotId.valueOf(var10001);
            List var10;
            List var11 = var10 = new ArrayList();
            ProfileSlotId var12 = var48;
            List var13 = var5;
            var10000 = var9.itemDef();
            Intrinsics.checkNotNullExpressionValue(var10000, "");

            for (ProfilesParser$ItemDefContext var14 : var10000) {
               label60: {
                  var10000 = var11;
                  var10001 = var14.itemName().NamespacedId().getText();
                  Intrinsics.checkNotNullExpressionValue(var10001, "");
                  var10001 = StringsKt.removeSurrounding(var10001, "\"");
                  ProfilesParser$CustomNameContext var52 = var14.itemName().customName();
                  if (var52 != null) {
                     i var53 = var52.STRING();
                     if (var53 != null) {
                        String var54 = var53.getText();
                        if (var54 != null) {
                           var55 = StringsKt.removeSurrounding(var54, "\"");
                           if (var55 != null) {
                              break label60;
                           }
                        }
                     }
                  }

                  var55 = "";
               }

               label77: {
                  ProfilesParser$ComponentsContext var56 = var14.components();
                  if (var56 != null) {
                     List var57 = var56.component();
                     if (var57 != null) {
                        Iterable var30 = var57;
                        String var15 = var55;
                        String var16 = var10001;
                        List var17 = var11;
                        Iterable var18 = var30;
                        var var31 = new ArrayList(CollectionsKt.collectionSizeOrDefault(var30, 10));

                        for (ProfilesParser$ComponentContext var19 : var18) {
                           ArrayList var20;
                           ResourceLocation var21;
                           label69: {
                              var19 = var19;
                              var20 = var31;
                              var21 = get_asIdentifier_/* $VF was: get(asIdentifier) */(StringsKt.removeSurrounding(var19.NamespacedId().toString(), "\""));
                              i var42 = var19.customName().STRING();
                              String var43 = var42 != null ? var42.getText() : null;
                              String var44 = var43 != null ? StringsKt.removeSurrounding(var43, "\"") : null;
                              if (var44 != null) {
                                 String var45 = StringsKt.replace$default(var44, "\\\"", "\"", false, 4, null);
                                 if (var45 != null) {
                                    var46 = StringsKt.replace$default(var45, "\\\\", "\\", false, 4, null);
                                    break label69;
                                 }
                              }

                              var46 = null;
                           }

                           if (var46 == null) {
                              var46 = "";
                           }

                           String var37 = var46;
                           var20.add(new ProfileComponentData(var21, NbtUtils.INSTANCE.parseNbtOrEmpty(var37)));
                        }

                        List var38 = var31;
                        var10000 = var17;
                        var10001 = var16;
                        var55 = var15;
                        var58 = var38;
                        break label77;
                     }
                  }

                  var58 = null;
               }

               List var32 = var58;
               String var33 = var55;
               String var34 = var10001;
               var10000.add(new ProfileItemData(var34, var33, var32));
            }

            List var28 = var10;
            ProfileSlotId var29 = var12;
            var13.add(new ProfileSlot(var29, var28));
         }

         List var24 = var4;
         ProfileSlotId var25 = var6;
         String var26 = var7;
         var8.add(new ProfileData(var26, var25, var24, false, 8, null));
      }

      return var1;
   }

   public static final int fromEnchantmentLevel(@NotNull String var0) {
      Intrinsics.checkNotNullParameter(var0, "");

      int var1;
      try {
         var1 = Integer.parseInt(StringsKt.substring(var0, new IntRange(0, var0.length() - 2)));
      } catch (NumberFormatException var3) {
         try {
            var4 = Integer.parseInt(var0);
         } catch (NumberFormatException var2) {
            var4 = 10;
         }

         var1 = var4;
      }

      return var1;
   }

   public static final void addSlotId(@NotNull ProfileSlotId var0, int var1) {
      Intrinsics.checkNotNullParameter(var0, "");
      Integer var2 = var1;
      slotIdToProfileSlotId.put(var2, var0);
   }

   public static final void main(@NotNull String[] var0) {
      Intrinsics.checkNotNullParameter(var0, "");
      String var1 = "profile Main activate HOT1\n\tHOT1\n\t\t\"minecraft:netherite_axe\" -> \"Enchantments\" : [{id:\"minecraft:efficiency\",lvl:5},{id:\"minecraft:silk_touch\",lvl:1},{id:\"minecraft:smite\",lvl:5},{id:\"minecraft:unbreaking\",lvl:3}]\n\t\t\"minecraft:diamond_axe\" -> \"Enchantments\" : [{id:\"minecraft:efficiency\",lvl:5},{id:\"minecraft:silk_touch\",lvl:1},{id:\"minecraft:smite\",lvl:5},{id:\"minecraft:unbreaking\",lvl:3}]\n\tHOT2\n\t\t\"minecraft:netherite_axe\" -> \"Enchantments\" : [{id:\"minecraft:efficiency\",lvl:5}]\n\t\t\"minecraft:diamond_axe\" -> \"Enchantments\" : [{id:\"minecraft:efficiency\",lvl:5},{id:\"minecraft:silk_touch\",lvl:1},{id:\"minecraft:smite\",lvl:5},{id:\"minecraft:unbreaking\",lvl:3}]\n\t\t\"minecraft:netherite_axe\"\n\t\t\"minecraft:diamond_axe\"\n\tHOT3\n\t\t\"minecraft:water_bucket\"\n\tHOT4\n\t\t\"minecraft:terracotta\"\n\tCHEST\n\t\t\"minecraft:netherite_chestplate\"\n\tOFFHAND\n\t\t\"minecraft:shield\" -> \"Enchantments\" : [{id:\"minecraft:unbreaking\",lvl:3},{id:\"minecraft:mending\",lvl:1}]\n\tLEGS\n\t\t\"minecraft:netherite_leggings\" -> \"Enchantments\" : [{id:\"minecraft:fire_protection\",lvl:3},{id:\"minecraft:unbreaking\",lvl:3}]\n\tFEET\n\t\t\"minecraft:netherite_boots\" -> \"Enchantments\" : [{id:\"minecraft:protection\",lvl:4},{id:\"minecraft:unbreaking\",lvl:3}]\n\nprofile PvP activate HOT5\n\tHOT3\n\t\t\"minecraft:netherite_axe\" -> \"Enchantments\" : [{id:\"minecraft:efficiency\",lvl:5},{id:\"minecraft:silk_touch\",lvl:1},{id:\"minecraft:smite\",lvl:5},{id:\"minecraft:unbreaking\",lvl:3}]\n\t\t\"minecraft:diamond_axe\" -> \"Enchantments\" : [{id:\"minecraft:efficiency\",lvl:5},{id:\"minecraft:silk_touch\",lvl:1},{id:\"minecraft:smite\",lvl:5},{id:\"minecraft:unbreaking\",lvl:3}]\n\tHOT5\n\t\t\"minecraft:netherite_axe\" -> \"Enchantments\" : [{id:\"minecraft:efficiency\",lvl:5},{id:\"minecraft:silk_touch\",lvl:1},{id:\"minecraft:smite\",lvl:5},{id:\"minecraft:unbreaking\",lvl:3}]\n\t\t\"minecraft:diamond_axe\" -> \"Enchantments\" : [{id:\"minecraft:efficiency\",lvl:5},{id:\"minecraft:silk_touch\",lvl:1},{id:\"minecraft:smite\",lvl:5},{id:\"minecraft:unbreaking\",lvl:3}]\n\t\t\"minecraft:diamond_axe\"\n\tHOT4\n\t\t\"minecraft:iron_pickaxe\" -> \"Enchantments\" : [{id:\"minecraft:efficiency\",lvl:4},{id:\"minecraft:fortune\",lvl:3}]\n\tHOT7\n\t\t\"minecraft:splash_potion\" -> \"Potion\" : {id:\"minecraft:strong_harming\"}\n\tHOT6\n\t\t\"minecraft:splash_potion\" -> \"Potion\" : {id:\"minecraft:harming\"}\n\tHOT2\n\t\t\"minecraft:splash_potion\" -> \"Potion\" : {id:\"minecraft:weakness\"}\n\tHOT1\n\t\t\"minecraft:splash_potion\" -> \"Potion\" : {id:\"minecraft:regeneration\"}\n\tHOT8\n\t\t\"minecraft:splash_potion\" -> \"Potion\" : {id:\"minecraft:long_turtle_master\"}\n\tHOT9\n    HOT1\n";
      List var2 = ProfilesConfig.INSTANCE.getProfiles(var1);
      System.out.println((Object)ProfilesConfig.INSTANCE.asString(var2));
   }

   private static final String dump$lambda$6$lambda$5$lambda$0(ProfileData var0) {
      return var0.getActive() != ProfileSlotId.NONE ? var0.getName() + " activate slot " + var0.getActive().name() : var0.getName();
   }
}
