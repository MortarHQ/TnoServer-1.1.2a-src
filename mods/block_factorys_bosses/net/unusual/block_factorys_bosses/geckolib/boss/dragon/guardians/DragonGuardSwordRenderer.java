package net.unusual.block_factorys_bosses.geckolib.boss.dragon.guardians;

import net.minecraft.client.renderer.entity.EntityRendererProvider.Context;
import net.minecraft.resources.ResourceLocation;
import net.unusual.block_factorys_bosses.entity.boss.dragon.guardians.DragonGuardSwordEntity;
import net.unusual.block_factorys_bosses.geckolib.util.CustomEntityGeoModel;
import software.bernie.geckolib.renderer.GeoEntityRenderer;

public class DragonGuardSwordRenderer extends GeoEntityRenderer<DragonGuardSwordEntity> {
   public DragonGuardSwordRenderer(Context context) {
      super(context, new CustomEntityGeoModel(ResourceLocation.fromNamespaceAndPath("block_factorys_bosses", "dragon_guard_sword")));
   }
}
