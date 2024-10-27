import java.awt.Desktop;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.LinkedList;
import java.util.Random;
import java.util.Scanner;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
/**
 *
 * @author abbygamboa
 */
public class musicRec {

    public static int NUMQ = 15; // number of questions
    public static int TOTA = 0; //total users
    public static int INTXT = 18745; // how many artists are in text
    public static int zeroCount; // counts the amount of times our user weighs an artist at 0
    public static String url = "";// used to open link if user wishes. 
    public static LinkedList<Integer>[] AllArtists; // collection of all artists from user_Arts.txt file
    public static LinkedList<Integer>[] AllWeights; // collection of all weights from user_Artiss.txt
    public static LinkedList<Integer> userlikes; // the current user's "liked" artists
    public static LinkedList<Integer> userWeights; // the current user's weights of the artist they like
    public static graphics g = new graphics(); // graphics window

    public static void questions(String ready) throws InterruptedException {
        //initializes per each user 
        zeroCount = 0;
        LinkedList<Integer> removeCases = new LinkedList<>();
        userlikes = new LinkedList<>();
        userWeights = new LinkedList<>();

        if (ready.equals("yes")) {
            //prompts ther user with NUMQ artists
            for (int i = 0; i < NUMQ; i++) {
                prompt(removeCases);
            }
            //checks if more questions need to be asked to ensure an accurate outcome
            addMoreQ(removeCases);
        } else {
            g.OnExit("<html> Thank you for using <br>the Music Recommendation System!<html>");
            Thread.sleep(2000);
            System.exit(0);
        }
    }

    // chooses a random number to call, each number will correlate to a artist on the artist.txt file
    public static int randomNum() {
        Random rando = new Random();
        int rand = rando.nextInt(1, INTXT);
        return rand;
    }

    /* if the current user does not like or listen to most of the artists prompted, 
    they must be prompted with more questions, until the zeroCount is less than the amount of 
    artists they like. */
    public static void addMoreQ(LinkedList<Integer> removeCases) throws InterruptedException {
        while (zeroCount > userlikes.size()) {
            prompt(removeCases);
        }
    }

    public static void traverseLists() throws InterruptedException {
        //which past users we should consider
        LinkedList<LinkedList<Integer>> considerArtists = new LinkedList<>();
        LinkedList<LinkedList<Integer>> considerWeight = new LinkedList<>();
        //picking those of similar ratings
        
        for (int i = 2; i < AllArtists.length; i++) {
            for (int j = 0; j < userlikes.size(); j++) {
                /*if there is a past user that also likes an artist the current 
                user likes count, their likes and weights are added 
                considered linkedlist */
                if (AllArtists[i].contains(userlikes.get(j))
                        && !considerArtists.contains(AllArtists[i])) {
                    considerArtists.add(AllArtists[i]);
                    considerWeight.add(AllWeights[i]);
                }
            }
        }
        File user = new File("/Users/abbygamboa/NetBeansProjects/MusicRecommender/artists.txt");
        
        //recommendation will come from the smaller list of common users. 
        recommend(considerWeight, considerArtists, user);
    }

    public static void recommend(LinkedList<LinkedList<Integer>> conWeight,
            LinkedList<LinkedList<Integer>> conArt,
            File file) throws InterruptedException {
        /*lists keep track of artists, number of instances of a given artist, 
        weights given by the past users to a given artist and the overall average
        weight a given artist receives. */
        LinkedList<Integer> vals = new LinkedList<>();
        LinkedList<Integer> counts = new LinkedList<>();
        LinkedList<Integer> weights = new LinkedList<>();
        LinkedList<Integer> averages = new LinkedList<>();

        /*traverse throught the considered artists list and for each instance 
        of an artist, count how many times it is seen throughout the entire list*/
        for (int i = 0; i < conArt.size(); i++) {
            LinkedList<Integer> test = conArt.get(i);
            for (int j = 0; j < test.size(); j++) {
                int count = 1;
                int val = test.get(j);
                //get the first weight of the artist
                int weight = conWeight.get(i).get(j);
                /*as long as it is the first instance seeing the artist and it 
                is not a common artists with the current user's interests, 
                add it to the vals list*/
                if (!vals.contains(val) && !userlikes.contains(val)) {
                    for (int k = 1; k < conArt.size(); k++) {
                        if (conArt.get(k).contains(val)) {
                            count++;
                            int index = conArt.get(k).indexOf(val);
                            //add all the weights of the given artist to overall weight
                            weight += conWeight.get(k).get(index);
                        }
                    }
                }

                /*as long as more than one person listens to an artist 
                add the findings to the lists*/
                if (count > 1) {
                    vals.add(val);
                    counts.add(count);
                    weights.add(weight);
                }
            }
        }
        
        //find the average weight of every artist.
        for (int j = 0; j < vals.size(); j++) {
            int average = weights.get(j) / counts.get(j);
            averages.add(average);
        }
        
        int max = 0;
        for (int i = 0; i < averages.size(); i++) {
            //which evere artist has the maximum weight will be the output artist
            if (averages.get(i) > max) {
                max = averages.get(i);
            }
        }
        
        /*at this point average and vals should have the same size, 
        so rely on the position of the maximum average to find the 
        artist to output*/
        int index = averages.indexOf(max);

        findSong(file, vals.get(index));

    }

