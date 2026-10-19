package net.unusual.block_factorys_bosses.init;

import javax.annotation.ParametersAreNonnullByDefault;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.entity.BlockEntityType.BlockEntitySupplier;
import net.minecraft.world.level.block.entity.BlockEntityType.Builder;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.capabilities.RegisterCapabilitiesEvent;
import net.neoforged.neoforge.capabilities.Capabilities.ItemHandler;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.unusual.block_factorys_bosses.block.entity.BossSpawnerBlockEntity;
import net.unusual.block_factorys_bosses.block.entity.DragonBannerBlockEntity;
import net.unusual.block_factorys_bosses.block.entity.KrakenSpawnerBlockEntity;
import net.unusual.block_factorys_bosses.block.entity.PlankBlockEntity;
import net.unusual.block_factorys_bosses.block.entity.PrisonDoorBlockEntity;
import net.unusual.block_factorys_bosses.block.entity.RopeRollBlockEntity;
import net.unusual.block_factorys_bosses.block.entity.RustyPrisonDoorBlockEntity;
import net.unusual.block_factorys_bosses.block.entity.UnderworldArenaDoorBlockEntity;

@EventBusSubscriber
@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
public class BossesRiseBlockEntities {
   public static final DeferredRegister<BlockEntityType<?>> REGISTRY = DeferredRegister.create(BuiltInRegistries.BLOCK_ENTITY_TYPE, "block_factorys_bosses");
   public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<PrisonDoorBlockEntity>> PRISON_DOOR = register(
      "prison_door", BossesRiseBlocks.PRISON_DOOR, PrisonDoorBlockEntity::new
   );
   public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<RustyPrisonDoorBlockEntity>> RUSTY_PRISON_DOOR = register(
      "rusty_prison_door", BossesRiseBlocks.RUSTY_PRISON_DOOR, RustyPrisonDoorBlockEntity::new
   );
   public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<DragonBannerBlockEntity>> DRAGON_BANNER = register(
      "dragon_banner", BossesRiseBlocks.DRAGON_BANNER, DragonBannerBlockEntity::new
   );
   public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<BossSpawnerBlockEntity>> BOSS_SPAWNER = register(
      "boss_spawner", BossesRiseBlocks.BOSS_SPAWNER, BossSpawnerBlockEntity::new
   );
   public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<KrakenSpawnerBlockEntity>> KRAKEN_SPAWNER = register(
      "kraken_spawner", BossesRiseBlocks.KRAKEN_SPAWNER, KrakenSpawnerBlockEntity::new
   );
   public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<UnderworldArenaDoorBlockEntity>> UNDERWORLD_ARENA_DOOR = register(
      "underworld_arena_door", BossesRiseBlocks.UNDERWORLD_ARENA_DOOR, UnderworldArenaDoorBlockEntity::new
   );
   public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<RopeRollBlockEntity>> ROPE_ROLL = register(
      "rope_roll", BossesRiseBlocks.ROPE_ROLL, RopeRollBlockEntity::new
   );
   public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<PlankBlockEntity>> PLANK = register(
      "plank", BossesRiseBlocks.PLANK, PlankBlockEntity::new
   );

   private static <T extends BlockEntity, P extends Block> DeferredHolder<BlockEntityType<?>, BlockEntityType<T>> register(
      String registryname, DeferredHolder<Block, P> block, BlockEntitySupplier<T> supplier
   ) {
      return REGISTRY.register(registryname, () -> Builder.of(supplier, new Block[]{(Block)block.get()}).build(null));
   }

   @SubscribeEvent
   public static void registerCapabilities(RegisterCapabilitiesEvent event) {
      event.registerBlockEntity(ItemHandler.BLOCK, (BlockEntityType)PRISON_DOOR.get(), (blockEntity, side) -> blockEntity.getItemHandler());
      event.registerBlockEntity(ItemHandler.BLOCK, (BlockEntityType)RUSTY_PRISON_DOOR.get(), (blockEntity, side) -> blockEntity.getItemHandler());
      event.registerBlockEntity(ItemHandler.BLOCK, (BlockEntityType)BOSS_SPAWNER.get(), (blockEntity, side) -> blockEntity.getItemHandler());
   }
}
