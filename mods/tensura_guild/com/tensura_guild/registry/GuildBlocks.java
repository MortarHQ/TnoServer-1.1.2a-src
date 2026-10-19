package com.tensura_guild.registry;

import com.tensura_guild.block.YuukiSpawnerBlock;
import java.util.function.Supplier;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour.Properties;
import net.neoforged.neoforge.registries.DeferredRegister;

public class GuildBlocks {
   public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(Registries.BLOCK, "tensura_guild");
   public static final Supplier<Block> YUUKI_KAGURAZAKA_SPAWNER = BLOCKS.register(
      "yuuki_kagurazaka_spawner",
      () -> new YuukiSpawnerBlock(Properties.of().strength(-1.0F, 3600000.0F).sound(SoundType.STONE).noOcclusion().noCollission().noLootTable())
   );
}
