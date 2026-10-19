package com.tom.storagemod.components;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import java.util.List;
import net.minecraft.core.registries.Registries;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;

public record TagFilterComponent(List<TagKey<Item>> tags, boolean allowList) {
   public static final Codec<TagFilterComponent> CODEC = RecordCodecBuilder.mapCodec(
         b -> b.group(
               Codec.list(TagKey.codec(Registries.ITEM)).fieldOf("tags").forGetter(TagFilterComponent::tags),
               Codec.BOOL.fieldOf("allow_list").forGetter(TagFilterComponent::allowList)
            )
            .apply(b, TagFilterComponent::new)
      )
      .codec();
}
