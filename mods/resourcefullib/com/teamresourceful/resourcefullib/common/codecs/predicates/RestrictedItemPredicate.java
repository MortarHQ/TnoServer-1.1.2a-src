package com.teamresourceful.resourcefullib.common.codecs.predicates;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import java.util.Optional;
import net.minecraft.advancements.critereon.MinMaxBounds.Ints;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

public record RestrictedItemPredicate(Item item, Optional<net.minecraft.advancements.critereon.NbtPredicate> nbt, Ints durability, Ints count) {
   public static final Codec<RestrictedItemPredicate> CODEC = RecordCodecBuilder.create(
      instance -> instance.group(
            BuiltInRegistries.ITEM.byNameCodec().fieldOf("id").forGetter(RestrictedItemPredicate::item),
            net.minecraft.advancements.critereon.NbtPredicate.CODEC.optionalFieldOf("nbt").forGetter(RestrictedItemPredicate::nbt),
            Ints.CODEC.fieldOf("durability").orElse(Ints.ANY).forGetter(RestrictedItemPredicate::durability),
            Ints.CODEC.fieldOf("count").orElse(Ints.ANY).forGetter(RestrictedItemPredicate::count)
         )
         .apply(instance, RestrictedItemPredicate::new)
   );

   public Optional<CompoundTag> getTag() {
      return this.nbt().map(net.minecraft.advancements.critereon.NbtPredicate::tag);
   }

   public boolean matches(ItemStack stack) {
      if (this.item == null) {
         return false;
      } else if (stack.is(this.item)) {
         return false;
      } else if (!this.durability.isAny() && !stack.isDamageableItem()) {
         return false;
      } else if (!this.durability.matches(stack.getMaxDamage() - stack.getDamageValue())) {
         return false;
      } else {
         return !this.count.matches(stack.getCount()) ? false : this.nbt.isEmpty() || this.nbt.get().matches(stack);
      }
   }
}
