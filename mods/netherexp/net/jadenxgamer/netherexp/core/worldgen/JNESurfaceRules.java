package net.jadenxgamer.netherexp.core.worldgen;

import net.jadenxgamer.elysium_api.api.surface_rules.ElysiumSurfaceRules;
import net.jadenxgamer.netherexp.core.keys.JNEBiomes;
import net.jadenxgamer.netherexp.core.keys.JNETags;
import net.jadenxgamer.netherexp.registry.JNEBlocks;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.levelgen.Noises;
import net.minecraft.world.level.levelgen.SurfaceRules;
import net.minecraft.world.level.levelgen.VerticalAnchor;
import net.minecraft.world.level.levelgen.SurfaceRules.RuleSource;

public class JNESurfaceRules {
   private static final RuleSource BEDROCK = SurfaceRules.state(Blocks.BEDROCK.defaultBlockState());
   private static final RuleSource NETHERRACK = SurfaceRules.state(Blocks.NETHERRACK.defaultBlockState());
   private static final RuleSource SOUL_SAND = SurfaceRules.state(Blocks.SOUL_SAND.defaultBlockState());
   private static final RuleSource SOUL_SOIL = SurfaceRules.state(Blocks.SOUL_SOIL.defaultBlockState());
   private static final RuleSource SOUL_PERMAFROST = SurfaceRules.state(JNEBlocks.SOUL_PERMAFROST.get().defaultBlockState());
   private static final RuleSource GRAVEL = SurfaceRules.state(Blocks.GRAVEL.defaultBlockState());
   private static final RuleSource SILT = SurfaceRules.state(JNEBlocks.SILT.get().defaultBlockState());

   public static RuleSource init() {
      return SurfaceRules.sequence(
         new RuleSource[]{
            JNESurfaceRules.Global.bedrockFloor(),
            JNESurfaceRules.Global.bedrockRoof(),
            JNESurfaceRules.Global.siltShoreline(),
            JNESurfaceRules.BiomeSpecific.blackIceGlaciers()
         }
      );
   }

   private static class BiomeSpecific {
      private static RuleSource blackIceGlaciers() {
         return SurfaceRules.ifTrue(
            SurfaceRules.isBiome(new ResourceKey[]{JNEBiomes.BLACK_ICE_GLACIERS}),
            SurfaceRules.sequence(
               new RuleSource[]{
                  SurfaceRules.ifTrue(
                     SurfaceRules.UNDER_CEILING,
                     SurfaceRules.sequence(
                        new RuleSource[]{
                           SurfaceRules.ifTrue(SurfaceRules.noiseCondition(Noises.NETHER_STATE_SELECTOR, 0.0, Double.MAX_VALUE), JNESurfaceRules.SOUL_SOIL),
                           JNESurfaceRules.SOUL_PERMAFROST
                        }
                     )
                  ),
                  SurfaceRules.ifTrue(
                     SurfaceRules.UNDER_FLOOR,
                     SurfaceRules.sequence(
                        new RuleSource[]{
                           SurfaceRules.ifTrue(
                              SurfaceRules.noiseCondition(Noises.PATCH, -0.012, Double.MAX_VALUE),
                              SurfaceRules.ifTrue(
                                 SurfaceRules.yStartCheck(VerticalAnchor.absolute(30), 0),
                                 SurfaceRules.ifTrue(
                                    SurfaceRules.not(SurfaceRules.yStartCheck(VerticalAnchor.absolute(35), 0)), JNESurfaceRules.SOUL_PERMAFROST
                                 )
                              )
                           ),
                           SurfaceRules.ifTrue(
                              SurfaceRules.noiseCondition(Noises.NETHER_STATE_SELECTOR, 0.0, 7.976931348623157E307), JNESurfaceRules.SOUL_PERMAFROST
                           ),
                           JNESurfaceRules.SOUL_SOIL
                        }
                     )
                  )
               }
            )
         );
      }
   }

   private static class Global {
      private static RuleSource bedrockFloor() {
         return SurfaceRules.ifTrue(
            SurfaceRules.verticalGradient("bedrock_floor", VerticalAnchor.bottom(), VerticalAnchor.aboveBottom(5)), JNESurfaceRules.BEDROCK
         );
      }

      private static RuleSource bedrockRoof() {
         return SurfaceRules.ifTrue(
            SurfaceRules.not(SurfaceRules.verticalGradient("bedrock_roof", VerticalAnchor.belowTop(5), VerticalAnchor.top())),
            SurfaceRules.ifTrue(SurfaceRules.yBlockCheck(VerticalAnchor.belowTop(5), 0), JNESurfaceRules.BEDROCK)
         );
      }

      private static RuleSource siltShoreline() {
         return SurfaceRules.sequence(
            new RuleSource[]{
               SurfaceRules.ifTrue(ElysiumSurfaceRules.isBiomeTag(JNETags.Biomes.HAS_BEACH_SHORELINE), beachShore()),
               SurfaceRules.ifTrue(
                  SurfaceRules.not(ElysiumSurfaceRules.isBiomeTag(JNETags.Biomes.HAS_BEACH_SHORELINE)),
                  SurfaceRules.ifTrue(SurfaceRules.not(ElysiumSurfaceRules.isBiomeTag(JNETags.Biomes.HAS_NO_SHORELINE)), coveShore())
               )
            }
         );
      }

      private static RuleSource beachShore() {
         return SurfaceRules.ifTrue(
            SurfaceRules.UNDER_FLOOR,
            SurfaceRules.ifTrue(
               SurfaceRules.yStartCheck(VerticalAnchor.absolute(27), 0),
               SurfaceRules.ifTrue(
                  SurfaceRules.not(SurfaceRules.yStartCheck(VerticalAnchor.absolute(38), 1)),
                  SurfaceRules.ifTrue(
                     SurfaceRules.noiseCondition(Noises.GRAVEL_LAYER, -1.0, 0.4),
                     SurfaceRules.ifTrue(
                        SurfaceRules.not(SurfaceRules.hole()),
                        SurfaceRules.sequence(
                           new RuleSource[]{
                              SurfaceRules.ifTrue(SurfaceRules.yStartCheck(VerticalAnchor.absolute(32), 0), JNESurfaceRules.SILT),
                              SurfaceRules.ifTrue(SurfaceRules.not(SurfaceRules.hole()), JNESurfaceRules.SILT)
                           }
                        )
                     )
                  )
               )
            )
         );
      }

      private static RuleSource coveShore() {
         return SurfaceRules.ifTrue(
            SurfaceRules.UNDER_FLOOR,
            SurfaceRules.ifTrue(
               SurfaceRules.yStartCheck(VerticalAnchor.absolute(31), 0),
               SurfaceRules.ifTrue(
                  SurfaceRules.not(SurfaceRules.yStartCheck(VerticalAnchor.absolute(35), 1)),
                  SurfaceRules.ifTrue(
                     SurfaceRules.noiseCondition(Noises.GRAVEL_LAYER, -1.0, 0.4),
                     SurfaceRules.ifTrue(
                        SurfaceRules.not(SurfaceRules.hole()),
                        SurfaceRules.sequence(
                           new RuleSource[]{
                              SurfaceRules.ifTrue(SurfaceRules.yStartCheck(VerticalAnchor.absolute(32), 0), JNESurfaceRules.SILT),
                              SurfaceRules.ifTrue(SurfaceRules.not(SurfaceRules.hole()), JNESurfaceRules.SILT)
                           }
                        )
                     )
                  )
               )
            )
         );
      }
   }
}
