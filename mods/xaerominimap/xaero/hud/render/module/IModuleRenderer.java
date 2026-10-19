package xaero.hud.render.module;

import net.minecraft.client.gui.GuiGraphics;
import xaero.hud.module.ModuleSession;

public interface IModuleRenderer<MS extends ModuleSession<MS>> {
   void render(MS var1, ModuleRenderContext var2, GuiGraphics var3, float var4);
}
