package net.p3pp3rf1y.sophisticatedbackpacks.init;

import java.util.function.Supplier;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.entity.BlockEntityType.Builder;
import net.neoforged.bus.api.EventPriority;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.capabilities.RegisterCapabilitiesEvent;
import net.neoforged.neoforge.capabilities.Capabilities.EnergyStorage;
import net.neoforged.neoforge.capabilities.Capabilities.FluidHandler;
import net.neoforged.neoforge.capabilities.Capabilities.ItemHandler;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.p3pp3rf1y.sophisticatedbackpacks.backpack.BackpackBlock;
import net.p3pp3rf1y.sophisticatedbackpacks.backpack.BackpackBlockEntity;

public class ModBlocks {
   private static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(BuiltInRegistries.BLOCK, "sophisticatedbackpacks");
   private static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITY_TYPES = DeferredRegister.create(
      BuiltInRegistries.BLOCK_ENTITY_TYPE, "sophisticatedbackpacks"
   );
   public static final Supplier<BackpackBlock> BACKPACK = BLOCKS.register("backpack", () -> new BackpackBlock());
   public static final Supplier<BackpackBlock> COPPER_BACKPACK = BLOCKS.register("copper_backpack", () -> new BackpackBlock());
   public static final Supplier<BackpackBlock> IRON_BACKPACK = BLOCKS.register("iron_backpack", () -> new BackpackBlock());
   public static final Supplier<BackpackBlock> GOLD_BACKPACK = BLOCKS.register("gold_backpack", () -> new BackpackBlock());
   public static final Supplier<BackpackBlock> DIAMOND_BACKPACK = BLOCKS.register("diamond_backpack", () -> new BackpackBlock());
   public static final Supplier<BackpackBlock> NETHERITE_BACKPACK = BLOCKS.register("netherite_backpack", () -> new BackpackBlock(1200.0F));
   public static final Supplier<BlockEntityType<BackpackBlockEntity>> BACKPACK_TILE_TYPE = BLOCK_ENTITY_TYPES.register(
      "backpack",
      () -> Builder.of(
            BackpackBlockEntity::new,
            new Block[]{BACKPACK.get(), COPPER_BACKPACK.get(), IRON_BACKPACK.get(), GOLD_BACKPACK.get(), DIAMOND_BACKPACK.get(), NETHERITE_BACKPACK.get()}
         )
         .build(null)
   );

   private ModBlocks() {
   }

   public static void registerHandlers(IEventBus modBus) {
      BLOCKS.register(modBus);
      BLOCK_ENTITY_TYPES.register(modBus);
      NeoForge.EVENT_BUS.addListener(EventPriority.LOWEST, BackpackBlock::playerInteract);
      modBus.addListener(ModBlocks::registerCapabilities);
   }

   private static void registerCapabilities(RegisterCapabilitiesEvent event) {
      event.registerBlockEntity(ItemHandler.BLOCK, BACKPACK_TILE_TYPE.get(), BackpackBlockEntity::getExternalItemHandler);
      event.registerBlockEntity(FluidHandler.BLOCK, BACKPACK_TILE_TYPE.get(), BackpackBlockEntity::getExternalFluidHandler);
      event.registerBlockEntity(EnergyStorage.BLOCK, BACKPACK_TILE_TYPE.get(), BackpackBlockEntity::getExternalEnergyStorage);
   }
}
