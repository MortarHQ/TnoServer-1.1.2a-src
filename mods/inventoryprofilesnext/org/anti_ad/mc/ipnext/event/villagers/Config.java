package org.anti_ad.mc.ipnext.event.villagers;

import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;
import kotlin.Lazy;
import kotlin.LazyKt;
import kotlin.LazyThreadSafetyMode;
import kotlin.collections.MapsKt;
import kotlin.jvm.JvmField;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.SourceDebugExtension;
import kotlinx.serialization.KSerializer;
import kotlinx.serialization.Serializable;
import kotlinx.serialization.internal.ArrayListSerializer;
import kotlinx.serialization.internal.LinkedHashMapSerializer;
import kotlinx.serialization.internal.StringSerializer;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Serializable
@SourceDebugExtension(
   "SMAP\nConfig.kt\nKotlin\n*S Kotlin\n*F\n+ 1 Config.kt\norg/anti_ad/mc/ipnext/event/villagers/Config\n+ 2 Maps.kt\nkotlin/collections/MapsKt__MapsKt\n*L\n1#1,105:1\n538#2:106\n523#2,6:107\n538#2:113\n523#2,6:114\n538#2:120\n523#2,6:121\n*S KotlinDebug\n*F\n+ 1 Config.kt\norg/anti_ad/mc/ipnext/event/villagers/Config\n*L\n85#1:106\n85#1:107,6\n88#1:113\n88#1:114,6\n91#1:120\n91#1:121,6\n*E\n"
)
public final class Config {
   @NotNull
   public static final Config$Companion Companion = new Config$Companion(null);
   @NotNull
   private final Map globalBookmarks;
   @NotNull
   private final Map localBookmarks;
   @NotNull
   private final Map globalBookmarks1;
   @NotNull
   private final Map localBookmarks1;
   @NotNull
   private final Map globalBookmarks2;
   @NotNull
   private final Map localBookmarks2;
   @NotNull
   private final Object sync;
   private boolean isDirty;
   @JvmField
   @NotNull
   private static final Lazy[] $childSerializers;

   public Config(@NotNull Map var1, @NotNull Map var2, @NotNull Map var3, @NotNull Map var4, @NotNull Map var5, @NotNull Map var6) {
      Intrinsics.checkNotNullParameter(var1, "");
      Intrinsics.checkNotNullParameter(var2, "");
      Intrinsics.checkNotNullParameter(var3, "");
      Intrinsics.checkNotNullParameter(var4, "");
      Intrinsics.checkNotNullParameter(var5, "");
      Intrinsics.checkNotNullParameter(var6, "");
      super();
      this.globalBookmarks = var1;
      this.localBookmarks = var2;
      this.globalBookmarks1 = var3;
      this.localBookmarks1 = var4;
      this.globalBookmarks2 = var5;
      this.localBookmarks2 = var6;
      this.sync = new Object();
   }

   @NotNull
   public final Map getGlobalBookmarks() {
      return this.globalBookmarks;
   }

   @NotNull
   public final Map getLocalBookmarks() {
      return this.localBookmarks;
   }

   @NotNull
   public final Map getGlobalBookmarks1() {
      return this.globalBookmarks1;
   }

   @NotNull
   public final Map getLocalBookmarks1() {
      return this.localBookmarks1;
   }

   @NotNull
   public final Map getGlobalBookmarks2() {
      return this.globalBookmarks2;
   }

   @NotNull
   public final Map getLocalBookmarks2() {
      return this.localBookmarks2;
   }

   @NotNull
   public final Object getSync() {
      return this.sync;
   }

   public final boolean isDirty() {
      return this.isDirty;
   }

   public final void copyFrom(@NotNull Config var1) {
      Intrinsics.checkNotNullParameter(var1, "");
      synchronized (this.sync) {
         this.globalBookmarks.clear();
         this.localBookmarks.clear();
         this.globalBookmarks.putAll(var1.globalBookmarks);
         this.localBookmarks.putAll(var1.localBookmarks);
         this.globalBookmarks1.clear();
         this.localBookmarks1.clear();
         this.globalBookmarks1.putAll(var1.globalBookmarks1);
         this.localBookmarks1.putAll(var1.localBookmarks1);
         this.globalBookmarks2.clear();
         this.localBookmarks2.clear();
         this.globalBookmarks2.putAll(var1.globalBookmarks2);
         this.localBookmarks2.putAll(var1.localBookmarks2);
      }
   }