    /* Original idea no longer used, would find the maximum song weighted and 
    out put the value, problem with this is it only takes into account one past
    user's preference. 
    public static void maxWeigh(LinkedList<LinkedList<Integer>> conWeight,
            LinkedList<LinkedList<Integer>> conArt,
            File file) throws InterruptedException {
        int maxWeight = 0;
        int recArtist = 0;
        for (int i = 0; i < conWeight.size(); i++) {
            for (int j = 0; j < conWeight.get(i).size(); j++) {
                if (maxWeight < conWeight.get(i).get(j)) {
                    maxWeight = conWeight.get(i).get(j);
                    recArtist = conArt.get(i).get(j);
                }
            }
        }
        String songMax = "";

        try {
            String line = "";
            try (Scanner read = new Scanner(file)) {
                read.nextLine();
                while (read.hasNextLine()) {
                    line = read.nextLine();
                    String[] vals = line.split("\t");
                    String artName = vals[1];

                    if (Integer.toString(recArtist).equals(vals[0])) {
                        songMax = artName;
                        url = vals[2];
                    }
                }
                read.close();
            }

        } catch (FileNotFoundException e) {

        }
        g.printArtist("You should listen to: " + songMax, " Want to view the artist? ");
        while (g.getAnswer().equals("")) {
            Thread.sleep(1);
        }
        String ans = g.getAnswer();
        openSource(ans);
    }*/

    /* based on the artist.txt file, if the recommended song number is on the given 
    line of the file, the url will equal the link for said artist. */
    public static void findSong(File file, int recArtist) throws InterruptedException {
        String songMax = "";
        try {
            String line = "";
            try (Scanner read = new Scanner(file)) {
                read.nextLine();
                while (read.hasNextLine()) {
                    line = read.nextLine();
                    String[] vals = line.split("\t");
                    String artName = vals[1];
                    
                    /*if the number at the beginning of the line is equal to 
                    the recommended artist, the url will equal the last.fm 
                    website correlated to said artist*/
                    if (Integer.toString(recArtist).equals(vals[0])) {
                        songMax = artName;
                        url = vals[2];
                    }
                }
                read.close();
            }

        } catch (FileNotFoundException e) {

        }
        g.printArtist("You should listen to: " + songMax, " "
                + "Want to view the artist? ");
        //waits till answer is either yes or no
        while (g.getAnswer().equals("")) {
            Thread.sleep(1);
        }
        String ans = g.getAnswer();
        //if answer is yes opens link if no does not. 
        openSource(ans);
    }

