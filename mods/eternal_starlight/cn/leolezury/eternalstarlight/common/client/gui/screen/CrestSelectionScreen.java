package cn.leolezury.eternalstarlight.common.client.gui.screen;

import cn.leolezury.eternalstarlight.common.client.gui.screen.widget.CrestButton;
import cn.leolezury.eternalstarlight.common.client.gui.screen.widget.CrestPageButton;
import cn.leolezury.eternalstarlight.common.client.shader.ESShaders;
import cn.leolezury.eternalstarlight.common.crest.Crest;
import cn.leolezury.eternalstarlight.common.network.UpdateCrestsPacket;
import cn.leolezury.eternalstarlight.common.platform.ESClientPlatform;
import com.mojang.blaze3d.platform.Window;
import com.mojang.blaze3d.shaders.Uniform;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.BufferBuilder;
import com.mojang.blaze3d.vertex.BufferUploader;
import com.mojang.blaze3d.vertex.DefaultVertexFormat;
import com.mojang.blaze3d.vertex.Tesselator;
import com.mojang.blaze3d.vertex.VertexFormat.Mode;
import java.util.ArrayList;
import java.util.List;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.renderer.ShaderInstance;
import net.minecraft.network.chat.Component;
import org.joml.Matrix4f;

public class CrestSelectionScreen extends Screen {
   private final List<CrestButton> crestButtons = new ArrayList<>();
   private final List<Crest.Instance> ownedCrests;
   private List<Crest.Instance> crests;
   private CrestButton selectedCrestButton;
   private CrestPageButton nextPage;
   private CrestPageButton previousPage;
   private Crest.Instance selectedCrest;
   private int selected;
   private int tickCount;

   public CrestSelectionScreen(List<Crest.Instance> crests, List<Crest.Instance> ownedCrests) {
      super(Component.empty());
      this.crests = crests;
      this.ownedCrests = ownedCrests;
   }

   protected void init() {
      this.previousPage = (CrestPageButton)this.addRenderableWidget(
         new CrestPageButton(this.width / 4 - 24, this.height / 2 - 12 - 50, 48, 24, false, Component.empty(), button -> this.previousPage())
      );
      this.nextPage = (CrestPageButton)this.addRenderableWidget(
         new CrestPageButton(this.width / 4 - 24, this.height / 2 - 12 + 50, 48, 24, true, Component.empty(), button -> this.nextPage())
      );
      List<Crest.Instance> crests = this.crests == null ? this.crestButtons.stream().map(CrestButton::getCrest).toList() : this.crests;
      this.crestButtons.clear();

      for (int i = 0; i < 5; i++) {
         int ordinal = i;
         CrestButton crestButton = (CrestButton)this.addRenderableWidget(
            new CrestButton((int)(this.width / 9.0F * 5.0F), (int)(this.height / 2.0F), 72, 72, true, Component.empty(), button -> this.cancelCrest(ordinal))
               .setCrest(i >= crests.size() ? null : crests.get(i))
         );
         this.crestButtons.add(crestButton);
      }

      this.selectedCrestButton = (CrestButton)this.addRenderableWidget(
         new CrestButton(this.width / 4 - 36, this.height / 2 - 36, 72, 72, Component.empty(), button -> this.selectCrest())
      );
      this.updateGui();
      this.crests = null;
   }

   public void tick() {
      for (CrestButton button : this.crestButtons) {
         button.tick();
      }

      this.selectedCrestButton.tick();
      this.nextPage.tick();
      this.previousPage.tick();
      this.updateGui();
      this.tickCount++;
   }

   public void updateGui() {
      if (!this.ownedCrests.isEmpty()) {
         this.nextPage.active = this.selected < this.ownedCrests.size() - 1;
         this.previousPage.active = this.selected > 0;
         this.selectedCrest = this.ownedCrests.get(this.selected);
         this.selectedCrestButton.setCrest(this.selectedCrest);
      } else {
         this.nextPage.active = false;
         this.previousPage.active = false;
         this.selectedCrest = null;
         this.selectedCrestButton.setCrest(null);
         this.selectedCrestButton.active = false;
      }

      List<CrestButton> notEmptyButtons = this.crestButtons.stream().filter(crestButtonx -> !crestButtonx.isEmpty()).toList();

      for (int n = 0; n < notEmptyButtons.size(); n++) {
         CrestButton crestButton = this.crestButtons.get(n);
         crestButton.setOrbitCenter(this.width / 9.0F * 5.0F, this.height / 2.0F);
         crestButton.setAngle(360.0F / notEmptyButtons.size() * n + this.tickCount);
      }
   }

