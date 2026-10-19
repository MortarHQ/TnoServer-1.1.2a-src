package org.anti_ad.mc.ipnext.item;

import java.util.function.Predicate;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import net.minecraft.core.component.DataComponentMap;
import net.minecraft.core.component.DataComponentPatch;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.component.PatchedDataComponentMap;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import org.anti_ad.mc.ipnext.item.rule.CountSink;
import org.anti_ad.mc.ipnext.item.rule.CountSource;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public final class ItemType implements CountSink {
   @NotNull
   public static final ItemType$Companion Companion = new ItemType$Companion(null);
   @NotNull
   private final Item item;
   @Nullable
   private final PatchedDataComponentMap aTag;
   @Nullable
   private final DataComponentPatch aChanges;
   @NotNull
   private final Function0 isDamageableFn;
   private boolean ignoreDurability;
   private final boolean isDamageable;
   @NotNull
   private Function1 stackSource;
   @NotNull
   private final PatchedDataComponentMap EMPTY_COMPONENT_MAP;
   @Nullable
   private final ItemStack sourceStack;
   @Nullable
   private final PatchedDataComponentMap tag;
   @NotNull
   private final DataComponentPatch changes;
   @NotNull
   private CountSource countSource;

   public ItemType(
      @NotNull Item var1,
      @Nullable PatchedDataComponentMap var2,
      @Nullable DataComponentPatch var3,
      @NotNull Function0 var4,
      boolean var5,
      boolean var6,
      @NotNull Function1 var7
   ) {
      Intrinsics.checkNotNullParameter(var1, "");
      Intrinsics.checkNotNullParameter(var4, "");
      Intrinsics.checkNotNullParameter(var7, "");
      super();
      this.item = var1;
      this.aTag = var2;
      this.aChanges = var3;
      this.isDamageableFn = var4;
      this.ignoreDurability = var5;
      this.isDamageable = var6;
      this.stackSource = var7;
      this.EMPTY_COMPONENT_MAP = new PatchedDataComponentMap(DataComponentMap.EMPTY);
      this.sourceStack = (ItemStack)this.stackSource.invoke(this);
      ItemType var10000;
      DataComponentPatch var10001;
      if (this.aTag == null && !Intrinsics.areEqual(this.item, Items.AIR)) {
         this.tag = this.EMPTY_COMPONENT_MAP;
         var10000 = this;
         var10001 = DataComponentPatch.EMPTY;
         Intrinsics.checkNotNullExpressionValue(DataComponentPatch.EMPTY, "");
      } else {
         this.tag = this.aTag;
         var10000 = this;
         var10001 = this.aChanges;
         if (this.aChanges == null) {
            var10001 = DataComponentPatch.EMPTY;
            Intrinsics.checkNotNullExpressionValue(DataComponentPatch.EMPTY, "");
         }
      }

      var10000.changes = var10001;
      this.countSource = ItemType::countSource$lambda$2;
   }

   @NotNull
   public final Item getItem() {
      return this.item;
   }

   @NotNull
   public final Function0 isDamageableFn() {
      return this.isDamageableFn;
   }

   public final boolean getIgnoreDurability() {
      return this.ignoreDurability;
   }

   public final void setIgnoreDurability(boolean var1) {
      this.ignoreDurability = var1;
   }

   @Nullable
   public final ItemStack getSourceStack() {
      return this.sourceStack;
   }

   @Nullable
   public final PatchedDataComponentMap getTag() {
      return this.tag;
   }

   @NotNull
   public final DataComponentPatch getChanges() {
      return this.changes;
   }

   public final int getAccumulatedCount() {
      return this.countSource.count(this);
   }

   @NotNull
   @Override
   public final String toString() {
      Object var10001 = this.tag;
      if (this.tag == null) {
         var10001 = "";
      }

      return "" + this.item + var10001;
   }

   @Override
   public final boolean equals(@Nullable Object var1) {
      if (this == var1) {
         return true;
      } else if (!Intrinsics.areEqual(this.getClass(), var1 != null ? var1.getClass() : null)) {
         return false;
      } else {
         Intrinsics.checkNotNull(var1);
         if (ItemTypeExtensionsKt.isEmpty(this) && ItemTypeExtensionsKt.isEmpty((ItemType)var1)) {
            return true;
         } else if (!Intrinsics.areEqual(this.item, ((ItemType)var1).item)) {
            return false;
         } else if (this.ignoreDurability && this.isDamageable) {
            Predicate var2 = ItemType::equals$lambda$0;
            DataComponentPatch var3 = ComponentUtils.INSTANCE._withRemovedIf_/* $VF was: (withRemovedIf) */(this.changes, var2);
            DataComponentPatch var4 = ComponentUtils.INSTANCE._withRemovedIf_/* $VF was: (withRemovedIf) */(((ItemType)var1).changes, var2);
            return Intrinsics.areEqual(var3, var4);
         } else {
            return Intrinsics.areEqual(this.changes, ((ItemType)var1).changes);
         }
      }
   }

   private final int tagHashCode() {
      byte var1 = 0;
      if (!this.ignoreDurability || !this.isDamageable) {
         var1 = (byte)(this.tag != null ? this.tag.hashCode() : 0);
      }

      return var1;
   }

   @Override
   public final int hashCode() {
      return ItemTypeExtensionsKt.isEmpty(this) ? 0 : this.item.hashCode() * 31 + this.tagHashCode();
   }

   @Override
   public final void setCountSource(@NotNull CountSource var1) {
      Intrinsics.checkNotNullParameter(var1, "");
      this.countSource = var1;
   }

   @NotNull
   public final Item component1() {
      return this.item;
   }

   private final PatchedDataComponentMap component2() {
      return this.aTag;
   }

   private final DataComponentPatch component3() {
      return this.aChanges;
   }

   @NotNull
   public final Function0 component4() {
      return this.isDamageableFn;
   }

   public final boolean component5() {
      return this.ignoreDurability;
   }

   private final boolean component6() {
      return this.isDamageable;
   }

   private final Function1 component7() {
      return this.stackSource;
   }

   @NotNull
   public final ItemType copy(
      @NotNull Item var1,
      @Nullable PatchedDataComponentMap var2,
      @Nullable DataComponentPatch var3,
      @NotNull Function0 var4,
      boolean var5,
      boolean var6,
      @NotNull Function1 var7
   ) {
      Intrinsics.checkNotNullParameter(var1, "");
      Intrinsics.checkNotNullParameter(var4, "");
      Intrinsics.checkNotNullParameter(var7, "");
      return new ItemType(var1, var2, var3, var4, var5, var6, var7);
   }

   private static final boolean equals$lambda$0(DataComponentType var0) {
      Intrinsics.checkNotNullParameter(var0, "");
      return Intrinsics.areEqual(var0, DataComponents.DAMAGE);
   }

   private static final int countSource$lambda$2(ItemType var0) {
      Intrinsics.checkNotNullParameter(var0, "");
      return 0;
   }
}
