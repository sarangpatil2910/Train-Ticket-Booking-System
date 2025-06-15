
# IRCTC Ticket Booking System 

A Java-based simulation of the IRCTC ticket booking system. This console application allows users to register, log in, search for trains, check availability, and simulate ticket bookings using Java classes and file-based data management.

## 🚀 Features

- 🔍 Train search by source, destination, and date  
- 🧾 Ticket booking and cancellation simulation  
- 🧑‍💻 User registration and login system  
- 🧮 Fare calculation and basic validations  
- 📁 File-based data storage (no database)  

## 🛠️ Tech Stack

- **Language:** Java (Core Java Concepts)  
- **Data Storage:** File I/O (Text or Serialized Files)  
- **IDE Recommended:** IntelliJ IDEA / Eclipse  
- **No external libraries or frameworks used**

## 📂 Project Structure (Example)

```
NEWIRCTC/
├── Main.java                    # Entry point of the application
├── User.java                    # User class with login/signup logic
├── Train.java                   # Train details and search logic
├── Booking.java                 # Booking details and ticket generation
├── DataManager.java             # Handles reading/writing from/to files
├── data/
│   ├── users.txt                # User data
│   └── trains.txt               # Train data
└── utils/
    └── ValidationUtil.java      # Input and logic validations
```

## 🧪 How to Run

1. Open the project in your preferred IDE (Eclipse / IntelliJ).
2. Compile all `.java` files.
3. Run the `Main.java` class.
4. Follow the CLI prompts to register, log in, and book tickets.

## 🧩 Future Enhancements

- Migrate to database (e.g., MySQL + JDBC)  
- GUI support with JavaFX or Swing  
- Add PNR status, waitlisting, and transaction history  
- Include payment and refund simulation  

## 📄 License

This project is intended for educational and demonstration purposes only. Not affiliated with official IRCTC.
