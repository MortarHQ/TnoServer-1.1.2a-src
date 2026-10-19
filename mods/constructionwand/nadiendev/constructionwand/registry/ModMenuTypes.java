package nadiendev.constructionwand.registry;

import nadiendev.constructionwand.items.containeritems.MenuVoidSack;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.inventory.MenuType;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.common.extensions.IMenuTypeExtension;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class ModMenuTypes {
   public static final DeferredRegister<MenuType<?>> MENU_TYPES = DeferredRegister.create(Registries.MENU, "constructionwand");
   public static final DeferredHolder<MenuType<?>, MenuType<MenuVoidSack>> VOID_SACK = MENU_TYPES.register(
      "void_sack", () -> IMenuTypeExtension.create((windowId, inv, data) -> {
         InteractionHand hand = data.readBoolean() ? InteractionHand.MAIN_HAND : InteractionHand.OFF_HAND;
         return new MenuVoidSack(windowId, inv, hand);
      })
   );

   public static void register(IEventBus eventBus) {
      MENU_TYPES.register(eventBus);
   }
}
