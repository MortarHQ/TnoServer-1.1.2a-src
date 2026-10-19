package com.github.wal_bos.moreskills.registry.menu;

import com.github.wal_bos.moreskills.casino.GreedCasinoMenu;
import com.github.wal_bos.moreskills.fungus.FungusInfectionMenu;
import com.github.wal_bos.moreskills.gene.GeneEditorMenu;
import com.github.wal_bos.moreskills.limbo.IWantLimboMenu;
import com.github.wal_bos.moreskills.menu.CaedrosAspectMenu;
import com.github.wal_bos.moreskills.menu.CaedrosReconstructMenu;
import com.github.wal_bos.moreskills.menu.GodlyWorkMenu;
import com.github.wal_bos.moreskills.menu.MahoragaAdaptationMenu;
import com.github.wal_bos.moreskills.menu.MitamaOfferingMenu;
import com.github.wal_bos.moreskills.menu.NaberiusMenu;
import com.github.wal_bos.moreskills.menu.VoidArchiveForgeMenu;
import com.github.wal_bos.moreskills.menu.VoidArchiveLibraryMenu;
import com.github.wal_bos.moreskills.menu.VoidArchiveLoadoutMenu;
import com.github.wal_bos.moreskills.nirvana.NirvanaMenu;
import dev.architectury.registry.registries.DeferredRegister;
import dev.architectury.registry.registries.RegistrySupplier;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.inventory.MenuType;
import net.neoforged.neoforge.common.extensions.IMenuTypeExtension;

public final class ModMenus {
   public static final DeferredRegister<MenuType<?>> MENUS = DeferredRegister.create("tensuramoreskills", Registries.MENU);
   public static final RegistrySupplier<MenuType<FungusInfectionMenu>> FUNGUS_INFECTION_MENU = MENUS.register(
      "fungus_infection_menu", () -> IMenuTypeExtension.create(FungusInfectionMenu::new)
   );
   public static final RegistrySupplier<MenuType<CaedrosAspectMenu>> CAEDROS_ASPECT_MENU = MENUS.register(
      "caedros_aspect_menu", () -> IMenuTypeExtension.create(CaedrosAspectMenu::new)
   );
   public static final RegistrySupplier<MenuType<MitamaOfferingMenu>> MITAMA_OFFERING_MENU = MENUS.register(
      "mitama_offering", () -> IMenuTypeExtension.create(MitamaOfferingMenu::client)
   );
   public static final RegistrySupplier<MenuType<CaedrosReconstructMenu>> CAEDROS_RECONSTRUCT_MENU = MENUS.register(
      "caedros_reconstruct_menu", () -> IMenuTypeExtension.create(CaedrosReconstructMenu::new)
   );
   public static final RegistrySupplier<MenuType<VoidArchiveLibraryMenu>> VOID_ARCHIVE_LIBRARY_MENU = MENUS.register(
      "void_archive_library_menu", () -> IMenuTypeExtension.create(VoidArchiveLibraryMenu::new)
   );
   public static final RegistrySupplier<MenuType<IWantLimboMenu>> I_WANT_LIMBO_MENU = MENUS.register(
      "i_want_limbo", () -> IMenuTypeExtension.create(IWantLimboMenu::new)
   );
   public static final RegistrySupplier<MenuType<VoidArchiveForgeMenu>> VOID_ARCHIVE_FORGE_MENU = MENUS.register(
      "void_archive_forge_menu", () -> IMenuTypeExtension.create(VoidArchiveForgeMenu::new)
   );
   public static final RegistrySupplier<MenuType<NirvanaMenu>> NIRVANA_ARCHIVE_MENU = MENUS.register(
      "nirvana_archive", () -> IMenuTypeExtension.create(NirvanaMenu::new)
   );
   public static final RegistrySupplier<MenuType<GeneEditorMenu>> GENE_EDITOR_MENU = MENUS.register(
      "gene_editor_menu", () -> IMenuTypeExtension.create(GeneEditorMenu::new)
   );
   public static final RegistrySupplier<MenuType<VoidArchiveLoadoutMenu>> VOID_ARCHIVE_LOADOUT_MENU = MENUS.register(
      "void_archive_loadout_menu", () -> IMenuTypeExtension.create(VoidArchiveLoadoutMenu::new)
   );
   public static final RegistrySupplier<MenuType<MahoragaAdaptationMenu>> MAHORAGA_ADAPTATION_MENU = MENUS.register(
      "mahoraga_adaptation_menu", () -> IMenuTypeExtension.create(MahoragaAdaptationMenu::client)
   );
   public static final RegistrySupplier<MenuType<GreedCasinoMenu>> GREED_CASINO = MENUS.register(
      "greed_casino", () -> IMenuTypeExtension.create((id, inventory, buf) -> new GreedCasinoMenu(id, inventory))
   );
   public static final RegistrySupplier<MenuType<NaberiusMenu>> NABERIUS_MENU = MENUS.register(
      "naberius_menu", () -> IMenuTypeExtension.create(NaberiusMenu::new)
   );
   public static final RegistrySupplier<MenuType<GodlyWorkMenu>> GODLY_WORK_MENU = MENUS.register(
      "godly_work", () -> IMenuTypeExtension.create(GodlyWorkMenu::new)
   );

   private ModMenus() {
   }

   public static void init() {
      MENUS.register();
   }
}
