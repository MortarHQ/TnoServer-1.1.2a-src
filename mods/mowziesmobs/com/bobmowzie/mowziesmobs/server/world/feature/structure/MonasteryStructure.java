package com.bobmowzie.mowziesmobs.server.world.feature.structure;

import com.bobmowzie.mowziesmobs.MMCommon;
import com.bobmowzie.mowziesmobs.server.config.ConfigHandler;
import com.bobmowzie.mowziesmobs.server.world.feature.structure.jigsaw.MowzieJigsawManager;
import com.mojang.serialization.MapCodec;
import java.util.Optional;
import java.util.Set;
import java.util.function.Predicate;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.levelgen.GenerationStep.Decoration;
import net.minecraft.world.level.levelgen.structure.StructureType;
import net.minecraft.world.level.levelgen.structure.Structure.GenerationContext;
import net.minecraft.world.level.levelgen.structure.Structure.GenerationStub;
import net.minecraft.world.level.levelgen.structure.Structure.StructureSettings;
import net.minecraft.world.level.levelgen.structure.pools.StructureTemplatePool;
import org.apache.logging.log4j.Level;

public class MonasteryStructure extends MowzieStructure {
   public static final Set<String> MUST_CONNECT_POOLS = Set.of("mowziesmobs:monastery/path_pool", "mowziesmobs:monastery/path_connector_pool");
   public static final Set<String> REPLACE_POOLS = Set.of("mowziesmobs:monastery/path_pool");
   public static final String STRAIGHT_POOL = "mowziesmobs:monastery/dead_end_connect_pool";
   public static final MapCodec<MonasteryStructure> CODEC = simpleCodec(MonasteryStructure::new);

   public MonasteryStructure(StructureSettings settings) {
      super(settings, ConfigHandler.COMMON.MOBS.SCULPTOR.generationConfig, StructureTypeHandler.SCULPTOR_BIOMES, true, true, true);
   }

   @Override
   public Decoration step() {
      return Decoration.UNDERGROUND_DECORATION;
   }

   public static Optional<GenerationStub> createPiecesGenerator(Predicate<GenerationContext> canGeneratePredicate, GenerationContext context) {
      if (!canGeneratePredicate.test(context)) {
         return Optional.empty();
      }

      GenerationContext newContext = new GenerationContext(
         context.registryAccess(),
         context.chunkGenerator(),
         context.biomeSource(),
         context.randomState(),
         context.structureTemplateManager(),
         context.random(),
         context.seed(),
         context.chunkPos(),
         context.heightAccessor(),
         context.validBiome()
      );
      BlockPos blockpos = context.chunkPos().getMiddleBlockPosition(0);
      Optional<GenerationStub> structurePiecesGenerator = MowzieJigsawManager.addPieces(
         newContext,
         Holder.direct(
            (StructureTemplatePool)context.registryAccess()
               .registryOrThrow(Registries.TEMPLATE_POOL)
               .get(ResourceLocation.fromNamespaceAndPath("mowziesmobs", "monastery/start_pool"))
         ),
         blockpos,
         false,
         true,
         140,
         "mowziesmobs:monastery/path",
         "mowziesmobs:monastery/interior",
         MUST_CONNECT_POOLS,
         REPLACE_POOLS,
         "mowziesmobs:monastery/dead_end_connect_pool",
         23
      );
      if (structurePiecesGenerator.isPresent()) {
         MMCommon.LOGGER.log(Level.DEBUG, "Monastery at " + blockpos);
      }

      return structurePiecesGenerator;
   }

   @Override
   public Optional<GenerationStub> findGenerationPoint(GenerationContext context) {
      return createPiecesGenerator(t -> this.checkLocation(t), context);
   }

   public StructureType<?> type() {
      return (StructureType<?>)StructureTypeHandler.MONASTERY.get();
   }
}
