package ui;

import model.Movie;
import model.Watchlist;
import model.Event;
import model.EventLog;

import persistence.JsonReader;
import persistence.JsonWriter;

import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.*;
import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.List;

// Watchlist application
//NOTE: some code used in this class is taken from the TellerApp ui class as mentioned in the Phase 1 description
public class WatchlistAppGUI {
    private static Watchlist watchlist = new Watchlist();
    private static final String JSON_STORE = "./data/watchlist.json";
    private JsonWriter jsonWriter;
    private JsonReader jsonReader;

    private JFrame frame;
    private JTextArea watchlistTextArea;

    public WatchlistAppGUI() throws FileNotFoundException {
        jsonWriter = new JsonWriter(JSON_STORE);
        jsonReader = new JsonReader(JSON_STORE);

        initializeFrame();
        initializeComponents();

        logger();
        // Load data on startup
        loadDataOnStartup();

    }

    //MODIFIES: this
    //EFFECTS: Initializes the GUI with a frame set with dimensions, colour, watchlist image and text heading
    private void initializeFrame() {
        frame = new JFrame("Watchlist App GUI");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        frame.setSize(1300, 500);
        centerFrameOnScreen(frame);
        frame.setLayout(new BorderLayout());

        JPanel titlePanel = new JPanel();
        titlePanel.setPreferredSize(new Dimension(1300, 425));
        TitledBorder titledBorder = BorderFactory.createTitledBorder(BorderFactory.createEmptyBorder(),
                "Watchlist App", TitledBorder.CENTER, TitledBorder.TOP, new Font("Arial", Font.BOLD, 30));
        titledBorder.setTitleColor(Color.WHITE);
        titlePanel.setBorder(titledBorder);
//        titlePanel.setSize(80, 30);
        titlePanel.setBackground(new Color(139, 69, 19));
        ImageIcon imageIcon = new ImageIcon(".\\data\\Watchlist.jpg");


        JLabel imageLabel = new JLabel(imageIcon);
        imageLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        titlePanel.add(imageLabel);
        // Brown background color

        // Add the title panel to the content pane
        frame.getContentPane().add(titlePanel, BorderLayout.NORTH);
    }

    // MODIFIES: EventLog
    // EFFECTS: logs each void event to EventLog
    private void logger() {
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            System.out.println("Closing the application");

            // Print all the events that have been logged since the application started
            for (Event e : EventLog.getInstance()) {
                System.out.println(e.toString());
            }
        }));
    }

    //MODIFIES: this
    //EFFECTS: centers the frame
    private void centerFrameOnScreen(JFrame frame) {
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int x = (screenSize.width - frame.getWidth()) / 2;
        int y = (screenSize.height - frame.getHeight()) / 2;
        frame.setLocation(x, y);
    }

    //MODIFIES: this
    //EFFECTS: Initializes the buttons and other GUI components
    @SuppressWarnings("methodlength")
    private void initializeComponents() {
        watchlistTextArea = new JTextArea();

        watchlistTextArea.setEditable(false);

        JButton addButton = new JButton("Add Movie");
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addMovieToWatchlist();
            }
        });

        JButton removeButton = new JButton("Remove Movie");
        removeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                removeMovieFromWatchlist();
            }
        });

        JButton loadButton = new JButton("Load Watchlist");
        loadButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                loadData();
            }
        });

        JButton saveButton = new JButton("Save Watchlist");
        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                saveData();
            }
        });

        JButton viewMovieDetailsButton = new JButton("View Movie Details");
        viewMovieDetailsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                viewMovieDetails();
            }
        });

        JButton displayMoviesButton = new JButton("Display Movies");
        displayMoviesButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                displayMovies(); // Implement this method to display movies
            }
        });

        JButton searchMoviesByGenreButton = new JButton("Search Movies by Genre");
        searchMoviesByGenreButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                searchMoviesByGenre(); // Implement this method to display movies
            }
        });

        JButton searchMoviesByActorButton = new JButton("Search Movies by Actor");
        searchMoviesByActorButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                searchMoviesByActor(); // Implement this method to display movies
            }
        });



        JPanel buttonPanel = new JPanel();
        buttonPanel.add(addButton);
        buttonPanel.add(removeButton);
        buttonPanel.add(displayMoviesButton);
        buttonPanel.add(viewMovieDetailsButton);
        buttonPanel.add(searchMoviesByGenreButton);
        buttonPanel.add(searchMoviesByActorButton);
        buttonPanel.add(saveButton);
        buttonPanel.add(loadButton);

