package de.ellpeck.naturesaura.compat.jei;

import com.mojang.blaze3d.platform.Lighting;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import de.ellpeck.naturesaura.recipes.AnimalSpawnerRecipe;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import mezz.jei.api.gui.builder.IRecipeLayoutBuilder;
import mezz.jei.api.gui.drawable.IDrawable;
import mezz.jei.api.gui.ingredient.IRecipeSlotsView;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.recipe.IFocusGroup;
import mezz.jei.api.recipe.RecipeIngredientRole;
import mezz.jei.api.recipe.RecipeType;
import mezz.jei.api.recipe.category.IRecipeCategory;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.renderer.MultiBufferSource.BufferSource;
import net.minecraft.client.renderer.entity.EntityRenderDispatcher;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.SpawnEggItem;
import org.joml.Matrix4fStack;
import org.joml.Quaternionf;

public class AnimalSpawnerCategory implements IRecipeCategory<AnimalSpawnerRecipe> {
   private final IDrawable background;
   private final Map<EntityType<?>, Entity> entityCache = new HashMap<>();

   public AnimalSpawnerCategory(IGuiHelper helper) {
      this.background = helper.createDrawable(ResourceLocation.fromNamespaceAndPath("naturesaura", "textures/gui/jei/animal_spawner.png"), 0, 0, 72, 86);
   }

   private static void renderEntity(PoseStack matrixstack, int x, int y, float scale, float yaw, float pitch, LivingEntity entity) {
      float f = (float)Math.atan(yaw / 40.0F);
      float f1 = (float)Math.atan(pitch / 40.0F);
      Matrix4fStack posestack = RenderSystem.getModelViewStack();
      posestack.pushMatrix();
      posestack.translate(x, y, 1050.0F);
      posestack.scale(1.0F, 1.0F, -1.0F);
      RenderSystem.applyModelViewMatrix();
      matrixstack.translate(0.0, 0.0, 1000.0);
      matrixstack.scale(scale, scale, scale);
      Quaternionf quaternion = Axis.ZP.rotationDegrees(180.0F);
      Quaternionf quaternion1 = Axis.XP.rotationDegrees(f1 * 20.0F);
      quaternion.mul(quaternion1);
      matrixstack.mulPose(quaternion);
      float f2 = entity.yBodyRot;
      float f3 = entity.getYRot();
      float f4 = entity.getXRot();
      float f5 = entity.yHeadRotO;
      float f6 = entity.yHeadRot;
      entity.yBodyRot = 180.0F + f * 20.0F;
      entity.setYRot(180.0F + f * 40.0F);
      entity.setXRot(-f1 * 20.0F);
      entity.yHeadRot = entity.getYRot();
      entity.yHeadRotO = entity.getYRot();
      Lighting.setupForEntityInInventory();
      EntityRenderDispatcher entityrenderermanager = Minecraft.getInstance().getEntityRenderDispatcher();
      quaternion1.conjugate();
      entityrenderermanager.overrideCameraOrientation(quaternion1);
      entityrenderermanager.setRenderShadow(false);
      BufferSource buff = Minecraft.getInstance().renderBuffers().bufferSource();
      entityrenderermanager.render(entity, 0.0, 0.0, 0.0, 0.0F, 1.0F, matrixstack, buff, 15728880);
      buff.endBatch();
      entityrenderermanager.setRenderShadow(true);
      entity.yBodyRot = f2;
      entity.setYRot(f3);
      entity.setXRot(f4);
      entity.yHeadRotO = f5;
      entity.yHeadRot = f6;
      posestack.popMatrix();
      RenderSystem.applyModelViewMatrix();
      Lighting.setupFor3DItems();
   }

   public RecipeType<AnimalSpawnerRecipe> getRecipeType() {
      return JEINaturesAuraPlugin.SPAWNER;
   }

   public Component getTitle() {
      return Component.translatable("container." + JEINaturesAuraPlugin.SPAWNER.getUid() + ".name");
   }

   public IDrawable getBackground() {
      return this.background;
   }

   public IDrawable getIcon() {
      return null;
   }

   public void setRecipe(IRecipeLayoutBuilder builder, AnimalSpawnerRecipe recipe, IFocusGroup focuses) {
      for (int i = 0; i < recipe.ingredients.size(); i++) {
         builder.addSlot(RecipeIngredientRole.INPUT, i * 18 + 1, 69).addItemStacks(Arrays.asList(recipe.ingredients.get(i).getItems()));
      }

      builder.addInvisibleIngredients(RecipeIngredientRole.OUTPUT).addItemStack(new ItemStack(SpawnEggItem.byId(recipe.entity)));
   }

   public void draw(AnimalSpawnerRecipe recipe, IRecipeSlotsView recipeSlotsView, GuiGraphics graphics, double mouseX, double mouseY) {
      Minecraft minecraft = Minecraft.getInstance();
      Entity entity = this.entityCache.get(recipe.entity);
      if (entity == null) {
         entity = recipe.makeEntity(minecraft.level, BlockPos.ZERO);
         this.entityCache.put(recipe.entity, entity);
      }

      graphics.pose().pushPose();
      float size = Math.max(1.0F, Math.max(recipe.entity.getWidth(), recipe.entity.getHeight()));
      renderEntity(graphics.pose(), 36, 56, 100.0F / size * 0.4F, 40.0F, size * 0.5F, (LivingEntity)entity);
      graphics.pose().popPose();
      String name = recipe.entity.getDescription().getString();
      graphics.drawString(minecraft.font, name, 36.0F - minecraft.font.width(name) / 2.0F, 55.0F, 16777215, true);
   }
}
