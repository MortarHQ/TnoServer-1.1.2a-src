package net.blay09.mods.waystones.worldgen.namegen;

import java.util.Optional;
import net.blay09.mods.waystones.api.Waystone;
import net.minecraft.network.chat.Component;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.LevelAccessor;

public interface NameGenerator {
   Optional<Component> generateName(LevelAccessor var1, Waystone var2, RandomSource var3);
}
