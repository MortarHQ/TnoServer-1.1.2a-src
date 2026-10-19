package mebahelcreaturesdwarven.entity.custom;

import java.util.ArrayList;
import java.util.List;
import mebahelcreaturesdwarven.sound.ModSounds;
import net.minecraft.class_1267;
import net.minecraft.class_1282;
import net.minecraft.class_1299;
import net.minecraft.class_13;
import net.minecraft.class_14;
import net.minecraft.class_1408;
import net.minecraft.class_1409;
import net.minecraft.class_1588;
import net.minecraft.class_1657;
import net.minecraft.class_1799;
import net.minecraft.class_1937;
import net.minecraft.class_2338;
import net.minecraft.class_2487;
import net.minecraft.class_2680;
import net.minecraft.class_2940;
import net.minecraft.class_2943;
import net.minecraft.class_2945;
import net.minecraft.class_3414;
import net.minecraft.class_5134;
import net.minecraft.class_9;
import net.minecraft.class_2945.class_9222;
import net.minecraft.class_5132.class_5133;

public class DwemerEntity extends class_1588 {
   private final List<class_1799> minedResources = new ArrayList<>();
   private double rand;
   public static final class_2940<Boolean> SHOOTING = class_2945.method_12791(DwemerEntity.class, class_2943.field_13323);
   public static final class_2940<Boolean> SWINGING = class_2945.method_12791(DwemerEntity.class, class_2943.field_13323);
   public static final class_2940<Float> COOLDOWN = class_2945.method_12791(DwemerEntity.class, class_2943.field_13320);
   public static final class_2940<String> ATTACK_NAME = class_2945.method_12791(DwemerEntity.class, class_2943.field_13326);
   public static final class_2940<Boolean> IS_MINING = class_2945.method_12791(DwemerEntity.class, class_2943.field_13323);
   public static final class_2940<Integer> MINING_COOLDOWN = class_2945.method_12791(DwemerEntity.class, class_2943.field_13327);
   public static final class_2940<String> CURRENT_ANIMATION = class_2945.method_12791(DwemerEntity.class, class_2943.field_13326);
   private static final class_2940<Integer> DATA_ID_TYPE_VARIANT = class_2945.method_12791(DwemerEntity.class, class_2943.field_13327);

   public DwemerEntity(class_1299<? extends class_1588> entityType, class_1937 world) {
      super(entityType, world);
      this.field_6191 = -this.method_5970();
   }

   public float getCooldown() {
      return (Float)this.field_6011.method_12789(COOLDOWN);
   }

   public void setCooldown(float cooldown) {
      this.field_6011.method_12778(COOLDOWN, cooldown);
   }

   public boolean isShooting() {
      return (Boolean)this.field_6011.method_12789(SHOOTING);
   }

   public void setShooting(boolean shooting) {
      this.field_6011.method_12778(SHOOTING, shooting);
   }

   public void setSwinging(boolean swinging) {
      this.field_6011.method_12778(SWINGING, swinging);
   }

   public boolean isSwinging() {
      return (Boolean)this.field_6011.method_12789(SWINGING);
   }

   public void setAttackName(String attackName) {
      this.field_6011.method_12778(ATTACK_NAME, attackName);
   }

   public String getAttackName() {
      return (String)this.field_6011.method_12789(ATTACK_NAME);
   }

   public boolean isMining() {
      return (Boolean)this.field_6011.method_12789(IS_MINING);
   }

   public void setMining(boolean mining) {
      this.field_6011.method_12778(IS_MINING, mining);
   }

   public int getMiningCooldown() {
      return (Integer)this.field_6011.method_12789(MINING_COOLDOWN);
   }

   public void setMiningCooldown(int ticks) {
      this.field_6011.method_12778(MINING_COOLDOWN, ticks);
   }

   public String getCurrentAnimation() {
      return (String)this.field_6011.method_12789(CURRENT_ANIMATION);
   }

   public void setCurrentAnimation(String animation) {
      this.field_6011.method_12778(CURRENT_ANIMATION, animation);
   }

   public boolean shouldDespawnInPeaceful() {
      return this.method_37908().method_8407() == class_1267.field_5801;
   }

