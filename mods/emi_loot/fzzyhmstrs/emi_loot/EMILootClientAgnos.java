package fzzyhmstrs.emi_loot;

import dev.emi.emi.api.recipe.EmiRecipe;
import fzzyhmstrs.emi_loot.client.ClientBuiltPool;
import fzzyhmstrs.emi_loot.util.IconEmiWidget;
import fzzyhmstrs.emi_loot.util.IconGroupEmiWidget;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;
import net.minecraft.world.level.block.state.BlockState;

public abstract class EMILootClientAgnos {
   public static EMILootClientAgnos delegate;

   public static IconEmiWidget createIconEmiWidget(int x, int y, int keyIndex, Component text) {
      return delegate.createIconEmiWidgetAgnos(x, y, keyIndex, text);
   }

   protected abstract IconEmiWidget createIconEmiWidgetAgnos(int var1, int var2, int var3, Component var4);

   public static IconGroupEmiWidget createIconGroupEmiWidget(int x, int y, ClientBuiltPool pool, EmiRecipe recipe) {
      return delegate.createIconGroupEmiWidgetAgnos(x, y, pool, recipe);
   }

   protected abstract IconGroupEmiWidget createIconGroupEmiWidgetAgnos(int var1, int var2, ClientBuiltPool var3, EmiRecipe var4);

   public static void renderBlock(BlockState blockState, GuiGraphics draw, int x, int y, float delta) {
      delegate.renderBlockAgnos(blockState, draw, x, y, delta);
   }

   protected abstract void renderBlockAgnos(BlockState var1, GuiGraphics var2, int var3, int var4, float var5);

   static {
      try {
         Class.forName("fzzyhmstrs.emi_loot.fabric.EMILootClientAgnosFabric");
      } catch (Throwable var2) {
      }

      try {
         Class.forName("fzzyhmstrs.emi_loot.neoforge.EMILootClientAgnosNeoForge");
      } catch (Throwable var1) {
      }
   }
}
