package net.blay09.mods.waystones.client.requirement;

import com.mojang.datafixers.util.Pair;
import java.util.Comparator;
import java.util.List;
import net.blay09.mods.waystones.api.requirement.WarpRequirement;
import net.blay09.mods.waystones.requirement.CombinedRequirement;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.world.entity.player.Player;

public class CombinedRequirementRenderer implements RequirementRenderer<CombinedRequirement> {
   public void renderWidget(Player player, CombinedRequirement requirement, GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTicks, int x, int y) {
      List<Pair<WarpRequirement, RequirementRenderer<WarpRequirement>>> sortedChildren = requirement.getRequirements()
         .stream()
         .filter(it -> !it.isEmpty())
         .map(it -> Pair.of(it, RequirementClientRegistry.getRenderer(it)))
         .sorted(Comparator.comparingInt(it -> ((RequirementRenderer)it.getSecond()).getOrder()))
         .toList();
      int currentX = x;

      for (Pair<WarpRequirement, RequirementRenderer<WarpRequirement>> child : sortedChildren) {
         RequirementRenderer<WarpRequirement> childRenderer = (RequirementRenderer<WarpRequirement>)child.getSecond();
         if (childRenderer != null) {
            childRenderer.renderWidget(player, (WarpRequirement)child.getFirst(), guiGraphics, mouseX, mouseY, partialTicks, currentX, y);
            currentX += 2 + childRenderer.getWidth(player, (WarpRequirement)child.getFirst());
         }
      }
   }

   public int getWidth(Player player, CombinedRequirement requirement) {
      return requirement.getRequirements()
         .stream()
         .filter(it -> !it.isEmpty())
         .map(it -> Pair.of(it, RequirementClientRegistry.getRenderer(it)))
         .filter(it -> it.getSecond() != null)
         .mapToInt(it -> ((RequirementRenderer)it.getSecond()).getWidth(player, (WarpRequirement)it.getFirst()))
         .sum();
   }
}
