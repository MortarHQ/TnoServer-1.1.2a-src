package com.tensura_tno.client.race;

import net.minecraft.client.renderer.RenderType;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Player;
import software.bernie.geckolib.animation.AnimationState;
import software.bernie.geckolib.cache.object.GeoBone;
import software.bernie.geckolib.model.GeoModel;

public final class PlayerOrcGeoModel extends GeoModel<PlayerOrcAnimatable> {
   private static final float DEG_TO_RAD = (float) (Math.PI / 180.0);
   private static final ResourceLocation GEO = ResourceLocation.fromNamespaceAndPath("tensura", "geo/entity/orc.geo.json");
   private static final ResourceLocation ANIM = ResourceLocation.fromNamespaceAndPath("tensura_tno", "animations/entity/player_orc.animation.json");
   private static final ResourceLocation TEXTURE = ResourceLocation.fromNamespaceAndPath("tensura", "textures/entity/orc/orc_ham.png");
   private static final String[] HIDDEN_BONES = new String[]{
      "HeadArmor", "ChestArmor", "RightArmArmor", "LeftArmArmor", "RightLegArmor", "LeftLegArmor", "RightBootArmor", "LeftBootArmor", "ArmorBelly"
   };

   public ResourceLocation getModelResource(PlayerOrcAnimatable animatable) {
      return GEO;
   }

   public ResourceLocation getTextureResource(PlayerOrcAnimatable animatable) {
      return TEXTURE;
   }

   public ResourceLocation getAnimationResource(PlayerOrcAnimatable animatable) {
      return ANIM;
   }

   public RenderType getRenderType(PlayerOrcAnimatable animatable, ResourceLocation texture) {
      return RenderType.entityCutoutNoCull(texture, false);
   }

   public void setCustomAnimations(PlayerOrcAnimatable animatable, long instanceId, AnimationState<PlayerOrcAnimatable> animationState) {
      super.setCustomAnimations(animatable, instanceId, animationState);
      this.hideEntityOnlyBones(animatable.getPlayer());
      this.rotateHead(animatable.getPlayer());
   }

   private void hideEntityOnlyBones(Player player) {
      for (String name : HIDDEN_BONES) {
         GeoBone bone = this.getAnimationProcessor().getBone(name);
         if (bone != null && !bone.isHidden()) {
            bone.setHidden(true);
         }
      }

      boolean hasChestplate = PlayerRaceArmorRenderCompatibility.hasRenderableArmor(player, EquipmentSlot.CHEST);
      GeoBone belly = this.getAnimationProcessor().getBone("Belly");
      if (belly != null) {
         belly.setHidden(hasChestplate);
      }
   }

   private void rotateHead(Player player) {
      if (player != null) {
         GeoBone head = this.getAnimationProcessor().getBone("head");
         if (head != null) {
            float netHeadYaw = Mth.wrapDegrees(player.getYHeadRot() - player.yBodyRot);
            head.setRotY(-Mth.clamp(netHeadYaw, -75.0F, 75.0F) * (float) (Math.PI / 180.0));
            head.setRotX(-player.getXRot() * (float) (Math.PI / 180.0));
         }
      }
   }
}
