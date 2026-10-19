package com.bobmowzie.mowziesmobs.client;

import com.bobmowzie.mowziesmobs.MMCommon;
import com.bobmowzie.mowziesmobs.client.render.entity.FrozenRenderHandler;
import com.bobmowzie.mowziesmobs.client.render.entity.layer.SunblockLayer;
import com.bobmowzie.mowziesmobs.client.render.entity.player.GeckoPlayer;
import com.bobmowzie.mowziesmobs.server.entity.MowzieEntity;
import com.google.common.collect.ImmutableList;
import java.util.List;
import java.util.stream.Collectors;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.client.resources.PlayerSkin.Model;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.client.event.EntityRenderersEvent.AddLayers;

public class ClientLayerRegistry {
   @SubscribeEvent
   public static void onAddLayers(AddLayers event) {
      List<EntityType<? extends LivingEntity>> entityTypes = ImmutableList.copyOf(MMCommon.getLivingEntityTypes().collect(Collectors.toList()));
      entityTypes.forEach(entityType -> addLayerIfApplicable((EntityType<? extends LivingEntity>)entityType, event));

      for (Model skin : event.getSkins()) {
         if (event.getSkin(skin) instanceof LivingEntityRenderer<?, ?> renderer) {
            renderer.addLayer(new FrozenRenderHandler.LayerFrozen(renderer));
            renderer.addLayer(new SunblockLayer(renderer));
         }
      }

      GeckoPlayer.GeckoPlayerThirdPerson.initRenderer();
   }

   private static void addLayerIfApplicable(EntityType<? extends LivingEntity> entityType, AddLayers event) {
      LivingEntityRenderer<?, ?> renderer = null;
      if (entityType != EntityType.ENDER_DRAGON) {
         try {
            if (event.getRenderer(entityType) instanceof LivingEntityRenderer<?, ?> livingRenderer) {
               renderer = livingRenderer;
            }
         } catch (Exception e) {
            if (!entityType.getBaseClass().isAssignableFrom(MowzieEntity.class)) {
               MMCommon.LOGGER.warn("Could not apply layer to " + entityType.getDescriptionId() + ", has custom renderer that is not LivingEntityRenderer.");
            }
         }

         if (renderer != null) {
            renderer.addLayer(new FrozenRenderHandler.LayerFrozen(renderer));
            renderer.addLayer(new SunblockLayer(renderer));
         }
      }
   }
}
