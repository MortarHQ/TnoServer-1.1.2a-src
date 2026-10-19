package dev.ftb.mods.ftblibrary.sidebar;

import dev.architectury.platform.Platform;
import dev.ftb.mods.ftblibrary.api.sidebar.ButtonOverlayRender;
import dev.ftb.mods.ftblibrary.api.sidebar.SidebarButton;
import dev.ftb.mods.ftblibrary.ui.GuiHelper;
import dev.ftb.mods.ftblibrary.ui.misc.LoadingScreen;
import dev.ftb.mods.ftblibrary.util.ChainedBooleanSupplier;
import dev.ftb.mods.ftblibrary.util.client.ClientUtils;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.BooleanSupplier;
import java.util.function.Supplier;
import net.minecraft.Util;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;

public class RegisteredSidebarButton implements SidebarButton {
   private final SidebarButtonData data;
   private final ResourceLocation id;
   private final String langKey;
   private final Component tooltip;
   private final List<ButtonOverlayRender> extraRenderers;
   private Supplier<List<Component>> tooltipOverride;
   private ChainedBooleanSupplier visible = ChainedBooleanSupplier.TRUE;
   private boolean forceHidden = false;

   public RegisteredSidebarButton(ResourceLocation id, SidebarButtonData data) {
      this.id = id;
      this.data = data;
      this.langKey = Util.makeDescriptionId("sidebar_button", id);
      this.tooltip = Component.translatable(this.langKey + ".tooltip");
      if (data.requiresOp()) {
         this.addVisibilityCondition(ClientUtils.IS_CLIENT_OP);
      }

      data.requiredMods().ifPresent(mods -> this.addVisibilityCondition(() -> mods.stream().allMatch(Platform::isModLoaded)));
      this.extraRenderers = new ArrayList<>();
      if (!data.envCondition().isEmpty()) {
         if (data.envCondition().startsWith("!")) {
            this.addVisibilityCondition(() -> !System.getenv().containsKey(data.envCondition().substring(1)));
         } else {
            this.addVisibilityCondition(() -> System.getenv().containsKey(data.envCondition()));
         }
      }
   }

   public SidebarButtonData getData() {
      return this.data;
   }

   @Override
   public ResourceLocation getId() {
      return this.id;
   }

   public String getLangKey() {
      return this.langKey;
   }

   public List<Component> getTooltip(boolean shift) {
      if (this.tooltipOverride != null) {
         return this.tooltipOverride.get();
      }

      List<Component> tooltips = new ArrayList<>();
      tooltips.add(Component.translatable(this.langKey));
      if (shift) {
         tooltips.add(this.tooltip);
      }

      Optional<List<Component>> components = shift ? this.data.shiftTooltip() : this.data.tooltip();
      components.ifPresent(tooltips::addAll);
      return tooltips;
   }

   public void clickButton(boolean shift) {
      if (this.data.loadingScreen()) {
         new LoadingScreen(Component.translatable(this.getLangKey())).openGui();
      }

      boolean canShift = shift && this.data.shiftClickEvent().isPresent();

      for (String event : canShift ? this.data.shiftClickEvent().get() : this.data.clickEvents()) {
         GuiHelper.BLANK_GUI.handleClick(event);
      }
   }

   public boolean canSee() {
      return !this.forceHidden && this.visible.getAsBoolean();
   }

   public void setForceHidden(boolean forceHidden) {
      this.forceHidden = forceHidden;
   }

   @Override
   public void addVisibilityCondition(BooleanSupplier condition) {
      this.visible = this.visible.and(condition);
   }

   @Override
   public void addOverlayRender(ButtonOverlayRender renderer) {
      this.extraRenderers.add(renderer);
   }

   @Override
   public void setTooltipOverride(Supplier<List<Component>> tooltipOverride) {
      this.tooltipOverride = tooltipOverride;
   }

   public List<ButtonOverlayRender> getExtraRenderers() {
      return this.extraRenderers;
   }
}
