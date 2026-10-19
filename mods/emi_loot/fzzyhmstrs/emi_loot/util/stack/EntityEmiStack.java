package fzzyhmstrs.emi_loot.util.stack;

import com.mojang.blaze3d.platform.Lighting;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import dev.emi.emi.api.render.EmiTooltipComponents;
import dev.emi.emi.api.stack.EmiStack;
import fzzyhmstrs.emi_loot.EMILoot;
import fzzyhmstrs.emi_loot.client.ClientResourceData;
import fzzyhmstrs.emi_loot.util.LText;
import java.util.ArrayList;
import java.util.List;
import net.minecraft.Util;
import net.minecraft.client.Minecraft;
import net.minecraft.client.MouseHandler;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.screens.inventory.tooltip.ClientTooltipComponent;
import net.minecraft.client.renderer.MultiBufferSource.BufferSource;
import net.minecraft.client.renderer.entity.EntityRenderDispatcher;
import net.minecraft.core.component.DataComponentPatch;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import org.jetbrains.annotations.Nullable;
import org.joml.Matrix4fStack;
import org.joml.Quaternionf;
import org.joml.Vector3f;

public class EntityEmiStack extends EmiStack {
   @Nullable
   private final Entity entity;
   private final EntityEmiStack.EntityRenderContext ctx;

   protected EntityEmiStack(@Nullable Entity entity) {
      this(entity, 8.0);
   }

   protected EntityEmiStack(@Nullable Entity entity, double scale) {
      this.entity = entity;
      if (entity != null) {
         boolean hasTransform = ClientResourceData.MOB_ROTATIONS.containsKey(entity.getType());
         Vector3f transform = ClientResourceData.MOB_ROTATIONS.getOrDefault(entity.getType(), new Vector3f(0.0F, 0.0F, 0.0F)).mul((float) (Math.PI / 180.0));
         this.ctx = new EntityEmiStack.EntityRenderContext(scale, hasTransform, transform);
      } else {
         this.ctx = new EntityEmiStack.EntityRenderContext(scale, false, new Vector3f(0.0F, 0.0F, 0.0F));
      }
   }

   public static EntityEmiStack of(@Nullable Entity entity) {
      return new EntityEmiStack(entity);
   }

   public static EntityEmiStack ofScaled(@Nullable Entity entity, double scale) {
      return new EntityEmiStack(entity, scale);
   }

   public EmiStack copy() {
      EntityEmiStack stack = new EntityEmiStack(this.entity);
      stack.setRemainder(this.getRemainder().copy());
      stack.comparison = this.comparison;
      return stack;
   }

   public boolean isEmpty() {
      return this.entity == null;
   }

   public void render(GuiGraphics matrices, int x, int y, float delta, int flags) {
      try {
         if (this.entity != null) {
            if (this.entity instanceof LivingEntity living) {
               renderEntity(matrices.pose(), x + 8, (int)(y + 8 + this.ctx.size), this.ctx, living);
            } else {
               renderEntity(matrices.pose(), (int)(x + 2.0 * this.ctx.size / 2.0), (int)(y + 2.0 * this.ctx.size), this.ctx, this.entity);
            }
         }
      } catch (Throwable e) {
         if (EMILoot.DEBUG && Util.getMillis() % 1000L == 0L) {
            EMILoot.LOGGER.error("Rendering error for entity stack" + this.toString());
         }
      }
   }

   public DataComponentPatch getComponentChanges() {
      return DataComponentPatch.EMPTY;
   }

   public Object getKey() {
      return this.entity;
   }

   public ResourceLocation getId() {
      if (this.entity == null) {
         throw new RuntimeException("Entity is null");
      } else {
         return BuiltInRegistries.ENTITY_TYPE.getKey(this.entity.getType());
      }
   }

   public List<Component> getTooltipText() {
      return List.of(this.getName());
   }

