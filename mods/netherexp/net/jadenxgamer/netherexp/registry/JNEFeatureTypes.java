package net.jadenxgamer.netherexp.registry;

import java.util.function.Supplier;
import net.jadenxgamer.netherexp.core.worldgen.feature.BlackIceClusterFeature;
import net.jadenxgamer.netherexp.core.worldgen.feature.BlottedPatchFeature;
import net.jadenxgamer.netherexp.core.worldgen.feature.JNEHugeFungusFeature;
import net.jadenxgamer.netherexp.core.worldgen.feature.MoundFeature;
import net.jadenxgamer.netherexp.core.worldgen.feature.NetherSpeleothemFeature;
import net.jadenxgamer.netherexp.core.worldgen.feature.NotGarbageLargeDripstoneFeature;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.DripstoneClusterConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;

public class JNEFeatureTypes {
   public static final DeferredRegister<Feature<?>> FEATURES = DeferredRegister.create(BuiltInRegistries.FEATURE, "netherexp");
   public static final Supplier<Feature<?>> NOT_GARBAGE_LARGE_DRIPSTONE = FEATURES.register(
      "not_garbage_large_dripstone", () -> new NotGarbageLargeDripstoneFeature(NotGarbageLargeDripstoneFeature.Config.CODEC)
   );
   public static final Supplier<Feature<?>> JNE_HUGE_FUNGUS = FEATURES.register(
      "jne_huge_fungus", () -> new JNEHugeFungusFeature(JNEHugeFungusFeature.Config.CODEC)
   );
   public static final Supplier<Feature<?>> BLOTTED_PATCH = FEATURES.register("blotted_patch", () -> new BlottedPatchFeature(BlottedPatchFeature.Config.CODEC));
   public static final Supplier<Feature<?>> MOUND = FEATURES.register("mound", () -> new MoundFeature(MoundFeature.Config.CODEC));
   public static final Supplier<Feature<?>> NETHER_SPELEOTHEM = FEATURES.register(
      "nether_speleothem", () -> new NetherSpeleothemFeature(NoneFeatureConfiguration.CODEC)
   );
   public static final Supplier<Feature<?>> BLACK_ICE_CLUSTER = FEATURES.register(
      "black_ice_cluster", () -> new BlackIceClusterFeature(DripstoneClusterConfiguration.CODEC)
   );

   public static void init(IEventBus eventBus) {
      FEATURES.register(eventBus);
   }
}
