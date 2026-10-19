package cn.leolezury.eternalstarlight.common.spell;

import cn.leolezury.eternalstarlight.common.registry.ESSpells;
import java.util.Objects;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Item;

public record SpellCastData(boolean hasSpell, AbstractSpell spell, int strength, int castTicks, boolean offhand) {
   public static final StreamCodec<RegistryFriendlyByteBuf, SpellCastData> STREAM_CODEC = StreamCodec.ofMember(
      SpellCastData::toNetwork, SpellCastData::fromNetwork
   );

   public static SpellCastData getDefault() {
      return new SpellCastData(false, ESSpells.GUIDANCE_OF_STARS.get(), 0, 0, false);
   }

   public boolean hasSpell() {
      return this.hasSpell && this.spell() != null;
   }

   public SpellCastData increaseTick() {
      return new SpellCastData(this.hasSpell(), this.spell(), this.strength(), this.castTicks() + 1, this.offhand());
   }

   public static SpellCastData fromNetwork(RegistryFriendlyByteBuf buf) {
      boolean hasSpell = buf.readBoolean();
      AbstractSpell spell = (AbstractSpell)buf.readById(ESSpells.SPELLS.registry()::byId);
      int strength = buf.readInt();
      int ticks = buf.readInt();
      boolean offhand = buf.readBoolean();
      return new SpellCastData(hasSpell, spell, strength, ticks, offhand);
   }

   public void toNetwork(RegistryFriendlyByteBuf buf) {
      buf.writeBoolean(this.hasSpell());
      buf.writeById(ESSpells.SPELLS.registry()::getId, this.spell());
      buf.writeInt(this.strength());
      buf.writeInt(this.castTicks());
      buf.writeBoolean(this.offhand());
   }

   @Override
   public boolean equals(Object o) {
      if (o != null && this.getClass() == o.getClass()) {
         SpellCastData that = (SpellCastData)o;
         return this.strength == that.strength
            && this.castTicks == that.castTicks
            && this.offhand == that.offhand
            && this.hasSpell == that.hasSpell
            && Objects.equals(this.spell, that.spell);
      } else {
         return false;
      }
   }

   @Override
   public int hashCode() {
      return Objects.hash(this.hasSpell, this.spell, this.strength, this.castTicks, this.offhand);
   }

   public record ItemSpellSource(Item item, InteractionHand hand) implements SpellCastData.SpellSource {
      @Override
      public boolean canContinue(LivingEntity living) {
         return living.getItemInHand(this.hand()).is(this.item());
      }
   }

   public interface SpellSource {
      boolean canContinue(LivingEntity var1);
   }
}
