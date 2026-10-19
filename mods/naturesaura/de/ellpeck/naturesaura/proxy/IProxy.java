package de.ellpeck.naturesaura.proxy;

import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;

public interface IProxy {
   void preInit(FMLCommonSetupEvent var1);

   void init(FMLCommonSetupEvent var1);

   void postInit(FMLCommonSetupEvent var1);

   void spawnMagicParticle(
      double var1,
      double var3,
      double var5,
      double var7,
      double var9,
      double var11,
      int var13,
      float var14,
      int var15,
      float var16,
      boolean var17,
      boolean var18
   );

   void setParticleDepth(boolean var1);

   void setParticleSpawnRange(int var1);

   void setParticleCulling(boolean var1);
}
