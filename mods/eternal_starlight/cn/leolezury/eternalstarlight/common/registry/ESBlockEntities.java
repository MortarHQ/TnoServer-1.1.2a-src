package cn.leolezury.eternalstarlight.common.registry;

import cn.leolezury.eternalstarlight.common.block.entity.AbyssalGeyserBlockEntity;
import cn.leolezury.eternalstarlight.common.block.entity.AlloyFurnaceBlockEntity;
import cn.leolezury.eternalstarlight.common.block.entity.CrateBlockEntity;
import cn.leolezury.eternalstarlight.common.block.entity.CrystalbornCatalystBlockEntity;
import cn.leolezury.eternalstarlight.common.block.entity.DryingRackBlockEntity;
import cn.leolezury.eternalstarlight.common.block.entity.DuskEmitterBlockEntity;
import cn.leolezury.eternalstarlight.common.block.entity.DuskLightBlockEntity;
import cn.leolezury.eternalstarlight.common.block.entity.DuskLockboxBlockEntity;
import cn.leolezury.eternalstarlight.common.block.entity.ESBrushableBlockEntity;
import cn.leolezury.eternalstarlight.common.block.entity.ESHangingSignBlockEntity;
import cn.leolezury.eternalstarlight.common.block.entity.ESPortalBlockEntity;
import cn.leolezury.eternalstarlight.common.block.entity.ESSignBlockEntity;
import cn.leolezury.eternalstarlight.common.block.entity.ESSkullBlockEntity;
import cn.leolezury.eternalstarlight.common.block.entity.EclipseCoreBlockEntity;
import cn.leolezury.eternalstarlight.common.block.entity.EnergyTransmitterBlockEntity;
import cn.leolezury.eternalstarlight.common.block.entity.FlareSpawnerBlockEntity;
import cn.leolezury.eternalstarlight.common.block.entity.GolemSteelJetBlockEntity;
import cn.leolezury.eternalstarlight.common.block.entity.LootChestBlockEntity;
import cn.leolezury.eternalstarlight.common.block.entity.LunarVineBlockEntity;
import cn.leolezury.eternalstarlight.common.block.entity.MechanicalSpawnerBlockEntity;
import cn.leolezury.eternalstarlight.common.block.entity.SolarEggBlockEntity;
import cn.leolezury.eternalstarlight.common.block.entity.StarfireBirdNestBlockEntity;
import cn.leolezury.eternalstarlight.common.block.entity.StellarRackBlockEntity;
import cn.leolezury.eternalstarlight.common.block.entity.TorreyaCampfireBlockEntity;
import cn.leolezury.eternalstarlight.common.block.entity.spawner.LunarMonstrositySpawnerBlockEntity;
import cn.leolezury.eternalstarlight.common.block.entity.spawner.PermafrostSpawnerBlockEntity;
import cn.leolezury.eternalstarlight.common.block.entity.spawner.StarlightGolemSpawnerBlockEntity;
import cn.leolezury.eternalstarlight.common.block.entity.spawner.TheGatekeeperSpawnerBlockEntity;
import cn.leolezury.eternalstarlight.common.platform.registry.RegistrationProvider;
import cn.leolezury.eternalstarlight.common.platform.registry.RegistryObject;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.entity.BlockEntityType.Builder;

