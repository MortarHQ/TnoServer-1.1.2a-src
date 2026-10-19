package com.tensura_guild.registry;

import com.tensura_guild.block.entity.YuukiSpawnerBlockEntity;
import java.util.function.Supplier;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.entity.BlockEntityType.Builder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class GuildBlockEntities {
   public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITIES = DeferredRegister.create(Registries.BLOCK_ENTITY_TYPE, "tensura_guild");
   public static final Supplier<BlockEntityType<YuukiSpawnerBlockEntity>> YUUKI_SPAWNER = BLOCK_ENTITIES.register(
      "yuuki_spawner", () -> Builder.of(YuukiSpawnerBlockEntity::new, new Block[]{GuildBlocks.YUUKI_KAGURAZAKA_SPAWNER.get()}).build(null)
   );
}
