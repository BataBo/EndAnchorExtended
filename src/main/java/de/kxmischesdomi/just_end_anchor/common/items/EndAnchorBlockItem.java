package de.kxmischesdomi.just_end_anchor.common.items;

import de.kxmischesdomi.just_end_anchor.common.entities.EndAnchorBlockEntity;
import de.kxmischesdomi.just_end_anchor.enums.EndAnchorType;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;

/**
 * @author BataBo | https://github.com/BataBo/EndAnchorExtended
 * @since 1.0
 */
public class EndAnchorBlockItem extends BlockItem {

    public EndAnchorBlockItem(Block block, Properties properties) {
        super(block, properties);
    }

    @Override
    protected boolean placeBlock(BlockPlaceContext blockPlaceContext, BlockState blockState) {
        BlockPos position = blockPlaceContext.getClickedPos();
        boolean isPlaced = blockPlaceContext.getLevel().setBlock(position, blockState,11);
        if(isPlaced)
        {
            EndAnchorBlockEntity entity = new EndAnchorBlockEntity(position,blockState);
            CompoundTag tag = blockPlaceContext.getItemInHand().getTag();
            if(tag != null)
            {
                EndAnchorType endAnchorType = EndAnchorType.fromInt(tag.getInt("EndAnchorType"));
                entity.setHasLodeStone(endAnchorType);
                if(endAnchorType == EndAnchorType.LodeStoneTeleporter)
                {
                    entity.setLodeStoneDimension(tag.getString("LodeStoneDimension"));
                    CompoundTag LodeStonePos = tag.getCompound("LoadStonePos");
                    int x = LodeStonePos.getInt("x");
                    int y = LodeStonePos.getInt("y");
                    int z = LodeStonePos.getInt("z");
                    entity.setLodeStonePosition(x,y,z);
                }
            }
            else
            {
                entity.setHasLodeStone(EndAnchorType.RespawnAnchor);
            }
            blockPlaceContext.getLevel().setBlockEntity(entity);
            return true;
        }
        return false;
    }


}
