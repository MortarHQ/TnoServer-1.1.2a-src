package net.unusual.block_factorys_bosses.item;

import java.util.List;
import javax.annotation.Nonnull;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.BlockEntityWithoutLevelRenderer;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EquipmentSlotGroup;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.attributes.AttributeModifier.Operation;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.Item.Properties;
import net.minecraft.world.item.Item.TooltipContext;
import net.minecraft.world.item.component.ItemAttributeModifiers;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.extensions.common.RegisterClientExtensionsEvent;
import net.unusual.block_factorys_bosses.BossesRise;
import net.unusual.block_factorys_bosses.client.renderer.KnightSwordItemRenderer;
import net.unusual.block_factorys_bosses.entity.projectile.SwordWaveEntity;
import net.unusual.block_factorys_bosses.init.BossesRiseEntities;
import net.unusual.block_factorys_bosses.init.BossesRiseItems;
import net.unusual.block_factorys_bosses.init.BossesRiseSounds;

@EventBusSubscriber
public class KnightSwordItem extends AnimatedSwordItem {
   private static final Tier TOOL_TIER = new Tier() {
      public int getUses() {
         return 1480;
      }

      public float getSpeed() {
         return 4.0F;
      }

      public float getAttackDamageBonus() {
         return 0.0F;
      }

      public TagKey<Block> getIncorrectBlocksForDrops() {
         return BlockTags.INCORRECT_FOR_NETHERITE_TOOL;
      }

      public int getEnchantmentValue() {
         return 2;
      }

      public Ingredient getRepairIngredient() {
         return Ingredient.of(new ItemLike[]{Items.IRON_INGOT});
      }
   };
   private static final int USE_DURATION = 1200;

   public KnightSwordItem() {
      super(
         TOOL_TIER,
         new Properties()
            .rarity(Rarity.UNCOMMON)
            .attributes(
               ItemAttributeModifiers.builder()
                  .add(
                     Attributes.ENTITY_INTERACTION_RANGE,
                     new AttributeModifier(ResourceLocation.withDefaultNamespace("base_entity_interaction_range"), 0.5, Operation.ADD_VALUE),
                     EquipmentSlotGroup.MAINHAND
                  )
                  .add(
                     Attributes.ATTACK_DAMAGE,
                     new AttributeModifier(BASE_ATTACK_DAMAGE_ID, 13.0F + TOOL_TIER.getAttackDamageBonus(), Operation.ADD_VALUE),
                     EquipmentSlotGroup.MAINHAND
                  )
                  .add(Attributes.ATTACK_SPEED, new AttributeModifier(BASE_ATTACK_SPEED_ID, -3.1F, Operation.ADD_VALUE), EquipmentSlotGroup.MAINHAND)
                  .build()
            )
      );
   }

   @OnlyIn(Dist.CLIENT)
   public void appendHoverText(ItemStack itemstack, TooltipContext context, List<Component> list, TooltipFlag flag) {
      super.appendHoverText(itemstack, context, list, flag);
      list.add(Component.translatable("item.block_factorys_bosses.knight_sword.description_0"));
      list.add(Component.translatable("item.block_factorys_bosses.knight_sword.description_1"));
   }

   @Override
   public int getAttackDelay() {
      return 22;
   }

   @Override
   public SoundEvent getSwingSound() {
      return (SoundEvent)BossesRiseSounds.KNIGHT_SWORD_SWING.value();
   }

   @Override
   public void onEntitySwingHook(Entity entity, ItemStack stack) {
   }

   public int getUseDuration(ItemStack stack, LivingEntity entity) {
      return 1200;
   }

   public void onUseTick(Level level, LivingEntity livingEntity, ItemStack stack, int remainingUseDuration) {
      super.onUseTick(level, livingEntity, stack, remainingUseDuration);
      if (!level.isClientSide()) {
         int timeCharged = 1200 - remainingUseDuration - 5;
         if (timeCharged % 20 == 0 && timeCharged >= 0 && timeCharged <= 60) {
            level.playSound(
               null,
               BlockPos.containing(livingEntity.getX(), livingEntity.getY(), livingEntity.getZ()),
               (SoundEvent)SoundEvents.NOTE_BLOCK_CHIME.value(),
               SoundSource.PLAYERS,
               0.8F,
               1.0F
            );
         }
      }
   }

   public void releaseUsing(ItemStack stack, Level level, LivingEntity livingEntity, int timeLeft) {
      super.releaseUsing(stack, level, livingEntity, timeLeft);
      if (livingEntity instanceof Player player && !level.isClientSide()) {
         int timeCharged = 1200 - timeLeft;
         int slashes = timeCharged < 5 ? 0 : Math.min((timeCharged - 5) / 20 * 2 + 1, 7);
         BossesRise.LOGGER.debug(timeCharged + " " + slashes);
         if (slashes > 0) {
            player.getCooldowns().addCooldown(stack.getItem(), 80);
            level.playSound(null, BlockPos.containing(player.getX(), player.getY(), player.getZ()), SoundEvents.BREEZE_SHOOT, SoundSource.PLAYERS, 0.8F, 0.4F);

            for (int i = 0; i < slashes; i++) {
               SwordWaveEntity swordWave = new SwordWaveEntity((EntityType<? extends SwordWaveEntity>)BossesRiseEntities.SWORD_WAVE.get(), level);
               swordWave.setOwner(player);
               swordWave.setBaseDamage(5.0);
               swordWave.setSilent(true);
               swordWave.setKnockback(0);
               swordWave.setPos(player.getX(), player.getEyeY() - 0.1, player.getZ());
               swordWave.shoot(player.getLookAngle().x, player.getLookAngle().y, player.getLookAngle().z, 2.0F, 0.0F);
               level.addFreshEntity(swordWave);
            }
         }
      }
   }

   public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand usedHand) {
      ItemStack itemstack = player.getItemInHand(usedHand);
      if (!player.getCooldowns().isOnCooldown(itemstack.getItem())) {
         player.startUsingItem(usedHand);
         return InteractionResultHolder.consume(itemstack);
      } else {
         return super.use(level, player, usedHand);
      }
   }

   @SubscribeEvent
   public static void registerClientExtensions(RegisterClientExtensionsEvent event) {
      final Minecraft instance = Minecraft.getInstance();
      event.registerItem(new AnimatedSwordItem.AnimatedSwordItemExtension() {
         final KnightSwordItemRenderer rendererInstance = new KnightSwordItemRenderer(instance.getBlockEntityRenderDispatcher(), instance.getEntityModels());

         @Nonnull
         public BlockEntityWithoutLevelRenderer getCustomRenderer() {
            return this.rendererInstance;
         }
      }, new Item[]{(Item)BossesRiseItems.KNIGHT_SWORD.get()});
   }
}
