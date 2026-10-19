package dev.ftb.mods.ftbchunks;

import dev.ftb.mods.ftbchunks.api.LevelMinYCalculator;
import dev.ftb.mods.ftbchunks.api.event.CustomMinYEvent;
import java.util.List;
import java.util.OptionalInt;
import java.util.concurrent.CopyOnWriteArrayList;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;

public class CustomMinYRegistryImpl implements CustomMinYEvent.CustomMinYRegistry {
   private static final CustomMinYRegistryImpl serverInstance = new CustomMinYRegistryImpl();
   private static final CustomMinYRegistryImpl clientInstance = new CustomMinYRegistryImpl();
   private final List<LevelMinYCalculator> calculators = new CopyOnWriteArrayList<>();

   public static CustomMinYRegistryImpl getInstance(boolean isClientSide) {
      return isClientSide ? clientInstance : serverInstance;
   }

   @Override
   public void register(LevelMinYCalculator calculator) {
      this.calculators.add(calculator);
   }

   public int getMinYAt(Level level, BlockPos pos) {
      for (LevelMinYCalculator calc : this.calculators) {
         OptionalInt h = calc.getLevelMinY(level, pos);
         if (h.isPresent()) {
            return h.getAsInt();
         }
      }

      return level.getMinBuildHeight();
   }
}
