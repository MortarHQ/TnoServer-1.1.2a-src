package twilightforest.client.model.entity;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.world.item.ItemDisplayContext;

public interface TrophyBlockModel {
   void setupRotationsForTrophy(float var1, float var2, float var3, float var4);

   void renderTrophy(PoseStack var1, MultiBufferSource var2, int var3, int var4, int var5, ItemDisplayContext var6);
}
