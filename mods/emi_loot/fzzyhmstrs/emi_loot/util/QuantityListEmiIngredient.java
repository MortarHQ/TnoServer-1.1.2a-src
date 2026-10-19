package fzzyhmstrs.emi_loot.util;

import dev.emi.emi.api.render.EmiRender;
import dev.emi.emi.api.render.EmiTooltipComponents;
import dev.emi.emi.api.stack.EmiIngredient;
import dev.emi.emi.api.stack.EmiStack;
import java.util.List;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.tooltip.ClientTooltipComponent;
import org.apache.commons.compress.utils.Lists;

public class QuantityListEmiIngredient implements EmiIngredient {
   private final List<EmiStack> ingredients;

   public QuantityListEmiIngredient(List<EmiStack> ingredients) {
      this.ingredients = ingredients;
   }

   @Override
   public boolean equals(Object obj) {
      return obj instanceof QuantityListEmiIngredient other ? other.getEmiStacks().equals(this.getEmiStacks()) : false;
   }

   @Override
   public int hashCode() {
      return this.ingredients.hashCode();
   }

   public EmiIngredient copy() {
      return new QuantityListEmiIngredient(this.ingredients);
   }

   @Override
   public String toString() {
      return "Ingredient" + this.getEmiStacks();
   }

   public List<EmiStack> getEmiStacks() {
      return this.ingredients;
   }

   public long getAmount() {
      return 1L;
   }

   public EmiIngredient setAmount(long amount) {
      return this;
   }

   public float getChance() {
      return 1.0F;
   }

   public EmiIngredient setChance(float chance) {
      return this;
   }

   public void render(GuiGraphics draw, int x, int y, float delta, int flags) {
      int item = (int)(System.currentTimeMillis() / 1000L % this.ingredients.size());
      EmiIngredient current = (EmiIngredient)this.ingredients.get(item);
      if ((flags & 1) != 0) {
         current.render(draw, x, y, delta, -3);
      }

      if ((flags & 2) != 0) {
         current.render(draw, x, y, delta, 2);
      }

      if ((flags & 4) != 0) {
         EmiRender.renderIngredientIcon(this, draw, x, y);
      }
   }

   public List<ClientTooltipComponent> getTooltip() {
      List<ClientTooltipComponent> tooltip = Lists.newArrayList();
      tooltip.add(ClientTooltipComponent.create(LText.translatable("tooltip.emi.accepts").getVisualOrderText()));
      tooltip.add(EmiTooltipComponents.getIngredientTooltipComponent(this.ingredients));
      int item = (int)(System.currentTimeMillis() / 1000L % this.ingredients.size());
      tooltip.addAll(this.ingredients.get(item).getTooltip());
      return tooltip;
   }
}
