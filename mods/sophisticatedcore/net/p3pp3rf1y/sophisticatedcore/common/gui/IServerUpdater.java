package net.p3pp3rf1y.sophisticatedcore.common.gui;

import java.util.function.Supplier;
import net.minecraft.nbt.CompoundTag;

public interface IServerUpdater {
   void sendBooleanToServer(String var1, boolean var2);

   void sendDataToServer(Supplier<CompoundTag> var1);
}
