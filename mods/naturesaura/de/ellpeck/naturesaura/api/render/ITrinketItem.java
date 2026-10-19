package de.ellpeck.naturesaura.api.render;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;

public interface ITrinketItem {
   @OnlyIn(Dist.CLIENT)
   void render(ItemStack var1, Player var2, ITrinketItem.RenderType var3, PoseStack var4, MultiBufferSource var5, int var6, boolean var7);

   enum RenderType {
      HEAD,
      BODY;
   }
}
