package com.natamus.collective.fabric.services;

import com.natamus.collective.fabric.callbacks.CollectiveBlockEvents;
import com.natamus.collective_common_fabric.services.helpers.EventTriggerHelper;
import net.minecraft.class_1937;
import net.minecraft.class_2338;
import net.minecraft.class_2424;

public class FabricEventTriggerHelper implements EventTriggerHelper {
   @Override
   public void triggerNetherPortalSpawnEvent(class_1937 level, class_2338 portalPos, class_2424 size) {
      ((CollectiveBlockEvents.Possible_Portal_Spawn)CollectiveBlockEvents.ON_NETHER_PORTAL_SPAWN.invoker()).onPossiblePortal(level, portalPos, size);
   }
}
