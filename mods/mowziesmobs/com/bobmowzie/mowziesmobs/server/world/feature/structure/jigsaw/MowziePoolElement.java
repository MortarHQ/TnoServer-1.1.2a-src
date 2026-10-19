package com.bobmowzie.mowziesmobs.server.world.feature.structure.jigsaw;

import com.mojang.datafixers.util.Either;
import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Holder;
import net.minecraft.core.Vec3i;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.block.JigsawBlock;
import net.minecraft.world.level.block.Mirror;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.block.entity.JigsawBlockEntity.JointType;
import net.minecraft.world.level.levelgen.structure.BoundingBox;
import net.minecraft.world.level.levelgen.structure.pools.SinglePoolElement;
import net.minecraft.world.level.levelgen.structure.pools.StructureTemplatePool.Projection;
import net.minecraft.world.level.levelgen.structure.templatesystem.LiquidSettings;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureProcessorList;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplate;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplateManager;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplate.StructureBlockInfo;

public class MowziePoolElement extends SinglePoolElement {
   public static final MapCodec<MowziePoolElement> CODEC = RecordCodecBuilder.mapCodec(
      builder -> builder.group(
            templateCodec(),
            processorsCodec(),
            projectionCodec(),
            MowziePoolElement.BoundsParams.CODEC
               .optionalFieldOf(
                  "bounds",
                  new MowziePoolElement.BoundsParams(
                     false,
                     BlockPos.ZERO,
                     BlockPos.ZERO,
                     BlockPos.ZERO,
                     Optional.empty(),
                     Optional.empty(),
                     Optional.empty(),
                     BlockPos.ZERO,
                     BlockPos.ZERO,
                     true,
                     false,
                     Optional.empty(),
                     Optional.empty()
                  )
               )
               .forGetter(element -> element.bounds),
            MowziePoolElement.ConditionsParams.CODEC
               .optionalFieldOf(
                  "conditions",
                  new MowziePoolElement.ConditionsParams(
                     -1, -1, Optional.empty(), Optional.empty(), Optional.empty(), Optional.empty(), Optional.empty(), Collections.emptyList(), 1
                  )
               )
               .forGetter(element -> element.conditions),
            MowziePoolElement.TagsParams.CODEC
               .optionalFieldOf("tags", new MowziePoolElement.TagsParams(Collections.emptyList(), false, Optional.empty(), 1))
               .forGetter(element -> element.tags),
            Codec.BOOL.optionalFieldOf("two_way", false).forGetter(element -> element.twoWay),
            Codec.INT.optionalFieldOf("gen_order", 0).forGetter(element -> element.genOrder),
            Codec.INT.optionalFieldOf("priority", 0).forGetter(element -> element.priority)
         )
         .apply(builder, MowziePoolElement::new)
   );
   public final MowziePoolElement.BoundsParams bounds;
   public final MowziePoolElement.ConditionsParams conditions;
   public final MowziePoolElement.TagsParams tags;
   public final boolean twoWay;
   public final int genOrder;
   public final int priority;

   protected MowziePoolElement(
      Either<ResourceLocation, StructureTemplate> p_210415_,
      Holder<StructureProcessorList> p_210416_,
      Projection p_210417_,
      MowziePoolElement.BoundsParams bounds,
      MowziePoolElement.ConditionsParams conditions,
      MowziePoolElement.TagsParams tags,
      boolean twoWay,
      int genOrder,
      int priority
   ) {
      super(p_210415_, p_210416_, p_210417_, Optional.of(LiquidSettings.IGNORE_WATERLOGGING));
      this.bounds = bounds;
      this.conditions = conditions;
      this.tags = tags;
      this.twoWay = twoWay;
      this.genOrder = genOrder;
      this.priority = priority;
   }

   public static boolean canAttachTwoWays(StructureBlockInfo p_54246_, StructureBlockInfo p_54247_) {
      Direction direction = JigsawBlock.getFrontFacing(p_54246_.state());
      Direction direction1 = JigsawBlock.getFrontFacing(p_54247_.state());
      Direction direction2 = JigsawBlock.getTopFacing(p_54246_.state());
      Direction direction3 = JigsawBlock.getTopFacing(p_54247_.state());
      JointType jigsawblockentity$jointtype = JointType.byName(p_54246_.nbt().getString("joint"))
         .orElseGet(() -> direction.getAxis().isHorizontal() ? JointType.ALIGNED : JointType.ROLLABLE);
      boolean flag = jigsawblockentity$jointtype == JointType.ROLLABLE;
      return direction == direction1 && (flag || direction2 == direction3) && p_54246_.nbt().getString("target").equals(p_54247_.nbt().getString("name"));
   }

