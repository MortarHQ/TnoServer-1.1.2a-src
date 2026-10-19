package telepathicgrunt.structure_layout_optimizer.mixins;

import com.google.common.collect.Lists;
import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.llamalad7.mixinextras.sugar.Local;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import net.minecraft.core.BlockPos;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.levelgen.structure.BoundingBox;
import net.minecraft.world.level.levelgen.structure.pools.StructurePoolElement;
import net.minecraft.world.level.levelgen.structure.pools.StructureTemplatePool;
import net.minecraft.world.level.levelgen.structure.pools.StructureTemplatePool.Projection;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplateManager;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplate.StructureBlockInfo;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.shapes.BooleanOp;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.apache.commons.lang3.mutable.MutableObject;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;
import telepathicgrunt.structure_layout_optimizer.SloConfig;
import telepathicgrunt.structure_layout_optimizer.utils.BoxOctree;
import telepathicgrunt.structure_layout_optimizer.utils.GeneralUtils;
import telepathicgrunt.structure_layout_optimizer.utils.TrojanArrayList;
import telepathicgrunt.structure_layout_optimizer.utils.TrojanVoxelShape;

@Mixin(targets = "net.minecraft.world.level.levelgen.structure.pools.JigsawPlacement$Placer")
public class JigsawPlacementPlacerMixin {
   @Final
   @Shadow
   private RandomSource random;
   @Final
   @Shadow
   private StructureTemplateManager structureTemplateManager;

   @Redirect(
      method = "tryPlacingChildren(Lnet/minecraft/world/level/levelgen/structure/PoolElementStructurePiece;Lorg/apache/commons/lang3/mutable/MutableObject;IZLnet/minecraft/world/level/LevelHeightAccessor;Lnet/minecraft/world/level/levelgen/RandomState;Lnet/minecraft/world/level/levelgen/structure/pools/alias/PoolAliasLookup;Lnet/minecraft/world/level/levelgen/structure/templatesystem/LiquidSettings;)V",
      at = @At(
         value = "INVOKE",
         target = "Lnet/minecraft/world/level/block/JigsawBlock;canAttach(Lnet/minecraft/world/level/levelgen/structure/templatesystem/StructureTemplate$StructureBlockInfo;Lnet/minecraft/world/level/levelgen/structure/templatesystem/StructureTemplate$StructureBlockInfo;)Z"
      )
   )
   private boolean structureLayoutOptimizer$optimizeJigsawConnecting(StructureBlockInfo jigsaw1, StructureBlockInfo jigsaw2) {
      return GeneralUtils.canJigsawsAttach(jigsaw1, jigsaw2);
   }

   @ModifyExpressionValue(
      method = "tryPlacingChildren",
      at = @At(
         value = "INVOKE",
         target = "Lnet/minecraft/world/level/levelgen/structure/pools/StructurePoolElement;getShuffledJigsawBlocks(Lnet/minecraft/world/level/levelgen/structure/templatesystem/StructureTemplateManager;Lnet/minecraft/core/BlockPos;Lnet/minecraft/world/level/block/Rotation;Lnet/minecraft/util/RandomSource;)Ljava/util/List;",
         ordinal = 1
      )
   )
   private List<StructureBlockInfo> structureLayoutOptimizer$skipBlockedJigsaws(
      List<StructureBlockInfo> original,
      @Local(ordinal = 0, argsOnly = true) boolean useExpansionHack,
      @Local(ordinal = 2) MutableObject<VoxelShape> voxelShapeMutableObject,
      @Local(ordinal = 1) StructurePoolElement structurePoolElement,
      @Local(ordinal = 0) StructureBlockInfo parentJigsawBlockInfo,
      @Local(ordinal = 2) BlockPos parentTargetPosition
   ) {
      if (voxelShapeMutableObject.getValue() instanceof TrojanVoxelShape trojanVoxelShape) {
         Projection candidatePlacementBehavior = structurePoolElement.getProjection();
         boolean isCandidateRigid = candidatePlacementBehavior == Projection.RIGID;
         if (isCandidateRigid
            && (!trojanVoxelShape.boxOctree.boundaryContains(parentTargetPosition) || trojanVoxelShape.boxOctree.withinAnyBox(parentTargetPosition))) {
            return new ArrayList<>();
         }
      }

      return original;
   }

