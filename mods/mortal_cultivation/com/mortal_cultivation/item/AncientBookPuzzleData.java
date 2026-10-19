package com.mortal_cultivation.item;

import java.util.Arrays;
import javax.annotation.Nullable;
import net.minecraft.core.component.DataComponents;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.CustomData;

public final class AncientBookPuzzleData {
   private static final String PUZZLE_TAG = "MortalCultivationDecipherPuzzle";
   private static final String SOLUTION_MASKS_TAG = "SolutionMasks";
   private static final String ROTATIONS_TAG = "Rotations";
   private static final String VERSION_TAG = "Version";
   private static final int LEGACY_ROTATIONS_ONLY_VERSION = 1;
   private static final int CURRENT_VERSION = 2;

   private AncientBookPuzzleData() {
   }

   @Nullable
   public static AncientBookPuzzleData.PuzzleData readPuzzle(ItemStack stack, int expectedCount, int[] legacySolutionMasks) {
      if (!stack.isEmpty() && expectedCount > 0 && legacySolutionMasks != null && legacySolutionMasks.length == expectedCount) {
         CompoundTag customTag = ((CustomData)stack.getOrDefault(DataComponents.CUSTOM_DATA, CustomData.EMPTY)).copyTag();
         if (!customTag.contains("MortalCultivationDecipherPuzzle", 10)) {
            return null;
         }

         CompoundTag puzzleTag = customTag.getCompound("MortalCultivationDecipherPuzzle");
         int version = puzzleTag.getInt("Version");
         if ((version == 1 || version == 2) && puzzleTag.contains("Rotations", 11)) {
            int[] rotations = puzzleTag.getIntArray("Rotations");
            if (rotations.length != expectedCount) {
               return null;
            }

            int[] solutionMasks;
            if (version == 1) {
               solutionMasks = Arrays.copyOf(legacySolutionMasks, legacySolutionMasks.length);
            } else {
               if (!puzzleTag.contains("SolutionMasks", 11)) {
                  return null;
               }

               solutionMasks = puzzleTag.getIntArray("SolutionMasks");
               if (solutionMasks.length != expectedCount) {
                  return null;
               }
            }

            for (int index = 0; index < rotations.length; index++) {
               rotations[index] &= 3;
            }

            return new AncientBookPuzzleData.PuzzleData(solutionMasks, rotations, version == 1);
         } else {
            return null;
         }
      } else {
         return null;
      }
   }

   public static void writePuzzle(ItemStack stack, int[] solutionMasks, int[] rotations) {
      if (!stack.isEmpty() && solutionMasks != null && rotations != null && solutionMasks.length != 0 && solutionMasks.length == rotations.length) {
         int[] storedSolutionMasks = Arrays.copyOf(solutionMasks, solutionMasks.length);
         int[] storedRotations = Arrays.copyOf(rotations, rotations.length);

         for (int index = 0; index < storedRotations.length; index++) {
            storedSolutionMasks[index] &= 15;
            storedRotations[index] &= 3;
         }

         CompoundTag customTag = ((CustomData)stack.getOrDefault(DataComponents.CUSTOM_DATA, CustomData.EMPTY)).copyTag();
         CompoundTag puzzleTag = customTag.contains("MortalCultivationDecipherPuzzle", 10)
            ? customTag.getCompound("MortalCultivationDecipherPuzzle").copy()
            : new CompoundTag();
         puzzleTag.putInt("Version", 2);
         puzzleTag.putIntArray("SolutionMasks", storedSolutionMasks);
         puzzleTag.putIntArray("Rotations", storedRotations);
         customTag.put("MortalCultivationDecipherPuzzle", puzzleTag);
         stack.set(DataComponents.CUSTOM_DATA, CustomData.of(customTag));
      }
   }

   public record PuzzleData(int[] solutionMasks, int[] rotations, boolean legacyFormat) {
      public PuzzleData {
         solutionMasks = Arrays.copyOf(solutionMasks, solutionMasks.length);
         rotations = Arrays.copyOf(rotations, rotations.length);
      }

      public int[] solutionMasks() {
         return Arrays.copyOf(this.solutionMasks, this.solutionMasks.length);
      }

      public int[] rotations() {
         return Arrays.copyOf(this.rotations, this.rotations.length);
      }
   }
}
