package io.github.Memoires.mysticism.network.c2s;

import dev.architectury.networking.NetworkManager.PacketContext;
import dev.architectury.utils.Env;
import io.github.Memoires.mysticism.ability.subclass.IBookStorage;
import io.github.Memoires.mysticism.menu.BookStorageMenu;
import io.github.Memoires.mysticism.registry.component.MysticismDataComponents;
import io.github.manasmods.manascore.skill.api.ManasSkillInstance;
import io.github.manasmods.tensura.registry.sound.TensuraSoundEvents;
import it.unimi.dsi.fastutil.objects.Object2IntMap.Entry;
import java.util.Iterator;
import net.minecraft.ChatFormatting;
import net.minecraft.core.Holder;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.core.component.DataComponents;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload.Type;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.ItemEnchantments;
import net.minecraft.world.item.enchantment.ItemEnchantments.Mutable;
import org.jetbrains.annotations.NotNull;

public record RequestChangeEnchantmentLevelPacket(int containerId, int slot, int delta) implements CustomPacketPayload {
   public static final Type<RequestChangeEnchantmentLevelPacket> TYPE = new Type(ResourceLocation.fromNamespaceAndPath("mysticism", "change_enchantment_level"));
   public static final StreamCodec<FriendlyByteBuf, RequestChangeEnchantmentLevelPacket> STREAM_CODEC = CustomPacketPayload.codec(
      RequestChangeEnchantmentLevelPacket::encode, RequestChangeEnchantmentLevelPacket::new
   );

   public RequestChangeEnchantmentLevelPacket(FriendlyByteBuf buf) {
      this(buf.readInt(), buf.readInt(), buf.readInt());
   }

   public void encode(FriendlyByteBuf buf) {
      buf.writeInt(this.containerId);
      buf.writeInt(this.slot);
      buf.writeInt(this.delta);
   }

   @NotNull
   public Type<? extends CustomPacketPayload> type() {
      return TYPE;
   }

   public void handle(PacketContext context) {
      if (context.getEnvironment() == Env.SERVER) {
         context.queue(
            () -> {
               Player player = context.getPlayer();
               if (player != null) {
                  if (player.containerMenu instanceof BookStorageMenu menu) {
                     if (menu.containerId == this.containerId) {
                        if (this.delta != 0) {
                           Slot menuSlot = menu.getSlot(this.slot);
                           ItemStack book = menuSlot.getItem();
                           if (book.is(Items.ENCHANTED_BOOK)) {
                              ManasSkillInstance instance = menu.getSkillInstance(player);
                              if (instance != null) {
                                 if (instance.getSkill() instanceof IBookStorage bookStorage) {
                                    if (bookStorage.isAllowedToUpgradeEnchantments()) {
                                       ItemEnchantments current = (ItemEnchantments)book.getOrDefault(
                                          DataComponents.STORED_ENCHANTMENTS, ItemEnchantments.EMPTY
                                       );
                                       Holder<Enchantment> enchantment = null;
                                       int currentLevel = 0;
                                       Iterator id = current.entrySet().iterator();
                                       if (id.hasNext()) {
                                          Entry<Holder<Enchantment>> entry = (Entry<Holder<Enchantment>>)id.next();
                                          enchantment = (Holder<Enchantment>)entry.getKey();
                                          currentLevel = entry.getIntValue();
                                       }

                                       if (enchantment != null) {
                                          ResourceLocation idx = menu.getEnchantmentId(enchantment);
                                          if (idx != null) {
                                             String enchantmentId = idx.toString();
                                             if (!bookStorage.getBlacklistEnchantments().contains(enchantmentId)) {
                                                int targetLevel = currentLevel + this.delta;
                                                if (this.delta > 0) {
                                                   int maxAllowed = menu.getMaxAllowedLevel(bookStorage, enchantment, enchantmentId);
                                                   if (targetLevel > maxAllowed) {
                                                      targetLevel = maxAllowed;
                                                   }

                                                   int actualDelta = targetLevel - currentLevel;
                                                   if (actualDelta <= 0) {
                                                      return;
                                                   }

                                                   int totalCost = 0;

                                                   for (int i = 0; i < actualDelta; i++) {
                                                      totalCost += (currentLevel + i + 1) * 10;
                                                   }

                                                   if (player.experienceLevel < totalCost && !player.isCreative()) {
                                                      player.displayClientMessage(
                                                         Component.translatable(
                                                               "mysticism.skill.scholar.library_of_babel.failed_to_level_up", new Object[]{totalCost}
                                                            )
                                                            .withStyle(ChatFormatting.RED),
                                                         false
                                                      );
                                                      player.playNotifySound(
                                                         (SoundEvent)TensuraSoundEvents.GENERIC_CAST_FAIL.get(), SoundSource.PLAYERS, 0.5F, 1.0F
                                                      );
                                                      return;
                                                   }

                                                   if (!player.isCreative()) {
                                                      player.displayClientMessage(
                                                         Component.translatable(
                                                               "mysticism.skill.scholar.library_of_babel.leveled_up",
                                                               new Object[]{((Enchantment)enchantment.value()).description(), totalCost}
                                                            )
                                                            .withStyle(ChatFormatting.GREEN),
                                                         false
                                                      );
                                                      player.giveExperienceLevels(-totalCost);
                                                   }

                                                   player.playNotifySound(SoundEvents.ENCHANTMENT_TABLE_USE, SoundSource.PLAYERS, 0.8F, 1.2F);

                                                   for (int i = 0; i < actualDelta; i++) {
                                                      instance.addMasteryPoint(player, (currentLevel + i + 2) / 2);
                                                   }
                                                } else {
                                                   if (targetLevel < 1) {
                                                      targetLevel = 1;
                                                   }

                                                   if (currentLevel == targetLevel) {
                                                      return;
                                                   }

                                                   int actualDelta = currentLevel - targetLevel;
                                                   int totalRefund = 0;

                                                   for (int i = 0; i < actualDelta; i++) {
                                                      int levelBeingRemoved = currentLevel - i;
                                                      int originalCost = levelBeingRemoved * 10;
                                                      totalRefund += originalCost / 2;
                                                   }

                                                   if (!player.isCreative() && totalRefund > 0) {
                                                      player.giveExperienceLevels(totalRefund);
                                                      player.displayClientMessage(
                                                         Component.translatable(
                                                               "mysticism.skill.scholar.library_of_babel.level_refunded",
                                                               new Object[]{((Enchantment)enchantment.value()).description(), totalRefund}
                                                            )
                                                            .withStyle(ChatFormatting.GREEN),
                                                         false
                                                      );
                                                   }

                                                   player.playNotifySound(SoundEvents.GRINDSTONE_USE, SoundSource.PLAYERS, 0.8F, 1.0F);
                                                }

                                                Mutable mutable = new Mutable(current);
                                                mutable.set(enchantment, targetLevel);
                                                book.set(DataComponents.STORED_ENCHANTMENTS, mutable.toImmutable());
                                                book.set((DataComponentType)MysticismDataComponents.ENHANCED_ENCHANTMENT.get(), idx);
                                                menuSlot.setChanged();
                                                menu.broadcastChanges();
                                             }
                                          }
                                       }
                                    }
                                 }
                              }
                           }
                        }
                     }
                  }
               }
            }
         );
      }
   }
}
