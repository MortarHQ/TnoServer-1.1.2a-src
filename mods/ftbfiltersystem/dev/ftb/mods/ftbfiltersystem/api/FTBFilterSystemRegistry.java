package dev.ftb.mods.ftbfiltersystem.api;

import dev.ftb.mods.ftbfiltersystem.api.filter.SmartFilter;
import java.util.Collection;
import net.minecraft.resources.ResourceLocation;

public interface FTBFilterSystemRegistry {
   <T extends SmartFilter> void register(ResourceLocation var1, SmartFilter.Factory<T> var2, SmartFilter.DefaultFactory<T> var3);

   Collection<ResourceLocation> allFilterKeys();

   Collection<SmartFilter> defaultFilterInstances();
}
