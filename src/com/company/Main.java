package com.company;

import java.sql.*;
import java.util.Scanner;

public class Main {
    private static final String DB_USERNAME = "postgres";
    private static final String DB_PASSWORD = "oandsandn312";
    private static final String DB_URL = "jdbc:postgresql://localhost:5432/postgres";

    public static void main(String[] args) throws SQLException {
        // Создали объект, который  считывает  информации с консоли
        Scanner scanner = new Scanner(System.in);
        // Создали подключение
        Connection connection = DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD);
        //Бесконечный вывод меню
        while (true) {
            System.out.println("1. Показать список всех задач ");
            System.out.println("2. Выполнить задачу ");
            System.out.println("3. Создать задачу ");
            System.out.println("4. Выйти ");
            // Считываем команды пользователя
            int command = scanner.nextInt();

            try {
                if (command == 1) {
                    // Объект который умеет отправлять запросы в БД
                    Statement statement = connection.createStatement();
                    // String SQL_SELECT_TASKS = "select * from   task order by id desc";
                    String SQL_SELECT_TASKS = "select * from  visitors where visitors_sex='M' order by id desc";
                    //Объект который хранить результат выполнения запроса
                    ResultSet result = statement.executeQuery(SQL_SELECT_TASKS);
                    // Просматриваем все данные, которые вернулись из БД и выводим их на экран
                    while (result.next()) {

//                        System.out.println(result.getInt("id") + " "
//                                + result.getString("name") + " "
//                                + result.getString("state"));
                        System.out.println(result.getInt("id") + " "
                                + result.getString("visitors_name_last_name") + " "
                                + result.getString("date_of_birth")+ " "
                        + result.getString("visitors_sex"));
                    }

                } else if (command == 2) {
                    // Описиваем запрос, не зная какие параметры там будут
                    String sql = "update task set state = 'DONE' where id = ?";
                    PreparedStatement preparedStatement = connection.prepareStatement(sql);
                    System.out.println("Введите идентификатор задачи: ");
                    int taskId = scanner.nextInt();
//                    кладем параметр который мы считали с консоли в строку запроса
                    preparedStatement.setInt(1, taskId);
                    preparedStatement.executeUpdate();

                } else if (command == 3) {
                    String sql = "INSERT INTO task (name, state) VALUES (?, 'in_process')";
                    PreparedStatement preparedStatement = connection.prepareStatement(sql);
                    System.out.println("Введите название задачи: ");
                    scanner.nextLine();
                    String taskName = scanner.nextLine();
                    preparedStatement.setString(1, taskName);
                    preparedStatement.executeUpdate();


                } else if (command == 4) {
                    System.exit(0);
                } else {
                    System.err.println("Команда не распознана");
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }


        }
    }
}
