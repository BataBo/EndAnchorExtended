package de.kxmischesdomi.just_end_anchor.loot_table;

import de.kxmischesdomi.just_end_anchor.common.entities.EndAnchorBlockEntity;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonObject;
import de.kxmischesdomi.just_end_anchor.EndAnchorMod;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.functions.LootItemConditionalFunction;
import net.minecraft.world.level.storage.loot.functions.LootItemFunctionType;
import net.minecraft.world.level.storage.loot.parameters.LootContextParam;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.minecraft.world.level.storage.loot.providers.nbt.NbtProvider;

import java.util.Set;

/**
 * @author BataBo | https://github.com/BataBo/EndAnchorExtended
 * @since 1.0
 */
public class CompassTypeLootFunction extends LootItemConditionalFunction {
    final NbtProvider source;

    public  static LootItemFunctionType CompassTypeFunction;

    public static void init()
    {
        CompassTypeFunction = (LootItemFunctionType) Registry.register(BuiltInRegistries.LOOT_FUNCTION_TYPE, new ResourceLocation(EndAnchorMod.MOD_ID,"drop_compass"), new LootItemFunctionType(new CompassTypeLootFunction.Serializer()));
    }

    protected CompassTypeLootFunction(LootItemCondition[] lootItemConditions, NbtProvider provider) {
        super(lootItemConditions);
        source = provider;
    }




    public Set<LootContextParam<?>> getReferencedContextParams() {
        return this.source.getReferencedContextParams();
    }

    @Override
    protected ItemStack run(ItemStack itemStack, LootContext lootContext) {
        CompoundTag tag = (CompoundTag) this.source.get(lootContext);
        return EndAnchorBlockEntity.DetermineDrop(lootContext.getLevel(),tag);
    }

    @Override
    public LootItemFunctionType getType() {
        return CompassTypeFunction;
    }

    public static class Serializer extends LootItemConditionalFunction.Serializer<CompassTypeLootFunction>
    {
        @Override
        public CompassTypeLootFunction deserialize(JsonObject jsonObject, JsonDeserializationContext jsonDeserializationContext, LootItemCondition[] lootItemConditions) {
            NbtProvider nbtProvider = (NbtProvider) GsonHelper.getAsObject(jsonObject, "source", jsonDeserializationContext, NbtProvider.class);
            return new CompassTypeLootFunction(lootItemConditions,nbtProvider);
        }
    }
}
