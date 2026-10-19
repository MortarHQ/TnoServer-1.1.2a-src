package net.mcreator.rpgstylemorebosses.init;

import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class RpgStyleMoreBossesModSounds {
   public static final DeferredRegister<SoundEvent> REGISTRY = DeferredRegister.create(Registries.SOUND_EVENT, "rpg_style_more_bosses");
   public static final DeferredHolder<SoundEvent, SoundEvent> BLOCK1 = REGISTRY.register(
      "block1", () -> SoundEvent.createVariableRangeEvent(ResourceLocation.fromNamespaceAndPath("rpg_style_more_bosses", "block1"))
   );
   public static final DeferredHolder<SoundEvent, SoundEvent> BLOCK2 = REGISTRY.register(
      "block2", () -> SoundEvent.createVariableRangeEvent(ResourceLocation.fromNamespaceAndPath("rpg_style_more_bosses", "block2"))
   );
   public static final DeferredHolder<SoundEvent, SoundEvent> PUCNH1 = REGISTRY.register(
      "pucnh1", () -> SoundEvent.createVariableRangeEvent(ResourceLocation.fromNamespaceAndPath("rpg_style_more_bosses", "pucnh1"))
   );
   public static final DeferredHolder<SoundEvent, SoundEvent> PUNCH2 = REGISTRY.register(
      "punch2", () -> SoundEvent.createVariableRangeEvent(ResourceLocation.fromNamespaceAndPath("rpg_style_more_bosses", "punch2"))
   );
   public static final DeferredHolder<SoundEvent, SoundEvent> HEAVY_PUNCH = REGISTRY.register(
      "heavy_punch", () -> SoundEvent.createVariableRangeEvent(ResourceLocation.fromNamespaceAndPath("rpg_style_more_bosses", "heavy_punch"))
   );
   public static final DeferredHolder<SoundEvent, SoundEvent> WHOOSH = REGISTRY.register(
      "whoosh", () -> SoundEvent.createVariableRangeEvent(ResourceLocation.fromNamespaceAndPath("rpg_style_more_bosses", "whoosh"))
   );
   public static final DeferredHolder<SoundEvent, SoundEvent> DEVILHAND = REGISTRY.register(
      "devilhand", () -> SoundEvent.createVariableRangeEvent(ResourceLocation.fromNamespaceAndPath("rpg_style_more_bosses", "devilhand"))
   );
   public static final DeferredHolder<SoundEvent, SoundEvent> PLANETSIDELASER2 = REGISTRY.register(
      "planetsidelaser2", () -> SoundEvent.createVariableRangeEvent(ResourceLocation.fromNamespaceAndPath("rpg_style_more_bosses", "planetsidelaser2"))
   );
   public static final DeferredHolder<SoundEvent, SoundEvent> PLANETSIDELASER1 = REGISTRY.register(
      "planetsidelaser1", () -> SoundEvent.createVariableRangeEvent(ResourceLocation.fromNamespaceAndPath("rpg_style_more_bosses", "planetsidelaser1"))
   );
   public static final DeferredHolder<SoundEvent, SoundEvent> LASERTAGRETPLANETSIDE = REGISTRY.register(
      "lasertagretplanetside",
      () -> SoundEvent.createVariableRangeEvent(ResourceLocation.fromNamespaceAndPath("rpg_style_more_bosses", "lasertagretplanetside"))
   );
   public static final DeferredHolder<SoundEvent, SoundEvent> MAXLANDPLANETSIDE = REGISTRY.register(
      "maxlandplanetside", () -> SoundEvent.createVariableRangeEvent(ResourceLocation.fromNamespaceAndPath("rpg_style_more_bosses", "maxlandplanetside"))
   );
   public static final DeferredHolder<SoundEvent, SoundEvent> MAXJUMPPLANETSIDE = REGISTRY.register(
      "maxjumpplanetside", () -> SoundEvent.createVariableRangeEvent(ResourceLocation.fromNamespaceAndPath("rpg_style_more_bosses", "maxjumpplanetside"))
   );
   public static final DeferredHolder<SoundEvent, SoundEvent> MAXWALKPLANETSIDE = REGISTRY.register(
      "maxwalkplanetside", () -> SoundEvent.createVariableRangeEvent(ResourceLocation.fromNamespaceAndPath("rpg_style_more_bosses", "maxwalkplanetside"))
   );
   public static final DeferredHolder<SoundEvent, SoundEvent> CATMEW = REGISTRY.register(
      "catmew", () -> SoundEvent.createVariableRangeEvent(ResourceLocation.fromNamespaceAndPath("rpg_style_more_bosses", "catmew"))
   );
   public static final DeferredHolder<SoundEvent, SoundEvent> EXPLOSION_SMALL_PLANETSIDE1 = REGISTRY.register(
      "explosion_small_planetside1",
      () -> SoundEvent.createVariableRangeEvent(ResourceLocation.fromNamespaceAndPath("rpg_style_more_bosses", "explosion_small_planetside1"))
   );
   public static final DeferredHolder<SoundEvent, SoundEvent> EXPLOSION_SMALL_PLANETSIDE2 = REGISTRY.register(
      "explosion_small_planetside2",
      () -> SoundEvent.createVariableRangeEvent(ResourceLocation.fromNamespaceAndPath("rpg_style_more_bosses", "explosion_small_planetside2"))
   );
   public static final DeferredHolder<SoundEvent, SoundEvent> EXPLOSION_SMALL_PLANETSIDE3 = REGISTRY.register(
      "explosion_small_planetside3",
      () -> SoundEvent.createVariableRangeEvent(ResourceLocation.fromNamespaceAndPath("rpg_style_more_bosses", "explosion_small_planetside3"))
   );
}
