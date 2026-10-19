package com.github.wal_bos.moreskills.client.renderer;

import com.github.wal_bos.moreskills.client.model.TheCoordinateModel;
import com.github.wal_bos.moreskills.registry.entity.ModEntityTypes;
import com.github.wal_bos.moreskills.world.entity.FeldwayAngelEntity;
import com.github.wal_bos.moreskills.world.entity.FeldwayAwakenedEntity;
import com.github.wal_bos.moreskills.world.entity.FeldwayAwakeningEntity;
import com.github.wal_bos.moreskills.world.entity.FeldwayEntity;
import com.github.wal_bos.moreskills.world.entity.MichaelManasEntity;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.renderer.entity.HumanoidMobRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider.Context;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.neoforged.neoforge.client.event.EntityRenderersEvent.RegisterLayerDefinitions;
import net.neoforged.neoforge.client.event.EntityRenderersEvent.RegisterRenderers;

public final class ModEntityRenderers {
   private static final ResourceLocation FELDWAY_TEX = ResourceLocation.fromNamespaceAndPath("tensuramoreskills", "textures/entity/feldway.png");
   private static final ResourceLocation ANGEL_TEX = ResourceLocation.fromNamespaceAndPath("tensuramoreskills", "textures/entity/feldway_angel.png");
   private static final ResourceLocation MICHAEL_TEX = ResourceLocation.fromNamespaceAndPath("tensuramoreskills", "textures/entity/michael_manas.png");

   private ModEntityRenderers() {
   }

   public static void registerRenderers(RegisterRenderers event) {
      event.registerEntityRenderer((EntityType)ModEntityTypes.FELDWAY.get(), ModEntityRenderers.FeldwayRenderer::new);
      event.registerEntityRenderer((EntityType)ModEntityTypes.FELDWAY_ANGEL.get(), ModEntityRenderers.FeldwayAngelRenderer::new);
      event.registerEntityRenderer((EntityType)ModEntityTypes.MICHAEL_MANAS.get(), ModEntityRenderers.MichaelRenderer::new);
      event.registerEntityRenderer((EntityType)ModEntityTypes.FELDWAY_AWAKENING.get(), ModEntityRenderers.FeldwayAwakeningRenderer::new);
      event.registerEntityRenderer((EntityType)ModEntityTypes.FELDWAY_AWAKENED.get(), ModEntityRenderers.FeldwayAwakenedRenderer::new);
      event.registerEntityRenderer((EntityType)ModEntityTypes.THE_COORDINATE.get(), TheCoordinateRenderer::new);
      event.registerEntityRenderer((EntityType)ModEntityTypes.ARS_WEISS_CLONE.get(), ArsWeissCloneRenderer::new);
      event.registerEntityRenderer((EntityType)ModEntityTypes.AVALON_NOVARI.get(), InvisibleEntityRenderer::new);
      event.registerEntityRenderer((EntityType)ModEntityTypes.AVALON_NOVARI_TRANSITION.get(), InvisibleEntityRenderer::new);
      event.registerEntityRenderer((EntityType)ModEntityTypes.MICHAEL_METEOR.get(), MichaelMeteorRenderer::new);
      event.registerEntityRenderer((EntityType)ModEntityTypes.BERETTA.get(), BerettaRenderer::new);
      event.registerEntityRenderer((EntityType)ModEntityTypes.AVALON_KNIGHT.get(), AvalonKnightRenderer::new);
   }

   public static void registerLayerDefinitions(RegisterLayerDefinitions event) {
      event.registerLayerDefinition(TheCoordinateModel.LAYER_LOCATION, TheCoordinateModel::createBodyLayer);
   }

   public static class FeldwayAngelRenderer extends HumanoidMobRenderer<FeldwayAngelEntity, HumanoidModel<FeldwayAngelEntity>> {
      public FeldwayAngelRenderer(Context ctx) {
         super(ctx, new HumanoidModel(ctx.bakeLayer(ModelLayers.PLAYER)), 0.6F);
      }

      public ResourceLocation getTextureLocation(FeldwayAngelEntity entity) {
         return ModEntityRenderers.ANGEL_TEX;
      }
   }

   public static class FeldwayAwakenedRenderer extends HumanoidMobRenderer<FeldwayAwakenedEntity, HumanoidModel<FeldwayAwakenedEntity>> {
      public FeldwayAwakenedRenderer(Context ctx) {
         super(ctx, new HumanoidModel(ctx.bakeLayer(ModelLayers.PLAYER)), 1.05F);
      }

      public ResourceLocation getTextureLocation(FeldwayAwakenedEntity entity) {
         return ModEntityRenderers.FELDWAY_TEX;
      }
   }

   public static class FeldwayAwakeningRenderer extends HumanoidMobRenderer<FeldwayAwakeningEntity, HumanoidModel<FeldwayAwakeningEntity>> {
      public FeldwayAwakeningRenderer(Context ctx) {
         super(ctx, new HumanoidModel(ctx.bakeLayer(ModelLayers.PLAYER)), 0.95F);
      }

      public ResourceLocation getTextureLocation(FeldwayAwakeningEntity entity) {
         return ModEntityRenderers.FELDWAY_TEX;
      }
   }

   public static class FeldwayRenderer extends HumanoidMobRenderer<FeldwayEntity, HumanoidModel<FeldwayEntity>> {
      public FeldwayRenderer(Context ctx) {
         super(ctx, new HumanoidModel(ctx.bakeLayer(ModelLayers.PLAYER)), 0.8F);
      }

      public ResourceLocation getTextureLocation(FeldwayEntity entity) {
         return ModEntityRenderers.FELDWAY_TEX;
      }
   }

   public static class MichaelRenderer extends HumanoidMobRenderer<MichaelManasEntity, HumanoidModel<MichaelManasEntity>> {
      public MichaelRenderer(Context ctx) {
         super(ctx, new HumanoidModel(ctx.bakeLayer(ModelLayers.PLAYER)), 0.7F);
      }

      public ResourceLocation getTextureLocation(MichaelManasEntity entity) {
         return ModEntityRenderers.MICHAEL_TEX;
      }
   }
}
