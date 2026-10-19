package net.p3pp3rf1y.sophisticatedbackpacks.init;

import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.client.event.RegisterMenuScreensEvent;
import net.p3pp3rf1y.sophisticatedbackpacks.Config;
import net.p3pp3rf1y.sophisticatedbackpacks.client.gui.BackpackScreen;
import net.p3pp3rf1y.sophisticatedbackpacks.client.gui.BackpackSettingsScreen;
import net.p3pp3rf1y.sophisticatedbackpacks.client.gui.SBPButtonDefinitions;
import net.p3pp3rf1y.sophisticatedbackpacks.upgrades.anvil.AnvilUpgradeTab;
import net.p3pp3rf1y.sophisticatedbackpacks.upgrades.deposit.DepositUpgradeTab;
import net.p3pp3rf1y.sophisticatedbackpacks.upgrades.inception.InceptionUpgradeTab;
import net.p3pp3rf1y.sophisticatedbackpacks.upgrades.refill.RefillUpgradeTab;
import net.p3pp3rf1y.sophisticatedbackpacks.upgrades.restock.RestockUpgradeTab;
import net.p3pp3rf1y.sophisticatedbackpacks.upgrades.smithing.SmithingUpgradeTab;
import net.p3pp3rf1y.sophisticatedbackpacks.upgrades.toolswapper.ToolSwapperUpgradeTab;
import net.p3pp3rf1y.sophisticatedcore.client.gui.UpgradeGuiManager;
import net.p3pp3rf1y.sophisticatedcore.compat.trashslot.TrashSlotScreenRegistry;
import net.p3pp3rf1y.sophisticatedcore.upgrades.battery.BatteryInventoryPart;
import net.p3pp3rf1y.sophisticatedcore.upgrades.battery.BatteryUpgradeTab;
import net.p3pp3rf1y.sophisticatedcore.upgrades.cooking.AutoCookingUpgradeTab.AutoBlastingUpgradeTab;
import net.p3pp3rf1y.sophisticatedcore.upgrades.cooking.AutoCookingUpgradeTab.AutoSmeltingUpgradeTab;
import net.p3pp3rf1y.sophisticatedcore.upgrades.cooking.AutoCookingUpgradeTab.AutoSmokingUpgradeTab;
import net.p3pp3rf1y.sophisticatedcore.upgrades.cooking.CookingUpgradeTab.BlastingUpgradeTab;
import net.p3pp3rf1y.sophisticatedcore.upgrades.cooking.CookingUpgradeTab.SmeltingUpgradeTab;
import net.p3pp3rf1y.sophisticatedcore.upgrades.cooking.CookingUpgradeTab.SmokingUpgradeTab;
import net.p3pp3rf1y.sophisticatedcore.upgrades.crafting.CraftingUpgradeTab;
import net.p3pp3rf1y.sophisticatedcore.upgrades.filter.FilterUpgradeContainer;
import net.p3pp3rf1y.sophisticatedcore.upgrades.pickup.PickupUpgradeTab.Advanced;
import net.p3pp3rf1y.sophisticatedcore.upgrades.pickup.PickupUpgradeTab.Basic;
import net.p3pp3rf1y.sophisticatedcore.upgrades.stonecutter.StonecutterUpgradeTab;
import net.p3pp3rf1y.sophisticatedcore.upgrades.tank.TankInventoryPart;
import net.p3pp3rf1y.sophisticatedcore.upgrades.tank.TankUpgradeTab;
import net.p3pp3rf1y.sophisticatedcore.upgrades.xppump.XpPumpUpgradeTab;

public class ModItemsClient {
   public static void init(IEventBus modBus) {
      modBus.addListener(ModItemsClient::onMenuScreenRegister);
   }

