package it.hurts.sskirillss.relics.items.relics;

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
import it.hurts.sskirillss.relics.utils.MathUtils;
import it.hurts.sskirillss.relics.utils.ParticleUtils;
import java.awt.Color;
import javax.annotation.Nullable;
import net.minecraft.core.BlockPos;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.UseAnim;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.dimension.DimensionType;
import net.minecraft.world.level.portal.DimensionTransition;
import net.minecraft.world.phys.Vec3;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.ComputeFovModifierEvent;
import net.neoforged.neoforge.event.entity.living.LivingIncomingDamageEvent;
import org.apache.commons.lang3.tuple.Pair;
import top.theillusivec4.curios.api.SlotContext;

public class MagicMirrorItem extends RelicItem {
   @Override
   public RelicData constructDefaultRelicData() {
      return RelicData.builder()
         .abilities(
            AbilitiesData.builder()
               .ability(
                  AbilityData.builder("teleport")
                     .stat(
                        StatData.builder("distance")
                           .initialValue(500.0, 1000.0)
                           .upgradeModifier(UpgradeOperation.MULTIPLY_BASE, 0.5)
                           .formatValue(value -> MathUtils.round(value, 1))
                           .build()
                     )
                     .stat(
                        StatData.builder("cooldown")
                           .initialValue(120.0, 60.0)
                           .upgradeModifier(UpgradeOperation.MULTIPLY_BASE, -0.05)
                           .formatValue(value -> MathUtils.round(value, 1))
                           .build()
                     )
                     .build()
               )
               .build()
         )
         .leveling(new LevelingData(100, 10, 200))
         .loot(LootData.builder().entry(LootEntries.CAVE, LootEntries.MINESHAFT).build())
         .build();
   }

   public InteractionResultHolder<ItemStack> use(Level worldIn, Player playerIn, InteractionHand handIn) {
      ItemStack stack = playerIn.getItemInHand(handIn);
      if (!playerIn.getCooldowns().isOnCooldown((Item)ItemRegistry.MAGIC_MIRROR.get()) && !worldIn.isClientSide()) {
         ServerPlayer serverPlayer = (ServerPlayer)playerIn;
         Pair<ServerLevel, Vec3> data = this.getHomePos(serverPlayer, false);
         if (!this.canTeleport(serverPlayer, data, stack)) {
            return InteractionResultHolder.fail(stack);
         }

         playerIn.startUsingItem(handIn);
         return InteractionResultHolder.pass(stack);
      } else {
         return InteractionResultHolder.fail(stack);
      }
   }

   public ItemStack finishUsingItem(ItemStack stack, Level world, LivingEntity entity) {
      if (!world.isClientSide() && entity instanceof ServerPlayer player) {
         Pair data = this.getHomePos(player, true);
         if (!this.canTeleport(player, data, stack)) {
            return stack;
         }

         Vec3 pos = (Vec3)data.getRight();
         this.spreadRelicExperience(
            player,
            stack,
            (int)(
               1L
                  + Math.round(
                        player.position().distanceTo(new Vec3(pos.x(), player.getY(), pos.z()))
                           * DimensionType.getTeleportationScale(player.level().dimensionType(), ((ServerLevel)data.getLeft()).dimensionType())
                     )
                     / 50L
            )
         );
         player.teleportTo((ServerLevel)data.getLeft(), pos.x() + 0.5, pos.y() + 1.0, pos.z() + 0.5, player.getYRot(), player.getXRot());
         if (!player.isCreative()) {
            player.getCooldowns().addCooldown(stack.getItem(), (int)Math.round(this.getStatValue(stack, "teleport", "cooldown") * 20.0));
         }

         world.playSound(null, player.blockPosition(), SoundEvents.TOTEM_USE, SoundSource.PLAYERS, 1.0F, 1.0F);
         return stack;
      } else {
         return stack;
      }
   }

