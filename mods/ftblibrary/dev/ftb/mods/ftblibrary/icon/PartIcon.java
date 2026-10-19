package dev.ftb.mods.ftblibrary.icon;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import net.minecraft.client.gui.GuiGraphics;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;

public class PartIcon extends IconWithParent {
   public final Icon parent;
   public int textureWidth;
   public int textureHeight;
   public int textureU;
   public int textureV;
   public int corner;
   public int subWidth;
   public int subHeight;
   private Icon all;
   private Icon middleU;
   private Icon middleD;
   private Icon middleL;
   private Icon middleR;
   private Icon cornerNN;
   private Icon cornerPN;
   private Icon cornerNP;
   private Icon cornerPP;
   private Icon center;

   public PartIcon(Icon icon, int textureU, int textureV, int subWidth, int subHeight, int corner, int textureWidth, int textureHeight) {
      super(icon);
      this.parent = icon;
      this.textureWidth = textureWidth;
      this.textureHeight = textureHeight;
      this.textureU = textureU;
      this.textureV = textureV;
      this.subWidth = subWidth;
      this.subHeight = subHeight;
      this.corner = corner;
      this.updateParts();
   }

   public PartIcon(Icon icon, int x, int y, int w, int h, int c) {
      this(icon, x, y, w, h, c, 256, 256);
   }

   public PartIcon(String iconId, int textureU, int textureV, int subWidth, int subHeight, int corner, int textureWidth, int textureHeight) {
      this(Icon.getIcon(iconId), textureU, textureV, subWidth, subHeight, corner, textureWidth, textureHeight);
   }

   public PartIcon(Icon icon) {
      this(icon, 0, 0, 256, 256, 6);
   }

   public static PartIcon wholeTexture(String textureId, int textureWidth, int textureHeight, int corner) {
      return new PartIcon(textureId, 0, 0, textureWidth, textureHeight, corner, textureWidth, textureHeight);
   }

   public PartIcon setTextureSize(int w, int h) {
      this.textureWidth = w;
      this.textureHeight = h;
      return this;
   }

   private Icon get(int x, int y, int w, int h) {
      return this.parent.withUV(this.textureU + x, this.textureV + y, w, h, this.textureWidth, this.textureHeight);
   }

   public void updateParts() {
      int mw = this.subWidth - this.corner * 2;
      int mh = this.subHeight - this.corner * 2;
      this.all = this.get(0, 0, this.subWidth, this.subHeight);
      this.middleU = this.get(this.corner, 0, mw, this.corner);
      this.middleD = this.get(this.corner, this.subHeight - this.corner, mw, this.corner);
      this.middleL = this.get(0, this.corner, this.corner, mh);
      this.middleR = this.get(this.subWidth - this.corner, this.corner, this.corner, mh);
      this.cornerNN = this.get(0, 0, this.corner, this.corner);
      this.cornerPN = this.get(this.subWidth - this.corner, 0, this.corner, this.corner);
      this.cornerNP = this.get(0, this.subHeight - this.corner, this.corner, this.corner);
      this.cornerPP = this.get(this.subWidth - this.corner, this.subHeight - this.corner, this.corner, this.corner);
      this.center = this.get(this.corner, this.corner, mw, mh);
   }

   public PartIcon copy() {
      PartIcon icon = new PartIcon(this.parent.copy());
      icon.textureU = this.textureU;
      icon.textureV = this.textureV;
      icon.subWidth = this.subWidth;
      icon.subHeight = this.subHeight;
      icon.corner = this.corner;
      icon.textureWidth = this.textureWidth;
      icon.textureHeight = this.textureHeight;
      return icon;
   }

   @Override
   protected void setProperties(IconProperties properties) {
      super.setProperties(properties);
      this.textureU = properties.getInt("x", this.textureU);
      this.textureV = properties.getInt("y", this.textureV);
      this.subWidth = properties.getInt("width", this.subHeight);
      this.subHeight = properties.getInt("height", this.subHeight);
      this.corner = properties.getInt("corner", this.corner);
      this.textureWidth = properties.getInt("texture_w", this.textureWidth);
      this.textureHeight = properties.getInt("texture_h", this.textureHeight);
      String s = properties.getString("pos", "");
      if (!s.isEmpty()) {
         String[] s1 = s.split(",", 4);
         if (s1.length == 4) {
            this.textureU = Integer.parseInt(s1[0]);
            this.textureV = Integer.parseInt(s1[1]);
            this.subWidth = Integer.parseInt(s1[2]);
            this.subHeight = Integer.parseInt(s1[3]);
         }
      }

      this.updateParts();
   }

   @OnlyIn(Dist.CLIENT)
   @Override
   public void draw(GuiGraphics graphics, int x, int y, int w, int h) {
      if (w == this.subWidth && h == this.subHeight) {
         this.all.draw(graphics, x, y, w, h);
      } else {
         int c = this.corner;
         int mw = w - c * 2;
         int mh = h - c * 2;
         this.middleU.draw(graphics, x + c, y, mw, c);
         this.middleR.draw(graphics, x + w - c, y + c, c, mh);
         this.middleD.draw(graphics, x + c, y + h - c, mw, c);
         this.middleL.draw(graphics, x, y + c, c, mh);
         this.cornerNN.draw(graphics, x, y, c, c);
         this.cornerNP.draw(graphics, x, y + h - c, c, c);
         this.cornerPN.draw(graphics, x + w - c, y, c, c);
         this.cornerPP.draw(graphics, x + w - c, y + h - c, c, c);
         this.center.draw(graphics, x + c, y + c, mw, mh);
      }
   }

   @Override
   public JsonElement getJson() {
      JsonObject json = new JsonObject();
      json.addProperty("id", "part");
      json.add("parent", this.parent.getJson());
      json.addProperty("x", this.textureU);
      json.addProperty("y", this.textureV);
      json.addProperty("width", this.subWidth);
      json.addProperty("height", this.subHeight);
      json.addProperty("corner", this.corner);
      json.addProperty("texture_width", this.textureWidth);
      json.addProperty("texture_height", this.textureHeight);
      return json;
   }
}
