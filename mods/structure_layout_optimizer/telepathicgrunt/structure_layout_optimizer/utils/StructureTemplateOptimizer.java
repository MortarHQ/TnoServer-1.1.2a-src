package telepathicgrunt.structure_layout_optimizer.utils;

import it.unimi.dsi.fastutil.objects.Object2BooleanMaps;
import it.unimi.dsi.fastutil.objects.Object2BooleanOpenHashMap;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import net.minecraft.core.BlockPos;
import net.minecraft.core.BlockPos.MutableBlockPos;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.block.Mirror;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.levelgen.structure.BoundingBox;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructurePlaceSettings;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureProcessor;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplate.Palette;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplate.StructureBlockInfo;
import org.jetbrains.annotations.NotNull;
import telepathicgrunt.structure_layout_optimizer.services.PlatformService;

public class StructureTemplateOptimizer {
   private static final Map<StructureProcessor, Boolean> FINALIZE_PROCESSING_PROCESSORS = Object2BooleanMaps.synchronize(new Object2BooleanOpenHashMap());
   private static final String FINALIZE_PROCESSING_METHOD_NAME = PlatformService.INSTANCE.getFinalizeProcessingMethodName();

   @NotNull
   public static List<StructureBlockInfo> getStructureBlockInfosInBounds(Palette palette, BlockPos offset, StructurePlaceSettings structurePlaceSettings) {
      BoundingBox boundingBox = structurePlaceSettings.getBoundingBox();
      List<StructureBlockInfo> originalPositions = palette.blocks();
      if (boundingBox == null) {
         return originalPositions;
      }

      for (StructureProcessor processor : structurePlaceSettings.getProcessors()) {
         if (FINALIZE_PROCESSING_PROCESSORS.computeIfAbsent(processor, StructureTemplateOptimizer::isFinalizeProcessor)) {
            return palette.blocks();
         }
      }

      Mirror mirror = structurePlaceSettings.getMirror();
      Rotation rotation = structurePlaceSettings.getRotation();
      BlockPos pivot = structurePlaceSettings.getRotationPivot();
      List<StructureBlockInfo> listOfInBoundsRelativePositions = new ArrayList<>();
      MutableBlockPos mutableBlockPos = new MutableBlockPos();

      for (StructureBlockInfo blockInfo : palette.blocks()) {
         mutableBlockPos.set(blockInfo.pos());
         transform(mutableBlockPos, mirror, rotation, pivot);
         mutableBlockPos.move(offset);
         if (boundingBox.isInside(mutableBlockPos)) {
            listOfInBoundsRelativePositions.add(blockInfo);
         }
      }

      if (listOfInBoundsRelativePositions.isEmpty() && !originalPositions.isEmpty()) {
         listOfInBoundsRelativePositions.add(originalPositions.get(0));
      }

      return listOfInBoundsRelativePositions;
   }

   @NotNull
   private static Boolean isFinalizeProcessor(StructureProcessor structureProcessor) {
      try {
         Method method = structureProcessor.getClass()
            .getMethod(
               FINALIZE_PROCESSING_METHOD_NAME, ServerLevelAccessor.class, BlockPos.class, BlockPos.class, List.class, List.class, StructurePlaceSettings.class
            );
         return method.getDeclaringClass() != StructureProcessor.class;
      } catch (NoSuchMethodException e) {
         throw new RuntimeException("Unable to find 'finalizeProcessing' method. Report this major issue to Dev of structure_layout_optimizer", e);
      }
   }

   private static void transform(MutableBlockPos mutableBlockPos, Mirror mirror, Rotation rotation, BlockPos pivot) {
      int i = mutableBlockPos.getX();
      int j = mutableBlockPos.getY();
      int k = mutableBlockPos.getZ();
      boolean flag = true;
      switch (mirror) {
         case LEFT_RIGHT:
            k = -k;
            break;
         case FRONT_BACK:
            i = -i;
            break;
         default:
            flag = false;
      }

      int l = pivot.getX();
      int i1 = pivot.getZ();
      switch (rotation) {
         case COUNTERCLOCKWISE_90:
            mutableBlockPos.set(l - i1 + k, j, l + i1 - i);
            return;
         case CLOCKWISE_90:
            mutableBlockPos.set(l + i1 - k, j, i1 - l + i);
            return;
         case CLOCKWISE_180:
            mutableBlockPos.set(l + l - i, j, i1 + i1 - k);
            return;
         default:
            if (flag) {
               mutableBlockPos.set(i, j, k);
            }
      }
   }
}
