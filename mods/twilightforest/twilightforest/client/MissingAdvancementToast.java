package twilightforest.client;

import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.toasts.Toast;
import net.minecraft.client.gui.components.toasts.ToastComponent;
import net.minecraft.client.gui.components.toasts.Toast.Visibility;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ItemLike;
import twilightforest.init.TFBlocks;

public record MissingAdvancementToast(Component title, ItemStack icon) implements Toast {
   public static final MissingAdvancementToast FALLBACK = new MissingAdvancementToast(
      Component.translatable("misc.twilightforest.advancement_hidden"), new ItemStack((ItemLike)TFBlocks.TWILIGHT_PORTAL_MINIATURE_STRUCTURE.get())
   );
   private static final Component UPPER_TEXT = Component.translatable("misc.twilightforest.advancement_required");
   private static final ResourceLocation BACKGROUND_SPRITE = ResourceLocation.withDefaultNamespace("toast/advancement");

   public Visibility render(GuiGraphics graphics, ToastComponent component, long timer) {
      graphics.blitSprite(BACKGROUND_SPRITE, 0, 0, this.width(), this.height());
      graphics.renderFakeItem(this.icon(), 6, 8);
      graphics.drawString(component.getMinecraft().font, UPPER_TEXT, 25, 7, -1, false);
      graphics.drawString(component.getMinecraft().font, this.title(), 25, 18, 16777215, false);
      return timer >= 10000L ? Visibility.HIDE : Visibility.SHOW;
   }
}
