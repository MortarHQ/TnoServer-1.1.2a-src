package twilightforest.world.components.structures;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderSet;
import net.minecraft.core.BlockPos.MutableBlockPos;
import net.minecraft.core.Holder.Reference;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.StructureManager;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.levelgen.structure.BoundingBox;
import net.minecraft.world.level.levelgen.structure.StructurePiece;
import net.minecraft.world.level.levelgen.structure.StructurePieceAccessor;
import net.minecraft.world.level.levelgen.structure.pieces.StructurePieceSerializationContext;
import net.minecraft.world.level.levelgen.structure.pieces.StructurePieceType;
import twilightforest.init.TFBlocks;
import twilightforest.init.TFStructurePieceTypes;
import twilightforest.util.features.FeaturePlacers;

public class HydraLairComponent extends HollowHillComponent {
   public HydraLairComponent(StructurePieceSerializationContext ctx, CompoundTag nbt) {
      super(ctx, (StructurePieceType)TFStructurePieceTypes.TFHydra.get(), nbt);
   }

   public HydraLairComponent(int i, int x, int y, int z, Reference<StructureSpeleothemConfig> speleothemConfig) {
      super((StructurePieceType)TFStructurePieceTypes.TFHydra.get(), i, 2, x, y + 2, z, speleothemConfig);
   }

   public void addChildren(StructurePiece structurecomponent, StructurePieceAccessor accessor, RandomSource random) {
   }

   @Override
   public void postProcess(
      WorldGenLevel world,
      StructureManager manager,
      ChunkGenerator generator,
      RandomSource rand,
      BoundingBox writeableBounds,
      ChunkPos chunkPosIn,
      BlockPos blockPos
   ) {
      FeaturePlacers.replaceBlocksDome(
         world,
         this.boundingBox.getCenter().atY(this.getWorldY(-4)),
         36.5F,
         0.0125F,
         writeableBounds,
         this.boundingBox.inflatedBy(4),
         HolderSet.direct(new Holder[]{Blocks.GRASS_BLOCK.builtInRegistryHolder(), Blocks.DIRT.builtInRegistryHolder()}),
         Blocks.STONE.defaultBlockState()
      );
      FeaturePlacers.replaceBlocksDome(
         world,
         this.boundingBox.getCenter().offset(-16, 0, -16).atY(this.getWorldY(-1)),
         18.5F,
         0.0125F,
         writeableBounds,
         this.boundingBox.inflatedBy(1),
         HolderSet.direct(new Holder[]{Blocks.STONE.builtInRegistryHolder()}),
         Blocks.GRASS_BLOCK.defaultBlockState()
      );
      this.generateSpeleothems(world, rand, writeableBounds);
      this.placeBlock(world, ((Block)TFBlocks.HYDRA_BOSS_SPAWNER.get()).defaultBlockState(), 27, 3, 27, writeableBounds);
   }

   private void generateSpeleothems(WorldGenLevel world, RandomSource rand, BoundingBox writeableBounds) {
      float radiusSq = 0.9F * this.radius * this.radius;
      BlockPos locator = this.getLocatorPosition();
      BlockPos exclusionCenter = locator.offset(-16, 0, -16);
      float exclusionRadiusSq = 529.0F;

      for (MutableBlockPos dest : this.speleothemConfig.latticeIterator(writeableBounds, 0)) {
         int dX = dest.getX() - exclusionCenter.getX();
         int dZ = dest.getZ() - exclusionCenter.getZ();
         if (!(dX * dX + dZ * dZ < exclusionRadiusSq)) {
            float distSq = getDistSqFromCenter(locator, dest);
            if (!(distSq > rand.nextFloat() * 0.9F * radiusSq)) {
               dest.setY(Mth.floor(Mth.cos(Mth.sqrt(distSq) / this.hdiam * (float) Math.PI) * (this.hdiam / 4.0F)));
               if (this.speleothemConfig.shouldDoAStalactite(rand)) {
                  this.generateSpeleothem(world, dest, writeableBounds, true);
               }

               if (this.speleothemConfig.shouldDoAStalagmite(rand) && !((rand.nextFloat() * 0.667F + 0.333F) * distSq / radiusSq < 0.333F)) {
                  dest.setY(1);
                  this.generateSpeleothem(world, dest, writeableBounds, false);
               }
            }
         }
      }
   }
}
