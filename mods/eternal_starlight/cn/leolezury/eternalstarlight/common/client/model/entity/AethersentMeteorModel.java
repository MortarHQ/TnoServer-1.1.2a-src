package cn.leolezury.eternalstarlight.common.client.model.entity;

import cn.leolezury.eternalstarlight.common.EternalStarlight;
import cn.leolezury.eternalstarlight.common.client.model.ESModelUtil;
import cn.leolezury.eternalstarlight.common.entity.projectile.AethersentMeteor;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeDeformation;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;
import net.minecraft.util.Mth;
import net.minecraft.util.FastColor.ARGB32;

public class AethersentMeteorModel<T extends AethersentMeteor> extends EntityModel<T> {
   public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(EternalStarlight.id("aethersent_meteor"), "main");
   public final ModelPart root;
   public final ModelPart rotator;
   public final List<String> allPartNames;
   public float alphaFactor = 1.0F;

   public AethersentMeteorModel(ModelPart root) {
      this.root = root.getChild("root");
      this.rotator = this.root.getChild("rotator");
      this.allPartNames = Stream.concat(Stream.of("root"), ESModelUtil.getAllPartNames(this.root)).toList();
   }

   public static LayerDefinition createBodyLayer() {
      MeshDefinition meshdefinition = new MeshDefinition();
      PartDefinition partdefinition = meshdefinition.getRoot();
      PartDefinition root = partdefinition.addOrReplaceChild("root", CubeListBuilder.create(), PartPose.offset(0.0F, 24.0F, 0.0F));
      root.addOrReplaceChild(
         "rotator",
         CubeListBuilder.create().texOffs(0, 0).addBox(-8.0F, -8.0F, -8.0F, 16.0F, 16.0F, 16.0F, new CubeDeformation(0.0F)),
         PartPose.offset(0.0F, -8.0F, 0.0F)
      );
      return LayerDefinition.create(meshdefinition, 64, 64);
   }

   public void setupAnim(T entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
      this.rotator.xRot = Mth.lerp(Mth.frac(ageInTicks), entity.oXSpin, entity.xSpin);
      this.rotator.yRot = Mth.lerp(Mth.frac(ageInTicks), entity.oYSpin, entity.ySpin);
      this.root.xScale = this.root.yScale = this.root.zScale = entity.getSize() / 10.0F;
   }

   public Optional<ModelPart> getAnyDescendantWithName(String name) {
      return name.equals("root")
         ? Optional.of(this.root)
         : this.root.getAllParts().filter(part -> part.hasChild(name)).findFirst().map(part -> part.getChild(name));
   }

   public void renderToBuffer(PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight, int packedOverlay, int color) {
      this.root
         .render(
            poseStack,
            vertexConsumer,
            packedLight,
            packedOverlay,
            ARGB32.color(Math.round(ARGB32.alpha(color) * this.alphaFactor), ARGB32.red(color), ARGB32.green(color), ARGB32.blue(color))
         );
   }
}
