package net.p3pp3rf1y.sophisticatedcore.settings;

import java.util.function.Supplier;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.neoforged.neoforge.network.PacketDistributor;
import net.p3pp3rf1y.sophisticatedcore.common.gui.SettingsContainerMenu;
import net.p3pp3rf1y.sophisticatedcore.network.SyncContainerClientDataPayload;
import net.p3pp3rf1y.sophisticatedcore.util.NBTHelper;

public abstract class SettingsContainerBase<C extends ISettingsCategory<?>> {
   private final SettingsContainerMenu<?> settingsContainer;
   private final String categoryName;
   private final C category;

   protected SettingsContainerBase(SettingsContainerMenu<?> settingsContainer, String categoryName, C category) {
      this.settingsContainer = settingsContainer;
      this.categoryName = categoryName;
      this.category = category;
   }

   protected C getCategory() {
      return this.category;
   }

   public SettingsContainerMenu<?> getSettingsContainer() {
      return this.settingsContainer;
   }

   public void sendIntToServer(String key, int value) {
      this.sendDataToServer(() -> {
         CompoundTag data = new CompoundTag();
         data.putInt(key, value);
         return data;
      });
   }

   public void sendStringToServer(String key, String value) {
      this.sendDataToServer(() -> {
         CompoundTag data = new CompoundTag();
         data.putString(key, value);
         return data;
      });
   }

   public void sendBooleanToServer(String key, boolean value) {
      this.sendDataToServer(() -> NBTHelper.putBoolean(new CompoundTag(), key, value));
   }

   public void sendDataToServer(Supplier<CompoundTag> supplyData) {
      if (!this.isServer()) {
         CompoundTag data = supplyData.get();
         data.putString("categoryName", this.categoryName);
         PacketDistributor.sendToServer(new SyncContainerClientDataPayload(data), new CustomPacketPayload[0]);
      }
   }

   protected boolean isServer() {
      return !this.settingsContainer.getPlayer().level().isClientSide;
   }

   public abstract void handlePacket(CompoundTag var1);
}
