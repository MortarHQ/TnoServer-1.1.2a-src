package org.anti_ad.mc.ipnext.event;

import java.util.Iterator;
import java.util.List;
import kotlin.Unit;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.SourceDebugExtension;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.core.NonNullList;
import net.minecraft.core.component.DataComponentMap;
import net.minecraft.core.component.PatchedDataComponentMap;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.Item;
import org.anti_ad.mc.common.input.KeybindSettings;
import org.anti_ad.mc.common.input.MainKeybind;
import org.anti_ad.mc.common.vanilla.Vanilla;
import org.anti_ad.mc.ipnext.Log;
import org.anti_ad.mc.ipnext.config.GuiSettings;
import org.anti_ad.mc.ipnext.container.ExtensionsKt;
import org.anti_ad.mc.ipnext.ingame.VanillaAccessorsKt$(itemType)$1;
import org.anti_ad.mc.ipnext.inventory.AreaType;
import org.anti_ad.mc.ipnext.inventory.AreaTypes;
import org.anti_ad.mc.ipnext.inventory.ContainerClicker;
import org.anti_ad.mc.ipnext.item.ItemStack;
import org.anti_ad.mc.ipnext.item.ItemStack$Companion;
import org.anti_ad.mc.ipnext.item.ItemStackExtensionsKt;
import org.anti_ad.mc.ipnext.item.ItemType;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@SourceDebugExtension(
   "SMAP\nStoneCutterCraftingHandler.kt\nKotlin\n*S Kotlin\n*F\n+ 1 StoneCutterCraftingHandler.kt\norg/anti_ad/mc/ipnext/event/CutterCraftingHandlerBase\n+ 2 VanillaAccessors.kt\norg/anti_ad/mc/ipnext/ingame/VanillaAccessorsKt\n+ 3 ItemTypeExtensions.kt\norg/anti_ad/mc/ipnext/item/ItemTypeExtensionsKt\n*L\n1#1,293:1\n78#2:294\n78#2:295\n88#2:296\n71#2:297\n66#2,7:298\n88#2:305\n71#2:306\n66#2,7:307\n84#2:314\n78#2,11:315\n71#2:326\n66#2,7:327\n78#2,11:334\n71#2:345\n66#2,7:346\n112#3:353\n*S KotlinDebug\n*F\n+ 1 StoneCutterCraftingHandler.kt\norg/anti_ad/mc/ipnext/event/CutterCraftingHandlerBase\n*L\n89#1:294\n131#1:295\n133#1:296\n133#1:297\n133#1:298,7\n137#1:305\n137#1:306\n137#1:307,7\n140#1:314\n178#1:315,11\n178#1:326\n178#1:327,7\n185#1:334,11\n185#1:345\n185#1:346,7\n195#1:353\n*E\n"
)
public abstract class CutterCraftingHandlerBase {
   private boolean skipTick;
   private int waitForEmptyNumTicks;
   private boolean isCraftClick;
   private boolean stillCrafting;
   private boolean isRefillTick;
   private int lastRecipe = -1;
   @NotNull
   private ItemStack lastInput = ItemStackExtensionsKt.getEMPTY(ItemStack.Companion);
   private int recipe = -1;
   protected ItemStack input;
   private boolean isNewScreen = true;
   @Nullable
   private AbstractContainerScreen currentScreen;
   @Nullable
   private AbstractContainerMenu currentContainer;
   @NotNull
   private final MainKeybind SHIFT = new MainKeybind("LEFT_SHIFT", KeybindSettings.Companion.getGUI_EXTRA());

   public CutterCraftingHandlerBase() {
      ExtensionsKt.setSelectPostAction(CutterCraftingHandlerBase::_init_$lambda$0);
      ExtensionsKt.setSelectPreAction(CutterCraftingHandlerBase::_init_$lambda$1);
   }

   public final boolean getSkipTick() {
      return this.skipTick;
   }

   public final int getWaitForEmptyNumTicks() {
      return this.waitForEmptyNumTicks;
   }

   protected final boolean isCraftClick() {
      return this.isCraftClick;
   }

