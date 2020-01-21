# WatBin

An app to help users sort their waste based on guidelines provided by the University of Waterloo. The user can employ a search through a list or use image recognition to detect and sort their waste.

Landing on the home page, the user is presented with a UI modeled after the University of Waterloo and a simple bottom navigation with three options

![Image of Homepage](https://github.com/xinyizou/WatBin/blob/master/images/Screenshot_20190603-141756_WatBin.jpg)

With the search option, the user can look through an expandable recyclerview to learn details about disposal. The header loads with the appropriate colour and further details are presented in the dropdown when the header is clicked.

![Image of RecyclerView](https://github.com/xinyizou/WatBin/blob/master/images/Screenshot_20190603-151546_WatBin.jpg)

The user also has the option of using the search option to narrow down the list. This refreshes the recyclerview after every button tap and returns a filtered list

![Image of RecyclerView search](https://github.com/xinyizou/WatBin/blob/master/images/Screenshot_20190603-141812_WatBin.jpg)

With the image recognition option, the user can upload a photo of the item they wish to dispose. The photo is taken with the Android native camera activity. 

![Image of ImageRecognition](https://github.com/xinyizou/WatBin/blob/master/images/Screenshot_20190603-141831_WatBin.jpg)

This photo is sent to a custom IBM Visual Recognition model.

![Image of SendingPhoto](https://github.com/xinyizou/WatBin/blob/master/images/Screenshot_20190603-141900_WatBin.jpg)

The model will return the predicted waste class and the confidence score.

![Image of Received data](https://github.com/xinyizou/WatBin/blob/master/images/pen_screen_grab.png)
![Image of UWaterloo sorting instructions](https://github.com/xinyizou/WatBin/blob/master/images/pen.PNG)

