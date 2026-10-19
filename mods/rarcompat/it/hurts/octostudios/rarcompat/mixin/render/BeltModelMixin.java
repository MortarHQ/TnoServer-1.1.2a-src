package it.hurts.octostudios.rarcompat.mixin.render;

import artifacts.client.item.model.BeltModel;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeDeformation;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(BeltModel.class)
public class BeltModelMixin {
   @Inject(method = "createBelt ", at = @At("HEAD"), cancellable = true)
   private static void createBelt(CubeListBuilder charm, CallbackInfoReturnable<MeshDefinition> cir) {
      MeshDefinition mesh = HumanoidModel.createMesh(CubeDeformation.NONE, 0.0F);
      mesh.getRoot().addOrReplaceChild("body", CubeListBuilder.create().texOffs(0, 0), PartPose.ZERO);
      mesh.getRoot().getChild("body").addOrReplaceChild("charm", charm, PartPose.ZERO);
      cir.setReturnValue(mesh);
   }
}
