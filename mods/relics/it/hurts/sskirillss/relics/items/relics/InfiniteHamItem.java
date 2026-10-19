package it.hurts.sskirillss.relics.items.relics;

import it.hurts.sskirillss.relics.api.events.common.ContainerSlotClickEvent;
import it.hurts.sskirillss.relics.init.CreativeTabRegistry;
import it.hurts.sskirillss.relics.init.DataComponentRegistry;
import it.hurts.sskirillss.relics.init.EffectRegistry;
import it.hurts.sskirillss.relics.items.misc.CreativeContentConstructor;
import it.hurts.sskirillss.relics.items.relics.base.RelicItem;
import it.hurts.sskirillss.relics.items.relics.base.data.RelicData;
import it.hurts.sskirillss.relics.items.relics.base.data.leveling.AbilitiesData;
import it.hurts.sskirillss.relics.items.relics.base.data.leveling.AbilityData;
import it.hurts.sskirillss.relics.items.relics.base.data.leveling.LevelingData;
import it.hurts.sskirillss.relics.items.relics.base.data.leveling.LevelingSourceData;
import it.hurts.sskirillss.relics.items.relics.base.data.leveling.LevelingSourcesData;
import it.hurts.sskirillss.relics.items.relics.base.data.leveling.StatData;
import it.hurts.sskirillss.relics.items.relics.base.data.leveling.misc.GemColor;
import it.hurts.sskirillss.relics.items.relics.base.data.leveling.misc.GemShape;
import it.hurts.sskirillss.relics.items.relics.base.data.leveling.misc.UpgradeOperation;
import it.hurts.sskirillss.relics.items.relics.base.data.loot.LootData;
import it.hurts.sskirillss.relics.items.relics.base.data.loot.misc.LootEntries;
import it.hurts.sskirillss.relics.items.relics.base.data.research.ResearchData;
import it.hurts.sskirillss.relics.items.relics.base.data.style.StyleData;
import it.hurts.sskirillss.relics.items.relics.base.data.style.TooltipData;
import it.hurts.sskirillss.relics.utils.EntityUtils;
import it.hurts.sskirillss.relics.utils.MathUtils;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.tooltip.ClientTooltipComponent;
import net.minecraft.client.resources.MobEffectTextureManager;
import net.minecraft.core.Holder;
import net.minecraft.core.component.DataComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.tags.DamageTypeTags;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.food.FoodProperties.Builder;
import net.minecraft.world.inventory.ClickAction;
import net.minecraft.world.inventory.tooltip.TooltipComponent;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.PotionItem;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.UseAnim;
import net.minecraft.world.item.CreativeModeTab.TabVisibility;
import net.minecraft.world.item.Item.Properties;
import net.minecraft.world.item.alchemy.PotionContents;
import net.minecraft.world.level.Level;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.living.LivingIncomingDamageEvent;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import top.theillusivec4.curios.api.SlotContext;

public class InfiniteHamItem extends RelicItem {
   public InfiniteHamItem() {
      super(new Properties().stacksTo(1).food(new Builder().build()).rarity(Rarity.RARE));
   }

