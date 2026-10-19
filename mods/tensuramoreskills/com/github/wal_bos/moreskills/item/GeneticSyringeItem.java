package com.github.wal_bos.moreskills.item;

import com.github.wal_bos.moreskills.configs.MoreSkillsConfigs;
import com.github.wal_bos.moreskills.handler.GeneEditorMobHandler;
import io.github.manasmods.manascore.skill.api.ManasSkill;
import io.github.manasmods.manascore.skill.api.SkillAPI;
import io.github.manasmods.tensura.ability.SkillHelper;
import io.github.manasmods.tensura.registry.attribute.TensuraAttributes;
import io.github.manasmods.tensura.util.AttributeHelper;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import net.minecraft.ChatFormatting;
import net.minecraft.core.component.DataComponents;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.StringTag;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.attributes.AttributeModifier.Operation;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.Item.Properties;
import net.minecraft.world.item.Item.TooltipContext;
import net.minecraft.world.item.component.CustomData;

public class GeneticSyringeItem extends Item {
   private static final String ROOT = "GeneSyringe";
   private static final String STATE = "State";
   private static final String EMPTY = "empty";
   private static final String SAMPLE = "sample";
   private static final String FULL = "full";
   private static final String SKILLS = "Skills";
   private static final String EP = "EP";
   private static final String MP = "MP";
   private static final String HP = "HP";
   private static final String SHP = "SHP";
   private static final ResourceLocation HP_ID = ResourceLocation.fromNamespaceAndPath("tensuramoreskills", "gene_editor_hp");
   private static final ResourceLocation MP_ID = ResourceLocation.fromNamespaceAndPath("tensuramoreskills", "gene_editor_mp");
   private static final ResourceLocation SHP_ID = ResourceLocation.fromNamespaceAndPath("tensuramoreskills", "gene_editor_shp");
   private static final ResourceLocation AURA_ID = ResourceLocation.fromNamespaceAndPath("tensuramoreskills", "gene_editor_aura");
   private static final ResourceLocation WILL_ID = ResourceLocation.fromNamespaceAndPath("tensuramoreskills", "gene_editor_will");
   private static final ResourceLocation SPEED_ID = ResourceLocation.fromNamespaceAndPath("tensuramoreskills", "gene_editor_speed");
   private static final ResourceLocation DMG_ID = ResourceLocation.fromNamespaceAndPath("tensuramoreskills", "gene_editor_damage");
   private static final ResourceLocation ATK_SPEED_ID = ResourceLocation.fromNamespaceAndPath("tensuramoreskills", "gene_editor_attack_speed");
   private static final ResourceLocation VISION_ID = ResourceLocation.fromNamespaceAndPath("tensuramoreskills", "gene_editor_presence");
   private static final ResourceLocation CHANT_ID = ResourceLocation.fromNamespaceAndPath("tensuramoreskills", "gene_editor_chant");
   private static final ResourceLocation DODGE_ID = ResourceLocation.fromNamespaceAndPath("tensuramoreskills", "gene_editor_projectile_dodge");
   private static final ResourceLocation RESIST_ID = ResourceLocation.fromNamespaceAndPath("tensuramoreskills", "gene_editor_resistance_degradation");

   public GeneticSyringeItem(Properties properties) {
      super(properties.stacksTo(1));
   }

   private static MoreSkillsConfigs.GeneEditorConfig cfg() {
      return MoreSkillsConfigs.GRAND.geneEditor;
   }

   public InteractionResult interactLivingEntity(ItemStack stack, Player player, LivingEntity target, InteractionHand hand) {
      if (player.level().isClientSide()) {
         return InteractionResult.SUCCESS;
      } else if (target instanceof Player) {
         player.displayClientMessage(Component.translatable("tensuramoreskills.skill.gene_editor.no_player_injection").withStyle(ChatFormatting.RED), true);
         return InteractionResult.FAIL;
      } else if (!isFull(stack)) {
         player.displayClientMessage(Component.translatable("tensuramoreskills.skill.gene_editor.not_full_syringe").withStyle(ChatFormatting.RED), true);
         return InteractionResult.FAIL;
      } else {
         applyInjection(stack, player, target);
         stack.shrink(1);
         player.swing(hand, true);
         player.level().playSound(null, target.getX(), target.getY(), target.getZ(), SoundEvents.HONEY_DRINK, SoundSource.PLAYERS, 0.9F, 1.2F);
         player.displayClientMessage(
            Component.translatable("tensuramoreskills.skill.gene_editor.injected", new Object[]{target.getDisplayName()}).withStyle(ChatFormatting.GREEN), true
         );
         return InteractionResult.SUCCESS;
      }
   }

