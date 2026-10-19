package net.p3pp3rf1y.sophisticatedcore.util;

import com.mojang.serialization.Codec;
import com.mojang.serialization.DataResult;
import com.mojang.serialization.DynamicOps;
import com.mojang.serialization.codecs.PrimitiveCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import java.util.Optional;
import java.util.Set;
import net.minecraft.core.component.DataComponentPatch;
import net.minecraft.util.ExtraCodecs;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.ItemContainerContents;
import net.minecraft.world.item.component.ItemContainerContents.Slot;

public class CodecHelper {
   public static final Codec<ItemStack> OVERSIZED_ITEM_STACK_CODEC = Codec.lazyInitialized(
      () -> RecordCodecBuilder.create(
         instance -> instance.group(
               ItemStack.ITEM_NON_AIR_CODEC.fieldOf("id").forGetter(ItemStack::getItemHolder),
               Codec.INT.fieldOf("count").orElse(1).forGetter(ItemStack::getCount),
               DataComponentPatch.CODEC.optionalFieldOf("components", DataComponentPatch.EMPTY).forGetter(p_330103_ -> p_330103_.components.asPatch())
            )
            .apply(instance, ItemStack::new)
      )
   );
   public static final Codec<ItemStack> OPTIONAL_OVERSIZED_ITEM_STACK_CODEC = ExtraCodecs.optionalEmptyMap(OVERSIZED_ITEM_STACK_CODEC)
      .xmap(optional -> optional.orElse(ItemStack.EMPTY), stack -> stack.isEmpty() ? Optional.empty() : Optional.of(stack))
      .orElse(ItemStack.EMPTY);
   public static final Codec<Slot> LENIENT_CONTENTS_SLOT_CODEC = RecordCodecBuilder.create(
      instance -> instance.group(
            Codec.intRange(0, 255).fieldOf("slot").forGetter(Slot::index),
            ItemStack.CODEC.lenientOptionalFieldOf("item", ItemStack.EMPTY).forGetter(Slot::item)
         )
         .apply(instance, Slot::new)
   );
   public static Codec<ItemContainerContents> LENIENT_ITEM_CONTAINER_CONTENTS_CODEC = LENIENT_CONTENTS_SLOT_CODEC.sizeLimitedListOf(256)
      .xmap(ItemContainerContents::fromSlots, ItemContainerContents::asSlots);
   public static final PrimitiveCodec<Integer> STRING_ENCODED_INT = new PrimitiveCodec<Integer>() {
      public <T> DataResult<Integer> read(DynamicOps<T> ops, T input) {
         return ops.getStringValue(input).map(s -> s.startsWith("i") ? Integer.parseInt(s.substring(1)) : Integer.parseInt(s));
      }

      public <T> T write(DynamicOps<T> ops, Integer value) {
         return (T)ops.createString("i" + value);
      }

      @Override
      public String toString() {
         return "Int";
      }
   };

   private CodecHelper() {
   }

   public static <T> Codec<Set<T>> setOf(Codec<T> elementCodec) {
      return new SetCodec<>(elementCodec);
   }
}
