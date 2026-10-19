package twilightforest.world.components.structures.darktower;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.StructureManager;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.levelgen.structure.BoundingBox;
import net.minecraft.world.level.levelgen.structure.StructurePiece;
import net.minecraft.world.level.levelgen.structure.StructurePieceAccessor;
import net.minecraft.world.level.levelgen.structure.pieces.StructurePieceSerializationContext;
import net.minecraft.world.level.levelgen.structure.pieces.StructurePieceType;
import twilightforest.block.GhastTrapBlock;
import twilightforest.init.TFBlocks;
import twilightforest.init.TFStructurePieceTypes;
import twilightforest.util.RotationUtil;
import twilightforest.world.components.structures.TFStructureComponentOld;

public class DarkTowerBossTrapComponent extends DarkTowerWingComponent {
   public DarkTowerBossTrapComponent(StructurePieceSerializationContext ctx, CompoundTag nbt) {
      super((StructurePieceType)TFStructurePieceTypes.TFDTBT.get(), nbt);
   }

   protected DarkTowerBossTrapComponent(int i, int x, int y, int z, int pSize, int pHeight, Direction direction) {
      super((StructurePieceType)TFStructurePieceTypes.TFDTBT.get(), i, x, y, z, pSize, pHeight, direction);
      this.spawnListIndex = -1;
   }

   @Override
   public void addChildren(StructurePiece parent, StructurePieceAccessor list, RandomSource rand) {
      if (parent != null && parent instanceof TFStructureComponentOld) {
         this.deco = ((TFStructureComponentOld)parent).deco;
      }

      this.addOpening(0, 1, this.size / 2, Rotation.CLOCKWISE_180);
      this.makeABeard(parent, list, rand);

      for (Rotation i : RotationUtil.ROTATIONS) {
         if (i != Rotation.CLOCKWISE_180 && !rand.nextBoolean()) {
            int[] dest = this.getValidOpening(rand, i);
            dest[1] = 1;
            this.makeTowerBalcony(list, rand, this.getGenDepth(), dest[0], dest[1], dest[2], i);
         }
      }
   }

   @Override
   public void makeARoof(StructurePiece parent, StructurePieceAccessor list, RandomSource rand) {
   }

   @Override
   public void postProcess(
      WorldGenLevel world, StructureManager manager, ChunkGenerator generator, RandomSource rand, BoundingBox sbb, ChunkPos chunkPosIn, BlockPos blockPos
   ) {
      RandomSource decoRNG = RandomSource.create(world.getSeed() + this.boundingBox.minX() * 321534781L ^ this.boundingBox.minZ() * 756839L);
      this.makeEncasedWalls(world, rand, sbb, 0, 0, 0, this.size - 1, this.height - 1, this.size - 1);
      this.generateAirBox(world, sbb, 1, 1, 1, this.size - 2, this.height - 2, this.size - 2);
      this.makeOpenings(world, sbb);
      this.addBossTrapFloors(world, sbb);
      this.destroyTower(world, decoRNG, 5, this.height + 2, 5, 4, sbb);
      this.destroyTower(world, decoRNG, 0, this.height, 0, 3, sbb);
      this.destroyTower(world, decoRNG, 0, this.height, 8, 4, sbb);
      this.destroyTower(world, decoRNG, 5, 6, 5, 2, sbb);
      this.generateBox(world, sbb, 1, 0, 1, this.size / 2, 0, this.size - 2, this.deco.blockState, Blocks.AIR.defaultBlockState(), false);
      this.generateBox(world, sbb, 1, 1, 1, this.size / 2, 1, this.size - 2, Blocks.AIR.defaultBlockState(), Blocks.AIR.defaultBlockState(), false);
      this.placeBlock(world, ((GhastTrapBlock)TFBlocks.GHAST_TRAP.get()).defaultBlockState(), 5, 1, 5, sbb);
      this.placeBlock(world, Blocks.REDSTONE_WIRE.defaultBlockState(), 5, 1, 6, sbb);
      this.placeBlock(world, Blocks.REDSTONE_WIRE.defaultBlockState(), 5, 1, 7, sbb);
      this.placeBlock(world, Blocks.REDSTONE_WIRE.defaultBlockState(), 5, 1, 8, sbb);
      this.placeBlock(world, Blocks.REDSTONE_WIRE.defaultBlockState(), 4, 1, 8, sbb);
      this.placeBlock(world, Blocks.REDSTONE_WIRE.defaultBlockState(), 3, 1, 8, sbb);
      this.placeBlock(world, Blocks.OAK_PRESSURE_PLATE.defaultBlockState(), 2, 1, 8, sbb);
   }

   protected void addBossTrapFloors(WorldGenLevel world, BoundingBox sbb) {
      this.makeFullFloor(world, sbb, 4);
      this.addStairsDown(world, sbb, Rotation.COUNTERCLOCKWISE_90, 4, this.size - 2, 4);
      this.addStairsDown(world, sbb, Rotation.COUNTERCLOCKWISE_90, 4, this.size - 3, 4);
      this.addStairsDown(world, sbb, Rotation.CLOCKWISE_90, this.height - 1, this.size - 2, 4);
      this.addStairsDown(world, sbb, Rotation.CLOCKWISE_90, this.height - 1, this.size - 3, 4);
   }
}
