package com.tom.storagemod.components;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import java.util.List;
import net.minecraft.world.item.ItemStack;

public record SimpleItemFilterComponent(List<ItemStack> stacks, boolean matchComp, boolean allowList) {
   public static final Codec<SimpleItemFilterComponent> CODEC = RecordCodecBuilder.mapCodec(
         b -> b.group(
               Codec.list(ItemStack.OPTIONAL_CODEC).fieldOf("stacks").forGetter(SimpleItemFilterComponent::stacks),
               Codec.BOOL.fieldOf("match_component").forGetter(SimpleItemFilterComponent::matchComp),
               Codec.BOOL.fieldOf("allow_list").forGetter(SimpleItemFilterComponent::allowList)
            )
            .apply(b, SimpleItemFilterComponent::new)
      )
      .codec();
}
