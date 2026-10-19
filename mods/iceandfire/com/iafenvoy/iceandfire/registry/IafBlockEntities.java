package com.iafenvoy.iceandfire.registry;

import com.iafenvoy.iceandfire.item.block.entity.DragonForgeBlockEntity;
import com.iafenvoy.iceandfire.item.block.entity.DragonForgeBrickBlockEntity;
import com.iafenvoy.iceandfire.item.block.entity.DragonForgeInputBlockEntity;
import com.iafenvoy.iceandfire.item.block.entity.DreadPortalBlockEntity;
import com.iafenvoy.iceandfire.item.block.entity.DreadSpawnerBlockEntity;
import com.iafenvoy.iceandfire.item.block.entity.EggInIceBlockEntity;
import com.iafenvoy.iceandfire.item.block.entity.GhostChestBlockEntity;
import com.iafenvoy.iceandfire.item.block.entity.JarBlockEntity;
import com.iafenvoy.iceandfire.item.block.entity.LecternBlockEntity;
import com.iafenvoy.iceandfire.item.block.entity.PixieHouseBlockEntity;
import com.iafenvoy.iceandfire.item.block.entity.PodiumBlockEntity;
import dev.architectury.registry.registries.DeferredRegister;
import dev.architectury.registry.registries.RegistrySupplier;
import java.util.function.Supplier;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.entity.BlockEntityType.Builder;

public final class IafBlockEntities {
   public static final DeferredRegister<BlockEntityType<?>> REGISTRY = DeferredRegister.create("iceandfire", Registries.BLOCK_ENTITY_TYPE);
   public static final RegistrySupplier<BlockEntityType<EggInIceBlockEntity>> EGG_IN_ICE = register(
      "egginice", () -> Builder.of(EggInIceBlockEntity::new, new Block[]{(Block)IafBlocks.EGG_IN_ICE.get()})
   );
   public static final RegistrySupplier<BlockEntityType<PixieHouseBlockEntity>> PIXIE_HOUSE = register(
      "pixie_house",
      () -> Builder.of(
         PixieHouseBlockEntity::new,
         new Block[]{
            (Block)IafBlocks.PIXIE_HOUSE_MUSHROOM_RED.get(),
            (Block)IafBlocks.PIXIE_HOUSE_MUSHROOM_BROWN.get(),
            (Block)IafBlocks.PIXIE_HOUSE_OAK.get(),
            (Block)IafBlocks.PIXIE_HOUSE_BIRCH.get(),
            (Block)IafBlocks.PIXIE_HOUSE_BIRCH.get(),
            (Block)IafBlocks.PIXIE_HOUSE_SPRUCE.get(),
            (Block)IafBlocks.PIXIE_HOUSE_DARK_OAK.get()
         }
      )
   );
   public static final RegistrySupplier<BlockEntityType<JarBlockEntity>> PIXIE_JAR = register(
      "pixie_jar",
      () -> Builder.of(
         JarBlockEntity::new,
         new Block[]{
            (Block)IafBlocks.JAR_EMPTY.get(),
            (Block)IafBlocks.JAR_PIXIE_0.get(),
            (Block)IafBlocks.JAR_PIXIE_1.get(),
            (Block)IafBlocks.JAR_PIXIE_2.get(),
            (Block)IafBlocks.JAR_PIXIE_3.get(),
            (Block)IafBlocks.JAR_PIXIE_4.get()
         }
      )
   );
   public static final RegistrySupplier<BlockEntityType<DragonForgeBlockEntity>> DRAGONFORGE_CORE = register(
      "dragonforge_core",
      () -> Builder.of(
         DragonForgeBlockEntity::new,
         new Block[]{
            (Block)IafBlocks.DRAGONFORGE_FIRE_CORE.get(),
            (Block)IafBlocks.DRAGONFORGE_ICE_CORE.get(),
            (Block)IafBlocks.DRAGONFORGE_FIRE_CORE_DISABLED.get(),
            (Block)IafBlocks.DRAGONFORGE_ICE_CORE_DISABLED.get(),
            (Block)IafBlocks.DRAGONFORGE_LIGHTNING_CORE.get(),
            (Block)IafBlocks.DRAGONFORGE_LIGHTNING_CORE_DISABLED.get()
         }
      )
   );
   public static final RegistrySupplier<BlockEntityType<DragonForgeBrickBlockEntity>> DRAGONFORGE_BRICK = register(
      "dragonforge_brick",
      () -> Builder.of(
         DragonForgeBrickBlockEntity::new,
         new Block[]{
            (Block)IafBlocks.DRAGONFORGE_FIRE_BRICK.get(), (Block)IafBlocks.DRAGONFORGE_ICE_BRICK.get(), (Block)IafBlocks.DRAGONFORGE_LIGHTNING_BRICK.get()
         }
      )
   );
   public static final RegistrySupplier<BlockEntityType<DragonForgeInputBlockEntity>> DRAGONFORGE_INPUT = register(
      "dragonforge_input",
      () -> Builder.of(
         DragonForgeInputBlockEntity::new,
         new Block[]{
            (Block)IafBlocks.DRAGONFORGE_FIRE_INPUT.get(), (Block)IafBlocks.DRAGONFORGE_ICE_INPUT.get(), (Block)IafBlocks.DRAGONFORGE_LIGHTNING_INPUT.get()
         }
      )
   );
   public static final RegistrySupplier<BlockEntityType<DreadPortalBlockEntity>> DREAD_PORTAL = register(
      "dread_portal", () -> Builder.of(DreadPortalBlockEntity::new, new Block[]{(Block)IafBlocks.DREAD_PORTAL.get()})
   );
   public static final RegistrySupplier<BlockEntityType<DreadSpawnerBlockEntity>> DREAD_SPAWNER = register(
      "dread_spawner", () -> Builder.of(DreadSpawnerBlockEntity::new, new Block[]{(Block)IafBlocks.DREAD_SPAWNER.get()})
   );
   public static final RegistrySupplier<BlockEntityType<GhostChestBlockEntity>> GHOST_CHEST = register(
      "ghost_chest", () -> Builder.of(GhostChestBlockEntity::new, new Block[]{(Block)IafBlocks.GHOST_CHEST.get()})
   );
   public static final RegistrySupplier<BlockEntityType<LecternBlockEntity>> IAF_LECTERN = register(
      "lectern", () -> Builder.of(LecternBlockEntity::new, new Block[]{(Block)IafBlocks.LECTERN.get()})
   );
   public static final RegistrySupplier<BlockEntityType<PodiumBlockEntity>> PODIUM = register(
      "podium",
      () -> Builder.of(
         PodiumBlockEntity::new,
         new Block[]{
            (Block)IafBlocks.PODIUM_OAK.get(),
            (Block)IafBlocks.PODIUM_BIRCH.get(),
            (Block)IafBlocks.PODIUM_SPRUCE.get(),
            (Block)IafBlocks.PODIUM_JUNGLE.get(),
            (Block)IafBlocks.PODIUM_DARK_OAK.get(),
            (Block)IafBlocks.PODIUM_ACACIA.get()
         }
      )
   );

   private static <T extends BlockEntity> RegistrySupplier<BlockEntityType<T>> register(String entityName, Supplier<Builder<T>> builder) {
      return REGISTRY.register(entityName, () -> builder.get().build(null));
   }
}
