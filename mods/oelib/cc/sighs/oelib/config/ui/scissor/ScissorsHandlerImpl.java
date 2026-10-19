package cc.sighs.oelib.config.ui.scissor;

import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.platform.Window;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import net.minecraft.client.Minecraft;

public final class ScissorsHandlerImpl implements ScissorsHandler {
   private final List<Rectangle> scissorsAreas = new ArrayList<>();

   @Override
   public void clearScissors() {
      this.scissorsAreas.clear();
      this.applyScissors();
   }

   @Override
   public List<Rectangle> getScissorsAreas() {
      return Collections.unmodifiableList(this.scissorsAreas);
   }

   @Override
   public void scissor(Rectangle rectangle) {
      this.scissorsAreas.add(new Rectangle(rectangle));
      this.applyScissors();
   }

   @Override
   public void removeLastScissor() {
      if (!this.scissorsAreas.isEmpty()) {
         this.scissorsAreas.removeLast();
      }

      this.applyScissors();
   }

   @Override
   public void applyScissors() {
      if (!this.scissorsAreas.isEmpty()) {
         Rectangle r = this.scissorsAreas.getFirst().clone();

         for (int i = 1; i < this.scissorsAreas.size(); i++) {
            Rectangle other = this.scissorsAreas.get(i);
            if (!r.intersects(other)) {
               this._applyScissor(null);
               return;
            }

            r.setBounds(r.intersection(other));
         }

         r.setBounds(Math.min(r.x, r.x + r.width), Math.min(r.y, r.y + r.height), Math.abs(r.width), Math.abs(r.height));
         this._applyScissor(r);
      } else {
         this._applyScissor(null);
      }
   }

   private void _applyScissor(Rectangle r) {
      if (r != null) {
         GlStateManager._enableScissorTest();
         if (r.isEmpty()) {
            GlStateManager._scissorBox(0, 0, 0, 0);
         } else {
            Window window = Minecraft.getInstance().getWindow();
            double scaleFactor = window.getGuiScale();
            int x = (int)(r.x * scaleFactor);
            int y = (int)((window.getGuiScaledHeight() - r.height - r.y) * scaleFactor);
            int w = (int)(r.width * scaleFactor);
            int h = (int)(r.height * scaleFactor);
            GlStateManager._scissorBox(x, y, w, h);
         }
      } else {
         GlStateManager._disableScissorTest();
      }
   }
}
