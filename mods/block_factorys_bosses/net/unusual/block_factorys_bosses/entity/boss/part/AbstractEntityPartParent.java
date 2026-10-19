package net.unusual.block_factorys_bosses.entity.boss.part;

import javax.annotation.ParametersAreNonnullByDefault;
import net.minecraft.FieldsAreNonnullByDefault;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;

@FieldsAreNonnullByDefault
@MethodsReturnNonnullByDefault
@ParametersAreNonnullByDefault
public interface AbstractEntityPartParent<ParentT extends Entity & AbstractEntityPartParent<ParentT, PartT>, PartT extends AbstractEntityPart<ParentT, PartT>> {
   boolean hurt(PartT var1, DamageSource var2, float var3);
}
