# GreloadToDNSFix

If you have dynamic IP and you want to setup server in bungeecord sever,
you should use service like [ddns.net](ddns.net).
Unfortunately, bungee resolves DNS only once on load (or reload),
so this plugin checks every 5 minutes if ping to server still working, if not,
it executes greload command. 



## TODO:
(I will not do that, but if you wish, you can.)

- use config file
  - check period
  - log level
- reload self configuration

