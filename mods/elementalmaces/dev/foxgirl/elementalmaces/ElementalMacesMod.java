package dev.foxgirl.elementalmaces;

import java.util.function.Supplier;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.CreativeModeTab.TabVisibility;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModList;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.neoforged.fml.loading.FMLLoader;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.BuildCreativeModeTabContentsEvent;
import net.neoforged.neoforge.event.entity.living.LivingIncomingDamageEvent;
import net.neoforged.neoforge.registries.DeferredRegister;

@Mod("elementalmaces")
public final class ElementalMacesMod {
   private final IEventBus modEventBus;
   private final IEventBus forgeEventBus;
   private final ElementalMaces elementalMacesImpl;

   private ElementalMaces.ItemRegisterAdapter createItemRegisterAdapter(String modID) {
      return new ElementalMaces.ItemRegisterAdapter() {
         private final DeferredRegister<Item> register = DeferredRegister.create(Registries.ITEM, modID);

         @Override
         public ResourceLocation getID(Item item) {
            return ((Registry)this.register.getRegistry().get()).getKey(item);
         }

         @Override
         public Item getItem(ResourceLocation id) {
            return (Item)((Registry)this.register.getRegistry().get()).get(id);
         }

         @Override
         public <T extends Item> Supplier<T> add(String name, Supplier<T> supplier) {
            return this.register.register(name, supplier);
         }

         @Override
         public void register() {
            this.register.register(ElementalMacesMod.this.modEventBus);
         }
      };
   }

   private ElementalMaces.LoaderAdapter createLoaderAdapter() {
      return new ElementalMaces.LoaderAdapter() {
         @Override
         public ElementalMaces.ItemRegisterAdapter createItemRegister(String modID) {
            return ElementalMacesMod.this.createItemRegisterAdapter(modID);
         }

         @Override
         public boolean isModLoaded(String modID) {
            return ModList.get().isLoaded(modID);
         }

         @Override
         public boolean isDedicatedServer() {
            return FMLLoader.getDist().isDedicatedServer();
         }
      };
   }

   public ElementalMacesMod(IEventBus modEventBusImpl) {
      this.modEventBus = modEventBusImpl;
      this.forgeEventBus = NeoForge.EVENT_BUS;
      this.elementalMacesImpl = new ElementalMaces(this.createLoaderAdapter());
      this.elementalMacesImpl.registerItems();
      this.modEventBus.addListener(this::onFMLCommonSetupEvent);
      this.modEventBus.addListener(this::onBuildCreativeModeTabContentsEvent);
      this.forgeEventBus.addListener(this::onLivingIncomingDamageEvent);
   }

   private void onFMLCommonSetupEvent(FMLCommonSetupEvent event) {
      this.elementalMacesImpl.registerMace3DModels();
   }

   private void onBuildCreativeModeTabContentsEvent(BuildCreativeModeTabContentsEvent event) {
      if (event.getTabKey() == CreativeModeTabs.COMBAT) {
         this.elementalMacesImpl
            .getMaceItems()
            .forEach(item -> event.insertAfter(new ItemStack(Items.MACE), new ItemStack(item), TabVisibility.PARENT_AND_SEARCH_TABS));
      }

      if (event.getTabKey() == CreativeModeTabs.INGREDIENTS) {
         this.elementalMacesImpl
            .getRodItems()
            .forEach(item -> event.insertAfter(new ItemStack(Items.BREEZE_ROD), new ItemStack(item), TabVisibility.PARENT_AND_SEARCH_TABS));
      }
   }

   private void onLivingIncomingDamageEvent(LivingIncomingDamageEvent event) {
      if (this.elementalMacesImpl.shouldIgnoreDamage(event.getEntity(), event.getSource(), event.getAmount())) {
         event.setCanceled(true);
      }
   }
}
