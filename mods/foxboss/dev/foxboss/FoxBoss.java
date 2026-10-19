package dev.foxboss;

import dev.foxboss.entity.MilimEntity;
import dev.foxboss.network.MilimBossBars;
import dev.foxboss.registry.FoxEntities;
import dev.foxboss.registry.FoxItems;
import net.minecraft.world.entity.EntityType;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.config.ModConfig.Type;
import net.neoforged.neoforge.event.entity.EntityAttributeCreationEvent;

@Mod("foxboss")
public final class FoxBoss {
   public static final String MOD_ID = "foxboss";

   public FoxBoss(IEventBus modBus, ModContainer container) {
      FoxEntities.TYPES.register(modBus);
      FoxItems.ITEMS.register(modBus);
      modBus.addListener(FoxBoss::attributes);
      modBus.addListener(FoxItems::creativeTabContents);
      modBus.addListener(MilimBossBars::register);
      container.registerConfig(Type.COMMON, FoxBossConfig.SPEC);
   }

   private static void attributes(EntityAttributeCreationEvent event) {
      event.put((EntityType)FoxEntities.MILIM.get(), MilimEntity.createAttributes().build());
   }
}
