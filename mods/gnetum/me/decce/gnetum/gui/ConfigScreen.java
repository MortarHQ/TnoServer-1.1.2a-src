package me.decce.gnetum.gui;

import java.time.Duration;
import me.decce.gnetum.Gnetum;
import me.decce.gnetum.PerformanceAnalyzer;
import me.decce.gnetum.gui.widgets.IconButton;
import me.decce.gnetum.gui.widgets.ToggleButton;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.Tooltip;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.resources.language.I18n;
import net.minecraft.network.chat.Component;

public class ConfigScreen extends BaseScreen {
   private Button btnMoreOptions;
   private IconButton btnAnalysis;
   private Button btnModdedPre;
   private Button btnVanilla;
   private Button btnModdedPost;
   private PerformanceAnalyzer.Result analysis;

   public ConfigScreen() {
      this((PerformanceAnalyzer.Result)null);
   }

   public ConfigScreen(Screen parent) {
      super(parent);
   }

   public ConfigScreen(PerformanceAnalyzer.Result analysis) {
      this.analysis = analysis;
   }

   public ConfigScreen(Screen parent, PerformanceAnalyzer.Result analysis) {
      super(parent);
      this.analysis = analysis;
   }

   @Override
   public void init() {
      super.init();
   }

   @Override
   protected void rebuild() {
      super.rebuild();
      ToggleButton btnEnabled = new ToggleButton(
         this.width / 2 - 130, this.height / 2 - 90, 120, 20, Gnetum.config.enabled, () -> I18n.get("gnetum.config.enabled", new Object[0]) + ": %s"
      );
      btnEnabled.setTooltip(Tooltip.create(Component.translatable("gnetum.config.enabled.tooltip")));
      btnEnabled.setTooltipDelay(Duration.ZERO);
      int w1 = this.analysis == null ? 120 : 100;
      this.btnMoreOptions = Button.builder(Component.translatable("gnetum.config.moreOptions"), b -> Minecraft.getInstance().setScreen(new MoreOptionsScreen()))
         .pos(this.width / 2 + 10, this.height / 2 - 90)
         .size(w1, 20)
         .build();
      if (this.analysis != null) {
         this.btnAnalysis = new IconButton(
            this.width / 2 + 10 + 120 - 20,
            this.height / 2 - 90,
            20,
            20,
            Component.empty(),
            b -> Minecraft.getInstance().setScreen(new AnalysisScreen(this.analysis))
         );
         this.btnAnalysis.setIcon(this.analysis.getIcon().icon(), 2, 2, 16, 16);
         this.btnAnalysis.active = !this.analysis.isOutdated();
         Component tooltip = Component.translatable(
            this.analysis.isOutdated()
               ? "gnetum.config.analysis.outdated"
               : (this.analysis.getMessages().isEmpty() ? "gnetum.config.analysis.good" : "gnetum.config.analysis.suboptimal")
         );
         this.btnAnalysis.setTooltip(Tooltip.create(tooltip));
         this.btnAnalysis.setTooltipDelay(Duration.ZERO);
         this.addRenderableWidget(this.btnAnalysis);
      }

      int w = 260;
      int h = 20;
      int margin = 8;
      int x = this.width / 2 - 130;
      int y = this.height / 2 - h / 2 - margin - h;
      this.btnModdedPre = Button.builder(
            Component.translatable("gnetum.config.moddedPre"),
            b -> Minecraft.getInstance().setScreen(new ElementsScreen(Gnetum.config.mapModdedElementsPre, false))
         )
         .pos(x, y)
         .size(w, h)
         .build();
      y += margin + h;
      this.btnVanilla = Button.builder(
            Component.translatable("gnetum.config.vanilla"), b -> Minecraft.getInstance().setScreen(new ElementsScreen(Gnetum.config.mapVanillaElements, true))
         )
         .pos(x, y)
         .size(w, h)
         .build();
      y += margin + h;
      this.btnModdedPost = Button.builder(
            Component.translatable("gnetum.config.moddedPost"),
            b -> Minecraft.getInstance().setScreen(new ElementsScreen(Gnetum.config.mapModdedElementsPost, false))
         )
         .pos(x, y)
         .size(w, h)
         .build();
      this.addRenderableWidget(btnEnabled);
      this.addRenderableWidget(this.btnMoreOptions);
      this.addRenderableWidget(this.btnModdedPre);
      this.addRenderableWidget(this.btnVanilla);
      this.addRenderableWidget(this.btnModdedPost);
      super.addDoneButton();
   }

   public void tick() {
      super.tick();
      this.btnMoreOptions.active = Gnetum.config.isEnabled();
      if (this.btnAnalysis != null) {
         this.btnAnalysis.active = Gnetum.config.isEnabled() && !this.analysis.isOutdated();
         this.btnAnalysis.setTooltipDelay(this.btnAnalysis.active ? Duration.ZERO : Duration.ofMillis(Long.MAX_VALUE));
         if (Gnetum.config.isEnabled() && this.analysis.isOutdated()) {
            this.btnAnalysis.setTooltipDelay(Duration.ZERO);
         }
      }

      this.btnVanilla.active = Gnetum.config.isEnabled();
      this.btnModdedPre.active = Gnetum.config.isEnabled();
      this.btnModdedPost.active = Gnetum.config.isEnabled();
   }

   @Override
   public void close() {
      super.close();
   }
}