   protected final void setCraftClick(boolean var1) {
      this.isCraftClick = var1;
   }

   public final boolean getStillCrafting() {
      return this.stillCrafting;
   }

   public final void setStillCrafting(boolean var1) {
      this.stillCrafting = var1;
   }

   public final boolean isRefillTick() {
      return this.isRefillTick;
   }

   public final void setRefillTick(boolean var1) {
      this.isRefillTick = var1;
   }

   @NotNull
   protected final ItemStack getInput() {
      if (this.input != null) {
         return this.input;
      }

      Intrinsics.throwUninitializedPropertyAccessException("");
      return null;
   }

   protected final void setInput(@NotNull ItemStack var1) {
      Intrinsics.checkNotNullParameter(var1, "");
      this.input = var1;
   }

   private final boolean getEnabled() {
      return GuiSettings.INSTANCE.getCONTINUOUS_CRAFTING_SAVED_VALUE().getBooleanValue();
   }

   public final boolean isNewScreen() {
      return this.isNewScreen;
   }

   protected final void setNewScreen(boolean var1) {
      this.isNewScreen = var1;
   }

   @NotNull
   protected final MainKeybind getSHIFT() {
      return this.SHIFT;
   }

   @NotNull
   public final List getPlayerSlotIndices() {
      AbstractContainerMenu var1 = this.currentContainer;
      if (this.currentContainer != null) {
         AreaTypes var2 = AreaTypes.INSTANCE;
         AreaType var10000 = AreaTypes.INSTANCE.getPlayerStorage().plus(var2.getPlayerHotbar()).plus(var2.getPlayerOffhand()).minus(var2.getLockedSlots());
         NonNullList var10002 = var1.slots;
         Intrinsics.checkNotNullExpressionValue(var1.slots, "");
         return var10000.getItemArea(var1, (List)var10002).getSlotIndices();
      } else {
         return CollectionsKt.emptyList();
      }
   }

   public abstract void typeSpecificOnTickInGame(@NotNull AbstractContainerScreen var1);

   public abstract void typeSpecificNewContainer(@NotNull AbstractContainerScreen var1);

   public abstract void selectRecipe(@NotNull AbstractContainerMenu var1, int var2);

   public abstract int selectedRecipe(@NotNull AbstractContainerMenu var1);

   @Nullable
   public abstract Integer selectedRecipeOrNull(@Nullable AbstractContainerMenu var1);

