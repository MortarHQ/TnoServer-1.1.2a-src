package fuzs.puzzleslib.api.core.v1;

import fuzs.puzzleslib.api.init.v3.registry.LookupHelper;
import java.util.function.BiConsumer;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload.Type;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.packs.repository.PackCompatibility;
import net.minecraft.server.packs.repository.Pack.Metadata;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.flag.FeatureFlagSet;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.Explosion;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;

public interface CommonAbstractions {
   CommonAbstractions INSTANCE = ServiceProviderHelper.load(CommonAbstractions.class);

   MinecraftServer getMinecraftServer();

   boolean hasChannel(ServerPlayer var1, Type<?> var2);

   void openMenu(ServerPlayer var1, MenuProvider var2, BiConsumer<ServerPlayer, RegistryFriendlyByteBuf> var3);

   Entity getPartEntityParent(Entity var1);

   boolean isBossMob(EntityType<?> var1);

   float getEnchantPowerBonus(BlockState var1, Level var2, BlockPos var3);

   boolean canEquip(ItemStack var1, EquipmentSlot var2, LivingEntity var3);

   default int getMobLootingLevel(Entity target, @Nullable Entity attacker, @Nullable DamageSource damageSource) {
      if (attacker instanceof LivingEntity livingEntity) {
         Holder<Enchantment> enchantment = LookupHelper.lookupEnchantment(target, Enchantments.LOOTING);
         return EnchantmentHelper.getEnchantmentLevel(enchantment, livingEntity);
      } else {
         return 0;
      }
   }

   boolean getMobGriefingRule(Level var1, @Nullable Entity var2);

   void onPlayerDestroyItem(Player var1, ItemStack var2, @Nullable InteractionHand var3);

   @Nullable
   MobSpawnType getMobSpawnType(Mob var1);

   Metadata createPackInfo(ResourceLocation var1, Component var2, PackCompatibility var3, FeatureFlagSet var4, boolean var5);

   boolean canApplyAtEnchantingTable(Holder<Enchantment> var1, ItemStack var2);

   default boolean isAllowedOnBooks(Holder<Enchantment> enchantment) {
      return true;
   }

   boolean isBookEnchantable(ItemStack var1, ItemStack var2);

   boolean onExplosionStart(Level var1, Explosion var2);
}
