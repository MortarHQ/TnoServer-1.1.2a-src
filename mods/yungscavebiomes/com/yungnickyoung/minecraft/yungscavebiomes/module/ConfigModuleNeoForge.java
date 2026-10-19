package com.yungnickyoung.minecraft.yungscavebiomes.module;

import com.yungnickyoung.minecraft.yungscavebiomes.YungsCaveBiomesCommon;
import com.yungnickyoung.minecraft.yungscavebiomes.config.YCBNeoForgeConfig;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.config.ModConfig.Type;
import net.neoforged.fml.event.config.ModConfigEvent;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.level.LevelEvent.Load;

public class ConfigModuleNeoForge {
   public static void init(IEventBus eventBus, ModContainer container) {
      container.registerConfig(Type.COMMON, YCBNeoForgeConfig.SPEC, "yungscavebiomes-neoforge-1_21_1.toml");
      NeoForge.EVENT_BUS.addListener(ConfigModuleNeoForge::onWorldLoad);
      eventBus.addListener(ConfigModuleNeoForge::onConfigChange);
   }

   private static void onWorldLoad(Load event) {
      bakeConfig();
   }

   private static void onConfigChange(ModConfigEvent event) {
      if (event.getConfig().getSpec() == YCBNeoForgeConfig.SPEC) {
         bakeConfig();
      }
   }

