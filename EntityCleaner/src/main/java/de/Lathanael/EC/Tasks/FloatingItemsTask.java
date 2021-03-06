/*************************************************************************
 * Copyright (C) 2012 Philippe Leipold
 *
 * EntityCleaner is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * EntityCleaner is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with EntityCleaner. If not, see <http://www.gnu.org/licenses/>.
 *
 **************************************************************************/

package de.Lathanael.EC.Tasks;

import java.util.List;

import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Item;
import org.bukkit.inventory.ItemStack;

/**
 * @author Lathanael (aka Philippe Leipold)
 *
 */
public class FloatingItemsTask implements Runnable {

	private World world;
	private List<Material> items;

	public FloatingItemsTask(World world, List<Material> items) {
		this.world = world;
		this.items =items;
	}

	/* (non-Javadoc)
	 * @see java.lang.Runnable#run()
	 */
	@Override
	public void run() {
		List<Entity> entites;
		entites = world.getEntities();
		for (Entity e : entites) {
			if (e instanceof ItemStack) {
				ItemStack stack = (ItemStack) e;
				if (items.contains(stack.getType()))
					e.remove();
			} else if (e instanceof Item) {
				Item stack = (Item) e;
				if (items.contains(stack.getItemStack().getType()))
					e.remove();
			}
		}
	}
}
