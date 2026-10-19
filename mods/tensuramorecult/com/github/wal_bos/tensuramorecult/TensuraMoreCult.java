package com.github.wal_bos.tensuramorecult;

import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.RangedAttribute;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.config.ModConfig.Type;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

@Mod("tensuramorecult")
public final class TensuraMoreCult {
   public static final String ID = "tensuramorecult";
   private static final DeferredRegister<Attribute> ATTRIBUTES = DeferredRegister.create(Registries.ATTRIBUTE, "tensuramorecult");
   public static final DeferredHolder<Attribute, Attribute> DAMAGE_MULTIPLIER = ATTRIBUTES.register(
      "damage_multiplier", () -> new RangedAttribute("attribute.name.tensuramorecult.damage_multiplier", 1.0, 0.0, 1000.0).setSyncable(true)
   );

   public TensuraMoreCult(IEventBus bus, ModContainer container) {
      ATTRIBUTES.register(bus);
      bus.addListener(CultCombat::attributes);
      NeoForge.EVENT_BUS.register(CultCombat.class);
      NeoForge.EVENT_BUS.register(CultEquipment.class);
      container.registerConfig(Type.SERVER, CultConfig.SPEC);
   }

   public static ResourceLocation id(String path) {
      return ResourceLocation.fromNamespaceAndPath("tensuramorecult", path);
   }
}
