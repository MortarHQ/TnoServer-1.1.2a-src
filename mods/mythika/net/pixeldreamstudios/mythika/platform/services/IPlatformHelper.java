package net.pixeldreamstudios.mythika.platform.services;

import net.minecraft.world.entity.Entity;

public interface IPlatformHelper {
   boolean canGrief(Entity var1);

   String getPlatformName();

   boolean isModLoaded(String var1);

   boolean isDevelopmentEnvironment();
}
