package it.hurts.sskirillss.relics.client.gui.layers;

import it.hurts.sskirillss.relics.tiles.base.IHasHUDInfo;
import net.minecraft.client.DeltaTracker;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.LayeredDraw.Layer;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.core.BlockPos;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.HitResult.Type;

public class InfoTileLayer implements Layer {
   public void render(GuiGraphics guiGraphics, DeltaTracker deltaTracker) {
      Minecraft MC = Minecraft.getInstance();
      ClientLevel level = MC.level;
      if (level != null) {
         HitResult hit = MC.hitResult;
         if (hit != null && hit.getType() == Type.BLOCK) {
            BlockPos pos = ((BlockHitResult)MC.hitResult).getBlockPos();
            if (level.getBlockEntity(pos) instanceof IHasHUDInfo infoTile) {
               infoTile.renderHUDInfo(guiGraphics.pose(), MC.getWindow());
            }
         }
      }
   }
}
