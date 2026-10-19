package com.faboslav.friendsandfoes.common.init;

import com.faboslav.friendsandfoes.common.client.render.entity.renderer.CopperGolemEntityRenderer;
import com.faboslav.friendsandfoes.common.client.render.entity.renderer.CrabEntityRenderer;
import com.faboslav.friendsandfoes.common.client.render.entity.renderer.GlareEntityRenderer;
import com.faboslav.friendsandfoes.common.client.render.entity.renderer.IceologerEntityRenderer;
import com.faboslav.friendsandfoes.common.client.render.entity.renderer.IceologerIceChunkRenderer;
import com.faboslav.friendsandfoes.common.client.render.entity.renderer.IllusionerEntityRenderer;
import com.faboslav.friendsandfoes.common.client.render.entity.renderer.MaulerEntityRenderer;
import com.faboslav.friendsandfoes.common.client.render.entity.renderer.MoobloomEntityRenderer;
import com.faboslav.friendsandfoes.common.client.render.entity.renderer.PlayerIllusionEntityRenderer;
import com.faboslav.friendsandfoes.common.client.render.entity.renderer.RascalEntityRenderer;
import com.faboslav.friendsandfoes.common.client.render.entity.renderer.TuffGolemEntityRenderer;
import com.faboslav.friendsandfoes.common.client.render.entity.renderer.WildfireEntityRenderer;
import com.faboslav.friendsandfoes.common.entity.PlayerIllusionEntity;
import com.faboslav.friendsandfoes.common.events.client.RegisterEntityRenderersEvent;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableMap.Builder;
import java.util.Map;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.EntityRendererProvider.Context;
import net.minecraft.client.resources.PlayerSkin.Model;
import net.minecraft.world.entity.EntityType;

public final class FriendsAndFoesEntityRenderers {
   private static final Map<Model, EntityRendererProvider<PlayerIllusionEntity>> PLAYER_ILLUSION_RENDERER_FACTORIES = Map.of(
      Model.WIDE, context -> new PlayerIllusionEntityRenderer(context, false), Model.SLIM, context -> new PlayerIllusionEntityRenderer(context, true)
   );

   public static void registerEntityRenderers(RegisterEntityRenderersEvent event) {
      event.register((EntityType)FriendsAndFoesEntityTypes.COPPER_GOLEM.get(), CopperGolemEntityRenderer::new);
      event.register((EntityType)FriendsAndFoesEntityTypes.CRAB.get(), CrabEntityRenderer::new);
      event.register((EntityType)FriendsAndFoesEntityTypes.GLARE.get(), GlareEntityRenderer::new);
      event.register((EntityType)FriendsAndFoesEntityTypes.ICEOLOGER.get(), IceologerEntityRenderer::new);
      event.register((EntityType)FriendsAndFoesEntityTypes.ICE_CHUNK.get(), IceologerIceChunkRenderer::new);
      event.register((EntityType)FriendsAndFoesEntityTypes.ILLUSIONER.get(), IllusionerEntityRenderer::new);
      event.register((EntityType)FriendsAndFoesEntityTypes.MAULER.get(), MaulerEntityRenderer::new);
      event.register((EntityType)FriendsAndFoesEntityTypes.MOOBLOOM.get(), MoobloomEntityRenderer::new);
      event.register((EntityType)FriendsAndFoesEntityTypes.RASCAL.get(), RascalEntityRenderer::new);
      event.register((EntityType)FriendsAndFoesEntityTypes.TUFF_GOLEM.get(), TuffGolemEntityRenderer::new);
      event.register((EntityType)FriendsAndFoesEntityTypes.WILDFIRE.get(), WildfireEntityRenderer::new);
   }

   public static Map<Model, EntityRenderer<? extends PlayerIllusionEntity>> reloadPlayerIllusionRenderers(Context ctx) {
      Builder builder = ImmutableMap.builder();
      PLAYER_ILLUSION_RENDERER_FACTORIES.forEach((model, factory) -> {
         try {
            builder.put(model, factory.create(ctx));
         } catch (Exception exception) {
            throw new IllegalArgumentException("Failed to create player illusion model for " + model, exception);
         }
      });
      return builder.build();
   }

   private FriendsAndFoesEntityRenderers() {
   }
}
