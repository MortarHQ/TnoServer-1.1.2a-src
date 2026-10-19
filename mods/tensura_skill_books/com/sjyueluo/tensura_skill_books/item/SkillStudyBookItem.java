package com.sjyueluo.tensura_skill_books.item;

import com.sjyueluo.tensura_skill_books.TensuraSkillBooksConfig;
import com.sjyueluo.tensura_skill_books.catalog.SkillCatalog;
import com.sjyueluo.tensura_skill_books.catalog.SkillCategory;
import com.sjyueluo.tensura_skill_books.catalog.SkillEntry;
import com.sjyueluo.tensura_skill_books.client.SkillStudyBookItemExtensions;
import com.sjyueluo.tensura_skill_books.util.SkillCompat;
import com.sjyueluo.tensura_skill_books.util.SkillGrantCompat;
import com.sjyueluo.tensura_skill_books.util.SkillItemNbt;
import com.sjyueluo.tensura_skill_books.util.StudyBookLoreUtil;
import com.sjyueluo.tensura_skill_books.util.StudyBookStackFactory;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.Item.Properties;
import net.minecraft.world.level.Level;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.neoforged.neoforge.client.extensions.common.IClientItemExtensions;

public final class SkillStudyBookItem extends Item {
   public SkillStudyBookItem(Properties properties) {
      super(properties);
   }

   public static Properties defaultProperties(Rarity rarity) {
      return new Properties().stacksTo(1).rarity(rarity);
   }

   @OnlyIn(Dist.CLIENT)
   public void initializeClient(Consumer<IClientItemExtensions> consumer) {
      consumer.accept(new SkillStudyBookItemExtensions());
   }

   public void inventoryTick(ItemStack stack, Level level, Entity entity, int slot, boolean selected) {
      ResourceLocation skillId = SkillItemNbt.getSkillId(stack);
      SkillCategory randomCategory = SkillItemNbt.getRandomCategory(stack);
      if (skillId != null) {
         StudyBookLoreUtil.ensureLore(stack, skillId);
      } else if (randomCategory != null) {
         StudyBookLoreUtil.ensureRandomLore(stack, randomCategory);
      }

      super.inventoryTick(stack, level, entity, slot, selected);
   }

   public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
      ItemStack stack = player.getItemInHand(hand);
      ResourceLocation skillId = SkillItemNbt.getSkillId(stack);
      SkillCategory randomCategory = SkillItemNbt.getRandomCategory(stack);
      if (randomCategory != null) {
         if (!level.isClientSide() && level instanceof ServerLevel serverLevel) {
            this.handleServerRandomUse(serverLevel, player, stack, randomCategory);
         }

         return InteractionResultHolder.sidedSuccess(stack, level.isClientSide());
      } else if (skillId == null) {
         if (!level.isClientSide()) {
            player.displayClientMessage(Component.translatable("message.tensura_skill_books.missing_skill_id"), true);
         }

         return InteractionResultHolder.sidedSuccess(stack, level.isClientSide());
      } else {
         if (!level.isClientSide() && level instanceof ServerLevel serverLevel) {
            this.handleServerUse(serverLevel, player, stack, skillId);
         }

         return InteractionResultHolder.sidedSuccess(stack, level.isClientSide());
      }
   }

   private void handleServerUse(ServerLevel serverLevel, Player player, ItemStack stack, ResourceLocation skillId) {
      Object skill = SkillCatalog.get(skillId);
      if (skill == null) {
         player.displayClientMessage(Component.translatable("message.tensura_skill_books.invalid_skill", new Object[]{skillId.toString()}), true);
      } else {
         SkillGrantCompat.GrantResult result = SkillGrantCompat.grantSkill(player, skill);
         Component skillName = SkillCompat.getSkillName(skill);
         switch (result) {
            case SUCCESS:
               stack.shrink(1);
               serverLevel.playSound(null, player.blockPosition(), SoundEvents.TOTEM_USE, SoundSource.PLAYERS, 0.8F, 0.475F);
               break;
            case ALREADY_HAS:
               player.displayClientMessage(Component.translatable("message.tensura_skill_books.already_has", new Object[]{skillName}), true);
               break;
            case INVALID:
            case ERROR:
               player.displayClientMessage(Component.translatable("message.tensura_skill_books.invalid_skill", new Object[]{skillName}), true);
         }
      }
   }

   private void handleServerRandomUse(ServerLevel serverLevel, Player player, ItemStack stack, SkillCategory category) {
      Component randomName = StudyBookStackFactory.randomCategoryName(category);
      if (!TensuraSkillBooksConfig.randomBookEnabled(category)) {
         player.displayClientMessage(Component.translatable("message.tensura_skill_books.random_disabled", new Object[]{randomName}), true);
      } else {
         List<SkillEntry> available = new ArrayList<>();

         for (SkillEntry entry : category == SkillCategory.ALL ? SkillCatalog.all() : SkillCatalog.byCategory().get(category)) {
            if (!SkillGrantCompat.hasSkillQuietly(player, entry.skill())) {
               available.add(entry);
            }
         }

         if (available.isEmpty()) {
            player.displayClientMessage(Component.translatable("message.tensura_skill_books.random_empty", new Object[]{randomName}), true);
         } else {
            while (!available.isEmpty()) {
               SkillEntry entry = available.remove(serverLevel.random.nextInt(available.size()));
               SkillGrantCompat.GrantResult result = SkillGrantCompat.grantSkill(player, entry.skill());
               Component skillName = SkillCompat.getSkillName(entry.skill());
               if (result == SkillGrantCompat.GrantResult.SUCCESS) {
                  stack.shrink(1);
                  serverLevel.playSound(null, player.blockPosition(), SoundEvents.TOTEM_USE, SoundSource.PLAYERS, 0.8F, 0.475F);
                  player.displayClientMessage(Component.translatable("message.tensura_skill_books.random_success", new Object[]{skillName}), true);
                  return;
               }
            }

            player.displayClientMessage(Component.translatable("message.tensura_skill_books.invalid_skill", new Object[]{randomName}), true);
         }
      }
   }
}
