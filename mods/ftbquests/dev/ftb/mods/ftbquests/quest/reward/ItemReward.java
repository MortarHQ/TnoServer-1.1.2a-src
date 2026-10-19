package dev.ftb.mods.ftbquests.quest.reward;

import dev.architectury.hooks.item.ItemStackHooks;
import dev.architectury.networking.NetworkManager;
import dev.ftb.mods.ftblibrary.config.ConfigGroup;
import dev.ftb.mods.ftblibrary.icon.Icon;
import dev.ftb.mods.ftblibrary.icon.ItemIcon;
import dev.ftb.mods.ftblibrary.ui.Widget;
import dev.ftb.mods.ftblibrary.util.client.PositionedIngredient;
import dev.ftb.mods.ftbquests.FTBQuests;
import dev.ftb.mods.ftbquests.net.NotifyItemRewardMessage;
import dev.ftb.mods.ftbquests.quest.Quest;
import dev.ftb.mods.ftbquests.registry.ModItems;
import io.netty.handler.codec.EncoderException;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import net.minecraft.core.HolderLookup.Provider;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.util.RandomSource;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import org.jetbrains.annotations.Nullable;

public class ItemReward extends Reward {
   private ItemStack item;
   private int count;
   private int randomBonus;
   private boolean onlyOne;

   public ItemReward(long id, Quest quest, ItemStack is) {
      this(id, quest, is, 1);
   }

   public ItemReward(long id, Quest quest, ItemStack is, int count) {
      super(id, quest);
      this.item = is;
      this.count = count;
      this.randomBonus = 0;
      this.onlyOne = false;
   }

   public ItemReward(long id, Quest quest) {
      this(id, quest, new ItemStack(Items.APPLE));
   }

   public ItemStack getItem() {
      return this.item;
   }

   public int getCount() {
      return this.count;
   }

   @Override
   public RewardType getType() {
      return RewardTypes.ITEM;
   }

   @Override
   public void writeData(CompoundTag nbt, Provider provider) {
      super.writeData(nbt, provider);
      if (!this.item.isEmpty()) {
         nbt.put("item", this.item.save(provider));
      }

      if (this.count > 1) {
         nbt.putInt("count", this.count);
      }

      if (this.randomBonus > 0) {
         nbt.putInt("random_bonus", this.randomBonus);
      }

      if (this.onlyOne) {
         nbt.putBoolean("only_one", true);
      }
   }

   @Override
   public void readData(CompoundTag nbt, Provider provider) {
      super.readData(nbt, provider);
      this.item = itemOrMissingFromNBT(nbt.get("item"), provider);
      this.count = nbt.getInt("count");
      if (this.count == 0) {
         this.count = this.item.getCount();
         this.item.setCount(1);
      }

      this.randomBonus = nbt.getInt("random_bonus");
      this.onlyOne = nbt.getBoolean("only_one");
   }

   @Override
   public void writeNetData(RegistryFriendlyByteBuf buffer) {
      super.writeNetData(buffer);

      try {
         ItemStack.OPTIONAL_STREAM_CODEC.encode(buffer, this.item);
      } catch (EncoderException e) {
         FTBQuests.LOGGER.error("Caught EncoderException while encoding item for client sync! {}", e.getMessage());
         FTBQuests.LOGGER.error("- Item:");
         FTBQuests.LOGGER.error(this.item);
         FTBQuests.LOGGER.error("- Item components:");
         FTBQuests.LOGGER.error(this.item.getComponents());
         ItemStack.OPTIONAL_STREAM_CODEC.encode(buffer, ((Item)ModItems.MISSING_ITEM.get()).getDefaultInstance());
      }

      buffer.writeVarInt(this.count);
      buffer.writeVarInt(this.randomBonus);
      buffer.writeBoolean(this.onlyOne);
   }

   @Override
   public void readNetData(RegistryFriendlyByteBuf buffer) {
      super.readNetData(buffer);
      this.item = (ItemStack)ItemStack.OPTIONAL_STREAM_CODEC.decode(buffer);
      this.count = buffer.readVarInt();
      this.randomBonus = buffer.readVarInt();
      this.onlyOne = buffer.readBoolean();
   }

   @OnlyIn(Dist.CLIENT)
   @Override
   public void fillConfigGroup(ConfigGroup config) {
      super.fillConfigGroup(config);
      config.addItemStack("item", this.item, v -> this.item = v, ItemStack.EMPTY, true, false).setNameKey("ftbquests.reward.ftbquests.item");
      config.addInt("count", this.count, v -> this.count = v, 1, 1, 8192);
      config.addInt("random_bonus", this.randomBonus, v -> this.randomBonus = v, 0, 0, 8192).setNameKey("ftbquests.reward.random_bonus");
      config.addBool("only_one", this.onlyOne, v -> this.onlyOne = v, false);
   }

   @Override
   public void claim(ServerPlayer player, boolean notify) {
      if (!this.onlyOne || !player.getInventory().contains(this.item)) {
         int size = this.count + player.level().random.nextInt(this.randomBonus + 1);

         while (size > 0) {
            int s = Math.min(size, this.item.getMaxStackSize());
            ItemStackHooks.giveItem(player, ItemStackHooks.copyWithCount(this.item, s));
            size -= s;
         }

         if (notify) {
            NetworkManager.sendToPlayer(player, new NotifyItemRewardMessage(this.item, size, this.disableRewardScreenBlur));
         }
      }
   }

   @Override
   public boolean automatedClaimPre(BlockEntity blockEntity, List<ItemStack> items, RandomSource random, UUID playerId, @Nullable ServerPlayer player) {
      int size = this.count + random.nextInt(this.randomBonus + 1);

      while (size > 0) {
         int s = Math.min(size, this.item.getMaxStackSize());
         items.add(ItemStackHooks.copyWithCount(this.item, s));
         size -= s;
      }

      return true;
   }

   @Override
   public void automatedClaimPost(BlockEntity blockEntity, UUID playerId, @Nullable ServerPlayer player) {
   }

   @OnlyIn(Dist.CLIENT)
   public MutableComponent getAltTitle() {
      if (this.randomBonus > 0) {
         return Component.literal(this.count + "-" + (this.count + this.randomBonus) + "x ").append(this.item.getHoverName());
      } else {
         return this.count > 1 ? Component.literal(this.count + "x ").append(this.item.getHoverName()) : this.item.getHoverName().copy();
      }
   }

   @OnlyIn(Dist.CLIENT)
   @Override
   public Icon getAltIcon() {
      return this.item.isEmpty() ? super.getAltIcon() : ItemIcon.getItemIcon(ItemStackHooks.copyWithCount(this.item, 1));
   }

   @OnlyIn(Dist.CLIENT)
   @Override
   public Optional<PositionedIngredient> getIngredient(Widget widget) {
      return PositionedIngredient.of(this.item, widget, true);
   }

   @OnlyIn(Dist.CLIENT)
   @Override
   public String getButtonText() {
      return this.randomBonus > 0 ? this.count + "-" + (this.count + this.randomBonus) : Integer.toString(this.count);
   }
}