   private static void onMenuScreenRegister(RegisterMenuScreensEvent event) {
      event.register(ModItems.BACKPACK_CONTAINER_TYPE.get(), BackpackScreen::constructScreen);
      event.register(ModItems.SETTINGS_CONTAINER_TYPE.get(), BackpackSettingsScreen::constructScreen);
      UpgradeGuiManager.registerTab(
         ModItems.PICKUP_BASIC_TYPE,
         (uc, p, s) -> new Basic(uc, p, s, (Integer)Config.SERVER.pickupUpgrade.slotsInRow.get(), SBPButtonDefinitions.BACKPACK_CONTENTS_FILTER_TYPE)
      );
      UpgradeGuiManager.registerTab(
         ModItems.PICKUP_ADVANCED_TYPE,
         (uc, p, s) -> new Advanced(uc, p, s, (Integer)Config.SERVER.advancedPickupUpgrade.slotsInRow.get(), SBPButtonDefinitions.BACKPACK_CONTENTS_FILTER_TYPE)
      );
      UpgradeGuiManager.registerTab(
         FilterUpgradeContainer.BASIC_TYPE,
         (uc, p, s) -> new net.p3pp3rf1y.sophisticatedcore.upgrades.filter.FilterUpgradeTab.Basic(
            uc, p, s, (Integer)Config.SERVER.filterUpgrade.slotsInRow.get(), SBPButtonDefinitions.BACKPACK_CONTENTS_FILTER_TYPE
         )
      );
      UpgradeGuiManager.registerTab(
         FilterUpgradeContainer.ADVANCED_TYPE,
         (uc, p, s) -> new net.p3pp3rf1y.sophisticatedcore.upgrades.filter.FilterUpgradeTab.Advanced(
            uc, p, s, (Integer)Config.SERVER.advancedFilterUpgrade.slotsInRow.get(), SBPButtonDefinitions.BACKPACK_CONTENTS_FILTER_TYPE
         )
      );
      UpgradeGuiManager.registerTab(
         ModItems.MAGNET_BASIC_TYPE,
         (uc, p, s) -> new net.p3pp3rf1y.sophisticatedcore.upgrades.magnet.MagnetUpgradeTab.Basic(
            uc, p, s, (Integer)Config.SERVER.magnetUpgrade.slotsInRow.get(), SBPButtonDefinitions.BACKPACK_CONTENTS_FILTER_TYPE
         )
      );
      UpgradeGuiManager.registerTab(
         ModItems.MAGNET_ADVANCED_TYPE,
         (uc, p, s) -> new net.p3pp3rf1y.sophisticatedcore.upgrades.magnet.MagnetUpgradeTab.Advanced(
            uc, p, s, (Integer)Config.SERVER.advancedMagnetUpgrade.slotsInRow.get(), SBPButtonDefinitions.BACKPACK_CONTENTS_FILTER_TYPE
         )
      );
      UpgradeGuiManager.registerTab(
         ModItems.FEEDING_TYPE,
         (uc, p, s) -> new net.p3pp3rf1y.sophisticatedcore.upgrades.feeding.FeedingUpgradeTab.Basic(
            uc, p, s, (Integer)Config.SERVER.feedingUpgrade.slotsInRow.get()
         )
      );
      UpgradeGuiManager.registerTab(
         ModItems.ADVANCED_FEEDING_TYPE,
         (uc, p, s) -> new net.p3pp3rf1y.sophisticatedcore.upgrades.feeding.FeedingUpgradeTab.Advanced(
            uc, p, s, (Integer)Config.SERVER.advancedFeedingUpgrade.slotsInRow.get()
         )
      );
      UpgradeGuiManager.registerTab(
         ModItems.COMPACTING_TYPE,
         (uc, p, s) -> new net.p3pp3rf1y.sophisticatedcore.upgrades.compacting.CompactingUpgradeTab.Basic(
            uc, p, s, (Integer)Config.SERVER.compactingUpgrade.slotsInRow.get()
         )
      );
      UpgradeGuiManager.registerTab(
         ModItems.ADVANCED_COMPACTING_TYPE,
         (uc, p, s) -> new net.p3pp3rf1y.sophisticatedcore.upgrades.compacting.CompactingUpgradeTab.Advanced(
            uc, p, s, (Integer)Config.SERVER.advancedCompactingUpgrade.slotsInRow.get()
         )
      );
      UpgradeGuiManager.registerTab(
         ModItems.VOID_TYPE,
         (uc, p, s) -> new net.p3pp3rf1y.sophisticatedcore.upgrades.voiding.VoidUpgradeTab.Basic(uc, p, s, (Integer)Config.SERVER.voidUpgrade.slotsInRow.get())
      );
      UpgradeGuiManager.registerTab(
         ModItems.ADVANCED_VOID_TYPE,
         (uc, p, s) -> new net.p3pp3rf1y.sophisticatedcore.upgrades.voiding.VoidUpgradeTab.Advanced(
            uc, p, s, (Integer)Config.SERVER.advancedVoidUpgrade.slotsInRow.get()
         )
      );
      UpgradeGuiManager.registerTab(
         ModItems.RESTOCK_TYPE, (uc, p, s) -> new RestockUpgradeTab.Basic(uc, p, s, SBPButtonDefinitions.BACKPACK_CONTENTS_FILTER_TYPE)
      );
      UpgradeGuiManager.registerTab(
         ModItems.ADVANCED_RESTOCK_TYPE, (uc, p, s) -> new RestockUpgradeTab.Advanced(uc, p, s, SBPButtonDefinitions.BACKPACK_CONTENTS_FILTER_TYPE)
      );
      UpgradeGuiManager.registerTab(ModItems.DEPOSIT_TYPE, DepositUpgradeTab.Basic::new);
      UpgradeGuiManager.registerTab(ModItems.ADVANCED_DEPOSIT_TYPE, DepositUpgradeTab.Advanced::new);
      UpgradeGuiManager.registerTab(
         ModItems.REFILL_TYPE, (uc, p, s) -> new RefillUpgradeTab.Basic(uc, p, s, (Integer)Config.SERVER.refillUpgrade.slotsInRow.get())
      );
      UpgradeGuiManager.registerTab(
         ModItems.ADVANCED_REFILL_TYPE, (uc, p, s) -> new RefillUpgradeTab.Advanced(uc, p, s, (Integer)Config.SERVER.advancedRefillUpgrade.slotsInRow.get())
      );
      UpgradeGuiManager.registerTab(ModItems.SMELTING_TYPE, SmeltingUpgradeTab::new);
      UpgradeGuiManager.registerTab(
         ModItems.AUTO_SMELTING_TYPE,
         (uc, p, s) -> new AutoSmeltingUpgradeTab(
            uc,
            p,
            s,
            (Integer)Config.SERVER.autoSmeltingUpgrade.inputFilterSlotsInRow.get(),
            (Integer)Config.SERVER.autoSmeltingUpgrade.fuelFilterSlotsInRow.get()
         )
      );
      UpgradeGuiManager.registerTab(ModItems.SMOKING_TYPE, SmokingUpgradeTab::new);
      UpgradeGuiManager.registerTab(
         ModItems.AUTO_SMOKING_TYPE,
         (uc, p, s) -> new AutoSmokingUpgradeTab(
            uc,
            p,
            s,
            (Integer)Config.SERVER.autoSmokingUpgrade.inputFilterSlotsInRow.get(),
            (Integer)Config.SERVER.autoSmokingUpgrade.fuelFilterSlotsInRow.get()
         )
      );
      UpgradeGuiManager.registerTab(ModItems.BLASTING_TYPE, BlastingUpgradeTab::new);
      UpgradeGuiManager.registerTab(
         ModItems.AUTO_BLASTING_TYPE,
         (uc, p, s) -> new AutoBlastingUpgradeTab(
            uc,
            p,
            s,
            (Integer)Config.SERVER.autoBlastingUpgrade.inputFilterSlotsInRow.get(),
            (Integer)Config.SERVER.autoBlastingUpgrade.fuelFilterSlotsInRow.get()
         )
      );
      UpgradeGuiManager.registerTab(
         ModItems.CRAFTING_TYPE,
         (uc, p, s) -> new CraftingUpgradeTab(uc, p, s, SBPButtonDefinitions.SHIFT_CLICK_TARGET, SBPButtonDefinitions.REFILL_CRAFTING_GRID)
      );
      UpgradeGuiManager.registerTab(ModItems.INCEPTION_TYPE, InceptionUpgradeTab::new);
      UpgradeGuiManager.registerTab(
         ModItems.STONECUTTER_TYPE,
         (upgradeContainer, position, screen) -> new StonecutterUpgradeTab(upgradeContainer, position, screen, SBPButtonDefinitions.SHIFT_CLICK_TARGET)
      );
      UpgradeGuiManager.registerTab(ModItems.JUKEBOX_TYPE, net.p3pp3rf1y.sophisticatedcore.upgrades.jukebox.JukeboxUpgradeTab.Basic::new);
      UpgradeGuiManager.registerTab(
         ModItems.ADVANCED_JUKEBOX_TYPE,
         (uc, p, s) -> new net.p3pp3rf1y.sophisticatedcore.upgrades.jukebox.JukeboxUpgradeTab.Advanced(
            uc, p, s, (Integer)Config.SERVER.advancedJukeboxUpgrade.slotsInRow.get()
         )
      );
      UpgradeGuiManager.registerTab(ModItems.TOOL_SWAPPER_TYPE, ToolSwapperUpgradeTab::new);
      UpgradeGuiManager.registerTab(ModItems.TANK_TYPE, TankUpgradeTab::new);
      UpgradeGuiManager.registerTab(ModItems.BATTERY_TYPE, BatteryUpgradeTab::new);
      UpgradeGuiManager.registerInventoryPart(ModItems.TANK_TYPE, TankInventoryPart::new);
      UpgradeGuiManager.registerInventoryPart(ModItems.BATTERY_TYPE, BatteryInventoryPart::new);
      UpgradeGuiManager.registerTab(ModItems.PUMP_TYPE, net.p3pp3rf1y.sophisticatedcore.upgrades.pump.PumpUpgradeTab.Basic::new);
      UpgradeGuiManager.registerTab(ModItems.ADVANCED_PUMP_TYPE, net.p3pp3rf1y.sophisticatedcore.upgrades.pump.PumpUpgradeTab.Advanced::new);
      UpgradeGuiManager.registerTab(
         ModItems.XP_PUMP_TYPE,
         (upgradeContainer, position, screen) -> new XpPumpUpgradeTab(upgradeContainer, position, screen, (Boolean)Config.SERVER.xpPumpUpgrade.mendingOn.get())
      );
      UpgradeGuiManager.registerTab(ModItems.ANVIL_TYPE, AnvilUpgradeTab::new);
      UpgradeGuiManager.registerTab(ModItems.SMITHING_TYPE, SmithingUpgradeTab::new);
      UpgradeGuiManager.registerTab(ModItems.ALCHEMY_TYPE, net.p3pp3rf1y.sophisticatedcore.upgrades.alchemy.AlchemyUpgradeTab.Basic::new);
      UpgradeGuiManager.registerTab(ModItems.ADVANCED_ALCHEMY_TYPE, net.p3pp3rf1y.sophisticatedcore.upgrades.alchemy.AlchemyUpgradeTab.Advanced::new);
      TrashSlotScreenRegistry.registerScreen(BackpackScreen.class);
   }
}
