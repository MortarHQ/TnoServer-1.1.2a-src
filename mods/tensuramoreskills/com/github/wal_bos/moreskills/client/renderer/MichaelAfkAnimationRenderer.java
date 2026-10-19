package com.github.wal_bos.moreskills.client.renderer;

import com.github.wal_bos.moreskills.client.fx.MichaelAfkStarsVfxClient;
import com.github.wal_bos.moreskills.item.KeyOfSealingItem;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import net.minecraft.Util;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRendererProvider.Context;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Pose;
import net.minecraft.world.entity.player.PlayerModelPart;
import net.neoforged.neoforge.client.event.ClientTickEvent.Post;
import net.neoforged.neoforge.client.event.EntityRenderersEvent.AddLayers;
import net.neoforged.neoforge.client.event.RenderPlayerEvent.Pre;
import software.bernie.geckolib.animatable.GeoReplacedEntity;
import software.bernie.geckolib.animatable.SingletonGeoAnimatable;
import software.bernie.geckolib.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.animation.AnimationController;
import software.bernie.geckolib.animation.PlayState;
import software.bernie.geckolib.animation.RawAnimation;
import software.bernie.geckolib.animation.AnimatableManager.ControllerRegistrar;
import software.bernie.geckolib.cache.object.GeoBone;
import software.bernie.geckolib.model.GeoModel;
import software.bernie.geckolib.renderer.GeoReplacedEntityRenderer;
import software.bernie.geckolib.util.GeckoLibUtil;

public final class MichaelAfkAnimationRenderer extends GeoReplacedEntityRenderer<AbstractClientPlayer, MichaelAfkAnimationRenderer.MichaelAfkAnimatable> {
   private static final long IDLE_MILLIS_REQUIRED = 5000L;
   private static final double MOVEMENT_EPSILON_SQR = 1.0E-6;
   public static final long ANIMATION_LENGTH_MILLIS = 32458L;
   public static final float ANIMATION_LENGTH_SECONDS = 32.458F;
   private static final String CONTROLLER_NAME = "michael_afk_controller";
   private static final String TRIGGER_NAME = "michael_afk";
   private static final String ANIMATION_NAME = "idleanimation";
   private static final ResourceLocation MODEL_RESOURCE = ResourceLocation.fromNamespaceAndPath("tensuramoreskills", "geo/michael_afk.geo.json");
   private static final ResourceLocation ANIMATION_RESOURCE = ResourceLocation.fromNamespaceAndPath(
      "tensuramoreskills", "animations/michael_afk.animation.json"
   );
   private static final ResourceLocation WEAPON_TEXTURE_RESOURCE = ResourceLocation.fromNamespaceAndPath(
      "tensuramoreskills", "textures/entity/commissionkeyanimationtexture.png"
   );
   private static final Set<String> PLAYER_BONES = Set.of(
      "player_root",
      "bone",
      "bone2",
      "bone3",
      "bone4",
      "bone5",
      "bone6",
      "left_pants_outer",
      "right_pants_outer",
      "left_sleeve_outer",
      "right_sleeve_outer",
      "jacket_outer",
      "hat_outer"
   );
   private static final Map<String, PlayerModelPart> OUTER_LAYER_PARTS = Map.of(
      "left_pants_outer",
      PlayerModelPart.LEFT_PANTS_LEG,
      "right_pants_outer",
      PlayerModelPart.RIGHT_PANTS_LEG,
      "left_sleeve_outer",
      PlayerModelPart.LEFT_SLEEVE,
      "right_sleeve_outer",
      PlayerModelPart.RIGHT_SLEEVE,
      "jacket_outer",
      PlayerModelPart.JACKET,
      "hat_outer",
      PlayerModelPart.HAT
   );
   private static final MichaelAfkAnimationRenderer.MichaelAfkAnimatable ANIMATABLE = new MichaelAfkAnimationRenderer.MichaelAfkAnimatable();
   private static final Map<UUID, MichaelAfkAnimationRenderer.IdleState> STATES = new HashMap<>();
   private static MichaelAfkAnimationRenderer renderer;
   private static int scanId;
   private static boolean modelChecked;
   private static boolean modelAvailable;

