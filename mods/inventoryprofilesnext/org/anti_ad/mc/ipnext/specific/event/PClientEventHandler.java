package org.anti_ad.mc.ipnext.specific.event;

import net.minecraft.network.chat.MutableComponent;
import org.anti_ad.mc.common.moreinfo.SemVer;
import org.jetbrains.annotations.NotNull;

public interface PClientEventHandler {
   @NotNull
   MutableComponent createChatMessage(@NotNull SemVer var1);
}
