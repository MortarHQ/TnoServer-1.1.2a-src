package mebahelcreaturesdwarven.entity.ai.dwarven_spider;

import java.util.Arrays;
import java.util.List;
import mebahelcreaturesdwarven.entity.custom.DwemerSpiderEntity;
import net.minecraft.class_1352;
import net.minecraft.class_1937;
import net.minecraft.class_2246;
import net.minecraft.class_2248;
import net.minecraft.class_2338;
import net.minecraft.class_2388;
import net.minecraft.class_2398;
import net.minecraft.class_2680;
import net.minecraft.class_3218;
import net.minecraft.class_3417;
import net.minecraft.class_3419;

public class MineOreGoal extends class_1352 {
   private final DwemerSpiderEntity spider;
   private class_2338 targetOre = null;
   private int miningTime = 0;
   private boolean miningFished = false;
   private static final int MINING_DURATION = 180;
   private static final int MINING_COOLDOWN = 800;
   private static final List<class_2248> ORES = Arrays.asList(
      class_2246.field_10212,
      class_2246.field_10571,
      class_2246.field_10442,
      class_2246.field_10013,
      class_2246.field_10090,
      class_2246.field_10080,
      class_2246.field_29027,
      class_2246.field_29026,
      class_2246.field_29029,
      class_2246.field_29220,
      class_2246.field_29028,
      class_2246.field_29030,
      class_2246.field_27120,
      class_2246.field_29221,
      class_2246.field_10213,
      class_2246.field_23077,
      class_2246.field_10418,
      class_2246.field_29219
   );

   public MineOreGoal(DwemerSpiderEntity spider) {
      this.spider = spider;
   }

   public boolean method_6264() {
      if ((Boolean)this.spider.method_5841().method_12789(DwemerSpiderEntity.IS_MINING)
         || (Integer)this.spider.method_5841().method_12789(DwemerSpiderEntity.MINING_COOLDOWN) > 0) {
         return false;
      }

      if (this.spider.method_5968() != null) {
         return false;
      }

      this.targetOre = this.findNearbyOre();
      return this.targetOre != null;
   }

   public void method_6269() {
      if (this.targetOre != null) {
         class_2338 bestPosition = this.findBestMiningPosition(this.targetOre);
         this.spider.method_5942().method_6337(bestPosition.method_10263(), bestPosition.method_10264(), bestPosition.method_10260(), 1.0);
         this.miningTime = 0;
      }
   }

   public boolean method_6266() {
      return this.miningTime <= 180 && this.spider.method_5968() == null && !this.miningFished;
   }

   public void method_6268() {
      if (this.targetOre != null && this.isStillOre(this.targetOre)) {
         class_2338 bestPosition = this.findBestMiningPosition(this.targetOre);
         double distance = this.spider.method_19538().method_1022(bestPosition.method_46558());
         if (this.spider.method_5942().method_6357()) {
            if (distance < 1.5) {
               this.spider
                  .method_5988()
                  .method_20248(this.targetOre.method_10263() + 0.5, this.targetOre.method_10264() + 0.5, this.targetOre.method_10260() + 0.5);
               if (!(Boolean)this.spider.method_5841().method_12789(DwemerSpiderEntity.IS_MINING)) {
                  this.spider.method_5841().method_12778(DwemerSpiderEntity.IS_MINING, true);
                  this.miningTime = 0;
               }

               this.miningTime++;
               if (this.miningTime % 7 == 0) {
                  this.spawnMiningParticles(this.targetOre);
                  this.spider.method_37908().method_8396(null, this.targetOre, class_3417.field_15026, class_3419.field_15245, 0.5F, 1.0F);
               }

               int breakProgress = (int)(this.miningTime / 180.0F * 10.0F);
               this.spider.method_37908().method_8517(this.spider.method_5628(), this.targetOre, breakProgress);
               if (this.miningTime >= 180) {
                  this.spider.collectDroppedItems(this.targetOre);
                  this.spider.method_37908().method_22352(this.targetOre, false);
                  this.miningFished = true;
               }
            }
         }
      } else {
         this.miningFished = true;
      }
   }

   private boolean isStillOre(class_2338 pos) {
      class_1937 world = this.spider.method_37908();
      class_2248 block = world.method_8320(pos).method_26204();
      return ORES.contains(block);
   }

   public void method_6270() {
      this.spider.method_5841().method_12778(DwemerSpiderEntity.IS_MINING, false);
      this.spider.method_5841().method_12778(DwemerSpiderEntity.MINING_COOLDOWN, 800);
      this.miningFished = false;
      this.targetOre = null;
      this.miningTime = 0;
   }

   private class_2338 findNearbyOre() {
      class_2338 spiderPos = this.spider.method_24515();
      class_1937 world = this.spider.method_37908();

      for (class_2338 pos : class_2338.method_25996(spiderPos, 10, 3, 10)) {
         if (this.isOreExposed(world, pos)) {
            return pos;
         }
      }

      return null;
   }

   private boolean isOreExposed(class_1937 world, class_2338 pos) {
      class_2248 block = world.method_8320(pos).method_26204();
      if (!ORES.contains(block)) {
         return false;
      }

      for (class_2338 neighbor : new class_2338[]{
         pos.method_10084(), pos.method_10074(), pos.method_10095(), pos.method_10072(), pos.method_10078(), pos.method_10067()
      }) {
         if (world.method_8320(neighbor).method_26215()) {
            return true;
         }
      }

      return false;
   }

   private void spawnMiningParticles(class_2338 pos) {
      class_1937 world = this.spider.method_37908();
      if (!world.field_9236) {
         class_2680 blockState = world.method_8320(pos);

         for (int i = 0; i < 5; i++) {
            double offsetX = (Math.random() - 0.5) * 0.6;
            double offsetY = (Math.random() - 0.5) * 0.6;
            double offsetZ = (Math.random() - 0.5) * 0.6;
            ((class_3218)world)
               .method_14199(
                  new class_2388(class_2398.field_11217, blockState),
                  pos.method_10263() + 0.5 + offsetX,
                  pos.method_10264() + 0.5 + offsetY,
                  pos.method_10260() + 0.5 + offsetZ,
                  5,
                  0.0,
                  0.0,
                  0.0,
                  0.1
               );
         }
      }
   }

   private class_2338 findBestMiningPosition(class_2338 orePos) {
      class_1937 world = this.spider.method_37908();
      class_2338 bestPos = null;
      double bestDistance = Double.MAX_VALUE;

      for (class_2338 neighbor : new class_2338[]{
         orePos.method_10095(),
         orePos.method_10072(),
         orePos.method_10078(),
         orePos.method_10067(),
         orePos.method_10095().method_10084(),
         orePos.method_10072().method_10084(),
         orePos.method_10078().method_10084(),
         orePos.method_10067().method_10084(),
         orePos.method_10095().method_10074(),
         orePos.method_10072().method_10074(),
         orePos.method_10078().method_10074(),
         orePos.method_10067().method_10074()
      }) {
         if (world.method_8320(neighbor.method_10074()).method_26212(world, neighbor.method_10074()) && world.method_8320(neighbor).method_26215()) {
            double distance = neighbor.method_19770(this.spider.method_19538());
            if (distance < bestDistance) {
               bestPos = neighbor;
               bestDistance = distance;
            }
         }
      }

      return bestPos != null ? bestPos : orePos.method_10084();
   }
}
