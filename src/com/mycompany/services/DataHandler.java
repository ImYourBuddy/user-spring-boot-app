package com.mycompany.services;

import com.mycompany.models.User;
import org.springframework.stereotype.Service;

import java.io.*;

@Service
public class DataHandler {
    public boolean saveToFile(User user) {
        StringBuilder builder = new StringBuilder();
        if (user.getMiddleName().length() != 0) {
            builder.append(user.getFirstName() + " " +
                    user.getMiddleName() + " " +
                    user.getLastName() + ", " +
                    user.getAge() + ", " +
                    user.getEmail() + ", " +
                    user.getJob() + ", " +
                    user.getSalary() + ".");
        } else {
            builder.append(user.getFirstName() + " " +
                    user.getLastName() + ", " +
                    user.getAge() + ", " +
                    user.getEmail() + ", " +
                    user.getJob() + ", " +
                    user.getSalary() + ".");
        }
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("DataOfUsers.txt", true))) {
            if (checkStrings(builder.toString())) {
                return false;
            }
            builder.append("\n");
            writer.write(builder.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return true;
    }

    public User searchUser(User user) {
        String firstName = user.getFirstName();
        String lastName = user.getLastName();
        String line = null;
        String[] userData = null;
        try (BufferedReader reader = new BufferedReader(new FileReader("DataOfUsers.txt"))) {
            while ((line = reader.readLine()) != null) {
                userData = line.split(", ");
                String[] fullName = userData[0].split(" ");
                if (fullName.length == 3) {
                    if (firstName.equals(fullName[0]) && lastName.equals(fullName[2])) {
                        return new User(firstName, fullName[1], lastName, Integer.parseInt(userData[1]),
                                userData[2], userData[3], Integer.parseInt(userData[4].substring(0, userData[4].length() - 1)));

                    }
                } else {
                    if (firstName.equals(fullName[0]) && lastName.equals(fullName[1])) {
                        return new User(firstName, null, lastName, Integer.parseInt(userData[1]),
                                userData[2], userData[3], Integer.parseInt(userData[4].substring(0, userData[4].length() - 1)));
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void mergeFiles(String fileName) throws IOException {
        try (FileWriter writer = new FileWriter("DataOfUsers.txt", true);
             BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            String line = null;
            while ((line = reader.readLine()) != null) {
                if(!checkStrings(line)) {
                    writer.write(line + "\n");
                    writer.flush();
                }
            }
        }
    }

    private boolean checkStrings(String s) {
        try (BufferedReader reader = new BufferedReader(new FileReader("DataOfUsers.txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (s.equals(line)) {
                    return true;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }
}
