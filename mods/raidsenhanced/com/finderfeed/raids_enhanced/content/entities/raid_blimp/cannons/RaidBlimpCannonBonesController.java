package com.finderfeed.raids_enhanced.content.entities.raid_blimp.cannons;

import com.finderfeed.fdlib.data_structures.Pair;
import com.finderfeed.fdlib.systems.bedrock.animations.animation_system.BoneTransformationController;
import com.finderfeed.fdlib.systems.bedrock.models.FDModel;
import com.finderfeed.fdlib.systems.bedrock.models.FDModelPart;
import com.finderfeed.raids_enhanced.content.entities.raid_blimp.RaidBlimp;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.MultiBufferSource;
import org.joml.Matrix4f;
import org.joml.Quaternionf;
import org.joml.Vector3f;

public class RaidBlimpCannonBonesController implements BoneTransformationController<RaidBlimp> {
   public void transformBone(
      RaidBlimp raidBlimp, FDModel fdModel, FDModelPart fdModelPart, PoseStack poseStack, MultiBufferSource multiBufferSource, String s, int i, int i1, float v
   ) {
      FDModelPart cannonRight1 = fdModel.getModelPart("cannon_right_1");
      FDModelPart cannonRight2 = fdModel.getModelPart("cannon_right_2");
      FDModelPart cannonRight3 = fdModel.getModelPart("cannon_right_3");
      FDModelPart cannonLeft1 = fdModel.getModelPart("cannon_left_1");
      FDModelPart cannonLeft2 = fdModel.getModelPart("cannon_left_2");
      FDModelPart cannonLeft3 = fdModel.getModelPart("cannon_left_3");
      RaidBlimpCannonsController cannonsController = raidBlimp.cannonsController;
      RaidBlimpCannon cannonr1 = cannonsController.getCannonRight1();
      RaidBlimpCannon cannonr2 = cannonsController.getCannonRight2();
      RaidBlimpCannon cannonr3 = cannonsController.getCannonRight3();
      RaidBlimpCannon cannonl1 = cannonsController.getCannonLeft1();
      RaidBlimpCannon cannonl2 = cannonsController.getCannonLeft2();
      RaidBlimpCannon cannonl3 = cannonsController.getCannonLeft3();
      Pair<Float, Float> yxr1 = cannonr1.getCurrentYXRotation(v);
      Pair<Float, Float> yxr2 = cannonr2.getCurrentYXRotation(v);
      Pair<Float, Float> yxr3 = cannonr3.getCurrentYXRotation(v);
      Pair<Float, Float> yxl1 = cannonl1.getCurrentYXRotation(v);
      Pair<Float, Float> yxl2 = cannonl2.getCurrentYXRotation(v);
      Pair<Float, Float> yxl3 = cannonl3.getCurrentYXRotation(v);
      FDModel model = RaidBlimp.getModel(raidBlimp);
      model.resetTransformations();
      Vector3f yxr1e = this.getCannonEulerAngles(model, "cannon_right_1", (Float)yxr1.second, (Float)yxr1.first);
      Vector3f yxr2e = this.getCannonEulerAngles(model, "cannon_right_2", (Float)yxr2.second, (Float)yxr2.first);
      Vector3f yxr3e = this.getCannonEulerAngles(model, "cannon_right_3", (Float)yxr3.second, (Float)yxr3.first);
      Vector3f yxl1e = this.getCannonEulerAngles(model, "cannon_left_1", (Float)yxl1.second, (Float)yxl1.first);
      Vector3f yxl2e = this.getCannonEulerAngles(model, "cannon_left_2", (Float)yxl2.second, (Float)yxl2.first);
      Vector3f yxl3e = this.getCannonEulerAngles(model, "cannon_left_3", (Float)yxl3.second, (Float)yxl3.first);
      cannonRight1.addXRot((float)Math.toDegrees(yxr1e.x));
      cannonRight1.addYRot((float)Math.toDegrees(yxr1e.y));
      cannonRight1.addZRot((float)Math.toDegrees(yxr1e.z));
      cannonRight2.addXRot((float)Math.toDegrees(yxr2e.x));
      cannonRight2.addYRot((float)Math.toDegrees(yxr2e.y));
      cannonRight2.addZRot((float)Math.toDegrees(yxr2e.z));
      cannonRight3.addXRot((float)Math.toDegrees(yxr3e.x));
      cannonRight3.addYRot((float)Math.toDegrees(yxr3e.y));
      cannonRight3.addZRot((float)Math.toDegrees(yxr3e.z));
      cannonLeft1.addXRot((float)Math.toDegrees(yxl1e.x));
      cannonLeft1.addYRot((float)Math.toDegrees(yxl1e.y));
      cannonLeft1.addZRot((float)Math.toDegrees(yxl1e.z));
      cannonLeft2.addXRot((float)Math.toDegrees(yxl2e.x));
      cannonLeft2.addYRot((float)Math.toDegrees(yxl2e.y));
      cannonLeft2.addZRot((float)Math.toDegrees(yxl2e.z));
      cannonLeft3.addXRot((float)Math.toDegrees(yxl3e.x));
      cannonLeft3.addYRot((float)Math.toDegrees(yxl3e.y));
      cannonLeft3.addZRot((float)Math.toDegrees(yxl3e.z));
   }

   private Vector3f getCannonEulerAngles(FDModel model, String cannonName, float targetX, float targetY) {
      Matrix4f transform = model.getModelPartTransformation(cannonName);
      transform.rotateZ((float)Math.toRadians(targetX));
      transform.rotateY((float)Math.toRadians(targetY));
      Quaternionf rotation = transform.getNormalizedRotation(new Quaternionf());
      return rotation.getEulerAnglesXYZ(new Vector3f());
   }
}
