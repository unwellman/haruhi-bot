# HaruhiBot
A simplistic Minecraft Spigot to Discord API binding

# Feature Road Map
## Configuration
- [x] Provide Discord app token and user
- [x] Select which channels receive status updates
- [x] Configurable status message formats

## Status updates
- [x] Notify Discord channels when server is online or offline and on player join/quit
- [x] Forward chat messages from players

## Discord Commands
- [ ] `/msg`, `/whisper`:
    - [ ] PM/whisper to a user on the Minecraft server
    - [ ] Tab-complete name from list of online users
- [ ] `/console`:
    - [ ] Pass any console command to the server and retrieve its output

## Minecraft Commands: wrapped under `/haruhi` to avoid namespace collisions
- [ ] `[channel] <guild#channel>`:
    - [ ] Provide a delimiter to specify guild and channel
    - [ ] Warn if channel name is ambiguous
- [ ] `[reply]`< `[r]`:
    - [ ] Cache last PM received per-player
    - [ ] Reply to same channel cached
- [ ] `[user] <Discord username>`:
    - [ ] Tab-complete Discord tag from guilds Haruhi is in


