package com.faboslav.friendsandfoes.common.util;

import net.minecraft.world.entity.raid.Raid.RaiderType;

public final class CustomRaidMember {
   public static final String ICEOLOGER_INTERNAL_NAME = "ICEOLOGER";
   public static final int[] ICEOLOGER_COUNT_IN_WAVE = new int[]{0, 0, 0, 0, 1, 1, 0, 1};
   public static RaiderType ICEOLOGER;
   public static final String ILLUSIONER_INTERNAL_NAME = "ILLUSIONER";
   public static final int[] ILLUSIONER_COUNT_IN_WAVE = new int[]{0, 0, 0, 0, 1, 0, 1, 1};
   public static RaiderType ILLUSIONER;

   static {
      RaiderType.values();
   }
}
