package com.tno_tensura_extras.client;

import com.tno_tensura_extras.TensuraExtras;
import com.tno_tensura_extras.item.MagicCameraItem;
import software.bernie.geckolib.model.DefaultedItemGeoModel;

public class MagicCameraModel extends DefaultedItemGeoModel<MagicCameraItem> {
   public MagicCameraModel() {
      super(TensuraExtras.id("magic_camera"));
   }
}
