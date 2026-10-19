package twilightforest.world.components.structures.courtyard;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.FrontAndTop;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.StructureManager;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.levelgen.structure.BoundingBox;
import net.minecraft.world.level.levelgen.structure.StructurePiece;
import net.minecraft.world.level.levelgen.structure.StructurePieceAccessor;
import net.minecraft.world.level.levelgen.structure.pieces.StructurePieceSerializationContext;
import net.minecraft.world.level.levelgen.structure.pieces.StructurePieceType;
import net.minecraft.world.level.levelgen.structure.templatesystem.BlockRotProcessor;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplateManager;
import twilightforest.TwilightForestMod;
import twilightforest.beanification.Autowired;
import twilightforest.init.TFBlocks;
import twilightforest.init.TFStructurePieceTypes;
import twilightforest.util.BoundingBoxUtils;
import twilightforest.world.components.structures.TwilightJigsawPiece;
import twilightforest.world.components.structures.util.StructureTemplateDefinitions;

public class CourtyardMain extends StructureMazeGenerator {
   static final int ROW_OF_CELLS = 8;
   static final int RADIUS = 44;
   static final float HEDGE_FLOOF = 0.5F;
   static final float WALL_DECAY = 0.1F;
   static final float WALL_INTEGRITY = 0.95F;
   static final BlockRotProcessor WALL_INTEGRITY_PROCESSOR = new BlockRotProcessor(0.95F);
   static final BlockRotProcessor WALL_DECAY_PROCESSOR = new BlockRotProcessor(0.1F);
   public static final ResourceLocation CENTER_POOL = TwilightForestMod.prefix("courtyard/center");
   @Deprecated
   private final boolean placeSpawner;
   @Autowired
   private static StructureTemplateDefinitions structureTemplateDefinitions;

   public CourtyardMain(StructurePieceSerializationContext ctx, CompoundTag nbt) {
      super(ctx.structureTemplateManager(), (StructurePieceType)TFStructurePieceTypes.TFNCMn.get(), nbt);
      this.placeSpawner = nbt.contains("placeSpawner") ? nbt.getBoolean("placeSpawner") : true;
   }

   public CourtyardMain(RandomSource rand, int i, int x, int y, int z, StructureTemplateManager structureManager) {
      super((StructurePieceType)TFStructurePieceTypes.TFNCMn.get(), rand, i, 8, 8, x, y, z, structureManager);
      this.setOrientation(Direction.NORTH);
      this.boundingBox = BoundingBoxUtils.getComponentToAddBoundingBox(x, y, z, -22, -1, -22, 44, 10, 44, this.getOrientation(), false);
      this.sizeConstraints = BoundingBoxUtils.getComponentToAddBoundingBox(x, y, z, -44, -1, -44, 88, 10, 88, this.getOrientation(), false);
      this.placeSpawner = false;
   }

   @Override
   protected void addAdditionalSaveData(StructurePieceSerializationContext ctx, CompoundTag tagCompound) {
      super.addAdditionalSaveData(ctx, tagCompound);
      tagCompound.putBoolean("placeSpawner", this.placeSpawner);
   }

   @Override
   public void addChildren(StructurePiece parent, StructurePieceAccessor list, RandomSource random) {
      super.addChildren(parent, list, random);
      BlockPos pos = this.getWorldPos(22, 1, 22).immutable();
      Direction direction = Rotation.getRandom(random).rotate(Direction.SOUTH);
      FrontAndTop oriented = FrontAndTop.fromFrontAndTop(Direction.UP, direction);
      TwilightJigsawPiece bossSpawner = structureTemplateDefinitions.initializeTemplateFromPool(
         CENTER_POOL, pos, oriented, "twilightforest:center", random, this.genDepth + 1, this.structureManager
      );
      if (bossSpawner != null) {
         list.addPiece(bossSpawner);
      }
   }

   public void postProcess(
      WorldGenLevel world, StructureManager manager, ChunkGenerator generator, RandomSource rand, BoundingBox sbb, ChunkPos chunkPosIn, BlockPos blockPos
   ) {
      if (this.placeSpawner) {
         this.placeBlock(world, ((Block)TFBlocks.NAGA_BOSS_SPAWNER.get()).defaultBlockState(), 22, 3, 22, sbb);
      }
   }
}
