package twilightforest.world.registration.surface_rules;

import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.levelgen.Noises;
import net.minecraft.world.level.levelgen.SurfaceRules;
import net.minecraft.world.level.levelgen.VerticalAnchor;
import net.minecraft.world.level.levelgen.SurfaceRules.ConditionSource;
import net.minecraft.world.level.levelgen.SurfaceRules.RuleSource;
import org.jetbrains.annotations.NotNull;
import twilightforest.init.TFBiomes;
import twilightforest.init.TFBlocks;

public class TFSurfaceRules {
   private static final RuleSource BEDROCK = makeStateRule(Blocks.BEDROCK);
   private static final RuleSource GRASS_BLOCK = makeStateRule(Blocks.GRASS_BLOCK);
   private static final RuleSource DIRT = makeStateRule(Blocks.DIRT);
   private static final RuleSource PODZOL = makeStateRule(Blocks.PODZOL);
   private static final RuleSource COARSE_DIRT = makeStateRule(Blocks.COARSE_DIRT);
   private static final RuleSource GRAVEL = makeStateRule(Blocks.GRAVEL);
   private static final RuleSource SAND = makeStateRule(Blocks.SAND);
   private static final RuleSource SANDSTONE = makeStateRule(Blocks.SANDSTONE);
   private static final RuleSource SNOW = makeStateRule(Blocks.SNOW_BLOCK);
   private static final RuleSource WEATHERED_DEADROCK = makeStateRule((Block)TFBlocks.WEATHERED_DEADROCK.get());
   private static final RuleSource CRACKED_DEADROCK = makeStateRule((Block)TFBlocks.CRACKED_DEADROCK.get());
   private static final RuleSource DEADROCK = makeStateRule((Block)TFBlocks.DEADROCK.get());

   private static RuleSource makeStateRule(Block block) {
      return SurfaceRules.state(block.defaultBlockState());
   }

   public static RuleSource tfSurface() {
      RuleSource bedrockLayer = SurfaceRules.ifTrue(
         SurfaceRules.verticalGradient("bedrock_floor", VerticalAnchor.bottom(), VerticalAnchor.aboveBottom(5)), BEDROCK
      );
      return SurfaceRules.sequence(
         new RuleSource[]{bedrockLayer, highlandsSurface(), deadrockSurface(), snowyForestSurface(), glacierSurface(), overworldLikeFloor()}
      );
   }

   @NotNull
   private static RuleSource highlandsSurface() {
      RuleSource podzolFloor = SurfaceRules.sequence(new RuleSource[]{SurfaceRules.ifTrue(SurfaceRules.waterBlockCheck(-1, 0), PODZOL), DIRT});
      RuleSource highlandsSoil = SurfaceRules.ifTrue(
         SurfaceRules.ON_FLOOR,
         SurfaceRules.sequence(
            new RuleSource[]{SurfaceRules.ifTrue(surfaceNoiseAbove(2.25), COARSE_DIRT), SurfaceRules.ifTrue(surfaceNoiseAbove(-2.25), podzolFloor)}
         )
      );
      return SurfaceRules.ifTrue(SurfaceRules.isBiome(new ResourceKey[]{TFBiomes.HIGHLANDS}), highlandsSoil);
   }

   @NotNull
   private static RuleSource deadrockSurface() {
      RuleSource deadrockTerrain = SurfaceRules.sequence(
         new RuleSource[]{
            SurfaceRules.ifTrue(SurfaceRules.ON_FLOOR, WEATHERED_DEADROCK),
            SurfaceRules.ifTrue(SurfaceRules.waterStartCheck(-6, -1), SurfaceRules.ifTrue(SurfaceRules.UNDER_FLOOR, CRACKED_DEADROCK)),
            DEADROCK
         }
      );
      return SurfaceRules.ifTrue(SurfaceRules.isBiome(new ResourceKey[]{TFBiomes.THORNLANDS, TFBiomes.FINAL_PLATEAU}), deadrockTerrain);
   }

