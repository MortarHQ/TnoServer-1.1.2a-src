package com.github.b4ndithelps.tennogamenolife.race.ExMachina;

import com.github.b4ndithelps.tennogamenolife.registry.item.Tenreincarnationitems;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

public class ExMachinaDecorativeManager {
   private static final Map<UUID, ExMachinaDecorativeData> playerDecorativeData = new HashMap<>();

   public static ExMachinaDecorativeData getDecorativeData(Player player) {
      UUID playerId = player.getUUID();
      return playerDecorativeData.computeIfAbsent(playerId, k -> new ExMachinaDecorativeData());
   }

   public static ExMachinaDecorativeManager.ArmorAnimationState getArmorAnimationState(Player player) {
      ExMachinaDecorativeData data = getDecorativeData(player);
      if (data.isCancelAnimationPlaying()) {
         return ExMachinaDecorativeManager.ArmorAnimationState.CANCEL_WEAPON;
      } else {
         return data.hasWeapon() ? ExMachinaDecorativeManager.ArmorAnimationState.WEAPON : ExMachinaDecorativeManager.ArmorAnimationState.IDLE;
      }
   }

   public static int getAnimationTimer(Player player) {
      ExMachinaDecorativeData data = getDecorativeData(player);
      if (data.isCancelAnimationPlaying()) {
         return data.getCancelAnimationTimer();
      } else {
         return data.hasWeapon() ? data.getWeaponAnimationTimer() : player.tickCount;
      }
   }

   public static void updateDecorativeState(Player player) {
      ExMachinaDecorativeData data = getDecorativeData(player);
      boolean previousHasWeapon = data.hasWeapon();
      boolean currentHasWeapon = isHoldingExMachinasWeapon(player);
      data.setHasWeapon(currentHasWeapon);
      if (currentHasWeapon && !previousHasWeapon) {
         data.setWeaponAnimationTimer(0);
         data.setCancelAnimationPlaying(false);
      } else if (!currentHasWeapon && previousHasWeapon) {
         data.setCancelAnimationTimer(0);
         data.setCancelAnimationPlaying(true);
      }

      if (data.hasWeapon()) {
         data.incrementWeaponAnimationTimer();
      }

      if (data.isCancelAnimationPlaying()) {
         data.incrementCancelAnimationTimer();
         if (data.getCancelAnimationTimer() >= 12) {
            data.setCancelAnimationPlaying(false);
            data.setCancelAnimationTimer(0);
         }
      }
   }

   public static boolean isHoldingExMachinasWeapon(Player player) {
      ItemStack mainHand = player.getMainHandItem();
      ItemStack offHand = player.getOffhandItem();
      return mainHand.is((Item)Tenreincarnationitems.EX_MACHINAS_WEAPON.get()) || offHand.is((Item)Tenreincarnationitems.EX_MACHINAS_WEAPON.get());
   }

   public static boolean shouldShowDecorative(Player player) {
      return true;
   }

   public static void saveDecorativeData(Player player, CompoundTag nbt) {
      ExMachinaDecorativeData data = getDecorativeData(player);
      CompoundTag decorativeTag = new CompoundTag();
      decorativeTag.putBoolean("HasWeapon", data.hasWeapon());
      decorativeTag.putBoolean("IsCancelAnimationPlaying", data.isCancelAnimationPlaying());
      decorativeTag.putInt("WeaponAnimationTimer", data.getWeaponAnimationTimer());
      decorativeTag.putInt("CancelAnimationTimer", data.getCancelAnimationTimer());
      nbt.put("ExMachinaDecorative", decorativeTag);
   }

   public static void loadDecorativeData(Player player, CompoundTag nbt) {
      if (nbt.contains("ExMachinaDecorative")) {
         CompoundTag decorativeTag = nbt.getCompound("ExMachinaDecorative");
         ExMachinaDecorativeData data = getDecorativeData(player);
         data.setHasWeapon(decorativeTag.getBoolean("HasWeapon"));
         data.setCancelAnimationPlaying(decorativeTag.getBoolean("IsCancelAnimationPlaying"));
         data.setWeaponAnimationTimer(decorativeTag.getInt("WeaponAnimationTimer"));
         data.setCancelAnimationTimer(decorativeTag.getInt("CancelAnimationTimer"));
      }
   }

   public static void cleanupPlayerData(UUID playerId) {
      playerDecorativeData.remove(playerId);
   }

   public static String[] getDecorativeRaceList() {
      return new String[]{
         "MachineRace - 基础机铠种",
         "BefehlerRace - 指挥机铠种",
         "KampfRace - 战斗机铠种",
         "ZeichenRace - 标记机铠种",
         "PrueferRace - 检验机铠种",
         "SeherRace - 观察机铠种",
         "EinzeigRace - 单体机铠种",
         "EmirEinsRace - 统领机铠种",
         "HorouRace - 破坏机铠种",
         "HubieDoraRace - 休比朵拉机铠种",
         "PuraiyaRace - 普莱雅机铠种"
      };
   }

   public static String getDecorativeDescription() {
      return "ExMachina种族装饰系统功能：\n\n1. 机铠装饰显示\n   - 所有ExMachina种族玩家都会显示机铠装饰\n   - 包括翅膀、光环、机械部件等\n\n2. 武器动画系统\n   - 拿出机铠种之械时播放武器动画\n   - 收起武器时播放取消动画\n   - 待机时播放待机动画\n\n3. 数据持久化\n   - 装饰状态会保存到玩家数据\n   - 重新登录后状态保持\n\n4. 性能优化\n   - 使用缓存系统减少计算\n   - 自动清理离线玩家数据\n";
   }

   public enum ArmorAnimationState {
      IDLE,
      WEAPON,
      CANCEL_WEAPON;
   }
}
