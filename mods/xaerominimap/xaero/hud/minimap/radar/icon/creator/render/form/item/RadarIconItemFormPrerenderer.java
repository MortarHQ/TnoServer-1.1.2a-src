package xaero.hud.minimap.radar.icon.creator.render.form.item;

import com.mojang.blaze3d.vertex.PoseStack;
import java.util.List;
import javax.annotation.Nullable;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.decoration.ItemFrame;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import xaero.hud.minimap.radar.icon.creator.RadarIconCreator;
import xaero.hud.minimap.radar.icon.creator.render.form.IRadarIconFormPrerenderer;
import xaero.hud.minimap.radar.icon.creator.render.trace.ModelRenderTrace;
import xaero.hud.minimap.radar.icon.definition.form.item.RadarIconItemForm;

public class RadarIconItemFormPrerenderer implements IRadarIconFormPrerenderer {
   @Override
   public boolean requiresEntityModel() {
      return false;
   }

   @Override
   public boolean isFlipped() {
      return false;
   }

   @Override
   public boolean isOutlined() {
      return true;
   }

   @Override
   public <T extends Entity> boolean prerender(
      GuiGraphics guiGraphics,
      EntityRenderer<? super T> entityRenderer,
      @Nullable EntityModel<T> entityModel,
      T entity,
      @Nullable List<ModelRenderTrace> traceResult,
      RadarIconCreator.Parameters parameters
   ) {
      RadarIconItemForm itemForm = (RadarIconItemForm)parameters.form;
      ItemStack itemStack = this.getItemToRender(entity, itemForm);
      if (itemStack != null && !itemStack.isEmpty()) {
         PoseStack matrixStack = guiGraphics.pose();
         int halfIcon = 32;
         matrixStack.translate(halfIcon, halfIcon, 1.0F);
         float scale = parameters.scale;
         if (scale < 1.0F) {
            matrixStack.scale(scale, scale, 1.0F);
         }

         matrixStack.translate(0.0F, 0.0F, -300.0F);
         guiGraphics.renderItem(itemStack, -8, -8);
         guiGraphics.flush();
         return true;
      } else {
         return false;
      }
   }

   private ItemStack getItemToRender(Entity entity, RadarIconItemForm itemForm) {
      ResourceLocation itemKey = itemForm.getItemKey();
      if (itemKey != null) {
         Item item = (Item)BuiltInRegistries.ITEM.get(itemKey);
         return item == null ? null : new ItemStack(item);
      } else {
         ItemStack selfStack = this.getSelfItem(entity);
         return selfStack == null ? null : new ItemStack(selfStack.getItem());
      }
   }

   private ItemStack getSelfItem(Entity entity) {
      if (entity instanceof ItemEntity itemEntity) {
         return itemEntity.getItem();
      } else {
         return entity instanceof ItemFrame itemFrame ? itemFrame.getItem() : entity.getPickResult();
      }
   }
}
