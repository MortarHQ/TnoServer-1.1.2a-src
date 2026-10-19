package org.anti_ad.mc.ipnext.inventory;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import kotlin.Pair;
import kotlin.collections.SetsKt;
import kotlin.jvm.JvmStatic;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.SourceDebugExtension;
import net.minecraft.core.NonNullList;
import net.minecraft.world.inventory.AbstractContainerMenu;
import org.anti_ad.mc.ipnext.container.ExtensionsKt;
import org.anti_ad.mc.ipnext.integration.HintClassData;
import org.jetbrains.annotations.NotNull;

@SourceDebugExtension(
   "SMAP\nContainerTypes.kt\nKotlin\n*S Kotlin\n*F\n+ 1 ContainerTypes.kt\norg/anti_ad/mc/ipnext/inventory/ContainerTypes\n+ 2 _Collections.kt\nkotlin/collections/CollectionsKt___CollectionsKt\n+ 3 _Arrays.kt\nkotlin/collections/ArraysKt___ArraysKt\n+ 4 VanillaAccessors.kt\norg/anti_ad/mc/ipnext/ingame/VanillaAccessorsKt\n*L\n1#1,196:1\n1869#2,2:197\n1740#2,3:201\n1869#2,2:204\n295#2,2:206\n13472#3,2:199\n78#4:208\n*S KotlinDebug\n*F\n+ 1 ContainerTypes.kt\norg/anti_ad/mc/ipnext/inventory/ContainerTypes\n*L\n52#1:197,2\n111#1:201,3\n117#1:204,2\n126#1:206,2\n103#1:199,2\n136#1:208\n*E\n"
)
public final class ContainerTypes {
   @NotNull
   public static final ContainerTypes INSTANCE = new ContainerTypes();
   @NotNull
   private static final List sources = new ArrayList();
   @NotNull
   private static final Map innerMap = new LinkedHashMap();
   @NotNull
   private static final Map outerMap = new LinkedHashMap();

   private ContainerTypes() {
   }

   public final void addContainersSource(@NotNull Function0 var1) {
      Intrinsics.checkNotNullParameter(var1, "");
      sources.add(var1);
   }

   @JvmStatic
   public static final void init() {
      Iterator var0 = sources.iterator();

      while (var0.hasNext()) {
         Pair[] var1 = (Pair[])((Function0)var0.next()).invoke();
         INSTANCE.register(Arrays.copyOf(var1, var1.length));
      }
   }

   public final void reset() {
      outerMap.clear();
      innerMap.clear();
      init();
   }

   private final Set getUnknownContainerDefaultTypes(AbstractContainerMenu var1) {
      ContainerType[] var2;
      (var2 = new ContainerType[3])[0] = ContainerType.SORTABLE_STORAGE;
      var2[1] = ContainerType.RECTANGULAR;
      var2[2] = ContainerType.WIDTH_9;
      return SetsKt.setOf(var2);
   }

   public final void deregister(@NotNull Class var1) {
      Intrinsics.checkNotNullParameter(var1, "");
      outerMap.remove(var1);
   }

   private final void register(Class var1, Set var2, boolean var3) {
      if (var3) {
         outerMap.put(var1, SetsKt.plus(outerMap.getOrDefault(var1, SetsKt.emptySet()), var2));
      } else {
         innerMap.put(var1, SetsKt.plus(innerMap.getOrDefault(var1, SetsKt.emptySet()), var2));
      }
   }

   private final void register(Pair... var1) {
      int var2 = 0;

      for (int var3 = var1.length; var2 < var3; var2++) {
         Pair var4 = var1[var2];
         register$default(INSTANCE, (Class)var4.getFirst(), (Set)var4.getSecond(), false, 4, null);
      }
   }

   public final boolean match(@NotNull Set var1, @NotNull Set var2, @NotNull Set var3) {
      Intrinsics.checkNotNullParameter(var1, "");
      Intrinsics.checkNotNullParameter(var2, "");
      Intrinsics.checkNotNullParameter(var3, "");
      if (var1.containsAll(var2)) {
         boolean var10000;
         label40: {
            Iterable var4;
            if (!((var4 = var3) instanceof Collection) || !((Collection)var4).isEmpty()) {
               for (ContainerType var6 : var4) {
                  if (var1.contains(var6)) {
                     var10000 = false;
                     break label40;
                  }
               }
            }

            var10000 = true;
         }

         if (var10000) {
            return true;
         }
      }

      return false;
   }

   private final Class getRepresentingClass(AbstractContainerMenu var1) {
      if (outerMap.containsKey(var1.getClass())) {
         return var1.getClass();
      }

      Iterator var2 = outerMap.keySet().iterator();

      while (var2.hasNext()) {
         Class var3;
         if ((var3 = (Class)var2.next()).isInstance(var1)) {
            return var3;
         }
      }

      if (innerMap.containsKey(var1.getClass())) {
         return var1.getClass();
      }

      var2 = innerMap.keySet().iterator();

      Object var10000;
      while (true) {
         if (var2.hasNext()) {
            Object var5;
            if (!((Class)(var5 = var2.next())).isInstance(var1)) {
               continue;
            }

            var10000 = var5;
            break;
         }

         var10000 = null;
         break;
      }

      return (Class)var10000;
   }

   @NotNull
   public final Set getTypes(@NotNull AbstractContainerMenu var1, @NotNull HintClassData var2) {
      Intrinsics.checkNotNullParameter(var1, "");
      Intrinsics.checkNotNullParameter(var2, "");
      NonNullList var10000 = var1.slots;
      Intrinsics.checkNotNullExpressionValue(var1.slots, "");
      Set var7;
      if (((List)var10000).isEmpty()) {
         var7 = ContainerTypesKt.getNonStorage();
      } else {
         Class var3 = this.getRepresentingClass(var1);
         Class var4 = var2.getIgnore() ? var1.getClass() : null;
         boolean var5 = var2.getPlayerSideOnly();
         if (var3 == null) {
            if (var4 == null) {
               Set var6;
               if (!var5) {
                  var6 = this.getUnknownContainerDefaultTypes(var1);
                  this.register(var1.getClass(), var6, true);
               } else {
                  var6 = ContainerTypesKt.getPlayerOnly();
                  this.register(var1.getClass(), var6, true);
               }

               return var6;
            } else {
               Set var9 = ContainerTypesKt.getNonStorage();
               this.register(var4, var9, true);
               return var9;
            }
         }

         if (var4 != null && !Intrinsics.areEqual(var3, var4)) {
            Set var8 = ContainerTypesKt.getNonStorage();
            this.register(var4, var8, true);
            return var8;
         }

         var7 = (Set)outerMap.get(var3);
         if (var7 == null) {
            var7 = (Set)innerMap.get(var3);
            if (var7 == null) {
               var7 = this.getUnknownContainerDefaultTypes(var1);
            }
         }
      }

      return var7;
   }

   private static final Pair[] _init_$lambda$1() {
      return ExtensionsKt.getVersionSpecificContainerTypes();
   }

   static {
      INSTANCE.addContainersSource(ContainerTypes::_init_$lambda$1);
      init();
   }
}
