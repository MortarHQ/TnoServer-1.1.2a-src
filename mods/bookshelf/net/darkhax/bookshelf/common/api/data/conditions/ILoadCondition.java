package net.darkhax.bookshelf.common.api.data.conditions;

public interface ILoadCondition {
   boolean allowLoading();

   ConditionType getType();
}
