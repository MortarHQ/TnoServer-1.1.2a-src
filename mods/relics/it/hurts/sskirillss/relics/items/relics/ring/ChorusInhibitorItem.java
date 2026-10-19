package it.hurts.sskirillss.relics.items.relics.ring;

import it.hurts.sskirillss.relics.init.ItemRegistry;
import it.hurts.sskirillss.relics.items.relics.base.RelicItem;
import it.hurts.sskirillss.relics.items.relics.base.data.RelicData;
import it.hurts.sskirillss.relics.items.relics.base.data.leveling.AbilitiesData;
import it.hurts.sskirillss.relics.items.relics.base.data.leveling.AbilityData;
import it.hurts.sskirillss.relics.items.relics.base.data.leveling.LevelingData;
import it.hurts.sskirillss.relics.items.relics.base.data.leveling.StatData;
import it.hurts.sskirillss.relics.items.relics.base.data.leveling.misc.UpgradeOperation;
import it.hurts.sskirillss.relics.items.relics.base.data.loot.LootData;
import it.hurts.sskirillss.relics.items.relics.base.data.loot.misc.LootEntries;
import it.hurts.sskirillss.relics.utils.EntityUtils;
import it.hurts.sskirillss.relics.utils.MathUtils;
import it.hurts.sskirillss.relics.utils.ParticleUtils;
import java.awt.Color;
import javax.annotation.Nullable;
import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ClipContext.Block;
import net.minecraft.world.level.ClipContext.Fluid;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.EntityTeleportEvent.ChorusFruit;
import top.theillusivec4.curios.api.SlotContext;

public class ChorusInhibitorItem extends RelicItem {
   @Override
   public RelicData constructDefaultRelicData() {
      return RelicData.builder()
         .abilities(
            AbilitiesData.builder()
               .ability(
                  AbilityData.builder("blink")
                     .stat(
                        StatData.builder("distance")
                           .initialValue(16.0, 32.0)
                           .upgradeModifier(UpgradeOperation.MULTIPLY_BASE, 0.2)
                           .formatValue(value -> (int)MathUtils.round(value, 0))
                           .build()
                     )
                     .stat(
                        StatData.builder("cooldown")
                           .initialValue(5.0, 10.0)
                           .upgradeModifier(UpgradeOperation.ADD, -0.5)
                           .formatValue(value -> MathUtils.round(value, 1))
                           .build()
                     )
                     .build()
               )
               .build()
         )
         .leveling(new LevelingData(100, 10, 100))
         .loot(LootData.builder().entry(LootEntries.THE_END, LootEntries.END_LIKE).build())
         .build();
   }

   public void curioTick(SlotContext slotContext, ItemStack stack) {
      if (slotContext.entity() instanceof Player player
         && player.getItemInHand(InteractionHand.MAIN_HAND).getItem() == Items.CHORUS_FRUIT
         && !player.getCooldowns().isOnCooldown(Items.CHORUS_FRUIT)
         && player.level().isClientSide()) {
         BlockPos pos = this.getEyesPos(player, stack);
         if (pos != null) {
            Vec3 start = player.position().add(0.0, player.getBbHeight() * 0.65, 0.0);
            Vec3 end = new Vec3(pos.getX() + 0.5F, pos.getY() - 0.5F, pos.getZ() + 0.5F);
            ParticleUtils.createLine(
               ParticleUtils.constructSimpleSpark(new Color(20, 0, 80), 0.15F, 0, 0.5F),
               player.level(),
               start,
               end,
               (int)Math.round(start.distanceTo(end) * 5.0)
            );
         }
      }
   }

   @Nullable
   public BlockPos getEyesPos(Player player, ItemStack stack) {
      Level world = player.getCommandSenderWorld();
      Vec3 view = player.getViewVector(0.0F);
      Vec3 eyeVec = player.getEyePosition(0.0F);
      double distance = this.getStatValue(stack, "blink", "distance");
      BlockHitResult ray = world.clip(
         new ClipContext(eyeVec, eyeVec.add(view.x * distance, view.y * distance, view.z * distance), Block.COLLIDER, Fluid.NONE, player)
      );
      BlockPos pos = ray.getBlockPos();
      if (!world.getBlockState(pos).blocksMotion()) {
         return null;
      }

      pos = pos.above();

      for (int i = 0; i < 10; i++) {
         if (!world.getBlockState(pos).blocksMotion() && !world.getBlockState(pos.above()).blocksMotion()) {
            return pos;
         }

         pos = pos.above();
      }

      return null;
   }

   @EventBusSubscriber
   public static class Events {
      @SubscribeEvent
      public static void onChorusTeleport(ChorusFruit event) {
         if (event.getEntity() instanceof Player player) {
            ItemStack stack = EntityUtils.findEquippedCurio(player, (Item)ItemRegistry.CHORUS_INHIBITOR.get());
            if (stack.getItem() instanceof ChorusInhibitorItem relic) {
               event.setCanceled(true);
               BlockPos pos = relic.getEyesPos(player, stack);
               if (pos != null) {
                  relic.spreadRelicExperience(player, stack, (int)Math.floor(player.position().distanceTo(new Vec3(pos.getX(), pos.getY(), pos.getZ())) / 10.0));
                  player.teleportTo(pos.getX() + 0.5, pos.getY(), pos.getZ() + 0.5);
                  player.level().playSound(null, pos, SoundEvents.CHORUS_FRUIT_TELEPORT, SoundSource.PLAYERS, 1.0F, 1.0F);
                  player.getCooldowns().addCooldown(Items.CHORUS_FRUIT, Math.max((int)Math.round(relic.getStatValue(stack, "blink", "cooldown") * 20.0), 0));
               }
            }
         }
      }
   }
}
