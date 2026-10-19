package com.natamus.starterkit_common_fabric.data;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import net.minecraft.class_1293;
import net.minecraft.class_1304;
import net.minecraft.class_1661;
import net.minecraft.class_1799;

public class VariablesClient {
   public static HashMap<String, String> cachedStarterGearEntries = new HashMap<>();
   public static HashMap<String, String> cachedStarterKitDescriptions = new HashMap<>();
   public static String cachedStarterKitName = "";
   public static class_1661 cachedStarterKitInventory = null;
   public static List<class_1293> cachedStarterKitEffects = new ArrayList<>();
   public static HashMap<class_1304, class_1799> priorPlayerEquipment = null;
   public static List<class_1799> priorPlayerHotbar = null;
   public static boolean waitingForAnnouncement = false;
   public static boolean openChooseKitScreen = false;
}
