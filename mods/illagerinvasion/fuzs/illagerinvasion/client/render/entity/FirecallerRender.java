package fuzs.illagerinvasion.client.render.entity;

import com.mojang.blaze3d.vertex.PoseStack;
import fuzs.illagerinvasion.IllagerInvasion;
import fuzs.illagerinvasion.client.init.ClientModRegistry;
import fuzs.illagerinvasion.client.model.IllagerWithStaffEntityModel;
import fuzs.illagerinvasion.world.entity.monster.Firecaller;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider.Context;
import net.minecraft.resources.ResourceLocation;

public class FirecallerRender extends MobRenderer<Firecaller, IllagerWithStaffEntityModel<Firecaller>> {
   private static final ResourceLocation FIRECALLER_LOCATION = IllagerInvasion.id("textures/entity/firecaller.png");

   public FirecallerRender(Context context) {
      super(context, new IllagerWithStaffEntityModel(context.bakeLayer(ClientModRegistry.STAFF_ILLAGER)), 0.5F);
      ((IllagerWithStaffEntityModel)this.model).getHat().visible = true;
   }

   protected void scale(Firecaller Firecaller, PoseStack matrixStack, float f) {
      matrixStack.scale(0.9F, 0.9F, 0.9F);
   }

   public ResourceLocation getTextureLocation(Firecaller entity) {
      return FIRECALLER_LOCATION;
   }
}
