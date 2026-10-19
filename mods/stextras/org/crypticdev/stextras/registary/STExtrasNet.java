package org.crypticdev.stextras.registary;

import org.crypticdev.stextras.net.STExtrasQuestSync;
import org.crypticdev.stextras.net.C2S.OpenPrestigeScreenPacket;
import org.crypticdev.stextras.net.C2S.OpenSkillLockScreenPacket;
import org.crypticdev.stextras.net.C2S.SkillLockPacket;
import org.crypticdev.stextras.net.C2S.SkillUnlockPacket;
import org.crypticdev.stextras.net.S2C.STExtrasBossKillsSync;
import org.crypticdev.stextras.net.S2C.STExtrasPlayerStorageSync;
import org.crypticdev.stextras.net.S2C.SyncQuestDefinitionsPacket;
import org.crypticdev.stextras.utils.VersionControl;

public final class STExtrasNet {
   private STExtrasNet() {
   }

   public static void init() {
      STExtrasQuestSync.registerCommon();
      STExtrasPlayerStorageSync.registerCommon();
      STExtrasBossKillsSync.registerCommon();
      OpenPrestigeScreenPacket.registerCommon();
      OpenPrestigeScreenPacket.registerServer();
      OpenSkillLockScreenPacket.registerCommon();
      OpenSkillLockScreenPacket.registerServer();
      SkillLockPacket.registerCommon();
      SkillLockPacket.registerServer();
      SkillUnlockPacket.registerCommon();
      SkillUnlockPacket.registerServer();
      SyncQuestDefinitionsPacket.registerCommon();
      VersionControl.registerCommon();
   }

   public static void initClient() {
      STExtrasQuestSync.registerClient();
      STExtrasPlayerStorageSync.registerClient();
      STExtrasBossKillsSync.registerClient();
      SyncQuestDefinitionsPacket.registerClient();
      VersionControl.registerClient();
   }
}
