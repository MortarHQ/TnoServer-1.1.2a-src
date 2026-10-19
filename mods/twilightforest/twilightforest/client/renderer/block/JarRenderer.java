package twilightforest.client.renderer.block;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.Nullable;
import net.minecraft.client.gui.Font;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.block.BlockRenderDispatcher;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider.Context;
import net.minecraft.client.renderer.entity.EntityRenderDispatcher;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.entity.DecoratedPotBlockEntity.WobbleStyle;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.RotationSegment;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.neoforge.client.RenderTypeHelper;
import net.neoforged.neoforge.client.model.data.ModelData;
import net.neoforged.neoforge.common.util.Lazy;
import net.neoforged.neoforge.registries.DeferredBlock;
import twilightforest.beanification.Autowired;
import twilightforest.beanification.BeanContext;
import twilightforest.beanification.Configurable;
import twilightforest.block.entity.JarBlockEntity;
import twilightforest.block.entity.MasonJarBlockEntity;
import twilightforest.enums.extensions.TFItemDisplayContextEnumExtension;
import twilightforest.init.TFBlocks;

public class JarRenderer<T extends JarBlockEntity> implements BlockEntityRenderer<T> {
   public static final Map<Item, BakedModel> LIDS = new HashMap<>();
   public static final Lazy<List<JarRenderer.LidResource>> LID_LOCATION_LIST = Lazy.of(
      () -> List.of(
         new JarRenderer.LidResource(TFBlocks.MANGROVE_LOG),
         new JarRenderer.LidResource(TFBlocks.CANOPY_LOG),
         new JarRenderer.LidResource(TFBlocks.DARK_LOG),
         new JarRenderer.LidResource(TFBlocks.MINING_LOG),
         new JarRenderer.LidResource(TFBlocks.SORTING_LOG),
         new JarRenderer.LidResource(TFBlocks.TIME_LOG),
         new JarRenderer.LidResource(TFBlocks.TRANSFORMATION_LOG),
         new JarRenderer.LidResource(TFBlocks.TWILIGHT_OAK_LOG),
         new JarRenderer.LidResource(Items.ACACIA_LOG, "acacia_log"),
         new JarRenderer.LidResource(Items.BIRCH_LOG, "birch_log"),
         new JarRenderer.LidResource(Items.CHERRY_LOG, "cherry_log"),
         new JarRenderer.LidResource(Items.DARK_OAK_LOG, "dark_oak_log"),
         new JarRenderer.LidResource(Items.JUNGLE_LOG, "jungle_log"),
         new JarRenderer.LidResource(Items.MANGROVE_LOG, "mangrove_log", "vanilla_mangrove_log"),
         new JarRenderer.LidResource(Items.OAK_LOG, "oak_log"),
         new JarRenderer.LidResource(Items.SPRUCE_LOG, "spruce_log"),
         new JarRenderer.LidResource(Items.CRIMSON_STEM, "crimson_stem"),
         new JarRenderer.LidResource(Items.WARPED_STEM, "warped_stem"),
         new JarRenderer.LidResource(TFBlocks.STRIPPED_MANGROVE_LOG),
         new JarRenderer.LidResource(TFBlocks.STRIPPED_CANOPY_LOG),
         new JarRenderer.LidResource(TFBlocks.STRIPPED_DARK_LOG),
         new JarRenderer.LidResource(TFBlocks.STRIPPED_MINING_LOG),
         new JarRenderer.LidResource(TFBlocks.STRIPPED_SORTING_LOG),
         new JarRenderer.LidResource(TFBlocks.STRIPPED_TIME_LOG),
         new JarRenderer.LidResource(TFBlocks.STRIPPED_TRANSFORMATION_LOG),
         new JarRenderer.LidResource(TFBlocks.STRIPPED_TWILIGHT_OAK_LOG),
         new JarRenderer.LidResource(Items.STRIPPED_ACACIA_LOG, "stripped_acacia_log"),
         new JarRenderer.LidResource(Items.STRIPPED_BIRCH_LOG, "stripped_birch_log"),
         new JarRenderer.LidResource(Items.STRIPPED_CHERRY_LOG, "stripped_cherry_log"),
         new JarRenderer.LidResource(Items.STRIPPED_DARK_OAK_LOG, "stripped_dark_oak_log"),
         new JarRenderer.LidResource(Items.STRIPPED_JUNGLE_LOG, "stripped_jungle_log"),
         new JarRenderer.LidResource(Items.STRIPPED_MANGROVE_LOG, "stripped_mangrove_log", "vanilla_stripped_mangrove_log"),
         new JarRenderer.LidResource(Items.STRIPPED_OAK_LOG, "stripped_oak_log"),
         new JarRenderer.LidResource(Items.STRIPPED_SPRUCE_LOG, "stripped_spruce_log"),
         new JarRenderer.LidResource(Items.STRIPPED_CRIMSON_STEM, "stripped_crimson_stem"),
         new JarRenderer.LidResource(Items.STRIPPED_WARPED_STEM, "stripped_warped_stem"),
         new JarRenderer.LidResource(TFBlocks.CINDER_LOG),
         new JarRenderer.LidResource(Items.PUMPKIN, "pumpkin"),
         new JarRenderer.LidResource(Items.BAMBOO_BLOCK, "bamboo_block"),
         new JarRenderer.LidResource(Items.STRIPPED_BAMBOO_BLOCK, "stripped_bamboo_block")
      )
   );
   protected final BlockRenderDispatcher blockRenderer;
   protected static final float WOBBLE_AMPLITUDE = 0.125F;

   public JarRenderer(Context context) {
      this.blockRenderer = context.getBlockRenderDispatcher();
   }

