README

This program implements a Smart Home Monitoring Dashboard, which monitors
    environmental conditions in different types of rooms (dormitory vs. laboratory).

# How to compile and run the program

Run "{shell} run.sh" in your terminal. {shell} is your shell, e.g. bash, zsh, etc.

The output will be displayed in the terminal.

# How to switch between dormitory and laboratory configurations

The configuration type is determined by roomType in SmartHomeApp.java.

To change the configuration, you may change the value of roomType:

"dormitory" for dormitory configuration
"laboratory" for laboratory configuration

Save and rerun the program to see the different behaviors in different configurations.

# How Observer and Factory patterns interact

The factory pattern is responsible for creating and configuring specific observer instances
    (such as AlarmPanel and RealtimePanel),
    and registering them to the MonitoringHub during the construction process.