package com.metodipaskov.utils;

import java.sql.*;

public class DatabaseInteractions {

    private static final String HOST = "jdbc:mysql://127.0.0.1:3306/lms";
    private static final String USERNAME = "devuser";
    private static final String PASSWORD = "12345";
    private static final int FAILURE_CODE = -999;

    public static int createPerson(String fName, String lName, String address, long phone, String email, String password,
                                   int office, int desk, double salary) {

        // We are checking if user already exists
        String sql = "SELECT ID FROM LMS.PERSON WHERE FirstName = \"" + fName + "\" AND LastName = \"" + lName +
                "\" AND Email = \"" + email + "\"";
        int id = queryDbForId(sql);

        if (id < 0) {
            sql = "INSERT INTO LMS.PERSON VALUES (0, \"" + fName + "\", \"" + lName + "\", \"" + address + "\", " + phone +
                    ", \"" + email + "\", \"" + password + "\")";
            int result = updateDb(sql);

            if (result == 1) {
                sql = "SELECT ID FROM LMS.PERSON WHERE FirstName = \"" + fName + "\" AND LastName = \"" + lName +
                        "\" AND Email = \"" + email + "\"";
                id = queryDbForId(sql);

                if (id > 0) {
                    if (salary > 0) {
                        sql = "INSERT INTO LMS.STAFF VALUES (" + id + ", " + salary + ")";
                        result = updateDb(sql);

                        if (result == 1) {
                            if (office > 0) {   // This must be Librarian
                                sql = "INSERT INTO LMS.LIBRARIAN VALUES (" + id + ", " + office + ")";
                                result = updateDb(sql);
                                if (result == 1) {
                                    return id;
                                }

                            } else if (desk > 0) {  //This must be Clerk
                                sql = "INSERT INTO LMS.CLERK VALUES (" + id + ", " + desk + ")";
                                result = updateDb(sql);
                                if (result == 1) {
                                    return id;
                                }

                            } else {
                                System.out.println("Can't determine what kind of staff you are trying to add to the system.");
                                System.out.println("Please, verify input info and have the administrator correct the db tables -> lms.person & lms.staff");
                            }
                        }

                    } else {    // The user is Borrower type. Return the newly created user id
                        return id;
                    }

                } else {
                    System.out.println("Problem detected! User not created into Database.");
                }

            } else {
                System.out.println("Problem detected! User not inserted into Database.");
            }
        } else {
            System.out.println("This user is already configured in the system.");
        }

        return FAILURE_CODE;
    }

    public static int updatePerson(int id, String fName, String lName, String address, long phone, String email, String password,
                                   int office, int desk, double salary) {

        if (fName != null || lName != null || address != null || phone > 0 || email != null || password != null) {
            String sqlPt1 = "UPDATE LMS.PERSON SET ";
            String sqlPt2 = "";
            String sqlPt3 = " WHERE ID = " + id;

            String sql = null;

            if (fName != null) {
                sqlPt2 = "FirstName = \"" + fName + "\"";
            }

            if (lName != null) {
                if (sqlPt2.isEmpty()) {
                    sqlPt2 = "LastName = \"" + lName + "\"";
                } else {
                    sqlPt2 += ", LastName = \"" + lName + "\"";
                }
            }

            if (address != null) {
                if (sqlPt2.isEmpty()) {
                    sqlPt2 = "Address = \"" + address + "\"";
                } else {
                    sqlPt2 += ", Address = \"" + address + "\"";
                }
            }

            if (phone > 0) {
                if (sqlPt2.isEmpty()) {
                    sqlPt2 = "PhoneNumber = " + phone;
                } else {
                    sqlPt2 += ", PhoneNumber = " + phone;
                }
            }

            if (email != null) {
                if (sqlPt2.isEmpty()) {
                    sqlPt2 = "Email = \"" + email + "\"";
                } else {
                    sqlPt2 += ", Email = \"" + email + "\"";
                }
            }

            if (password != null) {
                if (password.isEmpty()) {
                    sqlPt2 = "Password = \"" + password + "\"";
                } else {
                    sqlPt2 += ", Password = \"" + password + "\"";
                }
            }

            if (!sqlPt2.isEmpty()) {
                sql = sqlPt1.concat(sqlPt2).concat(sqlPt3);
            }

            int result = updateDb(sql);
            if (result != 1) {
                System.out.println("Problem detected! During Person table update.");
                return FAILURE_CODE;
            }
        }

        if (office > 0 || desk > 0 || salary > 0) {
            String sql;

            if (office > 0) {
                sql = "UPDATE LMS.LIBRARIAN SET OfficeNumber = " + office + " WHERE ID = " + id;
                int result = updateDb(sql);
                if (result != 1) {
                    System.out.println("Problem detected! During Librarian table update.");
                    return FAILURE_CODE;
                }
            }

            if (desk > 0) {
                sql = "UPDATE LMS.CLERK SET DeskNumber = " + desk + " WHERE ID = " + id;
                int result = updateDb(sql);
                if (result != 1) {
                    System.out.println("Problem detected! During Clerk table update.");
                    return FAILURE_CODE;
                }
            }

            if (salary > 0) {
                sql = "UPDATE LMS.STAFF SET Salary = " + salary + " WHERE ID = " + id;
                int result = updateDb(sql);
                if (result != 1) {
                    System.out.println("Problem detected! During Staff table update.");
                    return FAILURE_CODE;
                }
            }

        }

        return 1;
    }

