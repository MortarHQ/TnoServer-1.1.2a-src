package com.cerbon.bosses_of_mass_destruction.entity.custom.obsidilith;

import com.cerbon.bosses_of_mass_destruction.client.render.IBoneLight;
import com.cerbon.bosses_of_mass_destruction.client.render.IRenderer;
import com.cerbon.cerbons_api.api.static_utilities.Vec3Colors;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.world.phys.Vec3;
import org.joml.Vector4f;
import software.bernie.geckolib.cache.object.GeoBone;

public class ObsidilithBoneLight implements IBoneLight, IRenderer<ObsidilithEntity> {
   private ObsidilithEntity entity;
   private final Vector4f defaultBoneColor = new Vector4f(0.5F, 0.5F, 0.5F, 1.0F);

   @Override
   public int getLightForBone(GeoBone bone, int packedLight) {
      return bone.getName().equals("middle_runes") && this.entity != null && this.entity.currentAttack > 0 ? 15728880 : packedLight;
   }

   @Override
   public Vector4f getColorForBone(GeoBone bone, Vector4f rgbaColor) {
      if (bone.getName().equals("middle_runes")) {
         return switch (this.entity.currentAttack) {
            case 5 -> this.colorToVec4(Vec3Colors.ORANGE);
            case 6 -> this.colorToVec4(Vec3Colors.RED);
            case 7 -> this.colorToVec4(Vec3Colors.COMET_BLUE);
            case 8 -> this.colorToVec4(Vec3Colors.ENDER_PURPLE);
            case 9 -> this.colorToVec4(Vec3Colors.WHITE);
            default -> this.defaultBoneColor;
         };
      } else {
         return IBoneLight.super.getColorForBone(bone, rgbaColor);
      }
   }

   private Vector4f colorToVec4(Vec3 color) {
      return new Vector4f((float)color.x, (float)color.y, (float)color.z, 1.0F);
   }

   public void render(ObsidilithEntity entity, float yaw, float partialTicks, PoseStack poseStack, MultiBufferSource multiBufferSource, int light) {
      this.entity = entity;
   }
}
