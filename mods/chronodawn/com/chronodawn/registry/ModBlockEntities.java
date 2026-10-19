package com.chronodawn.registry;

import com.chronodawn.ChronoDawn;
import com.chronodawn.blocks.BossRoomBoundaryMarkerBlockEntity;
import com.chronodawn.blocks.BossRoomDoorBlockEntity;
import com.chronodawn.blocks.ClockTowerTeleporterBlockEntity;
import dev.architectury.registry.registries.DeferredRegister;
import dev.architectury.registry.registries.RegistrySupplier;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.entity.BlockEntityType.Builder;

public class ModBlockEntities {
   public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITIES = DeferredRegister.create("chronodawn", Registries.BLOCK_ENTITY_TYPE);
   public static final RegistrySupplier<BlockEntityType<ClockTowerTeleporterBlockEntity>> CLOCK_TOWER_TELEPORTER = BLOCK_ENTITIES.register(
      ModBlockEntityId.CLOCK_TOWER_TELEPORTER.id(),
      () -> Builder.of(ClockTowerTeleporterBlockEntity::new, new Block[]{(Block)ModBlocks.CLOCK_TOWER_TELEPORTER.get()}).build(null)
   );
   public static final RegistrySupplier<BlockEntityType<BossRoomDoorBlockEntity>> BOSS_ROOM_DOOR = BLOCK_ENTITIES.register(
      ModBlockEntityId.BOSS_ROOM_DOOR.id(), () -> Builder.of(BossRoomDoorBlockEntity::new, new Block[]{(Block)ModBlocks.BOSS_ROOM_DOOR.get()}).build(null)
   );
   public static final RegistrySupplier<BlockEntityType<BossRoomBoundaryMarkerBlockEntity>> BOSS_ROOM_BOUNDARY_MARKER = BLOCK_ENTITIES.register(
      ModBlockEntityId.BOSS_ROOM_BOUNDARY_MARKER.id(),
      () -> Builder.of(BossRoomBoundaryMarkerBlockEntity::new, new Block[]{(Block)ModBlocks.BOSS_ROOM_BOUNDARY_MARKER.get()}).build(null)
   );

   public static void register() {
      BLOCK_ENTITIES.register();
      ChronoDawn.LOGGER.debug("Registered ModBlockEntities");
   }
}
