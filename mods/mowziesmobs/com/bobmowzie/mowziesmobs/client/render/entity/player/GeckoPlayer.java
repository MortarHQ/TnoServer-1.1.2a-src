package com.bobmowzie.mowziesmobs.client.render.entity.player;

import com.bobmowzie.mowziesmobs.client.model.entity.ModelGeckoPlayerFirstPerson;
import com.bobmowzie.mowziesmobs.client.model.entity.ModelGeckoPlayerThirdPerson;
import com.bobmowzie.mowziesmobs.client.model.tools.geckolib.MowzieAnimationController;
import com.bobmowzie.mowziesmobs.client.model.tools.geckolib.MowzieGeoModel;
import com.bobmowzie.mowziesmobs.server.capability.AbilityData;
import com.bobmowzie.mowziesmobs.server.capability.DataHandler;
import com.bobmowzie.mowziesmobs.server.capability.PlayerData;
import javax.annotation.Nullable;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.model.geom.EntityModelSet;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.client.renderer.entity.EntityRenderDispatcher;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider.Context;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.world.entity.player.Player;
import software.bernie.geckolib.animatable.GeoEntity;
import software.bernie.geckolib.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.animation.AnimationState;
import software.bernie.geckolib.animation.PlayState;
import software.bernie.geckolib.animation.RawAnimation;
import software.bernie.geckolib.animation.AnimatableManager.ControllerRegistrar;
import software.bernie.geckolib.renderer.GeoRenderer;
import software.bernie.geckolib.util.GeckoLibUtil;

public abstract class GeckoPlayer implements GeoEntity {
   protected GeoRenderer<GeckoPlayer> renderer;
   protected MowzieGeoModel<GeckoPlayer> model;
   protected MowzieAnimationController<GeckoPlayer> controller;
   private int tickTimer = 0;
   private Player player;
   private final AnimatableInstanceCache cache = GeckoLibUtil.createInstanceCache(this);
   public static final String THIRD_PERSON_CONTROLLER_NAME = "thirdPersonAnimation";
   public static final String FIRST_PERSON_CONTROLLER_NAME = "firstPersonAnimation";
   private static RawAnimation IDLE_ANIMATION = RawAnimation.begin().thenLoop("idle");

   public GeckoPlayer(Player player) {
      this.player = player;
      this.setup(player);
   }

   public void registerControllers(ControllerRegistrar controllers) {
      this.controller = new MowzieAnimationController(this, this.getControllerName(), 0, this::predicate, 0.0);
      controllers.add(this.controller);
   }

   public MowzieAnimationController<GeckoPlayer> getController() {
      return this.controller;
   }

   public Player getPlayer() {
      return this.player;
   }

   public void tick() {
      this.tickTimer++;
   }

   public double getTick(Object entity) {
      return ((GeckoPlayer)entity).tickTimer;
   }

   public <E extends GeoEntity> PlayState predicate(AnimationState<E> e) {
      e.getController().transitionLength(0);
      Player player = this.getPlayer();
      if (player == null) {
         return PlayState.STOP;
      }

      AbilityData abilityData = DataHandler.getData(player, DataHandler.ABILITY_DATA);
      if (abilityData == null) {
         return PlayState.STOP;
      }

      if (abilityData.getActiveAbility() != null) {
         return abilityData.animationPredicate(e, this.getPerspective());
      }

      e.getController().setAnimation(IDLE_ANIMATION);
      return PlayState.CONTINUE;
   }

   @Nullable
   public static GeckoPlayer getGeckoPlayer(Player player, GeckoPlayer.Perspective perspective) {
      return perspective == GeckoPlayer.Perspective.FIRST_PERSON
         ? GeckoFirstPersonRenderer.GECKO_PLAYER_FIRST_PERSON
         : DataHandler.<PlayerData>getData(player, DataHandler.PLAYER_DATA).getGeckoPlayer();
   }

   public static MowzieAnimationController<GeckoPlayer> getAnimationController(Player player, GeckoPlayer.Perspective perspective) {
      GeckoPlayer geckoPlayer;
      if (perspective == GeckoPlayer.Perspective.FIRST_PERSON) {
         geckoPlayer = GeckoFirstPersonRenderer.GECKO_PLAYER_FIRST_PERSON;
      } else {
         geckoPlayer = DataHandler.<PlayerData>getData(player, DataHandler.PLAYER_DATA).getGeckoPlayer();
      }

      return geckoPlayer != null ? geckoPlayer.controller : null;
   }

   public GeoRenderer<GeckoPlayer> getPlayerRenderer() {
      return this.renderer;
   }

   public MowzieGeoModel<GeckoPlayer> getModel() {
      return this.model;
   }

   public abstract String getControllerName();

   public abstract GeckoPlayer.Perspective getPerspective();

   public abstract void setup(Player var1);

   public AnimatableInstanceCache getAnimatableInstanceCache() {
      return this.cache;
   }

   public static class GeckoPlayerFirstPerson extends GeckoPlayer {
      public GeckoPlayerFirstPerson(Player player) {
         super(player);
      }

      @Override
      public String getControllerName() {
         return "firstPersonAnimation";
      }

