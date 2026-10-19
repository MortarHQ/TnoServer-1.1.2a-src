package twilightforest.world.components.structures.trollcave;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.StructureManager;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.levelgen.structure.BoundingBox;
import net.minecraft.world.level.levelgen.structure.pieces.StructurePieceSerializationContext;
import net.minecraft.world.level.levelgen.structure.pieces.StructurePieceType;
import twilightforest.init.TFBlocks;
import twilightforest.init.TFStructurePieceTypes;
import twilightforest.loot.TFLootTables;
import twilightforest.util.BoundingBoxUtils;
import twilightforest.world.components.structures.TFStructureComponentOld;

public class TrollVaultComponent extends TFStructureComponentOld {
   public TrollVaultComponent(StructurePieceSerializationContext ctx, CompoundTag nbt) {
      super((StructurePieceType)TFStructurePieceTypes.TFTCVa.get(), nbt);
   }

   public TrollVaultComponent(int index, int x, int y, int z) {
      super((StructurePieceType)TFStructurePieceTypes.TFTCVa.get(), index, x, y, z);
      this.setOrientation(Direction.SOUTH);
      x = x >> 2 << 2;
      y = y / 4 * 4;
      z = z >> 2 << 2;
      this.spawnListIndex = -1;
      this.boundingBox = BoundingBoxUtils.getComponentToAddBoundingBox(x, y, z, -8, 0, -8, 12, 12, 12, Direction.SOUTH, false);
   }

   public void postProcess(
      WorldGenLevel world, StructureManager manager, ChunkGenerator generator, RandomSource rand, BoundingBox sbb, ChunkPos chunkPosIn, BlockPos blockPos
   ) {
      this.generateBox(
         world,
         sbb,
         0,
         0,
         0,
         11,
         11,
         11,
         ((Block)TFBlocks.GIANT_OBSIDIAN.get()).defaultBlockState(),
         ((Block)TFBlocks.GIANT_OBSIDIAN.get()).defaultBlockState(),
         false
      );
      this.generateAirBox(world, sbb, 4, 4, 4, 7, 7, 7);
      this.generateBox(world, sbb, 5, 5, 5, 6, 5, 6, Blocks.COBBLESTONE.defaultBlockState(), Blocks.COBBLESTONE.defaultBlockState(), false);
      this.setDoubleLootChest(
         world, 5, 6, 5, 5, 6, 6, this.getOrientation().getClockWise(), TFLootTables.TROLL_VAULT, TFLootTables.TROLL_VAULT_WITH_LAMP, sbb, false
      );
      this.setDoubleLootChest(world, 6, 6, 5, 6, 6, 6, this.getOrientation().getClockWise(), TFLootTables.TROLL_GARDEN, sbb, false);
   }
}
