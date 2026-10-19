package com.chronodawn.compat;

import net.minecraft.gametest.framework.GameTestHelper;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.level.GameType;

public class CompatGameTestHelper {
   public static ServerPlayer makeMockServerPlayer(GameTestHelper helper) {
      return helper.makeMockPlayer(GameType.SURVIVAL) instanceof ServerPlayer serverPlayer ? serverPlayer : null;
   }

   private CompatGameTestHelper() {
   }
}
