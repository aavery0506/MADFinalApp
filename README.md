# MADFinalApp

This app was developed for CS5520 Mobile Application Development with Dr. Gary Cantrell at The Roux Institute Northeastern University.

Original Purpose:
The purpose of this project would be to develop a meditation app. This app would allow users to have a gamified meditation platform where they can set personal goals and earn achievements. I am interested in creating this app since I have not been able to find an app that has all the features I would like to have in one place. There are a lot of basic meditation app out there with simple breathing exercises, and ones that include too many features and bloat. I struggle to maintain a routine of practicing mindfulness strategies and feel as though this type of app would be beneficial to keep me on track. 


The implementation of this app has been simplified due to time constraints. 

Current status:
This app allows a user to login and select from 4 different activities. There are options for Journal, Breathe, Goals, and Affirmations. At most points in the app there is s lotus image in the top left corner that users can click to bring them back to the home screen. 

Journal:
allows users to add journal entries, show past journal entries, and search journal entries by date.
Breathe:
Users can select a breathing time or input a custom time. This then brings them to a timer that has a duration of the selected time. These exercises count towards the users set daily and weekly goals
Goals:
There are daily and weekly goals that users can set. This uses a tablayout to facilitate showing the progress for each goal and setting new goals.
Affirmations:
A random affirmation is shown and the user has the option of generating a new affirmation. These are connected with an API and has local caching for instances when the app is not connected to the internet. 