   public final void onTickBase() {
      if (!this.skipTick) {
         Screen var1;
         if ((var1 = Vanilla.INSTANCE.screen()) != null) {
            if (this.getEnabled() && var1 instanceof AbstractContainerScreen) {
               if (this.stillCrafting && !this.isRefillTick) {
                  Log.INSTANCE.trace("Still crafting");
                  this.typeSpecificOnTickInGame((AbstractContainerScreen)var1);
                  this.stillCrafting = false;
                  return;
               }

               if (this.isCraftClick) {
                  Log.INSTANCE.trace("craft click");
                  ContainerClicker.INSTANCE.shiftClick(1);
                  this.isCraftClick = false;
                  return;
               }

               if (!this.isRefillTick) {
                  if (this.isNewScreen) {
                     this.typeSpecificNewContainer((AbstractContainerScreen)var1);
                     return;
                  }

                  this.checkChanged();
                  return;
               }

               List var15;
               label82: {
                  Log.INSTANCE.trace("refill tick");
                  if (this.currentContainer != null) {
                     NonNullList var10000 = this.currentContainer.slots;
                     Intrinsics.checkNotNullExpressionValue(this.currentContainer.slots, "");
                     var15 = (List)var10000;
                     if ((List)var10000 != null) {
                        break label82;
                     }
                  }

                  var15 = CollectionsKt.emptyList();
               }

               List var6 = var15;
               if (!var15.isEmpty()) {
                  net.minecraft.world.item.ItemStack var10001 = ((Slot)var6.get(0)).getItem();
                  Intrinsics.checkNotNullExpressionValue(var10001, "");
                  net.minecraft.world.item.ItemStack var2 = var10001;
                  ItemStack var20;
                  if (var10001.isEmpty()) {
                     var20 = ItemStackExtensionsKt.getEMPTY(ItemStack.Companion);
                  } else {
                     ItemStack$Companion var21 = ItemStack.Companion;
                     net.minecraft.world.item.ItemStack var4 = var2;
                     Item var10004 = var4.getItem();
                     Intrinsics.checkNotNullExpressionValue(var10004, "");
                     DataComponentMap var10005 = var4.getComponents();
                     Intrinsics.checkNotNull(var10005);
                     var20 = var21.invoke(
                        new ItemType(
                           var10004,
                           (PatchedDataComponentMap)var10005,
                           var4.getComponentsPatch(),
                           new VanillaAccessorsKt$(itemType)$1(var4),
                           false,
                           false,
                           null,
                           112,
                           null
                        ),
                        var2.getCount()
                     );
                  }

                  this.setInput(var20);
                  if (!ItemStackExtensionsKt.isEmpty(this.getInput())) {
                     int var11 = this.waitForEmptyNumTicks++;
                     if (this.waitForEmptyNumTicks <= 2) {
                        return;
                     }

                     this.waitForEmptyNumTicks = 0;
                     Log.INSTANCE.trace("did not find empty slot, will stop refill");
                  } else {
                     this.waitForEmptyNumTicks = 0;
                     Iterator var3 = this.getPlayerSlotIndices().iterator();

                     while (true) {
                        if (var3.hasNext()) {
                           Object var14;
                           int var7 = ((Number)(var14 = var3.next())).intValue();
                           net.minecraft.world.item.ItemStack var17 = ((Slot)var6.get(var7)).getItem();
                           Intrinsics.checkNotNullExpressionValue(var17, "");
                           var2 = var17;
                           ItemStack var18;
                           if (var17.isEmpty()) {
                              var18 = ItemStackExtensionsKt.getEMPTY(ItemStack.Companion);
                           } else {
                              ItemStack$Companion var19 = ItemStack.Companion;
                              net.minecraft.world.item.ItemStack var5 = var2;
                              Item var10003 = var5.getItem();
                              Intrinsics.checkNotNullExpressionValue(var10003, "");
                              DataComponentMap var22 = var5.getComponents();
                              Intrinsics.checkNotNull(var22);
                              var18 = var19.invoke(
                                 new ItemType(
                                    var10003,
                                    (PatchedDataComponentMap)var22,
                                    var5.getComponentsPatch(),
                                    new VanillaAccessorsKt$(itemType)$1(var5),
                                    false,
                                    false,
                                    null,
                                    112,
                                    null
                                 ),
                                 var2.getCount()
                              );
                           }

                           if (!Intrinsics.areEqual(var18.getItemType(), this.lastInput.getItemType())) {
                              continue;
                           }

                           var16 = var14;
                           break;
                        }

                        var16 = null;
                        break;
                     }

                     Integer var9;
                     if ((var9 = (Integer)var16) != null) {
                        int var10 = var9.intValue();
                        Log.INSTANCE.trace("will shift click on slot " + var10);
                        ContainerClicker.INSTANCE.shiftClick(((Slot)var6.get(var10)).index);
                     }
                  }

                  int var12 = this.lastRecipe;
                  if (this.currentContainer != null) {
                     AbstractContainerMenu var13 = this.currentContainer;
                     this.selectRecipe(var13, var12);
                  }
               }

               this.isRefillTick = false;
               return;
            }
         } else {
            this.isNewScreen = true;
            this.currentContainer = null;
            this.currentScreen = null;
            this.setInput(ItemStackExtensionsKt.getEMPTY(ItemStack.Companion));
            this.recipe = -1;
         }
      }
   }