   public boolean ignoresBounds() {
      return this.bounds.ignoreBounds;
   }

   public boolean placeBounds() {
      return this.bounds.placeBounds;
   }

   public boolean twoWay() {
      return this.twoWay;
   }

   public Vec3i offset() {
      return new Vec3i(this.bounds.offset.getX(), this.bounds.offset.getY(), this.bounds.offset.getZ());
   }

   public boolean checkCriteria(MowzieJigsawManager.PieceState pieceState, MowzieJigsawManager.Placer placer) {
      int maxDepth = this.conditions.maxDepth;
      if (maxDepth != -1 && pieceState.depth > maxDepth) {
         return false;
      }

      int minDepth = this.conditions.minDepth;
      if (minDepth != -1 && pieceState.depth < minDepth) {
         return false;
      }

      MowzieJigsawManager.PieceState parent = pieceState;

      for (int i = 0; i < this.conditions.forbiddenParentsDepth && parent != null; i++) {
         String parentName = parent.piece.getElement().toString().split("[\\[\\]]")[2];
         if (this.conditions.forbiddenParents.contains(parentName)) {
            return false;
         }

         parent = parent.parent;
      }

      if (this.tags.needsTag.isPresent()) {
         parent = pieceState;
         boolean foundTag = false;

         for (int i = 0; i < this.tags.needsTagDepth; i++) {
            if (this.tags.needsTag.get().equals(parent.tag)) {
               foundTag = true;
               break;
            }

            parent = parent.parent;
         }

         if (!foundTag) {
            return false;
         }
      }

      if (!(placer instanceof MowzieJigsawManager.FallbackPlacer)) {
         if (this.conditions.minRequiredPaths.isPresent() && placer.numPaths < this.conditions.minRequiredPaths.get()) {
            return false;
         }

         if (this.conditions.maxAllowedPaths.isPresent() && placer.numPaths > this.conditions.maxAllowedPaths.get()) {
            return false;
         }
      }

      return true;
   }

   public String getRandomTag(RandomSource random) {
      if (this.tags.tags.isEmpty()) {
         return null;
      }

      int total = 0;

      for (MowziePoolElement.Tag tag : this.tags.tags) {
         total += tag.weight;
      }

      float rand = random.nextFloat() * total;
      total = 0;

      for (MowziePoolElement.Tag tag : this.tags.tags) {
         total += tag.weight;
         if (total >= rand) {
            return tag.tag;
         }
      }

      return null;
   }

   public BoundingBox getBoundingBox(StructureTemplateManager structureManager, BlockPos blockPos, Rotation rotation) {
      StructureTemplate structuretemplate = this.getTemplate(structureManager);
      Vec3i sizeVec = structuretemplate.getSize().offset(-1, -1, -1);
      BlockPos blockpos = StructureTemplate.transform(BlockPos.ZERO.offset(this.bounds.boundsMinOffset), Mirror.NONE, rotation, BlockPos.ZERO);
      BlockPos blockpos1 = StructureTemplate.transform(BlockPos.ZERO.offset(sizeVec).offset(this.bounds.boundsMaxOffset), Mirror.NONE, rotation, BlockPos.ZERO);
      return BoundingBox.fromCorners(blockpos, blockpos1).move(blockPos);
   }

   public BoundingBox getCheckBoundingBox(StructureTemplateManager structureManager, BlockPos blockPos, Rotation rotation) {
      StructureTemplate structuretemplate = this.getTemplate(structureManager);
      Vec3i sizeVec = structuretemplate.getSize().offset(-1, -1, -1);
      BlockPos blockpos = StructureTemplate.transform(
         BlockPos.ZERO.offset(this.bounds.boundsMinOffset).offset(this.bounds.checkBoundsMinOffset), Mirror.NONE, rotation, BlockPos.ZERO
      );
      BlockPos blockpos1 = StructureTemplate.transform(
         BlockPos.ZERO.offset(sizeVec).offset(this.bounds.boundsMaxOffset).offset(this.bounds.checkBoundsMaxOffset), Mirror.NONE, rotation, BlockPos.ZERO
      );
      return BoundingBox.fromCorners(blockpos, blockpos1).move(blockPos);
   }

