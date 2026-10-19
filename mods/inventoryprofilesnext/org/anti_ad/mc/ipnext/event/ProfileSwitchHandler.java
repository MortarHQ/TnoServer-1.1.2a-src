package org.anti_ad.mc.ipnext.event;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.Map.Entry;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.SourceDebugExtension;
import kotlin.ranges.IntRange;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.screens.inventory.InventoryScreen;
import net.minecraft.core.NonNullList;
import net.minecraft.core.component.DataComponentMap;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.component.PatchedDataComponentMap;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.nbt.Tag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ItemLike;
import org.anti_ad.mc.alias.item.ItemExKt;
import org.anti_ad.mc.alias.text.TextExKt;
import org.anti_ad.mc.common.IInputHandler;
import org.anti_ad.mc.common.vanilla.Vanilla;
import org.anti_ad.mc.common.vanilla.VanillaUtil;
import org.anti_ad.mc.ipnext.Log;
import org.anti_ad.mc.ipnext.config.EditProfiles;
import org.anti_ad.mc.ipnext.config.GuiSettings;
import org.anti_ad.mc.ipnext.config.Hotkeys;
import org.anti_ad.mc.ipnext.ingame.VanillaAccessorsKt$(itemType)$1;
import org.anti_ad.mc.ipnext.item.ComponentUtils;
import org.anti_ad.mc.ipnext.item.ItemStack$Companion;
import org.anti_ad.mc.ipnext.item.ItemStackExtensionsKt;
import org.anti_ad.mc.ipnext.item.ItemType;
import org.anti_ad.mc.ipnext.parser.ProfilesLoader;
import org.anti_ad.mc.ipnext.profiles.config.ProfileComponentData;
import org.anti_ad.mc.ipnext.profiles.config.ProfileData;
import org.anti_ad.mc.ipnext.profiles.config.ProfileItemData;
import org.anti_ad.mc.ipnext.profiles.config.ProfileSlot;
import org.anti_ad.mc.ipnext.profiles.config.ProfileSlotId;
import org.anti_ad.mc.ipnext.profiles.config.ProfilesConfigParserKt;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@SourceDebugExtension(
   "SMAP\nProfileSwitchHandler.kt\nKotlin\n*S Kotlin\n*F\n+ 1 ProfileSwitchHandler.kt\norg/anti_ad/mc/ipnext/event/ProfileSwitchHandler\n+ 2 common.kt\norg/anti_ad/mc/common/extensions/CommonKt\n+ 3 VanillaAccessors.kt\norg/anti_ad/mc/ipnext/ingame/VanillaAccessorsKt\n+ 4 _Collections.kt\nkotlin/collections/CollectionsKt___CollectionsKt\n+ 5 ItemTypeExtensions.kt\norg/anti_ad/mc/ipnext/item/ItemTypeExtensionsKt\n+ 6 fake.kt\nkotlin/jvm/internal/FakeKt\n*L\n1#1,414:1\n97#2:415\n127#3:416\n78#3,11:418\n71#3:429\n66#3,7:430\n78#3:437\n90#3:438\n129#3,2:462\n1869#4:417\n1617#4,9:446\n1869#4:455\n1870#4:457\n1626#4:458\n1870#4:459\n1869#4,2:460\n1869#4,2:464\n1878#4,3:466\n125#5:439\n123#5:440\n106#5:441\n127#5:442\n106#5:443\n131#5:444\n112#5:445\n1#6:456\n*S KotlinDebug\n*F\n+ 1 ProfileSwitchHandler.kt\norg/anti_ad/mc/ipnext/event/ProfileSwitchHandler\n*L\n92#1:415\n99#1:416\n102#1:418,11\n102#1:429\n102#1:430,7\n103#1:437\n103#1:438\n131#1:462,2\n100#1:417\n107#1:446,9\n107#1:455\n107#1:457\n107#1:458\n100#1:459\n123#1:460,2\n164#1:464,2\n338#1:466,3\n106#1:439\n106#1:440\n106#1:441\n106#1:442\n106#1:443\n107#1:444\n107#1:445\n107#1:456\n*E\n"
)
public final class ProfileSwitchHandler implements IInputHandler {
   @NotNull
   public static final ProfileSwitchHandler INSTANCE = new ProfileSwitchHandler();
   private static int activeProfileId;
   private static boolean doApplyProfile;
   @NotNull
   private static final List allSlots = CollectionsKt.plus(
      CollectionsKt.toList((Iterable)(new IntRange(9, 45))), CollectionsKt.toList((Iterable)(new IntRange(5, 8)))
   );
   @NotNull
   private static final List hotBarSlots = CollectionsKt.toList((Iterable)(new IntRange(36, 44)));
   @NotNull
   private static final ProfileData EMPTY_PROFILE;
   @NotNull
   private static ProfileData targetProfile = EMPTY_PROFILE = new ProfileData("", ProfileSlotId.NONE, CollectionsKt.emptyList(), false);
   @NotNull
   private static final List monitors = new ArrayList();

