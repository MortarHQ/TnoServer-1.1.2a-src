package de.ellpeck.naturesaura.items.tools;

import de.ellpeck.naturesaura.NaturesAura;
import de.ellpeck.naturesaura.reg.IModItem;
import de.ellpeck.naturesaura.reg.ModArmorMaterial;
import de.ellpeck.naturesaura.reg.ModRegistry;
import java.util.Comparator;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.attributes.AttributeModifier.Operation;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.ArmorItem.Type;
import net.minecraft.world.item.Item.Properties;
import net.minecraft.world.phys.AABB;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.living.LivingIncomingDamageEvent;
import net.neoforged.neoforge.event.tick.PlayerTickEvent.Post;

public class ItemArmor extends ArmorItem implements IModItem {
   private static final AttributeModifier SKY_MOVEMENT_MODIFIER = new AttributeModifier(
      ResourceLocation.fromNamespaceAndPath("naturesaura", "sky_movement_speed"), 0.15F, Operation.ADD_MULTIPLIED_TOTAL
   );
   private static final AttributeModifier SKY_STEP_MODIFIER = new AttributeModifier(
      ResourceLocation.fromNamespaceAndPath("naturesaura", "sky_step_modifier"), 0.5, Operation.ADD_VALUE
   );
   private static final Map<ModArmorMaterial, Item[]> SETS = new ConcurrentHashMap<>();
   private final String baseName;
   private final ModArmorMaterial material;

   public ItemArmor(String baseName, ModArmorMaterial materialIn, Type equipmentSlotIn) {
      super(materialIn.material, equipmentSlotIn, new Properties().durability(materialIn.getDurability(equipmentSlotIn)));
      this.baseName = baseName;
      this.material = materialIn;
      ModRegistry.ALL_ITEMS.add(this);
   }

   public static boolean isFullSetEquipped(LivingEntity entity, ModArmorMaterial material) {
      Item[] set = SETS.computeIfAbsent(
         material,
         m -> {
            ItemArmor[] computed = ModRegistry.ALL_ITEMS
               .stream()
               .filter(ix -> ix instanceof ItemArmor a && a.material == material)
               .map(ix -> (ItemArmor)ix)
               .sorted(Comparator.comparingInt(ix -> ix.getEquipmentSlot().ordinal()))
               .toArray(ItemArmor[]::new);
            return computed.length < 4 ? null : computed;
         }
      );
      if (set == null) {
         NaturesAura.LOGGER.warn("Tried to access full armor set of type {} but it was not found. Maybe it was accessed too early?", material.name());
         return false;
      }

      for (int i = 0; i < 4; i++) {
         EquipmentSlot slot = EquipmentSlot.values()[i + 2];
         ItemStack stack = entity.getItemBySlot(slot);
         if (stack.getItem() != set[i] && (slot != EquipmentSlot.CHEST || stack.getItem() != Items.ELYTRA)) {
            return false;
         }
      }

      return true;
   }

   @Override
   public String getBaseName() {
      return this.baseName;
   }

   @EventBusSubscriber
   private static final class EventHandler {
      @SubscribeEvent
      public static void onAttack(LivingIncomingDamageEvent event) {
         LivingEntity entity = event.getEntity();
         if (!entity.level().isClientSide) {
            if (ItemArmor.isFullSetEquipped(entity, ModArmorMaterial.INFUSED)) {
               Entity source = event.getSource().getEntity();
               if (source instanceof LivingEntity) {
                  ((LivingEntity)source).addEffect(new MobEffectInstance(MobEffects.WITHER, 40));
               }
            } else if (ItemArmor.isFullSetEquipped(entity, ModArmorMaterial.DEPTH)) {
               for (LivingEntity other : entity.level().getEntitiesOfClass(LivingEntity.class, new AABB(entity.position(), entity.position()).inflate(2.0))) {
                  if (other != entity && !(entity instanceof Player player && player.isAlliedTo(other))) {
                     other.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 60, 255));
                  }
               }
            }
         }
      }

      @SubscribeEvent
      public static void update(Post event) {
         Player player = event.getEntity();
         AttributeInstance speed = player.getAttribute(Attributes.MOVEMENT_SPEED);
         AttributeInstance step = player.getAttribute(Attributes.STEP_HEIGHT);
         String key = "naturesaura:sky_equipped";
         CompoundTag nbt = player.getPersistentData();
         boolean equipped = ItemArmor.isFullSetEquipped(player, ModArmorMaterial.SKY);
         if (equipped && !nbt.getBoolean(key)) {
            nbt.putBoolean(key, true);
            if (!step.hasModifier(ItemArmor.SKY_STEP_MODIFIER.id())) {
               step.addPermanentModifier(ItemArmor.SKY_STEP_MODIFIER);
            }

            if (!speed.hasModifier(ItemArmor.SKY_MOVEMENT_MODIFIER.id())) {
               speed.addPermanentModifier(ItemArmor.SKY_MOVEMENT_MODIFIER);
            }
         } else if (!equipped && nbt.getBoolean(key)) {
            nbt.putBoolean(key, false);
            step.removeModifier(ItemArmor.SKY_STEP_MODIFIER.id());
            speed.removeModifier(ItemArmor.SKY_MOVEMENT_MODIFIER.id());
         }
      }
   }
}
