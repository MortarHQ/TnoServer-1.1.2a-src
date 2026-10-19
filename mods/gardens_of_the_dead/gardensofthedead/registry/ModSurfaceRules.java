package gardensofthedead.registry;

import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.levelgen.Noises;
import net.minecraft.world.level.levelgen.SurfaceRules;
import net.minecraft.world.level.levelgen.VerticalAnchor;
import net.minecraft.world.level.levelgen.SurfaceRules.ConditionSource;
import net.minecraft.world.level.levelgen.SurfaceRules.RuleSource;

public class ModSurfaceRules {
   private static final RuleSource BEDROCK = block(Blocks.BEDROCK);
   private static final RuleSource LAVA = block(Blocks.LAVA);
   private static final RuleSource GRAVEL = block(Blocks.GRAVEL);
   private static final RuleSource NETHERRACK = block(Blocks.NETHERRACK);
   private static final RuleSource SOUL_SAND = block(Blocks.SOUL_SAND);
   private static final RuleSource SOUL_SOIL = block(Blocks.SOUL_SOIL);
   private static final RuleSource NETHER_WART_BLOCK = block(Blocks.NETHER_WART_BLOCK);
   private static final RuleSource CRIMSON_NYLIUM = block(Blocks.CRIMSON_NYLIUM);

   public static RuleSource makeRules() {
      ConditionSource aboveLava = SurfaceRules.yBlockCheck(VerticalAnchor.absolute(31), 0);
      RuleSource commonRules = SurfaceRules.sequence(
         new RuleSource[]{
            SurfaceRules.ifTrue(SurfaceRules.verticalGradient("bedrock_floor", VerticalAnchor.bottom(), VerticalAnchor.aboveBottom(5)), BEDROCK),
            SurfaceRules.ifTrue(SurfaceRules.not(SurfaceRules.verticalGradient("bedrock_roof", VerticalAnchor.belowTop(5), VerticalAnchor.top())), BEDROCK),
            SurfaceRules.ifTrue(SurfaceRules.yBlockCheck(VerticalAnchor.belowTop(5), 0), NETHERRACK),
            SurfaceRules.ifTrue(SurfaceRules.ON_FLOOR, SurfaceRules.ifTrue(SurfaceRules.not(aboveLava), SurfaceRules.ifTrue(SurfaceRules.hole(), LAVA)))
         }
      );
      RuleSource soulblightForest = SurfaceRules.sequence(
         new RuleSource[]{
            commonRules,
            SurfaceRules.ifTrue(
               SurfaceRules.UNDER_CEILING,
               SurfaceRules.sequence(
                  new RuleSource[]{
                     SurfaceRules.ifTrue(SurfaceRules.noiseCondition(Noises.NETHER_STATE_SELECTOR, 0.3), SOUL_SAND),
                     SurfaceRules.ifTrue(SurfaceRules.noiseCondition(Noises.NETHER_STATE_SELECTOR, -0.2), SOUL_SOIL),
                     NETHERRACK
                  }
               )
            ),
            SurfaceRules.ifTrue(
               SurfaceRules.UNDER_FLOOR,
               SurfaceRules.sequence(
                  new RuleSource[]{gravelBeach(), SurfaceRules.ifTrue(SurfaceRules.noiseCondition(Noises.NETHER_STATE_SELECTOR, 0.3), SOUL_SAND), SOUL_SOIL}
               )
            ),
            NETHERRACK
         }
      );
      RuleSource whistlingWoods = SurfaceRules.sequence(
         new RuleSource[]{
            commonRules,
            SurfaceRules.ifTrue(
               SurfaceRules.ON_FLOOR,
               SurfaceRules.ifTrue(
                  SurfaceRules.not(SurfaceRules.noiseCondition(Noises.NETHERRACK, 0.4)),
                  SurfaceRules.ifTrue(
                     aboveLava,
                     SurfaceRules.sequence(
                        new RuleSource[]{SurfaceRules.ifTrue(SurfaceRules.noiseCondition(Noises.NETHER_WART, 1.17), NETHER_WART_BLOCK), CRIMSON_NYLIUM}
                     )
                  )
               )
            ),
            NETHERRACK
         }
      );
      return SurfaceRules.sequence(
         new RuleSource[]{
            SurfaceRules.ifTrue(SurfaceRules.isBiome(new ResourceKey[]{ModBiomes.SOULBLIGHT_FOREST}), soulblightForest),
            SurfaceRules.ifTrue(SurfaceRules.isBiome(new ResourceKey[]{ModBiomes.WHISTLING_WOODS}), whistlingWoods)
         }
      );
   }

   private static RuleSource gravelBeach() {
      ConditionSource above30 = SurfaceRules.yStartCheck(VerticalAnchor.absolute(30), 0);
      ConditionSource below35 = SurfaceRules.not(SurfaceRules.yStartCheck(VerticalAnchor.absolute(35), 0));
      ConditionSource patchNoise = SurfaceRules.noiseCondition(Noises.PATCH, -0.012);
      return SurfaceRules.ifTrue(patchNoise, SurfaceRules.ifTrue(above30, SurfaceRules.ifTrue(below35, GRAVEL)));
   }

   private static RuleSource block(Block block) {
      return SurfaceRules.state(block.defaultBlockState());
   }
}
