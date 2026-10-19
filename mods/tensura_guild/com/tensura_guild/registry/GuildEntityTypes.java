package com.tensura_guild.registry;

import com.tensura_guild.entity.LaplaceEntity;
import com.tensura_guild.entity.YuukiKagurazakaEntity;
import java.util.function.Supplier;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.entity.EntityType.Builder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class GuildEntityTypes {
   public static final DeferredRegister<EntityType<?>> ENTITY_TYPES = DeferredRegister.create(Registries.ENTITY_TYPE, "tensura_guild");
   public static final Supplier<EntityType<YuukiKagurazakaEntity>> YUUKI_KAGURAZAKA = ENTITY_TYPES.register(
      "yuuki_kagurazaka",
      () -> Builder.of(YuukiKagurazakaEntity::new, MobCategory.MONSTER)
         .sized(0.6F, 1.8F)
         .eyeHeight(1.62F)
         .build(ResourceLocation.fromNamespaceAndPath("tensura_guild", "yuuki_kagurazaka").toString())
   );
   public static final Supplier<EntityType<LaplaceEntity>> LAPLACE = ENTITY_TYPES.register(
      "laplace",
      () -> Builder.of(LaplaceEntity::new, MobCategory.MONSTER)
         .sized(0.6F, 1.8F)
         .eyeHeight(1.62F)
         .build(ResourceLocation.fromNamespaceAndPath("tensura_guild", "laplace").toString())
   );
}
