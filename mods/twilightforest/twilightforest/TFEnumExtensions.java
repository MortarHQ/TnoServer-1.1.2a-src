package twilightforest;

import net.minecraft.ChatFormatting;
import net.minecraft.world.item.Items;
import twilightforest.beanification.Autowired;
import twilightforest.init.TFBlocks;
import twilightforest.init.TFItems;
import twilightforest.init.TFSounds;
import twilightforest.util.ModidPrefixUtil;
import twilightforest.world.components.BiomeColorAlgorithms;

public class TFEnumExtensions {
   @Autowired
   private static BiomeColorAlgorithms biomeColorAlgorithms;
   private static final ModidPrefixUtil modidPrefixUtil = new ModidPrefixUtil();

   public static Object DamageEffects_PINCH(int idx, Class<?> type) {
      return type.cast(switch (idx) {
         case 0 -> modidPrefixUtil.stringPrefix("pinch");
         case 1 -> TFSounds.PINCH_BEETLE_ATTACK;
         default -> throw new IllegalArgumentException("Unexpected parameter index: " + idx);
      });
   }

   public static Object Rarity_TWILIGHT(int idx, Class<?> type) {
      return type.cast(switch (idx) {
         case 0 -> -1;
         case 1 -> modidPrefixUtil.stringPrefix("twilight");
         case 2 -> style -> style.withColor(ChatFormatting.DARK_GREEN);
         default -> throw new IllegalArgumentException("Unexpected parameter index: " + idx);
      });
   }

   public static Object GrassColorModifier_ENCHANTED_FOREST(int idx, Class<?> type) {
      return type.cast(switch (idx) {
         case 0 -> modidPrefixUtil.stringPrefix("enchanted_forest");
         case 1 -> (x, z, color) -> biomeColorAlgorithms.enchanted(color, (int)x, (int)z);
         default -> throw new IllegalArgumentException("Unexpected parameter index: " + idx);
      });
   }

   public static Object GrassColorModifier_SWAMP(int idx, Class<?> type) {
      return type.cast(switch (idx) {
         case 0 -> modidPrefixUtil.stringPrefix("swamp");
         case 1 -> (x, z, color) -> biomeColorAlgorithms.swamp(BiomeColorAlgorithms.Type.Grass);
         default -> throw new IllegalArgumentException("Unexpected parameter index: " + idx);
      });
   }

   public static Object GrassColorModifier_DARK_FOREST(int idx, Class<?> type) {
      return type.cast(switch (idx) {
         case 0 -> modidPrefixUtil.stringPrefix("dark_forest");
         case 1 -> (x, z, color) -> biomeColorAlgorithms.darkForest(BiomeColorAlgorithms.Type.Grass);
         default -> throw new IllegalArgumentException("Unexpected parameter index: " + idx);
      });
   }

   public static Object GrassColorModifier_DARK_FOREST_CENTER(int idx, Class<?> type) {
      return type.cast(switch (idx) {
         case 0 -> modidPrefixUtil.stringPrefix("dark_forest_center");
         case 1 -> (x, z, color) -> biomeColorAlgorithms.darkForestCenterGrass(x, z);
         default -> throw new IllegalArgumentException("Unexpected parameter index: " + idx);
      });
   }

   public static Object GrassColorModifier_SPOOKY_FOREST(int idx, Class<?> type) {
      return type.cast(switch (idx) {
         case 0 -> modidPrefixUtil.stringPrefix("spooky_forest");
         case 1 -> (x, z, color) -> biomeColorAlgorithms.spookyGrass(x, z);
         default -> throw new IllegalArgumentException("Unexpected parameter index: " + idx);
      });
   }

