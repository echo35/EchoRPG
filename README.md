# EchoRPG
Magic Themed RPG Plugin for Spigot (not in active development)

This plugin is moreorless a sandbox to me. When I think of a new way something could be programmed into the game, or a new way an ability could be used, I'll make it 
here.

####Interesting features
	Elytra-Gliding: enhanced flying machanics
	AoE Magic Gems: magic gems which have applied effects when dropped on the ground
	*Chat-based Casting: cast magic by chatting while holding a special book
	Totems: same effects as Magic Gems, but are fixed structures made of blocks.
	**Transmutation Circles: draw floor patterns [1] out of redstone, can be used to reassemble matter [2]

* = Inconvenient mechanic, subject to change
** = The detection system for Transmutation Circle is clunky, and deserves a rewrite

[1] see [GeomancyMethods.java#L176](https://github.com/echo35/EchoRPG/blob/master/src/ch/echo35/spigot/echoRPG/utils/GeomancyMethods.java#L176), 
[L193](https://github.com/echo35/EchoRPG/blob/master/src/ch/echo35/spigot/echoRPG/utils/GeomancyMethods.java#L193), and 
[L210](https://github.com/echo35/EchoRPG/blob/master/src/ch/echo35/spigot/echoRPG/utils/GeomancyMethods.java#L210) for 
current circle recipes. Storage Circles are not yet completed.

[2] see [GeomancyMethods.java#L47](https://github.com/echo35/EchoRPG/blob/master/src/ch/echo35/spigot/echoRPG/utils/GeomancyMethods.java#L47) for list of "recipes"
- Can create items from items
- Can create custom items (use metadata)
- Can transmute LivingEntities (with or without souls)
	- A soul-less entity will be mute, unable to move, or react to other entities or the environment

####TODO
- [ ] Come up with a simpler spell-casting method
- [ ] Redo transmutation circle detection
- [ ] Create multiverse world for Storage circles (space-time magic concept)