   private MichaelAfkAnimationRenderer(Context context) {
      super(context, new MichaelAfkAnimationRenderer.MichaelAfkModel(), ANIMATABLE);
      this.shadowRadius = 0.5F;
   }

   public static void onAddLayers(AddLayers event) {
      renderer = new MichaelAfkAnimationRenderer(event.getContext());
      MichaelAfkStarsVfxClient.init();
      modelChecked = false;
   }

   public static void onClientTick(Post event) {
      Minecraft minecraft = Minecraft.getInstance();
      if (minecraft.level == null) {
         STATES.clear();
         scanId = 0;
      } else {
         long now = Util.getMillis();
         int currentScan = ++scanId;

         for (AbstractClientPlayer player : minecraft.level.players()) {
            MichaelAfkAnimationRenderer.IdleState state = STATES.computeIfAbsent(player.getUUID(), id -> new MichaelAfkAnimationRenderer.IdleState(player));
            state.lastScan = currentScan;
            if (canAnimate(player) && !state.hasMoved(player)) {
               state.capture(player);
               if (state.idleStartedAtMillis == 0L) {
                  state.idleStartedAtMillis = now;
               }

               if (!state.active && now - state.idleStartedAtMillis >= 5000L) {
                  state.active = true;
                  state.animationStartedAtMillis = now;
                  ANIMATABLE.triggerAnim(player, "michael_afk_controller", "michael_afk");
               }
            } else {
               reset(player, state);
            }
         }

         STATES.entrySet().removeIf(entry -> entry.getValue().lastScan != currentScan);
      }
   }

   public static void onRenderPlayer(Pre event) {
      if (renderer != null && event.getEntity() instanceof AbstractClientPlayer player) {
         MichaelAfkAnimationRenderer.IdleState state = STATES.get(player.getUUID());
         if (state != null && state.active && hasModelResource()) {
            event.setCanceled(true);
            renderer.render(player, player.getYRot(), event.getPartialTick(), event.getPoseStack(), event.getMultiBufferSource(), event.getPackedLight());
         }
      }
   }

   public static boolean isAfkAnimationActive(AbstractClientPlayer player) {
      if (player == null) {
         return false;
      }

      MichaelAfkAnimationRenderer.IdleState state = STATES.get(player.getUUID());
      return state != null && state.active;
   }

   public static long getAnimationTimeMillis(AbstractClientPlayer player) {
      if (player == null) {
         return -1L;
      } else {
         MichaelAfkAnimationRenderer.IdleState state = STATES.get(player.getUUID());
         if (state != null && state.active && state.animationStartedAtMillis != 0L) {
            long elapsed = Math.max(0L, Util.getMillis() - state.animationStartedAtMillis);
            return elapsed % 32458L;
         } else {
            return -1L;
         }
      }
   }

   public static float getAnimationTimeSeconds(AbstractClientPlayer player) {
      long millis = getAnimationTimeMillis(player);
      return millis < 0L ? -1.0F : (float)millis / 1000.0F;
   }

   private static boolean canAnimate(AbstractClientPlayer player) {
      return player.isAlive()
         && !player.isSpectator()
         && player.onGround()
         && !player.isPassenger()
         && !player.isSleeping()
         && !player.isFallFlying()
         && !player.isSwimming()
         && player.getPose() == Pose.STANDING
         && !player.swinging
         && !player.isUsingItem()
         && player.hurtTime <= 0
         && player.getMainHandItem().getItem() instanceof KeyOfSealingItem;
   }

   private static void reset(AbstractClientPlayer player, MichaelAfkAnimationRenderer.IdleState state) {
      if (state.active) {
         ANIMATABLE.stopTriggeredAnim(player, "michael_afk_controller", "michael_afk");
      }

      state.idleStartedAtMillis = 0L;
      state.animationStartedAtMillis = 0L;
      state.active = false;
      state.capture(player);
   }

   private static boolean hasModelResource() {
      if (!modelChecked) {
         modelAvailable = Minecraft.getInstance().getResourceManager().getResource(MODEL_RESOURCE).isPresent();
         modelChecked = true;
      }

      return modelAvailable;
   }

