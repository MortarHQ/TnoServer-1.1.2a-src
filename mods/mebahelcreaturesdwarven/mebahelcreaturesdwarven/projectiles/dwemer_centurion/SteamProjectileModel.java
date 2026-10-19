package mebahelcreaturesdwarven.projectiles.dwemer_centurion;

import mebahelcreaturesdwarven.entity.projectile.SteamProjectileEntity;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.class_2960;
import software.bernie.geckolib.model.GeoModel;

@Environment(EnvType.CLIENT)
public class SteamProjectileModel extends GeoModel<SteamProjectileEntity> {
   public class_2960 getModelResource(SteamProjectileEntity object) {
      return class_2960.method_60655("mebahelcreaturesdwarven", "geo/venom_entity.geo.json");
   }

   public class_2960 getTextureResource(SteamProjectileEntity object) {
      return class_2960.method_60655("mebahelcreaturesdwarven", "textures/entity/mummy_projectile_entity_texture.png");
   }

   public class_2960 getAnimationResource(SteamProjectileEntity animatable) {
      return class_2960.method_60655("mebahelcreaturesdwarven", "animations/venom_entity.animation.json");
   }
}
