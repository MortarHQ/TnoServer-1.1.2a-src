package net.darkhax.bookshelf.common.api.registry.register;

import java.util.function.BiConsumer;
import net.darkhax.bookshelf.common.api.loot.LootPoolEntryDescriber;
import net.minecraft.world.level.storage.loot.entries.LootPoolEntryType;

public record RegisterLootDescription(BiConsumer<LootPoolEntryType, LootPoolEntryDescriber> registryFunc) {
}
