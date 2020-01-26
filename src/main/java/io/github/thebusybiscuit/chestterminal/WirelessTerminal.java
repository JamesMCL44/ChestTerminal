package io.github.thebusybiscuit.chestterminal;

import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import me.mrCookieSlime.Slimefun.Lists.RecipeType;
import me.mrCookieSlime.Slimefun.Objects.Category;
import me.mrCookieSlime.Slimefun.Objects.SlimefunItem.SimpleSlimefunItem;
import me.mrCookieSlime.Slimefun.Objects.handlers.ItemInteractionHandler;
import me.mrCookieSlime.Slimefun.api.BlockStorage;
import me.mrCookieSlime.Slimefun.api.SlimefunItemStack;
import me.mrCookieSlime.Slimefun.api.energy.ItemEnergy;

public abstract class WirelessTerminal extends SimpleSlimefunItem<ItemInteractionHandler> {

	public WirelessTerminal(Category category, SlimefunItemStack item, RecipeType recipeType, ItemStack[] recipe) {
		super(category, item, recipeType, recipe);
	}
	
	public abstract int getRange();

	@Override
	public ItemInteractionHandler getItemHandler() {
		return (e, p, stack) -> {
			if (isItem(stack)) {
				ItemMeta im = stack.getItemMeta();
				List<String> lore = im.getLore();
				if (lore.isEmpty()) return true;
				
				if (e.getClickedBlock() != null) {
					if (BlockStorage.check(e.getClickedBlock(), "CHEST_TERMINAL")) {
						lore.set(0, ChatColor.translateAlternateColorCodes('&', "&8\u21E8 &7\u5df2\u9023\u63a5: &8") + e.getClickedBlock().getWorld().getName() + " X: " + e.getClickedBlock().getX() + " Y: " + e.getClickedBlock().getY() + " Z: " + e.getClickedBlock().getZ());
						/* "&8\u21E8 &7已連接: &8" */
						p.sendMessage(ChatColor.translateAlternateColorCodes('&', "&b\u6210\u529f\u9023\u63a5\u7cfb\u7d71!")); /* "&b成功連接系統!" */
						im.setLore(lore);
						stack.setItemMeta(im);
						p.getInventory().setItemInMainHand(stack);
					}
					else {
						openRemoteTerminal(p, stack, lore.get(0), getRange());
					}
					
					e.setCancelled(true);
				}
				else {
					openRemoteTerminal(p, stack, lore.get(0), getRange());
				}
				
				return true;
			}
			else return false;
		};
	}
	
	private void openRemoteTerminal(Player p, ItemStack stack, String loc, int range) {
		if (loc.equals(ChatColor.translateAlternateColorCodes('&', "&8\u21E8 &7\u5df2\u9023\u63a5: &c--"))) { /* "&8\u21E8 &7已連接: &c--" */
			p.sendMessage(ChatColor.translateAlternateColorCodes('&', "&4\u9023\u63a5\u5931\u6557 &c- \u6b64\u88dd\u7f6e\u5c1a\u672a\u9023\u63a5\u81f3\u4e3b\u7d42\u7aef\u6a5f!"));
			/* "&4連接失敗 &c- 此裝置尚未連接至主終端機!" */
			return;
		}
		
		loc = loc.replace(ChatColor.translateAlternateColorCodes('&', "&8\u21E8 &7\u5df2\u9023\u63a5: &8"), ""); /* "&8\u21E8 &7已連接: &8" */
		World world = Bukkit.getWorld(loc.split(" X: ")[0]);
		if (world == null) {
			p.sendMessage(ChatColor.translateAlternateColorCodes('&', "&4\u9023\u63a5\u5931\u6557 &c- \u539f\u5df2\u9023\u63a5\u7684\u4e3b\u7d42\u7aef\u6a5f\u5df2\u4e0d\u5b58\u5728!"));
			/* "&4連接失敗 &c- 原已連接的主終端機已不存在!" */
			return;
		}
		int x = Integer.parseInt(loc.split(" X: ")[1].split(" Y: ")[0]);
		int y = Integer.parseInt(loc.split(" Y: ")[1].split(" Z: ")[0]);
		int z = Integer.parseInt(loc.split(" Z: ")[1]);
		
		Block block = world.getBlockAt(x, y, z);
		
		if (!BlockStorage.check(block, "CHEST_TERMINAL")) {
			p.sendMessage(ChatColor.translateAlternateColorCodes('&', "&4\u9023\u63a5\u5931\u6557 &c- \u539f\u5df2\u9023\u63a5\u7684\u4e3b\u7d42\u7aef\u6a5f\u5df2\u4e0d\u5b58\u5728!"));
			/* "&4連接失敗 &c- 原已連接的主終端機已不存在!" */
			return;
		}
		
		float charge = ItemEnergy.getStoredEnergy(stack);
		if (charge < 0.5F) {
			p.sendMessage(ChatColor.translateAlternateColorCodes('&', "&4\u9023\u63a5\u5931\u6557 &c- \u96fb\u529b\u4e0d\u8db3!")); /* "&4連接失敗 &c- 電力不足!" */
			return;
		}

		if (range > 0 && !world.getUID().equals(p.getWorld().getUID())) {
			p.sendMessage(ChatColor.translateAlternateColorCodes('&', "&4\u9023\u63a5\u5931\u6557 &c- \u5df2\u96e2\u958b\u53ef\u63a5\u6536\u7bc4\u570d!")); /* "&4連接失敗 &c- 已離開可接收範圍!" */
			return;
		}
		if (range > 0 && block.getLocation().distance(p.getLocation()) > range) {
			p.sendMessage(ChatColor.translateAlternateColorCodes('&', "&4\u9023\u63a5\u5931\u6557 &c- \u5df2\u96e2\u958b\u53ef\u63a5\u6536\u7bc4\u570d!")); /* "&4連接失敗 &c- 已離開可接收範圍!" */
			return;
		}

		p.getInventory().setItemInMainHand(ItemEnergy.chargeItem(stack, -0.5F));
		BlockStorage.getInventory(block).open(p);
	}

}
