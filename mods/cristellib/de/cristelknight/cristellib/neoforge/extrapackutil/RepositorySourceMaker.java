package de.cristelknight.cristellib.neoforge.extrapackutil;

import de.cristelknight.cristellib.builtinpacks.BuiltInDataPackLoader;
import java.util.function.Consumer;
import net.minecraft.server.packs.repository.Pack;
import net.minecraft.server.packs.repository.RepositorySource;
import org.jetbrains.annotations.NotNull;

public class RepositorySourceMaker implements RepositorySource {
   public void loadPacks(@NotNull Consumer<Pack> consumer) {
      BuiltInDataPackLoader.getPacks(consumer);
   }
}
