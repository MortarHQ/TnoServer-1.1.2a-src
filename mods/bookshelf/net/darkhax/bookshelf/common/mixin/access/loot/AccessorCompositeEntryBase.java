package net.darkhax.bookshelf.common.mixin.access.loot;

import java.util.List;
import net.minecraft.world.level.storage.loot.entries.CompositeEntryBase;
import net.minecraft.world.level.storage.loot.entries.LootPoolEntryContainer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(CompositeEntryBase.class)
public interface AccessorCompositeEntryBase {
   @Accessor("children")
   List<LootPoolEntryContainer> bookshelf$children();
}
