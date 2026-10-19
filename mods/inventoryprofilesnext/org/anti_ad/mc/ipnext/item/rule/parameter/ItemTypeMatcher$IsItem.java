package org.anti_ad.mc.ipnext.item.rule.parameter;

import kotlin.Lazy;
import kotlin.LazyKt;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.SourceDebugExtension;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import org.anti_ad.mc.ipnext.Log;
import org.anti_ad.mc.ipnext.item.ItemType;
import org.anti_ad.mc.ipnext.item.NbtUtils;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@SourceDebugExtension(
   "SMAP\nItemTypeMatcher.kt\nKotlin\n*S Kotlin\n*F\n+ 1 ItemTypeMatcher.kt\norg/anti_ad/mc/ipnext/item/rule/parameter/ItemTypeMatcher$IsItem\n+ 2 fake.kt\nkotlin/jvm/internal/FakeKt\n*L\n1#1,70:1\n1#2:71\n*E\n"
)
public final class ItemTypeMatcher$IsItem extends ItemTypeMatcher {
   @NotNull
   private final ResourceLocation identifier;
   @NotNull
   private final Lazy item$delegate;

   public ItemTypeMatcher$IsItem(@NotNull ResourceLocation var1) {
      Intrinsics.checkNotNullParameter(var1, "");
      super(null);
      this.identifier = var1;
      this.item$delegate = LazyKt.lazy(ItemTypeMatcher$IsItem::item_delegate$lambda$0);
   }

   @NotNull
   @Override
   public final ResourceLocation getIdentifier() {
      return this.identifier;
   }

   @Nullable
   public final Item getItem() {
      return (Item)this.item$delegate.getValue();
   }

   @Override
   public final boolean match(@NotNull ItemType var1) {
      Intrinsics.checkNotNullParameter(var1, "");
      Item var2;
      if ((var2 = this.getItem()) == null) {
         Log.INSTANCE.warn("Unknown item " + this.getIdentifier());
         return false;
      } else {
         return Intrinsics.areEqual(var1.getItem(), var2);
      }
   }

   private static final Item item_delegate$lambda$0(ItemTypeMatcher$IsItem var0) {
      return NbtUtils.INSTANCE.getItemFromId(var0.getIdentifier());
   }
}