   public BoundingBox getInteriorBoundingBox(StructureTemplateManager structureManager, BlockPos blockPos, Rotation rotation) {
      if (this.bounds.interiorBoundsMaxOffset.isEmpty() && this.bounds.interiorBoundsMinOffset.isEmpty()) {
         return null;
      }

      BlockPos interiorBoundsMinOffset = BlockPos.ZERO;
      BlockPos interiorBoundsMaxOffset = BlockPos.ZERO;
      if (this.bounds.interiorBoundsMinOffset.isPresent()) {
         interiorBoundsMinOffset = this.bounds.interiorBoundsMinOffset.get();
      }

      if (this.bounds.interiorBoundsMaxOffset.isPresent()) {
         interiorBoundsMaxOffset = this.bounds.interiorBoundsMaxOffset.get();
      }

      StructureTemplate structuretemplate = this.getTemplate(structureManager);
      Vec3i sizeVec = structuretemplate.getSize().offset(-1, -1, -1);
      BlockPos blockpos = StructureTemplate.transform(
         BlockPos.ZERO.offset(this.bounds.boundsMinOffset).offset(interiorBoundsMinOffset), Mirror.NONE, rotation, BlockPos.ZERO
      );
      BlockPos blockpos1 = StructureTemplate.transform(
         BlockPos.ZERO.offset(sizeVec).offset(this.bounds.boundsMaxOffset).offset(interiorBoundsMaxOffset), Mirror.NONE, rotation, BlockPos.ZERO
      );
      return BoundingBox.fromCorners(blockpos, blockpos1).move(blockPos);
   }

   public static class BoundsParams {
      public static final Codec<MowziePoolElement.BoundsParams> CODEC = RecordCodecBuilder.create(
         builder -> builder.group(
               Codec.BOOL.optionalFieldOf("ignore_bounds", false).forGetter(element -> element.ignoreBounds),
               BlockPos.CODEC.optionalFieldOf("bounds_min_offset", BlockPos.ZERO).forGetter(element -> element.boundsMinOffset),
               BlockPos.CODEC.optionalFieldOf("bounds_max_offset", BlockPos.ZERO).forGetter(element -> element.boundsMaxOffset),
               BlockPos.CODEC.optionalFieldOf("offset", BlockPos.ZERO).forGetter(element -> element.offset),
               Codec.STRING.optionalFieldOf("special_bounds").forGetter(element -> element.specialBounds),
               Codec.STRING.optionalFieldOf("needs_overlap_bounds").forGetter(element -> element.needsOverlapBounds),
               Codec.STRING.optionalFieldOf("forbidden_overlap_bounds").forGetter(element -> element.forbiddenOverlapBounds),
               BlockPos.CODEC.optionalFieldOf("check_bounds_min_offset", BlockPos.ZERO).forGetter(element -> element.checkBoundsMinOffset),
               BlockPos.CODEC.optionalFieldOf("check_bounds_max_offset", BlockPos.ZERO).forGetter(element -> element.checkBoundsMaxOffset),
               Codec.BOOL.optionalFieldOf("place_bounds", true).forGetter(element -> element.placeBounds),
               Codec.BOOL.optionalFieldOf("ignore_parent_bounds", false).forGetter(element -> element.ignoreParentBounds),
               BlockPos.CODEC.optionalFieldOf("interior_bounds_min_offset").forGetter(element -> element.interiorBoundsMinOffset),
               BlockPos.CODEC.optionalFieldOf("interior_bounds_max_offset").forGetter(element -> element.interiorBoundsMaxOffset)
            )
            .apply(builder, MowziePoolElement.BoundsParams::new)
      );
      public final boolean ignoreBounds;
      public final BlockPos boundsMinOffset;
      public final BlockPos boundsMaxOffset;
      public final BlockPos offset;
      public final Optional<String> specialBounds;
      public final Optional<String> needsOverlapBounds;
      public final Optional<String> forbiddenOverlapBounds;
      public final BlockPos checkBoundsMinOffset;
      public final BlockPos checkBoundsMaxOffset;
      public final boolean placeBounds;
      public final boolean ignoreParentBounds;
      public final Optional<BlockPos> interiorBoundsMinOffset;
      public final Optional<BlockPos> interiorBoundsMaxOffset;

      private BoundsParams(
         boolean ignoreBounds,
         BlockPos boundsMinOffset,
         BlockPos boundsMaxOffset,
         BlockPos offset,
         Optional<String> specialBounds,
         Optional<String> needsOverlapBounds,
         Optional<String> forbiddenOverlapBounds,
         BlockPos checkBoundsMinOffset,
         BlockPos checkBoundsMaxOffset,
         boolean placeBounds,
         boolean ignoreParentBounds,
         Optional<BlockPos> interiorBoundsMinOffset,
         Optional<BlockPos> interiorBoundsMaxOffset
      ) {
         this.ignoreBounds = ignoreBounds;
         this.boundsMinOffset = boundsMinOffset;
         this.boundsMaxOffset = boundsMaxOffset;
         this.offset = offset;
         this.specialBounds = specialBounds;
         this.forbiddenOverlapBounds = forbiddenOverlapBounds;
         this.needsOverlapBounds = needsOverlapBounds;
         this.checkBoundsMinOffset = checkBoundsMinOffset;
         this.checkBoundsMaxOffset = checkBoundsMaxOffset;
         this.placeBounds = placeBounds;
         this.ignoreParentBounds = ignoreParentBounds;
         this.interiorBoundsMinOffset = interiorBoundsMinOffset;
         this.interiorBoundsMaxOffset = interiorBoundsMaxOffset;
      }
   }

