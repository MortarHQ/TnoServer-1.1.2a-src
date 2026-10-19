package io.github.Memoires.mysticism.client.renderer.entity.monster;

import io.github.Memoires.mysticism.entity.golems.ClayGolemEntity;
import io.github.manasmods.tensura.client.entity.human.PlayerLikeModel;
import io.github.manasmods.tensura.client.entity.human.PlayerLikeRenderer;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.renderer.entity.EntityRendererProvider.Context;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.TamableAnimal;
import org.jetbrains.annotations.NotNull;

public class MagisteelGolemRenderer<T extends TamableAnimal> extends PlayerLikeRenderer<T> {
   public MagisteelGolemRenderer(Context pContext, boolean slim) {
      super(pContext, new PlayerLikeModel(pContext.bakeLayer(slim ? ModelLayers.PLAYER_SLIM : ModelLayers.PLAYER), slim), 0.5F);
   }

   @NotNull
   public ResourceLocation getTextureLocation(T entity) {
      return entity instanceof ClayGolemEntity
         ? ResourceLocation.fromNamespaceAndPath("mysticism", "textures/entity/golem/clay_golem.png")
         : ResourceLocation.fromNamespaceAndPath("mysticism", "textures/entity/golem/clay_golem.png");
   }
}