public class ESBlockEntities {
   public static final RegistrationProvider<BlockEntityType<?>> BLOCK_ENTITIES = RegistrationProvider.get(Registries.BLOCK_ENTITY_TYPE, "eternal_starlight");
   public static final RegistryObject<BlockEntityType<?>, BlockEntityType<ESSignBlockEntity>> SIGN = BLOCK_ENTITIES.register(
      "sign",
      () -> Builder.of(
            ESSignBlockEntity::new,
            new Block[]{
               (Block)ESBlocks.LUNAR_WALL_SIGN.get(),
               (Block)ESBlocks.LUNAR_SIGN.get(),
               (Block)ESBlocks.NORTHLAND_WALL_SIGN.get(),
               (Block)ESBlocks.NORTHLAND_SIGN.get(),
               (Block)ESBlocks.BANYIN_WALL_SIGN.get(),
               (Block)ESBlocks.BANYIN_SIGN.get(),
               (Block)ESBlocks.SCARLET_WALL_SIGN.get(),
               (Block)ESBlocks.SCARLET_SIGN.get(),
               (Block)ESBlocks.TORREYA_WALL_SIGN.get(),
               (Block)ESBlocks.TORREYA_SIGN.get(),
               (Block)ESBlocks.JINGLESTEM_WALL_SIGN.get(),
               (Block)ESBlocks.JINGLESTEM_SIGN.get(),
               (Block)ESBlocks.CRADLEWOOD_WALL_SIGN.get(),
               (Block)ESBlocks.CRADLEWOOD_SIGN.get()
            }
         )
         .build(null)
   );
   public static final RegistryObject<BlockEntityType<?>, BlockEntityType<ESHangingSignBlockEntity>> HANGING_SIGN = BLOCK_ENTITIES.register(
      "hanging_sign",
      () -> Builder.of(
            ESHangingSignBlockEntity::new,
            new Block[]{
               (Block)ESBlocks.LUNAR_WALL_HANGING_SIGN.get(),
               (Block)ESBlocks.LUNAR_HANGING_SIGN.get(),
               (Block)ESBlocks.NORTHLAND_WALL_HANGING_SIGN.get(),
               (Block)ESBlocks.NORTHLAND_HANGING_SIGN.get(),
               (Block)ESBlocks.BANYIN_WALL_HANGING_SIGN.get(),
               (Block)ESBlocks.BANYIN_HANGING_SIGN.get(),
               (Block)ESBlocks.SCARLET_WALL_HANGING_SIGN.get(),
               (Block)ESBlocks.SCARLET_HANGING_SIGN.get(),
               (Block)ESBlocks.TORREYA_WALL_HANGING_SIGN.get(),
               (Block)ESBlocks.TORREYA_HANGING_SIGN.get(),
               (Block)ESBlocks.JINGLESTEM_WALL_HANGING_SIGN.get(),
               (Block)ESBlocks.JINGLESTEM_HANGING_SIGN.get(),
               (Block)ESBlocks.CRADLEWOOD_WALL_HANGING_SIGN.get(),
               (Block)ESBlocks.CRADLEWOOD_HANGING_SIGN.get()
            }
         )
         .build(null)
   );
   public static final RegistryObject<BlockEntityType<?>, BlockEntityType<TorreyaCampfireBlockEntity>> TORREYA_CAMPFIRE = BLOCK_ENTITIES.register(
      "torreya_campfire", () -> Builder.of(TorreyaCampfireBlockEntity::new, new Block[]{(Block)ESBlocks.TORREYA_CAMPFIRE.get()}).build(null)
   );
   public static final RegistryObject<BlockEntityType<?>, BlockEntityType<AbyssalGeyserBlockEntity>> ABYSSAL_GEYSER = BLOCK_ENTITIES.register(
      "abyssal_geyser",
      () -> Builder.of(
            AbyssalGeyserBlockEntity::new,
            new Block[]{(Block)ESBlocks.ABYSSAL_GEYSER.get(), (Block)ESBlocks.THERMABYSSAL_GEYSER.get(), (Block)ESBlocks.CRYOBYSSAL_GEYSER.get()}
         )
         .build(null)
   );
   public static final RegistryObject<BlockEntityType<?>, BlockEntityType<ESSkullBlockEntity>> SKULL = BLOCK_ENTITIES.register(
      "skull",
      () -> Builder.of(ESSkullBlockEntity::new, new Block[]{(Block)ESBlocks.TANGLED_SKULL.get(), (Block)ESBlocks.TANGLED_WALL_SKULL.get()}).build(null)
   );
   public static final RegistryObject<BlockEntityType<?>, BlockEntityType<ESBrushableBlockEntity>> BRUSHABLE_BLOCK = BLOCK_ENTITIES.register(
      "brushable_block",
      () -> Builder.of(ESBrushableBlockEntity::new, new Block[]{(Block)ESBlocks.SUSPICIOUS_DUSTED_GRAVEL.get(), (Block)ESBlocks.SUSPICIOUS_DIMSLAG.get()})
         .build(null)
   );
   public static final RegistryObject<BlockEntityType<?>, BlockEntityType<CrystalbornCatalystBlockEntity>> CRYSTALBORN_CATALYST = BLOCK_ENTITIES.register(
      "crystalborn_catalyst", () -> Builder.of(CrystalbornCatalystBlockEntity::new, new Block[]{(Block)ESBlocks.CRYSTALBORN_CATALYST.get()}).build(null)
   );
   public static final RegistryObject<BlockEntityType<?>, BlockEntityType<DryingRackBlockEntity>> DRYING_RACK = BLOCK_ENTITIES.register(
      "drying_rack", () -> Builder.of(DryingRackBlockEntity::new, new Block[]{(Block)ESBlocks.DRYING_RACK.get()}).build(null)
   );
   public static final RegistryObject<BlockEntityType<?>, BlockEntityType<StarfireBirdNestBlockEntity>> STARFIRE_BIRD_NEST = BLOCK_ENTITIES.register(
      "starfire_bird_nest",
      () -> Builder.of(
            StarfireBirdNestBlockEntity::new,
            new Block[]{
               (Block)ESBlocks.STARFIRE_BIRD_NEST.get(),
               (Block)ESBlocks.OAK_STARFIRE_BIRD_AVIARY.get(),
               (Block)ESBlocks.SPRUCE_STARFIRE_BIRD_AVIARY.get(),
               (Block)ESBlocks.BIRCH_STARFIRE_BIRD_AVIARY.get(),
               (Block)ESBlocks.ACACIA_STARFIRE_BIRD_AVIARY.get(),
               (Block)ESBlocks.CHERRY_STARFIRE_BIRD_AVIARY.get(),
               (Block)ESBlocks.JUNGLE_STARFIRE_BIRD_AVIARY.get(),
               (Block)ESBlocks.DARK_OAK_STARFIRE_BIRD_AVIARY.get(),
               (Block)ESBlocks.CRIMSON_STARFIRE_BIRD_AVIARY.get(),
               (Block)ESBlocks.WARPED_STARFIRE_BIRD_AVIARY.get(),
               (Block)ESBlocks.MANGROVE_STARFIRE_BIRD_AVIARY.get(),
               (Block)ESBlocks.BAMBOO_STARFIRE_BIRD_AVIARY.get(),
               (Block)ESBlocks.LUNAR_STARFIRE_BIRD_AVIARY.get(),
               (Block)ESBlocks.NORTHLAND_STARFIRE_BIRD_AVIARY.get(),
               (Block)ESBlocks.BANYIN_STARFIRE_BIRD_AVIARY.get(),
               (Block)ESBlocks.SCARLET_STARFIRE_BIRD_AVIARY.get(),
               (Block)ESBlocks.TORREYA_STARFIRE_BIRD_AVIARY.get(),
               (Block)ESBlocks.JINGLESTEM_STARFIRE_BIRD_AVIARY.get(),
               (Block)ESBlocks.CRADLEWOOD_STARFIRE_BIRD_AVIARY.get()
            }
         )
         .build(null)
   );
   public static final RegistryObject<BlockEntityType<?>, BlockEntityType<LootChestBlockEntity>> LOOT_CHEST = BLOCK_ENTITIES.register(
      "loot_chest", () -> Builder.of(LootChestBlockEntity::new, new Block[]{(Block)ESBlocks.LOOT_CHEST.get()}).build(null)
   );
   public static final RegistryObject<BlockEntityType<?>, BlockEntityType<TheGatekeeperSpawnerBlockEntity>> THE_GATEKEEPER_SPAWNER = BLOCK_ENTITIES.register(
      "the_gatekeeper_spawner", () -> Builder.of(TheGatekeeperSpawnerBlockEntity::new, new Block[]{(Block)ESBlocks.THE_GATEKEEPER_SPAWNER.get()}).build(null)
   );
   public static final RegistryObject<BlockEntityType<?>, BlockEntityType<StarlightGolemSpawnerBlockEntity>> STARLIGHT_GOLEM_SPAWNER = BLOCK_ENTITIES.register(
      "starlight_golem_spawner",
      () -> Builder.of(StarlightGolemSpawnerBlockEntity::new, new Block[]{(Block)ESBlocks.STARLIGHT_GOLEM_SPAWNER.get()}).build(null)
   );
   public static final RegistryObject<BlockEntityType<?>, BlockEntityType<PermafrostSpawnerBlockEntity>> PERMAFROST_SPAWNER = BLOCK_ENTITIES.register(
      "permafrost_spawner", () -> Builder.of(PermafrostSpawnerBlockEntity::new, new Block[]{(Block)ESBlocks.PERMAFROST_SPAWNER.get()}).build(null)
   );
   public static final RegistryObject<BlockEntityType<?>, BlockEntityType<LunarMonstrositySpawnerBlockEntity>> LUNAR_MONSTROSITY_SPAWNER = BLOCK_ENTITIES.register(
      "lunar_monstrosity_spawner",
      () -> Builder.of(LunarMonstrositySpawnerBlockEntity::new, new Block[]{(Block)ESBlocks.LUNAR_MONSTROSITY_SPAWNER.get()}).build(null)
   );
   public static final RegistryObject<BlockEntityType<?>, BlockEntityType<GolemSteelJetBlockEntity>> GOLEM_STEEL_JET = BLOCK_ENTITIES.register(
      "golem_steel_jet",
      () -> Builder.of(
            GolemSteelJetBlockEntity::new,
            new Block[]{(Block)ESBlocks.GOLEM_STEEL_JET.get(), (Block)ESBlocks.WAXED_GOLEM_STEEL_JET.get(), (Block)ESBlocks.OXIDIZED_GOLEM_STEEL_JET.get()}
         )
         .build(null)
   );
   public static final RegistryObject<BlockEntityType<?>, BlockEntityType<EnergyTransmitterBlockEntity>> ENERGY_TRANSMITTER = BLOCK_ENTITIES.register(
      "energy_transmitter", () -> Builder.of(EnergyTransmitterBlockEntity::new, new Block[]{(Block)ESBlocks.ENERGY_TRANSMITTER.get()}).build(null)
   );
   public static final RegistryObject<BlockEntityType<?>, BlockEntityType<MechanicalSpawnerBlockEntity>> MECHANICAL_SPAWNER = BLOCK_ENTITIES.register(
      "mechanical_spawner", () -> Builder.of(MechanicalSpawnerBlockEntity::new, new Block[]{(Block)ESBlocks.MECHANICAL_SPAWNER.get()}).build(null)
   );
   public static final RegistryObject<BlockEntityType<?>, BlockEntityType<AlloyFurnaceBlockEntity>> ALLOY_FURNACE = BLOCK_ENTITIES.register(
      "alloy_furnace",
      () -> Builder.of(
            AlloyFurnaceBlockEntity::new,
            new Block[]{(Block)ESBlocks.ALLOY_FURNACE.get(), (Block)ESBlocks.WAXED_ALLOY_FURNACE.get(), (Block)ESBlocks.OXIDIZED_ALLOY_FURNACE.get()}
         )
         .build(null)
   );
   public static final RegistryObject<BlockEntityType<?>, BlockEntityType<SolarEggBlockEntity>> SOLAR_EGG = BLOCK_ENTITIES.register(
      "solar_egg", () -> Builder.of(SolarEggBlockEntity::new, new Block[]{(Block)ESBlocks.SOLAR_EGG.get()}).build(null)
   );
   public static final RegistryObject<BlockEntityType<?>, BlockEntityType<LunarVineBlockEntity>> LUNAR_VINE = BLOCK_ENTITIES.register(
      "lunar_vine", () -> Builder.of(LunarVineBlockEntity::new, new Block[]{(Block)ESBlocks.LUNAR_VINE.get()}).build(null)
   );
   public static final RegistryObject<BlockEntityType<?>, BlockEntityType<CrateBlockEntity>> CRATE = BLOCK_ENTITIES.register(
      "crate", () -> Builder.of(CrateBlockEntity::new, new Block[]{(Block)ESBlocks.GOLEM_STEEL_CRATE.get()}).build(null)
   );
   public static final RegistryObject<BlockEntityType<?>, BlockEntityType<DuskLightBlockEntity>> DUSK_LIGHT = BLOCK_ENTITIES.register(
      "dusk_light",
      () -> Builder.of(DuskLightBlockEntity::new, new Block[]{(Block)ESBlocks.DUSK_LIGHT.get(), (Block)ESBlocks.REINFORCED_DUSK_LIGHT.get()}).build(null)
   );
   public static final RegistryObject<BlockEntityType<?>, BlockEntityType<DuskEmitterBlockEntity>> DUSK_EMITTER = BLOCK_ENTITIES.register(
      "dusk_emitter", () -> Builder.of(DuskEmitterBlockEntity::new, new Block[]{(Block)ESBlocks.DUSK_EMITTER.get()}).build(null)
   );
   public static final RegistryObject<BlockEntityType<?>, BlockEntityType<DuskLockboxBlockEntity>> DUSK_LOCKBOX = BLOCK_ENTITIES.register(
      "dusk_lockbox", () -> Builder.of(DuskLockboxBlockEntity::new, new Block[]{(Block)ESBlocks.DUSK_LOCKBOX.get()}).build(null)
   );
   public static final RegistryObject<BlockEntityType<?>, BlockEntityType<FlareSpawnerBlockEntity>> FLARE_SPAWNER = BLOCK_ENTITIES.register(
      "flare_spawner", () -> Builder.of(FlareSpawnerBlockEntity::new, new Block[]{(Block)ESBlocks.FLARE_SPAWNER.get()}).build(null)
   );
   public static final RegistryObject<BlockEntityType<?>, BlockEntityType<EclipseCoreBlockEntity>> ECLIPSE_CORE = BLOCK_ENTITIES.register(
      "eclipse_core", () -> Builder.of(EclipseCoreBlockEntity::new, new Block[]{(Block)ESBlocks.ECLIPSE_CORE.get()}).build(null)
   );
   public static final RegistryObject<BlockEntityType<?>, BlockEntityType<StellarRackBlockEntity>> STELLAR_RACK = BLOCK_ENTITIES.register(
      "stellar_rack", () -> Builder.of(StellarRackBlockEntity::new, new Block[]{(Block)ESBlocks.STELLAR_RACK.get()}).build(null)
   );
   public static final RegistryObject<BlockEntityType<?>, BlockEntityType<ESPortalBlockEntity>> STARLIGHT_PORTAL = BLOCK_ENTITIES.register(
      "starlight_portal", () -> Builder.of(ESPortalBlockEntity::new, new Block[]{(Block)ESBlocks.STARLIGHT_PORTAL.get()}).build(null)
   );

   public static void loadClass() {
   }
}
