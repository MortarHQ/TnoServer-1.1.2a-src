package net.blay09.mods.waystones.item;

import java.util.Collection;
import java.util.Collections;
import java.util.Random;
import net.blay09.mods.balm.api.Balm;
import net.blay09.mods.balm.api.menu.BalmMenuProvider;
import net.blay09.mods.waystones.api.Waystone;
import net.blay09.mods.waystones.api.trait.IResetUseOnDamage;
import net.blay09.mods.waystones.compat.Compat;
import net.blay09.mods.waystones.config.WaystonesConfig;
import net.blay09.mods.waystones.core.PlayerWaystoneManager;
import net.blay09.mods.waystones.menu.ModMenus;
import net.blay09.mods.waystones.menu.WaystoneSelectionMenu;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.HumanoidArm;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.UseAnim;
import net.minecraft.world.item.Item.Properties;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

public class WarpStoneItem extends Item implements IResetUseOnDamage {
   private final Random random = new Random();

   public WarpStoneItem(Properties properties) {
      super(properties.durability(128));
   }

   public int getUseDuration(ItemStack itemStack, LivingEntity entity) {
      return WaystonesConfig.getActive().general.warpStoneUseTime;
   }

   public UseAnim getUseAnimation(ItemStack itemStack) {
      return WaystonesConfig.getActive().general.warpStoneUseTime > 0 && !Compat.isVivecraftInstalled ? UseAnim.BOW : UseAnim.NONE;
   }

   public void onUseTick(Level level, LivingEntity entity, ItemStack itemStack, int remainingTicks) {
      if (level.isClientSide) {
         int duration = this.getUseDuration(itemStack, entity);
         float progress = (float)(duration - remainingTicks) / duration;
         boolean shouldMirror = entity.getUsedItemHand() == InteractionHand.MAIN_HAND ^ entity.getMainArm() == HumanoidArm.RIGHT;
         Vec3 handOffset = new Vec3(shouldMirror ? 0.3F : -0.3F, 1.0, 0.52F);
         handOffset = handOffset.yRot(-entity.getYRot() * (float) (Math.PI / 180.0));
         handOffset = handOffset.zRot(entity.getXRot() * (float) (Math.PI / 180.0));
         int maxParticles = Math.max(4, (int)(progress * 48.0F));
         if (remainingTicks % 5 == 0) {
            for (int i = 0; i < Math.min(4, maxParticles); i++) {
               level.addParticle(
                  ParticleTypes.REVERSE_PORTAL,
                  entity.getX() + handOffset.x + (this.random.nextDouble() - 0.5) * 0.5,
                  entity.getY() + handOffset.y + this.random.nextDouble(),
                  entity.getZ() + handOffset.z + (this.random.nextDouble() - 0.5) * 0.5,
                  0.0,
                  0.05F,
                  0.0
               );
            }

            if (progress >= 0.25F) {
               for (int i = 0; i < maxParticles; i++) {
                  level.addParticle(
                     ParticleTypes.CRIMSON_SPORE,
                     entity.getX() + (this.random.nextDouble() - 0.5) * 1.5,
                     entity.getY() + this.random.nextDouble(),
                     entity.getZ() + (this.random.nextDouble() - 0.5) * 1.5,
                     0.0,
                     this.random.nextDouble() * 0.5,
                     0.0
                  );
               }
            }

            if (progress >= 0.5F) {
               for (int i = 0; i < maxParticles; i++) {
                  level.addParticle(
                     ParticleTypes.REVERSE_PORTAL,
                     entity.getX() + (this.random.nextDouble() - 0.5) * 1.5,
                     entity.getY() + this.random.nextDouble(),
                     entity.getZ() + (this.random.nextDouble() - 0.5) * 1.5,
                     0.0,
                     this.random.nextDouble(),
                     0.0
                  );
               }
            }

            if (progress >= 0.75F) {
               for (int i = 0; i < maxParticles / 3; i++) {
                  level.addParticle(
                     ParticleTypes.WITCH,
                     entity.getX() + (this.random.nextDouble() - 0.5) * 1.5,
                     entity.getY() + 0.5 + this.random.nextDouble(),
                     entity.getZ() + (this.random.nextDouble() - 0.5) * 1.5,
                     0.0,
                     this.random.nextDouble(),
                     0.0
                  );
               }
            }
         }

         if (remainingTicks == 1) {
            for (int i = 0; i < maxParticles; i++) {
               level.addParticle(
                  ParticleTypes.REVERSE_PORTAL,
                  entity.getX() + (this.random.nextDouble() - 0.5) * 1.5,
                  entity.getY() + this.random.nextDouble() + 1.0,
                  entity.getZ() + (this.random.nextDouble() - 0.5) * 1.5,
                  (this.random.nextDouble() - 0.5) * 0.0,
                  this.random.nextDouble(),
                  (this.random.nextDouble() - 0.5) * 0.0
               );
            }
         }
      }
   }

   public ItemStack finishUsingItem(final ItemStack itemStack, Level world, LivingEntity entity) {
      if (!world.isClientSide && entity instanceof ServerPlayer player) {
         final InteractionHand hand = player.getUsedItemHand();
         final Collection<Waystone> waystones = PlayerWaystoneManager.getTargetsForItem(player, itemStack);
         PlayerWaystoneManager.ensureSortingIndex(player, waystones);
         Balm.getNetworking()
            .openGui(
               player,
               new BalmMenuProvider<ModMenus.ItemInitiatedWaystoneMenuData>() {
                  public Component getDisplayName() {
                     return Component.translatable("container.waystones.waystone_selection");
                  }

                  public AbstractContainerMenu createMenu(int windowId, Inventory playerInventory, Player playerx) {
                     return new WaystoneSelectionMenu(
                           (MenuType<WaystoneSelectionMenu>)ModMenus.warpStoneSelection.get(), null, windowId, waystones, Collections.emptySet()
                        )
                        .withWarpItem(itemStack)
                        .setPostTeleportHandler(context -> itemStack.hurtAndBreak(1, player, LivingEntity.getSlotForHand(hand)));
                  }

                  public ModMenus.ItemInitiatedWaystoneMenuData getScreenOpeningData(ServerPlayer serverPlayer) {
                     return new ModMenus.ItemInitiatedWaystoneMenuData(waystones, itemStack);
                  }

                  public StreamCodec<RegistryFriendlyByteBuf, ModMenus.ItemInitiatedWaystoneMenuData> getScreenStreamCodec() {
                     return ModMenus.ItemInitiatedWaystoneMenuData.STREAM_CODEC;
                  }
               }
            );
      }

      return itemStack;
   }

   public InteractionResultHolder<ItemStack> use(Level world, Player player, InteractionHand hand) {
      ItemStack itemStack = player.getItemInHand(hand);
      if (!player.isUsingItem() && !world.isClientSide) {
         world.playSound(null, player, SoundEvents.PORTAL_TRIGGER, SoundSource.PLAYERS, 0.1F, 2.0F);
      }

      if (this.getUseDuration(itemStack, player) > 0 && !Compat.isVivecraftInstalled) {
         player.startUsingItem(hand);
      } else {
         this.finishUsingItem(itemStack, world, player);
      }

      return new InteractionResultHolder(InteractionResult.SUCCESS, itemStack);
   }

   public boolean isFoil(ItemStack itemStack) {
      return true;
   }
}
