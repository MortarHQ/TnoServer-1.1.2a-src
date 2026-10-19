package org.anti_ad.mc.ipnext.container;

import kotlin.Pair;
import kotlin.TuplesKt;
import kotlin.Unit;
import kotlin.collections.SetsKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.SourceDebugExtension;
import net.minecraft.client.gui.screens.inventory.CreativeModeInventoryScreen.ItemPickerMenu;
import net.minecraft.client.multiplayer.MultiPlayerGameMode;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.AbstractFurnaceMenu;
import net.minecraft.world.inventory.AnvilMenu;
import net.minecraft.world.inventory.BeaconMenu;
import net.minecraft.world.inventory.BlastFurnaceMenu;
import net.minecraft.world.inventory.BrewingStandMenu;
import net.minecraft.world.inventory.CartographyTableMenu;
import net.minecraft.world.inventory.ChestMenu;
import net.minecraft.world.inventory.CrafterMenu;
import net.minecraft.world.inventory.CraftingMenu;
import net.minecraft.world.inventory.DispenserMenu;
import net.minecraft.world.inventory.EnchantmentMenu;
import net.minecraft.world.inventory.FurnaceMenu;
import net.minecraft.world.inventory.GrindstoneMenu;
import net.minecraft.world.inventory.HopperMenu;
import net.minecraft.world.inventory.HorseInventoryMenu;
import net.minecraft.world.inventory.InventoryMenu;
import net.minecraft.world.inventory.ItemCombinerMenu;
import net.minecraft.world.inventory.LecternMenu;
import net.minecraft.world.inventory.LoomMenu;
import net.minecraft.world.inventory.MerchantMenu;
import net.minecraft.world.inventory.ShulkerBoxMenu;
import net.minecraft.world.inventory.SmithingMenu;
import net.minecraft.world.inventory.SmokerMenu;
import net.minecraft.world.inventory.StonecutterMenu;
import org.anti_ad.mc.common.vanilla.Vanilla;
import org.anti_ad.mc.ipnext.inventory.ContainerType;
import org.anti_ad.mc.ipnext.inventory.ContainerTypesKt;
import org.jetbrains.annotations.NotNull;

@SourceDebugExtension(
   "SMAP\nextensions.kt\nKotlin\n*S Kotlin\n*F\n+ 1 extensions.kt\norg/anti_ad/mc/ipnext/container/ExtensionsKt\n+ 2 VanillaAccessors.kt\norg/anti_ad/mc/ipnext/ingame/VanillaAccessorsKt\n+ 3 ArraysJVM.kt\nkotlin/collections/ArraysKt__ArraysJVMKt\n*L\n1#1,84:1\n81#2:85\n37#3:86\n36#3,3:87\n*S KotlinDebug\n*F\n+ 1 extensions.kt\norg/anti_ad/mc/ipnext/container/ExtensionsKt\n*L\n79#1:85\n72#1:86\n72#1:87,3\n*E\n"
)
public final class ExtensionsKt {
   @NotNull
   private static final Pair[] versionSpecificContainerTypes;
   @NotNull
   private static Function0 selectPostAction = ExtensionsKt::selectPostAction$lambda$0;
   @NotNull
   private static Function0 selectPreAction = ExtensionsKt::selectPreAction$lambda$1;

   @NotNull
   public static final Pair[] getVersionSpecificContainerTypes() {
      return versionSpecificContainerTypes;
   }

   @NotNull
   public static final Function0 getSelectPostAction() {
      return selectPostAction;
   }

   public static final void setSelectPostAction(@NotNull Function0 var0) {
      Intrinsics.checkNotNullParameter(var0, "");
      selectPostAction = var0;
   }

   @NotNull
   public static final Function0 getSelectPreAction() {
      return selectPreAction;
   }

   public static final void setSelectPreAction(@NotNull Function0 var0) {
      Intrinsics.checkNotNullParameter(var0, "");
      selectPreAction = var0;
   }

   public static final void selectRecipe(@NotNull StonecutterMenu var0, int var1) {
      Intrinsics.checkNotNullParameter(var0, "");
      var0.clickMenuButton((Player)Vanilla.INSTANCE.player(), var1);
      MultiPlayerGameMode var10000 = Vanilla.INSTANCE.mc().gameMode;
      if (var10000 != null) {
         var10000.handleInventoryButtonClick(((AbstractContainerMenu)var0).containerId, var1);
      }
   }

   public static final int get_levelCost_/* $VF was: get(levelCost) */(@NotNull AnvilMenu var0) {
      Intrinsics.checkNotNullParameter(var0, "");
      return var0.getCost();
   }

