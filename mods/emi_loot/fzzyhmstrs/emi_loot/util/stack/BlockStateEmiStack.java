package fzzyhmstrs.emi_loot.util.stack;

import dev.emi.emi.api.render.EmiTooltipComponents;
import dev.emi.emi.api.stack.EmiStack;
import fzzyhmstrs.emi_loot.EMILootClientAgnos;
import java.util.ArrayList;
import java.util.List;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.tooltip.ClientTooltipComponent;
import net.minecraft.core.component.DataComponentPatch;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;

public class BlockStateEmiStack extends EmiStack {
   private final BlockState state;
   private final ResourceLocation id;

   public BlockStateEmiStack(BlockState state, ResourceLocation id) {
      this.state = state;
      this.id = id;
   }

   public EmiStack copy() {
      return new BlockStateEmiStack(this.state, this.id);
   }

   public void render(GuiGraphics draw, int x, int y, float delta, int flags) {
      EMILootClientAgnos.renderBlock(this.state, draw, x, y, delta);
   }

   public boolean isEmpty() {
      return false;
   }

   public DataComponentPatch getComponentChanges() {
      return DataComponentPatch.EMPTY;
   }

   public Object getKey() {
      return this.state;
   }

   public ResourceLocation getId() {
      return this.id;
   }

   public List<Component> getTooltipText() {
      return List.of(this.state.getBlock().getName());
   }

   public List<ClientTooltipComponent> getTooltip() {
      ArrayList<ClientTooltipComponent> list = new ArrayList<>();
      list.add(EmiTooltipComponents.of(this.state.getBlock().getName()));
      EmiTooltipComponents.appendModName(list, this.id.getNamespace());
      return list;
   }

   public Component getName() {
      return ((Block)BuiltInRegistries.BLOCK.get(this.id)).getName();
   }
}
