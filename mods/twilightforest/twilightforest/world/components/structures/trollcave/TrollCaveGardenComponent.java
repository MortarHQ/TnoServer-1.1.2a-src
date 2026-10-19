package twilightforest.world.components.structures.trollcave;

import java.util.function.Predicate;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Registry;
import net.minecraft.core.BlockPos.MutableBlockPos;
import net.minecraft.core.Holder.Reference;
import net.minecraft.core.registries.Registries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceKey;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.StructureManager;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.structure.BoundingBox;
import net.minecraft.world.level.levelgen.structure.StructurePiece;
import net.minecraft.world.level.levelgen.structure.StructurePieceAccessor;
import net.minecraft.world.level.levelgen.structure.pieces.StructurePieceSerializationContext;
import net.minecraft.world.level.levelgen.structure.pieces.StructurePieceType;
import twilightforest.init.TFBiomes;
import twilightforest.init.TFConfiguredFeatures;
import twilightforest.init.TFStructurePieceTypes;
import twilightforest.util.BoundingBoxUtils;
import twilightforest.world.components.structures.StructureSpeleothemConfig;

public class TrollCaveGardenComponent extends TrollCaveMainComponent {
   public TrollCaveGardenComponent(StructurePieceSerializationContext ctx, CompoundTag nbt) {
      super((StructurePieceType)TFStructurePieceTypes.TFTCGard.get(), ctx, nbt);
   }

   public TrollCaveGardenComponent(
      int index, int x, int y, int z, int caveSize, int caveHeight, Direction direction, Reference<StructureSpeleothemConfig> speleothemConfig
   ) {
      super((StructurePieceType)TFStructurePieceTypes.TFTCGard.get(), index, x, y, z, speleothemConfig);
      this.size = caveSize;
      this.height = caveHeight;
      this.setOrientation(direction);
      this.boundingBox = BoundingBoxUtils.getComponentToAddBoundingBox(x, y, z, 0, 0, 0, this.size - 1, this.height - 1, this.size - 1, direction, false);
   }

   @Override
   public void addChildren(StructurePiece parent, StructurePieceAccessor list, RandomSource rand) {
   }

   @Override
   public void postProcess(
      WorldGenLevel world, StructureManager manager, ChunkGenerator generator, RandomSource rand, BoundingBox sbb, ChunkPos chunkPosIn, BlockPos blockPos
   ) {
      Registry<Biome> biomeRegistry = world.registryAccess().registryOrThrow(Registries.BIOME);
      Predicate<Biome> highlands = biome -> biome == biomeRegistry.get(TFBiomes.HIGHLANDS) || biome == biomeRegistry.get(TFBiomes.HIGHLANDS_UNDERGROUND);
      if (!this.isBoundingBoxOutsideBiomes(world, highlands, blockPos)) {
         this.hollowCaveMiddle(world, sbb, rand, 0, 0, 0, this.size - 1, this.height - 1, this.size - 1);
         RandomSource decoRNG = RandomSource.create(world.getSeed() + this.boundingBox.minX() * 321534781L ^ this.boundingBox.minZ() * 756839L);
         this.makeTreasureCrate(world, sbb);

         for (int i = 0; i < 24; i++) {
            BlockPos dest = this.getCoordsInCave(decoRNG);
            this.generate(world, generator, TFConfiguredFeatures.TROLL_CAVE_DIRT, decoRNG, dest.getX(), 0, dest.getZ(), sbb);
         }

         for (int i = 0; i < 16; i++) {
            BlockPos dest = this.getCoordsInCave(decoRNG);
            this.generate(world, generator, TFConfiguredFeatures.TROLL_CAVE_MYCELIUM, decoRNG, dest.getX(), 0, dest.getZ(), sbb);
         }

         for (int i = 0; i < 16; i++) {
            BlockPos dest = this.getCoordsInCave(decoRNG);
            this.generate(world, generator, TFConfiguredFeatures.UBEROUS_SOIL_PATCH_SMALL, decoRNG, dest.getX(), 0, dest.getZ(), sbb);
            this.generateAtSurface(world, generator, TFConfiguredFeatures.UBEROUS_SOIL_PATCH_SMALL, decoRNG, dest.getX(), dest.getZ(), sbb);
         }

         for (int i = 0; i < 8; i++) {
            MutableBlockPos dest = this.getCoordsInCave(decoRNG);
            this.setBlockStateRotated(world, Blocks.MYCELIUM.defaultBlockState(), dest.getX(), dest.setY(0).getY(), dest.getZ(), this.rotation, sbb);
            this.generate(world, generator, TFConfiguredFeatures.TROLL_BIG_MUSHGLOOMS, decoRNG, dest.getX(), dest.setY(1).getY(), dest.getZ(), sbb);
         }

         for (int i = 0; i < 16; i++) {
            MutableBlockPos dest = this.getCoordsInCave(decoRNG);
            this.setBlockStateRotated(world, Blocks.MYCELIUM.defaultBlockState(), dest.getX(), dest.setY(0).getY(), dest.getZ(), this.rotation, sbb);
            this.generate(
               world,
               generator,
               rand.nextBoolean() ? TFConfiguredFeatures.TROLL_HUGE_BROWN_MUSHROOMS : TFConfiguredFeatures.TROLL_HUGE_RED_MUSHROOMS,
               decoRNG,
               dest.getX(),
               dest.setY(1).getY(),
               dest.getZ(),
               sbb
            );
         }

         this.placeSpeleothems(world, rand, sbb, decoRNG);
      }
   }

   protected void generate(
      WorldGenLevel world, ChunkGenerator generator, ResourceKey<ConfiguredFeature<?, ?>> feature, RandomSource rand, int x, int y, int z, BoundingBox sbb
   ) {
      int dx = this.getWorldX(x, z);
      int dy = this.getWorldY(y);
      int dz = this.getWorldZ(x, z);
      BlockPos pos = new BlockPos(dx, dy, dz);
      if (sbb.isInside(pos)) {
         ((ConfiguredFeature)world.registryAccess().registryOrThrow(Registries.CONFIGURED_FEATURE).get(feature)).place(world, generator, rand, pos);
      }
   }
}
