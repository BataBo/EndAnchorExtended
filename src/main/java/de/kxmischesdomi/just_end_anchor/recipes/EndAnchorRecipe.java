package de.kxmischesdomi.just_end_anchor.recipes;

import de.kxmischesdomi.just_end_anchor.common.registry.ModItems;
import de.kxmischesdomi.just_end_anchor.EndAnchorMod;
import de.kxmischesdomi.just_end_anchor.enums.EndAnchorType;
import net.minecraft.core.GlobalPos;
import net.minecraft.core.Registry;
import net.minecraft.core.RegistryAccess;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.inventory.CraftingContainer;
import net.minecraft.world.item.CompassItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.*;
import net.minecraft.world.level.Level;

/**
 * @author BataBo | https://github.com/BataBo/EndAnchorExtended
 * @since 1.0
 */
public class EndAnchorRecipe extends CustomRecipe {

    private static RecipeSerializer<EndAnchorRecipe> serializer;

    public  static  void init()
    {
        serializer = Registry.register(BuiltInRegistries.RECIPE_SERIALIZER, new ResourceLocation(EndAnchorMod.MOD_ID,"crafting_special_end_anchor") ,new SimpleCraftingRecipeSerializer<EndAnchorRecipe>(EndAnchorRecipe::new));
    }

    public EndAnchorRecipe(ResourceLocation resourceLocation, CraftingBookCategory craftingBookCategory) {
        super(resourceLocation, craftingBookCategory);
    }

    private  static Boolean isValidRecipe(CraftingContainer container)
    {
        return  (container.getItem(0).is(Items.CRYING_OBSIDIAN) && container.getItem(1).is(Items.PURPUR_BLOCK) && container.getItem(2).is(Items.CRYING_OBSIDIAN)
                && container.getItem(3).is(Items.PURPUR_BLOCK) && (container.getItem(4).is(Items.COMPASS) || container.getItem(4).is(Items.RECOVERY_COMPASS)) && container.getItem(5).is(Items.PURPUR_BLOCK)
                && container.getItem(6).is(Items.CRYING_OBSIDIAN) && container.getItem(7).is(Items.PURPUR_BLOCK) && container.getItem(8).is(Items.CRYING_OBSIDIAN));

    }

    @Override
    public boolean matches(CraftingContainer container, Level level) {
        return isValidRecipe(container);
    }

    @Override
    public ItemStack assemble(CraftingContainer container, RegistryAccess registryAccess) {
        if(!isValidRecipe(container))
            return ItemStack.EMPTY;
        else
        {
            ItemStack itemStack2 = container.getItem(4);
            if (!itemStack2.isEmpty())
            {
                Item item = itemStack2.getItem();
                if(item instanceof CompassItem)
                {
                    if(CompassItem.isLodestoneCompass(itemStack2))
                    {
                        GlobalPos pos = CompassItem.getLodestonePosition(itemStack2.getTag());
                        ItemStack EndAnchorBlock = new ItemStack(ModItems.END_ANCHOR);
                        CompoundTag tag = EndAnchorBlock.getOrCreateTag();
                        tag.putInt("EndAnchorType", EndAnchorType.LodeStoneTeleporter.getValue());
                        tag.putString("LodeStoneDimension",itemStack2.getTag().getString("LodestoneDimension"));
                        CompoundTag LoadStonePos = new CompoundTag();
                        LoadStonePos.putInt("x",pos.pos().getX());
                        LoadStonePos.putInt("y",pos.pos().getY());
                        LoadStonePos.putInt("z",pos.pos().getZ());
                        tag.put("LoadStonePos",LoadStonePos);
                        return EndAnchorBlock;
                    }
                    else
                    {
                        ItemStack EndAnchorBlock = new ItemStack(ModItems.END_ANCHOR);
                        CompoundTag tag = EndAnchorBlock.getOrCreateTag();
                        tag.putInt("EndAnchorType", EndAnchorType.RespawnAnchor.getValue());
                        return EndAnchorBlock;
                    }
                }
                else if(item == Items.RECOVERY_COMPASS)
                {
                    ItemStack EndAnchorBlock = new ItemStack(ModItems.END_ANCHOR);
                    CompoundTag tag = EndAnchorBlock.getOrCreateTag();
                    tag.putInt("EndAnchorType", EndAnchorType.RecoveryTeleporter.getValue());
                    return EndAnchorBlock;
                }
                else
                {
                    return ItemStack.EMPTY;
                }
            }
            else
            {
                return  ItemStack.EMPTY;
            }
        }

    }

    @Override
    public boolean canCraftInDimensions(int i, int j) {
        return i == 3 && j == 3;
    }

    @Override
    public RecipeSerializer<?> getSerializer() {
        return serializer;
    }

    @Override
    public CraftingBookCategory category() {
        return CraftingBookCategory.MISC;
    }
}
