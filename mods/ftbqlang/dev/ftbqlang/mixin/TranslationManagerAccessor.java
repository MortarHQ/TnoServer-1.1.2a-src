package dev.ftbqlang.mixin;

import dev.ftb.mods.ftbquests.quest.translation.TranslationManager;
import dev.ftb.mods.ftbquests.quest.translation.TranslationTable;
import java.util.Map;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(value = TranslationManager.class, remap = false)
public interface TranslationManagerAccessor {
   @Accessor("map")
   Map<String, TranslationTable> ftbqlang$getTables();
}
