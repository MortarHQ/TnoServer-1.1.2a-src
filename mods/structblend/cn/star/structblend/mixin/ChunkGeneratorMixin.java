package cn.star.structblend.mixin;

import cn.star.structblend.terrain.DecorativeTemplateFilter;
import cn.star.structblend.terrain.ReferenceExpansion;
import cn.star.structblend.terrain.SurfaceBlendPlan;
import cn.star.structblend.terrain.VillageUnderpinning;
import java.util.function.Predicate;
import net.minecraft.core.Holder;
import net.minecraft.core.RegistryAccess;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.LevelHeightAccessor;
import net.minecraft.world.level.StructureManager;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.BiomeSource;
import net.minecraft.world.level.chunk.ChunkAccess;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.levelgen.RandomState;
import net.minecraft.world.level.levelgen.structure.BoundingBox;
import net.minecraft.world.level.levelgen.structure.Structure;
import net.minecraft.world.level.levelgen.structure.StructureStart;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplateManager;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(ChunkGenerator.class)
public abstract class ChunkGeneratorMixin {
   @Redirect(
      method = "createReferences(Lnet/minecraft/world/level/WorldGenLevel;Lnet/minecraft/world/level/StructureManager;Lnet/minecraft/world/level/chunk/ChunkAccess;)V",
      at = @At(
         value = "INVOKE",
         target = "Lnet/minecraft/world/level/levelgen/structure/StructureStart;getBoundingBox()Lnet/minecraft/world/level/levelgen/structure/BoundingBox;"
      )
   )
   private BoundingBox structblend$includeBlendArea(StructureStart start, WorldGenLevel level, StructureManager manager, ChunkAccess chunk) {
      BoundingBox box = start.getBoundingBox();
      ChunkPos pos = chunk.getPos();
      if (!ReferenceExpansion.affectsResult(
         box.minX(), box.maxX(), box.minZ(), box.maxZ(), pos.getMinBlockX(), pos.getMinBlockZ(), SurfaceBlendPlan.MAX_REFERENCE_RADIUS
      )) {
         return box;
      }

      int radius;
      if (VillageUnderpinning.isTarget(start, manager)) {
         radius = 6;
      } else {
         if (!SurfaceBlendPlan.isEligible(start, manager)) {
            return box;
         }

         radius = SurfaceBlendPlan.REFERENCE_RADIUS;
      }

      return new BoundingBox(box.minX() - radius, box.minY(), box.minZ() - radius, box.maxX() + radius, box.maxY(), box.maxZ() + radius);
   }

   @Redirect(
      method = "tryGenerateStructure(Lnet/minecraft/world/level/levelgen/structure/StructureSet$StructureSelectionEntry;Lnet/minecraft/world/level/StructureManager;Lnet/minecraft/core/RegistryAccess;Lnet/minecraft/world/level/levelgen/RandomState;Lnet/minecraft/world/level/levelgen/structure/templatesystem/StructureTemplateManager;JLnet/minecraft/world/level/chunk/ChunkAccess;Lnet/minecraft/world/level/ChunkPos;Lnet/minecraft/core/SectionPos;)Z",
      at = @At(
         value = "INVOKE",
         target = "Lnet/minecraft/world/level/levelgen/structure/Structure;generate(Lnet/minecraft/core/RegistryAccess;Lnet/minecraft/world/level/chunk/ChunkGenerator;Lnet/minecraft/world/level/biome/BiomeSource;Lnet/minecraft/world/level/levelgen/RandomState;Lnet/minecraft/world/level/levelgen/structure/templatesystem/StructureTemplateManager;JLnet/minecraft/world/level/ChunkPos;ILnet/minecraft/world/level/LevelHeightAccessor;Ljava/util/function/Predicate;)Lnet/minecraft/world/level/levelgen/structure/StructureStart;"
      )
   )
   private StructureStart structblend$rejectMarinePlatform(
      Structure structure,
      RegistryAccess access,
      ChunkGenerator generator,
      BiomeSource biomes,
      RandomState randomState,
      StructureTemplateManager templates,
      long seed,
      ChunkPos pos,
      int references,
      LevelHeightAccessor heightAccessor,
      Predicate<Holder<Biome>> validBiome
   ) {
      StructureStart start = structure.generate(access, generator, biomes, randomState, templates, seed, pos, references, heightAccessor, validBiome);
      if (DecorativeTemplateFilter.isDecoration(start)) {
         return start;
      } else {
         return SurfaceBlendPlan.shouldRejectMarinePlatform(start, generator, randomState, heightAccessor) ? StructureStart.INVALID_START : start;
      }
   }
}