   protected void method_5693(class_9222 builder) {
      super.method_5693(builder);
      builder.method_56912(SHOOTING, false);
      builder.method_56912(SWINGING, false);
      builder.method_56912(COOLDOWN, 0.0F);
      builder.method_56912(DATA_ID_TYPE_VARIANT, 0);
      builder.method_56912(ATTACK_NAME, "attack");
      builder.method_56912(CURRENT_ANIMATION, "idle");
      builder.method_56912(IS_MINING, false);
      builder.method_56912(MINING_COOLDOWN, 200);
   }

   public static class_5133 setAttributes() {
      return class_1588.method_26828()
         .method_26868(class_5134.field_23717, 20.0)
         .method_26868(class_5134.field_23716, 35.0)
         .method_26868(class_5134.field_23719, 0.3)
         .method_26868(class_5134.field_23724, 6.0)
         .method_26868(class_5134.field_23721, 6.0)
         .method_26868(class_5134.field_23718, 0.6F)
         .method_26868(class_5134.field_23722, 1.5);
   }

   public void method_6078(class_1282 source) {
      super.method_6078(source);

      for (class_1799 stack : this.minedResources) {
         this.method_5775(stack);
      }

      this.minedResources.clear();
   }

   protected class_3414 method_6011(class_1282 source) {
      this.rand = Math.random();
      return this.rand < 0.5 ? ModSounds.DWARVEN_SPIDER_INJURED_1 : ModSounds.DWARVEN_SPIDER_INJURED_1;
   }

   protected class_3414 method_6002() {
      this.rand = Math.random();
      return this.rand < 0.5 ? ModSounds.DWARVEN_SPIDER_DEATH_1 : ModSounds.DWARVEN_SPIDER_DEATH_1;
   }

   protected void method_6013(class_1282 source) {
      class_3414 hurtSound = this.method_6011(source);
      if (hurtSound != null) {
         this.method_5783(hurtSound, 0.45F, 1.0F);
      }
   }

   public int method_5970() {
      return 240;
   }

   protected class_3414 method_5994() {
      return ModSounds.DWARVEN_SPIDER_AMBIENT_1;
   }

   public void method_5966() {
      class_3414 soundEvent = this.method_5994();
      if (soundEvent != null) {
         this.method_5783(soundEvent, 0.35F, 1.0F);
      }
   }

   protected class_1408 method_5965(class_1937 world) {
      return new class_1409(this, world) {
         protected class_13 method_6336(int range) {
            this.field_6678 = new class_14();
            this.field_6678.method_15(true);
            return new class_13(this.field_6678, range) {
               protected float method_44000(class_9 a, class_9 b) {
                  return a.method_44022(b);
               }
            };
         }
      };
   }

   public void method_5652(class_2487 nbt) {
      super.method_5652(nbt);
      nbt.method_10569("Variant", this.getTypeVariant());
   }

   public void method_5749(class_2487 nbt) {
      super.method_5749(nbt);
      this.field_6011.method_12778(DATA_ID_TYPE_VARIANT, nbt.method_10550("Variant"));
   }

   public int getTypeVariant() {
      return (Integer)this.field_6011.method_12789(DATA_ID_TYPE_VARIANT);
   }

   protected void method_5712(class_2338 pos, class_2680 state) {
      this.method_5783(ModSounds.DWARVEN_SPIDER_WALK, 0.15F, 1.0F);
   }

   public boolean method_5643(class_1282 source, float amount) {
      if (source.method_5529() instanceof class_1657 player) {
         class_1799 heldItem = player.method_6047();
         String itemName = heldItem.method_7922().toLowerCase();
         boolean isPiercing = itemName.contains("claw")
            || itemName.contains("claymore")
            || itemName.contains("cutlass")
            || itemName.contains("blade")
            || itemName.contains("katana")
            || itemName.contains("lance")
            || itemName.contains("rapier")
            || itemName.contains("scythe")
            || itemName.contains("sickle")
            || itemName.contains("trident")
            || itemName.contains("sword")
            || itemName.contains("dagger")
            || itemName.contains("axe")
            || itemName.contains("war_axe")
            || itemName.contains("halberd")
            || itemName.contains("spear");
         boolean isBlunt = itemName.contains("anchor")
            || itemName.contains("claymore")
            || itemName.contains("fist")
            || itemName.contains("pickaxe")
            || itemName.contains("wand")
            || itemName.contains("mace")
            || itemName.contains("warhammer")
            || itemName.contains("hammer")
            || itemName.contains("staff");
         if (isPiercing) {
            amount *= 0.75F;
         } else if (isBlunt) {
            amount *= 1.25F;
         }
      }

      return super.method_5643(source, amount);
   }
}
