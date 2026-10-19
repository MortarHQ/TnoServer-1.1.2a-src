package com.illusivesoulworks.polymorph.common.util;

import com.illusivesoulworks.polymorph.api.common.base.IRecipePair;
import java.util.Objects;
import javax.annotation.Nonnull;
import net.minecraft.core.DefaultedRegistry;
import net.minecraft.core.component.DataComponentMap;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

public record RecipePair(ResourceLocation resourceLocation, ItemStack output) implements IRecipePair {
   public static final StreamCodec<RegistryFriendlyByteBuf, IRecipePair> STREAM_CODEC = StreamCodec.composite(
      ResourceLocation.STREAM_CODEC, IRecipePair::getResourceLocation, ItemStack.STREAM_CODEC, IRecipePair::getOutput, RecipePair::new
   );

   @Override
   public ItemStack getOutput() {
      return this.output;
   }

   @Override
   public ResourceLocation getResourceLocation() {
      return this.resourceLocation;
   }

   public int compareTo(@Nonnull IRecipePair other) {
      ItemStack output1 = this.getOutput();
      ItemStack output2 = other.getOutput();
      DefaultedRegistry<Item> registry = BuiltInRegistries.ITEM;
      int compare = registry.getKey(output1.getItem()).compareTo(registry.getKey(output2.getItem()));
      if (compare == 0) {
         compare = output1.getCount() - output2.getCount();
         if (compare == 0) {
            DataComponentMap components1 = output1.getComponents();
            DataComponentMap components2 = output2.getComponents();
            return Objects.equals(components1, components2) ? 0 : components1.hashCode() - components2.hashCode();
         } else {
            return compare;
         }
      } else if (this.getResourceLocation().getNamespace().equals("minecraft") && !other.getResourceLocation().getNamespace().equals("minecraft")) {
         return 1;
      } else {
         return !this.getResourceLocation().getNamespace().equals("minecraft") && other.getResourceLocation().getNamespace().equals("minecraft") ? -1 : compare;
      }
   }
}
