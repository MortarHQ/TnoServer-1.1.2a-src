package twilightforest.world.components.structures.util;

import com.mojang.datafixers.Products.P3;
import com.mojang.datafixers.Products.P4;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder.Instance;
import com.mojang.serialization.codecs.RecordCodecBuilder.Mu;
import java.util.Comparator;
import java.util.Optional;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.levelgen.structure.Structure;
import net.minecraft.world.level.levelgen.structure.StructurePiece;
import net.minecraft.world.level.levelgen.structure.Structure.GenerationContext;
import net.minecraft.world.level.levelgen.structure.Structure.GenerationStub;
import net.minecraft.world.level.levelgen.structure.Structure.StructureSettings;
import net.minecraft.world.level.levelgen.structure.pieces.StructurePiecesBuilder;
import net.minecraft.world.level.saveddata.maps.MapDecorationType;
import org.jetbrains.annotations.Nullable;
import twilightforest.world.components.biomesources.TFBiomeProvider;
import twilightforest.world.components.structures.TFStructureComponentTemplate;

public abstract class LandmarkStructure extends Structure implements DecorationClearance {
   protected final Optional<DecorationClearance.DecorationConfig> decorationConfig;
   protected final boolean centerInChunk;
   protected Optional<Holder<MapDecorationType>> structureIcon;

   protected static <S extends LandmarkStructure> P3<Mu<S>, Optional<DecorationClearance.DecorationConfig>, Boolean, Optional<Holder<MapDecorationType>>> landmarkCodecNoSettings(
      Instance<S> instance
   ) {
      return instance.group(
         DecorationClearance.DecorationConfig.CODEC.optionalFieldOf("decoration_clearance").forGetter(s -> s.decorationConfig),
         Codec.BOOL.optionalFieldOf("center_in_chunk", true).forGetter(s -> s.centerInChunk),
         BuiltInRegistries.MAP_DECORATION_TYPE.holderByNameCodec().optionalFieldOf("structure_icon").forGetter(s -> s.structureIcon)
      );
   }

   protected static <S extends LandmarkStructure> P4<Mu<S>, Optional<DecorationClearance.DecorationConfig>, Boolean, Optional<Holder<MapDecorationType>>, StructureSettings> landmarkCodec(
      Instance<S> instance
   ) {
      return instance.group(
         DecorationClearance.DecorationConfig.CODEC.optionalFieldOf("decoration_clearance").forGetter(s -> s.decorationConfig),
         Codec.BOOL.optionalFieldOf("center_in_chunk", true).forGetter(s -> s.centerInChunk),
         BuiltInRegistries.MAP_DECORATION_TYPE.holderByNameCodec().optionalFieldOf("structure_icon").forGetter(s -> s.structureIcon),
         Structure.settingsCodec(instance)
      );
   }

   public LandmarkStructure(
      Optional<DecorationClearance.DecorationConfig> decorationConfig,
      boolean centerInChunk,
      Optional<Holder<MapDecorationType>> structureIcon,
      StructureSettings structureSettings
   ) {
      super(structureSettings);
      this.decorationConfig = decorationConfig;
      this.centerInChunk = centerInChunk;
      this.structureIcon = structureIcon;
   }

   protected GenerationStub getStructurePieceGenerationStubFunction(StructurePiece startingPiece, GenerationContext context, int x, int y, int z) {
      return new GenerationStub(
         new BlockPos(x, y, z),
         structurePiecesBuilder -> {
            this.generateFromStartingPiece(startingPiece, context, structurePiecesBuilder);
            structurePiecesBuilder.pieces.sort(Comparator.comparing(piece -> piece instanceof SortablePiece sortable ? sortable.getSortKey() : 0));
            structurePiecesBuilder.pieces
               .stream()
               .filter(TFStructureComponentTemplate.class::isInstance)
               .map(TFStructureComponentTemplate.class::cast)
               .forEach(t -> t.LAZY_TEMPLATE_LOADER.run());
         }
      );
   }

   protected void generateFromStartingPiece(StructurePiece startingPiece, GenerationContext context, StructurePiecesBuilder structurePiecesBuilder) {
      structurePiecesBuilder.addPiece(startingPiece);
      startingPiece.addChildren(startingPiece, structurePiecesBuilder, context.random());
   }

   public Optional<GenerationStub> findGenerationPoint(GenerationContext context) {
      ChunkPos chunkPos = context.chunkPos();
      int x = (chunkPos.x << 4) + (this.centerInChunk ? 7 : 0);
      int z = (chunkPos.z << 4) + (this.centerInChunk ? 7 : 0);
      int y = this.adjustForTerrain(context, x, z);
      return Optional.ofNullable(
            this.getFirstPiece(context, RandomSource.create(context.seed() + chunkPos.x * 25117L + chunkPos.z * 151121L), chunkPos, x, y, z)
         )
         .map(piece -> this.getStructurePieceGenerationStubFunction(piece, context, x, y, z));
   }

   public final Optional<Holder<MapDecorationType>> getMapIcon() {
      return this.structureIcon;
   }

   @Nullable
   protected abstract StructurePiece getFirstPiece(GenerationContext var1, RandomSource var2, ChunkPos var3, int var4, int var5, int var6);

   @Override
   public boolean isSurfaceDecorationsAllowed() {
      return this.decorationConfig.map(DecorationClearance.DecorationConfig::surfaceDecorations).orElse(true);
   }

   @Override
   public boolean isUndergroundDecoAllowed() {
      return this.decorationConfig.map(DecorationClearance.DecorationConfig::undergroundDecorations).orElse(true);
   }

   @Override
   public boolean isGrassDecoAllowed() {
      return this.decorationConfig.map(DecorationClearance.DecorationConfig::vegetation).orElse(true);
   }

   @Override
   public boolean shouldAdjustToTerrain() {
      return this.decorationConfig.map(DecorationClearance.DecorationConfig::adjustElevation).orElse(false);
   }

   @Override
   public float chunkClearanceRadius() {
      return this.decorationConfig.map(DecorationClearance.DecorationConfig::chunkClearanceRadius).orElse(1.0F);
   }

   public Optional<GenerationStub> findValidGenerationPoint(GenerationContext context) {
      if (context.biomeSource() instanceof TFBiomeProvider twilightBiomeProvider) {
         ChunkPos chunkPos = context.chunkPos();
         int biomeX = (Math.round(chunkPos.x / 16.0F) << 6) + 2;
         int biomeZ = (Math.round(chunkPos.z / 16.0F) << 6) + 2;
         Holder biomeAt = twilightBiomeProvider.getMainBiome(biomeX, biomeZ);
         return context.validBiome().test(biomeAt) ? this.findGenerationPoint(context) : Optional.empty();
      } else {
         return super.findValidGenerationPoint(context);
      }
   }
}
