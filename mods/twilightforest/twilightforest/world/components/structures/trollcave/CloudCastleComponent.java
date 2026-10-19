package twilightforest.world.components.structures.trollcave;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.StructureManager;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.levelgen.structure.BoundingBox;
import net.minecraft.world.level.levelgen.structure.StructurePiece;
import net.minecraft.world.level.levelgen.structure.StructurePieceAccessor;
import net.minecraft.world.level.levelgen.structure.pieces.StructurePieceSerializationContext;
import net.minecraft.world.level.levelgen.structure.pieces.StructurePieceType;
import net.neoforged.neoforge.event.EventHooks;
import twilightforest.entity.monster.ArmoredGiant;
import twilightforest.entity.monster.GiantMiner;
import twilightforest.init.TFBlocks;
import twilightforest.init.TFEntities;
import twilightforest.init.TFStructurePieceTypes;
import twilightforest.util.BoundingBoxUtils;
import twilightforest.world.components.structures.TFStructureComponentOld;

public class CloudCastleComponent extends TFStructureComponentOld {
   public CloudCastleComponent(StructurePieceSerializationContext ctx, CompoundTag nbt) {
      super((StructurePieceType)TFStructurePieceTypes.TFClCa.get(), nbt);
   }

   public CloudCastleComponent(int index, int x, int y, int z) {
      super((StructurePieceType)TFStructurePieceTypes.TFClCa.get(), index, x, y, z);
      this.setOrientation(Direction.SOUTH);
      x &= -4;
      y &= -4;
      z &= -4;
      this.boundingBox = BoundingBoxUtils.getComponentToAddBoundingBox(x, y, z, -8, -4, -8, 64, 16, 64, Direction.SOUTH, false);
   }

   @Override
   protected void addAdditionalSaveData(StructurePieceSerializationContext ctx, CompoundTag tagCompound) {
      super.addAdditionalSaveData(ctx, tagCompound);
   }

   public void addChildren(StructurePiece parent, StructurePieceAccessor list, RandomSource rand) {
      CloudComponent cloudComponent = new CloudComponent(this.getGenDepth() + 1, this.boundingBox.minX(), this.boundingBox.minY() + 3, this.boundingBox.minZ());
      list.addPiece(cloudComponent);
      cloudComponent.addChildren(this, list, rand);
      boolean plus = rand.nextBoolean();
      int offset = rand.nextInt(5) - rand.nextInt(5);
      CloudTreeComponent treeX = new CloudTreeComponent(
         this.getGenDepth() + 1, this.boundingBox.minX() + 8 + (plus ? 32 : -16), 168, this.boundingBox.minZ() + offset * 4
      );
      list.addPiece(treeX);
      treeX.addChildren(this, list, rand);
      plus = rand.nextBoolean();
      offset = rand.nextInt(5) - rand.nextInt(5);
      CloudTreeComponent treeZ = new CloudTreeComponent(
         this.getGenDepth() + 1, this.boundingBox.minX() + offset * 4, 168, this.boundingBox.minZ() + 8 + (plus ? 32 : -16)
      );
      list.addPiece(treeZ);
      treeZ.addChildren(this, list, rand);
   }

   public void postProcess(
      WorldGenLevel world, StructureManager manager, ChunkGenerator generator, RandomSource rand, BoundingBox sbb, ChunkPos chunkPosIn, BlockPos blockPos
   ) {
      this.generateBox(
         world,
         sbb,
         8,
         0,
         8,
         23,
         3,
         23,
         ((Block)TFBlocks.FLUFFY_CLOUD.get()).defaultBlockState(),
         ((Block)TFBlocks.FLUFFY_CLOUD.get()).defaultBlockState(),
         false
      );
      this.generateBox(
         world,
         sbb,
         8,
         4,
         8,
         23,
         15,
         23,
         ((Block)TFBlocks.GIANT_COBBLESTONE.get()).defaultBlockState(),
         ((Block)TFBlocks.GIANT_COBBLESTONE.get()).defaultBlockState(),
         false
      );
      this.generateBox(
         world, sbb, 8, 16, 8, 23, 19, 23, ((Block)TFBlocks.GIANT_LOG.get()).defaultBlockState(), ((Block)TFBlocks.GIANT_LOG.get()).defaultBlockState(), false
      );
      this.generateAirBox(world, sbb, 12, 4, 12, 19, 15, 19);
      this.generateAirBox(world, sbb, 8, 4, 12, 12, 11, 15);
      this.placeGiantMiner(world, sbb);
      this.placeWarrior(world, sbb);
   }

   private void placeGiantMiner(WorldGenLevel world, BoundingBox chunkGenerating) {
      int bx = this.getWorldX(14, 14);
      int by = this.getWorldY(4);
      int bz = this.getWorldZ(14, 14);
      if (chunkGenerating.isInside(bx, by, bz)) {
         BlockPos pos = new BlockPos(bx, by, bz);
         GiantMiner miner = (GiantMiner)((EntityType)TFEntities.GIANT_MINER.get()).create(world.getLevel());
         miner.setPos(bx, by, bz);
         miner.setPersistenceRequired();
         EventHooks.finalizeMobSpawn(miner, world, world.getCurrentDifficultyAt(pos), MobSpawnType.STRUCTURE, null);
         world.addFreshEntity(miner);
      }
   }

   private void placeWarrior(WorldGenLevel world, BoundingBox chunkGenerating) {
      int bx = this.getWorldX(17, 17);
      int by = this.getWorldY(4);
      int bz = this.getWorldZ(17, 17);
      if (chunkGenerating.isInside(bx, by, bz)) {
         BlockPos pos = new BlockPos(bx, by, bz);
         ArmoredGiant warrior = (ArmoredGiant)((EntityType)TFEntities.ARMORED_GIANT.get()).create(world.getLevel());
         warrior.setPos(bx, by, bz);
         warrior.setPersistenceRequired();
         EventHooks.finalizeMobSpawn(warrior, world, world.getCurrentDifficultyAt(pos), MobSpawnType.STRUCTURE, null);
         world.addFreshEntity(warrior);
      }
   }
}
