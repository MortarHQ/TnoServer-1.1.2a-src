package twilightforest.world.components.structures.util;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.util.Mth;
import net.minecraft.world.level.levelgen.Heightmap.Types;
import net.minecraft.world.level.levelgen.structure.Structure.GenerationContext;

public interface DecorationClearance {
   String CODEC_NAME = "decoration_clearance";

   float chunkClearanceRadius();

   boolean isSurfaceDecorationsAllowed();

   boolean isUndergroundDecoAllowed();

   boolean isGrassDecoAllowed();

   boolean shouldAdjustToTerrain();

   default int adjustForTerrain(GenerationContext context, int x, int z) {
      return this.shouldAdjustToTerrain()
         ? Mth.clamp(
            context.chunkGenerator().getFirstOccupiedHeight(x, z, Types.WORLD_SURFACE_WG, context.heightAccessor(), context.randomState()),
            context.chunkGenerator().getSeaLevel() + 1,
            context.chunkGenerator().getSeaLevel() + 7
         )
         : context.chunkGenerator().getSeaLevel();
   }

   record DecorationConfig(
      float chunkClearanceRadius, boolean surfaceDecorations, boolean undergroundDecorations, boolean vegetation, @Deprecated boolean adjustElevation
   ) {
      public static final Codec<DecorationClearance.DecorationConfig> CODEC = RecordCodecBuilder.create(
         instance -> instance.group(
               Codec.floatRange(0.0F, 8.0F)
                  .fieldOf("chunk_clearance_radius")
                  .orElse(1.0F)
                  .forGetter(DecorationClearance.DecorationConfig::chunkClearanceRadius),
               Codec.BOOL.fieldOf("allow_biome_surface_decorations").forGetter(DecorationClearance.DecorationConfig::surfaceDecorations),
               Codec.BOOL.fieldOf("allow_biome_underground_decorations").forGetter(DecorationClearance.DecorationConfig::undergroundDecorations),
               Codec.BOOL.fieldOf("allow_biome_vegetation").forGetter(DecorationClearance.DecorationConfig::vegetation),
               Codec.BOOL.fieldOf("adjust_structure_elevation").forGetter(DecorationClearance.DecorationConfig::adjustElevation)
            )
            .apply(instance, DecorationClearance.DecorationConfig::new)
      );

      public DecorationConfig(float chunkClearanceRadius, boolean surfaceDecorations, boolean undergroundDecorations, @Deprecated boolean adjustElevation) {
         this(chunkClearanceRadius, surfaceDecorations, undergroundDecorations, true, adjustElevation);
      }
   }
}
