package com.natamus.collective_common_fabric.data;

import com.natamus.collective_common_fabric.objects.SAMObject;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.CopyOnWriteArrayList;
import net.minecraft.class_1299;
import net.minecraft.class_1792;
import net.minecraft.class_1802;
import net.minecraft.class_2246;
import net.minecraft.class_2248;
import net.minecraft.class_2591;
import net.minecraft.class_3620;
import net.minecraft.class_5819;

public class GlobalVariables {
   public static Random random = new Random();
   public static class_5819 randomSource = class_5819.method_43047();
   public static CopyOnWriteArrayList<class_1299<?>> activeSAMEntityTypes = new CopyOnWriteArrayList<>();
   public static CopyOnWriteArrayList<SAMObject> globalSAMs = new CopyOnWriteArrayList<>();
   public static List<String> moddedvillagers = new ArrayList<>(
      Arrays.asList(
         "Villager",
         "WanderingTrader",
         "Guard",
         "Farlander",
         "ElderFarlander",
         "Wanderer",
         "ThiefWanderingTrader",
         "PlagueDoctor",
         "GoblinTrader",
         "VeinGoblinTrader",
         "RedMerchant",
         "WanderingWizard",
         "WanderingWinemaker",
         "WanderingBaker",
         "Bowman",
         "Crossbowman",
         "Horseman",
         "Nomad",
         "Recruit",
         "RecruitShieldman",
         "Gatekeeper",
         "VillagerEntityMCA",
         "Priest"
      )
   );
   public static List<String> areaNames = new ArrayList<>();
   public static List<String> femaleNames = new ArrayList<>();
   public static List<String> maleNames = new ArrayList<>();
   public static List<String> lingerMessages = new ArrayList<>();
   public static HashMap<class_1299<?>, List<class_1792>> entitydrops = null;
   public static Map<class_2248, class_2591<?>> blocksWithTileEntity = new HashMap<>();
   public static List<class_2248> growblocks = Arrays.asList(
      class_2246.field_10219,
      class_2246.field_10566,
      class_2246.field_10253,
      class_2246.field_10255,
      class_2246.field_10402,
      class_2246.field_10520,
      class_2246.field_10102,
      class_2246.field_10534
   );
   public static List<class_2248> stoneblocks = Arrays.asList(
      class_2246.field_10445,
      class_2246.field_9989,
      class_2246.field_10340,
      class_2246.field_10056,
      class_2246.field_10552,
      class_2246.field_10416,
      class_2246.field_10360,
      class_2246.field_10255,
      class_2246.field_10115,
      class_2246.field_10093,
      class_2246.field_10508,
      class_2246.field_10346,
      class_2246.field_10474,
      class_2246.field_10289,
      class_2246.field_9979,
      class_2246.field_10292,
      class_2246.field_10361,
      class_2246.field_10467,
      class_2246.field_10344,
      class_2246.field_10117,
      class_2246.field_10518,
      class_2246.field_10483,
      class_2246.field_10471,
      class_2246.field_10462,
      class_2246.field_10515,
      class_2246.field_10266,
      class_2246.field_9986
   );
   public static List<class_1792> stoneblockitems = Arrays.asList(
      class_1802.field_20412,
      class_1802.field_20392,
      class_1802.field_20391,
      class_1802.field_20395,
      class_1802.field_8525,
      class_1802.field_8343,
      class_1802.field_20389,
      class_1802.field_8110,
      class_1802.field_20407,
      class_1802.field_20411,
      class_1802.field_20401,
      class_1802.field_20403,
      class_1802.field_20394,
      class_1802.field_20397,
      class_1802.field_20384,
      class_1802.field_8552,
      class_1802.field_20385,
      class_1802.field_20388,
      class_1802.field_20408,
      class_1802.field_8822,
      class_1802.field_20409,
      class_1802.field_20387,
      class_1802.field_20399,
      class_1802.field_20400,
      class_1802.field_8328,
      class_1802.field_20398,
      class_1802.field_20410
   );
   public static List<class_3620> surfacematerials = Arrays.asList(class_3620.field_16019, class_3620.field_16016);

   public static void generateHashMaps() {
      blocksWithTileEntity.put(class_2246.field_17350, class_2591.field_17380);
      blocksWithTileEntity.put(class_2246.field_10121, class_2591.field_11911);
   }
}
