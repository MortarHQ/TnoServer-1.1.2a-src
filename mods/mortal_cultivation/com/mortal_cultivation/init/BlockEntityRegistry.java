package com.mortal_cultivation.init;

import com.mortal_cultivation.block.entity.AlchemyFurnaceBlockEntity;
import com.mortal_cultivation.block.entity.AncientScholarTableBlockEntity;
import com.mortal_cultivation.block.entity.AncientSteleBlockEntity;
import com.mortal_cultivation.block.entity.LightningWoodLingzhiBlockEntity;
import com.mortal_cultivation.block.entity.MillenniumGinsengBlockEntity;
import com.mortal_cultivation.block.entity.NineYinLotusBlockEntity;
import com.mortal_cultivation.block.entity.RuneTableBlockEntity;
import com.mortal_cultivation.registry.BlockRegistry;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.entity.BlockEntityType.Builder;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class BlockEntityRegistry {
   public static final DeferredRegister<BlockEntityType<?>> REGISTRY = DeferredRegister.create(Registries.BLOCK_ENTITY_TYPE, "mortal_cultivation");
   public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<LightningWoodLingzhiBlockEntity>> LIGHTNING_WOOD_LINGZHI = REGISTRY.register(
      "lightning_wood_lingzhi",
      () -> Builder.of(LightningWoodLingzhiBlockEntity::new, new Block[]{(Block)BlockRegistry.LIGHTNING_WOOD_LINGZHI.get()}).build(null)
   );
   public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<NineYinLotusBlockEntity>> NINE_YIN_LOTUS = REGISTRY.register(
      "nine_yin_lotus", () -> Builder.of(NineYinLotusBlockEntity::new, new Block[]{(Block)BlockRegistry.NINE_YIN_LOTUS.get()}).build(null)
   );
   public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<MillenniumGinsengBlockEntity>> MILLENNIUM_GINSENG = REGISTRY.register(
      "millennium_ginseng", () -> Builder.of(MillenniumGinsengBlockEntity::new, new Block[]{(Block)BlockRegistry.MILLENNIUM_GINSENG.get()}).build(null)
   );
   public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<AlchemyFurnaceBlockEntity>> ALCHEMY_FURNACE = REGISTRY.register(
      "alchemy_furnace", () -> Builder.of(AlchemyFurnaceBlockEntity::new, new Block[]{(Block)BlockRegistry.ALCHEMY_FURNACE.get()}).build(null)
   );
   public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<AncientScholarTableBlockEntity>> ANCIENT_SCHOLAR_TABLE = REGISTRY.register(
      "ancient_scholar_table", () -> Builder.of(AncientScholarTableBlockEntity::new, new Block[]{(Block)BlockRegistry.ANCIENT_SCHOLAR_TABLE.get()}).build(null)
   );
   public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<AncientSteleBlockEntity>> ANCIENT_STELE = REGISTRY.register(
      "ancient_stele", () -> Builder.of(AncientSteleBlockEntity::new, new Block[]{(Block)BlockRegistry.ANCIENT_STELE.get()}).build(null)
   );
   public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<RuneTableBlockEntity>> RUNE_TABLE = REGISTRY.register(
      "rune_table", () -> Builder.of(RuneTableBlockEntity::new, new Block[]{(Block)BlockRegistry.RUNE_TABLE.get()}).build(null)
   );

   public static void register(IEventBus modEventBus) {
      REGISTRY.register(modEventBus);
   }
}
