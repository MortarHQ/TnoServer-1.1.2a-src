package net.mcreator.rpgstylemorebosses.init;

import net.mcreator.rpgstylemorebosses.block.DungeonBricksCubesBlock;
import net.minecraft.world.level.block.Block;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.DeferredRegister.Blocks;

public class RpgStyleMoreBossesModBlocks {
   public static final Blocks REGISTRY = DeferredRegister.createBlocks("rpg_style_more_bosses");
   public static final DeferredBlock<Block> DUNGEON_BRICKS_CUBES = REGISTRY.register("dungeon_bricks_cubes", DungeonBricksCubesBlock::new);
}
