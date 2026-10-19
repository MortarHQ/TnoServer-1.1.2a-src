package net.jadenxgamer.netherexp.registry;

import com.mojang.serialization.MapCodec;
import java.util.function.Supplier;
import net.jadenxgamer.netherexp.NetherExp;
import net.jadenxgamer.netherexp.core.datadriven.Antidote;
import net.jadenxgamer.netherexp.core.datadriven.ApparitionAggressions;
import net.jadenxgamer.netherexp.core.datadriven.ApparitionGargoyleStatues;
import net.jadenxgamer.netherexp.core.datadriven.EctoplasmHaunting;
import net.jadenxgamer.netherexp.core.datadriven.OnDeathGroundConversion;
import net.jadenxgamer.netherexp.core.datadriven.WispArchaeology;
import net.jadenxgamer.netherexp.core.misc.neoforge.conditional_data.ConfigCondition;
import net.jadenxgamer.netherexp.core.misc.neoforge.conditional_data.StartupConfigCondition;
import net.jadenxgamer.netherexp.core.misc.neoforge.glm.ReplaceItemModifier;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.common.conditions.ICondition;
import net.neoforged.neoforge.common.loot.IGlobalLootModifier;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.NewRegistryEvent;
import net.neoforged.neoforge.registries.DataPackRegistryEvent.NewRegistry;

public class JNERegistries {
   public static final DeferredRegister<MapCodec<? extends ICondition>> CONDITION_CODECS = DeferredRegister.create(
      net.neoforged.neoforge.registries.NeoForgeRegistries.Keys.CONDITION_CODECS, "netherexp"
   );
   public static final DeferredRegister<MapCodec<? extends IGlobalLootModifier>> GLOBAL_LOOT_MODIFIERS = DeferredRegister.create(
      net.neoforged.neoforge.registries.NeoForgeRegistries.Keys.GLOBAL_LOOT_MODIFIER_SERIALIZERS, "netherexp"
   );
   public static final Supplier<MapCodec<ConfigCondition>> CONFIG = CONDITION_CODECS.register("config", () -> ConfigCondition.CODEC);
   public static final Supplier<MapCodec<StartupConfigCondition>> STARTUP_CONFIG = CONDITION_CODECS.register(
      "startup_config", () -> StartupConfigCondition.CODEC
   );
   public static final Supplier<MapCodec<ReplaceItemModifier>> REPLACE_ITEM = GLOBAL_LOOT_MODIFIERS.register("replace_item", () -> ReplaceItemModifier.CODEC);

   private static <T> ResourceKey<Registry<T>> key(String name) {
      return ResourceKey.createRegistryKey(NetherExp.netherexpPath(name));
   }

   public static void init(IEventBus eventBus) {
      CONDITION_CODECS.register(eventBus);
      GLOBAL_LOOT_MODIFIERS.register(eventBus);
   }

   public static void registryInit(NewRegistryEvent event) {
   }

   public static void datapackInit(NewRegistry event) {
      event.dataPackRegistry(JNERegistries.Keys.WISP_ARCHAEOLOGY, WispArchaeology.CODEC);
      event.dataPackRegistry(JNERegistries.Keys.ON_DEATH_GROUND_CONVERSION, OnDeathGroundConversion.CODEC);
      event.dataPackRegistry(JNERegistries.Keys.APPARITION_AGGRESSIONS, ApparitionAggressions.CODEC);
      event.dataPackRegistry(JNERegistries.Keys.APPARITION_GARGOYLE_STATUES, ApparitionGargoyleStatues.CODEC);
      event.dataPackRegistry(JNERegistries.Keys.ANTIDOTE, Antidote.CODEC);
      event.dataPackRegistry(JNERegistries.Keys.ECTOPLASM_HAUNTING, EctoplasmHaunting.CODEC);
   }

   public static final class Keys {
      public static final ResourceKey<Registry<WispArchaeology>> WISP_ARCHAEOLOGY = JNERegistries.key("wisp_archaeology");
      public static final ResourceKey<Registry<OnDeathGroundConversion>> ON_DEATH_GROUND_CONVERSION = JNERegistries.key("on_death_ground_conversion");
      public static final ResourceKey<Registry<ApparitionAggressions>> APPARITION_AGGRESSIONS = JNERegistries.key("apparition/aggressions");
      public static final ResourceKey<Registry<ApparitionGargoyleStatues>> APPARITION_GARGOYLE_STATUES = JNERegistries.key("apparition/gargoyle_statues");
      public static final ResourceKey<Registry<Antidote>> ANTIDOTE = JNERegistries.key("antidote");
      public static final ResourceKey<Registry<EctoplasmHaunting>> ECTOPLASM_HAUNTING = JNERegistries.key("ectoplasm_haunting");
   }
}