   public List<ClientTooltipComponent> getTooltip() {
      List<ClientTooltipComponent> list = new ArrayList<>();
      if (this.entity != null) {
         list.addAll(this.getTooltipText().stream().map(Component::getVisualOrderText).map(ClientTooltipComponent::create).toList());
         EmiTooltipComponents.appendModName(list, BuiltInRegistries.ENTITY_TYPE.getKey(this.entity.getType()).getNamespace());
         if (!this.getRemainder().isEmpty()) {
            list.add(EmiTooltipComponents.getRemainderTooltipComponent(this));
         }
      }

      return list;
   }

   public Component getName() {
      return (Component)(this.entity != null ? this.entity.getName() : LText.literal("yet another missingno"));
   }

   static void renderEntity(PoseStack matrices, int x, int y, EntityEmiStack.EntityRenderContext ctx, LivingEntity entity) {
      Minecraft client = Minecraft.getInstance();
      double width = client.getWindow().getGuiScaledWidth();
      double height = client.getWindow().getGuiScaledHeight();
      float mouseX = (float)(client.mouseHandler.xpos() * width / client.getWindow().getScreenWidth());
      float mouseY = (float)(client.mouseHandler.ypos() * height / client.getWindow().getScreenHeight());
      double posX = mouseX - width / 2.0 + 63.0;
      if (!Double.isNaN(posX)) {
         double posY = mouseY - height / 2.0;
         if (!Double.isNaN(posY)) {
            float f = (float)Math.atan(-posX / 40.0);
            float g = (float)Math.atan(-posY / 40.0);
            Matrix4fStack matrixStack = RenderSystem.getModelViewStack();
            matrixStack.pushMatrix();
            matrixStack.mul(matrices.last().pose());
            matrixStack.translate(x, y, 1050.0F);
            matrixStack.scale(1.0F, 1.0F, -1.0F);
            RenderSystem.applyModelViewMatrix();
            PoseStack matrixStack2 = new PoseStack();
            matrixStack2.translate(0.0, 0.0, 1000.0);
            matrixStack2.scale((float)ctx.size, (float)ctx.size, (float)ctx.size);
            Quaternionf quaternion = new Quaternionf().rotateZ((float) Math.PI);
            Quaternionf quaternion2 = new Quaternionf()
               .rotateX(g * 20.0F * (float) (Math.PI / 180.0) * Mth.cos(ctx.transform.z) - f * 20.0F * (float) (Math.PI / 180.0) * Mth.sin(ctx.transform.z));
            if (ctx.hasTransform) {
               Quaternionf quaternion3 = new Quaternionf().rotateXYZ(ctx.transform.x, ctx.transform.y, ctx.transform.z);
               quaternion.mul(quaternion3);
            }

            quaternion.mul(quaternion2);
            matrixStack2.mulPose(quaternion);
            float h = entity.yBodyRot;
            float i = entity.getYRot();
            float j = entity.getXRot();
            float k = entity.yHeadRotO;
            float l = entity.yHeadRot;
            entity.yBodyRot = 180.0F + (f * 20.0F * Mth.cos(ctx.transform.z) + g * 20.0F * Mth.sin(ctx.transform.z));
            float yaw = 180.0F + (f * 40.0F * Mth.cos(ctx.transform.z) + g * 40.0F * Mth.sin(ctx.transform.z));
            entity.setYRot(yaw);
            float pitch = -g * 20.0F * Mth.cos(ctx.transform.z) + -f * 20.0F * Mth.sin(ctx.transform.z);
            entity.setXRot(pitch);
            entity.yHeadRot = entity.getYRot();
            entity.yHeadRotO = entity.getYRot();
            Lighting.setupForEntityInInventory();
            EntityRenderDispatcher entityRenderDispatcher = Minecraft.getInstance().getEntityRenderDispatcher();
            entityRenderDispatcher.setRenderShadow(false);
            BufferSource immediate = Minecraft.getInstance().renderBuffers().bufferSource();

            try {
               RenderSystem.runAsFancy(() -> entityRenderDispatcher.render(entity, 0.0, 0.0, 0.0, 0.0F, 1.0F, matrixStack2, immediate, 15728880));
            } finally {
               immediate.endBatch();
            }

            entityRenderDispatcher.setRenderShadow(true);
            entity.yBodyRot = h;
            entity.setYRot(i);
            entity.setXRot(j);
            entity.yHeadRotO = k;
            entity.yHeadRot = l;
            matrixStack.popMatrix();
            RenderSystem.applyModelViewMatrix();
            Lighting.setupFor3DItems();
         }
      }
   }