   private static final Unit selectPostAction$lambda$0() {
      return Unit.INSTANCE;
   }

   private static final Unit selectPreAction$lambda$1() {
      return Unit.INSTANCE;
   }

   static {
      Pair[] var0;
      (var0 = new Pair[25])[0] = TuplesKt.to(InventoryMenu.class, ContainerTypesKt.getPlayerOnly());
      ContainerType[] var1;
      (var1 = new ContainerType[2])[0] = ContainerType.PURE_BACKPACK;
      var1[1] = ContainerType.CREATIVE;
      var0[1] = TuplesKt.to(ItemPickerMenu.class, SetsKt.setOf(var1));
      var0[2] = TuplesKt.to(EnchantmentMenu.class, ContainerTypesKt.getNonStorage());
      (var1 = new ContainerType[2])[0] = ContainerType.PURE_BACKPACK;
      var1[1] = ContainerType.ANVIL;
      var0[3] = TuplesKt.to(AnvilMenu.class, SetsKt.setOf(var1));
      var0[4] = TuplesKt.to(BeaconMenu.class, ContainerTypesKt.getNonStorage());
      var0[5] = TuplesKt.to(BlastFurnaceMenu.class, ContainerTypesKt.getSimple());
      var0[6] = TuplesKt.to(CartographyTableMenu.class, ContainerTypesKt.getSimple());
      var0[7] = TuplesKt.to(ItemCombinerMenu.class, ContainerTypesKt.getSimple());
      var0[8] = TuplesKt.to(FurnaceMenu.class, ContainerTypesKt.getSimple());
      var0[9] = TuplesKt.to(GrindstoneMenu.class, ContainerTypesKt.getSimple());
      var0[10] = TuplesKt.to(LecternMenu.class, ContainerTypesKt.getNonStorage());
      var0[11] = TuplesKt.to(LoomMenu.class, ContainerTypesKt.getNonStorage());
      (var1 = new ContainerType[2])[0] = ContainerType.PURE_BACKPACK;
      var1[1] = ContainerType.STONECUTTER;
      var0[12] = TuplesKt.to(StonecutterMenu.class, SetsKt.setOf(var1));
      var0[13] = TuplesKt.to(SmithingMenu.class, ContainerTypesKt.getSimple());
      var0[14] = TuplesKt.to(SmokerMenu.class, ContainerTypesKt.getSimple());
      var0[15] = TuplesKt.to(MerchantMenu.class, SetsKt.setOf(ContainerType.TRADER));
      var0[16] = TuplesKt.to(CraftingMenu.class, SetsKt.setOf(ContainerType.CRAFTING));
      var0[17] = TuplesKt.to(CrafterMenu.class, ContainerTypesKt.getSimple());
      (var1 = new ContainerType[2])[0] = ContainerType.NO_SORTING_STORAGE;
      var1[1] = ContainerType.PURE_BACKPACK;
      var0[18] = TuplesKt.to(HopperMenu.class, SetsKt.setOf(var1));
      var0[19] = TuplesKt.to(BrewingStandMenu.class, ContainerTypesKt.getNonStorage());
      var0[20] = TuplesKt.to(AbstractFurnaceMenu.class, ContainerTypesKt.getSimple());
      (var1 = new ContainerType[3])[0] = ContainerType.SORTABLE_STORAGE;
      var1[1] = ContainerType.RECTANGULAR;
      var1[2] = ContainerType.WIDTH_9;
      var0[21] = TuplesKt.to(ChestMenu.class, SetsKt.setOf(var1));
      (var1 = new ContainerType[3])[0] = ContainerType.SORTABLE_STORAGE;
      var1[1] = ContainerType.RECTANGULAR;
      var1[2] = ContainerType.WIDTH_9;
      var0[22] = TuplesKt.to(ShulkerBoxMenu.class, SetsKt.setOf(var1));
      (var1 = new ContainerType[4])[0] = ContainerType.SORTABLE_STORAGE;
      var1[1] = ContainerType.RECTANGULAR;
      var1[2] = ContainerType.HEIGHT_3;
      var1[3] = ContainerType.HORSE_STORAGE;
      var0[23] = TuplesKt.to(HorseInventoryMenu.class, SetsKt.setOf(var1));
      (var1 = new ContainerType[3])[0] = ContainerType.SORTABLE_STORAGE;
      var1[1] = ContainerType.RECTANGULAR;
      var1[2] = ContainerType.HEIGHT_3;
      var0[24] = TuplesKt.to(DispenserMenu.class, SetsKt.setOf(var1));
      versionSpecificContainerTypes = SetsKt.setOf(var0).toArray(new Pair[0]);
   }
}
