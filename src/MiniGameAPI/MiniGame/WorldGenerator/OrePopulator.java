package MiniGameAPI.MiniGame.WorldGenerator;

import java.util.Random;

import org.bukkit.Chunk;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.generator.BlockPopulator;

import PluginUtils.Utils.Range;

public class OrePopulator extends BlockPopulator 
{
	protected OreInfo[] _oreInfos;
	
	public OrePopulator(OreInfo... oreInfos)
	{
		_oreInfos = oreInfos;
	}
	
	@Override
	public void populate(World world, Random random, Chunk source) 
	{
		int veinX = 0;
		int veinY = 0;
		int veinZ = 0;
		int oreX = 0;
		int oreY = 0;
		int oreZ = 0;
		Range heights = null;
		Block oreBlock = null;
		for(OreInfo oreInfo : _oreInfos)
		{
			heights = new Range(oreInfo.getMinHeight(), oreInfo.getMaxHeight());
			for(int i=0; i<oreInfo.getVeinsPerChunk(); ++i)
			{
				veinX = random.nextInt(16);
				veinY = heights.getRandomIntValue(random);
				veinZ = random.nextInt(16);
				for(int j=0; j<oreInfo.getAmountPerVein(); ++j)
				{
					oreX = veinX + random.nextInt(j>>1) - j>>2;
					// Clamping X to 15 to stay in the chunk
					if(oreX > 15)
					{
						oreX = 15;
					}
					oreY = veinY + random.nextInt(j>>2) - j>>4;
					oreZ = veinZ + random.nextInt(j>>1) - j>>2;
					// Clamping Z to 15 to stay in the chunk
					if(oreZ > 15)
					{
						oreZ = 15;
					}
					oreBlock = source.getBlock(oreX, oreY, oreZ);
					if(oreBlock.getType() == Material.STONE)
					{
						oreBlock.setType(oreInfo.getType(), false);
					}
				}
			}
		}
	}
}