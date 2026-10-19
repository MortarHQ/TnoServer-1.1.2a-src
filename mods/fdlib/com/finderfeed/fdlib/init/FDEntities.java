package com.finderfeed.fdlib.init;

import com.finderfeed.fdlib.systems.cutscenes.ClientCameraEntity;
import java.util.function.Supplier;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.entity.EntityType.Builder;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.common.EventBusSubscriber.Bus;
import net.neoforged.neoforge.event.entity.EntityAttributeCreationEvent;
import net.neoforged.neoforge.registries.DeferredRegister;

public class FDEntities {
   public static final DeferredRegister<EntityType<?>> ENTITY_TYPES = DeferredRegister.create(Registries.ENTITY_TYPE, "fdlib");
   public static final Supplier<EntityType<ClientCameraEntity>> CLIENT_CAMERA = ENTITY_TYPES.register(
      "client_camera", () -> Builder.of(ClientCameraEntity::new, MobCategory.MISC).updateInterval(1).sized(0.2F, 0.2F).build("client_camera")
   );

   @EventBusSubscriber(bus = Bus.MOD, modid = "fdlib")
   public static class Attributes {
      @SubscribeEvent
      public static void addAttributes(EntityAttributeCreationEvent event) {
         event.put(FDEntities.CLIENT_CAMERA.get(), LivingEntity.createLivingAttributes().build());
      }
   }
}
