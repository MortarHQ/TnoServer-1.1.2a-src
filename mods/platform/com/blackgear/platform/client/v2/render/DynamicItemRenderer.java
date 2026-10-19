package com.blackgear.platform.client.v2.render;

import com.blackgear.platform.core.util.event.ResultHolder;
import com.google.common.base.Supplier;
import com.google.common.base.Suppliers;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.blaze3d.vertex.PoseStack.Pose;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import net.minecraft.client.color.item.ItemColors;
import net.minecraft.client.renderer.ItemModelShaper;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.block.model.BakedQuad;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.core.Direction;
import net.minecraft.util.RandomSource;
import net.minecraft.util.FastColor.ARGB32;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ItemLike;

public class DynamicItemRenderer {
   public static final Supplier<DynamicItemRenderer> INSTANCE = Suppliers.memoize(DynamicItemRenderer::new);
   private static final Map<Item, DynamicItemRenderer.Renderer> RENDERERS = new HashMap<>();

   public void register(ItemLike item, DynamicItemRenderer.Renderer renderer) {
      RENDERERS.putIfAbsent(item.asItem(), renderer);
   }

   public DynamicItemRenderer.Renderer get(ItemLike item) {
      return RENDERERS.get(item.asItem());
   }

   public Map<Item, DynamicItemRenderer.Renderer> getRenderers() {
      return RENDERERS;
   }

   public interface Renderer {
      void renderFirstPerson(
         ItemStack var1,
         ItemDisplayContext var2,
         boolean var3,
         PoseStack var4,
         MultiBufferSource var5,
         int var6,
         int var7,
         BakedModel var8,
         ItemModelShaper var9,
         ItemColors var10
      );

      ResultHolder<BakedModel> renderThirdPerson(ItemStack var1, ItemModelShaper var2);

      Set<ModelResourceLocation> registerModels();

      default boolean shouldUse() {
         return true;
      }

      default void renderModelLists(BakedModel model, ItemStack stack, int light, int overlay, PoseStack pose, VertexConsumer buffer, ItemColors colors) {
         RandomSource random = RandomSource.create();
         long seed = 42L;

         for (Direction direction : Direction.values()) {
            random.setSeed(seed);
            this.renderQuadList(pose, buffer, model.getQuads(null, direction, random), stack, light, overlay, colors);
         }

         random.setSeed(seed);
         this.renderQuadList(pose, buffer, model.getQuads(null, null, random), stack, light, overlay, colors);
      }

      default void renderQuadList(PoseStack pose, VertexConsumer buffer, List<BakedQuad> quads, ItemStack stack, int light, int overlay, ItemColors colors) {
         boolean isPresent = !stack.isEmpty();
         Pose last = pose.last();

         for (BakedQuad quad : quads) {
            int tint = -1;
            if (isPresent && quad.isTinted()) {
               tint = colors.getColor(stack, quad.getTintIndex());
            }

            float alpha = ARGB32.alpha(tint) / 255.0F;
            float red = ARGB32.red(tint) / 255.0F;
            float green = ARGB32.green(tint) / 255.0F;
            float blue = ARGB32.blue(tint) / 255.0F;
            buffer.putBulkData(last, quad, red, green, blue, alpha, light, overlay);
         }
      }
   }
}