   private ProfileSwitchHandler() {
   }

   @Nullable
   public final String getActiveProfileName() {
      return targetProfile.getValid() ? targetProfile.getName() : null;
   }

   public final int getActiveProfileId() {
      return activeProfileId;
   }

   public final void setActiveProfileId(int var1) {
      activeProfileId = var1;
   }

   public final void onTickInGame() {
      Screen var1 = Vanilla.INSTANCE.screen();
      if (VanillaUtil.INSTANCE.inGame() && doApplyProfile && (var1 == null || var1 instanceof InventoryScreen)) {
         doApplyProfile = false;
         this.applyProfile();
      }
   }

   private final ProfileComponentData toProfileComponentDataOrNull(DataComponentType var1, Optional var2) {
      Tag var10000 = ComponentUtils.INSTANCE.toFilteredNbtOrNull(var1, var2);
      if (var10000 != null) {
         Tag var3 = var10000;
         return new ProfileComponentData(ProfilesConfigParserKt.get_asIdentifier_/* $VF was: get(asIdentifier) */(var1.toString()), var3);
      } else {
         return null;
      }
   }

   @NotNull
   public final ProfileData createProfileFromCurrentState() {
      Inventory var1 = Vanilla.INSTANCE.playerInventory();
      ProfileSlotId var10001 = ProfileSlotId.Companion.valueOf(var1.selected + 36);
      List var16;
      List var2 = var16 = new ArrayList();
      ProfileSlotId var15 = var10001;
      String var14 = "Saved";
      Iterator var3 = allSlots.iterator();

      while (var3.hasNext()) {
         int var4 = ((Number)var3.next()).intValue();
         if (ProfileSlotId.Companion.valueOf(var4) != ProfileSlotId.NONE) {
            NonNullList var10000 = ((AbstractContainerMenu)Vanilla.INSTANCE.playerContainer()).slots;
            Intrinsics.checkNotNullExpressionValue(var10000, "");
            ItemStack var39 = ((Slot)((List)var10000).get(var4)).getItem();
            Intrinsics.checkNotNullExpressionValue(var39, "");
            ItemStack var7 = var39;
            org.anti_ad.mc.ipnext.item.ItemStack var40;
            if (var39.isEmpty()) {
               var40 = ItemStackExtensionsKt.getEMPTY(org.anti_ad.mc.ipnext.item.ItemStack.Companion);
            } else {
               ItemStack$Companion var41 = org.anti_ad.mc.ipnext.item.ItemStack.Companion;
               ItemStack var8 = var7;
               Item var10003 = var8.getItem();
               Intrinsics.checkNotNullExpressionValue(var10003, "");
               DataComponentMap var10004 = var8.getComponents();
               Intrinsics.checkNotNull(var10004);
               var40 = var41.invoke(
                  new ItemType(
                     var10003,
                     (PatchedDataComponentMap)var10004,
                     var8.getComponentsPatch(),
                     new VanillaAccessorsKt$(itemType)$1(var8),
                     false,
                     false,
                     null,
                     112,
                     null
                  ),
                  var7.getCount()
               );
            }

            org.anti_ad.mc.ipnext.item.ItemStack var9 = var40;
            var10000 = ((AbstractContainerMenu)Vanilla.INSTANCE.playerContainer()).slots;
            Intrinsics.checkNotNullExpressionValue(var10000, "");
            ItemStack var43 = ((Slot)((List)var10000).get(var4)).getItem();
            Intrinsics.checkNotNullExpressionValue(var43, "");
            ItemStack var5 = var43;
            if (!ItemStackExtensionsKt.isEmpty(var9)) {
               var10001 = ProfileSlotId.Companion.valueOf(var4);
               List var6;
               List var24 = var6 = new ArrayList();
               ProfileSlotId var19 = var10001;
               List var10 = var2;
               String var46;
               if (EditProfiles.INSTANCE.getINCLUDE_CUSTOM_NAME().getBooleanValue()) {
                  ItemType var27;
                  ItemType var11 = var27 = var9.getItemType();
                  ItemStack var44 = new ItemStack;
                  ItemLike var10002 = (ItemLike)var11.getItem();
                  PatchedDataComponentMap var55 = var11.getTag();
                  if (var55 == null) {
                     DataComponentMap var56 = DataComponentMap.EMPTY;
                     Intrinsics.checkNotNull(DataComponentMap.EMPTY);
                     var55 = (PatchedDataComponentMap)var56;
                  }

                  var44./* $VF: Unable to resugar constructor */<init>(var10002, 1, var55);
                  if (var44.getComponents().get(DataComponents.CUSTOM_NAME) != null) {
                     var11 = var27;
                     ItemStack var45 = new ItemStack;
                     var10002 = (ItemLike)var11.getItem();
                     var55 = var11.getTag();
                     if (var55 == null) {
                        DataComponentMap var58 = DataComponentMap.EMPTY;
                        Intrinsics.checkNotNull(DataComponentMap.EMPTY);
                        var55 = (PatchedDataComponentMap)var58;
                     }

                     var45./* $VF: Unable to resugar constructor */<init>(var10002, 1, var55);
                     var46 = var45.getDisplayName().getString();
                     Intrinsics.checkNotNullExpressionValue(var46, "");
                  } else {
                     var46 = "";
                  }
               } else {
                  var46 = "";
               }

               String var12 = var46;
               ItemType var35 = var9.getItemType();
               ResourceLocation var50 = BuiltInRegistries.ITEM.getKey(var35.getItem());
               Intrinsics.checkNotNullExpressionValue(var50, "");
               String var51 = var50.toString();
               Intrinsics.checkNotNullExpressionValue(var51, "");
               Set var54 = ItemExKt.get_componentChanges_/* $VF was: get(componentChanges) */(var5).entrySet();
               Intrinsics.checkNotNullExpressionValue(var54, "");
               Iterable var28 = var54;
               String var33 = var12;
               var12 = var51;
               List var13 = var24;
               Iterable var36 = var28;
               var var21 = new ArrayList();
               Iterator var25 = var36.iterator();

               while (var25.hasNext()) {
                  Entry var29;
                  Intrinsics.checkNotNull(var29 = (Entry)var25.next());
                  DataComponentType var37 = (DataComponentType)var29.getKey();
                  Optional var30 = (Optional)var29.getValue();
                  ProfileSwitchHandler var47 = INSTANCE;
                  Intrinsics.checkNotNull(var37);
                  Intrinsics.checkNotNull(var30);
                  ProfileComponentData var48 = var47.toProfileComponentDataOrNull(var37, var30);
                  if (var48 != null) {
                     ProfileComponentData var31 = var48;
                     var21.add(var31);
                  }
               }

               List var22 = var21;
               String var26 = var33;
               String var32 = var12;
               var13.add(new ProfileItemData(var32, var26, var22));
               List var20 = var6;
               ProfileSlotId var23 = var19;
               var10.add(new ProfileSlot(var23, var20));
            }
         }
      }

      ProfileSlotId var17 = var15;
      String var18 = var14;
      return new ProfileData(var18, var17, var16, false, 8, null);
   }

