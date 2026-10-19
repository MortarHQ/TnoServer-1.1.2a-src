package twilightforest.world.components.structures.lichtowerrevamp;

import java.util.List;
import net.minecraft.Util;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Holder;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.tags.TagKey;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.decoration.PaintingVariant;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.StructureManager;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.CandleBlock;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.levelgen.structure.BoundingBox;
import net.minecraft.world.level.levelgen.structure.StructurePieceAccessor;
import net.minecraft.world.level.levelgen.structure.TerrainAdjustment;
import net.minecraft.world.level.levelgen.structure.Structure.GenerationContext;
import net.minecraft.world.level.levelgen.structure.pieces.StructurePieceSerializationContext;
import net.minecraft.world.level.levelgen.structure.pieces.StructurePieceType;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplateManager;
import net.neoforged.neoforge.common.world.PieceBeardifierModifier;
import org.apache.commons.lang3.StringUtils;
import org.jetbrains.annotations.NotNull;
import twilightforest.TwilightForestMod;
import twilightforest.data.tags.CustomTagGenerator;
import twilightforest.init.TFStructurePieceTypes;
import twilightforest.util.DirectionUtil;
import twilightforest.util.entities.EntityUtil;
import twilightforest.util.jigsaw.JigsawPlaceContext;
import twilightforest.util.jigsaw.JigsawRecord;
import twilightforest.world.components.structures.SpawnIndexProvider;
import twilightforest.world.components.structures.TwilightJigsawPiece;

public final class LichBossRoom extends TwilightJigsawPiece implements PieceBeardifierModifier, SpawnIndexProvider.Deny {
   public LichBossRoom(StructurePieceSerializationContext ctx, CompoundTag compoundTag) {
      super((StructurePieceType)TFStructurePieceTypes.LICH_BOSS_ROOM.get(), compoundTag, ctx, readSettings(compoundTag));
      LichTowerUtil.addDefaultProcessors(this.placeSettings);
   }

   public LichBossRoom(StructureTemplateManager structureManager, JigsawPlaceContext jigsawContext) {
      super(
         (StructurePieceType)TFStructurePieceTypes.LICH_BOSS_ROOM.get(),
         1,
         structureManager,
         TwilightForestMod.prefix("lich_tower/tower_boss_room"),
         jigsawContext
      );
      LichTowerUtil.addDefaultProcessors(this.placeSettings);
   }

   @Override
   public void postProcess(
      WorldGenLevel level,
      StructureManager structureManager,
      ChunkGenerator chunkGen,
      RandomSource random,
      BoundingBox chunkBounds,
      ChunkPos chunkPos,
      BlockPos structureCenterPos
   ) {
      super.postProcess(level, structureManager, chunkGen, random, chunkBounds, chunkPos, structureCenterPos);
      BlockPos center = this.getSourceJigsaw().pos().offset(this.templatePosition).above(9);
      long aLong = center.asLong();
      random.setSeed(aLong);
      BlockState candle = (BlockState)Blocks.CANDLE.defaultBlockState().setValue(CandleBlock.LIT, true);
      float angle = 0.0F;

      for (int i = 0; i < 20; i++) {
         angle += random.nextFloat();
         float range = Mth.clampedLerp(7.0F, 11.0F, random.nextFloat());
         int x = Math.round(Mth.cos(angle) * range);
         int z = Math.round(Mth.sin(angle) * range);
         int y = random.nextInt(3);
         BlockPos placeAt = center.offset(x, y, z);
         if (chunkBounds.isInside(placeAt) && level.getBlockState(placeAt).isAir() && level.getBlockState(placeAt.below()).isAir()) {
            level.setBlock(placeAt, candle, 2);
         }
      }
   }

   @Override
   protected void handleDataMarker(
      String label, BlockPos pos, WorldGenLevel level, RandomSource random, BoundingBox chunkBounds, ChunkGenerator chunkGen, Rotation rotation
   ) {
      placePainting(
         label, pos, level, random, chunkBounds, this.placeSettings.getRotation(), 3, 3, CustomTagGenerator.PaintingVariantTagGenerator.LICH_BOSS_PAINTINGS
      );
   }

   public static boolean placePainting(
      String label,
      BlockPos pos,
      ServerLevelAccessor level,
      RandomSource random,
      BoundingBox chunkBounds,
      Rotation rotation,
      int limitTries,
      int rarityFactor,
      TagKey<PaintingVariant> lichTowerPaintings
   ) {
      if (chunkBounds.isInside(pos) && random.nextInt(rarityFactor) == 0) {
         String[] params = label.split(":");
         if (params.length != 3) {
            return false;
         } else {
            Direction dir = DirectionUtil.fromStringOrElse(params[0], null);
            if (dir != null
               && !dir.getAxis().isVertical()
               && StringUtils.isNumeric(params[1])
               && StringUtils.isNumeric(params[2])
               && level instanceof WorldGenLevel genLevel) {
               dir = rotation.rotate(dir);
               int width = Integer.parseInt(params[1]);
               int height = Integer.parseInt(params[2]);
               return placePainting(pos, random, limitTries, lichTowerPaintings, genLevel, width, height, dir);
            } else {
               return false;
            }
         }
      } else {
         return false;
      }
   }

   public static boolean placePainting(
      BlockPos pos,
      RandomSource random,
      int limitTries,
      TagKey<PaintingVariant> lichTowerPaintings,
      WorldGenLevel genLevel,
      int width,
      int height,
      @NotNull Direction dir
   ) {
      int maxArea = width * height;
      List<Holder<PaintingVariant>> paintingsOfSizeOrSmaller = EntityUtil.getPaintingsOfSizeOrSmaller(genLevel, lichTowerPaintings, width, height);
      Util.shuffle(paintingsOfSizeOrSmaller, random);

      for (Holder<PaintingVariant> paintingHolder : paintingsOfSizeOrSmaller) {
         PaintingVariant painting = (PaintingVariant)paintingHolder.value();
         int area = painting.width() * painting.height();
         if (random.nextInt(maxArea) <= area && (EntityUtil.tryHangPainting(genLevel, pos, dir, paintingHolder) || limitTries-- <= 0)) {
            return true;
         }
      }

      return false;
   }

   @Override
   protected void processJigsaw(
      TwilightJigsawPiece parent, StructurePieceAccessor pieceAccessor, GenerationContext context, JigsawRecord connection, int jigsawIndex
   ) {
      if ("twilightforest:lich_tower/tower_below".equals(connection.target())) {
         JigsawPlaceContext placeableJunction = JigsawPlaceContext.pickPlaceableJunction(
            this.templatePosition(),
            connection.pos(),
            connection.orientation(),
            this.structureManager,
            TwilightForestMod.prefix("lich_tower/tower_boss_roof"),
            "twilightforest:lich_tower/tower_below",
            context.random()
         );
         if (placeableJunction != null) {
            LichBossRoof lichBossRoof = new LichBossRoof(this.structureManager, placeableJunction);
            pieceAccessor.addPiece(lichBossRoof);
            lichBossRoof.addJigsaws(this, pieceAccessor, context);
         }
      }
   }

   @Override
   public BoundingBox getBeardifierBox() {
      return this.boundingBox;
   }

   @Override
   public TerrainAdjustment getTerrainAdjustment() {
      return TerrainAdjustment.NONE;
   }

   @Override
   public int getGroundLevelDelta() {
      return 0;
   }
}
