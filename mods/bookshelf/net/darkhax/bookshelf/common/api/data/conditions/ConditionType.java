package net.darkhax.bookshelf.common.api.data.conditions;

import com.mojang.serialization.MapCodec;
import net.minecraft.resources.ResourceLocation;

public record ConditionType(ResourceLocation id, MapCodec<? extends ILoadCondition> codec) {
}
