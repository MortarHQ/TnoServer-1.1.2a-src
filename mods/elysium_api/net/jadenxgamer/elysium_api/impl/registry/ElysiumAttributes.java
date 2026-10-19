package net.jadenxgamer.elysium_api.impl.registry;

import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;

public class ElysiumAttributes {
   public static final DeferredRegister<Attribute> ATTRIBUTES = DeferredRegister.create(BuiltInRegistries.ATTRIBUTE, "elysium_api");

   public static void init(IEventBus eventBus) {
      ATTRIBUTES.register(eventBus);
   }
}
