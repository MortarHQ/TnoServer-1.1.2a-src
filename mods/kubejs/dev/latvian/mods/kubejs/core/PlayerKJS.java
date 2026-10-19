package dev.latvian.mods.kubejs.core;

import com.mojang.authlib.GameProfile;
import dev.latvian.mods.kubejs.item.ItemHandlerUtils;
import dev.latvian.mods.kubejs.player.KubeJSInventoryListener;
import dev.latvian.mods.kubejs.player.PlayerStatsJS;
import dev.latvian.mods.kubejs.stages.Stages;
import dev.latvian.mods.kubejs.util.NotificationToastData;
import dev.latvian.mods.rhino.util.RemapPrefixForJS;
import net.minecraft.network.chat.Component;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.phys.Vec3;
import net.neoforged.neoforge.common.util.FakePlayer;
import org.jetbrains.annotations.Nullable;

@RemapPrefixForJS("kjs$")
public interface PlayerKJS extends LivingEntityKJS, DataSenderKJS, WithAttachedData<Player> {
   default Player kjs$self() {
      return (Player)this;
   }

   default Stages kjs$getStages() {
      throw new NoMixinException();
   }

   default PlayerStatsJS kjs$getStats() {
      throw new NoMixinException();
   }

   default boolean kjs$isMiningBlock() {
      throw new NoMixinException();
   }

   @Override
   default boolean kjs$isPlayer() {
      return true;
   }

   default boolean kjs$isFake() {
      return this instanceof FakePlayer;
   }

   @Override
   default GameProfile kjs$getProfile() {
      return this.kjs$self().getGameProfile();
   }

   default InventoryKJS kjs$getInventory() {
      throw new NoMixinException();
   }

   default InventoryKJS kjs$getCraftingGrid() {
      throw new NoMixinException();
   }

   default void kjs$sendInventoryUpdate() {
      this.kjs$self().getInventory().setChanged();
      this.kjs$self().inventoryMenu.getCraftSlots().setChanged();
      this.kjs$self().inventoryMenu.broadcastChanges();
   }

   default void kjs$give(ItemStack item) {
      ItemHandlerUtils.giveItemToPlayer(this.kjs$self(), item, -1);
   }

   default void kjs$giveInHand(ItemStack item) {
      ItemHandlerUtils.giveItemToPlayer(this.kjs$self(), item, this.kjs$getSelectedSlot());
   }

   default int kjs$getSelectedSlot() {
      return this.kjs$self().getInventory().selected;
   }

   default void kjs$setSelectedSlot(int index) {
      this.kjs$self().getInventory().selected = Mth.clamp(index, 0, 8);
   }

   default ItemStack kjs$getMouseItem() {
      return this.kjs$self().containerMenu.getCarried();
   }

   default void kjs$setMouseItem(ItemStack item) {
      this.kjs$self().containerMenu.setCarried(item);
   }

   @Override
   default void kjs$setStatusMessage(Component message) {
      this.kjs$self().displayClientMessage(message, true);
   }

   @Override
   default void kjs$spawn() {
   }

   default void kjs$addFood(int f, float m) {
      this.kjs$self().getFoodData().eat(f, m);
   }

   default int kjs$getFoodLevel() {
      return this.kjs$self().getFoodData().getFoodLevel();
   }

   default void kjs$setFoodLevel(int foodLevel) {
      this.kjs$self().getFoodData().setFoodLevel(foodLevel);
   }

   default float kjs$getSaturation() {
      return this.kjs$self().getFoodData().getSaturationLevel();
   }

   default void kjs$setSaturation(float saturation) {
      this.kjs$self().getFoodData().setSaturation(saturation);
   }

   default void kjs$addExhaustion(float exhaustion) {
      this.kjs$self().causeFoodExhaustion(exhaustion);
   }

   default void kjs$addXP(int xp) {
      this.kjs$self().giveExperiencePoints(xp);
   }

   default void kjs$addXPLevels(int l) {
      this.kjs$self().giveExperienceLevels(l);
   }

   default void kjs$setXp(int xp) {
      this.kjs$self().totalExperience = 0;
      this.kjs$self().experienceProgress = 0.0F;
      this.kjs$self().experienceLevel = 0;
      this.kjs$self().giveExperiencePoints(xp);
   }

   default int kjs$getXp() {
      return this.kjs$self().totalExperience;
   }

   default void kjs$setXpLevel(int l) {
      this.kjs$self().totalExperience = 0;
      this.kjs$self().experienceProgress = 0.0F;
      this.kjs$self().experienceLevel = 0;
      this.kjs$self().giveExperienceLevels(l);
   }

   default int kjs$getXpLevel() {
      return this.kjs$self().experienceLevel;
   }

   default void kjs$boostElytraFlight() {
      if (this.kjs$self().isFallFlying()) {
         Vec3 v = this.kjs$self().getLookAngle();
         double d0 = 1.5;
         double d1 = 0.1;
         Vec3 m = this.kjs$self().getDeltaMovement();
         this.kjs$self().setDeltaMovement(m.add(v.x * d1 + (v.x * d0 - m.x) * 0.5, v.y * d1 + (v.y * d0 - m.y) * 0.5, v.z * d1 + (v.z * d0 - m.z) * 0.5));
      }
   }

   default AbstractContainerMenu kjs$getOpenInventory() {
      return this.kjs$self().containerMenu;
   }

   default void kjs$addItemCooldown(Item item, int ticks) {
      this.kjs$self().getCooldowns().addCooldown(item, ticks);
   }

   default KubeJSInventoryListener kjs$getInventoryChangeListener() {
      throw new NoMixinException();
   }

   default void kjs$notify(NotificationToastData builder) {
      throw new NoMixinException();
   }

   default void kjs$notify(Component title, @Nullable Component text) {
      this.kjs$notify(NotificationToastData.ofTitle(title, text));
   }
}
