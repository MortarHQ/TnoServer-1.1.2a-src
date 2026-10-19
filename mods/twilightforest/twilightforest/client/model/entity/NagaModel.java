package twilightforest.client.model.entity;

import com.google.common.collect.ImmutableList;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.model.ListModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.util.FastColor.ARGB32;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.ItemDisplayContext;
import org.jetbrains.annotations.Nullable;
import twilightforest.client.JappaPackReloadListener;
import twilightforest.client.renderer.entity.NagaRenderer;
import twilightforest.entity.boss.Naga;

public class NagaModel<T extends Entity> extends ListModel<T> implements TrophyBlockModel {
   private final ModelPart head;
   @Nullable
   private T entity;

   public NagaModel(ModelPart root) {
      this.head = root.getChild("head");
   }

   public static LayerDefinition checkForPack() {
      return JappaPackReloadListener.INSTANCE.isJappaPackLoaded() ? createJappaModel() : create();
   }

   private static LayerDefinition create() {
      MeshDefinition meshdefinition = new MeshDefinition();
      PartDefinition partdefinition = meshdefinition.getRoot();
      partdefinition.addOrReplaceChild("head", CubeListBuilder.create().texOffs(0, 0).addBox(-8.0F, -12.0F, -8.0F, 16.0F, 16.0F, 16.0F), PartPose.ZERO);
      return LayerDefinition.create(meshdefinition, 64, 32);
   }

   private static LayerDefinition createJappaModel() {
      MeshDefinition meshdefinition = new MeshDefinition();
      PartDefinition partdefinition = meshdefinition.getRoot();
      PartDefinition head = partdefinition.addOrReplaceChild(
         "head", CubeListBuilder.create().texOffs(0, 0).addBox(-16.0F, -16.0F, -16.0F, 32.0F, 32.0F, 32.0F), PartPose.offset(0.0F, 8.0F, 0.0F)
      );
      head.addOrReplaceChild(
         "tongue",
         CubeListBuilder.create().texOffs(84, 0).addBox(-6.0F, 0.0F, -12.0F, 12.0F, 0.0F, 12.0F),
         PartPose.offsetAndRotation(0.0F, 10.0F, -16.0F, 0.43633232F, 0.0F, 0.0F)
      );
      return LayerDefinition.create(meshdefinition, 128, 64);
   }

   public Iterable<ModelPart> parts() {
      return ImmutableList.of(this.head);
   }

   public void renderToBuffer(PoseStack stack, VertexConsumer builder, int light, int overlay, int color) {
      if (this.entity instanceof Naga naga) {
         this.head
            .render(
               stack,
               builder,
               light,
               overlay,
               ARGB32.color(
                  ARGB32.alpha(color),
                  ARGB32.red(color),
                  (int)(ARGB32.green(color) - naga.stunlessRedOverlayProgress),
                  (int)(ARGB32.blue(color) - naga.stunlessRedOverlayProgress)
               )
            );
      } else {
         this.head.render(stack, builder, light, overlay, color);
      }

      this.entity = null;
   }

   public void setupAnim(T entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
      this.entity = entity;
   }

   @Override
   public void renderTrophy(PoseStack stack, MultiBufferSource buffer, int light, int overlay, int color, ItemDisplayContext context) {
      if (JappaPackReloadListener.INSTANCE.isJappaPackLoaded()) {
         stack.scale(0.25F, 0.25F, 0.25F);
         stack.translate(0.0F, -1.5F, 0.0F);
      } else {
         stack.scale(0.5F, 0.5F, 0.5F);
         stack.translate(0.0F, -0.25F, 0.0F);
      }

      VertexConsumer consumer = buffer.getBuffer(RenderType.entityCutoutNoCull(NagaRenderer.TEXTURE));
      this.head.render(stack, consumer, light, overlay, color);
   }

   @Override
   public void setupRotationsForTrophy(float x, float y, float z, float mouthAngle) {
      this.head.yRot = y * (float) (Math.PI / 180.0);
      this.head.xRot = z * (float) (Math.PI / 180.0);
   }
}
