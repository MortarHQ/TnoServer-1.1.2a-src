package team.creative.creativecore.common.gui.manager;

import java.util.function.Function;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.gui.GuiGraphics;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import team.creative.creativecore.common.gui.GuiLayer;
import team.creative.creativecore.common.util.registry.NamedHandlerRegistry;

public abstract class GuiManager {
   public static final NamedHandlerRegistry<GuiManager.GuiManagerType> REGISTRY = new NamedHandlerRegistry<>(null);
   public static final GuiManager.GuiManagerType<GuiManagerItem> ITEM = register("item", GuiManagerItem.class, GuiManagerItem::new);
   public final GuiLayer layer;

   public static <T extends GuiManager> GuiManager.GuiManagerType<T> register(String name, Class<T> managerClass, Function<GuiLayer, T> factory) {
      GuiManager.GuiManagerType<T> type = new GuiManager.GuiManagerType<>(name, managerClass, factory);
      REGISTRY.register(name, type);
      return type;
   }

   public GuiManager(GuiLayer layer) {
      this.layer = layer;
   }

   @OnlyIn(Dist.CLIENT)
   @Environment(EnvType.CLIENT)
   public void renderOverlay(GuiGraphics graphics, GuiLayer layer, int mouseX, int mouseY) {
   }

   public void mouseReleased(double x, double y, int button) {
   }

   public void mouseClickedOutside(double x, double y) {
   }

   public void tick() {
   }

   public void closed() {
   }

   public record GuiManagerType<T extends GuiManager>(String name, Class<T> managerClass, Function<GuiLayer, T> factory) {
   }
}