   private final void applyProfile() {
      if (GuiSettings.INSTANCE.getENABLE_PROFILES_ANNOUNCEMENT().getBooleanValue()) {
         Vanilla.INSTANCE.inGameHud().setOverlayMessage(TextExKt.getLiteral(targetProfile.getName()), false);
      }

      List var1 = allSlots;
      Iterator var2 = monitors.iterator();

      while (var2.hasNext()) {
         ProfileSwitchHandler$ProfileMonitor var3;
         if ((var3 = (ProfileSwitchHandler$ProfileMonitor)var2.next()).findAndSwap(var1)) {
            var1 = CollectionsKt.minus(var1, var3.getSlot());
         }
      }

      LockedSlotKeeper.INSTANCE.init();
      if (targetProfile.getActive() != ProfileSlotId.NONE) {
         int var5 = targetProfile.getActive().getSlotId();
         Inventory var4 = Vanilla.INSTANCE.playerInventory();
         int var6 = var5 - 36;
         var4.selected = var6;
      }
   }

   @NotNull
   public final ProfileData getEMPTY_PROFILE() {
      return EMPTY_PROFILE;
   }

   @NotNull
   public final List getMonitors() {
      return monitors;
   }

   public final void applyCurrent(boolean var1) {
      doApplyProfile = targetProfile.getValid();
   }

   public final void reloadActiveProfile() {
      String var1 = targetProfile.getValid() ? this.getActiveProfileName() : null;
      activeProfileId = -1;
      this.init(EMPTY_PROFILE);
      if (var1 != null) {
         var1 = var1;
         INSTANCE.switchToProfileName(var1);
         doApplyProfile = false;
      }
   }

