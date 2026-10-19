package twilightforest.world.components.structures.util;

import com.mojang.datafixers.util.Pair;
import com.mojang.serialization.Codec;
import com.mojang.serialization.DataResult;
import com.mojang.serialization.DynamicOps;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.util.random.SimpleWeightedRandomList;
import net.minecraft.util.random.Weight;
import net.minecraft.util.random.WeightedEntry;
import net.minecraft.world.level.levelgen.Heightmap.Types;
import net.minecraft.world.level.levelgen.structure.BoundingBox;
import net.minecraft.world.level.levelgen.structure.TerrainAdjustment;
import net.minecraft.world.level.levelgen.structure.Structure.GenerationContext;
import net.minecraft.world.level.levelgen.structure.pools.StructureTemplatePool.Projection;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureProcessor;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureProcessorList;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureProcessorType;
import twilightforest.util.WorldUtil;
import twilightforest.util.jigsaw.JigsawPlaceContext;

public record TemplatePoolInstance(
   Weight weight,
   Optional<Holder<StructureProcessorList>> processors,
   Projection projection,
   TerrainAdjustment terrainAdjustment,
   Optional<TemplatePoolInstance.HeightAdjustment> beardifierGroundDelta,
   boolean ignoreWorldWaterlog,
   Optional<Holder<TemplateMarkerHandlerList>> markerHandlers,
   Optional<TemplatePoolInstance.ChooseRandomProcessors> randomizedProcessors,
   Map<String, String> poolAliases
) implements WeightedEntry {
   private static final Codec<TemplatePoolInstance> CODEC_DIRECT = Codec.withAlternative(
      RecordCodecBuilder.create(
         instance -> instance.group(
               Weight.CODEC.fieldOf("weight").forGetter(TemplatePoolInstance::weight),
               StructureProcessorType.LIST_CODEC.optionalFieldOf("processors").forGetter(TemplatePoolInstance::processors),
               Projection.CODEC.optionalFieldOf("projection", Projection.RIGID).forGetter(TemplatePoolInstance::projection),
               TerrainAdjustment.CODEC.optionalFieldOf("terrain_adaptation", TerrainAdjustment.NONE).forGetter(TemplatePoolInstance::terrainAdjustment),
               TemplatePoolInstance.HeightAdjustment.CODEC.optionalFieldOf("height_adjustment").forGetter(TemplatePoolInstance::beardifierGroundDelta),
               Codec.BOOL.optionalFieldOf("ignore_world_waterlog", false).forGetter(TemplatePoolInstance::ignoreWorldWaterlog),
               TemplateMarkerHandlerList.HOLDER_CODEC.optionalFieldOf("marker_handlers").forGetter(TemplatePoolInstance::markerHandlers),
               TemplatePoolInstance.ChooseRandomProcessors.CODEC.optionalFieldOf("randomized_processors").forGetter(TemplatePoolInstance::randomizedProcessors),
               Codec.unboundedMap(Codec.STRING, Codec.STRING).optionalFieldOf("pool_aliases", Map.of()).forGetter(TemplatePoolInstance::poolAliases)
            )
            .apply(instance, TemplatePoolInstance::new)
      ),
      Codec.INT,
      TemplatePoolInstance::defaultsWithWeight
   );
   public static final Codec<TemplatePoolInstance> CODEC = new TemplatePoolInstance.TemplatePoolInstanceCodec();

   public static TemplatePoolInstance defaultsWithWeight(int weight) {
      return new TemplatePoolInstance(
         Weight.of(weight), Optional.empty(), Projection.RIGID, TerrainAdjustment.NONE, Optional.empty(), false, Optional.empty(), Optional.empty(), Map.of()
      );
   }

   public Weight getWeight() {
      return this.weight;
   }

   public JigsawPlaceContext adjustContextForTerrain(JigsawPlaceContext placeContext, GenerationContext generationContext, boolean parentProjectsTerrain) {
      return this.beardifierGroundDelta.isEmpty()
         ? placeContext
         : this.beardifierGroundDelta.get().adjustForTerrain(placeContext, generationContext, parentProjectsTerrain);
   }

   public StructureProcessorList chooseRandomProcessors(RandomSource randomSource) {
      return this.randomizedProcessors.isEmpty() ? new StructureProcessorList(List.of()) : this.randomizedProcessors.get().chooseRandomProcessors(randomSource);
   }

   @Override
   public boolean equals(Object o) {
      if (o != null && this.getClass() == o.getClass()) {
         TemplatePoolInstance that = (TemplatePoolInstance)o;
         return Objects.equals(this.weight(), that.weight())
            && this.terrainAdjustment() == that.terrainAdjustment()
            && (
               this.processors.isEmpty() && that.processors.isEmpty()
                  || this.processors.isPresent()
                     && that.processors.isPresent()
                     && Objects.equals(
                        ((StructureProcessorList)this.processors().get().value()).list(), ((StructureProcessorList)that.processors().get().value()).list()
                     )
            )
            && this.projection() == that.projection();
      } else {
         return false;
      }
   }

   @Override
   public int hashCode() {
      return Objects.hash(this.weight(), this.processors().map(Holder::value).map(StructureProcessorList::list), this.projection(), this.terrainAdjustment());
   }

   public record ChooseRandomProcessors(List<SimpleWeightedRandomList<StructureProcessor>> processors) {
      public static final Codec<TemplatePoolInstance.ChooseRandomProcessors> CODEC = SimpleWeightedRandomList.wrappedCodec(StructureProcessorType.SINGLE_CODEC)
         .listOf()
         .xmap(TemplatePoolInstance.ChooseRandomProcessors::new, TemplatePoolInstance.ChooseRandomProcessors::processors);

      public StructureProcessorList chooseRandomProcessors(RandomSource random) {
         List<StructureProcessor> chosenProcessors = new ArrayList<>();

         for (SimpleWeightedRandomList<StructureProcessor> list : this.processors) {
            list.getRandomValue(random).ifPresent(chosenProcessors::add);
         }

         return new StructureProcessorList(Collections.unmodifiableList(chosenProcessors));
      }
   }

   public record HeightAdjustment(Types heightType, int beardifierGroundDelta, Optional<Integer> groundJunctionDiffLimit) {
      public static final Codec<TemplatePoolInstance.HeightAdjustment> CODEC = RecordCodecBuilder.create(
         instance -> instance.group(
               Types.CODEC.fieldOf("heightmap").forGetter(TemplatePoolInstance.HeightAdjustment::heightType),
               Codec.INT.optionalFieldOf("y_offset", 0).forGetter(TemplatePoolInstance.HeightAdjustment::beardifierGroundDelta),
               Codec.intRange(0, Integer.MAX_VALUE)
                  .optionalFieldOf("ground_junction_diff_clamp")
                  .forGetter(TemplatePoolInstance.HeightAdjustment::groundJunctionDiffLimit)
            )
            .apply(instance, TemplatePoolInstance.HeightAdjustment::new)
      );

      public JigsawPlaceContext adjustForTerrain(JigsawPlaceContext placeContext, GenerationContext generationContext, boolean parentProjectsTerrain) {
         BoundingBox box = placeContext.makeBoundingBox(generationContext.structureTemplateManager());
         int yAdjusted = parentProjectsTerrain
            ? this.clampYLevelPos(generationContext, placeContext, placeContext.templatePos().offset(placeContext.seedJigsaw().pos()))
            : this.clampYLevelBox(generationContext, placeContext, box);
         return new JigsawPlaceContext(
            placeContext.templatePos().atY(yAdjusted),
            placeContext.placementSettings().copy(),
            placeContext.seedJigsaw(),
            List.copyOf(placeContext.spareJigsaws()),
            placeContext.templateLocation()
         );
      }

      private int clampYLevelBox(GenerationContext generationContext, JigsawPlaceContext placeContext, BoundingBox box) {
         if (this.groundJunctionDiffLimit.isEmpty()) {
            return this.beardifierGroundDelta
               + WorldUtil.adjustForTerrain(generationContext, box.minX(), box.minZ(), box.maxX(), box.maxZ(), 2, this.heightType);
         }

         int templateY = placeContext.templatePos().getY();
         int variantLimit = this.groundJunctionDiffLimit.get();
         if (variantLimit == 0) {
            return templateY;
         }

         int yTerrain = WorldUtil.adjustForTerrain(generationContext, box.minX(), box.minZ(), box.maxX(), box.maxZ(), 2, this.heightType);
         return Mth.clamp(this.beardifierGroundDelta + yTerrain, templateY - variantLimit, templateY + variantLimit);
      }

      private int clampYLevelPos(GenerationContext generationContext, JigsawPlaceContext placeContext, BlockPos pos) {
         if (this.groundJunctionDiffLimit.isEmpty()) {
            return this.beardifierGroundDelta
               + generationContext.chunkGenerator()
                  .getFirstOccupiedHeight(pos.getX(), pos.getZ(), this.heightType, generationContext.heightAccessor(), generationContext.randomState());
         }

         int templateY = placeContext.templatePos().getY();
         int variantLimit = this.groundJunctionDiffLimit.get();
         if (variantLimit == 0) {
            return templateY;
         }

         int yTerrain = generationContext.chunkGenerator()
            .getFirstOccupiedHeight(pos.getX(), pos.getZ(), this.heightType, generationContext.heightAccessor(), generationContext.randomState());
         return Mth.clamp(this.beardifierGroundDelta + yTerrain, templateY - variantLimit, templateY + variantLimit);
      }
   }

   private static class TemplatePoolInstanceCodec implements Codec<TemplatePoolInstance> {
      public <T> DataResult<T> encode(TemplatePoolInstance input, DynamicOps<T> ops, T prefix) {
         int weight = input.weight.asInt();
         return input.equals(TemplatePoolInstance.defaultsWithWeight(weight))
            ? DataResult.success(ops.createInt(weight))
            : TemplatePoolInstance.CODEC_DIRECT.encodeStart(ops, input);
      }

      public <T> DataResult<Pair<TemplatePoolInstance, T>> decode(DynamicOps<T> ops, T input) {
         DataResult<TemplatePoolInstance> parse = TemplatePoolInstance.CODEC_DIRECT.parse(ops, input);
         Optional<TemplatePoolInstance> templatePoolInstance = parse.resultOrPartial();
         return templatePoolInstance.isEmpty()
            ? DataResult.error(() -> "TemplatePoolInstance.CODEC deserialization problem:\n" + parse + "\n\n from data:\n" + input)
            : DataResult.success(Pair.of(templatePoolInstance.get(), input));
      }
   }
}
