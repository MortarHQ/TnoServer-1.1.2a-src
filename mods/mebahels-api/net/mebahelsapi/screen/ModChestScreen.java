package net.mebahelsapi.screen;

import com.mojang.blaze3d.systems.RenderSystem;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.mebahelsapi.screenhandler.ModChestScreenHandler;
import net.minecraft.class_1661;
import net.minecraft.class_2561;
import net.minecraft.class_2960;
import net.minecraft.class_332;
import net.minecraft.class_465;
import net.minecraft.class_757;

@Environment(EnvType.CLIENT)
public class ModChestScreen extends class_465<ModChestScreenHandler> {
   private static final class_2960 TEXTURE = class_2960.method_60655("mebahelsapi", "textures/gui/chest_screen.png");

   public ModChestScreen(ModChestScreenHandler handler, class_1661 inventory, class_2561 title) {
      super(handler, inventory, title);
      this.field_2792 = 176;
      this.field_2779 = 222;
   }

   protected void method_25426() {
      super.method_25426();
      this.field_25267 = 8;
      this.field_25268 = 6;
      this.field_25269 = 8;
      this.field_25270 = this.field_2779 - 130;
   }

   protected void method_2389(class_332 context, float delta, int mouseX, int mouseY) {
      RenderSystem.setShader(class_757::method_34542);
      RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
      RenderSystem.setShaderTexture(0, TEXTURE);
      int x = (this.field_22789 - this.field_2792) / 2;
      int y = (this.field_22790 - this.field_2779) / 2;
      context.method_25302(TEXTURE, x, y, 0, 0, this.field_2792, this.field_2779);
   }

   public void method_25394(class_332 context, int mouseX, int mouseY, float delta) {
      this.method_25420(context, mouseX, mouseY, delta);
      super.method_25394(context, mouseX, mouseY, delta);
      this.method_2380(context, mouseX, mouseY);
   }
}
