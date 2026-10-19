package com.mafuyu404.oneenoughitem.api;

import java.util.Collection;
import net.minecraft.resources.ResourceLocation;

public interface DomainRuntimeCache {
   String matchData(String var1);

   String matchTag(ResourceLocation var1);

   void removeReplacements(Collection<String> var1, Collection<String> var2);

   boolean isTagReplaced(String var1);

   boolean isTagReplaced(ResourceLocation var1);
}
