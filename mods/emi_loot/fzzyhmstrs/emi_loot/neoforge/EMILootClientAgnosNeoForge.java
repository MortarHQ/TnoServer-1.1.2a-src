package fzzyhmstrs.emi_loot.neoforge;

import dev.emi.emi.api.recipe.EmiRecipe;
import fzzyhmstrs.emi_loot.EMILootClientAgnos;
import fzzyhmstrs.emi_loot.client.ClientBuiltPool;
import fzzyhmstrs.emi_loot.neoforge.util.BlockRendererImpl;
import fzzyhmstrs.emi_loot.neoforge.util.IconEmiWidgetImpl;
import fzzyhmstrs.emi_loot.neoforge.util.IconGroupEmiWidgetImpl;
import fzzyhmstrs.emi_loot.util.IconEmiWidget;
import fzzyhmstrs.emi_loot.util.IconGroupEmiWidget;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;
import net.minecraft.world.level.block.state.BlockState;

public class EMILootClientAgnosNeoForge extends EMILootClientAgnos {
   @Override
   protected IconEmiWidget createIconEmiWidgetAgnos(int x, int y, int keyIndex, Component text) {
      return new IconEmiWidgetImpl(x, y, keyIndex, text);
   }

   @Override
   protected IconGroupEmiWidget createIconGroupEmiWidgetAgnos(int x, int y, ClientBuiltPool pool, EmiRecipe recipe) {
      return new IconGroupEmiWidgetImpl(x, y, pool, recipe);
   }

   @Override
   protected void renderBlockAgnos(BlockState blockState, GuiGraphics draw, int x, int y, float delta) {
      BlockRendererImpl.render(blockState, draw, x, y, delta);
   }

   static {
      EMILootClientAgnos.delegate = new EMILootClientAgnosNeoForge();
   }
}
