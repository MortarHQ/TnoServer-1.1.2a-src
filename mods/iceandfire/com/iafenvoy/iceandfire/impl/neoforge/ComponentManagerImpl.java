package com.iafenvoy.iceandfire.impl.neoforge;

import com.iafenvoy.iceandfire.data.component.ChainData;
import com.iafenvoy.iceandfire.data.component.ChickenData;
import com.iafenvoy.iceandfire.data.component.MiscData;
import com.iafenvoy.iceandfire.data.component.PortalData;
import com.iafenvoy.iceandfire.neoforge.IafAttachments;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.neoforged.neoforge.attachment.AttachmentType;

public class ComponentManagerImpl {
   public static ChainData getChainData(LivingEntity living) {
      return (ChainData)living.getData((AttachmentType)IafAttachments.CHAIN_DATA.get());
   }

   public static ChickenData getChickenData(LivingEntity living) {
      return (ChickenData)living.getData((AttachmentType)IafAttachments.CHICKEN_DATA.get());
   }

   public static MiscData getMiscData(LivingEntity living) {
      return (MiscData)living.getData((AttachmentType)IafAttachments.MISC_DATA.get());
   }

   public static PortalData getPortalData(Player player) {
      return (PortalData)player.getData((AttachmentType)IafAttachments.PORTAL_DATA.get());
   }
}
