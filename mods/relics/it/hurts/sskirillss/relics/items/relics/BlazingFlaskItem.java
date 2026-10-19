package it.hurts.sskirillss.relics.items.relics;

import it.hurts.sskirillss.relics.init.DataComponentRegistry;
import it.hurts.sskirillss.relics.items.relics.base.RelicItem;
import it.hurts.sskirillss.relics.items.relics.base.data.RelicData;
import it.hurts.sskirillss.relics.items.relics.base.data.leveling.AbilitiesData;
import it.hurts.sskirillss.relics.items.relics.base.data.leveling.AbilityData;
import it.hurts.sskirillss.relics.items.relics.base.data.leveling.LevelingData;
import it.hurts.sskirillss.relics.items.relics.base.data.leveling.StatData;
import it.hurts.sskirillss.relics.items.relics.base.data.leveling.misc.UpgradeOperation;
import it.hurts.sskirillss.relics.items.relics.base.data.loot.LootData;
import it.hurts.sskirillss.relics.items.relics.base.data.loot.misc.LootEntries;
import it.hurts.sskirillss.relics.utils.MathUtils;
import it.hurts.sskirillss.relics.utils.ParticleUtils;
import it.hurts.sskirillss.relics.utils.WorldUtils;
import it.hurts.sskirillss.relics.utils.data.WorldPosition;
import java.awt.Color;
import java.util.List;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.MoverType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ClipContext.Block;
import net.minecraft.world.level.ClipContext.Fluid;
import net.minecraft.world.level.block.BaseFireBlock;
import net.minecraft.world.phys.Vec3;
import top.theillusivec4.curios.api.SlotContext;

public class BlazingFlaskItem extends RelicItem {
   @Override
   public RelicData constructDefaultRelicData() {
      return RelicData.builder()
         .abilities(
            AbilitiesData.builder()
               .ability(
                  AbilityData.builder("bonfire")
                     .stat(
                        StatData.builder("step")
                           .initialValue(1.0, 2.5)
                           .upgradeModifier(UpgradeOperation.MULTIPLY_BASE, 0.1)
                           .formatValue(value -> MathUtils.round(value, 1))
                           .build()
                     )
                     .stat(
                        StatData.builder("speed")
                           .initialValue(0.01, 0.05)
                           .upgradeModifier(UpgradeOperation.MULTIPLY_BASE, 1.9)
                           .formatValue(value -> MathUtils.round(value * 8.0, 1))
                           .build()
                     )
                     .stat(
                        StatData.builder("height")
                           .initialValue(3.0, 5.0)
                           .upgradeModifier(UpgradeOperation.ADD, 1.0)
                           .formatValue(value -> MathUtils.round(value, 1))
                           .build()
                     )
                     .build()
               )
               .build()
         )
         .leveling(new LevelingData(100, 10, 100))
         .loot(LootData.builder().entry(LootEntries.THE_NETHER, LootEntries.NETHER_LIKE).build())
         .build();
   }

