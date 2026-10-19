package org.anti_ad.mc.ipnext;

import java.util.Iterator;
import java.util.List;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.SourceDebugExtension;
import net.neoforged.fml.ModList;
import net.neoforged.neoforgespi.language.IModInfo;
import org.jetbrains.annotations.NotNull;

@SourceDebugExtension(
   "SMAP\nModInfo.kt\nKotlin\n*S Kotlin\n*F\n+ 1 ModInfo.kt\norg/anti_ad/mc/ipnext/ModInfo\n+ 2 _Collections.kt\nkotlin/collections/CollectionsKt___CollectionsKt\n*L\n1#1,46:1\n1869#2,2:47\n*S KotlinDebug\n*F\n+ 1 ModInfo.kt\norg/anti_ad/mc/ipnext/ModInfo\n*L\n38#1:47,2\n*E\n"
)
public final class ModInfo {
   @NotNull
   public static final ModInfo INSTANCE = new ModInfo();
   @NotNull
   public static final String MOD_ID = "inventoryprofilesnext";
   @NotNull
   public static final String MOD_NAME = "Inventory Profiles Next";
   @NotNull
   private static String MOD_VERSION = "null";
   public static final int MINECRAFT_VERSION = 1192;

   private ModInfo() {
   }

   @NotNull
   public final String getMOD_VERSION() {
      return MOD_VERSION;
   }

   public final void setMOD_VERSION(@NotNull String var1) {
      Intrinsics.checkNotNullParameter(var1, "");
      MOD_VERSION = var1;
   }

   @NotNull
   public final String getModVersion() {
      List var10000 = ModList.get().getMods();
      Intrinsics.checkNotNullExpressionValue(var10000, "");
      Iterator var1 = var10000.iterator();

      while (var1.hasNext()) {
         IModInfo var2;
         if (Intrinsics.areEqual((var2 = (IModInfo)var1.next()).getModId(), "inventoryprofilesnext")) {
            return var2.getVersion().toString();
         }
      }

      return "?";
   }
}