   protected final void init(@NotNull AbstractContainerScreen var1, @NotNull AbstractContainerMenu var2) {
      Intrinsics.checkNotNullParameter(var1, "");
      Intrinsics.checkNotNullParameter(var2, "");
      this.currentContainer = var2;
      this.currentScreen = var1;
      NonNullList var10001 = var2.slots;
      Intrinsics.checkNotNullExpressionValue(var2.slots, "");
      net.minecraft.world.item.ItemStack var5 = ((Slot)((List)var10001).get(0)).getItem();
      Intrinsics.checkNotNullExpressionValue(var5, "");
      net.minecraft.world.item.ItemStack var4 = var5;
      ItemStack var6;
      if (var5.isEmpty()) {
         var6 = ItemStackExtensionsKt.getEMPTY(ItemStack.Companion);
      } else {
         ItemStack$Companion var7 = ItemStack.Companion;
         net.minecraft.world.item.ItemStack var3 = var4;
         Item var10004 = var3.getItem();
         Intrinsics.checkNotNullExpressionValue(var10004, "");
         DataComponentMap var10005 = var3.getComponents();
         Intrinsics.checkNotNull(var10005);
         var6 = var7.invoke(
            new ItemType(
               var10004, (PatchedDataComponentMap)var10005, var3.getComponentsPatch(), new VanillaAccessorsKt$(itemType)$1(var3), false, false, null, 112, null
            ),
            var4.getCount()
         );
      }

      this.setInput(var6);
      this.recipe = this.selectedRecipe(var2);
   }

   private final void checkChanged() {
      ItemStack var6;
      label25: {
         this.lastInput = this.getInput();
         this.lastRecipe = this.recipe;
         if (this.currentContainer != null) {
            NonNullList var10001 = this.currentContainer.slots;
            Intrinsics.checkNotNullExpressionValue(this.currentContainer.slots, "");
            List var3 = (List)var10001;
            if ((List)var10001 != null) {
               Slot var4 = (Slot)var3.get(0);
               if (var4 != null) {
                  net.minecraft.world.item.ItemStack var5 = var4.getItem();
                  Intrinsics.checkNotNullExpressionValue(var5, "");
                  net.minecraft.world.item.ItemStack var1 = var5;
                  if (var5.isEmpty()) {
                     var6 = ItemStackExtensionsKt.getEMPTY(ItemStack.Companion);
                  } else {
                     ItemStack$Companion var7 = ItemStack.Companion;
                     net.minecraft.world.item.ItemStack var2 = var1;
                     Item var10004 = var2.getItem();
                     Intrinsics.checkNotNullExpressionValue(var10004, "");
                     DataComponentMap var10005 = var2.getComponents();
                     Intrinsics.checkNotNull(var10005);
                     var6 = var7.invoke(
                        new ItemType(
                           var10004,
                           (PatchedDataComponentMap)var10005,
                           var2.getComponentsPatch(),
                           new VanillaAccessorsKt$(itemType)$1(var2),
                           false,
                           false,
                           null,
                           112,
                           null
                        ),
                        var1.getCount()
                     );
                  }

                  if (var6 != null) {
                     break label25;
                  }
               }
            }
         }

         var6 = ItemStackExtensionsKt.getEMPTY(ItemStack.Companion);
      }

      this.setInput(var6);
      Integer var8 = this.selectedRecipeOrNull(this.currentContainer);
      this.recipe = var8 != null ? var8 : -1;
   }

   public final void onCraftedSink() {
      if (!this.stillCrafting) {
         Log.INSTANCE.traceIf(CutterCraftingHandlerBase::onCraftedSink$lambda$6);
         this.isRefillTick = true;
         this.stillCrafting = true;
      }
   }

   private static final Unit _init_$lambda$0(CutterCraftingHandlerBase var0) {
      var0.skipTick = false;
      return Unit.INSTANCE;
   }

   private static final Unit _init_$lambda$1(CutterCraftingHandlerBase var0) {
      var0.skipTick = true;
      return Unit.INSTANCE;
   }

   private static final Unit onCraftedSink$lambda$6(CutterCraftingHandlerBase var0) {
      Log var10000 = Log.INSTANCE;
      ItemType var1 = var0.getInput().getItemType();
      ResourceLocation var10001 = BuiltInRegistries.ITEM.getKey(var1.getItem());
      Intrinsics.checkNotNullExpressionValue(var10001, "");
      var10000.trace("input type: " + var10001);
      Log.INSTANCE.trace("input recipe: " + var0.recipe);
      return Unit.INSTANCE;
   }
}
