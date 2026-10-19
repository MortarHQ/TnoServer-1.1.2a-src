package com.blackgear.platform.common.integration.neoforge;

import com.blackgear.platform.common.integration.MobIntegration;
import com.blackgear.platform.common.integration.MobInteraction;
import com.blackgear.platform.core.Environment;
import com.blackgear.platform.core.util.EventBus;
import java.util.function.Consumer;
import java.util.function.Supplier;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.SpawnPlacementType;
import net.minecraft.world.entity.SpawnPlacements.SpawnPredicate;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier.Builder;
import net.minecraft.world.level.levelgen.Heightmap.Types;
import net.neoforged.neoforge.event.entity.RegisterSpawnPlacementsEvent.Operation;

public class MobIntegrationImpl {
   public static void registerIntegrations(Consumer<MobIntegration.Event> listener) {
      listener.accept(
         new MobIntegration.Event() {
            @Override
            public void registerMobInteraction(MobInteraction interaction) {
               EventBus.get(EventBus.LOADER).addListener(event -> {
                  InteractionResult result = interaction.onInteract(event.getEntity(), event.getTarget(), event.getHand());
                  if (result.consumesAction()) {
                     event.setCancellationResult(result);
                     event.setCanceled(true);
                  }
               });
            }

            @Override
            public void registerAttributes(Supplier<? extends EntityType<? extends LivingEntity>> type, Supplier<Builder> builder) {
               EventBus.get(EventBus.MOD).addListener(event -> event.put(type.get(), builder.get().build()));
            }

            @Override
            public <T extends Mob> void registerPlacement(
               Supplier<EntityType<T>> entity, SpawnPlacementType spawnPlacement, Types heightmap, SpawnPredicate<T> spawnPredicate
            ) {
               EventBus.get(EventBus.MOD).addListener(event -> {
                  if (entity.get() == EntityType.CAMEL) {
                     boolean hasNoMansLand = Environment.hasModLoaded("nomansland");
                     if (hasNoMansLand) {
                        return;
                     }
                  }

                  event.register(entity.get(), spawnPlacement, heightmap, spawnPredicate, Operation.OR);
               });
            }
         }
      );
   }
}
