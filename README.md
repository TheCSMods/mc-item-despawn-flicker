<b>Note:</b> You have to have this mod installed on the server as well in order for it to work. For single-player, you don't have to do anything. More info as to why can be found below.<br/>
<br/>
Also, if you have any photosensitivity issues, maybe it is best for you not to look at flickering items.

## Mod description
Did you ever try to pick up an item only for it to despawn right in front of you as you're about to pick it up? Wouldn't it be much better if you could know when an item is about to despawn? This mod helps you out with this issue by making items flicker before they despawn, similar to how some other games do it.

<details>
    <summary>Here is a GIF of what the item flickering looks like</summary>
<img src="https://user-images.githubusercontent.com/66475965/193444677-7f445136-b64d-4c4c-9809-04379d82650f.gif"/><br/>
(the gif framerate is 10fps so the flickering may look a bit off)
</details>

## Dependencies
- [Fabric API](https://modrinth.com/mod/fabric-api) - <b>Required</b> - The Fabric API is used by this mod for networking and event handling reasons.
- [Mod Menu](https://modrinth.com/mod/modmenu) - <b>Optional</b> - You may use this mod to access the configuration screen for this mod.
- [Minecraft server](https://fabricmc.net/use/server/) - <b>Required</b> - Yes, you do need to have this mod installed on the Minecraft server you choose to play on with this mod, unless you're playing in single-player.
 
## Some more info on how this mod works and answers to some questions
#### How long will it take for an item to start flickering?
- The items will start flickering twelve (12) seconds before despawning. The closer they get to despawning, the more frequently they will flicker.

#### Can I disable this mod while having it installed?
- Yes. You may do so using the configuration screen. Make sure you have the "Mod Menu" mod installed.

#### Does this mod work with other mods that change the way items look and the way they are rendered?
- Yes. It should.

#### Why do I need to have this mod installed on the server as well?
- Item entities have a property called "item age" that increases by one (1) for each tick that goes by. When this "item age" for a given item reaches 6000 (5 minutes), the item despawns. The client-side mod reads this property so it can know when to flicker a given item. There is one small issue however. The "item age" is a server-side property. In other words, only the server knows what the age of an item is. However, when this mod is installed on a server, it will then tell the server to let the clients (players) know about the ages of items around them, thus allowing clients (players) to know when exactly to flicker the items on their screens. So if you join a server that doesn't have this mod installed, you will likely see items despawn without flickering. Unless you drop some items and wait for them to despawn while standing right next to them, then the client will know the exact ages of those items.

#### I saw an item despawn without flickering, what happened?
- Three (3) possible reasons. One being the server not having the mod installed, another one being the mod being disabled on the server (check the mod's config), and yet another one being the mod being disabled on your side (check the mod config).

##
<p align=center>
  <a href="https://www.curseforge.com/minecraft/mc-mods/item-despawn-flicker"><img alt="CurseForge" src="https://cf.way2muchnoise.eu/683160.svg"/></a>
  <a href="https://modrinth.com/mod/item-despawn-flicker"><img alt="Modrinth" src="https://img.shields.io/modrinth/dt/7Dbj5Y8g?label=Modrinth"></a>
</p>