   @Override
   public RelicData constructDefaultRelicData() {
      return RelicData.builder()
         .abilities(
            AbilitiesData.builder()
               .ability(
                  AbilityData.builder("regeneration")
                     .requiredPoints(2)
                     .stat(
                        StatData.builder("cooldown")
                           .initialValue(30.0, 15.0)
                           .upgradeModifier(UpgradeOperation.ADD, -0.5)
                           .formatValue(value -> MathUtils.round(value, 1))
                           .build()
                     )
                     .stat(
                        StatData.builder("feed")
                           .initialValue(1.0, 3.0)
                           .upgradeModifier(UpgradeOperation.ADD, 1.0)
                           .formatValue(value -> (int)MathUtils.round(value, 0))
                           .build()
                     )
                     .research(
                        ResearchData.builder()
                           .star(0, 8, 7)
                           .star(1, 19, 7)
                           .star(2, 5, 15)
                           .star(3, 10, 16)
                           .star(4, 17, 20)
                           .star(5, 7, 24)
                           .link(1, 3)
                           .link(3, 0)
                           .link(3, 2)
                           .link(3, 4)
                           .link(3, 5)
                           .build()
                     )
                     .build()
               )
               .ability(
                  AbilityData.builder("marinade")
                     .requiredLevel(5)
                     .stat(
                        StatData.builder("duration")
                           .initialValue(1.0, 3.0)
                           .upgradeModifier(UpgradeOperation.MULTIPLY_BASE, 0.2)
                           .formatValue(value -> MathUtils.round(value, 1))
                           .build()
                     )
                     .research(
                        ResearchData.builder()
                           .star(0, 18, 5)
                           .star(1, 8, 7)
                           .star(2, 4, 16)
                           .star(3, 17, 16)
                           .star(4, 10, 21)
                           .star(5, 17, 23)
                           .star(6, 5, 25)
                           .star(7, 10, 29)
                           .link(0, 1)
                           .link(0, 3)
                           .link(1, 3)
                           .link(1, 2)
                           .link(3, 4)
                           .link(2, 4)
                           .link(3, 5)
                           .link(4, 7)
                           .link(2, 6)
                           .build()
                     )
                     .build()
               )
               .ability(
                  AbilityData.builder("meat_bat")
                     .requiredLevel(10)
                     .stat(
                        StatData.builder("damage")
                           .initialValue(0.5, 2.0)
                           .upgradeModifier(UpgradeOperation.MULTIPLY_BASE, 0.25)
                           .formatValue(value -> MathUtils.round(value, 1))
                           .build()
                     )
                     .stat(
                        StatData.builder("stun")
                           .initialValue(0.05, 0.25)
                           .upgradeModifier(UpgradeOperation.MULTIPLY_BASE, 0.1)
                           .formatValue(value -> MathUtils.round(value, 2))
                           .build()
                     )
                     .research(
                        ResearchData.builder()
                           .star(0, 10, 5)
                           .star(1, 18, 7)
                           .star(2, 3, 9)
                           .star(3, 17, 14)
                           .star(4, 10, 17)
                           .star(5, 3, 19)
                           .star(6, 9, 24)
                           .star(7, 18, 24)
                           .link(1, 4)
                           .link(4, 0)
                           .link(4, 2)
                           .link(4, 3)
                           .link(4, 5)
                           .link(4, 6)
                           .link(4, 7)
                           .link(0, 3)
                           .link(3, 7)
                           .link(7, 6)
                           .link(6, 5)
                           .link(5, 2)
                           .link(2, 0)
                           .link(2, 0)
                           .build()
                     )
                     .build()
               )
               .build()
         )
         .leveling(
            LevelingData.builder()
               .initialCost(100)
               .maxLevel(20)
               .step(100)
               .sources(
                  LevelingSourcesData.builder()
                     .source(LevelingSourceData.abilityBuilder("regeneration").initialValue(1).gem(GemShape.SQUARE, GemColor.ORANGE).build())
                     .source(LevelingSourceData.abilityBuilder("marinade").initialValue(1).gem(GemShape.SQUARE, GemColor.ORANGE).build())
                     .source(LevelingSourceData.abilityBuilder("meat_bat").initialValue(1).gem(GemShape.SQUARE, GemColor.ORANGE).build())
                     .build()
               )
               .build()
         )
         .style(StyleData.builder().tooltip(TooltipData.builder().borderTop(-10204607).borderBottom(-10935280).textured(true).build()).build())
         .loot(LootData.builder().entry(LootEntries.VILLAGE).build())
         .build();
   }

   @Override
   public void gatherCreativeTabContent(CreativeContentConstructor constructor) {
      ItemStack stack = this.getDefaultInstance();
      this.setPieces(stack, this.getMaxPieces());
      constructor.entry((CreativeModeTab)CreativeTabRegistry.RELICS_TAB.get(), TabVisibility.PARENT_AND_SEARCH_TABS, stack);
   }

   public void inventoryTick(@NotNull ItemStack stack, @NotNull Level level, @NotNull Entity entityIn, int itemSlot, boolean isSelected) {
      if (!level.isClientSide()
         && entityIn instanceof Player player
         && this.canPlayerUseAbility(player, stack, "regeneration")
         && entityIn.tickCount % (int)Math.max(1.0, this.getStatValue(stack, "regeneration", "cooldown") * 20.0) == 0
         && this.getPieces(stack) < this.getMaxPieces()) {
         this.addPieces(stack, 1);
      }
   }

