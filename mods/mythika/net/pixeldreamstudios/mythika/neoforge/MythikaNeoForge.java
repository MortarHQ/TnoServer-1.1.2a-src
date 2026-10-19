package net.pixeldreamstudios.mythika.neoforge;

import com.mojang.serialization.MapCodec;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.SpawnPlacementType;
import net.minecraft.world.entity.SpawnPlacements.SpawnPredicate;
import net.minecraft.world.level.levelgen.Heightmap.Types;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.loading.FMLEnvironment;
import net.neoforged.neoforge.common.world.BiomeModifier;
import net.neoforged.neoforge.event.entity.EntityAttributeCreationEvent;
import net.neoforged.neoforge.event.entity.RegisterSpawnPlacementsEvent;
import net.neoforged.neoforge.event.entity.RegisterSpawnPlacementsEvent.Operation;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.NeoForgeRegistries.Keys;
import net.pixeldreamstudios.mythika.Mythika;
import net.pixeldreamstudios.mythika.neoforge.client.MythikaNeoForgeClient;
import net.pixeldreamstudios.mythika.neoforge.platform.NeoForgeRegistryHelper;
import net.pixeldreamstudios.mythika.registry.EntityRegistry;

@Mod("mythika")
public final class MythikaNeoForge {
   private static final DeferredRegister<MapCodec<? extends BiomeModifier>> BIOME_MODIFIERS = DeferredRegister.create(
      Keys.BIOME_MODIFIER_SERIALIZERS, "mythika"
   );

   public MythikaNeoForge(IEventBus modEventBus, ModContainer container) {
      NeoForgeRegistryHelper.setModEventBus(modEventBus);
      Mythika.init();
      BIOME_MODIFIERS.register(modEventBus);
      modEventBus.addListener(MythikaNeoForge::onEntityAttributeCreation);
      modEventBus.addListener(MythikaNeoForge::onRegisterSpawnPlacements);
      if (FMLEnvironment.dist == Dist.CLIENT) {
         MythikaNeoForgeClient.init(modEventBus, container);
      }
   }

   private static void onEntityAttributeCreation(EntityAttributeCreationEvent event) {
      EntityRegistry.forEachAttribute((entityType, builder) -> event.put(entityType, builder.build()));
   }

   private static void onRegisterSpawnPlacements(final RegisterSpawnPlacementsEvent event) {
      EntityRegistry.forEachSpawnPlacement(new EntityRegistry.SpawnPlacementConsumer() {
         @Override
         public <T extends Mob> void accept(EntityType<T> entityType, SpawnPlacementType placementType, Types heightmap, SpawnPredicate<T> predicate) {
            event.register(entityType, placementType, heightmap, predicate, Operation.REPLACE);
         }
      });
   }

   static {
      BIOME_MODIFIERS.register("config_spawns", () -> ConfigSpawnBiomeModifier.CODEC);
   }
}
