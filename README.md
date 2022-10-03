# My Personal Project: A desktop alarm application

## What does it do?

This desktop alarm application has the basic functions 
of alarm applications on a phone:
- setting alarms for specific times 
- have reminder texts for the alarm
- repeat the alarm for specific days
- play sound when the alarm goes off

## Who will use it?
This alarm application can be used by anyone, people who like
to set alarms on their phone can also use it on their laptops if 
their phone is not around. Anyone who works on their computer a 
lot will find this application very useful.

## Why is this project created?
I have always found it to be hard if I want to set alarms
on my laptop, although there are ways of doing it, they all seem more 
complicated than how you would normally set the alarm on your phone.
Which is why I am creating this project, so it is as easy to set 
alarms on laptop as it is on phones.

## User Stories
- As a user, I want to be able to add an alarm for a specific time and into an alarm list
- As a user, I want to be able to delete alarm from a list of alarms
- As a user, I want to be able to set and change the information for repeating the alarm on specific days
- As a user, I want to be able to set and change the information for reminder text to each alarms
- As a user, I want to be able to see the current time and day of the week
- As a user, I want to be able to save my alarm list and the according information of the alarms on file
- As a user, I want to be able to load my alarm list and the according information of the alarms from file

## Phase 4: Task 2
Tue Mar 29 19:46:12 PDT 2022
<br /> Reminder text for alarm 12:00 is set to : Lunch!

Tue Mar 29 19:46:19 PDT 2022
<br /> Repeating day: Mon is added to 12:00

Tue Mar 29 19:46:32 PDT 2022
<br /> Alarm 00:00 added.

Tue Mar 29 19:46:38 PDT 2022
<br /> Reminder text for alarm 00:00 is set to : Sleep!!!

Tue Mar 29 19:46:44 PDT 2022
<br /> Repeating day: Wed is added to 00:00

Tue Mar 29 19:46:51 PDT 2022
<br /> Repeating day: Thu is added to 00:00

Tue Mar 29 19:47:08 PDT 2022
<br /> Alarm 07:00 added.

Tue Mar 29 19:47:18 PDT 2022
<br /> Reminder text for alarm 07:00 is set to : Wake up!

Tue Mar 29 19:47:24 PDT 2022
<br /> Alarm 07:00 removed.

Tue Mar 29 19:47:33 PDT 2022
<br /> Alarm 09:00 added.

Tue Mar 29 19:47:46 PDT 2022
<br /> Reminder text for alarm 09:00 is set to : Wake up!!

Tue Mar 29 19:47:53 PDT 2022
<br /> Repeating day: Wed is added to 09:00

Tue Mar 29 19:47:57 PDT 2022
<br /> Repeating day: Fri is added to 09:00


## Phase 4: Task 3
By looking at the UML design diagram, based on what I have learned, there are two changes that would improve to the design.
And the reason why I don't think there can be other design improvements made is because all the classes are responsible for one thing
which makes it have good cohesion, and there is also no coupling, and there are no other obvious design patterns that can be applied to this design. 

Changes I would make if more time is allowed:
- Use the singleton design pattern for AlarmList since only one instance of the list is needed, and it can be used globally
- Split the AlarmListGUI class because the AlarmListGUI has a lot of methods in it that could be taken out into a new class and make the old class have a field of the new class instead