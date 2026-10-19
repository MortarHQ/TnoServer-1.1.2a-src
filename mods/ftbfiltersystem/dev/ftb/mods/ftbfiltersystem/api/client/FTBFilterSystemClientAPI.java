package dev.ftb.mods.ftbfiltersystem.api.client;

import dev.ftb.mods.ftbfiltersystem.api.filter.SmartFilter;
import net.minecraft.resources.ResourceLocation;

public interface FTBFilterSystemClientAPI {
   <T extends SmartFilter> void registerFilterScreenFactory(ResourceLocation var1, FilterScreenFactory<T> var2);
}
