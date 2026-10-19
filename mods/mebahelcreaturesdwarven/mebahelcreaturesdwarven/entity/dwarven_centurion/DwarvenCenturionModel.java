package mebahelcreaturesdwarven.entity.dwarven_centurion;

import mebahelcreaturesdwarven.entity.custom.DwemerCenturionEntity;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.class_2960;
import software.bernie.geckolib.animation.AnimationState;
import software.bernie.geckolib.cache.object.GeoBone;
import software.bernie.geckolib.constant.DataTickets;
import software.bernie.geckolib.model.GeoModel;
import software.bernie.geckolib.model.data.EntityModelData;

@Environment(EnvType.CLIENT)
public class DwarvenCenturionModel extends GeoModel<DwemerCenturionEntity> {
   private static final class_2960 TEXTURE = class_2960.method_60655("mebahelcreaturesdwarven", "textures/entity/dwarven_centurion/dwarven_centurion_new.png");

   public class_2960 getModelResource(DwemerCenturionEntity object) {
      return class_2960.method_60655("mebahelcreaturesdwarven", "geo/dwarven_centurion.geo.json");
   }

   public class_2960 getTextureResource(DwemerCenturionEntity animatable) {
      return TEXTURE;
   }

   public class_2960 getAnimationResource(DwemerCenturionEntity animatable) {
      return class_2960.method_60655("mebahelcreaturesdwarven", "animations/dwarven_centurion.animation.json");
   }

   public void setCustomAnimations(DwemerCenturionEntity entity, long uniqueID, AnimationState<DwemerCenturionEntity> customPredicate) {
      GeoBone head = (GeoBone)this.getBone("head").orElse(null);
      if (head != null) {
         EntityModelData extraData = (EntityModelData)customPredicate.getData(DataTickets.ENTITY_MODEL_DATA);
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
