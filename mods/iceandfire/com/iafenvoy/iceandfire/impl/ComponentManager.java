package com.iafenvoy.iceandfire.impl;

import com.iafenvoy.iceandfire.data.component.ChainData;
import com.iafenvoy.iceandfire.data.component.ChickenData;
import com.iafenvoy.iceandfire.data.component.MiscData;
import com.iafenvoy.iceandfire.data.component.PortalData;
import com.iafenvoy.iceandfire.impl.neoforge.ComponentManagerImpl;
import dev.architectury.injectables.annotations.ExpectPlatform;
import dev.architectury.injectables.annotations.ExpectPlatform.Transformed;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;

public class ComponentManager {
   @ExpectPlatform
   @Transformed
   public static ChainData getChainData(LivingEntity living) {
      return ComponentManagerImpl.getChainData(living);
   }

   @ExpectPlatform
   @Transformed
   public static ChickenData getChickenData(LivingEntity living) {
      return ComponentManagerImpl.getChickenData(living);
   }

   @ExpectPlatform
   @Transformed
   public static MiscData getMiscData(LivingEntity living) {
      return ComponentManagerImpl.getMiscData(living);
   }

   @ExpectPlatform
   @Transformed
   public static PortalData getPortalData(Player player) {
      return ComponentManagerImpl.getPortalData(player);
   }
}
