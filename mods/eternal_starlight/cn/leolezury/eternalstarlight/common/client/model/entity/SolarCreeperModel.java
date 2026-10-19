package cn.leolezury.eternalstarlight.common.client.model.entity;

import cn.leolezury.eternalstarlight.common.EternalStarlight;
import cn.leolezury.eternalstarlight.common.client.model.ESModelUtil;
import cn.leolezury.eternalstarlight.common.client.model.animation.AnimatedEntityModel;
import cn.leolezury.eternalstarlight.common.entity.living.boss.creeper.SolarCreeper;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import java.util.List;
import java.util.stream.Stream;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeDeformation;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;
import net.minecraft.util.FastColor.ARGB32;

public class SolarCreeperModel<T extends SolarCreeper> extends AnimatedEntityModel<T> {
   public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(EternalStarlight.id("solar_creeper"), "main");
   private final ModelPart root;
   private final ModelPart head;
   public final List<String> allPartNames;
   public float alphaFactor = 1.0F;

   public SolarCreeperModel(ModelPart root) {
      this.root = root.getChild("root");
      this.head = this.root.getChild("head");
      this.allPartNames = Stream.concat(Stream.of("root"), ESModelUtil.getAllPartNames(this.root)).toList();
   }

   public static LayerDefinition createBodyLayer() {
      MeshDefinition meshdefinition = new MeshDefinition();
      PartDefinition partdefinition = meshdefinition.getRoot();
      PartDefinition root = partdefinition.addOrReplaceChild(
         "root",
         CubeListBuilder.create().texOffs(0, 44).addBox(-8.0F, -22.0F, -8.0F, 16.0F, 8.0F, 16.0F, new CubeDeformation(0.0F)),
         PartPose.offset(0.0F, 24.0F, 0.0F)
      );
      root.addOrReplaceChild(
         "head",
         CubeListBuilder.create().texOffs(0, 0).addBox(-14.0F, -22.0F, -11.0F, 28.0F, 22.0F, 22.0F, new CubeDeformation(0.0F)),
         PartPose.offset(0.0F, -22.0F, 0.0F)
      );
      root.addOrReplaceChild(
         "leg1",
         CubeListBuilder.create().texOffs(0, 68).addBox(-5.0F, -1.0F, -5.0F, 10.0F, 14.0F, 10.0F, new CubeDeformation(0.0F)),
         PartPose.offset(-5.0F, -13.0F, -7.0F)
      );
      root.addOrReplaceChild(
         "leg2",
         CubeListBuilder.create().texOffs(0, 68).mirror().addBox(-5.0F, -1.0F, -5.0F, 10.0F, 14.0F, 10.0F, new CubeDeformation(0.0F)).mirror(false),
         PartPose.offset(5.0F, -13.0F, -7.0F)
      );
      root.addOrReplaceChild(
         "leg3",
         CubeListBuilder.create().texOffs(0, 68).addBox(-5.0F, -1.0F, -5.0F, 10.0F, 14.0F, 10.0F, new CubeDeformation(0.0F)),
         PartPose.offset(-5.0F, -13.0F, 7.0F)
      );
      root.addOrReplaceChild(
         "leg4",
         CubeListBuilder.create().texOffs(0, 68).mirror().addBox(-5.0F, -1.0F, -5.0F, 10.0F, 14.0F, 10.0F, new CubeDeformation(0.0F)).mirror(false),
         PartPose.offset(5.0F, -13.0F, 7.0F)
      );
      return LayerDefinition.create(meshdefinition, 128, 128);
   }

   public void setupAnim(T entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
      this.root().getAllParts().forEach(ModelPart::resetPose);
      this.head.yRot = netHeadYaw * (float) (Math.PI / 180.0);
      this.head.xRot = headPitch * (float) (Math.PI / 180.0);
   }

   @Override
   public ModelPart root() {
      return this.root;
   }

   @Override
   public void renderToBuffer(PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight, int packedOverlay, int color) {
      this.root()
         .render(
            poseStack,
            vertexConsumer,
            packedLight,
            packedOverlay,
            ARGB32.color(Math.round(ARGB32.alpha(color) * this.alphaFactor), ARGB32.red(color), ARGB32.green(color), ARGB32.blue(color))
         );
   }
}
