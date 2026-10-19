package dev.ftb.mods.ftbchunks.api;

import java.util.OptionalInt;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;

@FunctionalInterface
public interface LevelMinYCalculator {
   OptionalInt getLevelMinY(Level var1, BlockPos var2);
}