   public static Object ItemDisplayContext_JARRED(int idx, Class<?> type) {
      // $VF: Couldn't be decompiled
      // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
      // java.lang.RuntimeException: invalid constant type: Ljava/io/Serializable; with value FIXED
      //   at org.jetbrains.java.decompiler.modules.decompiler.exps.ConstExprent.toJava(ConstExprent.java:364)
      //   at org.jetbrains.java.decompiler.modules.decompiler.exps.SwitchExprent.toJava(SwitchExprent.java:152)
      //   at org.jetbrains.java.decompiler.modules.decompiler.ExprProcessor.getCastedExprent(ExprProcessor.java:1054)
      //   at org.jetbrains.java.decompiler.modules.decompiler.exps.InvocationExprent.appendParamList(InvocationExprent.java:1151)
      //   at org.jetbrains.java.decompiler.modules.decompiler.exps.InvocationExprent.toJava(InvocationExprent.java:921)
      //   at org.jetbrains.java.decompiler.modules.decompiler.ExprProcessor.getCastedExprent(ExprProcessor.java:1054)
      //   at org.jetbrains.java.decompiler.modules.decompiler.exps.ExitExprent.toJava(ExitExprent.java:85)
      //   at org.jetbrains.java.decompiler.modules.decompiler.ExprProcessor.listToJava(ExprProcessor.java:925)
      //   at org.jetbrains.java.decompiler.modules.decompiler.stats.BasicBlockStatement.toJava(BasicBlockStatement.java:87)
      //   at org.jetbrains.java.decompiler.modules.decompiler.ExprProcessor.jmpWrapper(ExprProcessor.java:860)
      //   at org.jetbrains.java.decompiler.modules.decompiler.stats.SequenceStatement.toJava(SequenceStatement.java:107)
      //   at org.jetbrains.java.decompiler.modules.decompiler.stats.RootStatement.toJava(RootStatement.java:36)
      //   at org.jetbrains.java.decompiler.main.ClassWriter.writeMethod(ClassWriter.java:1351)
      //
      // Bytecode:
      // 00: aload 1
      // 01: iload 0
      // 02: tableswitch 49 0 2 26 33 44
      // 1c: bipush -1
      // 1d: invokestatic java/lang/Integer.valueOf (I)Ljava/lang/Integer;
      // 20: goto 41
      // 23: getstatic twilightforest/TFEnumExtensions.modidPrefixUtil Ltwilightforest/util/ModidPrefixUtil;
      // 26: ldc "jarred"
      // 28: invokevirtual twilightforest/util/ModidPrefixUtil.stringPrefix (Ljava/lang/String;)Ljava/lang/String;
      // 2b: goto 41
      // 2e: ldc "FIXED"
      // 30: goto 41
      // 33: new java/lang/IllegalArgumentException
      // 36: dup
      // 37: iload 0
      // 38: invokedynamic makeConcatWithConstants (I)Ljava/lang/String; bsm=java/lang/invoke/StringConcatFactory.makeConcatWithConstants (Ljava/lang/invoke/MethodHandles$Lookup;Ljava/lang/String;Ljava/lang/invoke/MethodType;Ljava/lang/String;[Ljava/lang/Object;)Ljava/lang/invoke/CallSite; args=[ "Unexpected parameter index: \u0001" ]
      // 3d: invokespecial java/lang/IllegalArgumentException.<init> (Ljava/lang/String;)V
      // 40: athrow
      // 41: invokevirtual java/lang/Class.cast (Ljava/lang/Object;)Ljava/lang/Object;
      // 44: areturn
   }

   public static Object Boat$Type_TWILIGHT_OAK(int idx, Class<?> type) {
      if (idx == 5) {
         return false;
      }

      return type.cast(switch (idx) {
         case 0 -> TFBlocks.TWILIGHT_OAK_PLANKS;
         case 1 -> modidPrefixUtil.stringPrefix("twilight_oak");
         case 2 -> TFItems.TWILIGHT_OAK_BOAT;
         case 3 -> TFItems.TWILIGHT_OAK_CHEST_BOAT;
         case 4 -> () -> Items.STICK;
         default -> throw new IllegalArgumentException("Unexpected parameter index: " + idx);
      });
   }

