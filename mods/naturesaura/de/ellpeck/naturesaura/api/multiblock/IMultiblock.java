package de.ellpeck.naturesaura.api.multiblock;

import java.util.Map;
import java.util.function.BiFunction;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.Level;

public interface IMultiblock {
   boolean isComplete(Level var1, BlockPos var2);

   boolean forEach(BlockPos var1, char var2, BiFunction<BlockPos, Matcher, Boolean> var3);

   BlockPos getStart(BlockPos var1);

   char getChar(BlockPos var1);

   ResourceLocation getName();

   Map<BlockPos, Matcher> getMatchers();

   int getWidth();

   int getHeight();

   int getDepth();

   int getXOffset();

   int getYOffset();

   int getZOffset();

   char[][][] getRawPattern();
}