    public static int getPerson(int userId, String fullName, String email) {
        String sql = null;
        int id = FAILURE_CODE;
        if (userId > 0) {
            sql = "SELECT ID FROM LMS.PERSON WHERE ID = " + userId;
        } else if (fullName != null) {
            String[] name = fullName.split("\\s");
            String fName = name[0];
            String lName = name[1];
            sql = "SELECT ID FROM LMS.PERSON WHERE FirstName = \"" + fName + "\" AND LastName = \"" + lName + "\"";
        } else if (email != null) {
            sql = "SELECT ID FROM LMS.PERSON WHERE Email = \"" + email + "\"";
        }

        if (sql != null) {
            id = queryDbForId(sql);
        } else {
            System.out.println("Problem detected! Can't collect user info.");
            System.out.println("Provided data: ");
            System.out.println("ID: " + userId);
            System.out.println("Full Name: " + fullName);
            System.out.println("Email: " + email);
        }

        if (id == userId) {
            return id;
        } else {
            return FAILURE_CODE;
        }
    }


    public static int createBook(String title, String author, String genre) {
        String sql = "INSERT INTO LMS.BOOK VALUES (0, \"" + title + "\", \"" + author + "\", \"" + genre + "\", false)";

        int result = updateDb(sql);
        if (result == FAILURE_CODE) {
            System.out.println("Problem detected! The book can't be created.");
        } else {
            sql = "SELECT ID FROM LMS.BOOK WHERE title = \"" + title + "\" AND Author = \"" + author + "\"";

            int id = queryDbForId(sql);
            if (id == FAILURE_CODE) {
                System.out.println("Problem detected! The book is not created.");
            }
            return id;
        }
        return result;
    }

    public static int removeBook(int id) {
        String sql = "DELETE FROM LMS.BOOK WHERE ID = " + id;

        int result = updateDb(sql);
        if (result == FAILURE_CODE) {
            System.out.println("Problem detected! The book can't be deleted.");
        } else if (result == 0) {
            System.out.println("No book with id: " + id + ", found to delete.");
        } else {
            sql = "SELECT * FROM LMS.BOOK WHERE ID = " + id;

            result = queryDbForId(sql);
            if (result != FAILURE_CODE) {
                System.out.println("Problem detected! The book is not deleted.");
            }
        }
        return result;
    }

    private static int queryDbForId(String sqlStatement) {
        try (Connection connection = DriverManager.getConnection(HOST, USERNAME, PASSWORD);
             Statement statement = connection.createStatement()) {

            ResultSet result = statement.executeQuery(sqlStatement);
            if (result.next()) {
                return result.getInt("ID");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return FAILURE_CODE;
    }

    private static int updateDb(String sqlStatement) {
        try (Connection connection = DriverManager.getConnection(HOST, USERNAME, PASSWORD);
             Statement statement = connection.createStatement()) {

            return statement.executeUpdate(sqlStatement);

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return FAILURE_CODE;
    }

}
