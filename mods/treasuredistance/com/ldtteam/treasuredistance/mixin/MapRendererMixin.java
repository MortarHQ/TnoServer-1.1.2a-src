package com.ldtteam.treasuredistance.mixin;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.Font.DisplayMode;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.core.component.DataComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.component.MapDecorations;
import net.minecraft.world.item.component.MapDecorations.Entry;
import net.minecraft.world.level.saveddata.maps.MapDecorationType;
import net.minecraft.world.level.saveddata.maps.MapItemSavedData;
import org.joml.Vector2d;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(targets = "net/minecraft/client/gui/MapRenderer$MapInstance")
public class MapRendererMixin {
   @Shadow
   private MapItemSavedData data;

   @Inject(method = "draw", at = @At("TAIL"))
   public void drawInject(PoseStack p_93292_, MultiBufferSource p_93293_, boolean p_93294_, int p_93295_, CallbackInfo ci) {
      Player player = Minecraft.getInstance().player;
      ItemStack stack = player.getMainHandItem();
      if (stack.getItem() == Items.FILLED_MAP) {
         for (Entry deco : ((MapDecorations)stack.getComponents().get(DataComponents.MAP_DECORATIONS)).decorations().values()) {
            if (((MapDecorationType)deco.type().value()).showOnItemFrame()) {
               double x = deco.x();
               double z = deco.z();
               Vector2d goalVec = new Vector2d(x, z);
               Vector2d targetVec = new Vector2d(player.getX(), player.getZ());
               int dist = (int)goalVec.distance(targetVec);
               Font font = Minecraft.getInstance().font;
               Component component = Component.literal(dist + " Blocks");
               float f6 = font.width(component);
               float f7 = Mth.clamp(25.0F / f6, 0.0F, 0.6666667F);
               p_93292_.pushPose();
               p_93292_.translate(64.0F - f6 * f7 / 2.0F, 68.0F, -0.025F);
               p_93292_.scale(f7, f7, 1.0F);
               p_93292_.translate(0.0F, 0.0F, -0.1F);
               font.drawInBatch(component, 0.0F, 0.0F, -1, false, p_93292_.last().pose(), p_93293_, DisplayMode.NORMAL, Integer.MIN_VALUE, p_93295_);
               p_93292_.popPose();
               break;
            }
         }
      }
   }
}
