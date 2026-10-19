package net.p3pp3rf1y.sophisticatedcore.upgrades.xppump;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.player.Player;
import net.p3pp3rf1y.sophisticatedcore.common.gui.UpgradeContainerBase;
import net.p3pp3rf1y.sophisticatedcore.common.gui.UpgradeContainerType;
import net.p3pp3rf1y.sophisticatedcore.util.NBTHelper;

public class XpPumpUpgradeContainer extends UpgradeContainerBase<XpPumpUpgradeWrapper, XpPumpUpgradeContainer> {
   private static final String DATA_LEVEL = "level";
   private static final String DATA_DIRECTION = "direction";
   private static final String DATA_ACTION = "action";
   private static final String ACTION_TAKE_LEVELS = "take";
   private static final String ACTION_STORE_LEVELS_FROM_PLAYER = "store";
   private static final String ACTION_STORE_ALL_PLAYERS_EXPERIENCE = "storeAll";
   private static final String ACTION_TAKE_ALL_LEVELS = "takeAll";
   private static final String DATA_LEVELS_TO_STORE = "levelsToStore";
   private static final String DATA_LEVELS_TO_TAKE = "levelsToTake";
   private static final String DATA_MEND_ITEMS = "mendItems";

   public XpPumpUpgradeContainer(
      Player player, int upgradeContainerId, XpPumpUpgradeWrapper upgradeWrapper, UpgradeContainerType<XpPumpUpgradeWrapper, XpPumpUpgradeContainer> type
   ) {
      super(player, upgradeContainerId, upgradeWrapper, type);
   }

   public void setDirection(AutomationDirection direction) {
      this.upgradeWrapper.setDirection(direction);
      this.sendDataToServer(() -> NBTHelper.putEnumConstant(new CompoundTag(), "direction", direction));
   }

   public AutomationDirection getDirection() {
      return this.upgradeWrapper.getDirection();
   }

   public int getLevel() {
      return this.upgradeWrapper.getLevel();
   }

   public void setLevel(int level) {
      if (level >= 0) {
         this.upgradeWrapper.setLevel(level);
         this.sendDataToServer(() -> NBTHelper.putInt(new CompoundTag(), "level", level));
      }
   }

   public void setLevelsToStore(int levelsToStore) {
      if (levelsToStore >= 1) {
         this.upgradeWrapper.setLevelsToStore(levelsToStore);
         this.sendDataToServer(() -> NBTHelper.putInt(new CompoundTag(), "levelsToStore", levelsToStore));
      }
   }

   public void setLevelsToTake(int levelsToTake) {
      if (levelsToTake >= 1) {
         this.upgradeWrapper.setLevelsToTake(levelsToTake);
         this.sendDataToServer(() -> NBTHelper.putInt(new CompoundTag(), "levelsToTake", levelsToTake));
      }
   }

   public void takeLevels() {
      this.triggerAction("take");
   }

   public void storeLevels() {
      this.triggerAction("store");
   }

   public void storeAllExperience() {
      this.triggerAction("storeAll");
   }

   public void takeAllExperience() {
      this.triggerAction("takeAll");
   }

   private void triggerAction(String actionName) {
      this.sendDataToServer(() -> NBTHelper.putString(new CompoundTag(), "action", actionName));
   }

   public int getLevelsToStore() {
      return this.upgradeWrapper.getLevelsToStore();
   }

   public int getLevelsToTake() {
      return this.upgradeWrapper.getLevelsToTake();
   }

   public void setMendItems(boolean mendItems) {
      this.upgradeWrapper.setMendItems(mendItems);
      this.sendBooleanToServer("mendItems", mendItems);
   }

   public boolean shouldMendItems() {
      return this.upgradeWrapper.shouldMendItems();
   }

   @Override
   public void handlePacket(CompoundTag data) {
      if (data.contains("direction")) {
         this.setDirection(AutomationDirection.fromName(data.getString("direction")));
      } else if (data.contains("level")) {
         this.setLevel(data.getInt("level"));
      } else if (data.contains("levelsToStore")) {
         this.setLevelsToStore(data.getInt("levelsToStore"));
      } else if (data.contains("levelsToTake")) {
         this.setLevelsToTake(data.getInt("levelsToTake"));
      } else if (data.contains("mendItems")) {
         this.setMendItems(data.getBoolean("mendItems"));
      } else if (data.contains("action")) {
         switch (data.getString("action")) {
            case "take":
               this.upgradeWrapper.giveLevelsToPlayer(this.player);
               break;
            case "store":
               this.upgradeWrapper.takeLevelsFromPlayer(this.player);
               break;
            case "takeAll":
               this.upgradeWrapper.giveAllExperienceToPlayer(this.player);
               break;
            case "storeAll":
               this.upgradeWrapper.takeAllExperienceFromPlayer(this.player);
         }
      }
   }
}