   public final void clear() {
      synchronized (this.sync) {
         this.isDirty = !this.globalBookmarks.isEmpty()
            || !this.localBookmarks.isEmpty()
            || !this.globalBookmarks1.isEmpty()
            || !this.localBookmarks1.isEmpty()
            || !this.globalBookmarks2.isEmpty()
            || !this.localBookmarks2.isEmpty();
         this.globalBookmarks.clear();
         this.localBookmarks.clear();
         this.globalBookmarks1.clear();
         this.localBookmarks1.clear();
         this.globalBookmarks2.clear();
         this.localBookmarks2.clear();
      }
   }

   public final void markDirty() {
      synchronized (this.sync) {
         this.isDirty = true;
      }
   }

   public final void cleanDirty() {
      synchronized (this.sync) {
         this.isDirty = false;
      }
   }

   @NotNull
   public final Config asSanitized() {
      synchronized (this.sync) {
         Map var3 = this.localBookmarks;
         Map var2 = new LinkedHashMap();
         Iterator var4 = var3.entrySet().iterator();

         while (var4.hasNext()) {
            Entry var5;
            if (!((Collection)(var5 = (Entry)var4.next()).getValue()).isEmpty()) {
               var2.put(var5.getKey(), var5.getValue());
            }
         }

         Map var7 = MapsKt.toMutableMap(var2);
         var2 = this.localBookmarks1;
         var3 = new LinkedHashMap();
         Iterator var18 = var2.entrySet().iterator();

         while (var18.hasNext()) {
            Entry var6;
            if (!((Collection)(var6 = (Entry)var18.next()).getValue()).isEmpty()) {
               var3.put(var6.getKey(), var6.getValue());
            }
         }

         var2 = MapsKt.toMutableMap(var3);
         var3 = this.localBookmarks2;
         Map var17 = new LinkedHashMap();
         Iterator var19 = var3.entrySet().iterator();

         while (var19.hasNext()) {
            Entry var14;
            if (!((Collection)(var14 = (Entry)var19.next()).getValue()).isEmpty()) {
               var17.put(var14.getKey(), var14.getValue());
            }
         }

         var3 = MapsKt.toMutableMap(var17);
         Config var16;
         (var16 = new Config(
               MapsKt.toMutableMap(this.globalBookmarks),
               var7,
               MapsKt.toMutableMap(this.globalBookmarks1),
               var2,
               MapsKt.toMutableMap(this.globalBookmarks2),
               var3
            ))
            .isDirty = this.isDirty;
         return var16;
      }
   }

   @NotNull
   public final Map component1() {
      return this.globalBookmarks;
   }

   @NotNull
   public final Map component2() {
      return this.localBookmarks;
   }

   @NotNull
   public final Map component3() {
      return this.globalBookmarks1;
   }

   @NotNull
   public final Map component4() {
      return this.localBookmarks1;
   }

   @NotNull
   public final Map component5() {
      return this.globalBookmarks2;
   }

   @NotNull
   public final Map component6() {
      return this.localBookmarks2;
   }

   @NotNull
   public final Config copy(@NotNull Map var1, @NotNull Map var2, @NotNull Map var3, @NotNull Map var4, @NotNull Map var5, @NotNull Map var6) {
      Intrinsics.checkNotNullParameter(var1, "");
      Intrinsics.checkNotNullParameter(var2, "");
      Intrinsics.checkNotNullParameter(var3, "");
      Intrinsics.checkNotNullParameter(var4, "");
      Intrinsics.checkNotNullParameter(var5, "");
      Intrinsics.checkNotNullParameter(var6, "");
      return new Config(var1, var2, var3, var4, var5, var6);
   }

