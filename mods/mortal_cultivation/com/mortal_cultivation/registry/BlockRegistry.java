package com.mortal_cultivation.registry;

import com.mortal_cultivation.block.AlchemyFurnaceBlock;
import com.mortal_cultivation.block.AncientScholarTableBlock;
import com.mortal_cultivation.block.AncientSteleBlock;
import com.mortal_cultivation.block.ImmortalHerbBlock;
import com.mortal_cultivation.block.LightningWoodLingzhiBlock;
import com.mortal_cultivation.block.MillenniumGinsengBlock;
import com.mortal_cultivation.block.NineYinLotusBlock;
import com.mortal_cultivation.block.RuneTableBlock;
import com.mortal_cultivation.block.SpiritCharcoalOreBlock;
import com.mortal_cultivation.block.SpiritLeavesBlock;
import com.mortal_cultivation.block.SpiritLogBlock;
import com.mortal_cultivation.block.SpiritRecoveryHerbBlock;
import com.mortal_cultivation.block.SpiritSaplingBlock;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour.Properties;
import net.minecraft.world.level.block.state.properties.DoubleBlockHalf;
import net.minecraft.world.level.material.MapColor;
import net.minecraft.world.level.material.PushReaction;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class BlockRegistry {
   public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(Registries.BLOCK, "mortal_cultivation");
   public static final DeferredHolder<Block, SpiritLogBlock> SPIRIT_LOG = BLOCKS.register(
      "spirit_log", () -> new SpiritLogBlock(Properties.ofFullCopy(Blocks.OAK_LOG))
   );
   public static final DeferredHolder<Block, SpiritLeavesBlock> SPIRIT_LEAVES = BLOCKS.register(
      "spirit_leaves", () -> new SpiritLeavesBlock(false, Properties.ofFullCopy(Blocks.OAK_LEAVES))
   );
   public static final DeferredHolder<Block, SpiritLeavesBlock> SPIRIT_FRUIT_LEAVES = BLOCKS.register(
      "spirit_fruit_leaves", () -> new SpiritLeavesBlock(true, Properties.ofFullCopy(Blocks.OAK_LEAVES))
   );
   public static final DeferredHolder<Block, SpiritSaplingBlock> SPIRIT_SAPLING = BLOCKS.register(
      "spirit_sapling", () -> new SpiritSaplingBlock(Properties.ofFullCopy(Blocks.OAK_SAPLING))
   );
   public static final DeferredHolder<Block, SpiritRecoveryHerbBlock> SPIRIT_RECOVERY_HERB = BLOCKS.register(
      "spirit_recovery_herb",
      () -> new SpiritRecoveryHerbBlock(
         Properties.ofFullCopy(Blocks.LARGE_FERN).lightLevel(state -> state.getValue(SpiritRecoveryHerbBlock.HALF) == DoubleBlockHalf.UPPER ? 1 : 0)
      )
   );
   public static final DeferredHolder<Block, MillenniumGinsengBlock> MILLENNIUM_GINSENG = BLOCKS.register(
      "millennium_ginseng", () -> new MillenniumGinsengBlock(Properties.ofFullCopy(Blocks.POPPY))
   );
   public static final DeferredHolder<Block, ImmortalHerbBlock> IMMORTAL_HERB = BLOCKS.register(
      "immortal_herb", () -> new ImmortalHerbBlock(Properties.ofFullCopy(Blocks.POPPY).lightLevel(state -> 2))
   );
   public static final DeferredHolder<Block, LightningWoodLingzhiBlock> LIGHTNING_WOOD_LINGZHI = BLOCKS.register(
      "lightning_wood_lingzhi",
      () -> new LightningWoodLingzhiBlock(
         Properties.of()
            .mapColor(MapColor.COLOR_PURPLE)
            .strength(0.5F)
            .sound(SoundType.WOOD)
            .noOcclusion()
            .lightLevel(state -> 3)
            .pushReaction(PushReaction.DESTROY)
      )
   );
   public static final DeferredHolder<Block, NineYinLotusBlock> NINE_YIN_LOTUS = BLOCKS.register(
      "nine_yin_lotus", () -> new NineYinLotusBlock(Properties.ofFullCopy(Blocks.LILY_PAD).mapColor(MapColor.COLOR_BLACK))
   );
   public static final DeferredHolder<Block, Block> ALCHEMY_FURNACE = BLOCKS.register("alchemy_furnace", () -> new AlchemyFurnaceBlock());
   public static final DeferredHolder<Block, Block> ANCIENT_SCHOLAR_TABLE = BLOCKS.register(
      "ancient_scholar_table",
      () -> new AncientScholarTableBlock(
         Properties.of().mapColor(MapColor.WOOD).strength(2.0F, 3.0F).sound(SoundType.WOOD).pushReaction(PushReaction.BLOCK).noOcclusion()
      )
   );
   public static final DeferredHolder<Block, Block> ANCIENT_STELE = BLOCKS.register(
      "ancient_stele",
      () -> new AncientSteleBlock(
         Properties.of()
            .mapColor(MapColor.STONE)
            .strength(-1.0F, 3600000.0F)
            .sound(SoundType.STONE)
            .pushReaction(PushReaction.BLOCK)
            .noLootTable()
            .noOcclusion()
      )
   );
   public static final DeferredHolder<Block, Block> RUNE_TABLE = BLOCKS.register(
      "rune_table",
      () -> new RuneTableBlock(
         Properties.of()
            .mapColor(MapColor.DEEPSLATE)
            .requiresCorrectToolForDrops()
            .strength(3.0F, 6.0F)
            .sound(SoundType.DEEPSLATE_TILES)
            .pushReaction(PushReaction.BLOCK)
            .noOcclusion()
      )
   );
   public static final DeferredHolder<Block, Block> SPIRIT_CHARCOAL_ORE = BLOCKS.register(
      "spirit_charcoal_ore",
      () -> new SpiritCharcoalOreBlock(Properties.of().mapColor(MapColor.STONE).requiresCorrectToolForDrops().strength(3.0F, 3.0F).sound(SoundType.STONE))
   );
   public static final DeferredHolder<Block, Block> DEEPSLATE_SPIRIT_CHARCOAL_ORE = BLOCKS.register(
      "deepslate_spirit_charcoal_ore",
      () -> new SpiritCharcoalOreBlock(
         Properties.of().mapColor(MapColor.DEEPSLATE).requiresCorrectToolForDrops().strength(4.5F, 3.0F).sound(SoundType.DEEPSLATE)
      )
   );

   public static void register(IEventBus modEventBus) {
      BLOCKS.register(modEventBus);
   }
}