//        frame.add(watchlistTextArea, BorderLayout.CENTER);
        frame.add(buttonPanel, BorderLayout.SOUTH);
    }


    //MODIFIES: this
    //EFFECTS: haha guess
    private void addMovieToWatchlist() {

        String title = JOptionPane.showInputDialog(frame, "Enter movie title:");
        String genreInput = JOptionPane.showInputDialog(frame, "Enter movie genre(s) separated by commas:");
        List<String> genres = Arrays.asList(genreInput.split(", "));
        String director = JOptionPane.showInputDialog(frame, "Enter director:");
        String leadActorsInput = JOptionPane.showInputDialog(frame, "Enter lead actor(s) separated by commas:");
        List<String> leadActors = Arrays.asList(leadActorsInput.split(", "));
        String supportingActorsInput = JOptionPane.showInputDialog(frame,
                "Enter supporting actor(s) separated by commas:");
        List<String> supportingActors = Arrays.asList(supportingActorsInput.split(", "));

        int year = Integer.parseInt(JOptionPane.showInputDialog(frame, "Enter release year:"));

        Movie movie = new Movie(title, genres, director, leadActors, supportingActors, year);

        watchlist.addMovie(movie);

        // Displays the message that movie has been added
        JOptionPane.showMessageDialog(frame, "Movie added to your watchlist.");

        // Update
        updateWatchlistTextArea();
    }

    //MODIFIES: this
    //EFFECTS: takes a movie title and removes the movie from the watchlist
    private void removeMovieFromWatchlist() {
        String title = JOptionPane.showInputDialog(frame, "Enter the title of the movie to remove:");

        if (title != null && !title.isEmpty()) {
            watchlist.removeMovie(title);
            JOptionPane.showMessageDialog(frame, "Movie removed from your watchlist.");
        }

        updateWatchlistTextArea();
    }


    //EFFECTS: not in use anymore, just for reference
    private void updateWatchlistTextArea() {
        List<String> titles = watchlist.getWatchlistTitles();
        StringBuilder sb = new StringBuilder();
        for (String title : titles) {
            sb.append(title).append("\n");
        }
        watchlistTextArea.setText(sb.toString());
    }

    //MODIFIES: this
    //EFFECTS: starts the world with existing saved data
    private void loadData() {
        try {
            watchlist = jsonReader.read();
            updateWatchlistTextArea();
            JOptionPane.showMessageDialog(frame, "Data loaded successfully!");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(frame, "Error loading data: " + e.getMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    //MODIFIES: this
    //EFFECTS: saves the current state of watchlist
    private void saveData() {
        try {
            jsonWriter.open();
            jsonWriter.write(watchlist);
            jsonWriter.close();
            JOptionPane.showMessageDialog(frame, "Data saved successfully!");
        } catch (FileNotFoundException e) {
            JOptionPane.showMessageDialog(frame, "Error saving data: " + e.getMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    //EFFECTS: Displays the details of a movie in the watchlist, if movie is not in the watchlist returns a string
    private void viewMovieDetails() {
        String title = JOptionPane.showInputDialog(frame, "Enter the title of the movie:");

        if (title != null && !title.isEmpty()) {
            String movieTitle = title.trim();

            List<Movie> matchingMovies = watchlist.getMoviesByTitle(movieTitle);

            if (matchingMovies.isEmpty()) {
                JOptionPane.showMessageDialog(frame, "No movies found with the specified title.");
            } else {
                StringBuilder message = new StringBuilder("Movie details for '" + movieTitle + "':\n");
                for (Movie movie : matchingMovies) {
                    message.append("Title: ").append(movie.getTitle()).append("\n");
                    message.append("Genre: ").append(String.join(", ", movie.getGenre())).append("\n");
                    message.append("Director: ").append(movie.getDirector()).append("\n");
                    message.append("Lead Actors: ").append(String.join(", ",
                            movie.getLeadActors())).append("\n");
                    message.append("Supporting Actors: ").append(String.join(", ",
                            movie.getSupportingActors())).append("\n");
                    message.append("Release Year: ").append(movie.getYear()).append("\n");
                    message.append("--------\n");
                }

                JOptionPane.showMessageDialog(frame, message.toString());
            }
        }
    }

    //
    // EFFECTS: displays titles of movies in the watchlist
    private void displayMovies() {
        List<String> titles = watchlist.getWatchlistTitles();

        if (titles.isEmpty()) {
            JOptionPane.showMessageDialog(frame, "No movies in the watchlist.");
        } else {
            StringBuilder message = new StringBuilder("Movies in the watchlist:\n");
            for (String title : titles) {
                message.append("- ").append(title).append("\n");
            }

            JOptionPane.showMessageDialog(frame, message.toString());
        }
    }

    //
    // EFFECTS: takes a genre and returns list of movie titles of all the movies in the watchlist of that genre
    private void searchMoviesByGenre() {
        String genreInput = JOptionPane.showInputDialog(frame, "Enter the genre:");

        if (genreInput != null && !genreInput.isEmpty()) {
            String genre = genreInput.trim().toLowerCase();

            List<String> matchingTitles = watchlist.filterByGenre(genre);

            if (matchingTitles.isEmpty()) {
                JOptionPane.showMessageDialog(frame, "No movies found with the specified genre.");
            } else {
                StringBuilder message = new StringBuilder("Movies with the genre '" + genre + "':\n");
                for (String title : matchingTitles) {
                    message.append("- ").append(title).append("\n");
                }

                JOptionPane.showMessageDialog(frame, message.toString());
            }
        }
    }

    //
    // EFFECTS: takes an actor's name and returns a list of movie titles of all the movies in the
    // watchlist starring that actor
    private void searchMoviesByActor() {
        String actorName = JOptionPane.showInputDialog(frame, "Enter the actor's name:");

        if (actorName != null && !actorName.isEmpty()) {
            String actor = actorName.trim();

            List<String> matchingTitles = watchlist.filterByActor(actor);

            if (matchingTitles.isEmpty()) {
                JOptionPane.showMessageDialog(frame, "No movies found starring the specified actor.");
            } else {
                StringBuilder message = new StringBuilder("Movies starring '" + actor + "':\n");
                for (String title : matchingTitles) {
                    message.append("- ").append(title).append("\n");
                }

                JOptionPane.showMessageDialog(frame, message.toString());
            }
        }
    }

//MODIFIES: this
//EFFECTS: prompts the user if they want to load past data when the application starts
    private void loadDataOnStartup() {
        int choice = JOptionPane.showConfirmDialog(frame, "Do you want to load data on startup?",
                "Load Data", JOptionPane.YES_NO_OPTION);
        if (choice == JOptionPane.YES_OPTION) {
            loadData();
        }
    }

    public void display() {
        frame.setVisible(true);
    }
}

