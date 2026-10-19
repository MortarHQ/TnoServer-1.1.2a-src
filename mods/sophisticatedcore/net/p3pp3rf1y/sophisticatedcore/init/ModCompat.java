package net.p3pp3rf1y.sophisticatedcore.init;

import net.p3pp3rf1y.sophisticatedcore.compat.CompatInfo;
import net.p3pp3rf1y.sophisticatedcore.compat.CompatRegistry;
import net.p3pp3rf1y.sophisticatedcore.compat.accessories.AccessoriesCompat;
import net.p3pp3rf1y.sophisticatedcore.compat.craftingtweaks.CraftingTweaksCompat;
import net.p3pp3rf1y.sophisticatedcore.compat.create.CreateCompat;
import net.p3pp3rf1y.sophisticatedcore.compat.curios.CuriosCompat;
import net.p3pp3rf1y.sophisticatedcore.compat.ftbchunks.FTBChunksCompat;
import net.p3pp3rf1y.sophisticatedcore.compat.inventorysorter.InventorySorterCompat;
import net.p3pp3rf1y.sophisticatedcore.compat.itemborders.ItemBordersCompat;
import net.p3pp3rf1y.sophisticatedcore.compat.mousetweaks.MouseTweaksCompat;
import net.p3pp3rf1y.sophisticatedcore.compat.openpartiesandclaims.OpenPACCompat;
import net.p3pp3rf1y.sophisticatedcore.compat.recipeviewers.emi.EmiCompat;
import net.p3pp3rf1y.sophisticatedcore.compat.recipeviewers.jei.JeiCompat;
import net.p3pp3rf1y.sophisticatedcore.compat.recipeviewers.rei.ReiCompat;
import net.p3pp3rf1y.sophisticatedcore.compat.reliquary.ReliquaryCompat;
import net.p3pp3rf1y.sophisticatedcore.compat.trashslot.TrashSlotCompat;

public class ModCompat {
   private ModCompat() {
   }

   public static void register() {
      CompatRegistry.registerCompat(new CompatInfo("jei"), () -> modBus -> new JeiCompat());
      CompatRegistry.registerCompat(new CompatInfo("emi"), () -> modBus -> new EmiCompat());
      CompatRegistry.registerCompat(new CompatInfo("roughlyenoughitems"), () -> modBus -> new ReiCompat());
      CompatRegistry.registerCompat(new CompatInfo("craftingtweaks"), () -> modBus -> new CraftingTweaksCompat());
      CompatRegistry.registerCompat(new CompatInfo("inventorysorter"), () -> modBus -> new InventorySorterCompat());
      CompatRegistry.registerCompat(new CompatInfo("itemborders"), () -> mobBus -> new ItemBordersCompat());
      CompatRegistry.registerCompat(new CompatInfo("curios"), () -> mobBus -> new CuriosCompat());
      CompatRegistry.registerCompat(new CompatInfo("create"), () -> mobBus -> new CreateCompat());
      CompatRegistry.registerCompat(new CompatInfo("trashslot"), () -> mobBus -> new TrashSlotCompat());
      CompatRegistry.registerCompat(new CompatInfo("reliquary"), () -> mobBus -> new ReliquaryCompat());
      CompatRegistry.registerCompat(new CompatInfo("mousetweaks"), () -> mobBus -> new MouseTweaksCompat());
      CompatRegistry.registerCompat(new CompatInfo("ftbchunks"), () -> mobBus -> new FTBChunksCompat());
      CompatRegistry.registerCompat(new CompatInfo("openpartiesandclaims"), () -> mobBus -> new OpenPACCompat());
      CompatRegistry.registerCompat(new CompatInfo("accessories"), () -> mobBus -> new AccessoriesCompat());
   }
}