   public static class ConditionsParams {
      public static final Codec<MowziePoolElement.ConditionsParams> CODEC = RecordCodecBuilder.create(
         builder -> builder.group(
               Codec.INT.optionalFieldOf("min_depth", -1).forGetter(element -> element.minDepth),
               Codec.INT.optionalFieldOf("max_depth", -1).forGetter(element -> element.maxDepth),
               Codec.INT.optionalFieldOf("min_height").forGetter(element -> element.minHeight),
               Codec.INT.optionalFieldOf("max_height").forGetter(element -> element.maxHeight),
               Codec.INT.optionalFieldOf("min_required_paths").forGetter(element -> element.minRequiredPaths),
               Codec.INT.optionalFieldOf("max_allowed_paths").forGetter(element -> element.maxAllowedPaths),
               Codec.INT.optionalFieldOf("num_paths_override").forGetter(element -> element.numPathsOverride),
               Codec.STRING.listOf().optionalFieldOf("forbidden_parents", Collections.emptyList()).forGetter(element -> element.forbiddenParents),
               Codec.INT.optionalFieldOf("forbidden_parents_depth", 1).forGetter(element -> element.forbiddenParentsDepth)
            )
            .apply(builder, MowziePoolElement.ConditionsParams::new)
      );
      public final int minDepth;
      public final int maxDepth;
      public final Optional<Integer> minHeight;
      public final Optional<Integer> maxHeight;
      public final Optional<Integer> minRequiredPaths;
      public final Optional<Integer> maxAllowedPaths;
      public final Optional<Integer> numPathsOverride;
      public final List<String> forbiddenParents;
      public final int forbiddenParentsDepth;

      private ConditionsParams(
         int minDepth,
         int maxDepth,
         Optional<Integer> minHeight,
         Optional<Integer> maxHeight,
         Optional<Integer> minRequiredPaths,
         Optional<Integer> maxAllowedPaths,
         Optional<Integer> numPathsOverride,
         List<String> forbiddenParents,
         int forbiddenParentsDepth
      ) {
         this.minDepth = minDepth;
         this.maxDepth = maxDepth;
         this.minHeight = minHeight;
         this.maxHeight = maxHeight;
         this.minRequiredPaths = minRequiredPaths;
         this.maxAllowedPaths = maxAllowedPaths;
         this.numPathsOverride = numPathsOverride;
         this.forbiddenParents = forbiddenParents;
         this.forbiddenParentsDepth = forbiddenParentsDepth;
      }
   }

   public record Tag(String tag, int weight) {
      public static final Codec<MowziePoolElement.Tag> CODEC = RecordCodecBuilder.create(
         builder -> builder.group(
               Codec.STRING.fieldOf("tag").forGetter(element -> element.tag), Codec.INT.optionalFieldOf("weight", 1).forGetter(element -> element.weight)
            )
            .apply(builder, MowziePoolElement.Tag::new)
      );
   }

   public static class TagsParams {
      public static final Codec<MowziePoolElement.TagsParams> CODEC = RecordCodecBuilder.create(
         builder -> builder.group(
               MowziePoolElement.Tag.CODEC.listOf().optionalFieldOf("possible_tags", Collections.emptyList()).forGetter(element -> element.tags),
               Codec.BOOL.optionalFieldOf("inherits_tag", false).forGetter(element -> element.inheritsTag),
               Codec.STRING.optionalFieldOf("needs_tag").forGetter(element -> element.needsTag),
               Codec.INT.optionalFieldOf("needs_tag_depth", 1).forGetter(element -> element.needsTagDepth)
            )
            .apply(builder, MowziePoolElement.TagsParams::new)
      );
      public final List<MowziePoolElement.Tag> tags;
      public final boolean inheritsTag;
      public final Optional<String> needsTag;
      public final int needsTagDepth;

      private TagsParams(List<MowziePoolElement.Tag> tags, boolean inheritsTag, Optional<String> needsTag, int needsTagDepth) {
         this.tags = tags;
         this.inheritsTag = inheritsTag;
         this.needsTag = needsTag;
         this.needsTagDepth = needsTagDepth;
      }
   }
}
