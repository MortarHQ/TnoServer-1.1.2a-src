package cn.leolezury.eternalstarlight.common.client.visual;

import cn.leolezury.eternalstarlight.common.EternalStarlight;
import cn.leolezury.eternalstarlight.common.client.ESRenderType;
import cn.leolezury.eternalstarlight.common.client.handler.ESClientHandler;
import cn.leolezury.eternalstarlight.common.entity.interfaces.TrailOwner;
import cn.leolezury.eternalstarlight.common.util.TrailEffect;
import com.mojang.blaze3d.vertex.PoseStack;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.phys.Vec3;

public class TrailVisualEffect<T extends Entity & TrailOwner> implements WorldVisualEffect {
   public static final ResourceLocation TRAIL_TEXTURE = EternalStarlight.id("textures/entity/trail.png");
   public static final Map<ResourceLocation, RenderType> RENDER_TYPES = new HashMap<>();
   private final T entity;
   private final TrailEffect effect;
   private final RenderType renderType;
   private boolean shouldRemove;

   public TrailVisualEffect(Entity entity) {
      if (entity instanceof TrailOwner) {
         this.entity = (T)entity;
         this.effect = ((TrailOwner)entity).createNewTrail();
         this.renderType = RENDER_TYPES.getOrDefault(
            BuiltInRegistries.ENTITY_TYPE.getKey(entity.getType()), ESRenderType.entityTranslucentNoDepth(TRAIL_TEXTURE)
         );
      } else {
         throw new UnsupportedOperationException("Entity using TrailVisualEffect must implement TrailOwner");
      }
   }

   public static void registerTrailRenderType(EntityType<?> type, RenderType renderType) {
      RENDER_TYPES.put(BuiltInRegistries.ENTITY_TYPE.getKey(type), renderType);
   }

   public T getEntity() {
      return this.entity;
   }

   public static void clientTick(ClientLevel level, List<WorldVisualEffect> visualEffects) {
      for (Entity entity : level.entitiesForRendering()) {
         if (entity instanceof TrailOwner
            && entity.tickCount > 2
            && visualEffects.stream().noneMatch(effect -> effect instanceof TrailVisualEffect<?> trail && trail.getEntity().getId() == entity.getId())) {
            visualEffects.add(new TrailVisualEffect(entity));
         }
      }
   }

   @Override
   public void worldTick() {
      if (Minecraft.getInstance().level == null) {
         this.shouldRemove = true;
      } else {
         boolean shouldRender = false;

         for (Entity toRender : Minecraft.getInstance().level.entitiesForRendering()) {
            if (toRender == this.entity) {
               shouldRender = true;
               break;
            }
         }

         if (!shouldRender && !this.entity.isRemoved()) {
            this.shouldRemove = true;
         } else {
            if (this.entity.isRemoved() && this.effect.getLength() <= 0.0F) {
               this.shouldRemove = true;
            }

            this.entity.updateTrail(this.effect);
         }
      }
   }

   @Override
   public void render(MultiBufferSource source, PoseStack stack, float partialTicks) {
      boolean entityRemoved = this.entity.isRemoved();
      float x = (float)(entityRemoved ? this.entity.getX() : Mth.lerp(partialTicks, this.entity.xOld, this.entity.getX()));
      float y = (float)(entityRemoved ? this.entity.getY() : Mth.lerp(partialTicks, this.entity.yOld, this.entity.getY()));
      float z = (float)(entityRemoved ? this.entity.getZ() : Mth.lerp(partialTicks, this.entity.zOld, this.entity.getZ()));
      this.effect.prepareRender(this.entity.getSmoothTrailPosition(new Vec3(x, y, z)), partialTicks);
      TrailRenderer.render(
         this.effect,
         (this.entity.isTrailSolid() ? source : ESClientHandler.DELAYED_BUFFER_SOURCE).getBuffer(this.renderType),
         stack,
         this.entity.getTrailOffsetFunction(),
         this.entity.isTrailSolid(),
         this.entity.getTrailColor().x,
         this.entity.getTrailColor().y,
         this.entity.getTrailColor().z,
         this.entity.getTrailColor().w,
         this.entity.isTrailFullBright() ? 15728880 : Minecraft.getInstance().getEntityRenderDispatcher().getPackedLightCoords(this.entity, partialTicks)
      );
   }

   @Override
   public boolean shouldRemove() {
      return this.shouldRemove;
   }
}
