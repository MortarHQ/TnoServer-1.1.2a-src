package twilightforest.init;

import java.util.List;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.util.random.SimpleWeightedRandomList.Builder;
import net.minecraft.util.valueproviders.ConstantFloat;
import net.minecraft.util.valueproviders.UniformFloat;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.levelgen.VerticalAnchor;
import net.minecraft.world.level.levelgen.carver.CaveCarverConfiguration;
import net.minecraft.world.level.levelgen.carver.ConfiguredWorldCarver;
import net.minecraft.world.level.levelgen.carver.WorldCarver;
import net.minecraft.world.level.levelgen.feature.stateproviders.NoiseProvider;
import net.minecraft.world.level.levelgen.feature.stateproviders.WeightedStateProvider;
import net.minecraft.world.level.levelgen.heightproviders.BiasedToBottomHeight;
import net.minecraft.world.level.levelgen.heightproviders.UniformHeight;
import net.minecraft.world.level.levelgen.synth.NormalNoise.NoiseParameters;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;
import twilightforest.TwilightForestMod;
import twilightforest.data.tags.BlockTagGenerator;
import twilightforest.world.components.TFCavesCarver;

public class TFCaveCarvers {
   public static final DeferredRegister<WorldCarver<?>> CARVER_TYPES = DeferredRegister.create(Registries.CARVER, "twilightforest");
   public static final DeferredHolder<WorldCarver<?>, TFCavesCarver> TF_CAVES = CARVER_TYPES.register(
      "tf_caves",
      () -> new TFCavesCarver(
         CaveCarverConfiguration.CODEC,
         false,
         new NoiseProvider(
            6972119253061020355L,
            new NoiseParameters(0, 1.0, new double[0]),
            0.5F,
            List.of(
               Blocks.DIRT.defaultBlockState(),
               Blocks.DIRT.defaultBlockState(),
               Blocks.ROOTED_DIRT.defaultBlockState(),
               Blocks.DIRT.defaultBlockState(),
               Blocks.DIRT.defaultBlockState(),
               Blocks.COARSE_DIRT.defaultBlockState(),
               Blocks.DIRT.defaultBlockState(),
               Blocks.DIRT.defaultBlockState()
            )
         )
      )
   );
   public static final DeferredHolder<WorldCarver<?>, TFCavesCarver> HIGHLAND_CAVES = CARVER_TYPES.register(
      "highland_caves",
      () -> new TFCavesCarver(
         CaveCarverConfiguration.CODEC,
         true,
         new WeightedStateProvider(new Builder().add(((Block)TFBlocks.TROLLSTEINN.value()).defaultBlockState(), 1).add(Blocks.STONE.defaultBlockState(), 3))
      )
   );
   public static final ResourceKey<ConfiguredWorldCarver<?>> TFCAVES_CONFIGURED = registerKey("tf_caves");
   public static final ResourceKey<ConfiguredWorldCarver<?>> HIGHLANDCAVES_CONFIGURED = registerKey("highland_caves");

   private static ResourceKey<ConfiguredWorldCarver<?>> registerKey(String name) {
      return ResourceKey.create(Registries.CONFIGURED_CARVER, TwilightForestMod.prefix(name));
   }

   public static void bootstrap(BootstrapContext<ConfiguredWorldCarver<?>> context) {
      HolderGetter<Block> blocks = context.lookup(Registries.BLOCK);
      context.register(
         TFCAVES_CONFIGURED,
         ((TFCavesCarver)TF_CAVES.value())
            .configured(
               new CaveCarverConfiguration(
                  0.1F,
                  UniformHeight.of(VerticalAnchor.aboveBottom(16), VerticalAnchor.absolute(-8)),
                  ConstantFloat.of(0.6F),
                  VerticalAnchor.bottom(),
                  blocks.getOrThrow(BlockTagGenerator.CARVER_REPLACEABLES),
                  ConstantFloat.of(1.05F),
                  ConstantFloat.of(1.05F),
                  ConstantFloat.of(-0.7F)
               )
            )
      );
      context.register(
         HIGHLANDCAVES_CONFIGURED,
         ((TFCavesCarver)HIGHLAND_CAVES.value())
            .configured(
               new CaveCarverConfiguration(
                  1.0F,
                  BiasedToBottomHeight.of(VerticalAnchor.absolute(8), VerticalAnchor.absolute(32), 16),
                  ConstantFloat.of(0.6F),
                  VerticalAnchor.bottom(),
                  blocks.getOrThrow(BlockTagGenerator.CARVER_REPLACEABLES),
                  UniformFloat.of(1.1F, 1.3F),
                  ConstantFloat.of(1.1F),
                  UniformFloat.of(-0.9F, -0.65F)
               )
            )
      );
   }
}