   @NotNull
   public InteractionResultHolder<ItemStack> use(@NotNull Level world, Player player, @NotNull InteractionHand hand) {
      ItemStack stack = player.getItemInHand(hand);
      if (this.canPlayerUseAbility(player, stack, "regeneration") && this.getPieces(stack) > 0 && player.getFoodData().needsFood()) {
         player.startUsingItem(hand);
         return InteractionResultHolder.consume(stack);
      } else {
         return InteractionResultHolder.pass(stack);
      }
   }

   public ItemStack finishUsingItem(ItemStack stack, Level level, LivingEntity entity) {
      if (entity instanceof Player player && this.canPlayerUseAbility(player, stack, "regeneration")) {
         FoodProperties properties = this.getFoodProperties(stack, entity);
         if (properties == null) {
            return stack;
         }

         player.eat(level, stack.copy());
         int eaten = (int)Math.ceil(properties.nutrition() / this.getStatValue(stack, "regeneration", "feed"));
         if (eaten > 0) {
            this.addPieces(stack, -eaten);
            if (this.isLevelingSourceUnlocked(stack, "regeneration")) {
               this.spreadRelicExperience(player, stack, eaten);
            }
         }

         return stack;
      } else {
         return stack;
      }
   }

   @Nullable
   public FoodProperties getFoodProperties(ItemStack stack, @Nullable LivingEntity entity) {
      if (entity instanceof Player player && this.canPlayerUseAbility(player, stack, "regeneration")) {
         int charge = this.getPieces(stack);
         if (charge == 0) {
            return null;
         }

         int nutrition = Math.min((int)Math.ceil(charge * this.getStatValue(stack, "regeneration", "feed")), 20 - player.getFoodData().getFoodLevel());
         Builder builder = new Builder().nutrition(nutrition).saturationModifier(nutrition / 3.0F);
         if (this.canPlayerUseAbility(player, stack, "marinade")) {
            PotionContents contents = (PotionContents)stack.get(DataComponents.POTION_CONTENTS);
            if (contents != null) {
               contents.forEachEffect(
                  effect -> {
                     Holder<MobEffect> holder = effect.getEffect();
                     boolean isInstant = ((MobEffect)holder.value()).isInstantenous();
                     builder.effect(
                        () -> new MobEffectInstance(
                           holder,
                           isInstant ? 1 : (int)(nutrition * this.getStatValue(stack, "marinade", "duration") * 20.0),
                           effect.getAmplifier(),
                           !isInstant && effect.isAmbient(),
                           !isInstant && effect.isVisible(),
                           !isInstant && effect.showIcon()
                        ),
                        1.0F
                     );
                  }
               );
            }
         }

         return builder.build();
      } else {
         return super.getFoodProperties(stack, entity);
      }
   }

   public boolean isFoil(ItemStack stack) {
      return stack.get(DataComponents.POTION_CONTENTS) != null;
   }

   public int getUseDuration(@NotNull ItemStack stack, LivingEntity entity) {
      return 32;
   }

   @NotNull
   public UseAnim getUseAnimation(@NotNull ItemStack stack) {
      return UseAnim.EAT;
   }

   @Override
   public boolean canEquipFromUse(SlotContext slotContext, ItemStack stack) {
      return false;
   }

   public int getMaxPieces() {
      return 6;
   }

   public int getPieces(ItemStack stack) {
      return Math.clamp(((Integer)stack.getOrDefault(DataComponentRegistry.CHARGE, 0)).intValue(), 0, this.getMaxPieces());
   }

   public void setPieces(ItemStack stack, int amount) {
      stack.set(DataComponentRegistry.CHARGE, Math.clamp(amount, 0, this.getMaxPieces()));
   }

   public void addPieces(ItemStack stack, int amount) {
      this.setPieces(stack, this.getPieces(stack) + amount);
   }

   public Optional<TooltipComponent> getTooltipImage(ItemStack stack) {
      PotionContents contents = (PotionContents)stack.get(DataComponents.POTION_CONTENTS);
      return contents == null
         ? Optional.empty()
         : Optional.of(
            new InfiniteHamItem.InfiniteHamTooltip(
               StreamSupport.<MobEffectInstance>stream(contents.getAllEffects().spliterator(), false).collect(Collectors.toList())
            )
         );
   }

