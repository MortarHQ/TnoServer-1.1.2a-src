package team.lodestar.lodestone.systems.item.tools.magic;

import net.minecraft.world.entity.EquipmentSlotGroup;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.AttributeModifier.Operation;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.component.ItemAttributeModifiers;
import team.lodestar.lodestone.registry.common.LodestoneAttributes;
import team.lodestar.lodestone.systems.item.LodestoneItemProperties;
import team.lodestar.lodestone.systems.item.tools.LodestoneShovelItem;

public class MagicShovelItem extends LodestoneShovelItem {
   public MagicShovelItem(Tier tier, float attackDamage, float attackSpeed, float magicDamage, LodestoneItemProperties properties) {
      super(
         tier,
         attackDamage,
         attackSpeed,
         properties.mergeAttributes(
            ItemAttributeModifiers.builder()
               .add(
                  LodestoneAttributes.MAGIC_DAMAGE,
                  new AttributeModifier(LodestoneAttributes.BASE_MAGIC_DAMAGE, magicDamage, Operation.ADD_VALUE),
                  EquipmentSlotGroup.MAINHAND
               )
               .build()
         )
      );
   }
}
