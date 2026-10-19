package twilightforest.world.components.structures.mushroomtower;

import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.levelgen.structure.BoundingBox;
import net.minecraft.world.level.levelgen.structure.StructurePiece;
import net.minecraft.world.level.levelgen.structure.StructurePieceAccessor;
import net.minecraft.world.level.levelgen.structure.pieces.StructurePieceSerializationContext;
import net.minecraft.world.level.levelgen.structure.pieces.StructurePieceType;
import twilightforest.TwilightForestMod;
import twilightforest.init.TFStructurePieceTypes;
import twilightforest.util.RotationUtil;
import twilightforest.world.components.structures.TFStructureComponentOld;
import twilightforest.world.components.structures.lichtower.TowerRoofComponent;

public class MushroomTowerMainComponent extends MushroomTowerWingComponent {
   public MushroomTowerMainComponent(StructurePieceSerializationContext ctx, CompoundTag nbt) {
      super((StructurePieceType)TFStructurePieceTypes.TFMTMai.get(), nbt);
   }

   public MushroomTowerMainComponent(RandomSource rand, int index, int x, int y, int z) {
      this(rand, index, x + 15, y + 4, z + 15, Direction.NORTH);
   }

   public MushroomTowerMainComponent(RandomSource rand, int index, int x, int y, int z, Direction rotation) {
      super((StructurePieceType)TFStructurePieceTypes.TFMTMai.get(), index, x, y, z, 15, 8 + rand.nextInt(3) * 4, rotation);
      if (this.deco == null) {
         this.deco = new MushroomTowerDecorator();
      }
   }

   protected MushroomTowerMainComponent(int i, int x, int y, int z, int pSize, int pHeight, Direction direction) {
      super((StructurePieceType)TFStructurePieceTypes.TFMTMai.get(), i, x, y, z, pSize, pHeight, direction);
   }

   @Override
   public void addChildren(StructurePiece parent, StructurePieceAccessor list, RandomSource rand) {
      if (parent != null && parent instanceof TFStructureComponentOld) {
         this.deco = ((TFStructureComponentOld)parent).deco;
      }

      this.addOpening(0, 1, this.size / 2, Rotation.CLOCKWISE_180);
      this.hasBase = true;
      Rotation mainDir = null;
      if (this.getGenDepth() < 3) {
         for (int i = 0; i < 6; i++) {
            mainDir = this.makeAscenderTower(list, rand);
            if (mainDir != null) {
               break;
            }
         }

         for (Rotation i : RotationUtil.ROTATIONS) {
            if (i != mainDir) {
               int[] dest = this.getValidOpening(rand, i);
               int childHeight = (rand.nextInt(2) + rand.nextInt(2) + 2) * 4 + 1;
               this.makeBridge(list, rand, this.getGenDepth() + 1, dest[0], dest[1], dest[2], this.size - 4, childHeight, i);
            }
         }
      } else {
         this.makeARoof(parent, list, rand);
      }
   }

   private Rotation makeAscenderTower(StructurePieceAccessor list, RandomSource rand) {
      Rotation mainDir = RotationUtil.ROTATIONS[rand.nextInt(4)];
      int[] dest = this.getValidOpening(rand, mainDir);
      int childHeight = this.height - dest[1] + (rand.nextInt(2) + rand.nextInt(2) + 3) * 4 + 1;
      boolean madeIt = this.makeBridge(list, rand, this.getGenDepth() + 1, dest[0], dest[1], dest[2], this.size - 4, childHeight, mainDir, true);
      if (madeIt) {
         TwilightForestMod.LOGGER.debug("Main tower made a bridge to another tower");
         return mainDir;
      } else {
         TwilightForestMod.LOGGER.info("Main tower failed to branch off at index {}", this.genDepth);
         return null;
      }
   }

   @Override
   public void makeARoof(StructurePiece parent, StructurePieceAccessor list, RandomSource rand) {
      TowerRoofComponent roof = new TowerRoofMushroomComponent(
         this.getGenDepth() + 1, this, 1.6F, this.getLocatorPosition().getX(), this.getLocatorPosition().getY(), this.getLocatorPosition().getZ()
      );
      list.addPiece(roof);
      roof.addChildren(this, list, rand);
   }

   @Override
   protected void makeDoorOpening(WorldGenLevel world, int dx, int dy, int dz, BoundingBox sbb) {
      super.makeDoorOpening(world, dx, dy, dz, sbb);
      if (dx == 0) {
         this.placeBlock(world, AIR, dx + 1, dy, dz, sbb);
         this.placeBlock(world, AIR, dx + 1, dy + 1, dz, sbb);
      }

      if (dx == this.size - 1) {
         this.placeBlock(world, AIR, dx - 1, dy, dz, sbb);
         this.placeBlock(world, AIR, dx - 1, dy + 1, dz, sbb);
      }

      if (dz == 0) {
         this.placeBlock(world, AIR, dx, dy, dz + 1, sbb);
         this.placeBlock(world, AIR, dx, dy + 1, dz + 1, sbb);
      }

      if (dz == this.size - 1) {
         this.placeBlock(world, AIR, dx, dy, dz - 1, sbb);
         this.placeBlock(world, AIR, dx, dy + 1, dz - 1, sbb);
      }
   }
}