   private static void bakeConfig() {
      YungsCaveBiomesCommon.CONFIG.lostCaves.enableSandstorms = (Boolean)YCBNeoForgeConfig.lostCaves.enableSandstorms.get();
      YungsCaveBiomesCommon.CONFIG.lostCaves.minSandstormDuration = (Integer)YCBNeoForgeConfig.lostCaves.minSandstormDuration.get();
      YungsCaveBiomesCommon.CONFIG.lostCaves.maxSandstormDuration = (Integer)YCBNeoForgeConfig.lostCaves.maxSandstormDuration.get();
      YungsCaveBiomesCommon.CONFIG.lostCaves.minTimeBetweenSandstorms = (Integer)YCBNeoForgeConfig.lostCaves.minTimeBetweenSandstorms.get();
      YungsCaveBiomesCommon.CONFIG.lostCaves.maxTimeBetweenSandstorms = (Integer)YCBNeoForgeConfig.lostCaves.maxTimeBetweenSandstorms.get();
      YungsCaveBiomesCommon.CONFIG.lostCaves.extraSandstormParticles = (Boolean)YCBNeoForgeConfig.lostCaves.extraSandstormParticles.get();
      YungsCaveBiomesCommon.CONFIG.lostCaves.noiseParameters.temperatureMin = ((Double)YCBNeoForgeConfig.lostCaves.noiseParameters.temperatureMin.get())
         .floatValue();
      YungsCaveBiomesCommon.CONFIG.lostCaves.noiseParameters.temperatureMax = ((Double)YCBNeoForgeConfig.lostCaves.noiseParameters.temperatureMax.get())
         .floatValue();
      YungsCaveBiomesCommon.CONFIG.lostCaves.noiseParameters.humidityMin = ((Double)YCBNeoForgeConfig.lostCaves.noiseParameters.humidityMin.get()).floatValue();
      YungsCaveBiomesCommon.CONFIG.lostCaves.noiseParameters.humidityMax = ((Double)YCBNeoForgeConfig.lostCaves.noiseParameters.humidityMax.get()).floatValue();
      YungsCaveBiomesCommon.CONFIG.lostCaves.noiseParameters.continentalnessMin = ((Double)YCBNeoForgeConfig.lostCaves.noiseParameters.continentalnessMin.get())
         .floatValue();
      YungsCaveBiomesCommon.CONFIG.lostCaves.noiseParameters.continentalnessMax = ((Double)YCBNeoForgeConfig.lostCaves.noiseParameters.continentalnessMax.get())
         .floatValue();
      YungsCaveBiomesCommon.CONFIG.lostCaves.noiseParameters.erosionMin = ((Double)YCBNeoForgeConfig.lostCaves.noiseParameters.erosionMin.get()).floatValue();
      YungsCaveBiomesCommon.CONFIG.lostCaves.noiseParameters.erosionMax = ((Double)YCBNeoForgeConfig.lostCaves.noiseParameters.erosionMax.get()).floatValue();
      YungsCaveBiomesCommon.CONFIG.lostCaves.noiseParameters.depthMin = ((Double)YCBNeoForgeConfig.lostCaves.noiseParameters.depthMin.get()).floatValue();
      YungsCaveBiomesCommon.CONFIG.lostCaves.noiseParameters.depthMax = ((Double)YCBNeoForgeConfig.lostCaves.noiseParameters.depthMax.get()).floatValue();
      YungsCaveBiomesCommon.CONFIG.lostCaves.noiseParameters.weirdnessMin = ((Double)YCBNeoForgeConfig.lostCaves.noiseParameters.weirdnessMin.get())
         .floatValue();
      YungsCaveBiomesCommon.CONFIG.lostCaves.noiseParameters.weirdnessMax = ((Double)YCBNeoForgeConfig.lostCaves.noiseParameters.weirdnessMax.get())
         .floatValue();
      YungsCaveBiomesCommon.CONFIG.lostCaves.noiseParameters.offset = ((Double)YCBNeoForgeConfig.lostCaves.noiseParameters.offset.get()).floatValue();
      YungsCaveBiomesCommon.CONFIG.frostedCaves.noiseParameters.temperatureMin = ((Double)YCBNeoForgeConfig.frostedCaves.noiseParameters.temperatureMin.get())
         .floatValue();
      YungsCaveBiomesCommon.CONFIG.frostedCaves.noiseParameters.temperatureMax = ((Double)YCBNeoForgeConfig.frostedCaves.noiseParameters.temperatureMax.get())
         .floatValue();
      YungsCaveBiomesCommon.CONFIG.frostedCaves.noiseParameters.humidityMin = ((Double)YCBNeoForgeConfig.frostedCaves.noiseParameters.humidityMin.get())
         .floatValue();
      YungsCaveBiomesCommon.CONFIG.frostedCaves.noiseParameters.humidityMax = ((Double)YCBNeoForgeConfig.frostedCaves.noiseParameters.humidityMax.get())
         .floatValue();
      YungsCaveBiomesCommon.CONFIG.frostedCaves.noiseParameters.continentalnessMin = ((Double)YCBNeoForgeConfig.frostedCaves
            .noiseParameters
            .continentalnessMin
            .get())
         .floatValue();
      YungsCaveBiomesCommon.CONFIG.frostedCaves.noiseParameters.continentalnessMax = ((Double)YCBNeoForgeConfig.frostedCaves
            .noiseParameters
            .continentalnessMax
            .get())
         .floatValue();
      YungsCaveBiomesCommon.CONFIG.frostedCaves.noiseParameters.erosionMin = ((Double)YCBNeoForgeConfig.frostedCaves.noiseParameters.erosionMin.get())
         .floatValue();
      YungsCaveBiomesCommon.CONFIG.frostedCaves.noiseParameters.erosionMax = ((Double)YCBNeoForgeConfig.frostedCaves.noiseParameters.erosionMax.get())
         .floatValue();
      YungsCaveBiomesCommon.CONFIG.frostedCaves.noiseParameters.depthMin = ((Double)YCBNeoForgeConfig.frostedCaves.noiseParameters.depthMin.get()).floatValue();
      YungsCaveBiomesCommon.CONFIG.frostedCaves.noiseParameters.depthMax = ((Double)YCBNeoForgeConfig.frostedCaves.noiseParameters.depthMax.get()).floatValue();
      YungsCaveBiomesCommon.CONFIG.frostedCaves.noiseParameters.weirdnessMin = ((Double)YCBNeoForgeConfig.frostedCaves.noiseParameters.weirdnessMin.get())
         .floatValue();
      YungsCaveBiomesCommon.CONFIG.frostedCaves.noiseParameters.weirdnessMax = ((Double)YCBNeoForgeConfig.frostedCaves.noiseParameters.weirdnessMax.get())
         .floatValue();
      YungsCaveBiomesCommon.CONFIG.frostedCaves.noiseParameters.offset = ((Double)YCBNeoForgeConfig.frostedCaves.noiseParameters.offset.get()).floatValue();
      YungsCaveBiomesCommon.CONFIG.other.vanillaBiomeModifications.dripstoneCaves.temperatureMin = ((Double)YCBNeoForgeConfig.other
            .vanillaBiomeModifications
            .dripstoneCaves
            .temperatureMin
            .get())
         .floatValue();
      YungsCaveBiomesCommon.CONFIG.other.vanillaBiomeModifications.dripstoneCaves.temperatureMax = ((Double)YCBNeoForgeConfig.other
            .vanillaBiomeModifications
            .dripstoneCaves
            .temperatureMax
            .get())
         .floatValue();
      YungsCaveBiomesCommon.CONFIG.other.vanillaBiomeModifications.dripstoneCaves.humidityMin = ((Double)YCBNeoForgeConfig.other
            .vanillaBiomeModifications
            .dripstoneCaves
            .humidityMin
            .get())
         .floatValue();
      YungsCaveBiomesCommon.CONFIG.other.vanillaBiomeModifications.dripstoneCaves.humidityMax = ((Double)YCBNeoForgeConfig.other
            .vanillaBiomeModifications
            .dripstoneCaves
            .humidityMax
            .get())
         .floatValue();
      YungsCaveBiomesCommon.CONFIG.other.vanillaBiomeModifications.dripstoneCaves.continentalnessMin = ((Double)YCBNeoForgeConfig.other
            .vanillaBiomeModifications
            .dripstoneCaves
            .continentalnessMin
            .get())
         .floatValue();
      YungsCaveBiomesCommon.CONFIG.other.vanillaBiomeModifications.dripstoneCaves.continentalnessMax = ((Double)YCBNeoForgeConfig.other
            .vanillaBiomeModifications
            .dripstoneCaves
            .continentalnessMax
            .get())
         .floatValue();
      YungsCaveBiomesCommon.CONFIG.other.vanillaBiomeModifications.dripstoneCaves.erosionMin = ((Double)YCBNeoForgeConfig.other
            .vanillaBiomeModifications
            .dripstoneCaves
            .erosionMin
            .get())
         .floatValue();
      YungsCaveBiomesCommon.CONFIG.other.vanillaBiomeModifications.dripstoneCaves.erosionMax = ((Double)YCBNeoForgeConfig.other
            .vanillaBiomeModifications
            .dripstoneCaves
            .erosionMax
            .get())
         .floatValue();
      YungsCaveBiomesCommon.CONFIG.other.vanillaBiomeModifications.dripstoneCaves.depthMin = ((Double)YCBNeoForgeConfig.other
            .vanillaBiomeModifications
            .dripstoneCaves
            .depthMin
            .get())
         .floatValue();
      YungsCaveBiomesCommon.CONFIG.other.vanillaBiomeModifications.dripstoneCaves.depthMax = ((Double)YCBNeoForgeConfig.other
            .vanillaBiomeModifications
            .dripstoneCaves
            .depthMax
            .get())
         .floatValue();
      YungsCaveBiomesCommon.CONFIG.other.vanillaBiomeModifications.dripstoneCaves.weirdnessMin = ((Double)YCBNeoForgeConfig.other
            .vanillaBiomeModifications
            .dripstoneCaves
            .weirdnessMin
            .get())
         .floatValue();
      YungsCaveBiomesCommon.CONFIG.other.vanillaBiomeModifications.dripstoneCaves.weirdnessMax = ((Double)YCBNeoForgeConfig.other
            .vanillaBiomeModifications
            .dripstoneCaves
            .weirdnessMax
            .get())
         .floatValue();
      YungsCaveBiomesCommon.CONFIG.other.vanillaBiomeModifications.dripstoneCaves.offset = ((Double)YCBNeoForgeConfig.other
            .vanillaBiomeModifications
            .dripstoneCaves
            .offset
            .get())
         .floatValue();
   }
}
