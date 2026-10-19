package net.darkhax.bookshelf.common.api.data.loot.modifiers;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.storage.loot.entries.LootPoolEntryContainer;

public record LootPoolAddition(ResourceLocation id, LootPoolEntryContainer entry) {
}
