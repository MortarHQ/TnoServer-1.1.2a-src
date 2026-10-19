package twilightforest.compat.jei;

import com.mojang.serialization.Codec;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.entity.EntityType;

public record FakeEntityType(EntityType<?> type) {
   public static final Codec<FakeEntityType> CODEC = BuiltInRegistries.ENTITY_TYPE.byNameCodec().xmap(FakeEntityType::new, FakeEntityType::type);
}