   public static Object Boat$Type_CANOPY(int idx, Class<?> type) {
      if (idx == 5) {
         return false;
      }

      return type.cast(switch (idx) {
         case 0 -> TFBlocks.CANOPY_PLANKS;
         case 1 -> modidPrefixUtil.stringPrefix("canopy");
         case 2 -> TFItems.CANOPY_BOAT;
         case 3 -> TFItems.CANOPY_CHEST_BOAT;
         case 4 -> () -> Items.STICK;
         default -> throw new IllegalArgumentException("Unexpected parameter index: " + idx);
      });
   }

   public static Object Boat$Type_MANGROVE(int idx, Class<?> type) {
      if (idx == 5) {
         return false;
      }

      return type.cast(switch (idx) {
         case 0 -> TFBlocks.MANGROVE_PLANKS;
         case 1 -> modidPrefixUtil.stringPrefix("mangrove");
         case 2 -> TFItems.MANGROVE_BOAT;
         case 3 -> TFItems.MANGROVE_CHEST_BOAT;
         case 4 -> () -> Items.STICK;
         default -> throw new IllegalArgumentException("Unexpected parameter index: " + idx);
      });
   }

   public static Object Boat$Type_DARK(int idx, Class<?> type) {
      if (idx == 5) {
         return false;
      }

      return type.cast(switch (idx) {
         case 0 -> TFBlocks.DARK_PLANKS;
         case 1 -> modidPrefixUtil.stringPrefix("dark");
         case 2 -> TFItems.DARK_BOAT;
         case 3 -> TFItems.DARK_CHEST_BOAT;
         case 4 -> () -> Items.STICK;
         default -> throw new IllegalArgumentException("Unexpected parameter index: " + idx);
      });
   }

   public static Object Boat$Type_TIME(int idx, Class<?> type) {
      if (idx == 5) {
         return false;
      }

      return type.cast(switch (idx) {
         case 0 -> TFBlocks.TIME_PLANKS;
         case 1 -> modidPrefixUtil.stringPrefix("time");
         case 2 -> TFItems.TIME_BOAT;
         case 3 -> TFItems.TIME_CHEST_BOAT;
         case 4 -> () -> Items.STICK;
         default -> throw new IllegalArgumentException("Unexpected parameter index: " + idx);
      });
   }

   public static Object Boat$Type_TRANSFORMATION(int idx, Class<?> type) {
      if (idx == 5) {
         return false;
      }

      return type.cast(switch (idx) {
         case 0 -> TFBlocks.TRANSFORMATION_PLANKS;
         case 1 -> modidPrefixUtil.stringPrefix("transformation");
         case 2 -> TFItems.TRANSFORMATION_BOAT;
         case 3 -> TFItems.TRANSFORMATION_CHEST_BOAT;
         case 4 -> () -> Items.STICK;
         default -> throw new IllegalArgumentException("Unexpected parameter index: " + idx);
      });
   }

   public static Object Boat$Type_MINING(int idx, Class<?> type) {
      if (idx == 5) {
         return false;
      }

      return type.cast(switch (idx) {
         case 0 -> TFBlocks.MINING_PLANKS;
         case 1 -> modidPrefixUtil.stringPrefix("mining");
         case 2 -> TFItems.MINING_BOAT;
         case 3 -> TFItems.MINING_CHEST_BOAT;
         case 4 -> () -> Items.STICK;
         default -> throw new IllegalArgumentException("Unexpected parameter index: " + idx);
      });
   }

   public static Object Boat$Type_SORTING(int idx, Class<?> type) {
      if (idx == 5) {
         return false;
      }

      return type.cast(switch (idx) {
         case 0 -> TFBlocks.SORTING_PLANKS;
         case 1 -> modidPrefixUtil.stringPrefix("sorting");
         case 2 -> TFItems.SORTING_BOAT;
         case 3 -> TFItems.SORTING_CHEST_BOAT;
         case 4 -> () -> Items.STICK;
         default -> throw new IllegalArgumentException("Unexpected parameter index: " + idx);
      });
   }
}
