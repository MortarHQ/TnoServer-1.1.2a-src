package artifacts.client.item.model;

import com.google.common.collect.ImmutableList;
import java.util.function.Function;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeDeformation;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.LivingEntity;

public class ScarfModel extends HumanoidModel<LivingEntity> {
   private final ModelPart cloak = this.body.getChild("cloak");

   public ScarfModel(ModelPart part, Function<ResourceLocation, RenderType> renderType) {
      super(part, renderType);
   }

   protected Iterable<ModelPart> headParts() {
      return ImmutableList.of(this.head);
   }

   protected Iterable<ModelPart> bodyParts() {
      return ImmutableList.of(this.body);
   }

   public void prepareMobModel(LivingEntity entity, float limbSwing, float limbSwingAmount, float partialTicks) {
      if (entity instanceof AbstractClientPlayer player) {
         double x = Mth.lerp(partialTicks, player.xCloakO, player.xCloak) - Mth.lerp(partialTicks, player.xo, player.getX());
         double y = Mth.lerp(partialTicks, player.yCloakO, player.yCloak) - Mth.lerp(partialTicks, player.yo, player.getY());
         double z = Mth.lerp(partialTicks, player.zCloakO, player.zCloak) - Mth.lerp(partialTicks, player.zo, player.getZ());
         float f = player.yBodyRotO + (player.yBodyRot - player.yBodyRotO);
         double d3 = Mth.sin(f * (float) (Math.PI / 180.0));
         double d4 = -Mth.cos(f * (float) (Math.PI / 180.0));
         float f1 = (float)y * 10.0F;
         f1 = Mth.clamp(f1, -6.0F, 32.0F);
         float f2 = (float)(x * d3 + z * d4) * 100.0F;
         f2 = Mth.clamp(f2, 0.0F, 150.0F);
         if (f2 < 0.0F) {
            f2 = 0.0F;
         }

         float f4 = Mth.lerp(partialTicks, player.oBob, player.bob);
         f1 += Mth.sin(Mth.lerp(partialTicks, player.walkDistO, player.walkDist) * 6.0F) * 32.0F * f4;
         this.cloak.xRot = this.body.xRot + (6.0F + f2 / 2.0F + f1) / 180.0F * (float) Math.PI;
      }
   }

   public static MeshDefinition createScarf() {
      MeshDefinition mesh = createMesh(new CubeDeformation(0.51F), 0.0F);
      mesh.getRoot().addOrReplaceChild("body", CubeListBuilder.create().texOffs(0, 16).addBox(-6.01F, -2.0F, -4.0F, 12.0F, 6.0F, 8.0F), PartPose.ZERO);
      mesh.getRoot()
         .getChild("body")
         .addOrReplaceChild("cloak", CubeListBuilder.create().texOffs(32, 0).addBox(-5.0F, 0.0F, 0.0F, 5.0F, 12.0F, 2.0F), PartPose.offset(0.0F, 0.0F, 1.99F));
      return mesh;
   }
}
