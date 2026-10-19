package me.decce.gnetum.gui;

import java.time.Duration;
import me.decce.gnetum.Gnetum;
import me.decce.gnetum.GnetumConfig;
import me.decce.gnetum.PerformanceAnalyzer;
import me.decce.gnetum.gui.widgets.IntSlider;
import me.decce.gnetum.gui.widgets.ToggleButton;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.components.AbstractWidget;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.Tooltip;
import net.minecraft.client.resources.language.I18n;
import net.minecraft.network.chat.Component;

public class MoreOptionsScreen extends BaseScreen {
   private static final int BUTTON_WIDTH = 181;
   private static final int BUTTON_HEIGHT = 20;
   private static final int MARGIN = 5;
   private int count;
   private int y;

   @Override
   public void rebuild() {
      super.rebuild();
      this.count = 0;
      this.y = this.height / 2 - 10 - 5 - 20 - 5 - 20 - 15;
      ToggleButton btnShowFps = new ToggleButton(0, 0, 181, 20, Gnetum.config.showHudFps, () -> I18n.get("gnetum.config.showFps", new Object[0]) + ": %s");
      btnShowFps.setTooltip(Tooltip.create(Component.translatable("gnetum.config.showFps.tooltip")));
      btnShowFps.setTooltipDelay(Duration.ZERO);
      ToggleButton btnDownscale = new ToggleButton(0, 0, 181, 20, Gnetum.config.downscale, () -> I18n.get("gnetum.config.downscale", new Object[0]) + ": %s");
      btnDownscale.setTooltip(Tooltip.create(Component.translatable("gnetum.config.downscale.tooltip")));
      btnDownscale.setTooltipDelay(Duration.ZERO);
      IntSlider sliderNumberOfPasses = new IntSlider(
         0,
         0,
         181,
         20,
         () -> I18n.get("gnetum.config.numberOfPasses", new Object[0]) + ": %s",
         2,
         10,
         Gnetum.config.numberOfPasses,
         true,
         i -> Gnetum.config.numberOfPasses = i
      );
      sliderNumberOfPasses.setTooltip(Tooltip.create(Component.translatable("gnetum.config.numberOfPasses.tooltip")));
      sliderNumberOfPasses.setTooltipDelay(Duration.ZERO);
      IntSlider sliderMaxFps = new IntSlider(
         0,
         0,
         181,
         20,
         () -> I18n.get("gnetum.config.maxFps", new Object[0]) + ": %s fps",
         5,
         125,
         Gnetum.config.getRawMaxFps(),
         5,
         true,
         i -> Gnetum.config.setMaxFps(i),
         i -> i == 125,
         () -> I18n.get("gnetum.config.maxFps", new Object[0]) + ": Unlimited"
      );
      sliderMaxFps.setTooltip(Tooltip.create(Component.translatable("gnetum.config.maxFps.tooltip")));
      sliderMaxFps.setTooltipDelay(Duration.ZERO);
      IntSlider sliderScreenMaxFps = new IntSlider(
         0,
         0,
         181,
         20,
         () -> I18n.get("gnetum.config.screenMaxFps", new Object[0]) + ": %s fps",
         5,
         65,
         Gnetum.config.screenMaxFps,
         5,
         true,
         i -> Gnetum.config.screenMaxFps = i,
         i -> i == 65,
         () -> I18n.get("gnetum.config.screenMaxFps", new Object[0]) + ": Unlimited"
      );
      sliderScreenMaxFps.setTooltip(Tooltip.create(Component.translatable("gnetum.config.screenMaxFps.tooltip")));
      sliderScreenMaxFps.setTooltipDelay(Duration.ZERO);
      Button btnReset = Button.builder(
            Component.translatable("gnetum.config.reset"),
            b -> Minecraft.getInstance()
               .setScreen(new ConfirmationScreen(() -> new ConfigScreen(null, PerformanceAnalyzer.latestAnalysisResult), () -> this, GnetumConfig::reset))
         )
         .size(181, 20)
         .build();
      this.add(btnShowFps);
      this.add(btnDownscale);
      this.add(sliderNumberOfPasses);
      this.add(sliderMaxFps);
      this.add(sliderScreenMaxFps);
      this.add(btnReset);
      super.addDoneButton();
   }

   private void add(AbstractWidget widget) {
      int x = this.count % 2 == 0 ? this.width / 2 - 2 - 181 : this.width / 2 + 2;
      widget.setX(x);
      widget.setY(this.y);
      this.addRenderableWidget(widget);
      if (this.count % 2 == 1) {
         this.y += 25;
      }

      this.count++;
   }
}
