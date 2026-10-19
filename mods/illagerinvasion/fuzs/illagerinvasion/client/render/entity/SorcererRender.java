package fuzs.illagerinvasion.client.render.entity;

import com.mojang.blaze3d.vertex.PoseStack;
import fuzs.illagerinvasion.IllagerInvasion;
import fuzs.illagerinvasion.client.init.ClientModRegistry;
import fuzs.illagerinvasion.client.model.HatIllagerEntityModel;
import fuzs.illagerinvasion.world.entity.monster.Sorcerer;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider.Context;
import net.minecraft.resources.ResourceLocation;

public class SorcererRender extends MobRenderer<Sorcerer, HatIllagerEntityModel<Sorcerer>> {
   private static final ResourceLocation SORCERER_LOCATION = IllagerInvasion.id("textures/entity/sorcerer.png");

   public SorcererRender(Context context) {
      super(context, new HatIllagerEntityModel(context.bakeLayer(ClientModRegistry.HAT_ILLAGER)), 0.5F);
      ((HatIllagerEntityModel)this.model).getHat().visible = true;
   }

   protected void scale(Sorcerer sorcerer, PoseStack matrixStack, float f) {
      matrixStack.scale(0.95F, 0.95F, 0.95F);
   }

   public ResourceLocation getTextureLocation(Sorcerer entity) {
      return SORCERER_LOCATION;
   }
}