   @NotNull
   @Override
   public final String toString() {
      return "Config(globalBookmarks="
         + this.globalBookmarks
         + ", localBookmarks="
         + this.localBookmarks
         + ", globalBookmarks1="
         + this.globalBookmarks1
         + ", localBookmarks1="
         + this.localBookmarks1
         + ", globalBookmarks2="
         + this.globalBookmarks2
         + ", localBookmarks2="
         + this.localBookmarks2
         + ")";
   }

   @Override
   public final int hashCode() {
      return (
               (
                        ((this.globalBookmarks.hashCode() * 31 + this.localBookmarks.hashCode()) * 31 + this.globalBookmarks1.hashCode()) * 31
                           + this.localBookmarks1.hashCode()
                     )
                     * 31
                  + this.globalBookmarks2.hashCode()
            )
            * 31
         + this.localBookmarks2.hashCode();
   }

   @Override
   public final boolean equals(@Nullable Object var1) {
      if (this == var1) {
         return true;
      } else if (!(var1 instanceof Config var2)) {
         return false;
      } else if (!Intrinsics.areEqual(this.globalBookmarks, var2.globalBookmarks)) {
         return false;
      } else if (!Intrinsics.areEqual(this.localBookmarks, var2.localBookmarks)) {
         return false;
      } else if (!Intrinsics.areEqual(this.globalBookmarks1, var2.globalBookmarks1)) {
         return false;
      } else if (!Intrinsics.areEqual(this.localBookmarks1, var2.localBookmarks1)) {
         return false;
      } else {
         return !Intrinsics.areEqual(this.globalBookmarks2, var2.globalBookmarks2) ? false : Intrinsics.areEqual(this.localBookmarks2, var2.localBookmarks2);
      }
   }

   public Config() {
      this(null, null, null, null, null, null, 63, null);
   }

   static {
      Lazy[] var0;
      (var0 = new Lazy[6])[0] = LazyKt.lazy(
         LazyThreadSafetyMode.PUBLICATION,
         () -> (KSerializer)(
            new LinkedHashMapSerializer(
               (KSerializer)StringSerializer.INSTANCE, (KSerializer)(new ArrayListSerializer((KSerializer)VillagerTradeData$$serializer.INSTANCE))
            )
         )
      );
      var0[1] = LazyKt.lazy(
         LazyThreadSafetyMode.PUBLICATION,
         () -> (KSerializer)(
            new LinkedHashMapSerializer(
               (KSerializer)StringSerializer.INSTANCE, (KSerializer)(new ArrayListSerializer((KSerializer)VillagerTradeData$$serializer.INSTANCE))
            )
         )
      );
      var0[2] = LazyKt.lazy(
         LazyThreadSafetyMode.PUBLICATION,
         () -> (KSerializer)(
            new LinkedHashMapSerializer(
               (KSerializer)StringSerializer.INSTANCE, (KSerializer)(new ArrayListSerializer((KSerializer)VillagerTradeData$$serializer.INSTANCE))
            )
         )
      );
      var0[3] = LazyKt.lazy(
         LazyThreadSafetyMode.PUBLICATION,
         () -> (KSerializer)(
            new LinkedHashMapSerializer(
               (KSerializer)StringSerializer.INSTANCE, (KSerializer)(new ArrayListSerializer((KSerializer)VillagerTradeData$$serializer.INSTANCE))
            )
         )
      );
      var0[4] = LazyKt.lazy(
         LazyThreadSafetyMode.PUBLICATION,
         () -> (KSerializer)(
            new LinkedHashMapSerializer(
               (KSerializer)StringSerializer.INSTANCE, (KSerializer)(new ArrayListSerializer((KSerializer)VillagerTradeData$$serializer.INSTANCE))
            )
         )
      );
      var0[5] = LazyKt.lazy(
         LazyThreadSafetyMode.PUBLICATION,
         () -> (KSerializer)(
            new LinkedHashMapSerializer(
               (KSerializer)StringSerializer.INSTANCE, (KSerializer)(new ArrayListSerializer((KSerializer)VillagerTradeData$$serializer.INSTANCE))
            )
         )
      );
      $childSerializers = var0;
   }
}
