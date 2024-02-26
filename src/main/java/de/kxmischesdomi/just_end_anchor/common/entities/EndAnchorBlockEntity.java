package de.kxmischesdomi.just_end_anchor.common.entities;

import de.kxmischesdomi.just_end_anchor.common.registry.ModBlockEntities;
import de.kxmischesdomi.just_end_anchor.enums.EndAnchorType;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.entity.TheEndPortalBlockEntity;
import net.minecraft.world.level.block.state.BlockState;

/**
 * @author KxmischesDomi | https://github.com/kxmischesdomi
 * @author BataBo | https://github.com/BataBo/EndAnchorExtended
 * @since 1.0
 */
public class EndAnchorBlockEntity extends TheEndPortalBlockEntity {
	public BlockPos getLodeStonePosition() {
		return LodeStonePosition;
	}

	public void setLodeStonePosition(int x,int y,int z ) {
		LodeStonePosition = new BlockPos(x,y,z);
	}

	public String getLodeStoneDimension() {
		return lodeStoneDimension;
	}

	public void setLodeStoneDimension(String lodeStoneDimension) {
		this.lodeStoneDimension = lodeStoneDimension;
	}

	public EndAnchorType getHasLodeStone() {
		return endAnchorType;
	}

	public void setHasLodeStone(EndAnchorType hasLodeStone) {
		endAnchorType = hasLodeStone;
	}

	private BlockPos LodeStonePosition = null;
	private EndAnchorType endAnchorType = EndAnchorType.RespawnAnchor;

	private  String lodeStoneDimension = "minecraft:overworld";

	public EndAnchorBlockEntity(BlockEntityType<?> blockEntityType, BlockPos blockPos, BlockState blockState) {
		super(blockEntityType, blockPos, blockState);
	}

	public EndAnchorBlockEntity(BlockPos pos, BlockState state) {
		this(ModBlockEntities.END_ANCHOR,pos,state);
	}

	@Override
	public void load(CompoundTag compoundTag) {
		super.load(compoundTag);
		endAnchorType = EndAnchorType.fromInt(compoundTag.getInt("EndAnchorType")) ;
		if(endAnchorType == EndAnchorType.LodeStoneTeleporter)
		{
			CompoundTag LodeStonePos = compoundTag.getCompound("LodeStonePos");
			int x = LodeStonePos.getInt("x");
			int y = LodeStonePos.getInt("y");
			int z = LodeStonePos.getInt("z");
			LodeStonePosition = new BlockPos(x,y,z);
			lodeStoneDimension = compoundTag.getString("LodeStoneDimension");
		}
		else
			LodeStonePosition = null;
	}

	@Override
	public CompoundTag getUpdateTag() {
		CompoundTag tag = super.getUpdateTag();
		saveAdditional(tag);
		return tag;
	}

	@Override
	protected void saveAdditional(CompoundTag compoundTag) {
		super.saveAdditional(compoundTag);
		compoundTag.putInt("EndAnchorType",endAnchorType.getValue());
		compoundTag.putString("LodeStoneDimension",lodeStoneDimension);
		if(endAnchorType == EndAnchorType.LodeStoneTeleporter)
		{
			CompoundTag LodeStonePos = new CompoundTag();
			LodeStonePos.putInt("x",LodeStonePosition.getX());
			LodeStonePos.putInt("y",LodeStonePosition.getY());
			LodeStonePos.putInt("z",LodeStonePosition.getZ());
			compoundTag.put("LodeStonePos",LodeStonePos);

		}
	}

	public  static Boolean isLodeStoneBlockSet(BlockPos pos, Level world, String dimension)
	{
		if(!dimension.equals(world.dimension().location().toString()))
		{
			return false;
		}
		else
		{
			return world.getBlockState(pos).is(Blocks.LODESTONE);
		}
	}

	public  static EndAnchorType getEndAnchorType(CompoundTag tag)
	{
		return EndAnchorType.fromInt(tag.getInt("EndAnchorType"));
	}

	public  static ItemStack DetermineDrop(Level world,CompoundTag tag)
	{
		if(tag == null)
			return new ItemStack(Items.COMPASS,1);
		else
		{
			EndAnchorType endAnchorType = EndAnchorType.fromInt(tag.getInt("EndAnchorType"));
			if(endAnchorType == EndAnchorType.RespawnAnchor)
				return new ItemStack(Items.COMPASS,1);
			else if(endAnchorType == EndAnchorType.RecoveryTeleporter)
				return new ItemStack(Items.RECOVERY_COMPASS,1);
			else if(endAnchorType == EndAnchorType.LodeStoneTeleporter)
			{
				if(isLodeStoneBlockSet(new BlockPos(((CompoundTag)tag.get("LodeStonePos")).getInt("x"),
								((CompoundTag)tag.get("LodeStonePos")).getInt("y"),
								((CompoundTag)tag.get("LodeStonePos")).getInt("z")),world,
						(tag.getString("LodeStoneDimension"))))
				{
					ItemStack i = new ItemStack(Items.COMPASS,1);
					CompoundTag t = i.getOrCreateTag();
					t.putBoolean("LodestoneTracked",true);
					t.putString("LodestoneDimension",tag.getString("LodeStoneDimension"));
					CompoundTag t1 = new CompoundTag();

					t1.putInt("X",((CompoundTag)tag.get("LodeStonePos")).getInt("x"));
					t1.putInt("Y",((CompoundTag)tag.get("LodeStonePos")).getInt("y"));
					t1.putInt("Z",((CompoundTag)tag.get("LodeStonePos")).getInt("z"));
					t.put("LodestonePos",t1);
					return i;
				}
				else
				{
					return new ItemStack(Items.COMPASS,1);
				}
			}
		}
		return new ItemStack(Items.COMPASS,1);
	}
}
