package org.anti_ad.mc.ipnext.item.rule.parameter;

import java.util.List;
import kotlin.Lazy;
import kotlin.LazyKt;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.SourceDebugExtension;
import net.minecraft.resources.ResourceLocation;
import org.anti_ad.mc.ipnext.Log;
import org.anti_ad.mc.ipnext.item.ItemType;
import org.anti_ad.mc.ipnext.item.NbtUtils;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@SourceDebugExtension(
   "SMAP\nItemTypeMatcher.kt\nKotlin\n*S Kotlin\n*F\n+ 1 ItemTypeMatcher.kt\norg/anti_ad/mc/ipnext/item/rule/parameter/ItemTypeMatcher$IsTag\n+ 2 fake.kt\nkotlin/jvm/internal/FakeKt\n*L\n1#1,70:1\n1#2:71\n*E\n"
)
public final class ItemTypeMatcher$IsTag extends ItemTypeMatcher {
   @NotNull
   private final ResourceLocation identifier;
   @NotNull
   private final Lazy tag$delegate;

   public ItemTypeMatcher$IsTag(@NotNull ResourceLocation var1) {
      Intrinsics.checkNotNullParameter(var1, "");
      super(null);
      this.identifier = var1;
      this.tag$delegate = LazyKt.lazy(ItemTypeMatcher$IsTag::tag_delegate$lambda$0);
   }

   @NotNull
   @Override
   public final ResourceLocation getIdentifier() {
      return this.identifier;
   }

   @Nullable
   public final List getTag() {
      return (List)this.tag$delegate.getValue();
   }

   @Override
   public final boolean match(@NotNull ItemType var1) {
      Intrinsics.checkNotNullParameter(var1, "");
      List var2;
      if ((var2 = this.getTag()) == null) {
         Log.INSTANCE.warn("Unknown tag #" + this.getIdentifier());
         return false;
      } else {
         return var2.contains(var1.getItem());
      }
   }

   private static final List tag_delegate$lambda$0(ItemTypeMatcher$IsTag var0) {
      return NbtUtils.INSTANCE.getTagFromId(var0.getIdentifier());
   }
}
