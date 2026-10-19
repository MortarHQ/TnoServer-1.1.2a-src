package net.mebahelsapi.block.block.custom.base;

import java.util.ArrayList;
import java.util.List;
import java.util.function.BiFunction;
import java.util.function.Supplier;
import net.mebahelsapi.block.entity.base.BaseChestBlockEntity;
import net.minecraft.class_1269;
import net.minecraft.class_1657;
import net.minecraft.class_1750;
import net.minecraft.class_1799;
import net.minecraft.class_1937;
import net.minecraft.class_2281;
import net.minecraft.class_2338;
import net.minecraft.class_2350;
import net.minecraft.class_2371;
import net.minecraft.class_2464;
import net.minecraft.class_2586;
import net.minecraft.class_2591;
import net.minecraft.class_2680;
import net.minecraft.class_3610;
import net.minecraft.class_3612;
import net.minecraft.class_3908;
import net.minecraft.class_3965;
import net.minecraft.class_4838;
import net.minecraft.class_5558;
import net.minecraft.class_4970.class_2251;
import org.jetbrains.annotations.Nullable;

public class BaseChestBlock extends class_2281 {
   private final Supplier<class_2591<? extends BaseChestBlockEntity>> beType;
   private final BiFunction<class_2338, class_2680, ? extends BaseChestBlockEntity> factory;

   public BaseChestBlock(
      class_2251 settings,
      Supplier<class_2591<? extends BaseChestBlockEntity>> beType,
      BiFunction<class_2338, class_2680, ? extends BaseChestBlockEntity> factory
   ) {
      super(settings, () -> beType.get());
      this.beType = beType;
      this.factory = factory;
   }

   protected boolean isMultiplayerEnabled() {
      return true;
   }

   public class_2464 method_9604(class_2680 state) {
      return class_2464.field_11455;
   }

   public class_2680 method_9605(class_1750 ctx) {
      class_2350 direction = ctx.method_8042().method_10153();
      class_3610 fluidState = ctx.method_8045().method_8316(ctx.method_8037());
      return (class_2680)((class_2680)this.method_9564().method_11657(field_10768, direction))
         .method_11657(field_10772, fluidState.method_15772() == class_3612.field_15910);
   }

   @Nullable
   public class_2586 method_10123(class_2338 pos, class_2680 state) {
      return (class_2586)this.factory.apply(pos, state);
   }

   public <T extends class_2586> class_5558<T> method_31645(class_1937 world, class_2680 state, class_2591<T> type) {
      if (type != this.beType.get()) {
         return null;
      } else {
         return world.field_9236 ? (w, p, s, be) -> ((BaseChestBlockEntity)be).clientTick() : (w, p, s, be) -> ((BaseChestBlockEntity)be).serverTick();
      }
   }

   @Nullable
   public class_3908 method_17454(class_2680 state, class_1937 world, class_2338 pos) {
      return null;
   }

   protected class_1269 method_55766(class_2680 state, class_1937 world, class_2338 pos, class_1657 player, class_3965 hit) {
      if (world.field_9236) {
         return class_1269.field_5812;
      }

      if (world.method_8321(pos) instanceof BaseChestBlockEntity chestEntity) {
         if (!this.isMultiplayerEnabled()) {
            try {
               chestEntity.method_54873(player);
               chestEntity.method_5431();
            } catch (Throwable t) {
               System.err.println("[ChestLoot] generateLoot failed at " + pos + " : " + t);
               t.printStackTrace();
            }
         } else {
            chestEntity.handlePlayerLoot(player);
         }

         player.method_17355(chestEntity);
         player.method_7259(this.method_9755());
         class_4838.method_24733(player, true);
         return class_1269.field_21466;
      } else {
         return class_1269.field_5814;
      }
   }

   public class_2680 method_9576(class_1937 world, class_2338 pos, class_2680 state, class_1657 player) {
      if (world.method_8321(pos) instanceof BaseChestBlockEntity chestEntity) {
         class_2371 itemsToDrop = class_2371.method_10213(chestEntity.method_5439(), class_1799.field_8037);

         try {
            if (!this.isMultiplayerEnabled()) {
               if (!world.field_9236) {
                  chestEntity.method_54873(player);
               }

               itemsToDrop = chestEntity.getInternalInventory();
            } else if (chestEntity.isSharedChest()) {
               itemsToDrop = chestEntity.getInternalInventory();
            } else if (player != null && chestEntity.personalLoots.containsKey(player.method_5667())) {
               itemsToDrop = chestEntity.personalLoots.get(player.method_5667());
            } else if (!chestEntity.personalLoots.isEmpty()) {
               List<class_2371<class_1799>> allLoots = new ArrayList<>(chestEntity.personalLoots.values());
               itemsToDrop = allLoots.get(world.field_9229.method_43048(allLoots.size()));
            } else if (chestEntity.hasLootTable() && player != null) {
               chestEntity.handlePlayerLoot(player);
               class_2371<class_1799> pLoot = chestEntity.personalLoots.get(player.method_5667());
               itemsToDrop = pLoot != null ? pLoot : chestEntity.getInternalInventory();
            } else {
               itemsToDrop = chestEntity.getInternalInventory();
            }
         } catch (Exception e) {
            System.err.println("[LootChest] Error choosing drop inventory: " + e.getMessage());
            e.printStackTrace();
            itemsToDrop = class_2371.method_10213(chestEntity.method_5439(), class_1799.field_8037);
         }

         for (class_1799 stack : itemsToDrop) {
            if (!stack.method_7960()) {
               method_9577(world, pos, stack);
            }
         }

         return super.method_9576(world, pos, state, player);
      } else {
         return super.method_9576(world, pos, state, player);
      }
   }
}
