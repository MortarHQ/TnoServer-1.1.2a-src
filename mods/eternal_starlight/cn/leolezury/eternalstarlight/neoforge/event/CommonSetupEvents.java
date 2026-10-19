package cn.leolezury.eternalstarlight.neoforge.event;

import cn.leolezury.eternalstarlight.common.handler.ESCommonSetupHandler;
import cn.leolezury.eternalstarlight.common.registry.ESBlocks;
import cn.leolezury.eternalstarlight.neoforge.registry.ESFluidTypes;
import net.minecraft.tags.FluidTags;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.SpawnPlacementType;
import net.minecraft.world.entity.SpawnPlacements.SpawnPredicate;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.levelgen.Heightmap.Types;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.neoforged.neoforge.event.entity.EntityAttributeCreationEvent;
import net.neoforged.neoforge.event.entity.RegisterSpawnPlacementsEvent;
import net.neoforged.neoforge.event.entity.RegisterSpawnPlacementsEvent.Operation;
import net.neoforged.neoforge.fluids.FluidInteractionRegistry;
import net.neoforged.neoforge.fluids.FluidInteractionRegistry.InteractionInformation;
import org.jetbrains.annotations.Nullable;

@EventBusSubscriber(modid = "eternal_starlight")
public class CommonSetupEvents {
   @SubscribeEvent
   private static void onSetup(FMLCommonSetupEvent event) {
      event.enqueueWork(
         () -> FluidInteractionRegistry.addInteraction(
            ESFluidTypes.ETHER.get(),
            new InteractionInformation(
               (level, blockPos, relativePos, fluidState) -> level.getFluidState(relativePos).is(FluidTags.LAVA)
                  && !level.getBlockState(relativePos).is((Block)ESBlocks.ETHER.get()),
               ESBlocks.MOLTEN_STELLAGMITE.get().defaultBlockState()
            )
         )
      );
      event.enqueueWork(
         () -> FluidInteractionRegistry.addInteraction(
            ESFluidTypes.ETHER.get(),
            new InteractionInformation(
               (level, blockPos, relativePos, fluidState) -> !level.getFluidState(relativePos).isEmpty()
                  && !level.getFluidState(relativePos).is(FluidTags.LAVA)
                  && !level.getBlockState(relativePos).is((Block)ESBlocks.ETHER.get()),
               ESBlocks.THIOQUARTZ_BLOCK.get().defaultBlockState()
            )
         )
      );
      event.enqueueWork(ESCommonSetupHandler::commonSetup);
   }

   @SubscribeEvent
   private static void onAttributeCreate(EntityAttributeCreationEvent event) {
      ESCommonSetupHandler.createAttributes(event::put);
   }

   @SubscribeEvent
   private static void onSpawnPlacementRegister(RegisterSpawnPlacementsEvent event) {
      ESCommonSetupHandler.SpawnPlacementRegisterStrategy strategy = new ESCommonSetupHandler.SpawnPlacementRegisterStrategy() {
         @Override
         public <T extends Mob> void register(
            EntityType<T> entityType, @Nullable SpawnPlacementType placementType, @Nullable Types heightmap, SpawnPredicate<T> predicate
         ) {
            event.register(entityType, placementType, heightmap, predicate, Operation.AND);
         }
      };
      ESCommonSetupHandler.registerSpawnPlacements(strategy);
   }
}
