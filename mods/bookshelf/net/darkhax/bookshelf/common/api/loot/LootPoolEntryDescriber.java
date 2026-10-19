package net.darkhax.bookshelf.common.api.loot;

import java.util.function.Consumer;
import net.minecraft.core.RegistryAccess;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.storage.loot.entries.LootPoolEntryContainer;
import org.jetbrains.annotations.NotNull;

@FunctionalInterface
public interface LootPoolEntryDescriber {
   void getPotentialDrops(@NotNull RegistryAccess var1, @NotNull LootPoolEntryContainer var2, Consumer<ItemStack> var3);
}
