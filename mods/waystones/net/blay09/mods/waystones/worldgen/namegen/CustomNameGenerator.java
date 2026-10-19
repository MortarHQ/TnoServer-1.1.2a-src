package net.blay09.mods.waystones.worldgen.namegen;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Optional;
import java.util.Set;
import net.blay09.mods.waystones.api.Waystone;
import net.blay09.mods.waystones.config.WaystonesConfig;
import net.minecraft.network.chat.Component;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.LevelAccessor;

public class CustomNameGenerator implements NameGenerator {
   private final boolean allowDuplicates;
   private final Set<String> usedNames;

   public CustomNameGenerator(boolean allowDuplicates, Set<String> usedNames) {
      this.allowDuplicates = allowDuplicates;
      this.usedNames = usedNames;
   }

   @Override
   public Optional<Component> generateName(LevelAccessor level, Waystone waystone, RandomSource rand) {
      ArrayList<String> customNames = new ArrayList<>(WaystonesConfig.getActive().worldGen.nameGenerationPresets);
      Collections.shuffle(customNames);

      for (String customName : customNames) {
         if (this.allowDuplicates || !this.usedNames.contains(customName)) {
            return Optional.of(Component.literal(customName));
         }
      }

      return Optional.empty();
   }
}