   public ResourceLocation getTextureLocation(MichaelAfkAnimationRenderer.MichaelAfkAnimatable animatable) {
      return WEAPON_TEXTURE_RESOURCE;
   }

   public void renderRecursively(
      PoseStack poseStack,
      MichaelAfkAnimationRenderer.MichaelAfkAnimatable animatable,
      GeoBone bone,
      RenderType renderType,
      MultiBufferSource bufferSource,
      VertexConsumer buffer,
      boolean isReRender,
      float partialTick,
      int packedLight,
      int packedOverlay,
      int renderColor
   ) {
      AbstractClientPlayer player = (AbstractClientPlayer)this.getCurrentEntity();
      if (player != null && PLAYER_BONES.contains(bone.getName())) {
         PlayerModelPart outerPart = OUTER_LAYER_PARTS.get(bone.getName());
         if (outerPart == null || player.isModelPartShown(outerPart)) {
            ResourceLocation playerTexture = player.getSkin().texture();
            RenderType playerRenderType = RenderType.entityCutoutNoCull(playerTexture);
            super.renderRecursively(
               poseStack,
               animatable,
               bone,
               playerRenderType,
               bufferSource,
               bufferSource.getBuffer(playerRenderType),
               isReRender,
               partialTick,
               packedLight,
               packedOverlay,
               renderColor
            );
         }
      } else {
         RenderType weaponRenderType = RenderType.entityCutoutNoCull(WEAPON_TEXTURE_RESOURCE);
         super.renderRecursively(
            poseStack,
            animatable,
            bone,
            weaponRenderType,
            bufferSource,
            bufferSource.getBuffer(weaponRenderType),
            isReRender,
            partialTick,
            packedLight,
            packedOverlay,
            renderColor
         );
      }
   }

   private static final class IdleState {
      private double x;
      private double y;
      private double z;
      private long idleStartedAtMillis;
      private long animationStartedAtMillis;
      private int lastScan;
      private boolean active;

      private IdleState(AbstractClientPlayer player) {
         this.capture(player);
      }

      private boolean hasMoved(AbstractClientPlayer player) {
         double dx = player.getX() - this.x;
         double dy = player.getY() - this.y;
         double dz = player.getZ() - this.z;
         return dx * dx + dy * dy + dz * dz > 1.0E-6;
      }

      private void capture(AbstractClientPlayer player) {
         this.x = player.getX();
         this.y = player.getY();
         this.z = player.getZ();
      }
   }

   public static final class MichaelAfkAnimatable implements GeoReplacedEntity {
      private static final RawAnimation AFK_ANIMATION = RawAnimation.begin().thenLoop("idleanimation");
      private final AnimatableInstanceCache cache = GeckoLibUtil.createInstanceCache(this);

      private MichaelAfkAnimatable() {
         SingletonGeoAnimatable.registerSyncedAnimatable(this);
      }

      public EntityType<?> getReplacingEntityType() {
         return EntityType.PLAYER;
      }

      public void registerControllers(ControllerRegistrar controllers) {
         controllers.add(new AnimationController(this, "michael_afk_controller", 0, state -> PlayState.STOP).triggerableAnim("michael_afk", AFK_ANIMATION));
      }

      public AnimatableInstanceCache getAnimatableInstanceCache() {
         return this.cache;
      }
   }

   private static final class MichaelAfkModel extends GeoModel<MichaelAfkAnimationRenderer.MichaelAfkAnimatable> {
      public ResourceLocation getModelResource(MichaelAfkAnimationRenderer.MichaelAfkAnimatable animatable) {
         return MichaelAfkAnimationRenderer.MODEL_RESOURCE;
      }

      public ResourceLocation getTextureResource(MichaelAfkAnimationRenderer.MichaelAfkAnimatable animatable) {
         return MichaelAfkAnimationRenderer.WEAPON_TEXTURE_RESOURCE;
      }

      public ResourceLocation getAnimationResource(MichaelAfkAnimationRenderer.MichaelAfkAnimatable animatable) {
         return MichaelAfkAnimationRenderer.ANIMATION_RESOURCE;
      }
   }
}
