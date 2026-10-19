package net.unusual.block_factorys_bosses.geckolib.boss.dragon.guardians;

import net.minecraft.client.renderer.entity.EntityRendererProvider.Context;
import net.minecraft.resources.ResourceLocation;
import net.unusual.block_factorys_bosses.entity.boss.dragon.guardians.FlamingSkeletonGuardSwordEntity;
import net.unusual.block_factorys_bosses.geckolib.EmissiveRenderLayer;
import net.unusual.block_factorys_bosses.geckolib.util.CustomEntityGeoModel;
import software.bernie.geckolib.renderer.GeoEntityRenderer;

public class FlamingSkeletonGuardSwordRenderer extends GeoEntityRenderer<FlamingSkeletonGuardSwordEntity> {
   private static final ResourceLocation BASE_TEXTURE = ResourceLocation.fromNamespaceAndPath("block_factorys_bosses", "flaming_skeleton_guard_sword");

   public FlamingSkeletonGuardSwordRenderer(Context context) {
      super(context, new CustomEntityGeoModel(BASE_TEXTURE));
      this.addRenderLayer(EmissiveRenderLayer.fromBaseTexture(this, BASE_TEXTURE));
   }
}