   private void previousPage() {
      if (this.selected > 0) {
         this.selected--;
         this.updateGui();
      }
   }

   private void nextPage() {
      if (this.selected < this.ownedCrests.size() - 1) {
         this.selected++;
         this.updateGui();
      }
   }

   private void cancelCrest(int ordinal) {
      if (!this.crestButtons.get(ordinal).isEmpty()) {
         List<Crest.Instance> crests = new ArrayList<>();
         this.crestButtons.get(ordinal).setCrest(null);

         for (CrestButton crestButton : this.crestButtons) {
            if (!crestButton.isEmpty()) {
               crests.add(crestButton.getCrest());
            }
         }

         for (int i = 0; i < this.crestButtons.size(); i++) {
            CrestButton crestButton = this.crestButtons.get(i);
            crestButton.setCrest(i >= crests.size() ? null : crests.get(i));
         }
      }
   }

   private void selectCrest() {
      if (!((Crest)this.selectedCrest.crest().value()).attributeModifiers().isEmpty() || !((Crest)this.selectedCrest.crest().value()).effects().isEmpty()) {
         if (!this.crestButtons
            .stream()
            .anyMatch(
               crestButtonx -> !crestButtonx.isEmpty()
                  && ((Crest)crestButtonx.getCrest().crest().value()).type() == ((Crest)this.selectedCrest.crest().value()).type()
            )) {
            for (CrestButton crestButton : this.crestButtons) {
               if (crestButton.isEmpty()) {
                  crestButton.setCrest(this.selectedCrest);
                  return;
               }
            }
         }
      }
   }

   public void render(GuiGraphics guiGraphics, int i, int j, float f) {
      Minecraft client = Minecraft.getInstance();
      Window window = client.getWindow();
      int x = window.getGuiScaledWidth();
      int y = window.getGuiScaledHeight();
      Matrix4f matrix4f = guiGraphics.pose().last().pose();
      ShaderInstance instance = ESShaders.getCrestSelectionGui();
      if (instance != null) {
         Uniform tickUniform = instance.getUniform("TickCount");
         Uniform ratioUniform = instance.getUniform("Ratio");
         if (tickUniform != null) {
            tickUniform.set(
               this.tickCount
                  + Minecraft.getInstance()
                     .getTimer()
                     .getGameTimeDeltaPartialTick(Minecraft.getInstance().level != null && Minecraft.getInstance().level.tickRateManager().runsNormally())
            );
         }

         if (ratioUniform != null) {
            ratioUniform.set((float)y / x);
         }
      }

      RenderSystem.setShader(ESShaders::getCrestSelectionGui);
      BufferBuilder bufferBuilder = Tesselator.getInstance().begin(Mode.QUADS, DefaultVertexFormat.POSITION_TEX);
      bufferBuilder.addVertex(matrix4f, 0.0F, 0.0F, 0.0F).setUv(0.0F, 0.0F);
      bufferBuilder.addVertex(matrix4f, 0.0F, y, 0.0F).setUv(0.0F, 1.0F);
      bufferBuilder.addVertex(matrix4f, x, y, 0.0F).setUv(1.0F, 1.0F);
      bufferBuilder.addVertex(matrix4f, x, 0.0F, 0.0F).setUv(1.0F, 0.0F);
      BufferUploader.drawWithShader(bufferBuilder.buildOrThrow());
      super.render(guiGraphics, i, j, f);
   }

   public void renderBackground(GuiGraphics guiGraphics, int i, int j, float f) {
   }

   public void onClose() {
      super.onClose();
      List<Crest.Instance> newCrests = new ArrayList<>();

      for (CrestButton button : this.crestButtons) {
         if (!button.isEmpty()) {
            newCrests.add(button.getCrest());
         }
      }

      ESClientPlatform.INSTANCE.sendToServer(new UpdateCrestsPacket(newCrests));
   }
}
