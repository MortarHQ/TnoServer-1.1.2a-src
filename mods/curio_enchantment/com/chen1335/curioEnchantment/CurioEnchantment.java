package com.chen1335.curioEnchantment;

import com.chen1335.curioEnchantment.API.objects.AttachmentTypes;
import com.chen1335.curioEnchantment.API.objects.CETags;
import com.chen1335.curioEnchantment.API.objects.DataComponentTypes;
import com.chen1335.curioEnchantment.common.EventHandler;
import com.mojang.logging.LogUtils;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.EnchantedBookItem;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentInstance;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.ModList;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;
import org.slf4j.Logger;

@Mod("curio_enchantment")
public class CurioEnchantment {
   public static final String MODID = "curio_enchantment";
   public static final Logger LOGGER = LogUtils.getLogger();
   public static boolean IRONS_SPELL_BOOKS_LOADED = false;
   public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TABS = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, "curio_enchantment");
   public static final DeferredHolder<CreativeModeTab, CreativeModeTab> ENCHANTMENT_TAB = CREATIVE_MODE_TABS.register(
      "curio_enchantment",
      () -> CreativeModeTab.builder()
         .title(Component.translatable("itemGroup.curio_enchantment"))
         .icon(Items.ENCHANTED_BOOK::getDefaultInstance)
         .displayItems(
            (parameters, output) -> parameters.holders()
               .lookupOrThrow(Registries.ENCHANTMENT)
               .get(CETags.EnchantmentTags.ENCHANTMENT)
               .ifPresent(
                  holders -> holders.forEach(
                     enchantmentHolder -> output.accept(
                        EnchantedBookItem.createForEnchantment(
                           new EnchantmentInstance(enchantmentHolder, ((Enchantment)enchantmentHolder.value()).getMaxLevel())
                        )
                     )
                  )
               )
         )
         .build()
   );

   public CurioEnchantment(IEventBus modEventBus, ModContainer modContainer) {
      CREATIVE_MODE_TABS.register(modEventBus);
      DataComponentTypes.DATA_COMPONENT_TYPES.register(modEventBus);
      AttachmentTypes.ATTACHMENT_TYPE_DEFERRED_REGISTER.register(modEventBus);
      if (ModList.get().isLoaded("irons_spellbooks")) {
         IRONS_SPELL_BOOKS_LOADED = true;
         NeoForge.EVENT_BUS.register(EventHandler.GAME.IronsSpellBooksEvents.class);
      }
   }

   public static ResourceLocation id(String name) {
      return ResourceLocation.fromNamespaceAndPath("curio_enchantment", name);
   }
}
