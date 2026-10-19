package me.decce.gnetum.gui;

import me.decce.gnetum.PerformanceAnalyzer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.MultiLineTextWidget;
import net.minecraft.client.resources.language.I18n;
import net.minecraft.network.chat.Component;

public class AnalysisScreen extends BaseScreen {
   public final PerformanceAnalyzer.Result analysis;
   private MultiLineTextWidget txt;

   public AnalysisScreen(PerformanceAnalyzer.Result analysis) {
      this.analysis = analysis;
   }

   @Override
   public void close() {
      Minecraft.getInstance().setScreen(super.parent);
   }

   @Override
   protected void rebuild() {
      super.rebuild();
      String body;
      if (this.analysis.getMessages().isEmpty()) {
         body = I18n.get("gnetum.config.analysis.good", new Object[0]);
      } else {
         body = String.join("\n\n", this.analysis.getMessages());
      }

      StringBuilder data = new StringBuilder();
      long[] durations = this.analysis.getDurations();
      if (durations == null) {
         data.append(I18n.get("gnetum.config.analysis.failedDuration", new Object[0]));
      } else {
         data.append(I18n.get("gnetum.config.analysis.detailDuration", new Object[0]));

         for (int i = 1; i < durations.length; i++) {
            data.append("\n");
            data.append("Pass [").append(i).append("] - ").append(String.format("%.2f", durations[i] / 1000.0)).append("μs");
         }
      }

      String str = body + "\n\n" + data.toString();
      int w = 300;
      int h = 150;
      this.txt = new MultiLineTextWidget(this.width / 2 - w / 2, this.height / 2 - h / 2, Component.literal(str), this.font);
      this.txt.setMaxWidth(w);
      this.addRenderableWidget(this.txt);
      super.addDoneButton();
   }

   @Override
   public void render(GuiGraphics graphics, int mouseX, int mouseY, float partialTick) {
      super.render(graphics, mouseX, mouseY, partialTick);
      String title = I18n.get(
         this.analysis.getIcon() == PerformanceAnalyzer.ResultIcon.TICK ? "gnetum.config.analysis.title.good" : "gnetum.config.analysis.title.suboptimal",
         new Object[0]
      );
      int titleWidth = this.font.width(title);
      int xfont = this.width / 2 - titleWidth / 2 + 8 + 2;
      int xicon = xfont - 4 - 16;
      int y = this.height / 2 - 90;
      graphics.blit(this.analysis.getIcon().icon(), xicon, y - 8, 16, 16, 0.0F, 0.0F, 16, 16, 16, 16);
      graphics.drawString(this.font, title, xfont, y, 16777215);
   }
}