   static void renderEntity(PoseStack matrices, int x, int y, EntityEmiStack.EntityRenderContext ctx, Entity entity) {
      Minecraft client = Minecraft.getInstance();
      MouseHandler mouse = client.mouseHandler;
      float w = 1920.0F;
      float h = 1080.0F;
      Screen screen = client.screen;
      if (screen != null) {
         w = screen.width;
         h = screen.height;
      }

      float mouseX = (float)(w + 51.0F - mouse.xpos());
      float mouseY = (float)(h + 75.0F - 50.0F - mouse.ypos());
      float f = (float)Math.atan(mouseX / 40.0F);
      float g = (float)Math.atan(mouseY / 40.0F);
      Matrix4fStack matrixStack = RenderSystem.getModelViewStack();
      matrixStack.pushMatrix();
      matrixStack.mul(matrices.last().pose());
      matrixStack.translate(x, y, 1050.0F);
      matrixStack.scale(1.0F, 1.0F, -1.0F);
      RenderSystem.applyModelViewMatrix();
      PoseStack matrixStack2 = new PoseStack();
      matrixStack2.translate(0.0, 0.0, 1000.0);
      matrixStack2.scale((float)ctx.size, (float)ctx.size, (float)ctx.size);
      Quaternionf quaternion = new Quaternionf().rotateZ((float) Math.PI);
      Quaternionf quaternion2 = new Quaternionf()
         .rotateX(g * 20.0F * (float) (Math.PI / 180.0) * Mth.cos(ctx.transform.z) - f * 20.0F * (float) (Math.PI / 180.0) * Mth.sin(ctx.transform.z));
      if (ctx.hasTransform) {
         Quaternionf quaternion3 = new Quaternionf().rotateXYZ(ctx.transform.x, ctx.transform.y, ctx.transform.z);
         quaternion.mul(quaternion3);
      }

      quaternion.mul(quaternion2);
      matrixStack2.mulPose(quaternion);
      float i = entity.getYRot();
      float j = entity.getXRot();
      entity.setYRot(180.0F + (f * 40.0F * Mth.cos(ctx.transform.z) + g * 40.0F * Mth.sin(ctx.transform.z)));
      entity.setXRot(-g * 20.0F * Mth.cos(ctx.transform.z) + -f * 20.0F * Mth.sin(ctx.transform.z));
      Lighting.setupForEntityInInventory();
      EntityRenderDispatcher entityRenderDispatcher = Minecraft.getInstance().getEntityRenderDispatcher();
      entityRenderDispatcher.setRenderShadow(false);
      BufferSource immediate = Minecraft.getInstance().renderBuffers().bufferSource();

      try {
         RenderSystem.runAsFancy(() -> entityRenderDispatcher.render(entity, 0.0, 0.0, 0.0, 0.0F, 1.0F, matrixStack2, immediate, 15728880));
      } finally {
         immediate.endBatch();
      }

      entityRenderDispatcher.setRenderShadow(true);
      entity.setYRot(i);
      entity.setXRot(j);
      matrixStack.popMatrix();
      RenderSystem.applyModelViewMatrix();
      Lighting.setupFor3DItems();
   }

   private record EntityRenderContext(double size, boolean hasTransform, Vector3f transform) {
      static EntityEmiStack.EntityRenderContext EMPTY = new EntityEmiStack.EntityRenderContext(8.0, false, new Vector3f(0.0F, 0.0F, 0.0F));
   }
}