   public final void init(@NotNull ProfileData var1) {
      Intrinsics.checkNotNullParameter(var1, "");
      if (Intrinsics.areEqual(targetProfile, var1)) {
         targetProfile = var1;
         doApplyProfile = var1.getValid();
      } else {
         monitors.clear();
         targetProfile = var1;
         if (var1.getValid()) {
            for (ProfileSlot var2 : targetProfile.getSlots()) {
               monitors.add(new ProfileSwitchHandler$ProfileMonitor(var2.getId().getSlotId(), var2.getItems()));
            }

            doApplyProfile = true;
         }
      }
   }

   public final boolean onInput(int var1, int var2) {
      if (!VanillaUtil.INSTANCE.inGame()) {
         return false;
      }

      Screen var3;
      if ((var3 = Vanilla.INSTANCE.screen()) == null || var3 instanceof InventoryScreen) {
         if (Hotkeys.INSTANCE.getAPPLY_PROFILE().isActivated()) {
            applyCurrent$default(this, false, 1, null);
            return true;
         }

         if (Hotkeys.INSTANCE.getNEXT_PROFILE().isActivated()) {
            nextProfile$default(this, false, 1, null);
            return true;
         }

         if (Hotkeys.INSTANCE.getPREV_PROFILE().isActivated()) {
            prevProfile$default(this, false, 1, null);
            return true;
         }

         if (Hotkeys.INSTANCE.getPROFILE_1().isActivated()) {
            String var7;
            if (!Intrinsics.areEqual(
               var7 = EditProfiles.INSTANCE.getQUICK_SLOT_1_PROFILE().getValue(), EditProfiles.INSTANCE.getQUICK_SLOT_1_PROFILE().getDefaultValue()
            )) {
               this.switchToProfileName(var7);
            }

            return true;
         }

         if (Hotkeys.INSTANCE.getPROFILE_2().isActivated()) {
            String var6;
            if (!Intrinsics.areEqual(
               var6 = EditProfiles.INSTANCE.getQUICK_SLOT_2_PROFILE().getValue(), EditProfiles.INSTANCE.getQUICK_SLOT_2_PROFILE().getDefaultValue()
            )) {
               this.switchToProfileName(var6);
            }

            return true;
         }

         if (Hotkeys.INSTANCE.getPROFILE_3().isActivated()) {
            String var5;
            if (!Intrinsics.areEqual(
               var5 = EditProfiles.INSTANCE.getQUICK_SLOT_3_PROFILE().getValue(), EditProfiles.INSTANCE.getQUICK_SLOT_3_PROFILE().getDefaultValue()
            )) {
               this.switchToProfileName(var5);
            }

            return true;
         }

         if (Hotkeys.INSTANCE.getSAVE_AS_PROFILE().isActivated()) {
            ProfileData var4 = this.createProfileFromCurrentState();
            ProfilesLoader.INSTANCE.getSavedProfiles().add(var4);
            ProfilesLoader.INSTANCE.save();
            VanillaUtil.INSTANCE.chat(TextExKt.getTranslatable("inventoryprofiles.profiles.created_new_saved", new Object[0]));
            Log.INSTANCE.trace("\n" + var4);
            return true;
         }
      }

      return false;
   }

   private final void switchToProfileName(String var1) {
      int var2;
      if ((var2 = this.byName(var1)) != -1) {
         activeProfileId = var2;
         this.init((ProfileData)ProfilesLoader.INSTANCE.getProfiles().get(var2));
      }
   }

   public final void prevProfile(boolean var1) {
      if (!ProfilesLoader.INSTANCE.getProfiles().isEmpty()) {
         this.init((ProfileData)ProfilesLoader.INSTANCE.getProfiles().get(this.nextOrFirst()));
      }
   }

   public final void nextProfile(boolean var1) {
      if (!ProfilesLoader.INSTANCE.getProfiles().isEmpty()) {
         this.init((ProfileData)ProfilesLoader.INSTANCE.getProfiles().get(this.prevOrLast()));
      }
   }

   private final int byName(String var1) {
      Iterable var2 = ProfilesLoader.INSTANCE.getProfiles();
      int var3 = 0;

      for (ProfileData var4 : var2) {
         int var5;
         if ((var5 = var3++) < 0) {
            CollectionsKt.throwIndexOverflow();
         }

         var4 = var4;
         var5 = var5;
         if (Intrinsics.areEqual(var4.getName(), var1)) {
            return var5;
         }
      }

      return -1;
   }

   private final int nextOrFirst() {
      int var1;
      return activeProfileId = (var1 = activeProfileId + 1) < ProfilesLoader.INSTANCE.getProfiles().size() ? var1 : 0;
   }

   private final int prevOrLast() {
      int var1;
      return activeProfileId = (var1 = activeProfileId - 1) >= 0 ? var1 : ProfilesLoader.INSTANCE.getProfiles().size() - 1;
   }
}