   public int getViewDistance() {
      return 256;
   }

   public void render(T blockEntity, float partialTick, PoseStack poseStack, MultiBufferSource buffer, int packedLight, int packedOverlay) {
      poseStack.pushPose();
      poseStack.translate(0.5, 0.0, 0.5);
      poseStack.mulPose(Axis.YP.rotationDegrees(180.0F));
      poseStack.translate(-0.5, 0.0, -0.5);
      WobbleStyle wobbleStyle = blockEntity.lastWobbleStyle;
      if (wobbleStyle != null && blockEntity.getLevel() != null) {
         float f = ((float)(blockEntity.getLevel().getGameTime() - blockEntity.wobbleStartedAtTick) + partialTick) / wobbleStyle.duration;
         if (f >= 0.0F && f <= 1.0F) {
            if (wobbleStyle == WobbleStyle.POSITIVE) {
               float f1 = 0.015625F;
               float f2 = f * (float) (Math.PI * 2);
               float f3 = -1.5F * (Mth.cos(f2) + 0.5F) * Mth.sin(f2 / 2.0F);
               poseStack.rotateAround(Axis.XP.rotation(f3 * f1), 0.5F, 0.0F, 0.5F);
               float f4 = Mth.sin(f2);
               poseStack.rotateAround(Axis.ZP.rotation(f4 * f1), 0.5F, 0.0F, 0.5F);
            } else {
               float f5 = Mth.sin(-f * 3.0F * (float) Math.PI) * 0.125F;
               float f6 = 1.0F - f;
               poseStack.rotateAround(Axis.YP.rotation(f5 * f6), 0.5F, 0.0F, 0.5F);
            }
         }
      }

      BlockState state = blockEntity.getBlockState();
      if (LIDS.containsKey(blockEntity.lid)) {
         renderModel(LIDS.get(blockEntity.lid), state, this.blockRenderer, poseStack, buffer, packedLight, packedOverlay);
      }

      renderJarModel(state, this.blockRenderer, poseStack, buffer, packedLight, packedOverlay);
      this.renderContents(blockEntity, partialTick, poseStack, buffer, packedLight, packedOverlay);
      poseStack.popPose();
   }

   public static void renderJarModel(
      BlockState blockState, BlockRenderDispatcher blockRenderer, PoseStack stack, MultiBufferSource buffer, int packedLight, int packedOverlay
   ) {
      BakedModel bakedModel = blockRenderer.getBlockModel(blockState);
      renderModel(bakedModel, blockState, blockRenderer, stack, buffer, packedLight, packedOverlay);
   }

   public static void renderModel(
      BakedModel bakedModel,
      BlockState blockState,
      BlockRenderDispatcher blockRenderer,
      PoseStack stack,
      MultiBufferSource buffer,
      int packedLight,
      int packedOverlay
   ) {
      int color = blockRenderer.blockColors.getColor(blockState, null, null, 0);
      float r = (color >> 16 & 0xFF) / 255.0F;
      float g = (color >> 8 & 0xFF) / 255.0F;
      float b = (color & 0xFF) / 255.0F;

      for (RenderType rt : bakedModel.getRenderTypes(blockState, RandomSource.create(42L), ModelData.EMPTY)) {
         blockRenderer.getModelRenderer()
            .renderModel(
               stack.last(),
               buffer.getBuffer(RenderTypeHelper.getEntityRenderType(rt, false)),
               blockState,
               bakedModel,
               r,
               g,
               b,
               packedLight,
               packedOverlay,
               ModelData.EMPTY,
               rt
            );
      }
   }

   public void renderContents(T blockEntity, float partialTick, PoseStack poseStack, MultiBufferSource buffer, int packedLight, int packedOverlay) {
   }

   public record LidResource(Item lid, ResourceLocation resourceLocation, @Nullable String customPath) {
      public LidResource(DeferredBlock<?> lid) {
         this(lid.asItem(), lid.getId(), null);
      }

      public LidResource(Item item, String path) {
         this(item, ResourceLocation.fromNamespaceAndPath("minecraft", path), null);
      }

      public LidResource(Item item, String path, String customPath) {
         this(item, ResourceLocation.fromNamespaceAndPath("minecraft", path), customPath);
      }
   }

   @Configurable
   public static class MasonJarRenderer extends JarRenderer<MasonJarBlockEntity> {
      @Autowired(dist = Dist.CLIENT)
      private TFItemDisplayContextEnumExtension itemDisplayContextEnumExtension;
      protected final ItemRenderer itemRenderer;
      protected final EntityRenderDispatcher entityRender;
      protected final Font font;

      public MasonJarRenderer(Context context) {
         super(context);
         this.entityRender = context.getEntityRenderer();
         this.itemRenderer = context.getItemRenderer();
         this.font = context.getFont();
         BeanContext.injectInto(this);
      }

      public void renderContents(
         MasonJarBlockEntity blockEntity, float partialTick, PoseStack poseStack, MultiBufferSource buffer, int packedLight, int packedOverlay
      ) {
         ItemStack stack = blockEntity.getItemHandler().getItem();
         if (!stack.isEmpty()) {
            poseStack.pushPose();
            poseStack.translate(0.5, 0.4375, 0.5);
            poseStack.mulPose(Axis.YN.rotationDegrees(RotationSegment.convertToDegrees(blockEntity.getItemRotation())));
            poseStack.scale(0.5F, 0.5F, 0.5F);
            this.itemRenderer
               .renderStatic(stack, this.itemDisplayContextEnumExtension.JARRED, packedLight, OverlayTexture.NO_OVERLAY, poseStack, buffer, null, 0);
            poseStack.popPose();
         }
      }
   }
}
