package com.github.b4ndithelps.tennogamenolife.client;

import com.github.b4ndithelps.tennogamenolife.client.model.XiubiArmorModel;
import com.github.b4ndithelps.tennogamenolife.client.model.XiubiHaloModel;
import com.github.b4ndithelps.tennogamenolife.client.renderer.layer.PlayerExMachinaArmorLayer;
import com.github.b4ndithelps.tennogamenolife.client.renderer.layer.PlayerExMachinaHaloLayer;
import com.github.b4ndithelps.tennogamenolife.client.renderer.layer.PlayerFlugelWingsLayer;
import java.util.Collections;
import java.util.Map;
import java.util.Set;
import java.util.WeakHashMap;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.player.PlayerRenderer;
import net.minecraft.client.resources.PlayerSkin.Model;
import net.minecraft.world.entity.player.Player;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.tick.PlayerTickEvent.Post;

@EventBusSubscriber(modid = "tennogamenolife", value = Dist.CLIENT)
public final class TenreincarnationClientForge {
   private static final Set<PlayerRenderer> INITIALIZED_RENDERERS = Collections.newSetFromMap(new WeakHashMap<>());

   private TenreincarnationClientForge() {
   }

   @SubscribeEvent
   public static void onClientTick(Post event) {
      if (event.getEntity().level().isClientSide) {
         Minecraft minecraft = Minecraft.getInstance();
         if (event.getEntity() == minecraft.player) {
            if (minecraft.getEntityRenderDispatcher() != null) {
               Map<Model, EntityRenderer<? extends Player>> skinMap = minecraft.getEntityRenderDispatcher().getSkinMap();
               if (!skinMap.isEmpty()) {
                  skinMap.forEach((skin, renderer) -> {
                     if (renderer instanceof PlayerRenderer playerRenderer && INITIALIZED_RENDERERS.add(playerRenderer)) {
                        XiubiArmorModel armorModel = new XiubiArmorModel(minecraft.getEntityModels().bakeLayer(XiubiArmorModel.LAYER_LOCATION));
                        XiubiHaloModel haloModel = new XiubiHaloModel(minecraft.getEntityModels().bakeLayer(XiubiHaloModel.LAYER_LOCATION));
                        playerRenderer.addLayer(new PlayerExMachinaArmorLayer(playerRenderer, armorModel));
                        playerRenderer.addLayer(new PlayerExMachinaHaloLayer(playerRenderer, haloModel));
                        playerRenderer.addLayer(new PlayerFlugelWingsLayer(playerRenderer));
                     }
                  });
               }
            }
         }
      }
   }
}