   @WrapOperation(
      method = "tryPlacingChildren(Lnet/minecraft/world/level/levelgen/structure/PoolElementStructurePiece;Lorg/apache/commons/lang3/mutable/MutableObject;IZLnet/minecraft/world/level/LevelHeightAccessor;Lnet/minecraft/world/level/levelgen/RandomState;Lnet/minecraft/world/level/levelgen/structure/pools/alias/PoolAliasLookup;Lnet/minecraft/world/level/levelgen/structure/templatesystem/LiquidSettings;)V",
      at = @At(
         value = "INVOKE",
         target = "Lnet/minecraft/world/phys/shapes/Shapes;joinIsNotEmpty(Lnet/minecraft/world/phys/shapes/VoxelShape;Lnet/minecraft/world/phys/shapes/VoxelShape;Lnet/minecraft/world/phys/shapes/BooleanOp;)Z"
      )
   )
   private boolean structureLayoutOptimizer$replaceVoxelShape2(
      VoxelShape parentBounds, VoxelShape pieceShape, BooleanOp booleanOp, Operation<Boolean> original, @Local(ordinal = 3) BoundingBox pieceBounds
   ) {
      if (parentBounds instanceof TrojanVoxelShape trojanVoxelShape) {
         AABB pieceAABB = AABB.of(pieceBounds).deflate(0.25);
         return !trojanVoxelShape.boxOctree.withinBoundsButNotIntersectingChildren(pieceAABB);
      } else {
         return (Boolean)original.call(new Object[]{parentBounds, pieceShape, booleanOp});
      }
   }

   @Redirect(
      method = "tryPlacingChildren(Lnet/minecraft/world/level/levelgen/structure/PoolElementStructurePiece;Lorg/apache/commons/lang3/mutable/MutableObject;IZLnet/minecraft/world/level/LevelHeightAccessor;Lnet/minecraft/world/level/levelgen/RandomState;Lnet/minecraft/world/level/levelgen/structure/pools/alias/PoolAliasLookup;Lnet/minecraft/world/level/levelgen/structure/templatesystem/LiquidSettings;)V",
      at = @At(value = "INVOKE", target = "Lorg/apache/commons/lang3/mutable/MutableObject;setValue(Ljava/lang/Object;)V", ordinal = 0)
   )
   private void structureLayoutOptimizer$replaceVoxelShape3(MutableObject<VoxelShape> instance, Object value, @Local(ordinal = 0) BoundingBox pieceBounds) {
      TrojanVoxelShape trojanVoxelShape = new TrojanVoxelShape(new BoxOctree(AABB.of(pieceBounds)));
      instance.setValue(trojanVoxelShape);
   }

   @Redirect(
      method = "tryPlacingChildren(Lnet/minecraft/world/level/levelgen/structure/PoolElementStructurePiece;Lorg/apache/commons/lang3/mutable/MutableObject;IZLnet/minecraft/world/level/LevelHeightAccessor;Lnet/minecraft/world/level/levelgen/RandomState;Lnet/minecraft/world/level/levelgen/structure/pools/alias/PoolAliasLookup;Lnet/minecraft/world/level/levelgen/structure/templatesystem/LiquidSettings;)V",
      at = @At(value = "INVOKE", target = "Lorg/apache/commons/lang3/mutable/MutableObject;setValue(Ljava/lang/Object;)V", ordinal = 1)
   )
   private void structureLayoutOptimizer$replaceVoxelShape4(MutableObject<VoxelShape> instance, Object value, @Local(ordinal = 3) BoundingBox pieceBounds) {
      if (instance.getValue() instanceof TrojanVoxelShape trojanVoxelShape) {
         trojanVoxelShape.boxOctree.addBox(AABB.of(pieceBounds));
      }
   }

   @Redirect(
      method = "tryPlacingChildren(Lnet/minecraft/world/level/levelgen/structure/PoolElementStructurePiece;Lorg/apache/commons/lang3/mutable/MutableObject;IZLnet/minecraft/world/level/LevelHeightAccessor;Lnet/minecraft/world/level/levelgen/RandomState;Lnet/minecraft/world/level/levelgen/structure/pools/alias/PoolAliasLookup;Lnet/minecraft/world/level/levelgen/structure/templatesystem/LiquidSettings;)V",
      at = @At(
         value = "INVOKE",
         target = "Lnet/minecraft/world/phys/shapes/Shapes;joinUnoptimized(Lnet/minecraft/world/phys/shapes/VoxelShape;Lnet/minecraft/world/phys/shapes/VoxelShape;Lnet/minecraft/world/phys/shapes/BooleanOp;)Lnet/minecraft/world/phys/shapes/VoxelShape;"
      )
   )
   private VoxelShape structureLayoutOptimizer$turnOffOldVoxelShapeBehavior1(VoxelShape indexmerger, VoxelShape indexmerger1, BooleanOp indexmerger2) {
      return Shapes.empty();
   }

