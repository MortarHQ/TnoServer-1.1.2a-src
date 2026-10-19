package dev.foxgirl.elementalmaces;

import dev.foxgirl.elementalmaces.items.AbstractElementalMaceItem;
import dev.foxgirl.elementalmaces.items.EnderMaceItem;
import dev.foxgirl.elementalmaces.items.ResonatingMaceItem;
import dev.foxgirl.elementalmaces.items.SmoulderingMaceItem;
import dev.foxgirl.elementalmaces.items.WitheringMaceItem;
import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.util.Arrays;
import java.util.List;
import java.util.function.Supplier;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Item.Properties;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public final class ElementalMaces {
   public static final Logger LOGGER = LogManager.getLogger();
   public final ElementalMaces.LoaderAdapter loader;
   public final ElementalMaces.ItemRegisterAdapter items;
   public final Supplier<AbstractElementalMaceItem> resonatingMace;
   public final Supplier<AbstractElementalMaceItem> witheringMace;
   public final Supplier<AbstractElementalMaceItem> enderMace;
   public final Supplier<AbstractElementalMaceItem> smoulderingMace;
   public final Supplier<Item> resonatingRod;
   public final Supplier<Item> witheringRod;

   public ElementalMaces(ElementalMaces.LoaderAdapter loader) {
      LOGGER.info("Setting up Elemental Maces...");
      this.loader = loader;
      this.items = loader.createItemRegister("elementalmaces");
      this.resonatingMace = this.items.add("resonating_mace", ResonatingMaceItem::new);
      this.witheringMace = this.items.add("withering_mace", WitheringMaceItem::new);
      this.enderMace = this.items.add("ender_mace", EnderMaceItem::new);
      this.smoulderingMace = this.items.add("smouldering_mace", SmoulderingMaceItem::new);
      this.resonatingRod = this.items.add("resonating_rod", () -> new Item(new Properties()));
      this.witheringRod = this.items.add("withering_rod", () -> new Item(new Properties()));
   }

   public List<AbstractElementalMaceItem> getMaceItems() {
      return Arrays.asList(this.resonatingMace.get(), this.witheringMace.get(), this.enderMace.get(), this.smoulderingMace.get());
   }

   public List<Item> getRodItems() {
      return Arrays.asList(this.resonatingRod.get(), this.witheringRod.get());
   }

   public void registerItems() {
      LOGGER.info("Registering items...");
      this.items.register();
   }

   public void registerMace3DModels() {
      if (!this.loader.isDedicatedServer() && this.loader.isModLoaded("mace3d")) {
         LOGGER.info("Registering external 3D mace models");

         try {
            MethodHandle registerModelMethod = MethodHandles.lookup()
               .findStatic(
                  Class.forName("dev.foxgirl.mace3d.Mace3D"),
                  "registerExternalMaceModel",
                  MethodType.methodType(void.class, Item.class, ResourceLocation.class, ResourceLocation.class)
               );

            for (AbstractElementalMaceItem item : this.getMaceItems()) {
               ResourceLocation id = this.items.getID(item);
               registerModelMethod.invoke((AbstractElementalMaceItem)item, (ResourceLocation)id, (ResourceLocation)id.withSuffix("_in_hand"));
            }
         } catch (Throwable cause) {
            LOGGER.error("Failed to register external 3D mace models", cause);
         }
      } else {
         LOGGER.info("Skipping external 3D mace models, 'mace3d' is not loaded");
      }
   }

   public void registerAll() {
      this.registerItems();
      this.registerMace3DModels();
   }

   public boolean shouldIgnoreDamage(LivingEntity entity, DamageSource source, float amount) {
      return source.is(DamageTypes.IN_FIRE) && entity.isHolding((Item)this.smoulderingMace.get())
         ? true
         : source.is(DamageTypes.WITHER) && entity.isHolding((Item)this.witheringMace.get());
   }

   public interface ItemRegisterAdapter {
      ResourceLocation getID(Item var1);

      Item getItem(ResourceLocation var1);

      <T extends Item> Supplier<T> add(String var1, Supplier<T> var2);

      void register();
   }

   public interface LoaderAdapter {
      ElementalMaces.ItemRegisterAdapter createItemRegister(String var1);

      boolean isModLoaded(String var1);

      boolean isDedicatedServer();
   }
}
