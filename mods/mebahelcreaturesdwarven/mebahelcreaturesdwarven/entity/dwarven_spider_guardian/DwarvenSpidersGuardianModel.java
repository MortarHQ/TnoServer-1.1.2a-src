package mebahelcreaturesdwarven.entity.dwarven_spider_guardian;

import mebahelcreaturesdwarven.entity.custom.DwemerSpiderGuardianEntity;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.class_2960;
import software.bernie.geckolib.animation.AnimationState;
import software.bernie.geckolib.cache.object.GeoBone;
import software.bernie.geckolib.constant.DataTickets;
import software.bernie.geckolib.model.GeoModel;
import software.bernie.geckolib.model.data.EntityModelData;

@Environment(EnvType.CLIENT)
public class DwarvenSpidersGuardianModel extends GeoModel<DwemerSpiderGuardianEntity> {
   private static final class_2960 TEXTURE = class_2960.method_60655(
      "mebahelcreaturesdwarven", "textures/entity/dwarven_spider/dwarven_spider_guardian_texture.png"
   );

   public class_2960 getModelResource(DwemerSpiderGuardianEntity object) {
      return class_2960.method_60655("mebahelcreaturesdwarven", "geo/dwarven_spider_guardian.geo.json");
   }

   public class_2960 getTextureResource(DwemerSpiderGuardianEntity animatable) {
      return TEXTURE;
   }

   public class_2960 getAnimationResource(DwemerSpiderGuardianEntity animatable) {
      return class_2960.method_60655("mebahelcreaturesdwarven", "animations/dwarven_spider_guardian.animation.json");
   }

   public void setCustomAnimations(DwemerSpiderGuardianEntity entity, long uniqueID, AnimationState<DwemerSpiderGuardianEntity> state) {
      GeoBone head = (GeoBone)this.getBone("head").orElse(null);
      EntityModelData extraData = (EntityModelData)state.getData(DataTickets.ENTITY_MODEL_DATA);
      if (head != null && extraData != null) {
         float yawAngle = 0.125F * extraData.netHeadYaw() * (float) (Math.PI / 180.0);
         float pitchAngle = 0.125F * extraData.headPitch() * (float) (Math.PI / 180.0);
         if (Math.abs(yawAngle) > 0.4F) {
            yawAngle = 0.0F;
         }

         head.setRotY(7.0F * yawAngle);
         head.setRotX(7.0F * pitchAngle);
      }
   }
}
