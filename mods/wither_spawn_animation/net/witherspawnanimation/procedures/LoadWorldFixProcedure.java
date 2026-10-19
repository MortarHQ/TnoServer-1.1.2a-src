package net.witherspawnanimation.procedures;

import java.util.Comparator;
import javax.annotation.Nullable;
import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.neoforged.bus.api.Event;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.player.PlayerEvent.PlayerLoggedInEvent;

@EventBusSubscriber
public class LoadWorldFixProcedure {
   @SubscribeEvent
   public static void onPlayerLoggedIn(PlayerLoggedInEvent event) {
      execute(event, event.getEntity().level(), event.getEntity().getX(), event.getEntity().getY(), event.getEntity().getZ(), event.getEntity());
   }

   public static void execute(LevelAccessor world, double x, double y, double z, Entity entity) {
      execute(null, world, x, y, z, entity);
   }

   private static void execute(@Nullable Event event, LevelAccessor world, double x, double y, double z, Entity entity) {
      if (entity != null) {
         String currentPlayerRegistryName = "";
         boolean foundOtherPlayer = false;
         double scannedArea = 0.0;
         scannedArea = 200.0;
         foundOtherPlayer = false;
         currentPlayerRegistryName = BuiltInRegistries.ENTITY_TYPE.getKey(entity.getType()).toString();
         Vec3 _center = new Vec3(x, y, z);

         for (Entity entityiterator : world.getEntitiesOfClass(Entity.class, new AABB(_center, _center).inflate(scannedArea / 2.0), e -> true)
            .stream()
            .sorted(Comparator.comparingDouble(_entcnd -> _entcnd.distanceToSqr(_center)))
            .toList()) {
            if (entityiterator instanceof Player
               && !currentPlayerRegistryName.equals(BuiltInRegistries.ENTITY_TYPE.getKey(entityiterator.getType()).toString())) {
               foundOtherPlayer = true;
               break;
            }
         }

         if (!foundOtherPlayer) {
            _center = new Vec3(x, y, z);

            for (Entity entityiterator : world.getEntitiesOfClass(Entity.class, new AABB(_center, _center).inflate(scannedArea / 2.0), e -> true)
               .stream()
               .sorted(Comparator.comparingDouble(_entcnd -> _entcnd.distanceToSqr(_center)))
               .toList()) {
               if (entityiterator.getType().is(TagKey.create(Registries.ENTITY_TYPE, ResourceLocation.parse("wither_spawn_animation:altar_storm_general")))) {
                  SpawnWitherStormAtCoordinatesProcedure.execute(world, x, y, z, entityiterator.getX(), entityiterator.getY(), entityiterator.getZ());
                  if (!entityiterator.level().isClientSide()) {
                     entityiterator.discard();
                  }
               } else if (entityiterator.getType().is(TagKey.create(Registries.ENTITY_TYPE, ResourceLocation.parse("wither_spawn_animation:altar_general")))) {
                  if (world instanceof ServerLevel _level) {
                     Entity entityToSpawn = EntityType.WITHER
                        .spawn(
                           _level, BlockPos.containing(entityiterator.getX(), entityiterator.getY() + 1.0, entityiterator.getZ()), MobSpawnType.MOB_SUMMONED
                        );
                     if (entityToSpawn != null) {
                        entityToSpawn.setDeltaMovement(0.0, 0.0, 0.0);
                     }
                  }

                  if (!entityiterator.level().isClientSide()) {
                     entityiterator.discard();
                  }
               }
            }
         }
      }
   }
}
