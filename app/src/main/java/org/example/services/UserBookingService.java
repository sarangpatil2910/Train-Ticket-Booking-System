package org.example.services;

//package ticket.booking.service;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.*;
import org.example.entities.Ticket;
import org.example.entities.Train;
import org.example.entities.User;
import org.example.util.UserServiceUtil;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class UserBookingService{

    private ObjectMapper objectMapper = new ObjectMapper();

    private List<User> userList;

    private User user;

    private final String USER_FILE_PATH = "app/src/main/java/org/example/localDb/users.json";

    public UserBookingService(User user) throws IOException {
        this.user = user;
        loadUserListFromFile();
    }

    public UserBookingService() throws IOException {
        loadUserListFromFile();
    }

    private void loadUserListFromFile() throws IOException {
        File userFile = new File(USER_FILE_PATH);

        if (!userFile.exists()) {
            System.err.println("User file not found at: " + userFile.getAbsolutePath());
            userList = new ArrayList<>();
            // Create the directory if it doesn't exist
            userFile.getParentFile().mkdirs();
            saveUserListToFile();
        } else {
            System.out.println("Loading users from: " + userFile.getAbsolutePath());
            try {
                userList = objectMapper.readValue(userFile, new TypeReference<List<User>>() {});
            } catch (Exception e) {
                System.err.println("Error parsing user file: " + e.getMessage());
                userList = new ArrayList<>();
                saveUserListToFile();
            }
        }
//        userList = objectMapper.readValue(new File(USER_FILE_PATH), new TypeReference<List<User>>() {});
    }

    public Boolean loginUser(){
        if (user == null) {
            return false;
        }
        Optional<User> foundUser = userList.stream().filter(user1 -> {
            return user1.getName().equals(user.getName()) && UserServiceUtil.checkPassword(user.getPassword(), user1.getHashedPassword());
        }).findFirst();
        return foundUser.isPresent();
//        Optional<User> foundUser = userList.stream().filter(user1 -> {
//            return user1.getName().equals(user.getName()) && UserServiceUtil.checkPassword(user.getPassword(), user1.getHashedPassword());
//        }).findFirst();
//        return foundUser.isPresent();
    }

    public Boolean signUp(User user1){
        try{
            userList.add(user1);
            saveUserListToFile();
            System.out.println("User signed up successfully!");
            return Boolean.TRUE;
        }catch (IOException ex){
            System.err.println("Error during signup: " + ex.getMessage());
            ex.printStackTrace();
            return Boolean.FALSE;
        }
    }

    private void saveUserListToFile() throws IOException {
        File usersFile = new File(USER_FILE_PATH);
        usersFile.getParentFile().mkdirs(); // Create directories if they don't exist
        objectMapper.writeValue(usersFile, userList);
    }

    public void fetchBookings(){
        if (user == null) {
            System.out.println("No user logged in.");
            return;
        }

        Optional<User> userFetched = userList.stream().filter(user1 -> {
            return user1.getName().equals(user.getName()) && UserServiceUtil.checkPassword(user.getPassword(), user1.getHashedPassword());
        }).findFirst();
        if(userFetched.isPresent()){
            userFetched.get().printTickets();
        }
        else {
            System.out.println("User not found or invalid credentials.");
        }
    }

//    public Boolean cancelBooking(String ticketId){
//         todo: Complete this function
public Boolean cancelBooking(String ticketId){
    if (user == null || user.getTicketsBooked() == null) {
        System.out.println("No user logged in or no tickets found.");
        return Boolean.FALSE;
    }

    Scanner s = new Scanner(System.in);
    System.out.println("Enter the ticket id to cancel");
    ticketId = s.next();

    if (ticketId == null || ticketId.isEmpty()) {
        System.out.println("Ticket ID cannot be null or empty.");
        return Boolean.FALSE;
    }

    String finalTicketId = ticketId;
    boolean removed = user.getTicketsBooked().removeIf(ticket -> ticket.getTicketId().equals(finalTicketId));

    if (removed) {
        System.out.println("Ticket with ID " + ticketId + " has been canceled.");
        return Boolean.TRUE;
    } else {
        System.out.println("No ticket found with ID " + ticketId);
        return Boolean.FALSE;
    }
}


        public List<Train> getTrains(String source, String destination){
            try{
                TrainService trainService = new TrainService();
                return trainService.searchTrains(source, destination);
            }catch(IOException ex){
                System.err.println("Error getting trains: " + ex.getMessage());
                ex.printStackTrace();
                return new ArrayList<>();
            }
        }

        public List<List<Integer>> fetchSeats(Train train){
            if (train == null || train.getSeats() == null) {
                return new ArrayList<>();
            }
            return train.getSeats();
        }

        public Boolean bookTrainSeat(Train train, int row, int seat) {
            try{
                TrainService trainService = new TrainService();
                List<List<Integer>> seats = train.getSeats();
                if (row >= 0 && row < seats.size() && seat >= 0 && seat < seats.get(row).size()) {
                    if (seats.get(row).get(seat) == 0) {
                        seats.get(row).set(seat, 1);
                        train.setSeats(seats);
                        trainService.addTrain(train);
                        return true; // Booking successful
                    } else {
                        return false; // Seat is already booked
                    }
                } else {
                    return false; // Invalid row or seat index
                }
            }catch (IOException ex){
                System.err.println("Error booking seat: " + ex.getMessage());
                ex.printStackTrace();
                return Boolean.FALSE;
            }
        }
    }