   @NotNull
   private static RuleSource snowyForestSurface() {
      RuleSource snowFloor = SurfaceRules.sequence(new RuleSource[]{SurfaceRules.ifTrue(SurfaceRules.waterBlockCheck(-1, 0), SNOW), DIRT});
      RuleSource snowySoil = SurfaceRules.sequence(
         new RuleSource[]{
            SurfaceRules.ifTrue(SurfaceRules.ON_FLOOR, snowFloor),
            SurfaceRules.ifTrue(SurfaceRules.waterStartCheck(-6, -1), SurfaceRules.ifTrue(SurfaceRules.UNDER_FLOOR, DIRT))
         }
      );
      return SurfaceRules.ifTrue(SurfaceRules.isBiome(new ResourceKey[]{TFBiomes.SNOWY_FOREST}), snowySoil);
   }

   @NotNull
   private static RuleSource glacierSurface() {
      RuleSource surfaceUnderPermafrost = SurfaceRules.sequence(
         new RuleSource[]{
            SurfaceRules.ifTrue(SurfaceRules.ON_FLOOR, GRAVEL),
            SurfaceRules.ifTrue(SurfaceRules.waterStartCheck(-6, -1), SurfaceRules.ifTrue(SurfaceRules.UNDER_FLOOR, GRAVEL))
         }
      );
      return SurfaceRules.ifTrue(SurfaceRules.isBiome(new ResourceKey[]{TFBiomes.GLACIER}), surfaceUnderPermafrost);
   }

   @NotNull
   private static RuleSource overworldLikeFloor() {
      RuleSource riverLakeBeds = SurfaceRules.ifTrue(
         SurfaceRules.isBiome(new ResourceKey[]{TFBiomes.LAKE, TFBiomes.STREAM}),
         SurfaceRules.sequence(
            new RuleSource[]{
               SurfaceRules.ifTrue(SurfaceRules.ON_CEILING, SANDSTONE), SurfaceRules.ifTrue(SurfaceRules.waterBlockCheck(-1, 0), GRASS_BLOCK), SAND
            }
         )
      );
      RuleSource swampBeds = SurfaceRules.ifTrue(
         SurfaceRules.isBiome(new ResourceKey[]{TFBiomes.SWAMP, TFBiomes.FIRE_SWAMP}),
         SurfaceRules.sequence(new RuleSource[]{SurfaceRules.ifTrue(SurfaceRules.waterBlockCheck(-1, 0), GRASS_BLOCK), DIRT})
      );
      RuleSource grassAboveSeaLevel = SurfaceRules.ifTrue(SurfaceRules.yStartCheck(VerticalAnchor.absolute(-4), 1), GRASS_BLOCK);
      RuleSource grassSurface = SurfaceRules.ifTrue(SurfaceRules.waterBlockCheck(-1, 0), grassAboveSeaLevel);
      RuleSource underwaterSurface = SurfaceRules.ifTrue(
         SurfaceRules.not(SurfaceRules.yStartCheck(VerticalAnchor.absolute(-4), 1)),
         SurfaceRules.ifTrue(SurfaceRules.not(SurfaceRules.waterBlockCheck(-1, 0)), DIRT)
      );
      RuleSource onFloor = SurfaceRules.ifTrue(
         SurfaceRules.ON_FLOOR, SurfaceRules.sequence(new RuleSource[]{riverLakeBeds, swampBeds, grassSurface, underwaterSurface})
      );
      RuleSource underFloor = SurfaceRules.ifTrue(
         SurfaceRules.waterStartCheck(-6, -1),
         SurfaceRules.ifTrue(SurfaceRules.yStartCheck(VerticalAnchor.absolute(-4), 1), SurfaceRules.ifTrue(SurfaceRules.UNDER_FLOOR, DIRT))
      );
      return SurfaceRules.sequence(new RuleSource[]{onFloor, underFloor});
   }

   private static ConditionSource surfaceNoiseAbove(double p_194809_) {
      return SurfaceRules.noiseCondition(Noises.SURFACE, p_194809_ / 8.25, Double.MAX_VALUE);
   }
}
