# Sticky-Bot

## How to use
Right click a message then go to "Apps" to access commands

![Commands](https://user-images.githubusercontent.com/73871477/171168857-093375c3-74bf-4eb8-ac39-142ddb5e490e.png)

### Message Application Commands:-
1. Stick Message -> Sticks the message to the channel with all the embeds.
2. Unstick Message -> Unsticks a stickied message.

## Demo:-
https://user-images.githubusercontent.com/73871477/171182015-7237ba37-2f53-47fd-8dd4-e401a2bb7164.mp4

## Self Hosting
1. Download `Docker` & `PostgreSQL`.
2. Host a `PostgreSQL`.
4. Clone this repo locally.
5. Go to [Discord Developer Portal](https://discord.com/developers/applications). And create a new application ![image](https://user-images.githubusercontent.com/73871477/179382626-42d033e4-cfa3-483e-90cd-e4d6c3290616.png)
6. Now go to the bot section and add a Bot ![image](https://user-images.githubusercontent.com/73871477/179382685-beeb02b0-a068-4f2c-b7a5-b53e8b3dc0d1.png)
7. Run `docker build -t sticky-bot .`.
8. Now run the image u just build with the command
  ```
  docker run -e TOKEN="bot token here" -e PGDATABASE="postgre db here" -e PGHOST="postgre host here" -e PGPASSWORD="postgre password here" -e PGPORT="postgre port here" -e PGUSER="postgre user here" sticky-bot
  ```
Now the bot should be up.

Use these scopes and permissons to invite the bot
![image](https://user-images.githubusercontent.com/73871477/179388869-f67e1e75-e59a-4209-83f6-b628c5483bbc.png)
