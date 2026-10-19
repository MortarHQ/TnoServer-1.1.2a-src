package com.tensura_tno.registry;

import com.tensura_tno.entity.spirit.FoxSpiritEntity;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.entity.EntityType.Builder;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.event.entity.EntityAttributeCreationEvent;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public final class TensuraTNOEntities {
   private static final DeferredRegister<EntityType<?>> ENTITIES = DeferredRegister.create(BuiltInRegistries.ENTITY_TYPE, "tensura_tno");
   public static final DeferredHolder<EntityType<?>, EntityType<FoxSpiritEntity>> FOX_SPIRIT = ENTITIES.register(
      "fox_spirit",
      () -> Builder.of(FoxSpiritEntity::new, MobCategory.MISC)
         .sized(0.6F, 0.7F)
         .clientTrackingRange(10)
         .build(ResourceLocation.fromNamespaceAndPath("tensura_tno", "fox_spirit").toString())
   );

   private TensuraTNOEntities() {
   }

   public static void register(IEventBus modBus) {
      ENTITIES.register(modBus);
      modBus.addListener(TensuraTNOEntities::onAttributesCreate);
   }

   private static void onAttributesCreate(EntityAttributeCreationEvent event) {
      event.put((EntityType)FOX_SPIRIT.get(), FoxSpiritEntity.createAttributes().build());
   }
}
