package model;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;

public class AlarmClock {
    public void alarmClock (){

        String q;

        try {
            Scanner sc = new Scanner(System.in);
            System.out.println("Enter the time that you want to get up in HH:mm format");
            String s = sc.nextLine();
            System.out.println("Search up a song to be the alarm");
            q = sc.nextLine();
            SpotifyCall.parse(SpotifyCall.trackSearch(q));
            System.out.println("Enter the number to choose the song");
            int index = Integer.parseInt(sc.nextLine());
            String trackURI = SpotifyCall.getTrackURI(index, SpotifyCall.trackSearch(q));


            System.out.println("Your alarm is now set for " + s + " !!");
            while (true) {    //An always true condition.
                String currentTime = new SimpleDateFormat("HH:mm").format(new Date());    //Fetching the current system time.
                boolean x = currentTime.equals(s);    //Equating the correct time to the time entered by the user.
                if (x) {
                    System.out.println("It's time!!! Enter STOP to stop the alarm.");
                    SpotifyCall.playTrack(trackURI);
                    String stop = sc.next();
                    if (stop.equals("STOP"))
                        SpotifyCall.stopPlaying();
                    break;    //Using break to jump out of the loop as soon as the alarm rings.
                } else
                    continue;  //To keep the program running until the desired time is reached.
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