   public static void applyInjection(ItemStack stack, Player injector, LivingEntity target) {
      CompoundTag data = getSyringe(stack);
      List<ResourceLocation> skills = readSkills(data);

      for (ResourceLocation id : skills) {
         ManasSkill skill = (ManasSkill)SkillAPI.getSkillRegistry().get(id);
         if (skill != null) {
            SkillHelper.learnSkill(target, skill);
         }
      }

      double ep = data.getDouble("EP");
      double mp = data.getDouble("MP");
      double hp = data.getDouble("HP");
      double shp = data.getDouble("SHP");
      if (target instanceof Mob mob) {
         GeneEditorMobHandler.addInjectedData(mob, skills, ep, mp, hp, shp);
      } else {
         applyBaseInjectedAttributes(target, ep, mp, hp, shp);
      }

      if (target.getHealth() < target.getMaxHealth()) {
         target.heal((float)Math.min(hp, target.getMaxHealth() - target.getHealth()));
      }
   }

   public static void applyBaseInjectedAttributes(LivingEntity target, double ep, double mp, double hp, double shp) {
      AttributeHelper.addPermanentAttribute(target, Attributes.MAX_HEALTH, HP_ID, hp, Operation.ADD_VALUE);
      AttributeHelper.addPermanentAttribute(target, TensuraAttributes.MAX_MAGICULE, MP_ID, mp, Operation.ADD_VALUE);
      AttributeHelper.addPermanentAttribute(target, TensuraAttributes.MAX_SPIRITUAL_HEALTH, SHP_ID, shp, Operation.ADD_VALUE);
      AttributeHelper.addPermanentAttribute(
         target, TensuraAttributes.MAX_AURA, AURA_ID, ep * (Double)cfg().injectedEpToAuraMultiplier.get(), Operation.ADD_VALUE
      );
      AttributeHelper.addPermanentAttribute(
         target, Attributes.KNOCKBACK_RESISTANCE, WILL_ID, (Double)cfg().injectedKnockbackResistance.get(), Operation.ADD_VALUE
      );
      AttributeHelper.addPermanentAttribute(target, Attributes.MOVEMENT_SPEED, SPEED_ID, (Double)cfg().injectedMovementSpeed.get(), Operation.ADD_VALUE);
      AttributeHelper.addPermanentAttribute(target, Attributes.ATTACK_DAMAGE, DMG_ID, (Double)cfg().injectedAttackDamage.get(), Operation.ADD_VALUE);
      AttributeHelper.addPermanentAttribute(target, Attributes.ATTACK_SPEED, ATK_SPEED_ID, (Double)cfg().injectedAttackSpeed.get(), Operation.ADD_VALUE);
      AttributeHelper.addPermanentAttribute(target, TensuraAttributes.PRESENCE_SENSE, VISION_ID, (Double)cfg().injectedPresenceSense.get(), Operation.ADD_VALUE);
      AttributeHelper.addPermanentAttribute(target, TensuraAttributes.CHANT_SPEED, CHANT_ID, (Double)cfg().injectedChantSpeed.get(), Operation.ADD_VALUE);
      AttributeHelper.addPermanentAttribute(
         target, TensuraAttributes.AUTO_PROJECTILE_DODGE_CHANCE, DODGE_ID, (Double)cfg().injectedProjectileDodgeChance.get(), Operation.ADD_VALUE
      );
      AttributeHelper.addPermanentAttribute(
         target, TensuraAttributes.RESISTANCE_DEGRADATION, RESIST_ID, (Double)cfg().injectedResistanceDegradation.get(), Operation.ADD_VALUE
      );
   }

   public static boolean isEmpty(ItemStack stack) {
      return state(stack).equals("empty");
   }

   public static boolean isSample(ItemStack stack) {
      return state(stack).equals("sample");
   }

   public static boolean isFull(ItemStack stack) {
      return state(stack).equals("full");
   }

