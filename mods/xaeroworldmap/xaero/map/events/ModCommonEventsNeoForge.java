package xaero.map.events;

import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.registries.RegisterEvent;
import xaero.lib.common.config.Config;
import xaero.lib.common.config.single.SingleConfigManager;
import xaero.map.WorldMap;
import xaero.map.common.config.primary.option.WorldMapPrimaryCommonConfigOptions;
import xaero.map.effects.EffectsRegister;

public class ModCommonEventsNeoForge extends ModCommonEvents {
   private final IEventBus fmlEventBus;

   public ModCommonEventsNeoForge(IEventBus fmlEventBus) {
      this.fmlEventBus = fmlEventBus;
   }

   @SubscribeEvent
   public void onRegister(RegisterEvent event) {
      SingleConfigManager<Config> primaryCommonConfig = WorldMap.INSTANCE.getConfigs().getPrimaryCommonConfigManager();
      boolean shouldRegisterEffects = (Boolean)primaryCommonConfig.getEffective(WorldMapPrimaryCommonConfigOptions.REGISTER_EFFECTS);
      if (event.getRegistry() == BuiltInRegistries.MOB_EFFECT && shouldRegisterEffects) {
         new EffectsRegister().registerEffects(effect -> Registry.registerForHolder(BuiltInRegistries.MOB_EFFECT, effect.getRegistryName(), effect));
      }
   }
}