   @OnlyIn(Dist.CLIENT)
   public record ClientInfiniteHamTooltip(InfiniteHamItem.InfiniteHamTooltip tooltip) implements ClientTooltipComponent {
      public int getHeight() {
         return this.tooltip.effects().size() * 11;
      }

      public int getWidth(Font font) {
         return 100;
      }

      public void renderImage(Font font, int mouseX, int mouseY, GuiGraphics guiGraphics) {
         int yOff = 0;

         for (MobEffectInstance effect : this.tooltip.effects()) {
            Holder<MobEffect> holder = effect.getEffect();
            MobEffectTextureManager manager = Minecraft.getInstance().getMobEffectTextures();
            guiGraphics.blit(mouseX, mouseY + yOff, 0, 10, 10, manager.get(holder));
            MutableComponent name = Component.translatable(effect.getDescriptionId());
            if (effect.getAmplifier() > 0) {
               name = Component.translatable("potion.withAmplifier", new Object[]{name, Component.translatable("potion.potency." + effect.getAmplifier())});
            }

            name.withStyle(((MobEffect)holder.value()).getCategory().getTooltipFormatting());
            guiGraphics.drawString(font, name, mouseX + 12, mouseY + 2 + yOff, 16777215);
            yOff += 11;
         }
      }
   }

   public record InfiniteHamTooltip(List<MobEffectInstance> effects) implements TooltipComponent {
   }

   @EventBusSubscriber
   public static class InfinityHamEvents {
      @SubscribeEvent
      public static void onLivingDamage(LivingIncomingDamageEvent event) {
         DamageSource source = event.getSource();
         if (source.getDirectEntity() instanceof Player player && source.is(DamageTypeTags.IS_PLAYER_ATTACK)) {
            ItemStack stack = player.getMainHandItem();
            if (stack.getItem() instanceof InfiniteHamItem relic && relic.canPlayerUseAbility(player, stack, "meat_bat")) {
               int charge = relic.getPieces(stack);
               if (charge > 0) {
                  if (relic.isLevelingSourceUnlocked(stack, "meat_bat")) {
                     relic.spreadRelicExperience(player, stack, charge);
                  }

                  event.setAmount((float)(event.getAmount() + relic.getStatValue(stack, "meat_bat", "damage") * charge));
                  event.getEntity()
                     .addEffect(new MobEffectInstance(EffectRegistry.STUN, (int)Math.round(relic.getStatValue(stack, "meat_bat", "stun") * charge * 20.0), 0));
                  relic.setPieces(stack, 0);
               }
            }
         }
      }

      @SubscribeEvent
      public static void onSlotClick(ContainerSlotClickEvent event) {
         if (event.getAction() == ClickAction.PRIMARY) {
            Player player = event.getEntity();
            ItemStack heldStack = event.getHeldStack();
            ItemStack slotStack = event.getSlotStack();
            if (heldStack.getItem() instanceof PotionItem
               && slotStack.getItem() instanceof InfiniteHamItem relic
               && relic.canPlayerUseAbility(player, slotStack, "marinade")) {
               PotionContents contents = (PotionContents)heldStack.getOrDefault(DataComponents.POTION_CONTENTS, PotionContents.EMPTY);
               List<MobEffectInstance> effects = StreamSupport.<MobEffectInstance>stream(contents.getAllEffects().spliterator(), false).toList();
               if (effects.isEmpty()) {
                  slotStack.set(DataComponents.POTION_CONTENTS, null);
               } else {
                  slotStack.set(DataComponents.POTION_CONTENTS, new PotionContents(Optional.empty(), Optional.empty(), effects));
                  if (relic.isLevelingSourceUnlocked(slotStack, "marinade")) {
                     relic.spreadRelicExperience(player, slotStack, effects.size());
                  }
               }

               ItemStack bottle = new ItemStack(Items.GLASS_BOTTLE);
               if (player.containerMenu.getCarried().getCount() <= 1) {
                  player.containerMenu.setCarried(bottle);
               } else {
                  player.containerMenu.getCarried().shrink(1);
                  EntityUtils.addItem(player, bottle);
               }

               player.playSound(SoundEvents.BOTTLE_FILL, 1.0F, 1.0F);
               event.setCanceled(true);
            }
         }
      }
   }
}
