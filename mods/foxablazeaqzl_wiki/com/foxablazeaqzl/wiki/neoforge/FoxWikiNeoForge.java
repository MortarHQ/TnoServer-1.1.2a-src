package com.foxablazeaqzl.wiki.neoforge;

import com.foxablazeaqzl.wiki.neoforge.client.FoxWikiNeoForgeClient;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.loading.FMLEnvironment;

@Mod("foxablazeaqzl_wiki")
public final class FoxWikiNeoForge {
   public FoxWikiNeoForge() {
      if (FMLEnvironment.dist == Dist.CLIENT) {
         FoxWikiNeoForgeClient.register();
      }
   }
}