   public void inventoryTick(ItemStack stack, Level level, Entity entity, int slot, boolean isSelected) {
      if (entity instanceof Player player) {
         Level world = player.getCommandSenderWorld();
         int fire = this.getFireAround(stack, world);
         if (fire <= 0) {
            stack.set(DataComponentRegistry.WORLD_POSITION, null);
         } else {
            stack.set(DataComponentRegistry.COUNT, fire);
         }

         WorldPosition center = (WorldPosition)stack.get(DataComponentRegistry.WORLD_POSITION);
         if (center != null) {
            double radius = (Double)stack.getOrDefault(DataComponentRegistry.RADIUS, 0.0);
            if (!player.isCreative()
               && !player.isSpectator()
               && !player.getAbilities().flying
               && !player.getAbilities().mayfly
               && new Vec3(player.getX(), center.getPos().y(), player.getZ()).distanceTo(center.getPos()) <= radius + 0.5) {
               player.fallDistance = 0.0F;
               if (player.tickCount % 100 == 0) {
                  this.spreadRelicExperience(player, stack, 1);
               }

               double speed = this.getStatValue(stack, "bonfire", "speed");
               if (world.isClientSide()) {
                  if (!player.onGround() && (player.zza != 0.0F || player.xxa != 0.0F)) {
                     player.move(MoverType.SELF, player.getDeltaMovement().multiply(speed, 0.0, speed));
                  }

                  if (player instanceof LocalPlayer localPlayer
                     && localPlayer.input.jumping
                     && WorldUtils.getGroundHeight(player, player.position(), 64) + this.getStatValue(stack, "bonfire", "height") - player.getY() > 0.0) {
                     Vec3 motion = player.getDeltaMovement();
                     if (motion.y() < 0.0) {
                        player.setDeltaMovement(motion.x(), motion.y() * 0.9F, motion.z());
                     }

                     player.setDeltaMovement(player.getDeltaMovement().add(0.0, 0.1F, 0.0));
                  }
               }
            }

            double size = ((Integer)stack.getOrDefault(DataComponentRegistry.COUNT, 0)).intValue() * this.getStatValue(stack, "bonfire", "step");
            double step = 0.1;
            int time = 0;
            if (radius < size) {
               if (radius + step < size) {
                  radius += step;
               } else {
                  radius = size;
               }

               time = 10;
               stack.set(DataComponentRegistry.RADIUS, radius);
            }

            if (radius > size) {
               if (radius - step > size) {
                  radius -= step;
               } else {
                  radius = size;
               }

               time = 10;
               stack.set(DataComponentRegistry.RADIUS, radius);
            }

            if (radius <= step) {
               ParticleUtils.createBall(ParticleUtils.constructSimpleSpark(new Color(255, 100, 0), 0.3F, 20, 0.9F), center.getPos(), level, 3, 0.2F);
            }

            ParticleUtils.createCyl(ParticleUtils.constructSimpleSpark(new Color(255, 100, 0), 0.2F, time, 0.8F), center.getPos(), level, radius, 0.15F);
         }
      }
   }

   public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
      ItemStack stack = player.getItemInHand(hand);
      Vec3 view = player.getViewVector(0.0F);
      Vec3 eyeVec = player.getEyePosition(0.0F);
      float distance = (float)(8.0 + this.getStatValue(stack, "bonfire", "height"));
      Vec3 end = level.clip(new ClipContext(eyeVec, eyeVec.add(view.x * distance, view.y * distance, view.z * distance), Block.COLLIDER, Fluid.NONE, player))
         .getLocation();
      if (this.getFireAround(stack, end, level) > 0) {
         WorldPosition center = (WorldPosition)stack.get(DataComponentRegistry.WORLD_POSITION);
         double radius = (Double)stack.getOrDefault(DataComponentRegistry.RADIUS, 0.0);
         stack.set(DataComponentRegistry.RADIUS, center != null && end.distanceTo(center.getPos()) <= radius ? radius - center.getPos().distanceTo(end) : 0.0);
         stack.set(DataComponentRegistry.WORLD_POSITION, new WorldPosition(player.level().dimension(), end));
         player.getCooldowns().addCooldown(this, 20);
      }

      return InteractionResultHolder.pass(stack);
   }

   public int getFireAround(ItemStack stack, Level level) {
      WorldPosition center = (WorldPosition)stack.get(DataComponentRegistry.WORLD_POSITION);
      return center == null ? 0 : this.getFireAround(stack, center.getPos(), level);
   }

   public int getFireAround(ItemStack stack, Vec3 center, Level level) {
      List<BlockPos> positions = WorldUtils.getBlockSphere(
            new BlockPos((int)center.x, (int)center.y, (int)center.z), this.getStatValue(stack, "bonfire", "step")
         )
         .stream()
         .filter(pos -> level.getBlockState(pos).getBlock() instanceof BaseFireBlock)
         .toList();
      return positions.size();
   }

   @Override
   public boolean canEquipFromUse(SlotContext slotContext, ItemStack stack) {
      return false;
   }
}
