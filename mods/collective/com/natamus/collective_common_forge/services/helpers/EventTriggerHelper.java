package com.natamus.collective_common_forge.services.helpers;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.portal.PortalShape;

public interface EventTriggerHelper {
   void triggerNetherPortalSpawnEvent(Level var1, BlockPos var2, PortalShape var3);
}