   @Redirect(
      method = "tryPlacingChildren(Lnet/minecraft/world/level/levelgen/structure/PoolElementStructurePiece;Lorg/apache/commons/lang3/mutable/MutableObject;IZLnet/minecraft/world/level/LevelHeightAccessor;Lnet/minecraft/world/level/levelgen/RandomState;Lnet/minecraft/world/level/levelgen/structure/pools/alias/PoolAliasLookup;Lnet/minecraft/world/level/levelgen/structure/templatesystem/LiquidSettings;)V",
      at = @At(
         value = "INVOKE",
         target = "Lnet/minecraft/world/level/levelgen/structure/pools/StructureTemplatePool;getShuffledTemplates(Lnet/minecraft/util/RandomSource;)Ljava/util/List;",
         ordinal = 0
      )
   )
   private List<StructurePoolElement> structureLayoutOptimizer$removeDuplicateTemplatePoolElementLists(StructureTemplatePool instance, RandomSource random) {
      if (!SloConfig.deduplicateShuffledTemplatePoolElementList) {
         return instance.getShuffledTemplates(random);
      }

      LinkedHashSet<StructurePoolElement> uniquePieces = new LinkedHashSet<>(((StructureTemplatePoolAccessor)instance).getRawTemplates().size());

      for (StructurePoolElement piece : instance.getShuffledTemplates(random)) {
         uniquePieces.add(piece);
      }

      int uniquePiecesFound = uniquePieces.size();
      List<StructurePoolElement> deduplicatedListOfPieces = new ArrayList<>(uniquePiecesFound);

      for (int i = 0; i < uniquePiecesFound; i++) {
         deduplicatedListOfPieces.add(uniquePieces.removeFirst());
      }

      return deduplicatedListOfPieces;
   }

   @Redirect(
      method = "tryPlacingChildren(Lnet/minecraft/world/level/levelgen/structure/PoolElementStructurePiece;Lorg/apache/commons/lang3/mutable/MutableObject;IZLnet/minecraft/world/level/LevelHeightAccessor;Lnet/minecraft/world/level/levelgen/RandomState;Lnet/minecraft/world/level/levelgen/structure/pools/alias/PoolAliasLookup;Lnet/minecraft/world/level/levelgen/structure/templatesystem/LiquidSettings;)V",
      at = @At(value = "INVOKE", target = "Lcom/google/common/collect/Lists;newArrayList()Ljava/util/ArrayList;", ordinal = 0)
   )
   private ArrayList<StructurePoolElement> structureLayoutOptimizer$skipDuplicateTemplatePoolElementLists1() {
      return SloConfig.deduplicateShuffledTemplatePoolElementList ? Lists.newArrayList() : new TrojanArrayList<>();
   }

   @ModifyExpressionValue(
      method = "tryPlacingChildren(Lnet/minecraft/world/level/levelgen/structure/PoolElementStructurePiece;Lorg/apache/commons/lang3/mutable/MutableObject;IZLnet/minecraft/world/level/LevelHeightAccessor;Lnet/minecraft/world/level/levelgen/RandomState;Lnet/minecraft/world/level/levelgen/structure/pools/alias/PoolAliasLookup;Lnet/minecraft/world/level/levelgen/structure/templatesystem/LiquidSettings;)V",
      at = @At(
         value = "INVOKE",
         target = "Lnet/minecraft/world/level/block/Rotation;getShuffled(Lnet/minecraft/util/RandomSource;)Ljava/util/List;",
         ordinal = 0
      )
   )
   private List<Rotation> structureLayoutOptimizer$skipDuplicateTemplatePoolElementLists2(
      List<Rotation> original, @Local(ordinal = 0) List<StructurePoolElement> list, @Local(ordinal = 1) StructurePoolElement structurepoolelement1
   ) {
      if (!SloConfig.deduplicateShuffledTemplatePoolElementList && list instanceof TrojanArrayList<StructurePoolElement> trojanArrayList) {
         if (trojanArrayList.elementsAlreadyParsed.contains(structurepoolelement1)) {
            for (Rotation rotation1 : original) {
               structurepoolelement1.getShuffledJigsawBlocks(this.structureTemplateManager, BlockPos.ZERO, rotation1, this.random);
            }

            return new ArrayList<>();
         }

         trojanArrayList.elementsAlreadyParsed.add(structurepoolelement1);
      }

      return original;
   }
}