      @Override
      public GeckoPlayer.Perspective getPerspective() {
         return GeckoPlayer.Perspective.FIRST_PERSON;
      }

      @Override
      public void setup(Player player) {
         ModelGeckoPlayerFirstPerson modelGeckoPlayer = new ModelGeckoPlayerFirstPerson();
         this.model = modelGeckoPlayer;
         GeckoFirstPersonRenderer geckoRenderer = new GeckoFirstPersonRenderer(Minecraft.getInstance(), modelGeckoPlayer);
         this.renderer = geckoRenderer;
         if (!geckoRenderer.getModelsToLoad().containsKey(this.getClass())) {
            geckoRenderer.getModelsToLoad().put((Class<? extends GeckoPlayer>)this.getClass(), geckoRenderer);
         }

         this.getAnimatableInstanceCache().getManagerForId(this.renderer.getInstanceId(this));
         this.getController().setLastModel(this.model);
         GeckoFirstPersonRenderer.GECKO_PLAYER_FIRST_PERSON = this;
      }
   }

   public static class GeckoPlayerThirdPerson extends GeckoPlayer {
      public static GeckoRenderPlayer GECKO_RENDERER_THIRD_PERSON_NORMAL;
      public static ModelGeckoPlayerThirdPerson GECKO_MODEL_THIRD_PERSON_NORMAL;
      public static GeckoRenderPlayer GECKO_RENDERER_THIRD_PERSON_SLIM;
      public static ModelGeckoPlayerThirdPerson GECKO_MODEL_THIRD_PERSON_SLIM;
      protected GeoRenderer<GeckoPlayer> rendererSlim;
      protected MowzieGeoModel<GeckoPlayer> modelSlim;

      public GeckoPlayerThirdPerson(Player player) {
         super(player);
      }

      @Override
      public String getControllerName() {
         return "thirdPersonAnimation";
      }

      @Override
      public GeckoPlayer.Perspective getPerspective() {
         return GeckoPlayer.Perspective.THIRD_PERSON;
      }

      @Override
      public void setup(Player player) {
         this.model = GECKO_MODEL_THIRD_PERSON_NORMAL;
         this.renderer = GECKO_RENDERER_THIRD_PERSON_NORMAL;
         this.modelSlim = GECKO_MODEL_THIRD_PERSON_SLIM;
         this.rendererSlim = GECKO_RENDERER_THIRD_PERSON_SLIM;
         this.getAnimatableInstanceCache().getManagerForId(this.renderer.getInstanceId(this));
         this.getController().setLastModel(this.model);
      }

      public boolean isPlayerSlim() {
         return ((AbstractClientPlayer)this.getPlayer()).getSkin().model().name().equals("SLIM");
      }

      @Override
      public MowzieGeoModel<GeckoPlayer> getModel() {
         return this.isPlayerSlim() ? this.modelSlim : this.model;
      }

      @Override
      public GeoRenderer<GeckoPlayer> getPlayerRenderer() {
         return this.isPlayerSlim() ? this.rendererSlim : this.renderer;
      }

      public static void initRenderer() {
         GECKO_MODEL_THIRD_PERSON_NORMAL = new ModelGeckoPlayerThirdPerson();
         GECKO_MODEL_THIRD_PERSON_SLIM = new ModelGeckoPlayerThirdPerson();
         GECKO_MODEL_THIRD_PERSON_SLIM.setUseSmallArms(true);
         Minecraft minecraft = Minecraft.getInstance();
         EntityRenderDispatcher dispatcher = minecraft.getEntityRenderDispatcher();
         ItemRenderer itemRenderer = minecraft.getItemRenderer();
         ResourceManager resourceManager = minecraft.getResourceManager();
         EntityModelSet entityModelSet = minecraft.getEntityModels();
         Font font = minecraft.font;
         Context context = new Context(
            dispatcher, itemRenderer, minecraft.getBlockRenderer(), dispatcher.getItemInHandRenderer(), resourceManager, entityModelSet, font
         );
         GeckoRenderPlayer geckoRenderer = new GeckoRenderPlayer(context, false, GECKO_MODEL_THIRD_PERSON_NORMAL);
         if (!geckoRenderer.getModelsToLoad().containsKey(GeckoPlayer.GeckoPlayerThirdPerson.class)) {
            geckoRenderer.getModelsToLoad().put(GeckoPlayer.GeckoPlayerThirdPerson.class, geckoRenderer);
         }

         GECKO_RENDERER_THIRD_PERSON_NORMAL = geckoRenderer;
         GeckoRenderPlayer geckoRendererSlim = new GeckoRenderPlayer(context, true, GECKO_MODEL_THIRD_PERSON_SLIM);
         if (!geckoRendererSlim.getModelsToLoad().containsKey(GeckoPlayer.GeckoPlayerThirdPerson.class)) {
            geckoRendererSlim.getModelsToLoad().put(GeckoPlayer.GeckoPlayerThirdPerson.class, geckoRendererSlim);
         }

         GECKO_RENDERER_THIRD_PERSON_SLIM = geckoRendererSlim;
      }
   }

   public enum Perspective {
      FIRST_PERSON,
      THIRD_PERSON;
   }
}