   public static String state(ItemStack stack) {
      CompoundTag tag = getSyringe(stack);
      return tag.contains("State") ? tag.getString("State") : "empty";
   }

   public static void writeEmpty(ItemStack stack) {
      CompoundTag tag = new CompoundTag();
      tag.putString("State", "empty");
      saveSyringe(stack, tag);
   }

   public static void writeSample(ItemStack stack, List<ResourceLocation> skills, double ep, double mp, double hp, double shp) {
      write(stack, "sample", skills, ep, mp, hp, shp);
   }

   public static void writeFull(ItemStack stack, List<ResourceLocation> skills, double ep, double mp, double hp, double shp) {
      write(stack, "full", skills, ep, mp, hp, shp);
   }

   private static void write(ItemStack stack, String state, List<ResourceLocation> skills, double ep, double mp, double hp, double shp) {
      CompoundTag tag = new CompoundTag();
      tag.putString("State", state);
      ListTag list = new ListTag();

      for (ResourceLocation id : new LinkedHashSet<>(skills)) {
         list.add(StringTag.valueOf(id.toString()));
      }

      tag.put("Skills", list);
      tag.putDouble("EP", Math.max(0.0, ep));
      tag.putDouble("MP", Math.max(0.0, mp));
      tag.putDouble("HP", Math.max(0.0, hp));
      tag.putDouble("SHP", Math.max(0.0, shp));
      saveSyringe(stack, tag);
   }

   public static List<ResourceLocation> readSkills(CompoundTag tag) {
      List<ResourceLocation> out = new ArrayList<>();
      ListTag list = tag.getList("Skills", 8);

      for (int i = 0; i < list.size(); i++) {
         try {
            out.add(ResourceLocation.parse(list.getString(i)));
         } catch (Exception var5) {
         }
      }

      return out;
   }

   public static int findEmptySyringeSlot(Player player) {
      for (int i = 0; i < player.getInventory().getContainerSize(); i++) {
         ItemStack stack = player.getInventory().getItem(i);
         if (stack.getItem() instanceof GeneticSyringeItem && isEmpty(stack)) {
            return i;
         }
      }

      ItemStack offhand = player.getOffhandItem();
      return offhand.getItem() instanceof GeneticSyringeItem && isEmpty(offhand) ? 40 : -1;
   }

   public static CompoundTag getSyringe(ItemStack stack) {
      CustomData data = (CustomData)stack.getOrDefault(DataComponents.CUSTOM_DATA, CustomData.EMPTY);
      CompoundTag root = data.copyTag();
      return root.getCompound("GeneSyringe");
   }

   private static void saveSyringe(ItemStack stack, CompoundTag syringe) {
      CompoundTag root = ((CustomData)stack.getOrDefault(DataComponents.CUSTOM_DATA, CustomData.EMPTY)).copyTag();
      root.put("GeneSyringe", syringe);
      stack.set(DataComponents.CUSTOM_DATA, CustomData.of(root));
   }

   public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltip, TooltipFlag flag) {
      CompoundTag tag = getSyringe(stack);
      String state = state(stack);
      tooltip.add(
         Component.translatable(
               "tensuramoreskills.item.genetic_syringe.state", new Object[]{Component.translatable("tensuramoreskills.item.genetic_syringe.state." + state)}
            )
            .withStyle(ChatFormatting.AQUA)
      );
      if (!state.equals("empty")) {
         tooltip.add(
            Component.translatable("tensuramoreskills.item.genetic_syringe.skills", new Object[]{tag.getList("Skills", 8).size()})
               .withStyle(ChatFormatting.GRAY)
         );
         tooltip.add(Component.literal("EP: " + Math.round(tag.getDouble("EP"))).withStyle(ChatFormatting.DARK_PURPLE));
         tooltip.add(Component.literal("MP: " + Math.round(tag.getDouble("MP"))).withStyle(ChatFormatting.BLUE));
         tooltip.add(Component.literal("HP: " + Math.round(tag.getDouble("HP"))).withStyle(ChatFormatting.RED));
         tooltip.add(Component.literal("SHP: " + Math.round(tag.getDouble("SHP"))).withStyle(ChatFormatting.LIGHT_PURPLE));
      }

      super.appendHoverText(stack, context, tooltip, flag);
   }
}
