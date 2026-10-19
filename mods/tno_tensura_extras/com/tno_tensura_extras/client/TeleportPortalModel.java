package com.tno_tensura_extras.client;

import com.tno_tensura_extras.entity.TeleportPortalEntity;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.model.DefaultedEntityGeoModel;

public class TeleportPortalModel extends DefaultedEntityGeoModel<TeleportPortalEntity> {
   public TeleportPortalModel() {
      super(ResourceLocation.fromNamespaceAndPath("tensura", "warp_portal"), false);
   }

   protected String subtype() {
      return "entity/misc";
   }
}