   public void onUseTick(Level level, LivingEntity entity, ItemStack stack, int count) {
      if (!level.isClientSide()) {
         ServerLevel serverLevel = (ServerLevel)level;
         float radius = count * 0.075F;
         double extraY = entity.getY() + 1.5 - Math.log((count + this.getUseDuration(stack, entity) * 0.075F) * 0.1F);
         RandomSource random = level.getRandom();

         Color color = switch (level.dimension().location().getPath()) {
            case "overworld" -> new Color(75, 150, 255);
            case "the_nether" -> new Color(150, 0, 0);
            case "the_end" -> new Color(100, 0, 200);
            default -> new Color(50, 150, 0);
         };

         for (int i = 0; i < 5; i++) {
            float angle = 0.01F * (count * 3 + i * 125);
            double extraX = radius * Mth.sin((float)(Math.PI + angle)) + entity.getX();
            double extraZ = radius * Mth.cos(angle) + entity.getZ();
            serverLevel.sendParticles(
               ParticleUtils.constructSimpleSpark(color, Math.max(0.2F, (this.getUseDuration(stack, entity) - count) * 0.015F), 40, 0.92F),
               extraX,
               extraY,
               extraZ,
               1,
               0.0,
               0.0,
               0.0,
               0.0
            );
         }

         serverLevel.sendParticles(
            ParticleUtils.constructSimpleSpark(color, (this.getUseDuration(stack, entity) - count) * 0.005F, 10 + random.nextInt(50), 0.95F),
            entity.getX(),
            entity.getY() + entity.getBbHeight() * 0.5F,
            entity.getZ(),
            (int)((this.getUseDuration(stack, entity) - count) * 0.5F),
            0.25,
            entity.getBbHeight() * 0.4F,
            0.25,
            0.025F
         );
      }
   }

   public UseAnim getUseAnimation(ItemStack pStack) {
      return UseAnim.BOW;
   }

   public int getUseDuration(ItemStack pStack, LivingEntity entity) {
      return 40;
   }

   @Override
   public boolean canEquipFromUse(SlotContext slotContext, ItemStack stack) {
      return false;
   }

   @Nullable
   private Pair<ServerLevel, Vec3> getHomePos(ServerPlayer player, boolean useAnchor) {
      if (player.level().isClientSide()) {
         return null;
      }

      BlockPos pos = player.getRespawnPosition();
      MinecraftServer server = player.getServer();
      if (server == null) {
         return null;
      }

      ServerLevel world = player.getServer().getLevel(player.getRespawnDimension());
      return world != null && pos != null ? Pair.of(world, player.findRespawnPositionAndUseSpawnBlock(!useAnchor, DimensionTransition.DO_NOTHING).pos()) : null;
   }

   private boolean canTeleport(ServerPlayer player, Pair<ServerLevel, Vec3> data, ItemStack stack) {
      if (data == null) {
         return false;
      }

      Vec3 pos = (Vec3)data.getRight();
      ServerLevel level = (ServerLevel)data.getLeft();
      return !(
         player.position().distanceTo(new Vec3(pos.x(), player.getY(), pos.z()))
               * DimensionType.getTeleportationScale(player.level().dimensionType(), level.dimensionType())
            > this.getStatValue(stack, "teleport", "distance")
      );
   }

   @EventBusSubscriber(modid = "relics", value = Dist.CLIENT)
   public static class ClientEvents {
      @SubscribeEvent
      public static void onFovUpdate(ComputeFovModifierEvent event) {
         Player player = event.getPlayer();
         ItemStack stack = player.getItemInHand(InteractionHand.MAIN_HAND);
         if (stack.getItem() == ItemRegistry.MAGIC_MIRROR.get() && player.isUsingItem()) {
            int time = player.getTicksUsingItem();
            if (time > 0) {
               event.setNewFovModifier(event.getNewFovModifier() - time * 0.02F);
            }
         }
      }
   }

   @EventBusSubscriber
   public static class ServerEvents {
      @SubscribeEvent
      public static void onLivingHurt(LivingIncomingDamageEvent event) {
         Item item = (Item)ItemRegistry.MAGIC_MIRROR.get();
         if (event.getEntity() instanceof Player player
            && player.isUsingItem()
            && (player.getMainHandItem().getItem() == item || player.getOffhandItem().getItem() == item)) {
            player.stopUsingItem();
            player.getCooldowns().addCooldown(item, 20);
         }
      }
   }
}
