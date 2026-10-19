package fuzs.illagerinvasion.client.render.entity;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;
import fuzs.illagerinvasion.IllagerInvasion;
import fuzs.illagerinvasion.world.entity.monster.Archivist;
import fuzs.puzzleslib.api.core.v1.utility.ResourceLocationHelper;
import net.minecraft.client.model.BookModel;
import net.minecraft.client.model.IllagerModel;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.IllagerRenderer;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.EntityRendererProvider.Context;
import net.minecraft.client.renderer.entity.layers.ItemInHandLayer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.client.resources.model.Material;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.inventory.InventoryMenu;

public class ArchivistRender extends IllagerRenderer<Archivist> {
   private static final ResourceLocation ARCHIVIST_LOCATION = IllagerInvasion.id("textures/entity/archivist.png");
   private static final Material BOOK_LOCATION = new Material(
      InventoryMenu.BLOCK_ATLAS, ResourceLocationHelper.withDefaultNamespace("entity/enchanting_table_book")
   );
   private final BookModel book;

   public ArchivistRender(Context context) {
      super(context, new IllagerModel(context.bakeLayer(ModelLayers.EVOKER)), 0.5F);
      this.book = new BookModel(context.bakeLayer(ModelLayers.BOOK));
      this.addLayer(
         new ItemInHandLayer<Archivist, IllagerModel<Archivist>>(this, context.getItemInHandRenderer()) {
            public void render(
               PoseStack matrixStack,
               MultiBufferSource vertexConsumerProvider,
               int i,
               Archivist Archivist,
               float f,
               float g,
               float h,
               float j,
               float k,
               float l
            ) {
               if (Archivist.isCastingSpell()) {
                  super.render(matrixStack, vertexConsumerProvider, i, Archivist, f, g, h, j, k, l);
               }

               matrixStack.pushPose();
               matrixStack.translate(0.0, 0.362, -0.5);
               matrixStack.mulPose(Axis.YP.rotationDegrees(270.0F));
               if (Archivist.isCastingSpell()) {
                  matrixStack.translate(-0.4, 0.0, 0.0);
                  ArchivistRender.this.book.setupAnim(0.0F, 10.0F + Mth.cos(Archivist.tickCount) * 0.55F, 0.0F, 1.05F);
                  matrixStack.mulPose(Axis.ZN.rotationDegrees(30.0F));
               } else {
                  ArchivistRender.this.book.setupAnim(0.0F, 0.0F, 0.0F, 0.0F);
               }

               VertexConsumer vertexConsumer = ArchivistRender.BOOK_LOCATION.buffer(vertexConsumerProvider, RenderType::entitySolid);
               ArchivistRender.this.book.renderToBuffer(matrixStack, vertexConsumer, i, OverlayTexture.NO_OVERLAY);
               matrixStack.popPose();
            }
         }
      );
      ((IllagerModel)this.model).getHat().visible = true;
   }

   public ResourceLocation getTextureLocation(Archivist entity) {
      return ARCHIVIST_LOCATION;
   }
}
