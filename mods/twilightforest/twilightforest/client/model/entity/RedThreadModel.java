package twilightforest.client.model.entity;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.model.Model;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.core.Direction;

public class RedThreadModel extends Model {
   private final ModelPart center;
   private final ModelPart up;
   private final ModelPart down;
   private final ModelPart left;
   private final ModelPart right;

   public RedThreadModel(ModelPart root) {
      super(RenderType::entityCutoutNoCull);
      this.center = root.getChild("center");
      this.up = root.getChild("up");
      this.down = root.getChild("down");
      this.left = root.getChild("left");
      this.right = root.getChild("right");
   }

   public static LayerDefinition create() {
      MeshDefinition meshdefinition = new MeshDefinition();
      PartDefinition partdefinition = meshdefinition.getRoot();
      partdefinition.addOrReplaceChild("center", CubeListBuilder.create().texOffs(0, 0).addBox(-1.0F, 0.2F, -1.0F, 2.0F, 0.0F, 2.0F), PartPose.ZERO);
      partdefinition.addOrReplaceChild("up", CubeListBuilder.create().texOffs(0, 2).addBox(-1.0F, 0.2F, -8.0F, 2.0F, 0.0F, 7.0F), PartPose.ZERO);
      partdefinition.addOrReplaceChild("down", CubeListBuilder.create().texOffs(4, 2).addBox(-1.0F, 0.2F, 1.0F, 2.0F, 0.0F, 7.0F), PartPose.ZERO);
      partdefinition.addOrReplaceChild("left", CubeListBuilder.create().texOffs(0, 10).addBox(-8.0F, 0.2F, -1.0F, 7.0F, 0.0F, 2.0F), PartPose.ZERO);
      partdefinition.addOrReplaceChild("right", CubeListBuilder.create().texOffs(0, 14).addBox(1.0F, 0.2F, -1.0F, 7.0F, 0.0F, 2.0F), PartPose.ZERO);
      return LayerDefinition.create(meshdefinition, 16, 16);
   }

   public void renderToBuffer(PoseStack stack, VertexConsumer consumer, int light, int overlay, int color) {
      this.center.render(stack, consumer, light, overlay, color);
      this.up.render(stack, consumer, light, overlay, color);
      this.down.render(stack, consumer, light, overlay, color);
      this.left.render(stack, consumer, light, overlay, color);
      this.right.render(stack, consumer, light, overlay, color);
   }

   public void renderCenterPiece(PoseStack stack, VertexConsumer consumer, int light) {
      this.center.render(stack, consumer, light, OverlayTexture.NO_OVERLAY);
   }

   public ModelPart getPart(Direction face, Direction direction) {
      return switch (face) {
         case NORTH -> {
            switch (direction) {
               case EAST:
                  yield this.right;
               case SOUTH:
               case WEST:
               default:
                  yield this.left;
               case UP:
                  yield this.up;
               case DOWN:
                  yield this.down;
            }
         }
         case EAST -> {
            switch (direction) {
               case SOUTH:
                  yield this.down;
               case WEST:
               default:
                  yield this.up;
               case UP:
                  yield this.right;
               case DOWN:
                  yield this.left;
            }
         }
         case SOUTH -> {
            switch (direction) {
               case WEST:
                  yield this.left;
               case UP:
                  yield this.down;
               case DOWN:
                  yield this.up;
               default:
                  yield this.right;
            }
         }
         case WEST -> {
            switch (direction) {
               case NORTH:
                  yield this.up;
               case UP:
                  yield this.left;
               case DOWN:
                  yield this.right;
               default:
                  yield this.down;
            }
         }
         case UP -> {
            switch (direction) {
               case NORTH:
                  yield this.up;
               case EAST:
               default:
                  yield this.left;
               case SOUTH:
                  yield this.down;
               case WEST:
                  yield this.right;
            }
         }
         case DOWN -> {
            switch (direction) {
               case NORTH:
                  yield this.up;
               case EAST:
                  yield this.right;
               case SOUTH:
                  yield this.down;
               default:
                  yield this.left;
            }
         }
         default -> throw new MatchException(null, null);
      };
   }
}
