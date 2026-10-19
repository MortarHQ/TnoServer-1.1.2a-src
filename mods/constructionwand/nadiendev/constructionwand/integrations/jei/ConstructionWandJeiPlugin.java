package nadiendev.constructionwand.integrations.jei;

import com.mojang.blaze3d.platform.InputConstants;
import javax.annotation.Nonnull;
import mezz.jei.api.IModPlugin;
import mezz.jei.api.JeiPlugin;
import mezz.jei.api.constants.VanillaTypes;
import mezz.jei.api.registration.IRecipeRegistration;
import nadiendev.constructionwand.basics.ConfigClient;
import nadiendev.constructionwand.basics.ConfigServer;
import nadiendev.constructionwand.client.KeybindHandler;
import nadiendev.constructionwand.items.ModItems;
import net.minecraft.ChatFormatting;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ItemLike;
import net.neoforged.neoforge.registries.DeferredItem;
import org.jetbrains.annotations.NotNull;

@JeiPlugin
public class ConstructionWandJeiPlugin implements IModPlugin {
   private static final ResourceLocation pluginId = ResourceLocation.fromNamespaceAndPath("constructionwand", "constructionwand");
   private static final String baseKey = "constructionwand.description.";
   private static final String baseKeyItem = "item.constructionwand.";

   @Nonnull
   public ResourceLocation getPluginUid() {
      return pluginId;
   }

   private Component keyComboComponent(boolean shiftOpt, Component optkeyComponent) {
      String key = shiftOpt ? "sneak_opt" : "sneak";
      return Component.translatable("constructionwand.description.key." + key, new Object[]{optkeyComponent}).withStyle(ChatFormatting.BLUE);
   }

   public void registerRecipes(@NotNull IRecipeRegistration registration) {
      Component optkeyComponent = Component.translatable(InputConstants.getKey((Integer)ConfigClient.OPT_KEY.get(), -1).getName())
         .withStyle(ChatFormatting.BLUE);
      Component wandModeComponent = this.keyComboComponent((Boolean)ConfigClient.SHIFTOPT_MODE.get(), optkeyComponent);
      Component wandGuiComponent = this.keyComboComponent((Boolean)ConfigClient.SHIFTOPT_GUI.get(), optkeyComponent);

      for (DeferredItem<Item> wandSupplier : ModItems.WANDS) {
         Item wand = (Item)wandSupplier.get();
         ConfigServer.WandProperties wandProperties = ConfigServer.getWandProperties(wand);
         String durabilityKey = wand == ModItems.WAND_INFINITY.get() ? "unlimited" : "limited";
         Component durabilityComponent = Component.translatable(
            "constructionwand.description.durability." + durabilityKey, new Object[]{wandProperties.getDurability()}
         );
         registration.addIngredientInfo(
            new ItemStack(wand),
            VanillaTypes.ITEM_STACK,
            new Component[]{
               Component.translatable(
                  "constructionwand.description.wand",
                  new Object[]{
                     Component.translatable("item.constructionwand." + BuiltInRegistries.ITEM.getKey(wand).getPath()),
                     wandProperties.getLimit(),
                     durabilityComponent,
                     optkeyComponent,
                     wandModeComponent,
                     wandGuiComponent
                  }
               )
            }
         );
      }

      for (DeferredItem<Item> coreSupplier : ModItems.CORES) {
         Item core = (Item)coreSupplier.get();
         registration.addIngredientInfo(
            new ItemStack(core),
            VanillaTypes.ITEM_STACK,
            new Component[]{
               Component.translatable("constructionwand.description." + BuiltInRegistries.ITEM.getKey(core).getPath())
                  .append("\n\n")
                  .append(Component.translatable("constructionwand.description.core", new Object[]{wandModeComponent}))
            }
         );
      }

      Component mKeyComponent = KeybindHandler.KEY_VOID_SACK_TOGGLE.getTranslatedKeyMessage().copy().withStyle(ChatFormatting.GOLD);
      registration.addIngredientInfo(
         new ItemStack((ItemLike)ModItems.VOID_SACK.get()),
         VanillaTypes.ITEM_STACK,
         new Component[]{Component.translatable("constructionwand.description.void_sack", new Object[]{mKeyComponent})}
      );
   }
}
