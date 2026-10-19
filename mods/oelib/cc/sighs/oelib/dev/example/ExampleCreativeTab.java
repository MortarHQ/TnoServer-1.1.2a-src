package cc.sighs.oelib.dev.example;

import cc.sighs.oelib.registry.DeferredRegister;
import cc.sighs.oelib.registry.RegisterSupplier;
import cc.sighs.oelib.registry.extra.CreativeTabRegister;
import net.minecraft.client.Minecraft;
import net.minecraft.core.Registry;
import net.minecraft.core.RegistryAccess;
import net.minecraft.core.Holder.Reference;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.CreativeModeTab.Row;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.ItemLike;

public class ExampleCreativeTab {
   public static final DeferredRegister<CreativeModeTab> TABS = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, "oelib");
   public static final RegisterSupplier<CreativeModeTab> GENERAL = TABS.register(
      "general",
      () -> CreativeModeTab.builder(Row.TOP, 0)
         .title(Component.translatable("itemGroup.oelib.general"))
         .icon(() -> new ItemStack((ItemLike)ExampleRegistry.EXAMPLE_ITEM.get()))
         .displayItems((params, output) -> output.accept((ItemLike)ExampleRegistry.EXAMPLE_BLOCK_ITEM.get()))
         .build()
   );

   public static void init() {
      TABS.register();
   }

   public static void registerCreativeTabEntries() {
      CreativeTabRegister.appendStack(GENERAL.key(), () -> {
         Minecraft mc = Minecraft.getInstance();
         RegistryAccess registryAccess = mc.level.registryAccess();
         Registry<Enchantment> enchantReg = registryAccess.registryOrThrow(Registries.ENCHANTMENT);
         Reference<Enchantment> sharpness = (Reference<Enchantment>)enchantReg.getHolder(Enchantments.SHARPNESS).orElse(null);
         ItemStack sword = new ItemStack((ItemLike)ExampleRegistry.EXAMPLE_ITEM.get());
         sword.enchant(sharpness, 5);
         return sword;
      });
   }

   public static void modifyCreativeTab() {
      CreativeTabRegister.modify(CreativeModeTabs.COMBAT, (flags, output, canUseGameMasterBlocks) -> {
         ItemStack item = new ItemStack((ItemLike)ExampleRegistry.EXAMPLE_ITEM.get());
         ItemStack sword = new ItemStack(Items.NETHERITE_SWORD);
         output.acceptAfter(sword, item);
      });
   }
}