    public static void prompt(LinkedList<Integer> removeCases) throws InterruptedException {
        File user = new File("/Users/abbygamboa/NetBeansProjects/MusicRecommender/artists.txt");
        try {
            //picks a random number from 1- max artist id number
            int artId = randomNum();
            //while picked number was already picked before, pick a different number
            while (removeCases.contains(artId)) {
                artId = randomNum();
            }
            String line = "";
            try (Scanner readUA = new Scanner(user)) {
                readUA.nextLine();
                while (readUA.hasNextLine()) {
                    line = readUA.nextLine();
                    String[] vals = line.split("\t");
                    int artistVal = Integer.parseInt(vals[0]);
                    
                    //put artId in the removed list, so it is not called again. 
                    removeCases.add(artId);
                    //if the number on the file line is equal to that of the picked random number
                    if (artistVal == artId) {
                        while (true) {
                            // prompt diplay using the name of the artist
                            int answer = display(vals[1]);
                            
                            /*while the anser is in between 1 and 10 add 
                            the artist to the userlikes and the output weight to 
                            usersWeights. */
                            if (answer >= 1 && answer <= 10) {
                                userlikes.add(artId);
                                /*muliplying the weight by 100 as most people are
                                used to ranking items of anything between 1-10.
                                But most of the past users have weights well 
                                into the 100s and 1000s*/
                                userWeights.add(answer * 100);
                                return;
                            }
                            //if the user doesnt know the artist or like the artist
                            if (answer == 0) {
                                //increase 0 count
                                zeroCount++;
                                return;
                            }

                        }
                    }
                }
            }

        } catch (FileNotFoundException e) {

        }
    }

    // Asks the user if they enjoy an artist and waits for an answer. 
    public static int display(String val) throws InterruptedException {
        g.printDisplay("<html> " + g.getCount() + ".) On a scale of 0-10 <br>how much do you like "
                + val + "? <html>");
        //while the correct button is not clicked, sleep
        while (g.getIfCorrect() == false) {
            Thread.sleep(1);
        }
        int answer = g.getValue();
        return answer;
    }

    //
    public static void read(File artists) {
        try {
            String line = "";
            try (Scanner readA = new Scanner(artists)) {
                readA.nextLine();
                while (readA.hasNextLine()) {
                    line = readA.nextLine();
                    //splits the line as each detail needed is sepereated by tabs
                    String[] vals = line.split("\t");
                    
                    int userId = Integer.parseInt(vals[0]);
                    int artistId = Integer.parseInt(vals[1]);
                    int weight = Integer.parseInt(vals[2]);

                    TOTA = userId;
                    
                    /*these two should have the same lengths there will be weight
                    given to each artist in a user's interest, */
                    //adds in the given user's artist 
                    AllArtists[userId].add(artistId);
                    //adds in the given user's weights 
                    AllWeights[userId].add(weight);

                }
            }

        } catch (FileNotFoundException e) {
            System.out.println("File not found");
        }
    }

    public static void addUser(File add) {
        try {
            try (FileWriter append = new FileWriter(add, true)) {
                //increases last users ID to create a new one
                TOTA++;
                //writes the user's likes onto the user_Art.txt file 
                for (int i = 0; i < userlikes.size(); i++) {
                    append.write("\n");
                    append.write(TOTA + "\t" + userlikes.get(i) + "\t" + userWeights.get(i));
                }
            }
        } catch (IOException e) {
        }
    }

    public static void openSource(String open) {
        while (true) {
            if (open.toLowerCase().equals("yes")) {
                if (Desktop.isDesktopSupported()) {
                    Desktop dt = Desktop.getDesktop();
                    try {
                        URI uri = new URI(url);
                        dt.browse(uri);
                    } catch (IOException e) {

                    } catch (URISyntaxException e) {
                        System.out.println("syntax error");
                    }
                }
                return;
            }
            if (open.toLowerCase().equals("no")) {
                return;
            }
            System.out.println("Sorry I did not get that, try again: ");
        }
    }

    public static void main(String args[]) throws InterruptedException {
        AllArtists = new LinkedList[10000];
        AllWeights = new LinkedList[10000];
        //Initalize All
        for (int i = 0; i < AllArtists.length; i++) {
            AllArtists[i] = new LinkedList<>();
            AllWeights[i] = new LinkedList<>();
        }
        
        //create title screen
        g.dra();
        //ensures AllArtists and AllWeights have data to look through 
        File artist = new File("/Users/abbygamboa/NetBeansProjects/MusicRecommender/user_arts1.txt");
        read(artist);
        //until user is ready, wait for a response
        while (g.getAnswer() == null) {
            Thread.sleep(1);
        }
        String answer = g.getAnswer();
        // will run until the user does not want to go again
        while (true) {
            
            questions(answer);
            traverseLists();
            //adds to the userArt.txt file to help future users receive a song
            addUser(artist);
            
            /*appears once all questions are answered, however, 
            if they do want to go again it will create another userID*/
            g.printNext("Want to go again? ");
            while (g.getAnswer().equals("")) {
                Thread.sleep(1);

            }
            answer = g.getAnswer();
        }
    }
}
