package dev.foxboss.client;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import dev.foxboss.entity.MilimEntity;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import software.bernie.geckolib.cache.object.GeoBone;
import software.bernie.geckolib.renderer.layer.BlockAndItemGeoLayer;

public final class MilimHeldItemLayer extends BlockAndItemGeoLayer<MilimEntity> {
   private static final String LEFT_HAND = "held_item_r";
   private static final String RIGHT_HAND = "held_item_l";
   private static final ResourceLocation DRAGON_KNUCKLE = ResourceLocation.fromNamespaceAndPath("tensura", "dragon_knuckle");
   private static final float HELD_ITEM_FORWARD_OFFSET = 0.125F;

   public MilimHeldItemLayer(MilimRenderer renderer) {
      super(renderer);
   }

   protected ItemStack getStackForBone(GeoBone bone, MilimEntity entity) {
      ItemStack stack = switch (bone.getName()) {
         case "held_item_r" -> entity.isLeftHanded() ? entity.getMainHandItem() : entity.getOffhandItem();
         case "held_item_l" -> entity.isLeftHanded() ? entity.getOffhandItem() : entity.getMainHandItem();
         default -> ItemStack.EMPTY;
      };
      return stack.isEmpty() ? null : stack;
   }

   protected ItemDisplayContext getTransformTypeForStack(GeoBone bone, ItemStack stack, MilimEntity entity) {
      return "held_item_r".equals(bone.getName()) ? ItemDisplayContext.THIRD_PERSON_LEFT_HAND : ItemDisplayContext.THIRD_PERSON_RIGHT_HAND;
   }

   protected void renderStackForBone(
      PoseStack pose, GeoBone bone, ItemStack stack, MilimEntity entity, MultiBufferSource source, float partial, int light, int overlay
   ) {
      if (DRAGON_KNUCKLE.equals(BuiltInRegistries.ITEM.getKey(stack.getItem()))) {
         pose.translate(0.0F, -0.01875F, -0.10625F);
         pose.scale(0.8F, 0.65F, 0.85F);
      } else {
         pose.translate(0.0F, 0.0F, -0.125F);
      }

      pose.mulPose(Axis.XP.rotationDegrees(-90.0F));
      Minecraft.getInstance()
         .getItemRenderer()
         .renderStatic(
            entity,
            stack,
            this.getTransformTypeForStack(bone, stack, entity),
            "held_item_r".equals(bone.getName()),
            pose,
            source,
            entity.level(),
            light,
            overlay,
            entity.getId()
         );
   }
}
