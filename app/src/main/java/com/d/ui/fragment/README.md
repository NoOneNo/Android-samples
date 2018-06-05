2018-05-28

## Fragment

https://stackoverflow.com/questions/28929637/difference-and-uses-of-oncreate-oncreateview-and-onactivitycreated-in-fra
onCreate
+ called after the Activity's onAttachFragment()
+ before that Fragment's onCreateView()
+ you can assign variables, get Intent extras
+ this method can be called when the Activity's onCreate() is not finished, and so trying to access the View hierarchy here may result in a crash.

