package twilightforest.world.components.structures.finalcastle;

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
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.levelgen.structure.BoundingBox;
import net.minecraft.world.level.levelgen.structure.StructurePiece;
import net.minecraft.world.level.levelgen.structure.StructurePieceAccessor;
import net.minecraft.world.level.levelgen.structure.pieces.StructurePieceSerializationContext;
import net.minecraft.world.level.levelgen.structure.pieces.StructurePieceType;
import net.neoforged.neoforge.server.ServerLifecycleHooks;
import twilightforest.TwilightForestMod;
import twilightforest.beanification.Autowired;
import twilightforest.init.TFBlocks;
import twilightforest.init.TFStructurePieceTypes;
import twilightforest.util.RotationUtil;
import twilightforest.world.components.structures.TFStructureComponentOld;
import twilightforest.world.components.structures.TwilightJigsawPiece;
import twilightforest.world.components.structures.util.StructureTemplateDefinitions;

public class FinalCastleBellTower21Component extends FinalCastleMazeTower13Component {
   public static final ResourceLocation BELL_TOWER_TEMP_POOL = TwilightForestMod.prefix("final_castle/temp/bell_tower");
   private static final int FLOORS = 8;
   @Autowired
   private static StructureTemplateDefinitions structureTemplateDefinitions;

   public FinalCastleBellTower21Component(StructurePieceSerializationContext ctx, CompoundTag nbt) {
      super((StructurePieceType)TFStructurePieceTypes.TFFCBelTo.get(), nbt);
   }

   public FinalCastleBellTower21Component(int i, int x, int y, int z, Direction direction) {
      super(
         (StructurePieceType)TFStructurePieceTypes.TFFCBelTo.get(),
         i,
         x,
         y,
         z,
         8,
         1,
         ((Block)TFBlocks.BLUE_CASTLE_RUNE_BRICK.get()).defaultBlockState(),
         direction
      );
      this.size = 21;
      this.height = 65;
      this.boundingBox = TFStructureComponentOld.getComponentToAddBoundingBox2(
         x, y, z, -6, -8, -this.size / 2, this.size - 1, this.height, this.size - 1, direction
      );
      this.openings.clear();
      this.addOpening(0, 9, this.size / 2, Rotation.CLOCKWISE_180);
   }

   @Override
   public void addChildren(StructurePiece parent, StructurePieceAccessor list, RandomSource rand) {
      if (parent != null && parent instanceof TFStructureComponentOld) {
         this.deco = ((TFStructureComponentOld)parent).deco;
      }

      FinalCastleBellFoundation21Component foundation = new FinalCastleBellFoundation21Component(
         4, this, this.getLocatorPosition().getX(), this.getLocatorPosition().getY(), this.getLocatorPosition().getZ()
      );
      list.addPiece(foundation);
      foundation.addChildren(this, list, rand);
      TFStructureComponentOld roof = new FinalCastleRoof13CrenellatedComponent(
         4, this, this.getLocatorPosition().getX(), this.getLocatorPosition().getY(), this.getLocatorPosition().getZ()
      );
      list.addPiece(roof);
      roof.addChildren(this, list, rand);
      TwilightJigsawPiece templatePiece = structureTemplateDefinitions.initializeTemplateFromPool(
         BELL_TOWER_TEMP_POOL,
         this.getWorldPos(0, 9, 10),
         this.rotation.rotation().rotate(FrontAndTop.WEST_UP),
         "twilightforest:final_castle/room",
         rand,
         this.genDepth + 1,
         ServerLifecycleHooks.getCurrentServer().getStructureManager()
      );
      if (templatePiece != null) {
         list.addPiece(templatePiece);
      }
   }

   @Override
   public void postProcess(
      WorldGenLevel world, StructureManager manager, ChunkGenerator generator, RandomSource rand, BoundingBox sbb, ChunkPos chunkPosIn, BlockPos blockPos
   ) {
      super.postProcess(world, manager, generator, rand, sbb, chunkPosIn, blockPos);
      BlockState fieldBlock = ((Block)TFBlocks.BLUE_FORCE_FIELD.get()).defaultBlockState();

      for (Rotation rotation : RotationUtil.ROTATIONS) {
         int y = 48;

         for (int x = 5; x < this.size - 4; x += 2) {
            this.fillBlocksRotated(world, sbb, x, y, 0, x, y + 14, 0, fieldBlock, rotation);
         }

         int var15 = 24;

         for (int x = 1; x < this.size - 1; x += 8) {
            this.fillBlocksRotated(world, sbb, x, var15, 0, x, var15 + 14, 0, fieldBlock, rotation);
            this.fillBlocksRotated(world, sbb, x + 2, var15, 0, x + 2, var15 + 14, 0, fieldBlock, rotation);
         }
      }
   }

   @Override
   protected void addFloors(WorldGenLevel world, BoundingBox sbb) {
   }
}
